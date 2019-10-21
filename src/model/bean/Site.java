package model.bean;

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

    /**
     * Compare object with this site
     * @param obj object with which it will be compare
     * @return it will return true if objects are equal
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        Site site = (Site) obj;
        if (listProducts.size()!=site.getListProducts().size())
            return  false;
        for (int i=0; i<listProducts.size(); i++) {
            if (!listProducts.get(i).equals(site.listProducts.get(i)))
                return false;
        }
        return true;
    }

    /**
     * Do hash code
     * @return hash code of site
     */
    @Override
    public int hashCode() {
        return super.hashCode();
    }

    /**
     * Do string from object
     * @return information about this site
     */
    @Override
    public String toString() {
        return "Сайт";
    }
}
