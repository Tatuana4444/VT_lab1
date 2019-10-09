package model;

import java.io.*;
import java.util.ArrayList;

/**
 * This class implements the guest yfunctions
 */
public class Guest  implements Serializable {
    /**
     * user id
     */
    private  int id;

    /**
     * calculate guest id
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
     * This method registers user on thq site
     * @param login user login
     * @param password user password
     * @return if such login has already existed, if will return false.Otherwise it will return false
     */
    public boolean Registration(String login, String password) {//шифрование????
        try {
            File f = new File(login+".txt");
            if (!f.createNewFile())
                return false;
        }
        catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        Source.saveLoginPass(login+' '+password);
        return true;
    }

    /**
     * This method authorizes the user in the system
     * @param kindUser kind of user(admin or buyer)
     * @param Login user login
     * @param password user password
     * @return if login and password is correct, it will return true. Otherwise, it will return false
     */
    public boolean Authorization(String kindUser, String Login, String password) {
        String loginPass = Login+" "+password;
        ArrayList<String> listLoginPass = Source.loadLoginPass(kindUser+".txt");
            for (String str: listLoginPass)
               if (loginPass.equals(str))
                   return true;
        return false;
    }
}
