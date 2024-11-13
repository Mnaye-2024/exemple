package dette.boutique.data.entities;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false, of = {"ref", "libelle"})
@Entity
@Table(name = "articles")
public class Article extends AbstractEntity {

    @Column(length = 55, nullable = false)
    private String ref;

    @Column(length = 55, unique = true, nullable = false)
    private String libelle;

    @Column(nullable = false)
    private int qteStock;

    @Column(nullable = false)
    private int prixUnitaire;

    @OneToMany(mappedBy = "article")
    private List<Details> details;

    @Transient
    private static int increment = 0;

    // public Article() {
    // this.id += increment;
    // this.ref = String.format("A%06d", id);
    // }

    public Article() {
    }

    public Article(String lib, int price, int stock) {
        this.id += increment;
        this.ref = String.format("A%06d", id);
        this.libelle = lib;
        this.prixUnitaire = price;
        this.qteStock = stock;
    }

    @Override
    public String toString() {
        return "Article[ref=" + ref + "; libelle=" + libelle + "; prix unitaire=" + prixUnitaire + "; stock= "
                + qteStock
                + "]";
    }
}
