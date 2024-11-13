package dette.boutique.core.factory.Impl;

import dette.boutique.core.factory.RepositoryFactory;
import dette.boutique.data.repository.DetteRepository;
import dette.boutique.data.repository.UserRepository;
import dette.boutique.data.repository.bdImpl.DetteRepositoryDbImpl;
import dette.boutique.data.repository.jpaImpl.DetteRepositoryJpaImpl;
import dette.boutique.data.repository.listImpl.DetteRepositoryListImpl;
import jakarta.persistence.EntityManagerFactory;

public class DetteRepositoryFactory implements RepositoryFactory<DetteRepository> {
    protected EntityManagerFactory emf;
    protected UserRepository userRepository;

    public DetteRepositoryFactory(EntityManagerFactory emf, UserRepository userRepository) {
        this.emf = emf;
        this.userRepository = userRepository;
    }

    public DetteRepositoryFactory(EntityManagerFactory emf) {
        this.emf = emf;
    }

    @Override
    public DetteRepository create(String persistence) {
        switch (persistence) {
            case "MYSQL", "POSTGRES" -> {
                return new DetteRepositoryJpaImpl(emf.createEntityManager());
            }
            case "JDBC" -> {
                return new DetteRepositoryDbImpl();
            }
            case "LIST" -> {
                return new DetteRepositoryListImpl();
            }
            default -> throw new UnsupportedOperationException("Unknown persistence type: " + persistence);
        }
    }
}
