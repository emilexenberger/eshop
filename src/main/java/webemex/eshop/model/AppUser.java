package webemex.eshop.model;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.springframework.security.crypto.bcrypt.BCrypt;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "app_user")
public class AppUser {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(updatable = false, nullable = false, columnDefinition = "CHAR(36)")
    @Type(type = "uuid-char")
    private UUID id;
    private String username;
    private String password;
    private String name;
    private String surname;
    private String role = "ROLE_USER";

    @OneToMany(
            mappedBy="appUser", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL
    )
    private List<CartItem> cartItems;

    @OneToMany(
            mappedBy="appUser", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL
    )
    private List<OrderItem> orderItems;

    public static String hashPassword(String password) {
        String salt = BCrypt.gensalt(12);
        String hashedPassword = BCrypt.hashpw(password, salt);
        return hashedPassword;
    }
    public void setPassword(String password) {
        this.password = hashPassword(password);
    }

    public String getPassword() {
        return password;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "AppUser{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
