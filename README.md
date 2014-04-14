[ ![Codeship Status for arpinum/alexandria-api](https://www.codeship.io/projects/cd050090-a5fc-0131-9df9-4e0ca91b4314/status?branch=master)](https://www.codeship.io/projects/18793)

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
 
 


