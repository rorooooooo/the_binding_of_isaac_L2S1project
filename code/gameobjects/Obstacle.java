package gameobjects;

import libraries.StdDraw;
import libraries.Vector2;

public abstract class Obstacle extends ElementDuJeu{

    /**
     * attributs
     */

    private Vector2 size;
    private Deplacement deplacement;
    private String imagePath;

    /**
     * getters / setters
     */
    public Vector2 getSize() {
        return size;
    }

    public void setSize(Vector2 size) {
        this.size = size;
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

    public Vector2 getPosition() {
        return deplacement.getPosition();
    }


    /**
     * methods
     */

    public void drawGameObject()
    {
        StdDraw.picture(deplacement.getPosition().getX(), deplacement.getPosition().getY(), getImagePath(), getSize().getX(), getSize().getY(), 0);
    }

    public abstract void actionCollision(EtreVivant etreVivant);

}


