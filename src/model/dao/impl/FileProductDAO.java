package model.dao.impl;

import model.bean.Product;

import java.io.*;
import java.util.ArrayList;
import model.dao.ProductDAO;
import model.dao.exception.DAOException;

/**
 * This class realize ProductDAO
 */
public class FileProductDAO implements ProductDAO {
    /**
     * Realize method that loads information about product from file
     * @param pathName path to file
     * @return list of product
     * @throws DAOException if it was exception
     */
    @Override
    public ArrayList<Product> loadList(String pathName) throws DAOException{
        ArrayList<Product> list = new ArrayList<>();
        try {
            File file = new File(pathName);
            FileReader fr = new FileReader(file);
            BufferedReader reader = new BufferedReader(fr);
            String name;

            while ((name = reader.readLine()) != null) {
                Product product = new Product();
                product.setName(name);
                product.setDescription(reader.readLine());
                product.setCount(Integer.parseInt(reader.readLine()));
                product.setPrice(Integer.parseInt(reader.readLine()));
                list.add(product);
            }
            reader.close();
        } catch (IOException ex) {
            throw new  DAOException(ex);
        }
        return list;
    }

    /**
     *Realize method that adds product to file
     * @param product product that needs to add to file
     * @param pathName path to file
     * @param isAppend Set it true if needs to append to file.Otherwise, set it false;
     * @throws DAOException if it was exception
     */
    @Override
     public void saveProduct(Product product, String pathName, boolean isAppend)throws DAOException{
        try (FileWriter writer = new FileWriter(pathName, isAppend)) {
            writer.append(product.getName());
            writer.append("\r\n");
            writer.append(product.getDescription());
            writer.append("\r\n");
            writer.append(String.valueOf(product.getCount()));
            writer.append("\r\n");
            writer.append(String.valueOf(product.getPrice()));
            writer.append("\r\n");
            writer.flush();
        } catch (IOException ex) {
            throw new  DAOException(ex);
        }
    }

    /**
     * Realize method that saves information about product to file
     * @param list list of products
     * @param pathName path to the file
     * @throws DAOException if it was exception
     */
    @Override
    public void saveList(ArrayList<Product> list, String pathName) throws DAOException{
        for (Product product:list){
            saveProduct(product, pathName, false);
        }
    }
}
