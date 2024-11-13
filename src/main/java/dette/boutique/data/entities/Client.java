package dette.boutique.data.entities;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;;

@Data
@EqualsAndHashCode(callSuper = false, of = { "nom", "prenom", "telephone" })
@Entity
@ToString(exclude = { "user", "dettes" })
@Table(name = "client")
public class Client extends AbstractEntity {

    @Column(length = 25, unique = true, nullable = false)
    private String telephone;

    @Column(length = 255, nullable = false)
    private String adresse;

    @OneToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "user_id", nullable = true)
    private User user;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Dette> dettes;

    @Transient()
    private int increment = 0;

    @Column(nullable = false)
    private String nom;

    @Column(nullable = false)
    private String prenom;

    // public Client() {
    // this.user = null;
    // this.id += increment;
    // }

    public Client() {
    }
    public Client(String nom, String prenom, String adresse, String telephone) {
        this.id = ++increment;
        this.nom = nom;
        this.prenom = prenom;
        this.adresse = adresse;
        this.telephone = telephone;
        this.user = null;
    }

    // public Client(String nom, String prenom, String adresse, String telephone,
    // User user) {
    // this.id = ++increment;
    // this.nom = nom;
    // this.prenom = prenom;
    // this.adresse = adresse;
    // this.telephone = telephone;
    // this.user = user;
    // }

    public Client(int id, String nom, String prenom, String telephone, String adresse, User user) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.adresse = adresse;
        this.telephone = telephone;
        this.user = user;
    }
}
