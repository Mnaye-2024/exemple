package dette.boutique.core.factory.Impl;

import dette.boutique.core.factory.RepositoryFactory;
import dette.boutique.data.repository.RoleRepository;
import dette.boutique.data.repository.bdImpl.RoleRepositoryDbImpl;
import dette.boutique.data.repository.jpaImpl.RoleRepositoryJpaImpl;
import dette.boutique.data.repository.listImpl.RoleRepositoryListImpl;
import jakarta.persistence.EntityManagerFactory;

public class RoleRepositoryFactory implements RepositoryFactory<RoleRepository> {
    protected EntityManagerFactory emf;

    public RoleRepositoryFactory(EntityManagerFactory emf) {
        this.emf = emf;
    }

    @Override
    public RoleRepository create(String persistence) {
        switch (persistence) {
            case "MYSQL", "POSTGRES" -> {
                return new RoleRepositoryJpaImpl(emf.createEntityManager());
            }
            case "JDBC" -> {
                return new RoleRepositoryDbImpl();
            }
            case "LIST" -> {
                return new RoleRepositoryListImpl();
            }
            default -> throw new UnsupportedOperationException("Unknown persistence type: " + persistence);
        }
    }

}
