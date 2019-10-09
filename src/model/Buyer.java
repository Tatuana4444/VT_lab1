package model;

import java.io.*;
import java.util.ArrayList;

/**
 * This class implements the functions of the buyer
 */
public class Buyer  implements Serializable{
    /**
     * User login
     */
    private String userName;


    /**
     * List of product that buyer adds to basket
     */
    private ArrayList<Product> Basket = new ArrayList<>();


    /**
     * This fields will set user login
     * @param userName user login
     */
    public void setUserName(String userName) {

        this.userName = userName;
        Basket = Source.loadList(userName+".txt");
    }


    /**
     * This fields will return user login
     * @return user login
     */
    public String getUserName() {

        return userName;
    }


    /**
     * This fiels will return list of product in basket
     * @return list of product in basket
     */
    public ArrayList<Product> getBasket() {
        return Basket;
    }


    /**
     * This method find index of product in basket
     * @param name name of desired product
     * @return index of desired product
     */
    private int findByName(String name){//поиск по имени
        for(Product product:Basket)
            if (product.getName().equals(name) )
                return Basket.indexOf(product);
        return  -1;
    }


    /**
     * This method adds product to basket
     * @param site site with which user works
     * @param name name of product
     * @param count count of product, that user wants to buy
     * @return if product isn't at list of product, it will return false. Otherwise it will return true
     */
    public boolean addToBasket(Site site, String name, int count){
        int index = site.findByName(name);
        if (index == -1)
            return  false;
        if (findByName(name)>=0)
            return  false;
        if (count < 1)
            return  false;
        if (site.getListProducts().get(index).getCount()<count)
            return false;
        Product product = site.getListProducts().get(index);
        Product productInBasket = new Product();
        productInBasket.setCount(count);
        productInBasket.setDescription(product.getDescription());
        productInBasket.setName(product.getName());
        productInBasket.setPrice(product.getPrice());
        Basket.add(productInBasket);
        Source.saveList(Basket, userName+".txt");
        return true;
    }


    /**
     * This method change count of  product that user wants to buy
     * @param site site with which user works
     * @param name name of product
     * @param count new count of product, that user wants to buy
     * @return if it is at list of product, it will return false. Otherwise it will return true
     */
    public boolean changeCount(Site site, String name, int count){
        int index = findByName(name);
        if (index == -1)
            return  false;
        Product product = Basket.get(index);
        int indexSite = site.findByName(name);
        if (indexSite == -1)
            return  false;
        if (count>site.getListProducts().get(indexSite).getCount())
            return false;
        product.setCount(count);
        Source.saveList(Basket, userName+".txt");
        return true;
    }


    /**
     * This method delete product from basket
     * @param name name of product
     * @return if it is at list of product, it will return false. Otherwise it will return true
     */
    public boolean deleteFromBasket(String name){
        int index = findByName(name);
        if (index == -1)
            return  false;
        Basket.remove(index);
        Source.saveList(Basket, userName+".txt");
        return true;
    }


    /**
     * This method emulates a purchase in shop
     * @param site site with which user works
     * @param name name of product
     * @return It will return 1, if it isn't product in basket.It will return 2, if it isn't product in site.
     * It will return 3, if it isn't enough product in stock. It will return 4, if description of product was changed.
     * It will return 0, if purchase was successful
     */
    public int buyProduct(Site site, String name){
        int indexInBasket = findByName(name);
        if (indexInBasket == -1)
            return  1;
        Product productInBasket = Basket.get(indexInBasket);
        int indexInSite = site.findByName(name);
        if (indexInSite == -1)
            return  2;
        Product productInSite = site.getListProducts().get(indexInSite);
        if (productInBasket.getCount()>=productInSite.getCount())
            return 3;
        if ((productInBasket.getDescription().equals(productInSite.getDescription())) &
                (productInBasket.getPrice() == productInSite.getPrice())){
            Admin.changeProductCount(site, name,productInSite.getCount()-productInBasket.getCount());
            deleteFromBasket(name);
            Source.saveList(Basket, userName+".txt");
            return 0;
        }
        productInBasket.setDescription(productInSite.getDescription());
        productInBasket.setPrice(productInSite.getPrice());
        Source.saveList(Basket, userName+".txt");
        return  4;
    }
}
