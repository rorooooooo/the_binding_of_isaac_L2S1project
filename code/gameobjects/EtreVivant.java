package gameobjects;

import libraries.StdDraw;
import libraries.Vector2;

import java.util.List;
import java.util.Objects;
import java.util.Vector;

public abstract class EtreVivant extends ElementDuJeu {



    private Vector2 size;
    private String imagePath;
    private int life;
    private int lifeMax;
    private Deplacement deplacement;
    private int degatCollision;
    private boolean fly;
    private boolean frappable;
    private double powerTear;

    public List<Projectile> projectiles;






    /**
     * getters setters
     */

    public int getDegatCollision() {
        return degatCollision;
    }

    public void setDegatCollision(int degatCollision) {
        this.degatCollision = degatCollision;
    }

    public Vector2 getSize() {
        return size;
    }

    public void setSize(Vector2 size) {
        this.size = size;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }



    public int getLifeMax() {
        return lifeMax;
    }

    public void setLifeMax(int lifeMax) {
        this.lifeMax = lifeMax;
    }

    public Deplacement getDeplacement() {
        return deplacement;
    }

    public void setDeplacement(Deplacement deplacement) {
        this.deplacement = deplacement;
    }

    public Vector2 getNextPosition(){
        return getPosition().addVector(deplacement.getNormalizedDirection());
    }

    public Vector2 getPosition(){
        return deplacement.getPosition();
    }

    public boolean isFly() {
        return fly;
    }

    public void setFly(boolean fly) {
        this.fly = fly;
    }

    public List<Projectile> getProjectiles() {
        return projectiles;
    }

    public void setProjectiles(List<Projectile> projectiles) {
        this.projectiles = projectiles;
    }



    public double getPowerTear() {
        return powerTear;
    }

    public void setPowerTear(double powerTear) {
        this.powerTear = powerTear;
    }





    /**
     * crée un projectile
     * shoot le projectile
     * l'ajoute à la liste de projectiles
     * @param direction la direction du projectile
     */
    public void shoot(int direction){
        Projectile projectile = new Projectile(getPosition(), getPowerTear(), this);
        projectile.shoot(direction);
        projectiles.add(projectile);//this.larme_a_draw.append(larme);
    }

    /**
     * lis la liste de projectiles
     * si une collision est détectée, le supprimer
     */
    public void suppProjectiles() {
        for(int i = 0; i < projectiles.size(); i++) {
            if(projectiles.get(i).isCollision()) projectiles.remove(i);
        }
    }

    public Vector2 getFuturePosition() {
        return deplacement.getFuturePosition();
    }

    public void drawGameObject()
    {
        StdDraw.picture(getDeplacement().getPosition().getX(), getDeplacement().getPosition().getY(), getImagePath(), getSize().getX(), getSize().getY(), 0);

        drawProjectiles();
    }

    public void drawProjectiles(){
        if(!Objects.isNull(projectiles)){
            for(int i = 0; i < projectiles.size(); i++){
                projectiles.get(i).drawGameObject();
            }
        }
    }

    public boolean isDie() {
        return (getLife() <= 0);
    }

    public abstract void updateGameObject();
    public abstract void frapper(EtreVivant etre);

}