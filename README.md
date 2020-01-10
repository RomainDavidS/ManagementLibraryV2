# ManagementLibrary
P10 - OCR Amélioration de la Gestion d'un bibliothèque d'une grande ville 
---
Description :

    Client UI : module library
        user1 = romaindavid.sergeant@gmail.com - mdp = a 
        user2 = romaind.sergeant@gmail.com - mdp = a 
        user3 = romaind.ocrlibrary@gmail.com - mdp = a 
    3 Microservices : modules mbooks, mfile et musers
    Edge service : module config-server , eureka-server, springadmin
    Gateway : module zuul-server
    - authentification basic : user = utilisateur - password = mdp
    Properties : https://github.com/RomainDavidS/ManagementLibraryV2.git
    Base de donées : postgresql pour tous les microservices
    - mfile : user = adm_file - password = root
    - mbooks : user = adm_books - password = root
    - musers : user = adm_user - password = root   
    
---

### Etapes de déploiement

    Attention bien suivre l'ordre de déploiement

#### Etape 1 - Les edges microservice
    1 - déployer config-server
    2 - déployer eureka-server  
---
#### Etape 2 - API Gateway
    1 - déployer zuul-server
---
#### Etape 3 - Les microservices
    1 - déployer mbooks
    2 - déployer mfile :
        Après le déploiement de mfile il faudra depuis pgadmin réaliser un restore (option : only-data)
        de data-dump.sql présent dans le répertoire resources du microservice
    3 - déployer musers 
    4 - déployer library

### Etape finale
    Lancer zipkin-server
    
### Les fichiers
#### Les bases de données
    - mbooks : /src/main/resources/database.sql et  /src/main/resources/structure.sql
    - mfile : /src/main/resources/database.sql et  /src/main/resources/structure.sql
    - musers : /src/main/resources/database.sql et  /src/main/resources/structure.sql
### Postman
    - mbooks : src/test/resources/Mbooks.postman_collection.json
    - mfile : src/test/resources/Mfile.postman_collection.json
    - musers : src/test/resources/Musers.postman_collection.json
#### Les tests
    Pour effectuer les différents tests il est nécessaire de faire le déploiement avec le profile test
    => -Dspring.profiles.active=test
    - dans chaque api lancer la commande mvn verify pour lancer les différents tests
    - dans Postman importer les collections mis à disposition

#### Les tickets présents dans github
    - ticket#1 : ajout d'un système de réservation
    - ticket#2 : ajout d'une nouvelle règle de gestion indiquant quel'emprunt n'est pas renouvelable après la date de fin
    - ticket#3 : ajouts des tests d'intégrations
 
     




