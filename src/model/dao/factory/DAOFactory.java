package model.dao.factory;

import model.dao.UserDAO;
import model.dao.ProductDAO;
import model.dao.impl.FileProductDAO;
import model.dao.impl.FileUserDAO;

/**
 * Singleton realization  for UserDAO and ProductDAO
 */
public class DAOFactory {

    /**
     * Create object DAOFactory
     */
    private  static  final DAOFactory instance = new DAOFactory();

    /**
     * Create object UserDAO
     */
    private final UserDAO fileUserImpl = new FileUserDAO();

    /**
     * Create object ProductDAO
     */
    private final ProductDAO fileProductImpl = new FileProductDAO();

    /**
     * Constructor for DAOFactory
     */
    private  DAOFactory(){}

    /**
     * This method returns DAOFactory
     * @return object DAOFactory
     */
    public static DAOFactory getInstance() {
        return instance;
    }

    /**
     * This method returns ProductDAO implementation
     * @return ProductDAO implementation
     */
    public ProductDAO getProductDAO() {
        return fileProductImpl;
    }

    /**
     * This method returns UserDAO implementation
     * @return UserDAO
     */
    public UserDAO getUserDAO() {
        return fileUserImpl;
    }
}
