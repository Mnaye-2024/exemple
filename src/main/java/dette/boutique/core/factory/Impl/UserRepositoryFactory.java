package dette.boutique.core.factory.Impl;

import dette.boutique.core.factory.RepositoryFactory;
import dette.boutique.data.repository.UserRepository;
import dette.boutique.data.repository.bdImpl.UserRepositoryDbImpl;
import dette.boutique.data.repository.jpaImpl.UserRepositoryJpaImpl;
import dette.boutique.data.repository.listImpl.UserRepositoryListImpl;
import jakarta.persistence.EntityManagerFactory;

public class UserRepositoryFactory implements RepositoryFactory<UserRepository> {
    protected EntityManagerFactory emf;

    public UserRepositoryFactory(EntityManagerFactory emf) {
        this.emf = emf;
    }

    @Override
    public UserRepository create(String persistence) {
        switch (persistence) {
            case "MYSQL", "POSTGRES" -> {
                return new UserRepositoryJpaImpl(emf.createEntityManager());
            }
            case "JDBC" -> {
                return new UserRepositoryDbImpl();
            }
            case "LIST" -> {
                return new UserRepositoryListImpl();
            }
            default -> throw new UnsupportedOperationException("Unknown persistence type: " + persistence);
        }
    }

}
