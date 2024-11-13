package dette.boutique.core.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dette.boutique.core.repository.Repository;
import dette.boutique.core.repository.impl.RepositoryJpaImpl;
import dette.boutique.data.entities.Article;
import dette.boutique.data.entities.Client;
import dette.boutique.data.entities.Details;
import dette.boutique.data.entities.Dette;
import dette.boutique.data.entities.Role;
import dette.boutique.data.entities.User;
import dette.boutique.data.repository.jpaImpl.ArticleRepositoryJpaImpl;
import dette.boutique.data.repository.jpaImpl.ClientRepositoryJpaImpl;
import dette.boutique.data.repository.jpaImpl.DetailsRepositoryJpaImpl;
import dette.boutique.data.repository.jpaImpl.DetteRepositoryJpaImpl;
import dette.boutique.data.repository.jpaImpl.RoleRepositoryJpaImpl;
import dette.boutique.data.repository.jpaImpl.UserRepositoryJpaImpl;
import dette.boutique.data.repository.listImpl.ArticleRepositoryListImpl;
import dette.boutique.data.repository.listImpl.ClientRepositoryListImpl;
import dette.boutique.data.repository.listImpl.DetailsRepositoryListImpl;
import dette.boutique.data.repository.listImpl.DetteRepositoryListImpl;
import dette.boutique.data.repository.listImpl.RoleRepositoryListImpl;
import dette.boutique.data.repository.listImpl.UserRepositoryListImpl;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

public class RepositoryFactory<T> {
    private Object persistenceHandler;

    public RepositoryFactory(Object persistenceHandler) {
        this.persistenceHandler = persistenceHandler;
    }

    // @SuppressWarnings("unchecked")
    // public <T> Repository<T> create(String persistenceType, Class<T> entityClass)
    // {
    // if ("LIST".equalsIgnoreCase(persistenceType)) {
    // // Utilisation des listes en mémoire
    // if (persistenceHandler instanceof EntityManagerCreator creator) {
    // // Gestion des différents types d'entités avec des listes typées
    // if (entityClass == User.class) {
    // List<User> userList = creator.getUserList();
    // return (T) new UserRepositoryListImpl(userList);
    // } else if (entityClass == Dette.class) {
    // List<Dette> detteList = creator.getDetteList();
    // return (T) new DetteRepositoryListImpl(detteList);
    // } else if (entityClass == Client.class) {
    // List<Client> clientList = creator.getClientList();
    // return (T) new ClientRepositoryListImpl(clientList);
    // } else if (entityClass == Details.class) {
    // List<Details> detailList = creator.getDetailList();
    // return (T) new DetailsRepositoryListImpl(detailList);
    // } else if (entityClass == Article.class) {
    // List<Article> articleList = creator.getArticleList();
    // return (T) new ArticleRepositoryListImpl(articleList);
    // }
    // }
    // } else {
    // EntityManagerFactory emf = (EntityManagerFactory) persistenceHandler;
    // EntityManager em = emf.createEntityManager();
    // return new RepositoryJpaImpl<>(entityClass, em);
    // }

    // throw new IllegalArgumentException("Aucun dépôt trouvé pour la classe : " +
    // entityClass.getName());
    // }
    ;

    @SuppressWarnings("unchecked")
    public <T> Repository<T> create(String persistenceType, Class<T> entityClass) {
        if ("LIST".equalsIgnoreCase(persistenceType)) {
            if (persistenceHandler instanceof EntityManagerCreator creator) {
                Map<Class<?>, List<?>> entityLists = new HashMap<>();
                entityLists.put(User.class, creator.getUserList());
                entityLists.put(Dette.class, creator.getDetteList());
                entityLists.put(Client.class, creator.getClientList());
                entityLists.put(Details.class, creator.getDetailList());
                entityLists.put(Article.class, creator.getArticleList());
                entityLists.put(Role.class, creator.getRoleList());

                // Cast explicite de List<?> à List<T>
                List<T> entityList = (List<T>) entityLists.get(entityClass);
                if (entityList != null) {
                    return createRepository(entityClass, entityList);
                }
            }
        } else {
            EntityManagerFactory emf = (EntityManagerFactory) persistenceHandler;
            EntityManager em = emf.createEntityManager();
            if (entityClass == User.class) {
                return (Repository<T>) new UserRepositoryJpaImpl(em);
            } else if(entityClass == Dette.class){
                return (Repository<T>) new DetteRepositoryJpaImpl(em);
            } else if(entityClass == Client.class){
                return (Repository<T>) new ClientRepositoryJpaImpl(em);
            } else if(entityClass == Details.class){
                return (Repository<T>) new DetailsRepositoryJpaImpl(em);
            } else if(entityClass == Article.class){
                return (Repository<T>) new ArticleRepositoryJpaImpl(em);
            } else if(entityClass == Role.class){
                return (Repository<T>) new RoleRepositoryJpaImpl(em);
            }
            // return new RepositoryJpaImpl<T>(entityClass, em);
            return (Repository<T>) new RepositoryJpaImpl<>(entityClass, em);
        }

        throw new IllegalArgumentException("Aucun dépôt trouvé pour la classe : " + entityClass.getName());
    }

    @SuppressWarnings("unchecked")
    private <T> Repository<T> createRepository(Class<T> entityClass, List<T> entityList) {
        if (entityClass == User.class) {
            return (Repository<T>) new UserRepositoryListImpl((List<User>) entityList);
        } else if (entityClass == Dette.class) {
            return (Repository<T>) new DetteRepositoryListImpl((List<Dette>) entityList);
        } else if (entityClass == Client.class) {
            return (Repository<T>) new ClientRepositoryListImpl((List<Client>) entityList);
        } else if (entityClass == Details.class) {
            return (Repository<T>) new DetailsRepositoryListImpl((List<Details>) entityList);
        } else if (entityClass == Article.class) {
            return (Repository<T>) new ArticleRepositoryListImpl((List<Article>) entityList);
        } else if (entityClass == Role.class) {
            return (Repository<T>) new RoleRepositoryListImpl((List<Role>) entityList);
        }

        throw new IllegalArgumentException("Aucun dépôt trouvé pour la classe : " + entityClass.getName());
    }

}
