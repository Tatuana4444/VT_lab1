package model.service;

import model.bean.Buyer;
import model.bean.Product;
import model.bean.Site;
import model.dao.ProductDAO;
import model.dao.exception.DAOException;
import model.dao.factory.DAOFactory;
import model.service.exception.ServiceException;

/**
 * This class implements buyer functions
 */
public class BuyerManger {

    /**
     * Buyer whom functions will be implemented
     */
    private Buyer buyer;

    /**
     * Constructor that initialize buyer
     * @param buyer buyer whom functions will be implemented
     */
    public BuyerManger(Buyer buyer){
        this.buyer = buyer;
    }


    /**
     * This method finds index of product in basket
     * @param name name of desired product
     * @return index of desired product
     */
    private int findByName(String name){
        for(Product product:buyer.getBasket())
            if (product.getName().equals(name) )
                return buyer.getBasket().indexOf(product);
        return  -1;
    }


    /**
     * This method adds product to basket
     * @param site site with which user works
     * @param name name of product
     * @param count count of product, that user wants to buy
     * @return if product isn't at list of product, it will return false. Otherwise it will return true
     * @throws ServiceException if it was DAOException
     */
    public boolean addToBasket(Site site, String name, int count) throws ServiceException{
        SiteManager siteManager = new SiteManager(site);
        int index = siteManager.findByName(name);
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
        buyer.getBasket().add(productInBasket);
        DAOFactory daoFactory = DAOFactory.getInstance();
        ProductDAO productDAO = daoFactory.getProductDAO();
        try {
            productDAO.saveList(buyer.getBasket(), buyer.getUserName()+".txt");
        }
        catch (DAOException ex){
            throw new ServiceException(ex);
        }
        return true;
    }


    /**
     * This method changes count of  product that user wants to buy
     * @param site site with which user works
     * @param name name of product
     * @param count new count of product, that user wants to buy
     * @return if it is at list of product, it will return false. Otherwise it will return true
     * @throws ServiceException if it was DAOException
     */
    public boolean changeCount(Site site, String name, int count) throws ServiceException{
        int index = findByName(name);
        if (index == -1)
            return  false;
        Product product = buyer.getBasket().get(index);
        SiteManager siteManager = new SiteManager(site);
        int indexSite = siteManager.findByName(name);
        if (indexSite == -1)
            return  false;
        if (count>site.getListProducts().get(indexSite).getCount())
            return false;
        product.setCount(count);
        DAOFactory daoFactory = DAOFactory.getInstance();
        ProductDAO productDAO = daoFactory.getProductDAO();
        try {
            productDAO.saveList(buyer.getBasket(), buyer.getUserName()+".txt");
        }
        catch (DAOException ex){
            throw new ServiceException(ex);
        }
        return true;
    }


    /**
     * This method deletes product from basket
     * @param name name of product
     * @return if it is at list of product, it will return false. Otherwise it will return true
     * @throws ServiceException if it was DAOException
     */
    public boolean deleteFromBasket(String name)throws ServiceException{
        int index = findByName(name);
        if (index == -1)
            return  false;
        buyer.getBasket().remove(index);
        DAOFactory daoFactory = DAOFactory.getInstance();
        ProductDAO productDAO = daoFactory.getProductDAO();
        try {
            productDAO.saveList(buyer.getBasket(), buyer.getUserName()+".txt");
        }
        catch (DAOException ex){
            throw new ServiceException(ex);
        }
        return true;
    }


    /**
     * This method emulates a purchase in shop
     * @param site site with which user works
     * @param name name of product
     * @return It will return 1, if it isn't product in basket.It will return 2, if it isn't product in site.
     * It will return 3, if it isn't enough product in stock. It will return 4, if description of product was changed.
     * It will return 0, if purchase was successful
     * @throws ServiceException if it was DAOException
     */
    public int buyProduct(Site site, String name) throws ServiceException{
        int indexInBasket = findByName(name);
        if (indexInBasket == -1)
            return  1;
        Product productInBasket = buyer.getBasket().get(indexInBasket);
        SiteManager siteManager = new SiteManager(site);
        int indexInSite = siteManager.findByName(name);
        if (indexInSite == -1)
            return  2;
        Product productInSite = site.getListProducts().get(indexInSite);
        if (productInBasket.getCount()>=productInSite.getCount())
            return 3;
        if ((productInBasket.getDescription().equals(productInSite.getDescription())) &
                (productInBasket.getPrice() == productInSite.getPrice())){
            siteManager.changeProductCount(name,productInSite.getCount()-productInBasket.getCount());
            deleteFromBasket(name);
            DAOFactory daoFactory = DAOFactory.getInstance();
            ProductDAO productDAO = daoFactory.getProductDAO();
            try {
                productDAO.saveList(buyer.getBasket(), buyer.getUserName()+".txt");
            }
            catch (DAOException ex){
                throw new ServiceException(ex);
            }
            return 0;
        }
        productInBasket.setDescription(productInSite.getDescription());
        productInBasket.setPrice(productInSite.getPrice());
        DAOFactory daoFactory = DAOFactory.getInstance();
        ProductDAO productDAO = daoFactory.getProductDAO();
        try {
            productDAO.saveList(buyer.getBasket(), buyer.getUserName()+".txt");
        }
        catch (DAOException ex){
            throw new ServiceException(ex);
        }
        return  4;
    }
}
