package gameWorld;

import gameobjects.*;
import gameobjects.Object;
import libraries.Vector2;

import java.util.ArrayList;
import java.util.List;

public class RoomShop extends Room{
    /**
     * attributs
     */



    /**constructeur
     *
     */

    public RoomShop(List<Door> portes, String chemin_map){
        super(portes, chemin_map);
        genererObjetsAleatoires(3);

    }
    /**
     * methods
     */

    @Override
    public Object randomObject(Vector2 position){
        double nb = Math.random() * 100;
        if (nb < 50) return new CoeurVariable(position);
        else if (nb < 75) return new HearthObject(position);
        else return new BloodOfTheMartyr(position);
    }

    public void drawPriceObjects(){
    }



}
