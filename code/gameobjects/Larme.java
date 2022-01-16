package gameobjects;

import libraries.StdDraw;
import libraries.Vector2;
import resources.HeroInfos;
import resources.ImagePaths;

public class Larme extends Projectile {



    /**
     * constructeurs
     */

    public Larme(Vector2 position, EtreVivant appartientA) {
        super(position, HeroInfos.TEAR_POWER, ImagePaths.TEAR, HeroInfos.TEAR_SPEED, appartientA);
    }

    public Larme(Vector2 position, int powerTear, EtreVivant appartientA) {
        super(position, powerTear, ImagePaths.TEAR, HeroInfos.TEAR_SPEED, appartientA);
    }


    /**
     * methods
     */



}
