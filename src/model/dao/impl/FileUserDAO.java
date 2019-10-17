package model.dao.impl;

import java.io.*;
import java.util.ArrayList;
import model.dao.UserDAO;
import model.dao.exception.DAOException;

/**
 * This class realize UserDAO
 */
public class FileUserDAO implements UserDAO{
    /**
     * Realize method that loads information about users from file
     * @param pathName path to the file
     * @return list of users login and password
     * @throws DAOException if it was exception
     */
    @Override
    public ArrayList<String> loadLoginPass(String pathName) throws DAOException{
        ArrayList<String> list = new ArrayList<>();
        try {
            File file = new File(pathName);
            FileReader fr = new FileReader(file);
            BufferedReader reader = new BufferedReader(fr);
            String name;
            while ((name = reader.readLine()) != null) {
                list.add(name);
            }
            reader.close();
        } catch (IOException ex) {
            throw new  DAOException(ex);
        }
        return list;
    }

    /**
     * Realize method that saves information about users to file
     * @param loginPass user login and password
     * @throws DAOException if it was exception
     */
    @Override
    public void saveLoginPass(String loginPass) throws DAOException{
        try (FileWriter writer = new FileWriter("buyer.txt", true)) {
            writer.append(loginPass);
            writer.append("\r\n");
        } catch (IOException ex) {
            throw new DAOException(ex);
        }
    }
}
