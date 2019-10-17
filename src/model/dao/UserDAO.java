package model.dao;

import model.dao.exception.DAOException;

import java.util.ArrayList;

/**
 * Interface for UserDAO implementation
 */
public interface UserDAO {
    /**
     * This method loads information about users from file
     * @param pathName path to the file
     * @return list of users login and password
     * @throws DAOException if it was exception
     */
    ArrayList<String> loadLoginPass(String pathName)throws DAOException;

    /**
     * This method saves information about users to file
     * @param loginPass user login and password
     * @throws DAOException if it was exception
     */
    void saveLoginPass(String loginPass)throws DAOException;
}
