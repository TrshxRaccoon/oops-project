package security;

public class UnauthorisedAccessException extends Exception 
{
    public UnauthorisedAccessException(String msg) 
    {
        super(msg);
    }
}