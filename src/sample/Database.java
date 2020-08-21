package sample;

public interface Database {




    public boolean verifyPassword(String user, String password);
    public boolean userExists(String user);
    public void createUser(String user, String password);
    public void deleteUser(String user);

    }
