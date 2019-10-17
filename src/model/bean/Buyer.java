package model.bean;

import model.dao.ProductDAO;
import model.dao.exception.DAOException;
import model.dao.factory.DAOFactory;
import model.service.exception.ServiceException;

import java.io.*;
import java.util.ArrayList;

/**
 * There is class that describes buyer
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
     * Constructor of buyer
     */
    public Buyer(){}

    /**
     * This fields will set user login
     * @param userName user login
     * @throws ServiceException if it was DAOException
     */
    public void setUserName(String userName) throws ServiceException {

        this.userName = userName;
        DAOFactory daoFactory = DAOFactory.getInstance();
        ProductDAO productDAO = daoFactory.getProductDAO();
        try {
            Basket = productDAO.loadList(userName+".txt");
        }catch (DAOException ex){
            throw new ServiceException(ex);
        }

    }


    /**
     * This fields will return user login
     * @return user login
     */
    public String getUserName() {

        return userName;
    }


    /**
     * This fields will return list of product in basket
     * @return list of product in basket
     */
    public ArrayList<Product> getBasket() {
        return Basket;
    }



}
