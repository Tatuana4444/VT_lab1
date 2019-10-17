package model.service;

import model.bean.Admin;
import model.dao.ProductDAO;
import model.dao.exception.DAOException;
import model.dao.factory.DAOFactory;
import model.service.exception.ServiceException;

/**
 * This class implements administrator functions
 */
public class AdminManager {
    /**
     * Administrator whom functions will be implemented
     */
    private Admin admin;

    /**
     * Functions of site with which administrator works
     */
    private SiteManager siteManager;

    /**
     * Constructor that initializes admin and siteManager
     * @param admin administrator whom functions will be implemented
     */
    public AdminManager(Admin admin){
        this.admin = admin;
        siteManager = new SiteManager(admin.getSite());
    }

    /**
     * Here method that adds product to site
     * @param name  name of product
     * @param description description of product
     * @param count count of product, that users can buy
     * @param price price of product
     * @return  if it is at list of product or price or count is negative or price is 0, it will return false. Otherwise it will return true
     * @throws ServiceException if it was DAOException
     */
    public boolean addProductToSite(String name, String description, int count, int price) throws ServiceException{
        if ((count<0)||(price<=0))
            return  false;
        if (!siteManager.addProduct(name, description, count, price))
            return  false;
        DAOFactory daoFactory = DAOFactory.getInstance();
        ProductDAO productDAO = daoFactory.getProductDAO();
        try {
            productDAO.saveProduct(admin.getSite().getListProducts().get(siteManager.findByName(name)),"site.txt", true);
        }
        catch (DAOException ex){
            throw new ServiceException(ex);
        }
        return true;
    }

    /**
     *Here method that deletes product from list and file
     * @param name name of product
     * @return if it isn't at list of product, it will return false. Otherwise it will return true
     * @throws ServiceException if it was DAOException
     */
    public boolean deleteProduct(String name)throws ServiceException{
        if (!siteManager.deleteProduct(name))
            return  false;
        DAOFactory daoFactory = DAOFactory.getInstance();
        ProductDAO productDAO = daoFactory.getProductDAO();
        try {
            productDAO.saveList(admin.getSite().getListProducts(), "site.txt");
        }
        catch (DAOException ex){
            throw new ServiceException(ex);
        }
        return  true;
    }

    /**
     *Here method that changes product count in list and file
     * @param name name of product
     * @param count new count of product, that users can buy
     * @return if it isn't at list of product count is negative, it will return false. Otherwise it will return true
     * @throws ServiceException if it was DAOException
     */
    public boolean  changeProductCount(String name, int count) throws ServiceException{
        if (count<0)
            return false;
            return siteManager.changeProductCount(name, count);
    }

    /**
     * Here method that changes product name in list and file
     * @param name name of product
     * @param newName new name of product
     * @return if it isn't at list of product or price, it will return false. Otherwise it will return true
     * @throws ServiceException if it was DAOException
     */
    public  boolean  changeProductName(String name, String newName) throws ServiceException{
        if (!siteManager.changeProductName(name, newName))
            return  false;
        DAOFactory daoFactory = DAOFactory.getInstance();
        ProductDAO productDAO = daoFactory.getProductDAO();
        try {
            productDAO.saveList(admin.getSite().getListProducts(), "site.txt");
        }
        catch (DAOException ex){
            throw new ServiceException(ex);
        }
        return  true;
    }

    /**
     *Here method that changes product description in list and file
     * @param name name of product
     * @param description new description of product
     * @return if it isn't at list of product, it will return false. Otherwise it will return true
     * @throws ServiceException if it was DAOException
     */
    public  boolean  changeDescription(String name, String description) throws ServiceException{

        if (!siteManager.changeDescription(name, description))
            return  false;
        DAOFactory daoFactory = DAOFactory.getInstance();
        ProductDAO productDAO = daoFactory.getProductDAO();
        try {
            productDAO.saveList(admin.getSite().getListProducts(), "site.txt");
        }
        catch (DAOException ex){
            throw new ServiceException(ex);
        }
        return  true;
    }

    /**
     *Here method that changes product price in list and file
     * @param name name of product
     * @param price new price of product
     * @return if it isn't at list of product or price is negative or 0, it will return false. Otherwise it will return true
     * @throws ServiceException if it was DAOException
     */
    public  boolean  changePrice(String name, int price) throws ServiceException{

        if (!siteManager.changeProductPrice(name, price))
            return  false;
        if (price<=0)
            return false;
        DAOFactory daoFactory = DAOFactory.getInstance();
        ProductDAO productDAO = daoFactory.getProductDAO();
        try {
            productDAO.saveList(admin.getSite().getListProducts(), "site.txt");
        }
        catch (DAOException ex){
            throw new ServiceException(ex);
        }
        return  true;
    }
}
