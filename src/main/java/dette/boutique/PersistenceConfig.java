// package dette.boutique;

// import java.io.InputStream;
// import java.util.Map;

// import org.yaml.snakeyaml.Yaml;

// public class PersistenceConfig {
//     private Map<String, Object> config;

//     public PersistenceConfig() {
//         Yaml yaml = new Yaml();
//         try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("META-INF/config.yaml")) {
//             if (inputStream == null) {
//                 throw new IllegalArgumentException("Fichier YAML introuvable.");
//             }
//             config = yaml.load(inputStream);
//         } catch (Exception e) {
//             e.printStackTrace();
//         }
//     }

//     public String getActivePersistenceUnit() {
//         Map<String, Object> persistence = (Map<String, Object>) config.get("persistence");

//         Object unit = persistence.get("init");
//         if (unit instanceof Map) {
//             unit = ((Map<?, ?>) unit).get("value");
//         }
//         if (unit instanceof String) {
//             return (String) unit;
//         } else {
//             throw new RuntimeException("L'unité de persistance doit être une chaîne : " + unit);
//         }
//     }

//     public Map<String, Object> getDatabaseProperties() {
//         String activeUnit = getActivePersistenceUnit();
//         System.out.println("Unité de persistance active : " + activeUnit);

//         // Afficher la configuration entière pour débogage
//         System.out.println("Configuration chargée : " + config);
        
//         // Accéder aux propriétés en utilisant la bonne clé
//         @SuppressWarnings("unchecked")
//         Map<String, Object> persistenceMap = (Map<String, Object>) config.get("persistence");
//         Map<String, Object> units = (Map<String, Object>) persistenceMap.get("units");
//         Map<String, Object> properties = (Map<String, Object>) units.get(activeUnit);

//         if (properties == null) {
//             throw new RuntimeException("Aucune propriété trouvée pour l'unité de persistance : " + activeUnit);
//         }

//         return properties;
//     }

//     public String getInitValue() {
//         // Récupérer la valeur d'initialisation
//         Map<String, Object> persistence = (Map<String, Object>) config.get("persistence");
//         return (String) ((Map<String, Object>) persistence.get("init")).get("value");
//     }
// }
