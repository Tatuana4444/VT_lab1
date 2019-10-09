package model;

import java.io.*;
import java.util.ArrayList;

/**
 * This class updates and downloads information from file
 */
class Source {

    /**
     * This method load information about product from file
     * @param pathName path to file
     * @return list of product
     */
    static ArrayList<Product> loadList(String pathName) {
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
            System.out.println(ex.getMessage());
        }
        return list;
    }

    /**
     *Add product to file
     * @param product product that needs to add to file
     * @param pathName path to file
     */
    static void saveProduct(Product product, String pathName){
        try (FileWriter writer = new FileWriter(pathName, true)) {
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
            System.out.println(ex.getMessage());
        }
    }

    /**
     * This method save information about product to file
     * @param list list of products
     * @param pathName path to the file
     */
     static void saveList(ArrayList<Product> list, String pathName) {
         try (FileWriter writer = new FileWriter(pathName, false)) {
             for (Product product:list){
                 writer.append(product.getName());
                 writer.append("\r\n");
                 writer.append(product.getDescription());
                 writer.append("\r\n");
                 writer.append(String.valueOf(product.getCount()));
                 writer.append("\r\n");
                 writer.append(String.valueOf(product.getPrice()));
                 writer.append("\r\n");
                 writer.flush();
             }
         } catch (IOException ex) {
             System.out.println(ex.getMessage());
         }



     }

    /**
     * This method load information about users from file
     * @param pathName path to the file
     * @return list of users login and password
     */
    static ArrayList<String> loadLoginPass(String pathName) {
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
            System.out.println(ex.getMessage());
        }
        return list;
    }

    /**
     * This method save information about users to file
     * @param loginPass user login and password
     */
    static void saveLoginPass(String loginPass) {
        try (FileWriter writer = new FileWriter("buyer.txt", true)) {
                writer.append(loginPass);
                writer.append("\r\n");
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
