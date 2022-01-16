package gameobjects;

import libraries.Vector2;
import resources.ImagePaths;
import resources.StuffInfos;

import java.util.Objects;

public class BloodOfTheMartyr extends Object{


    /**
     * constructeurs
     */
    public BloodOfTheMartyr(Vector2 position){
        super(ImagePaths.BLOOD_OF_THE_MARTYR, position, true, StuffInfos.PRICE_PASSIF_OBJECT);
    }


    /**
     * methods
     */
    @Override
    public void actionHero() {
            augmenterDegatsHero();
    }
}
