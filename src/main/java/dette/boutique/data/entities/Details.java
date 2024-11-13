package dette.boutique.data.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false, of = { "article", "quantite", "prixTotal" })
@Entity
@Table(name = "details")
public class Details extends AbstractEntity {

    @Column(nullable = false)
    private int quantite;

    @Column(nullable = false)
    private int prixTotal;

    @ManyToOne()
    @JoinColumn(name = "article_id")
    private Article article;

    @ManyToOne()
    @JoinColumn(name = "dette_id")
    private Dette dette;

    public Details() {
    }
    public Details(Article article, int quantité) {
        this.article = article;
        this.quantite = quantité;
        this.prixTotal = article.getPrixUnitaire() * quantité;
    }
}
