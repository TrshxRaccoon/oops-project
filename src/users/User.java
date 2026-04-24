package users;

public abstract class User 
{
    protected String id;//unique ID for each user
    protected String name;//name of the user    

    public User(String id, String name) 
    {
        this.id = id;
        this.name = name;
    }

    public String getId() 
    {
        return id;
    }

    public String getName() 
    {
        return name;
    }

    public abstract void display();
}