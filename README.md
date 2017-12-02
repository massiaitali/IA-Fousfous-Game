# Projet APP5 Mathilde - Massinissa

## Repartition des tâches Partie 2 

### Mathilde :
* lire/sauvegarder un plateau dans un fichier
* calculer l’ensemble des coups possibles pour un joueur

### Massinissa :
* tester une fin de partie
* tester la validité d’un coup pour un joueur
* jouer un coup sur un plateau


## Repartition des tâches Partie 3

### Mathilde :
* Alpha Beta
* Mini Max

### Massinissa :
* Heuristiques
* Iterative Deepening 

## COMMANDES
### Lancer le serveur
cd IA-FousFous
java -cp serveur/obfousfous.jar fousfous.ServeurJeu 1234 1

### Lancer joueur 1
cd IA-FousFous/bin
java -cp . fousfous_2.ClientJeu fousfous_2.JoueurFinal localhost 1234

### Lancer joueur 2
cd IA-FousFous/bin
java -cp . fousfous_2.ClientJeu fousfous_2.MonSuperJoueur localhost 1234

