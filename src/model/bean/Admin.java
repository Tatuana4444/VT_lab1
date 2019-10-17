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
}
