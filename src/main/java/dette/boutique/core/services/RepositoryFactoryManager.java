package dette.boutique.core.services;

import java.util.Map;

import dette.boutique.core.factory.Impl.ArticleRepositoryFactory;
import dette.boutique.core.factory.Impl.ClientRepositoryFactory;
import dette.boutique.core.factory.Impl.DetailsRepositoryFactory;
import dette.boutique.core.factory.Impl.DetteRepositoryFactory;
import dette.boutique.core.factory.Impl.RoleRepositoryFactory;
import dette.boutique.core.factory.Impl.UserRepositoryFactory;
import dette.boutique.data.repository.ArticleRepository;
import dette.boutique.data.repository.ClientRepository;
import dette.boutique.data.repository.DetailsRepository;
import dette.boutique.data.repository.DetteRepository;
import dette.boutique.data.repository.RoleRepository;
import dette.boutique.data.repository.UserRepository;

public class RepositoryFactoryManager {
    private YamlService yamlService;
    private  Object persistenceHandler;

    private ClientRepositoryFactory clientRepositoryFactory;
    private UserRepositoryFactory userRepositoryFactory;
    private ArticleRepositoryFactory articleRepositoryFactory;
    private DetteRepositoryFactory detteRepositoryFactory;
    private RoleRepositoryFactory roleRepositoryFactory;
    private DetailsRepositoryFactory detailsRepositoryFactory;

    public RepositoryFactoryManager(YamlService yamlService,  Object persistenceHandler) {
        this.yamlService = yamlService;
        // this.emf = emf;

        // // Initialisation des factories spécialisées
        // this.clientRepositoryFactory = new ClientRepositoryFactory(emf);
        // this.userRepositoryFactory = new UserRepositoryFactory(emf);
        // this.articleRepositoryFactory = new ArticleRepositoryFactory(emf);
        // this.detteRepositoryFactory = new DetteRepositoryFactory(emf);
        // this.roleRepositoryFactory = new RoleRepositoryFactory(emf);
        // this.detailsRepositoryFactory = new DetailsRepositoryFactory(emf);
    }

    public ClientRepository createClientRepository() {
        String persistenceType = getPersistenceType();
        return clientRepositoryFactory.create(persistenceType);
    }

    public UserRepository createUserRepository() {
        String persistenceType = getPersistenceType();
        return userRepositoryFactory.create(persistenceType);
    }

    public ArticleRepository createArticleRepository() {
        String persistenceType = getPersistenceType();
        return articleRepositoryFactory.create(persistenceType);
    }

    public DetteRepository createDetteRepository() {
        String persistenceType = getPersistenceType();
        return detteRepositoryFactory.create(persistenceType);
    }

    public RoleRepository createRoleRepository() {
        String persistenceType = getPersistenceType();
        return roleRepositoryFactory.create(persistenceType);
    }

    public DetailsRepository createDetailsRepository() {
        String persistenceType = getPersistenceType();
        return detailsRepositoryFactory.create(persistenceType);
    }

    private String getPersistenceType() {
        Map<String, Object> yamlData = yamlService.loadYaml();
        return (String) yamlData.get("persistence");
    }
}
