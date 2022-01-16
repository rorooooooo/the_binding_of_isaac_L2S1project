package gameobjects;

import libraries.Vector2;
import resources.HeroInfos;
import resources.ImagePaths;
import resources.MonstreInfos;

import java.util.List;

public class Spider extends Monstre{

    /**
     * attributs
     */

    private int last_move;
    private int attente;

    /**
     * constructeur
     */

    public Spider(Vector2 position){
        super(position, ImagePaths.SPIDER, MonstreInfos.SPIDER_LIFE, MonstreInfos.MONSTER_SIZE, MonstreInfos.SPIDER_SPEED, false);
        this.last_move = 0;
        this.attente = 0;
        setFly(false);
        setProjectiles(null);
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
        if(last_move % MonstreInfos.DELAI_PAUSE_SPIDER == 0){
            double x = Math.random();//random
            double y = Math.random();
            if(last_move % ((MonstreInfos.DELAI_PAUSE_SPIDER)*2) == 0){
                x= -x;
                y = -y;
            }
            getDeplacement().setDirection(new Vector2(x, y));
            attente++;
            if(attente % (MonstreInfos.DELAI_PAUSE_SPIDER/2) == 0) last_move++;
        }
        else if(attente % (MonstreInfos.DELAI_PAUSE_SPIDER/2) == 0){
            Vector2 direction = getDeplacement().getDirection();
            move();
            getDeplacement().setDirection(direction);
            last_move++;
        }
    }


    public void frapper(){

    }

}
