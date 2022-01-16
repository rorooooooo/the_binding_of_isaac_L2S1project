package gameobjects;

import libraries.Vector2;
import resources.ImagePaths;

public class CoeurVariable extends Object{

    /**
     * attributs
     */

    private int valeur; //1 ou 2


    /**
     * constructeur
     * @param position
     */

    public CoeurVariable(Vector2 position){
        super(null, position, false, 0);
        setValeur(choisirValeur());
        correctImage();
        setPrice(getValeur());
    }

    /**
     * getters setters
     */
    public int getValeur() {
        return valeur;
    }

    public void setValeur(int valeur) {
        this.valeur = valeur;
    }



    /**
     * methods
     */

    @Override
    public void actionHero() {
        rendreCoeurHero(getValeur());
    }

    private int choisirValeur() {
        double nb = Math.random() * 100;
        if (nb < 70) return 1;
        else return 2;

    }

    public void correctImage(){
        if(getValeur() == 2){
            setImagePath(ImagePaths.HEART_PICKABLE);
        }

        else{
            setImagePath(ImagePaths.HALF_HEART_PICKABLE);
        }
    }

}
