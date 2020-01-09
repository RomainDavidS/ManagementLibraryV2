# ManagementLibrary
P10 - OCR Amélioration de la Gestion d'un bibliothèque d'une grande ville 
---
Description :

    Client UI : module library
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




