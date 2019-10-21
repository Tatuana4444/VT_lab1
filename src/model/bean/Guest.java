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

    /**
     * Compare object with this guest
     * @param obj object with which it will be compare
     * @return it will return true if objects are equal
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        Guest guest = (Guest) obj;
        return id == guest.getId();
    }

    /**
     * Do hash code
     * @return hash code of guest
     */
    @Override
    public int hashCode() {
        return super.hashCode();
    }

    /**
     * Do string from object
     * @return information about this guest
     */
    @Override
    public String toString() {
        return "Гость " + id;
    }
}
