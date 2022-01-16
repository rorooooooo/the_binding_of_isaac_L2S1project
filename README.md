# Isaac_L2projet
 The Binding of Isaac, réalisé pour le projet de fin de semestre en PO 

https://github.com/rorooooooo/the_binding_of_isaac_L2S1project


Génération du monde
j'ai d'abord utilisé un graphe défini de ma création, que je "remplissais" avec des salles, en créeant les liens entre porte.
Bien qu'elle était totalement fonctionelle, j'ai choisi un autre système : 
Je pars d'un nombre défini de salles. Je commence par créer une salle avec une porte (position aléatoire) vers la salle suivante; Je crée la seconde salle, je regarde les portes de la salle précédente, crée le lien vers celle-ci (position opposé de la première), puis ajoute une nouvelle porte situé au hasard sur les 3 places restantes, vers la salle suivante, et recommande jusqu'à ne plus avoir de salles à créer. 

Génération des salles
Le dossiers maps contient toutes les cartes, il est aisé d'en créer une : le caractère vide est 0 (choisi arbitrairement)
S : araignée
F : mouche
P : pic
R : rocher
Le fichier est choisi aléatoirement pour cahque salle, dans le dossier de maps. 
Pour chaque salle, je lis le fichier une première fois, pour créer les obstacles.
Si la salle contient des monstres, je le relis pour les ajouter. 
Les objets sont quand à eux ajouter aléatoirement : 0 pour la salle de départ, 1 pour chaque salle de monstres, 3 pour le shop.
Je me suis servi de salle_piege pour "calibrer" le placement de chaque objet : j'ai rempli le pourtour de la salle de rochers, et ajuster les valeurs pour que tout rentre dans la map
bien centré.
Pour la salle de Boss, je me sers d'une salle de monstre, contenant un Boss
Le jeu est prévu pour être jouer avec entre 7 et 9 salles (la salle de départ, 2 de monstres, le shop, encore 2 de monstres, et la salle du boss)
La salle de Boss est implémenté comme salle finale
La salle du magasin est placé 3 salles avant la fin, afin d'équilibrer un peu la partie

Les etres vivants.
L'utilisation d'une classe mère pour tous les êtres vivants m'a permit de simplifier la gestion de mes collisions.
La classe Deplacmeent m'a permit de gérer simplement la manière de se mouvoir de chaque être, c'est un de mes premiers choix, et n'a pas évolué car fonctionnant pour mon code.
L'ajout d'un délai entre chaque prise de dégâts pour le hero simplifie le jeu 

Les projectiles
Chaque projectile est un objet à part entière, qui ne change pas de direction après création

Les collisions
Je vérifie les collisions de chaque élement dans les classes de salles, afin de faire bouger ou non les différents personnages
Pour cela, des fonctions dans Physics renvoient true ou false, en fonction des positions des deux éléments à vérifier
Les actions lors de collisions (une larme qui touche un monstre doit lui infliger des dégâts par exemple) sont gérés dans la classe Physics
La propriété fly de monstre autorise à ne pas avoir de collision avec un obstacle
Les monstres ne s'infligent pas de dégats entre eux lors des collisions

Les obstacles
les rochers possèdent 3 points de vie, et se détruisent au fur et à mesure de la partie
J'ai pour cette occasion créer une classe ElementDuJeu, afin d'ajouter la vie aux obstacles, et uniquement rendre les rochers cassable (via le boolean frappable)
son implémentation tardive m'a empêché d'exploiter davantage cette conception (pour simplifier les collisions par exemple )

Autres
Isaac apparaît à chaque fois au centre de la pièce, afin de ne pas se retéléporter ensuite à cause d'une collision avec une porte.


