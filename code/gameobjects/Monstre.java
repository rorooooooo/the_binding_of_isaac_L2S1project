package gameobjects;

import libraries.StdDraw;
import libraries.Vector2;
import resources.HeroInfos;
import resources.ImagePaths;
import resources.MonstreInfos;
import libraries.Physics;

import java.util.List;

public abstract class Monstre extends EtreVivant {

    /**
     * attributs
     */

    private boolean fly;

    private int last_shot;
    private int delai_tir;


    /**
     * constructeurs TODO SUPPRIMER LE CONSTRUCTEUR
     */

    public Monstre(Vector2 position, String imagePath, int life, Vector2 size, double speed, boolean fly){
        setImagePath(imagePath);
        setDeplacement(new Deplacement(position, speed, this));
        setSize(size);
        setLife(life);
        setLifeMax(life);
        setFly(fly);
        setDegatCollision(MonstreInfos.DEGAT_MONSTER);
        setFrappable(true);
        setDelai_tir(MonstreInfos.CADENCE_TIR_FLY);
        setFrappable(true);
    }



    /**
     * getters / setters
     */

    public int getDelai_tir() {
        return delai_tir;
    }

    public void setDelai_tir(int delai_tir) {
        this.delai_tir = delai_tir;
    }

    public Vector2 getPosition(){
        return getDeplacement().getPosition();
    }

    public boolean isFly() {
        return fly;
    }

    public void setFly(boolean fly) {
        this.fly = fly;
    }

    public Vector2 getDirection(){ return getDeplacement().getDirection();}

    /**
     * methods
     */

    /**
     *
     * tir dans la direction de déplacement de Fly
     */
    @Override
    public void shoot(int direction){
        if(last_shot % delai_tir == 0)
        {
            Projectile projec = new Projectile(getDeplacement().getPosition(), getPowerTear(), ImagePaths.TEAR, HeroInfos.TEAR_SPEED, this);
            projec.shoot(direction);
            projectiles.add(projec);
            last_shot ++;
        }
        else last_shot++;
    }

    /**
     * tir dans la direction de deplacement de Fly
     * @return la direction(0 haut, 1 bas, 2 droite sinon gauche)
     */
    public int directionTir() {
        double x = getDirection().getX();
        double y = getDirection().getY();
        if(y > 0) return 0;
        else if(x > 0) return 2;
        else if (x < 0) return 3;
        else return 1;
    }

    public abstract void updateGameObject();


    public void move(){
        getDeplacement().move();
    }


    public void drawGameObject()
    {
        StdDraw.picture(getDeplacement().getPosition().getX(), getDeplacement().getPosition().getY(), getImagePath(), getSize().getX(), getSize().getY(), 0);
        drawProjectiles();
    }


    public void prendDegat(int degats) {
        setLife(getLife() - degats);
    }

    /**
     * ne peut frapper un autre monstre
     * @param etre l'etre à frapper
     */
    @Override
    public void frapper(EtreVivant etre){
        if(!(etre instanceof Monstre) & !etre.equals(this)) etre.prendDegat(getDegatCollision());
    }

}
