package model;

import java.io.Serializable;

/**
 * This class implements administrator functions
 */
public class Admin  implements Serializable {
    /**
     * Here method that add product to site
     * @param site site with which admin works
     * @param name  name of product
     * @param description description of product
     * @param count count of product, that users can buy
     * @param price price of product
     * @return  if it is at list of product or price or count is negative or price is 0, it will return false. Otherwise it will return true
     */
    public boolean addProductToSite(Site site, String name, String description, int count, int price){
        if ((count<0)||(price<=0))
            return  false;
        if (!site.addProduct(name, description, count, price))
            return  false;
        Source.saveProduct(site.getListProducts().get(site.findByName(name)),"site.txt");
        return true;
    }

    /**
     *Here method that delete product from list and file
     * @param site site with which admin works
     * @param name name of product
     * @return if it isn't at list of product, it will return false. Otherwise it will return true
     */
    public boolean deleteProduct(Site site, String name){
        if (!site.deleteProduct(name))
            return  false;
        Source.saveList(site.getListProducts(), "site.txt");
        return  true;
    }

    /**
     *Here method that change product count in list and file
     * @param site site with which admin works
     * @param name name of product
     * @param count new count of product, that users can buy
     * @return if it isn't at list of product count is negative, it will return false. Otherwise it will return true
     */
    public  static  boolean  changeProductCount(Site site, String name, int count){
        if (!site.changeProductCount(name, count))
            return  false;
        if (count<0)
            return false;
        Source.saveList(site.getListProducts(), "site.txt");
        return  true;
    }

    /**
     * Here method that change product name in list and file
     * @param site site with which admin works
     * @param name name of product
     * @param newName new name of product
     * @return if it isn't at list of product or price, it will return false. Otherwise it will return true
     */
    public  boolean  changeProductName(Site site, String name, String newName){
        if (!site.changeProductName(name, newName))
            return  false;
        Source.saveList(site.getListProducts(), "site.txt");
        return  true;
    }

    /**
     *Here method that change product description in list and file
     * @param site site with which admin works
     * @param name name of product
     * @param description new description of product
     * @return if it isn't at list of product, it will return false. Otherwise it will return true
     */
    public  boolean  changeDescription(Site site, String name, String description){

        if (!site.changeDescription(name, description))
            return  false;
        Source.saveList(site.getListProducts(), "site.txt");
        return  true;
    }

    /**
     *Here method that change product price in list and file
     * @param site site with which admin works
     * @param name name of product
     * @param price new price of product
     * @return if it isn't at list of product or price is negative or 0, it will return false. Otherwise it will return true
     */
    public  boolean  changePrice(Site site, String name, int price){

        if (!site.changeProductPrice(name, price))
            return  false;
        if (price<=0)
            return false;
        Source.saveList(site.getListProducts(), "site.txt");
        return  true;
    }
}
