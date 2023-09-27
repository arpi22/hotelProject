import java.io.Serializable;

public class OperatorUser implements Serializable {
    private final String name;
    private final String email;
    private final String password;
    private final int id;

    public OperatorUser(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.id = System.identityHashCode(this);
    }

    public String getName() {
        return this.name;
    }

    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
