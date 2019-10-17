package view;


import model.bean.Product;

import java.util.ArrayList;

/**
 * This class responsible for displaying data to the user
 */
public class View {

    /**
     * Here method than welcomes user
     * @param userName user of user
     */
    public void viewWelcome(String userName){
        System.out.println("Добро пожаловать на сайт "+userName);
    }

    /**
     * Here method that prompts user to register or authorize
     */
    public void viewGuestInfo(){
        System.out.println("Нажмите 1, чтобы войти как покупатель");
        System.out.println("Нажмите 2, чтобы войти как администратор");
        System.out.println("Нажмите 3, чтобы зарегистрироваться");
        System.out.println("Нажмите 4,  чтобы выйти из системы");
    }

    /**
     * Here method that asks user about his login and password
     */
    public void viewLoginAndPassQuest(){
        System.out.println("Введите логин и пароль");
    }

    /**
     * Here method that asks user to repeat his password
     */
    public void viewRepeatPassQuest(){
        System.out.println("Повторите пароль");
    }

    /**
     *Here method that describes all functions of customer
     */
    public void viewBuyerInfo(){
        System.out.println("Нажмите 1, чтобы увидеть список товаров сайта(по дате добавления)");
        System.out.println("Нажмите 2, чтобы отсортировать по имени");
        System.out.println("Нажмите 3, чтобы отсортировать по цене");
        System.out.println("Нажмите 4, чтобы увидеть товары в корзине");
        System.out.println("Нажмите 5, чтобы добавить товар в корзину");
        System.out.println("Нажмите 6, чтобы удалить товар из корзины");
        System.out.println("Нажмите 7, чтобы изменить количество товара");
        System.out.println("Нажмите 8, чтобы найти товар по имени");
        System.out.println("Нажмите 9, чтобы купить товар");
        System.out.println("Нажмите 10, чтобы выйти из системы");
    }

    /**
     *Here method that says user about exception
     */
    public void viewException(){
        System.out.println("Ошибка! Повторите, пожалуйста, ввод");
    }

    /**
     *Here method that says user about success operation
     */
    public void viewSuccess(){
        System.out.println("Выполнено успешно");
    }

    /**
     * Here method that says user about product
     * @param product product that user wants to see
     */
    public void  viewProduct(Product product){
        System.out.println(product.getName());
        System.out.println("    *price: " + product.getPrice());
        System.out.println("    *description: " + product.getDescription());
        System.out.println("    *count: " + product.getCount());
    }

    /**
     * This method shows user list of product
     * @param list list of product
     */
    public  void  viewProductList(ArrayList<Product> list){
        for(Product product:list){
            viewProduct(product);
        }
    }

    /**
     * This method asks user about name of product
     */
    public  void  viewNameQuest(){
        System.out.println("Ввелите название товара");
    }

    /**
     * This method asks user about new name of product
     */
    public  void  viewNewNameQuest(){
        System.out.println("Ввелите новое азвание товара");
    }

    /**
     * This method asks user about count of product
     */
    public  void  viewCountQuest(){
        System.out.println("Введите количество товара");
    }

    /**
     * This method asks user about description of product
     */
    public  void  viewDescriptionQuest(){
        System.out.println("Введите описание товара");
    }

    /**
     * This method asks user about price of product
     */
    public  void  viewPriceQuest(){
        System.out.println("Введите цену товара");
    }


    /**
     * This method says user than the desired product is missing
     */
    public void viewNoProduct(){
        System.out.println("Товар не найден");
    }

    /**
     * This method says user than product absent in basket
     */
    public void viewNoInBasket(){
        System.out.println("Такого товара в корзине нет");
    }

    /**
     * This method says user than product absent in site
     */
    public void viewNoOnSite(){
        System.out.println("Такого товара на сайти больше нет");
    }

    /**
     * This method says user than there aren't enough product in stock
     */
    public void viewNotEnough(){
        System.out.println("На складе недостаточно товара");
    }

    /**
     * This method says user than description was change
     */
    public void viewDescriptionChanged(){
        System.out.println("Описание товара на сайте было изменено. Проверте информацию и попробуйте еще раз");
    }

    /**
     * This method says user than such password has already existed
     */
    public void viewChangeLogin(){
        System.out.print("Такой логин уже существует. ");
    }

    /**
     *Here method that describes all functions of customer
     */
    public void viewAdminInfo(){
        System.out.println("Нажмите 1, чтобы увидеть список товаров сайта(по дате добавления)");
        System.out.println("Нажмите 2, чтобы добавить товар в корзину");
        System.out.println("Нажмите 3, чтобы удалить товар из корзины");
        System.out.println("Нажмите 4, чтобы изменить название товара");
        System.out.println("Нажмите 5, чтобы изменить количество товара");
        System.out.println("Нажмите 6, чтобы изменить описание товара");
        System.out.println("Нажмите 7, чтобы изменить цену товара");
        System.out.println("Нажмите 8, чтобы найти товар по имени");
        System.out.println("Нажмите 9, чтобы выйти из системы");
    }

    /**
     * Here method that says about runtime error
     */
    public void viewRunException(){
        System.out.print("Ошибка во время выполнения. ");
    }

}
