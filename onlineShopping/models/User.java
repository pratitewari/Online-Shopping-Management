//user 
package onlineShopping.models;

public class User {
    private String username;
    private String password;
    private boolean isAuthenticated;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.isAuthenticated = false;
    }

    public boolean authenticate(String inputUsername, String inputPassword) {
        if (this.username.equals(inputUsername) && this.password.equals(inputPassword)) {
            isAuthenticated = true;
        }
        return isAuthenticated;
    }

    public boolean isAuthenticated() {
        return isAuthenticated;
    }

    public String getUsername() {
        return username;
    }
}
