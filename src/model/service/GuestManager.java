package model.service;

import model.bean.Guest;
import model.dao.UserDAO;
import model.dao.exception.DAOException;
import model.dao.factory.DAOFactory;
import model.service.exception.ServiceException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * This class implements the guest functions
 */
public class GuestManager {

    /**
     * This method registers user on thq site
     * @param guest user who works with site
     * @param login user login
     * @param password user password
     * @return if such login has already existed, if will return false.Otherwise it will return false
     * @throws ServiceException if it was DAOException
     */
    public boolean registration(Guest guest, String login, String password) throws ServiceException{
        if (guest==null)
            return  false;
        try {
            File f = new File(login+".txt");
            if (!f.createNewFile())
                return false;
        }
        catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        DAOFactory daoFactory = DAOFactory.getInstance();
        UserDAO userDAO = daoFactory.getUserDAO();
        try {
            userDAO.saveLoginPass(login+' '+password);
        }
        catch (DAOException ex){
            throw new ServiceException(ex);
        }
        return true;
    }

    /**
     * This method authorizes the user in the system
     * @param guest user who works with site
     * @param kindUser kind of user(admin or buyer)
     * @param login user login
     * @param password user password
     * @return if login and password is correct, it will return true. Otherwise, it will return false
     * @throws ServiceException if it was DAOException
     */
    public boolean authorization(Guest guest, String kindUser, String login, String password) throws ServiceException{
        if (guest==null)
            return  false;
        String loginPass = login+' '+password;
        DAOFactory daoFactory = DAOFactory.getInstance();
        UserDAO userDAO = daoFactory.getUserDAO();
        try {
            ArrayList<String> listLoginPass = userDAO.loadLoginPass(kindUser+".txt");
            for (int i=0; i<listLoginPass.size();i++)
                if (loginPass.equals(listLoginPass.get(i)))
                    return true;
        }
        catch (DAOException ex){
            throw new ServiceException(ex);
        }
        return false;
    }
}
