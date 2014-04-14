[ ![Codeship Status for arpinum/arpinum-seed-java](https://www.codeship.io/projects/8528db20-997c-0131-fb57-5670ddce16b6/status?branch=master)](https://www.codeship.io/projects/17332)

# Arpinum Seed - API

## Le but 

Ce projet est la base de travail pour la partie serveur des applications Arpinum. 
Les postulats sont les suivants : 

* Nous voulons coder le métier en ignorance de toute notion technique
* L'ignorance de la persistance est obtenue via le pattern Repository
* La communication avec le métier se fait via des cas d'utilisation capturés dans des commandes
* Les commandes sont passées via un bus
* La lecture et l'écriture sont séparées dans deux modèles distincts
* La persistance est assurée par MongoDB, et MongoLink
* Le serveur ne doit exposer qu'une API Rest, la présentation est déléguée aux applications

## Licence

La licence reste pour le moment à définir, faites ce que vous voulez. 
 
 


