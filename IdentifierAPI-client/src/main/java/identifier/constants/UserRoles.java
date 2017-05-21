package identifier.constants;

/**
 * Created by ldalzotto on 14/04/2017.
 */
public enum UserRoles {

    ROLE_USER("ROLE_USER"),
    ROLE_ADMIN("ROLE_ADMIN");

    private String role;

    UserRoles(String role) {
        this.role = role;
    }

    public String getRole(){
        return this.role;
    }

    @Override
    public String toString() {
        return "UserRoles{" +
                "role='" + role + '\'' +
                '}';
    }
}
