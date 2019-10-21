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


    /**
     * Compare object with this buyer
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
        Buyer buyer = (Buyer) obj;
        if (Basket.size()!=buyer.getBasket().size())
            return  false;
        for (int i=0; i<Basket.size(); i++) {
            if (!Basket.get(i).equals(buyer.getBasket().get(i)))
                return false;
        }
        return userName.equals(buyer.getUserName());
    }

    /**
     * Do hash code
     * @return hash code of buyer
     */
    @Override
    public int hashCode() {
        return super.hashCode();
    }

    /**
     * Do string from object
     * @return information about this buyer
     */
    @Override
    public String toString() {
        return "Покупатель " + userName;
    }

}
