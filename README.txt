Installation
============

    - créer un dossier Yaml dans le repertoire plugins
    - y mettre un fichier nommé database.yml contenant le texte suivant :

    MySQL:
    Host: localhost
    Login: database_user_login
    Password: database_user_password
    Database:  database_name
    Port: 3306

    Ce plugin enregistre des statistiques sur les joueurs.

    Les statistiques de chaque joueur sont enregistrés dans un objet (classe PlayerStats). De cette manière c'est cet objet qui est modifié lors des évenements, et non pas les tables en base de donnée pour plus de rapidité.
    Le serveur contient une liste de PlayerStats. A chaque fois qu'un joueur rejoint le serveur, un objet PlayerStats est ajouté à la liste. Lorsqu'un joueur quitte le serveur, son objet PlayerStats est retiré de la liste.
    Le fichier de configuration contient un noeud (TimebetweenSaves), indiquant le temps minimal (en secondes) pour mettre à jour les statistiques en base de données. Les objets sont enregistrés en base de données à chaque période.

    Le package studmine.mysqlManager contient des classes avec des méthodes gérant les envoie/reception de données pour chaque table:
    - table kill
    - table stats
    - table user
    - table note

    Il est possible d'indiquer quels évènements enregistrer dans la config. Il faut (pour l'instant, a voir si utile de changer) recharger le serveur pour désactiver/activer les évenements qui sont passés à true/false dans la config. En revanche pas besoin de recharger le serveur pour cacher les statistiques mis à false dans la config, lors de l'appel à la command /stats.

    Ce plugin gère également le système de prestige. Chaque joueur peut attribuer une note à un autre (note allant de -5 à 5 en fonction de son ancienneté sur le serveur). Le prestige commence à zéro et est mis à jour a chaque ajout de note par un joueur.
