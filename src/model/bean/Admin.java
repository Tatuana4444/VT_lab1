package model.bean;

import java.io.Serializable;

/**
 * There is class that describes administrator
 */
public class Admin  implements Serializable {

    /**
     * Constructor of administrator
     */
    public Admin(){}
    /**
     * Site that users use
     */
    private Site site;

    /**
     * This fields will return site
     * @return Site that users use
     */
    public Site getSite() {
        return site;
    }

    /**
     * This fields will set site
     * @param site site that users use
     */
    public void setSite(Site site) {
        this.site = site;
    }


    /**
     * Compare object with this admin
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
        Admin admin = (Admin)obj;
        return site==admin.getSite();
    }

    /**
     * Do hash code
     * @return hash code of admin
     */
    @Override
    public int hashCode() {
        return super.hashCode();
    }

    /**
     * Do string from object
     * @return information about this admin
     */
    @Override
    public String toString() {
        return "Администратор сайта";
    }
}
