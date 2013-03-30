Fonctionnement du plugin:

    Ce plugin enregistre des statistiques sur les joueurs.
    Les statistiques de chaque joueur sont enregistrés dans un objet (classe PlayerStats). De cette manière c'est cet objet qui est modifié lors des évenements, et non pas les tables en base de donnée pour plus de rapidité.

    Le serveur contient une liste de PlayerStats. A chaque fois qu'un joueur rejoint le serveur, un objet PlayerStats est ajouté à la liste. Lorsqu'un joueur quitte le serveur, son objet PlayerStats est retiré de la liste.
    Le fichier de configuration contient un noeud (TimebetweenSaves), indiquant le temps minimal (en secondes) pour mettre à jour les statistiques en base de données. Une fois ce temps dépassé, le prochain évenement déclenche la mise à jour, et remet le compteur à zéro.

    Le package studmine.mysqlManager contient des classes avec des méthodes gérant les envoie/reception de données pour chaque table:
    - table playerKill
    - table playerStats
    - table Player
## Ce qui pourrait etre bien c'est de rendre ces classes indépendantes d'un plugin particulier (cette classe à besoin d'une connexion StudConnection qui est instancié de manière static au niveau de la classe qui extend JavaPlugin), mais juste de la rendre dépendant de StudConnection car d'autres plugins auront surement besoin de méthodes de ce genre. A voir ##

Etant donné que la table Player est faite pour tester, la classe MysqlPlayerManager est suceptible de changer.

    Todo: Commandes pour récupérer les stats d'un joueur. Les commandes feront appels aux objets et non pas à la base.
