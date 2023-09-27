import java.io.Serializable;

public class CustomerUser implements Serializable {
    private final String name;
    private final String email;
    private final String password;

    public CustomerUser(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return this.name;
    }

    public String getPassword() {
        return password;
    }
}
