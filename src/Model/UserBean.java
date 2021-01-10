package Model;

public class UserBean {
    private int UserID;
    private String username;
    private String password;

    public UserBean() {
    }

    public UserBean(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public UserBean(int userID, String username, String password) {
        UserID = userID;
        this.username = username;
        this.password = password;
    }

    public int getUserID() {
        return UserID;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return "UserBean{" +
                "Username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
