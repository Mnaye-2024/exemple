package dette.boutique;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import dette.boutique.Views.ArticleView;
import dette.boutique.Views.ClientView;
import dette.boutique.Views.RoleView;
import dette.boutique.Views.UserView;
import dette.boutique.core.services.EntityManagerCreator;
import dette.boutique.core.services.RepositoryFactory;
import dette.boutique.core.services.YamlService;
import dette.boutique.core.services.impl.YamlServiceImpl;
import dette.boutique.data.entities.Article;
import dette.boutique.data.entities.Client;
import dette.boutique.data.entities.Dette;
import dette.boutique.data.entities.Role;
import dette.boutique.data.entities.User;
import dette.boutique.data.repository.ArticleRepository;
import dette.boutique.data.repository.ClientRepository;
import dette.boutique.data.repository.DetteRepository;
import dette.boutique.data.repository.RoleRepository;
import dette.boutique.data.repository.UserRepository;
import dette.boutique.services.ArticleService;
import dette.boutique.services.ClientService;
import dette.boutique.services.DetteService;
import dette.boutique.services.RoleService;
import dette.boutique.services.UserService;

public class Main {
    private static Scanner scanner = new Scanner(System.in);

    public static Scanner getScanner() {
        return scanner;
    }

    public static void main(String[] args) {
        YamlService yamlService = new YamlServiceImpl();

        // Création de l'EntityManager ou de la liste selon la persistance
        EntityManagerCreator entityManagerCreator = new EntityManagerCreator(yamlService);
        Object persistenceHandler = entityManagerCreator.createEntityManagerFactory();
        String persistence = entityManagerCreator.getPersistenceName();

        // Repositories yi
        RepositoryFactory<User> userRepositoryFactory = new RepositoryFactory<>(persistenceHandler);
        UserRepository userRepository = (UserRepository) userRepositoryFactory.create(persistence, User.class);

        RepositoryFactory<Role> roleRepositoryFactory = new RepositoryFactory<>(persistenceHandler);
        RoleRepository roleRepository = (RoleRepository) roleRepositoryFactory.create(persistence, Role.class);

        RepositoryFactory<Client> clientRepositoryFactory = new RepositoryFactory<>(persistenceHandler);
        ClientRepository clientRepository = (ClientRepository) clientRepositoryFactory.create(persistence,
                Client.class);

        RepositoryFactory<Article> articleRepositoryFactory = new RepositoryFactory<>(persistenceHandler);
        ArticleRepository articleRepository = (ArticleRepository) articleRepositoryFactory.create(persistence,
                Article.class);

        RepositoryFactory<Dette> detteRepositoryFactory = new RepositoryFactory<>(persistenceHandler);
        DetteRepository detteRepository = (DetteRepository) detteRepositoryFactory.create(persistence, Dette.class);

        // Servies yi
        ArticleService articleService = new ArticleService(articleRepository);
        UserService userService = new UserService(userRepository);
        ClientService clientService = new ClientService(clientRepository);
        RoleService roleService = new RoleService(roleRepository);
        DetteService detteService = new DetteService(detteRepository, articleService, clientService);

        // Views yi
        ArticleView articleView = new ArticleView(articleService);
        ClientView clientView = new ClientView(clientService);
        UserView userView = new UserView(userService);
        RoleView roleView = new RoleView(roleService);

        Role role = null;
        boolean continuer = true;

        while (continuer) {
            System.out.println("Menu Principal :");
            System.out.println("1. Menu client");
            System.out.println("2. Menu utilisateur");
            System.out.println("3. Quitter");
            System.out.print("Choisissez une option : ");
            int choix = userView.obtenirChoixUtilisateur(1, 3);

            try {
                switch (choix) {
                    case 1 -> {
                        System.out.println("------Menu Client------");
                        clientView.menuClient();
                    }

                    case 2 -> {
                        boolean continuerMenuUser = true;
                        while (continuerMenuUser) {
                            System.out.println("------Menu utilisateur------");
                            userView.menu();
                            int choixUser = userView.obtenirChoixUtilisateur(1, 5);
                            switch (choixUser) {
                                case 1 -> {
                                    String login1 = userView.saisieLogin();
                                    String password1 = userView.saisiePassword();
                                    Role role1 = roleView.choisirRole();
                                    User user1 = new User(login1, password1, role1);
                                    userService.create(user1);
                                    System.out.println("Utilisateur créé avec succès!");
                                }
                                case 2 -> {
                                    Client clientAffilie = clientView.choisirCLient();
                                    if (clientAffilie != null) {
                                        String nom = clientAffilie.getNom();
                                        String prenom = clientAffilie.getPrenom();
                                        String login = userView.saisieLogin();
                                        String password = userView.saisiePassword();
                                        Role roleUser = new Role();
                                        roleUser.setNom("CLIENT");

                                        User user = new User(login, password, clientAffilie, roleUser);
                                        userService.create(user);
                                        clientAffilie.setUser(user);
                                        System.out.println("Utilisateur créé avec succès!");
                                    } else {
                                        System.out.println("Aucun client n'est disponible.");
                                    }
                                }

                                case 3 -> {
                                    User userA = userView.choisirUser();
                                    if (userA != null) {
                                        Client clientA = clientView.choisirCLient();
                                        if (clientA != null) {
                                            clientA.setUser(userA);
                                            userA.setClient(clientA);
                                            userService.updateClientForUser(userA);
                                            clientService.updateUserForClient(clientA);
                                            System.out.println("Association effectuée avec succès!");
                                        } else {
                                            System.out.println("Aucun client sélectionné.");
                                        }
                                    }
                                }

                                case 4 -> {
                                    List<User> users = userService.list();
                                    userService.afficherListeUsers(users);
                                }

                                case 5 -> {
                                    continuerMenuUser = false;
                                    System.out.println("Sortie du menu Utilisateur.");
                                }

                                default -> System.out.println("Option invalide.");
                            }
                        }
                    }

                    case 3 -> {
                        continuer = false;
                        System.out.println("Au revoir.");
                    }

                    default -> System.out.println("Option invalide. Veuillez choisir une option valide.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Erreur : entrée invalide. Veuillez entrer un nombre entier.");
                scanner.next();
            }
        }
    }

}

// case 3:
// System.out.println("------Créer un compte utilisateur avec un rôle Boutiquier
// ou Admin.------");
// Client client = clientView.choisirCLient();
// if (client == null) {
// break;
// }
// String nomUser = client.getNom();
// String prenomUser = client.getPrenom();

// Role roleUserBi = Role.choisirRoleExceptClient();
// String loginUser = userService.saisieLogin();
// String passwordUser = userService.saisiePassword();

// User userBi = new User(nomUser, prenomUser, loginUser, passwordUser, client,
// roleUserBi);
// userService.create(userBi);
// client.setUser(userBi);
// break;
// case 4:
// System.out.println("------Désactiver/Activer un compte utilisateur.------");
// boolean continu = true;

// while (continu) {
// System.out.println("------Gestion des utilisateurs :
// Activer/Désactiver------");
// System.out.println("1. Activer un utilisateur");
// System.out.println("2. Désactiver un utilisateur");
// System.out.println("0. Retour au menu principal");

// try {
// int choixActiverDesactiver = scanner.nextInt();
// scanner.nextLine();

// switch (choixActiverDesactiver) {
// case 1:
// System.out.println("------Activer un compte utilisateur.------");
// User userActif = userService.choixUserActiver();
// if (userActif != null) {
// userService.activerUtilisateur(userActif);
// System.out.println("Utilisateur activé.");
// }
// break;

// case 2:
// System.out.println("------Désactiver un compte utilisateur.------");
// User userDesactive = userService.choixUserDesactiver();
// if (userDesactive != null) {
// userService.desactiverUtilisateur(userDesactive);
// System.out.println("Utilisateur désactivé.");
// }
// break;

// case 0:
// continu = false;
// break;
// default:
// System.out.println("Veuillez choisir une option valide!");
// break;
// }
// } catch (InputMismatchException e) {
// System.out.println("Erreur : entrée invalide. Veuillez entrer un nombre
// entier.");
// scanner.next();
// }
// }

// break;
// case 5:
// System.out.println("------Afficher les comptes utilisateurs actifs ou par
// rôle.------");
// boolean keep = true;

// while (keep) {
// System.out.println("------Gestion des utilisateurs :
// Activer/Désactiver------");
// System.out.println("1. Afficher tous les comptes utilisateurs actifs");
// System.out.println("2. Afficher les comptes utilisateurs par role");
// System.out.println("0. Retour au menu principal");

// try {
// int choixActiverDesactiver = scanner.nextInt();
// scanner.nextLine();

// switch (choixActiverDesactiver) {
// case 1:
// System.out
// .println("------Afficher tous les comptes utilisateurs actifs.------");
// List<User> userActif = userService.listUserActive();
// if (userActif != null) {
// for (User userSelect : userActif) {
// System.out.println(userSelect);
// }
// }
// break;

// case 2:
// System.out.println("------Afficher les comptes utilisateurs par
// role.------");
// Role roleBi = Role.choisirRole();
// List<User> usersRole = userService.listUserParRole(roleBi);
// for (User userRole : usersRole) {
// System.out.println(userRole);
// }
// case 0:
// keep = false;
// break;
// default:
// System.out.println("Veuillez choisir une option valide!");
// break;
// }
// } catch (InputMismatchException e) {
// System.out.println("Erreur : entrée invalide. Veuillez entrer un nombre
// entier.");
// scanner.next();
// }
// }
// break;
// case 6:
// System.out.println(
// "------Créer/lister des articles et filtrer par disponibilité (qteStock !=
// 0).------");
// boolean beugeu = true;

// while (beugeu) {
// System.out.println("1. Créer un article");
// System.out.println("2. Lister les articles");
// System.out.println("3. Lister les articles disponibles");
// System.out.println("0. Retour au menu principal");

// try {
// int choixActiverDesactiver = scanner.nextInt();
// scanner.nextLine();

// switch (choixActiverDesactiver) {
// case 1:
// System.out.println("------Création d'article------");
// articleView.create();
// break;
// case 2:
// List<Article> tousArticles = articleService.list();
// if (tousArticles != null) {
// for (Article articleBi : tousArticles) {
// System.out.println(articleBi);
// }
// } else {
// System.out.println("Aucun article!");
// return;
// }
// case 3:
// List<Article> articlesDispo = articleService.listeArticlesDispo();
// if (articlesDispo != null) {
// for (Article articleYi : articlesDispo) {
// System.out.println(articleYi);
// }
// } else {
// System.out.println("Aucun article disponible!");
// return;
// }

// case 0:
// beugeu = false;
// break;
// default:
// System.out.println("Veuillez choisir une option valide!");
// break;
// }
// } catch (InputMismatchException e) {
// System.out.println("Erreur : entrée invalide. Veuillez entrer un nombre
// entier.");
// scanner.next();
// }
// }
// break;
// case 7:
// System.out.println("------Mettre à jour la qté en stock d’un
// article.------");
// List<Article> tousArticles = articleService.list();
// for (Article articleBi : tousArticles) {
// System.out.println(articleBi);
// }
// String articleChoix = articleService.saisieLibelleModif();
// Article article = articleService.findArticle(articleChoix);
// int stockBi = articleView.saisieQteStock();
// article.setQteStock(stockBi);
// System.out.println("Quantité en stock modifiée avec succès!");
// break;
// case 8:
// System.out.println("------Créer une dette.------");
// Client client2 = clientView.choisirCLient();
// detteService.tekDette(client2);
// break;
// case 9:
// // Archiver les dettes soldées
// System.out.println("------Archiver les dettes soldées.------");
// // Ajoutez ici le code pour archiver les dettes soldées
// break;
