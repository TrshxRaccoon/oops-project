package users;

public interface Authentication 
{
    boolean login(String id);
    void logout();
    boolean isLoggedIn();
    String getRole();
}