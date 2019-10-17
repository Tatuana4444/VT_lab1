package model.bean;

import model.bean.Product;
import model.dao.ProductDAO;
import model.dao.exception.DAOException;
import model.dao.factory.DAOFactory;
import model.service.exception.ServiceException;

import java.io.*;
import java.util.ArrayList;

/**
 * There is class that describes site
 */
public class Site  implements Serializable {

    /**
     * Here list of product that buyer can buy
     */
    private ArrayList<Product> listProducts;

    /**
     * Constructor of site that initializes list of product
     * @throws ServiceException if it was DAOException
     */
    public Site() throws ServiceException{
        DAOFactory daoFactory = DAOFactory.getInstance();
        ProductDAO productDAO = daoFactory.getProductDAO();
        try {
            listProducts = productDAO.loadList("site.txt");
        }catch (DAOException ex){
            throw new ServiceException(ex);
        }

    }

    /**
     * This method will return list of product
     * @return list of product
     */
    public ArrayList<Product> getListProducts() {

        return listProducts;
    }
}
