package model.service.exception;

/**
 * This class describe ServiceException
 */
public class ServiceException extends Exception{

    /**
     * Used for serialization
     */
    private static final long serialVersionUID = 1L;

    /**
     * ServiceException handle
     * @param e information about exception
     */
    public ServiceException(Exception e){
        super(e);
    }
}
