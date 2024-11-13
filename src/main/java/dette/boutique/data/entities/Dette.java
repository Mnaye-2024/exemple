package dette.boutique.data.entities;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false, of = { "client", "montantVerse" })
@Entity
@Table(name = "dettes")
public class Dette extends AbstractEntity {

    @ManyToOne
    @JoinColumn(nullable = false)
    private Client client;

    @OneToMany(mappedBy = "dette")
    private List<Details> details;

    @Column(name = "montant-verse", nullable = false)
    private int montantVerse;

    @Column(nullable = false)
    private int montant;

    @Transient
    private int montantRestant;

    @Transient
    private static int newDette = 0;

    // public Dette() {
    // this.id = ++newDette;
    // }

    public Dette() {
    }
    public Dette(int montant, int montantVerse, Client client, List<Details> details) {
        this.id = ++newDette;
        this.montant = montant;
        this.montantVerse = montantVerse;
        this.montantRestant = montant - montantVerse;
        this.client = client;
        this.details = details;
    }

    @Override
    public String toString() {
        return "Client: " + client.getNom() + " " + client.getPrenom() + "; montant:" + montant + "; montant versé:"
                + montantVerse + "; montant restant:" + montantRestant;
    }
}
