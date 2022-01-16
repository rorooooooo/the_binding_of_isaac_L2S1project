package gameobjects;

import libraries.StdDraw;
import libraries.Vector2;
import resources.HeroInfos;
import resources.ImagePaths;

public class Door extends Obstacle{

    /**
     * attributs
     */

    private int positionDansMesCoor;
    private int inclinaison;
    private int versRoom;
    private int indice_salle;


    /**
     * constructeur
     */

    public Door( int position, int versRoom){
        setImagePath(ImagePaths.CLOSED_DOOR);
        setDeplacement(new Deplacement(intToCoor(position), 0, null));
        setSize(HeroInfos.ISAAC_SIZE);
        setInclinaison(bonneInclinaison());
        this.positionDansMesCoor = position;
        this.versRoom = versRoom;
    }



    /**
     * getters setters
     */

    public int getInclinaison() {
        return inclinaison;
    }

    public void setInclinaison(int inclinaison) {
        this.inclinaison = inclinaison;
    }

    public int getPositionDansMesCoor() {
        return positionDansMesCoor;
    }

    public void setPositionDansMesCoor(int positionDansMesCoor) {
        this.positionDansMesCoor = positionDansMesCoor;
    }

    public int getVersRoom() {
        return versRoom;
    }

    public void setVersRoom(int versRoom) {
        this.versRoom = versRoom;
    }

    public int getIndice_salle() {
        return indice_salle;
    }

    public void setIndice_salle(int indice_salle) {
        this.indice_salle = indice_salle;
    }

    /**
     * methods
     */

    public boolean isOpen() {
        return getImagePath().equals(ImagePaths.OPENED_DOOR);
    }

    public void openDoor() {
        setImagePath(ImagePaths.OPENED_DOOR);
    }
    public void closeDoor() {
        setImagePath(ImagePaths.CLOSED_DOOR);
    }

    @Override
    public void actionCollision(EtreVivant etre){

    }

    /**
     *
     * @param localisation un entier entre 0 t 3
     * @return la position de la porte en fonction de l'entier
     */
    public Vector2 intToCoor(int localisation) {
        Vector2 coordonnees; // x et y
        if(localisation == 0){
            coordonnees = new Vector2(0.5, 0.98);

        }else if(localisation == 1){
            coordonnees = new Vector2(0.5, 0.02);

        }else if(localisation == 2){
            coordonnees = new Vector2(0.98, 0.5);

        }else{
            coordonnees = new Vector2(0.02, 0.5);

        }
        return coordonnees;
    }

    @Override
    public void drawGameObject() {
        StdDraw.picture(getPosition().getX(), getPosition().getY(), getImagePath(), getSize().getX(), getSize().getY(), inclinaison);
    }

    public int bonneInclinaison() {
        if(getPosition().getX() < 0.5) return 90;
        else if(getPosition().getX() > 0.5) return -90;
        if(getPosition().getY() < 0.5) return 180;
        else return 0;
    }

}
