package gameobjects;

import libraries.Physics;
import libraries.StdDraw;
import libraries.Vector2;
import resources.HeroInfos;
import resources.ImagePaths;
import resources.MonstreInfos;

import java.util.List;

public class Projectile {

    /**
     * attributs
     */
    private double degats;
    private Deplacement deplacement;
    private String imagePath;
    private Vector2 size;
    private boolean collision;
    private EtreVivant appartientA;

    /**
     * constructeurs
     */

    public Projectile(Vector2 position, double degats, String imagePath, double speed, EtreVivant appartientA) {
        this.degats = degats;
        this.deplacement = new Deplacement(position, speed, null);
        this.size = HeroInfos.TEAR_SIZE;
        this.imagePath = imagePath;
        this.collision = false;
        this.appartientA = appartientA;
    }
    public Projectile(Vector2 position, double degats, EtreVivant appartientA) {
        this.degats = degats;
        this.deplacement = new Deplacement(position, HeroInfos.TEAR_SPEED, null);
        this.size = HeroInfos.TEAR_SIZE;
        this.imagePath = ImagePaths.TEAR;
        this.collision = false;
        this.appartientA = appartientA;
    }

    /**
     * getters / setters
     */
    public double getDegats() {
        return degats;
    }

    public void setDegats(int degats) {
        this.degats = degats;
    }

    public Deplacement getDeplacement() {
        return deplacement;
    }

    public void setDeplacement(Deplacement deplacement) {
        this.deplacement = deplacement;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public Vector2 getSize() {
        return size;
    }

    public void setSize(Vector2 size) {
        this.size = size;
    }

    public boolean isCollision() {
        return collision;
    }

    public void setCollision(boolean collision) {
        this.collision = collision;
    }

    public EtreVivant getAppartientA() {
        return appartientA;
    }

    public void setAppartientA(EtreVivant appartientA) {
        this.appartientA = appartientA;
    }

    public Vector2 getPosition(){
        return getDeplacement().getPosition();
    }

    /**
     * methods
     */

    public void drawGameObject() //BOUGE PAS
    {
        StdDraw.picture(deplacement.getPosition().getX(), deplacement.getPosition().getY(), getImagePath(), getSize().getX(), getSize().getY(), 0);
    }

    public boolean isOutMap() {
        return deplacement.isOutMap();
    }

    /**
     * set la direction du projectile
     * @param direction la direction du projectile
     */
    public void shoot(int direction) {
        deplacement.goNext(direction);
    }

    public void updateGameObject() {
        getDeplacement().moveProjectile();
    }

    /**
     *
     * @param obstacles la liste d'obstacles de la salle
     * @return true si collision avec l'un d'eux
     */
    public boolean checkColision(List<Obstacle> obstacles){
        for(int i = 0; i < obstacles.size(); i++){
            Vector2 sizeObstacle = obstacles.get(i).getSize();
            Vector2 positionObstacle = obstacles.get(i).getDeplacement().getPosition();
            if(Physics.rectangleCollision(positionObstacle, sizeObstacle, getDeplacement().getPosition(), getSize() )){
                return true;
            }
        }
        return false;
    }

    /**
     *
     * @param obstacles la liste d'obstacles de la salle
     * @return true si collision avec l'un d'eux
     */
    public boolean checkColisionObstacle(List<Obstacle> obstacles){
        for(int i = 0; i < obstacles.size(); i++){
            Vector2 sizeObstacle = obstacles.get(i).getSize();
            Vector2 positionObstacle = obstacles.get(i).getDeplacement().getPosition();
            if(Physics.rectangleCollision(positionObstacle, sizeObstacle, getDeplacement().getPosition(), getSize() )){
                return true;
            }
        }
        return false;
    }

    /**
     *
     * @param hero
     * @return true si collision avec le hero
     */
    public boolean checkColisionHero(Hero hero){
        Vector2 heroPosition = hero.getPosition();
        return Physics.rectangleCollision(heroPosition, HeroInfos.ISAAC_SIZE, getDeplacement().getPosition(), getSize() );
    }


    /**
     *
     * @param monsters la liste de monstres de la salle
     * @return true si collision avec un monstre
     */
    public boolean checkColisionMonsters(List<Monstre> monsters) {
        for(int i = 0; i < monsters.size(); i++){
            Vector2 sizeMonster = monsters.get(i).getSize();
            Vector2 positionMonster = monsters.get(i).getDeplacement().getPosition();
            if(Physics.rectangleCollision(positionMonster, sizeMonster, getDeplacement().getPosition(), getSize() )){
                return true;
            }
        }
        return false;
    }

    public void frapper(EtreVivant etre1) {
        etre1.prendDegat(getDegats());
        setCollision(true);
    }
}

