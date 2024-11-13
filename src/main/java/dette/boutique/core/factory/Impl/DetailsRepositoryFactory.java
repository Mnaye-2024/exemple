package dette.boutique.core.factory.Impl;

import dette.boutique.core.factory.RepositoryFactory;
import dette.boutique.data.repository.DetailsRepository;
import dette.boutique.data.repository.bdImpl.DetailsRepositoryDbImpl;
import dette.boutique.data.repository.jpaImpl.DetailsRepositoryJpaImpl;
import dette.boutique.data.repository.listImpl.DetailsRepositoryListImpl;
import jakarta.persistence.EntityManagerFactory;

public class DetailsRepositoryFactory implements RepositoryFactory<DetailsRepository> {
    protected EntityManagerFactory emf;

    public DetailsRepositoryFactory(EntityManagerFactory emf) {
        this.emf = emf;
    }

    @Override
    public DetailsRepository create(String persistence) {
        switch (persistence) {
            case "MYSQL", "POSTGRES" -> {
                return new DetailsRepositoryJpaImpl(emf.createEntityManager());
            }
            case "JDBC" -> {
                return new DetailsRepositoryDbImpl();
            }
            case "LIST" -> {
                return new DetailsRepositoryListImpl();
            }
            default -> throw new UnsupportedOperationException("Unknown persistence type: " + persistence);
        }
    }
}
