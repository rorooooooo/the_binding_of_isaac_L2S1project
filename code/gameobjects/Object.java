package gameobjects;

import libraries.StdDraw;
import libraries.Vector2;
import resources.HeroInfos;
import resources.StuffInfos;

import java.util.Objects;

public abstract class Object extends ElementDuJeu {

    /**
     * attributs
     */

    private Hero hero;
    private String imagePath;
    private Vector2 size;
    private Vector2 position;
    private int price;
    boolean passif;
    boolean achetable;

    /**
     * constructeur
     */

    public Object(String imagePath, Vector2 position, boolean passif, int price){
        this.position = position;
        this.imagePath = imagePath;
        this.size = StuffInfos.OBJECT_SIZE;
        this.hero = null;
        this.passif = passif;
        this.price = price;
        this.achetable = false;
    }
    /**
     * getters / setters
     */
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


    public Hero getHero() {
        return hero;
    }

    public void setHero(Hero hero) {
        this.hero = hero;
    }

    public Vector2 getPosition() {
        return position;
    }

    public void setPosition(Vector2 position) {
        this.position = position;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public boolean isPassif() {
        return passif;
    }

    public void setPassif(boolean passif) {
        this.passif = passif;
    }

    public boolean isAchetable() {
        return achetable;
    }

    public void setAchetable(boolean achetable) {
        this.achetable = achetable;
    }

    /**
     * methods
     */

    public void agir(){
        if(!Objects.isNull(hero)){
            actionHero();
            setHero(null);
        }
    }

    public abstract void actionHero();


    public void healHero(){
        hero.heal(hero.getLifeMax());
    }

    public void rendreCoeurHero(int nb){
        hero.heal(nb);
    }

    public void addTwoHearth(){
        hero.setLifeMax(hero.getLifeMax() + 2);
    }

    public void augmenterDegatsHero(){
        hero.setPowerTear(hero.getPowerTear() + 1);

    }


    public void drawGameObject() {
        StdDraw.picture(getPosition().getX(), getPosition().getY(), getImagePath(), getSize().getX(), getSize().getY(), 0);
    }

    public void objetAVendre(){
        setAchetable(true);
    }


}
