package gameobjects;

import libraries.Vector2;
import resources.ImagePaths;
import resources.StuffInfos;

import java.util.Objects;

public class HearthObject extends Object{


    /**
     * constructeurs
     */
    public HearthObject(Vector2 position){
        super(ImagePaths.HP_UP, position, true, StuffInfos.PRICE_PASSIF_OBJECT);
    }


    /**
     * methods
     */
    @Override
    public void actionHero() {
            addTwoHearth();
            healHero();
    }
}
