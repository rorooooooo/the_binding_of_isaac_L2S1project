package gameobjects;

import libraries.Vector2;
import resources.HeroInfos;
import resources.ImagePaths;
import resources.MonstreInfos;

import java.util.ArrayList;

public class Fly extends Monstre {

    /**
     * attributs
     */
    public int last_shot;

    /**
     * constructeur
     */

    public Fly(Vector2 position){
        super(position, ImagePaths.FLY, MonstreInfos.FLY_LIFE, MonstreInfos.MONSTER_SIZE, MonstreInfos.FLY_SPEED, true);
        this.last_shot = 1;
        setFly(true);
        this.projectiles = new ArrayList<Projectile>();
        setPowerTear(MonstreInfos.DEGAT_MONSTER);
    }

    /**
     * getters / setters
     */


    /**
     * methods
     */


    @Override
    public void updateGameObject()
    {
        shoot(directionTir());
        move();
        suppProjectiles();

    }
    @Override
    public void move(){
        getDeplacement().move();
    }


}
