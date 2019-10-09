import controller.Controller;

import java.security.NoSuchAlgorithmException;

/**
 * There is class that start program
 */
public class Program {
    /**
     * @author Tanyana Bondareva
     * Here start point of program
     * @param args command line values
     * @throws NoSuchAlgorithmException if MD5 absents
     */
    public static void main(String[] args) throws NoSuchAlgorithmException {
        Controller controller = new Controller();
        controller.controllerWelcome();
    }
}
