package model.bean;

import java.io.Serializable;

/**
 * There is class that describes product
 */
public class Product implements Serializable, Comparable<Product>  {

    /**
     * Count of product
     */
    private int count = 0;

    /**
     * Product name
     */
    private String name = "";

    /**
     * Product description
     */
    private String description ="";

    /**
     * Product price
     */
    private int price = 0;

    /**
     * Constructor of product
     */
    public Product(){}
    /**
     * This fields will return count of product in stock
     * @return count of product
     */
    public int getCount() {
        return count;
    }

    /**
     * This fields will set new count of product
     * @param count count of product
     */
    public void setCount(int count) {
        this.count = count;
    }

    /**
     * This fields will return product name
     * @return product name
     */
    public String getName() {
        return name;
    }

    /**
     *This fields will set new product name
     * @param name product name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * This fields will return product description
     * @return product description
     */
    public String getDescription() {
        return description;
    }

    /**
     * This fields will set new product description
     * @param description product description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * This fields will return product price
     * @return product price
     */
    public int getPrice() {
        return price;
    }

    /**
     * This fields will set new product price
     * @param price product price
     */
    public void setPrice(int price) {
        this.price = price;
    }

    /**
     * Compare product by name
     * @param prod product with witch needs to compare this product
     * @return it will return 0, if names are equal. It will return positive value, if current product name lager than prod name. Otherwise, it will return negative value
     */
    @Override
    public int compareTo(Product prod) {
        return name.compareTo(prod.getName());
    }

}