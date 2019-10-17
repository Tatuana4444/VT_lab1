package model.bean;

import java.io.*;

/**
 * There is class that describes guest
 */
public class Guest  implements Serializable {

    /**
     * user id
     */
    private  int id;

    /**
     * Constructor of guest that initializes guest id
     */
    public  Guest(){
        id=(int)(Math.random()*8999999+1000000);
    }

    /**
     *This fields will return user id
     * @return user id
     */
    public int getId() {
        return id;
    }


}
