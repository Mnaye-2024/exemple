package dette.boutique.data.entities;

import org.hibernate.annotations.ColumnDefault;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = false, of = { "login", "password" })
@Entity
@ToString(exclude = { "client" })
@NamedQueries({
        @NamedQuery(name = "selectByLogin", query = "SELECT u FROM User u WHERE u.login Like :login")
})
@Table(name = "users")
public class User extends AbstractEntity {

    @Column(length = 25, unique = true, nullable = false)
    private String login;

    @ColumnDefault(value = "true")
    private boolean active;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    @Column(nullable = false)
    private String password;

    @OneToOne(mappedBy = "user")
    @JoinColumn(name = "client", unique = true, nullable = true)
    private Client client;

    @Transient
    private int increment = 0;

    public User() {
    }

    // public User() {
    //     this.client = null;
    //     this.active = false;
    //     this.id += increment;
    // }

    public User(String login, String passsword, Role role) {
        this.id += increment;
        this.login = login;
        this.password = passsword;
        this.role = role;
        this.active = false;
    }

    public User(int id, String login, String passsword, Role role) {
        this.id = id;
        this.login = login;
        this.password = passsword;
        this.role = role;
        this.active = false;
    }

    public User(String login, String password, Client client, Role role) {
        this.id += increment;
        this.password = password;
        this.login = login;
        this.client = client;
        this.role = role;
        this.active = true;
    }

    public User(int id, String nom, String prenom, String login, String password, Role role) {
        this.id = id;
        this.password = password;
        this.login = login;
        this.role = role;
        this.active = true;
    }

}
