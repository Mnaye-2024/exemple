package dette.boutique.core.factory.Impl;

import dette.boutique.core.factory.RepositoryFactory;
import dette.boutique.data.repository.ArticleRepository;
import dette.boutique.data.repository.bdImpl.ArticleRepositoryDbImpl;
import dette.boutique.data.repository.jpaImpl.ArticleRepositoryJpaImpl;
import dette.boutique.data.repository.listImpl.ArticleRepositoryListImpl;
import jakarta.persistence.EntityManagerFactory;

public class ArticleRepositoryFactory implements RepositoryFactory<ArticleRepository> {
    protected EntityManagerFactory emf;

    public ArticleRepositoryFactory(EntityManagerFactory emf) {
        this.emf = emf;
    }

    @Override
    public ArticleRepository create(String persistence) {
        switch (persistence) {
            case "MYSQL", "POSTGRES" -> {
                return new ArticleRepositoryJpaImpl(emf.createEntityManager());
            }
            case "JDBC" -> {
                return new ArticleRepositoryDbImpl();
            }
            case "LIST" -> {
                return new ArticleRepositoryListImpl();
            }
            default -> throw new UnsupportedOperationException("Unknown persistence type: " + persistence);
        }
    }
}
