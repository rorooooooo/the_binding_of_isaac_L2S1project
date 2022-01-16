package gameobjects;

import libraries.Vector2;
import resources.ImagePaths;

import java.util.Objects;

public class Piece extends  Object{
    /**
     * attributs
     */

    private int valeur; //1, 5 ou 10

    /**
     * contructeur
     */
    public Piece(Vector2 position){
        super(null, position, false, 0);
        setValeur(choisirValeur());
        correctImage();
        setPrice(0);
        setAchetable(false);
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
            augmenterPoHero();
    }

    public void augmenterPoHero() {
        getHero().setSoldePo(getHero().getSoldePo() + valeur);
    }


    private int choisirValeur() {
        double nb = Math.random() * 100;
        if (nb < 40) return 1;
        else if (nb < 75) return 5;
        else return 10;

    }


    public void correctImage(){
        if(getValeur() == 1){
            setImagePath(ImagePaths.NICKEL);
        }
        else if(getValeur() == 5){
            setImagePath(ImagePaths.DIME);

        }
        else{
            setImagePath(ImagePaths.COIN);
        }
    }

}
