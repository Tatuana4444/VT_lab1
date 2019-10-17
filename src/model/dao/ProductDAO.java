package model.dao;

import model.bean.Product;
import model.dao.exception.DAOException;
import java.util.ArrayList;

/**
 * Interface for ProductDAO implementation
 */
public interface ProductDAO {
    /**
     * This method loads information about product from file
     * @param pathName path to file
     * @return list of product
     * @throws DAOException if it was exception
     */
    ArrayList<Product> loadList(String pathName) throws DAOException;

    /**
     * This method adds product to file
     * @param product product that needs to add to file
     * @param pathName path to file
     * @param isAppend Set it true if needs to append to file.Otherwise, set it false;
     * @throws DAOException if it was exception
     */
    void saveProduct(Product product, String pathName, boolean isAppend) throws DAOException;

    /**
     * This method saves information about product to file
     * @param list list of products
     * @param pathName path to the file
     * @throws DAOException if it was exception
     */
    void saveList(ArrayList<Product> list, String pathName)throws DAOException;

}
