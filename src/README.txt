Fonctionnement du plugin:

    Ce plugin enregistre des statistiques sur les joueurs.
    Les statistiques de chaque joueur sont enregistrés dans un objet (classe PlayerStats). De cette manière c'est cet objet qui est modifié lors des évenements, et non pas les tables en base de donnée pour plus de rapidité.
    Le serveur contient une liste de PlayerStats. A chaque fois qu'un joueur rejoint le serveur, un objet PlayerStats est ajouté à la liste. Lorsqu'un joueur quitte le serveur, son objet PlayerStats est retiré de la liste.
    Le fichier de configuration contient un noeud (TimebetweenSaves), indiquant le temps minimal (en secondes) pour mettre à jour les statistiques en base de données. Une fois ce temps dépassé, le prochain évenement déclenche la mise à jour, et remet le compteur à zéro.
    Le package studmine.mysqlManager contient des classes avec des méthodes gérant les envoie/reception de données pour chaque table:
    - table playerKill
    - table playerStats
    - table Player
    Il est possible d'indiquer quels évènements enregistrer dans la config. Il faut (pour l'instant, a voir si utile de changer) recharger le serveur pour désactiver/activer les évenements qui sont passés à true/false dans la config. En revanche pas besoin de recharger le serveur pour cacher les statistiques mis à false dans la config, lors de l'appel à la command /stats

# Notes #

    Etant donné que la table Player est faite pour tester, la classe MysqlPlayerManager est suceptible de changer.
    Utile d'avoir accès aux stats des joueurs hors ligne avec la commande?
    Modif modele -> timePlayed = long (java) = biginit (mysql)
    Ce qui pourrait etre bien c'est de rendre les classes du package mysqlManager indépendantes d'un plugin particulier (ces classes ont besoin d'une connexion StudConnection qui est instancié de manière static au niveau de la classe qui extend JavaPlugin), mais juste de les rendre dépendantes de StudConnection car d'autres plugins auront surement besoin de méthodes de ce genre. A voir
    Réfléchir au système de sauvegarde pour rendre le plugin moins gourmand -> associer la sauvegarde à un seul (ou seulement certains)  évenements par exemple (sinon cherche la date à chaque fois)
    