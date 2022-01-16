package gameobjects;

import libraries.Vector2;
import resources.HeroInfos;
import resources.ImagePaths;
import resources.MonstreInfos;

import java.util.ArrayList;

public class Boss extends Monstre{

    /**
     * attributs
     */
    public int last_shot;
    private int last_move;
    private int attente;

    /**
     * constructeur
     */

    public Boss(Vector2 position){
        super(position, ImagePaths.CRICKETS_HEAD, MonstreInfos.BOSS_LIFE, MonstreInfos.MONSTER_SIZE, MonstreInfos.SPIDER_SPEED, true);
        this.last_shot = 1;
        setFly(true);
        this.projectiles = new ArrayList<Projectile>();
        this.last_move = 0;
        this.attente = 0;
        setPowerTear(MonstreInfos.DEGAT_BOSS);
        setDelai_tir(MonstreInfos.CADENCE_TIR_BOSS);

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
        suppProjectiles();

        if(last_move % MonstreInfos.DELAI_PAUSE_SPIDER == 0){
            double x = Math.random();//random
            double y = Math.random();
            if(last_move % ((MonstreInfos.DELAI_PAUSE_SPIDER)*2) == 0){
                x= -x;
                y = -y;
            }
            shoot(directionTir());
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


    @Override
    public void move(){
        getDeplacement().move();
    }






}
