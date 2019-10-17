package model.service;

import model.bean.Product;
import model.bean.Site;
import model.dao.ProductDAO;
import model.dao.exception.DAOException;
import model.dao.factory.DAOFactory;
import model.service.exception.ServiceException;

/**
 * This class implements site functions
 */
public class SiteManager {

    /**
     *  Site which functions will be implemented
     */
    private Site site;

    /**
     * Constructor that initializes site
     * @param site Site which functions will be implemented
     */
    public SiteManager(Site site){
        this.site = site;
    }
    /**
     * This method find product by name
     * @param name name of desired product
     * @return index of desired product
     */
    public int findByName(String name){
        for(Product product:site.getListProducts())
            if (product.getName().equals(name))
                return site.getListProducts().indexOf(product);
        return  -1;
    }

    /**
     * This method adds new product in list
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
        site.getListProducts().add(product);
        return true;
    }

    /**
     * This method deletes product from site
     * @param name name of product than s to delete
     * @return if it isn't at list of product, it will return false. Otherwise it will return true
     */
    boolean deleteProduct(String name){
        int index = this.findByName(name);
        if (index == -1)
            return  false;
        site.getListProducts().remove(index);
        return true;
    }

    /**
     * This method changes count of product on new value
     * @param name name of product that needs to change count
     * @param count new count of product
     * @return if product is missing than it will return false, otherwise it will return true
     * @throws ServiceException if it was DAOException
     */
    boolean  changeProductCount(String name, int count) throws ServiceException{
        int index = this.findByName(name);
        if (index == -1)
            return  false;
        Product product = site.getListProducts().get(index);
        product.setCount(count);
        site.getListProducts().set(index, product);
        DAOFactory daoFactory = DAOFactory.getInstance();
        ProductDAO productDAO = daoFactory.getProductDAO();
        try {
            productDAO.saveList(site.getListProducts(), "site.txt");
        }
        catch (DAOException ex){
            throw new ServiceException(ex);
        }
        return true;
    }

    /**
     * This method changes name of product
     * @param name old version of name
     * @param newName new version of name
     * @return if product is missing than it will return false, otherwise it will return true
     */
     boolean  changeProductName(String name, String newName){
        int index = this.findByName(name);
        if (index == -1)
            return  false;
        Product product = site.getListProducts().get(index);
        product.setName(newName);
        site.getListProducts().set(index, product);
        return true;
    }

    /**
     * This method changes description of product
     * @param name name of product that needs a change of description
     * @param description new version of decription
     * @return if product is missing than it will return false, otherwise it will return true
     */
     boolean  changeDescription(String name, String description){
        int index = this.findByName(name);
        if (index == -1)
            return  false;
        Product product = site.getListProducts().get(index);
        product.setDescription(description);
        site.getListProducts().set(index, product);
        return true;
    }

    /**
     * This method changes price of product
     * @param name name of product that needs a change of price
     * @param price new version of price
     * @return if product is missing than it will return false, otherwise it will return true
     */
     boolean  changeProductPrice(String name, int price){
        int index = this.findByName(name);
        if (index == -1)
            return  false;
        Product product = site.getListProducts().get(index);
        product.setPrice(price);
        site.getListProducts().set(index, product);
        return true;
    }
}
