package gameobjects;

public abstract class ElementDuJeu {
    /**
     * attributs
     */
    private int life;
    private boolean frappable;


    /**
     * getters setters
     */

    public boolean isFrappable() {
        return frappable;
    }
    public void setFrappable(boolean frappable) {
        this.frappable = frappable;
    }
    public int getLife() {
        return life;
    }
    public void setLife(int life) {
        this.life = life;
    }

    /**
     * methods
     */

    public void prendDegat(double degat){
        if(isFrappable()) {
            setLife(getLife() - (int) degat);
        }
    }
}
