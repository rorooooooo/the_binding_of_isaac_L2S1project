package gameobjects;

import libraries.Vector2;
import resources.HeroInfos;
import resources.ImagePaths;

public class Rock extends Obstacle{

    /**
     * constructeur
     */

    public Rock(Vector2 position){
        setImagePath(ImagePaths.ROCK);
        setDeplacement(new Deplacement(position, 0, null));
        setSize(HeroInfos.ISAAC_SIZE);
        setFrappable(true);
        setLife(3);
    }

    /**
     * methods
     */

    /**
     * ne fait rien de particulier
     * @param etreVivant
     */
    @Override
    public void actionCollision(EtreVivant etreVivant) {

    }
}
