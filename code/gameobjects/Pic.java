package gameobjects;

import libraries.Vector2;
import resources.HeroInfos;
import resources.ImagePaths;

import java.util.Objects;

public class Pic extends Obstacle{

    /**
     * constructeur
     */

    public Pic(Vector2 position){
        setImagePath(ImagePaths.STIGMATA);
        setDeplacement(new Deplacement(position, 0, null));
        setSize(HeroInfos.ISAAC_SIZE);
        setFrappable(false);
        setLife(3);
    }

    /**
     * methods
     */

    /**
     * frappe l'etre qui s'y frotte (pas de spîder, le jeu était trop simple)
     * @param etreVivant
     */
    @Override
    public void actionCollision(EtreVivant etreVivant) {
        if (etreVivant instanceof Hero) etreVivant.prendDegat(HeroInfos.colisionPic);
    }
}
