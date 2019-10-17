package controller;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Scanner;

import model.bean.*;
import model.service.*;
import model.service.exception.ServiceException;
import  view.View;

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
    private Site site;

    /**
     * Here method than hashes password
     * @param password user password
     * @return hash of user password
     * @throws NoSuchAlgorithmException if MD5 absents
     */
    private String encryptMD5(String password) throws NoSuchAlgorithmException {
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
        try{
            GuestManager guestManager = new GuestManager();
            if (answer == 1) {
                if (guestManager.authorization(guest, "buyer", login, encryptMD5(password))) {
                    Buyer buyer = new Buyer();
                    buyer.setUserName(login);
                    controllerBuyer(site, buyer);
                    return true;
                }

            }
            if (answer == 2) {
                if (guestManager.authorization(guest, "admin", login, password)) {
                    controllerAdmin(site);
                    return true;
                }
            }
            if (answer == 3) {
                String repeatPassword;
                view.viewRepeatPassQuest();
                repeatPassword = in.next();
                while (!password.equals(repeatPassword)) {
                    view.viewException();
                    view.viewRepeatPassQuest();
                    repeatPassword = in.next();
                }
                if (!guestManager.registration(guest, login, encryptMD5(password))) {
                    view.viewChangeLogin();
                    return false;
                }
                Buyer buyer = new Buyer();//+создание файла покупателя
                buyer.setUserName(login);
                controllerBuyer(site, buyer);
                return true;
            }
        }catch (ServiceException ex){
            view.viewRunException();
        }
            return false;


    }


    /**
     * Fist controller which initializes client
     * @throws NoSuchAlgorithmException if MD5 absents
     */
    public void controllerWelcome() throws NoSuchAlgorithmException {
        Guest guest = new Guest();
        try {
            site = new Site();
        }catch (ServiceException ex){
            view.viewRunException();
            System.exit(1);
        }

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
        BuyerManger buyerManger = new BuyerManger(buyer);
        while (answer!=10){
            switch (answer){
                case 1:
                    list = site.getListProducts();
                    view.viewProductList(list);
                    break;
                case 2:
                    list =site.getListProducts();
                    list.sort(Product::compareTo);
                    view.viewProductList(list);
                    break;
                case 3:
                    list =site.getListProducts();
                    ProductManager productManager = new ProductManager();
                    list.sort(productManager::compareByPrice);
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
                    try {
                        if (buyerManger.addToBasket(site, name, count))
                            view.viewSuccess();
                        else
                            view.viewException();
                    }catch (ServiceException ex){
                        view.viewRunException();
                        System.exit(1);
                    }
                    break;
                case 6:
                    view.viewNameQuest();
                    name = in.next();
                    try {
                        if (buyerManger.deleteFromBasket(name))
                            view.viewSuccess();
                        else
                            view.viewException();
                    }catch (ServiceException ex){
                        view.viewRunException();
                        System.exit(1);
                    }
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
                    try {
                        if ( buyerManger.changeCount(site, name, count))
                            view.viewSuccess();
                        else
                            view.viewNotEnough();
                    }catch (ServiceException ex){
                        view.viewRunException();
                        System.exit(1);
                    }
                    break;
                case 8:
                    view.viewNameQuest();
                    name = in.next();
                    SiteManager siteManager = new SiteManager(site);
                    int index = siteManager.findByName(name);
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
                    try {
                        switch (buyerManger.buyProduct(site, name)){
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
                    }catch (ServiceException ex){
                        view.viewRunException();
                        System.exit(1);
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
        AdminManager adminManager = new AdminManager(admin);
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
                    try {
                        if (adminManager.addProductToSite(name,description,count,price))
                            view.viewSuccess();
                        else
                            view.viewException();
                    }catch (ServiceException ex){
                        view.viewRunException();
                        System.exit(1);
                    }
                    break;
                case 3:
                    view.viewNameQuest();
                    name = in.next();
                    try {
                        if (adminManager.deleteProduct(name))
                            view.viewSuccess();
                        else
                            view.viewException();
                    }catch (ServiceException ex){
                        view.viewRunException();
                        System.exit(1);
                    }
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
                    try {
                        if ( adminManager.changeProductCount(name, count))
                            view.viewSuccess();
                        else
                            view.viewException();
                    }catch (ServiceException ex){
                        view.viewRunException();
                        System.exit(1);
                    }
                    break;
                case 4:
                    view.viewNameQuest();
                    name = in.next();
                    view.viewNewNameQuest();
                    String newName = in.next();
                    try {
                        if (adminManager.changeProductName(name, newName))
                            view.viewSuccess();
                        else
                            view.viewException();
                    }catch (ServiceException ex){
                        view.viewRunException();
                        System.exit(1);
                    }
                    break;
                case 6:
                    view.viewNameQuest();
                    name = in.next();
                    view.viewDescriptionQuest();
                    description = in.next();
                    try {
                        if (adminManager.changeDescription(name, description))
                            view.viewSuccess();
                        else
                            view.viewException();
                    }catch (ServiceException ex){
                        view.viewRunException();
                        System.exit(1);
                    }
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
                    try {
                        if (adminManager.changePrice(name, price))
                            view.viewSuccess();
                        else
                            view.viewException();
                    }catch (ServiceException ex){
                        view.viewRunException();
                        System.exit(1);
                    }
                    break;
                case 8:
                    view.viewNameQuest();
                    name = in.next();
                    SiteManager siteManager = new SiteManager(site);
                    int index = siteManager.findByName(name);
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
