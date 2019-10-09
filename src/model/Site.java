package model;

import java.io.*;
import java.util.ArrayList;

/**
 *There is class than describe my site
 */
public class Site  implements Serializable {

    /**
     * Here list of product that buyer can buy
     */
    private ArrayList<Product> listProducts=Source.loadList("site.txt");


    /**
     * This method find product by name
     * @param name name of desired product
     * @return index of desired product
     */
    public int findByName(String name){//поиск по имени
        for(Product product: listProducts)
            if (product.getName().equals(name))
                return listProducts.indexOf(product);
        return  -1;
    }

    /**
     * This method add new product in list
     * @param name name of product
     * @param description description of product
     * @param count count of product in stock
     * @param price price of product
     * @return if it is at list of product, it will return false. Otherwise it will return true
     */
    boolean addProduct(String name, String description, int count, int price){
        if (this.findByName(name)>=0)
            return  false;
        Product product = new Product();
        product.setName(name);
        product.setDescription(description);
        product.setCount(count);
        product.setPrice(price);
        listProducts.add(product);
        return true;
    }

    /**
     * This method will return list of product
     * @return list of product
     */
    public ArrayList<Product> getListProducts() {

        return listProducts;
    }

    /**
     * This method delete product from site
     * @param name name of product than s to delete
     * @return if it isn't at list of product, it will return false. Otherwise it will return true
     */
    boolean deleteProduct(String name){
        int index = this.findByName(name);
        if (index == -1)
            return  false;
        listProducts.remove(index);
        return true;
    }

    /**
     * This method change count of product on new value
     * @param name name of product that needs to change count
     * @param count new count of product
     * @return if product is missing than it will return false, otherwise it will return true
     */
    boolean  changeProductCount(String name, int count){
        int index = this.findByName(name);
        if (index == -1)
            return  false;
        Product product = listProducts.get(index);
        product.setCount(count);
        listProducts.set(index, product);
        return true;
    }

    /**
     * This method change name of product
     * @param name old version of name
     * @param newName new version of name
     * @return if product is missing than it will return false, otherwise it will return true
     */
    boolean  changeProductName(String name, String newName){
        int index = this.findByName(name);
        if (index == -1)
            return  false;
        Product product = listProducts.get(index);
        product.setName(newName);
        listProducts.set(index, product);
        return true;
    }

    /**
     * This method change description of product
     * @param name name of product that needs a change of description
     * @param description new version of decription
     * @return if product is missing than it will return false, otherwise it will return true
     */
    boolean  changeDescription(String name, String description){
        int index = this.findByName(name);
        if (index == -1)
            return  false;
        Product product = listProducts.get(index);
        product.setDescription(description);
        listProducts.set(index, product);
        return true;
    }

    /**
     * This method change price of product
     * @param name name of product that needs a change of price
     * @param price new version of price
     * @return if product is missing than it will return false, otherwise it will return true
     */
    boolean  changeProductPrice(String name, int price){
        int index = this.findByName(name);
        if (index == -1)
            return  false;
        Product product = listProducts.get(index);
        product.setPrice(price);
        listProducts.set(index, product);
        return true;
    }

}
