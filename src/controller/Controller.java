package controller;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Scanner;
import  view.View;
import model.*;

/**
 *Interprets user actions and alerts the model about changes
 */
public class Controller {
    /**
     *to display model results
     */
    private View view = new View();

    /**
     * site with which user works
     */
    private Site site = new Site();

    /**
     * Here method than hashes password
     * @param password user password
     * @return hash of user password
     * @throws NoSuchAlgorithmException if MD5 absents
     */
    private String EncryptMD5(String password) throws NoSuchAlgorithmException {
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        byte[] bytes=md5.digest(password.getBytes());
        StringBuilder builder = new StringBuilder();
        for (byte b: bytes)
            builder.append(String.format("%02X",b));
        return  builder.toString();
    }

    /**
     * Check answer, login and password on correct values
     * @param answer response number
     * @param login user's login
     * @param password user's password
     * @param in reads data from a source
     * @param guest current user
     * @return if login was performed, it will return true. Otherwise, it will return false
     * @throws NoSuchAlgorithmException if MD5 absents
     */
    private boolean checkAnswer(int answer,String login, String password, Scanner in, Guest guest) throws NoSuchAlgorithmException {
        if (answer==1){
            if (guest.Authorization("buyer", login, EncryptMD5(password))) {
                Buyer buyer = new Buyer();
                buyer.setUserName(login);
                controllerBuyer(site, buyer);
                return true;
            }

        }
        if (answer==2){
            if (guest.Authorization("admin", login, password)){
                controllerAdmin(site);
                return true;
            }
        }
        if (answer==3){
            String repeatPassword;
            view.viewRepeatPassQuest();
            repeatPassword = in.next();
            while (!password.equals(repeatPassword)){
                view.viewException();
                view.viewRepeatPassQuest();
                repeatPassword = in.next();
            }
            if (!guest.Registration(login, EncryptMD5(password))){
                view.viewChangeLogin();
                return  false;
            }
            Buyer buyer = new Buyer();//+создание файла покупателя
            buyer.setUserName(login);
            controllerBuyer(site, buyer);
            return true;
        }
        return  false;
    }


    /**
     * Fist controller which initialize client
     * @throws NoSuchAlgorithmException if MD5 absents
     */
    public void controllerWelcome() throws NoSuchAlgorithmException {
        Guest guest = new Guest();
        int answer;
        Scanner in = new Scanner(System.in);
        view.viewWelcome("User"+guest.getId());
        view.viewGuestInfo();
        try {
            answer = Integer.parseInt(in.next());
        }catch (NumberFormatException e) {
           answer = 0;
        }

        while (answer!=4){
            while ((answer>3)||(answer<1)) {
                view.viewException();
                view.viewGuestInfo();
                try {
                    answer = Integer.parseInt(in.next());
                }
                catch (NumberFormatException e) {
                    answer=0;
                }
            }

            String login, password;
            view.viewLoginAndPassQuest();
            login =in.next();
            password = in.next();
            while (!checkAnswer(answer, login, password, in, guest)){
                view.viewException();
                view.viewLoginAndPassQuest();
                login =in.next();
                password = in.next();
            }
            view.viewGuestInfo();
            try {
                answer = Integer.parseInt(in.next());
            }catch (NumberFormatException e) {
                answer=0;
            }
        }
        in.close();
    }

    /**
     * Controller that answers responsible for function of Buyer
     * @param site site with which buyer work
     * @param buyer current customer
     */
    private void controllerBuyer(Site site, Buyer buyer){
        view.viewWelcome(buyer.getUserName());
        view.viewBuyerInfo();
        int answer, count;
        String name;
        Scanner in = new Scanner(System.in);
        ArrayList<Product> list;
        try {
            answer = Integer.parseInt(in.next()) ;
        }catch (NumberFormatException e) {
            answer = 0;
        }

        while (answer!=10){
            switch (answer){
                case 1:
                    list =site.getListProducts();
                    view.viewProductList(list);
                    break;
                case 2:
                    list =site.getListProducts();
                    list.sort(Product::compareTo);
                    view.viewProductList(list);
                    break;
                case 3:
                    list =site.getListProducts();
                    list.sort(Product::compareByPrice);
                    view.viewProductList(list);
                    break;
                case 4:
                    list =buyer.getBasket();
                    view.viewProductList(list);
                    break;
                case 5:
                    view.viewNameQuest();
                    name = in.next();
                    view.viewCountQuest();
                    try {
                        count = Integer.parseInt(in.next());
                    }catch (NumberFormatException e) {
                        view.viewException();
                        break;
                    }
                    if (buyer.addToBasket(site, name, count))
                        view.viewSuccess();
                    else
                        view.viewException();
                    break;
                case 6:
                    view.viewNameQuest();
                    name = in.next();
                    if (buyer.deleteFromBasket(name))
                        view.viewSuccess();
                    else
                        view.viewException();
                    break;
                case 7:
                    view.viewNameQuest();
                    name = in.next();
                    view.viewCountQuest();
                    try {
                        count = Integer.parseInt(in.next());
                    }catch (NumberFormatException e) {
                       view.viewException();
                        break;
                    }
                    if ( buyer.changeCount(site, name, count))
                        view.viewSuccess();
                    else
                        view.viewNotEnough();
                    break;
                case 8:
                    view.viewNameQuest();
                    name = in.next();
                    int index = site.findByName(name);
                    if (index>0){
                        Product product = site.getListProducts().get(index);
                        view.viewProduct(product);
                    }
                    else
                        view.viewNoProduct();
                    break;
                case 9:
                    view.viewNameQuest();
                    name = in.next();
                    switch (buyer.buyProduct(site, name)){
                        case 0:
                            view.viewSuccess();
                        break;
                        case 1:
                            view.viewNoInBasket();
                        break;
                        case 2:
                            view.viewNoOnSite();
                            break;
                        case 3:
                            view.viewNotEnough();
                            break;
                        case 4:
                            view.viewDescriptionChanged();
                            break;
                }
                    break;
                default:
                    view.viewException();
                    break;
            }
            view.viewBuyerInfo();
            try {
                answer = Integer.parseInt(in.next());
            }catch (NumberFormatException e) {
                answer = 0;
            }
        }

    }

    /**
     * Controller that answers responsible for function of Admin
     * @param site  site with which admin work
     */
    private void controllerAdmin(Site site){

        Admin admin = new Admin();
        view.viewWelcome("Admin");
        view.viewAdminInfo();
        int answer, count, price;
        String name, description;
        Scanner in = new Scanner(System.in);
        ArrayList<Product> list;
        try {
            answer =Integer.parseInt(in.next()) ;
        }catch (NumberFormatException e) {
            answer = 0;
        }

        while (answer != 9){
            switch (answer){
                case 1:
                    list = site.getListProducts();
                    view.viewProductList(list);
                    break;
                case 2:
                    view.viewNameQuest();
                    name = in.next();
                    view.viewDescriptionQuest();
                    description = in.next();
                    view.viewCountQuest();
                    try {
                        count = Integer.parseInt(in.next());
                    }catch (NumberFormatException e) {
                        view.viewException();
                        break;
                    }
                    view.viewPriceQuest();
                    try {
                        price = Integer.parseInt(in.next());
                    }catch (NumberFormatException e) {
                        view.viewException();
                        break;
                    }

                    if (admin.addProductToSite(site,name,description,count,price))
                        view.viewSuccess();
                    else
                        view.viewException();
                    break;
                case 3:
                    view.viewNameQuest();
                    name = in.next();
                    if (admin.deleteProduct(site, name))
                        view.viewSuccess();
                    else
                        view.viewException();
                    break;
                case 5:
                    view.viewNameQuest();
                    name = in.next();
                    view.viewCountQuest();
                    try {
                        count = Integer.parseInt(in.next());
                    }catch (NumberFormatException e) {
                        view.viewException();
                        break;
                    }
                    if ( Admin.changeProductCount(site, name, count))
                        view.viewSuccess();
                    else
                        view.viewException();
                    break;
                case 4:
                    view.viewNameQuest();
                    name = in.next();
                    view.viewNewNameQuest();
                    String newName = in.next();
                    if (admin.changeProductName(site, name, newName))
                        view.viewSuccess();
                    else
                        view.viewException();
                    break;
                case 6:
                    view.viewNameQuest();
                    name = in.next();
                    view.viewDescriptionQuest();
                    description = in.next();
                    if (admin.changeDescription(site, name, description))
                        view.viewSuccess();
                    else
                        view.viewException();
                    break;
                case 7:
                    view.viewNameQuest();
                    name = in.next();
                    view.viewPriceQuest();
                    try {
                        price = Integer.parseInt(in.next());
                    }catch (NumberFormatException e) {
                        view.viewException();
                        break;
                    }

                    if (admin.changePrice(site, name, price))
                        view.viewSuccess();
                    else
                        view.viewException();
                    break;
                case 8:
                    view.viewNameQuest();
                    name = in.next();
                    int index = site.findByName(name);
                    if (index>0){
                        Product product = site.getListProducts().get(index);
                        view.viewProduct(product);
                    }
                    else
                        view.viewNoProduct();
                    break;
                default:
                    view.viewException();
                    break;
            }
            view.viewAdminInfo();

            try {
                answer = Integer.parseInt(in.next());
            }catch (NumberFormatException e) {
                answer=0;
            }
        }
    }
}
