package model.dao.exception;

/**
 * This class describes DAOException
 */
public class DAOException extends Exception{

    /**
     * Used for serialization
     */
    private static final long serialVersionUID = 1L;

    /**
     * DAOException handle
     * @param e information about exception
     */
    public DAOException(Exception e){
        super(e);
    }

}
