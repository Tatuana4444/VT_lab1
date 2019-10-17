package model.service;

import model.bean.Product;

/**
 * This class implements functions of the product
 */
public class ProductManager {

    /**
     * This method compares product by price
     * @param product1 first product with witch needs to compare this product
     * @param product2 second product with witch needs to compare this product
     * @return it will return 0, if prices are equal. It will return positive value, if current product price lager than prod price. Otherwise, it will return negative value
     */
    public int compareByPrice(Product product1, Product product2) {
        return Integer.compare(product1.getPrice(), product2.getPrice());
    }

}
