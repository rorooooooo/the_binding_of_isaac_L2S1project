package gameWorld;

import gameobjects.*;
import gameobjects.Object;
import libraries.Physics;
import libraries.Vector2;
import resources.HeroInfos;
import resources.RoomInfos;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class RoomWithMonsters extends Room {
    /**
     * attributs
     */

    private List<EtreVivant> monsters;
    private boolean salleFini;

    /**
     * constructeurs
     */
    public RoomWithMonsters(List<Door> portes, String chemin_map) {
        super(portes, chemin_map);
        setLastCollisionCadenceWithMonster(-HeroInfos.colisionCadence);
        genererObjetsAleatoires(1);
        creerLesMonstres(chemin_map);
        setFinish(false);
    }


    /**
     * getters / setters
     */


    public List<EtreVivant> getMonsters() {
        return monsters;
    }

    public void setMonsters(List<EtreVivant> monsters) {
        this.monsters = monsters;
    }

    /**
     * fini uniquement si tous les monstres sont morts
     * @return true si fini
     */
    @Override
    public boolean isFinish() {
        if(monsters.size() == 0){
            setFinish(true);
            return true;
        }else {
            setFinish(false);
            return false;
        }
    }

    /**
     * methods
     */

    /**
     * lis le fichier map en entier, et effectue une verif pour chaque caractère
     * si le résultat de la vérification aboutit pour le carac donné, on ajoute le résultat à la liste de monstres
     * @param chemin_map
     */
    public void creerLesMonstres(String chemin_map) {
        this.monsters = new ArrayList<EtreVivant>();
        String fichier_map = GameWorld.salleLectureFile(chemin_map);
        for(int i = 0;  i < fichier_map.toString().length(); i++){
            EtreVivant carac = checkSiCestAMettreMonstre(fichier_map.charAt(i), i);

            if(!Objects.isNull(carac)){
                getMonsters().add(carac);
            }
        }
    }

    /**
     * prend le caractère et vérifie si il faut créer un monstre
     * se sert de l'indice pour calculer sa position sur la map (divisé en 49 carreaux)
     * @param charAt e caractère lut dans le tableau
     * @param i l'indice du caractère dans le tableau
     * @return
     */
    public EtreVivant checkSiCestAMettreMonstre(char charAt, double i) {
        int y = (int) i/7;
        int x = (int) i%7;

        double true_x = (double)  ((x)/7.0);
        double true_y = (double) ((7.0-y)/7.0);

        Vector2 position = new Vector2(true_x, true_y);
        if(charAt == 'F'){
            return new Fly(position);
        }else if(charAt == 'S'){
            return new Spider(position);
        } else if(charAt =='B'){
            return new Boss(position);
        }
        return null;
    }

    /**
     * une fois que la salle est fini,on verifie les collisions avec objets
     */
    public void updateRoom() //AJOUT makeMonstersPlay()
    {
        makeHeroPlay();
        if(isFinish()) verifierIsaacObjetTrouver();
        makeMonstersPlay();
        checkAllDoors();
    }

    /*
    rajout des collisions avec les monstres de la salle.
     */
    @Override
    public void checkCollisionEtre(EtreVivant etre) {
        checkCollisionEtreObstacles(etre);
        checkCollisionEntreEtre(etre);
    }

    /**
     *
     * @param etre
     * @return collision avec le hero si ça ne l'est pas, et collision avec les autres monstres (sauf celui envoyé)
     */
    private void checkCollisionEntreEtre(EtreVivant etre) {
        if(!etre.equals(getHero())){
            int life = getHero().getLife();
                Physics.checkCollisionEtreEtre(getHero(), etre);
                 if(life != getHero().getLife()) ilSestFaitFrapper(getTour());

        }
        List<EtreVivant> monstres = getMonsters();
        for (EtreVivant monstre : monstres) {
            if(!etre.equals(monstre)){
                Physics.checkCollisionEtreEtre(monstre, etre);
            }
        }
    }


    /**
     * pour chaque monstre :
     * vérifie si il est mort, si oui le supprimer de la liste
     * faire jouer le monstre, en vérifiant au préalable ses collisions
     * pour les Fly, je n'ai pour le moment pas trouver meilleur méthode pour leur assigner la bonne direction
     * peut-être ranger cela dans une fonction actionSpeciale(), dans EtreVivant, mais pas très propre
     */
    private void makeMonstersPlay() {
        for(int i = 0; i < getMonsters().size(); i++){
            EtreVivant monstre = getMonsters().get(i);
            if(monstre instanceof Fly) monstre.getDeplacement().setDirection(getHero().getDeplacement().getPosition().subVector(monstre.getDeplacement().getPosition()));
            if(monstre.isDie()) getMonsters().remove(i);
            else {
                checkCollisionEtre(monstre);
                monstre.updateGameObject();
                makeProjectilePlay(monstre.getProjectiles());
            }
        }
    }


    /**
     * rajout des collisions avec les etres de la rooms
     * @param projectile
     * @return true si collision
     */
    @Override
    public void checkCollisionProjectile(Projectile projectile){
        checkCollisionProjectileObstacles(projectile);
        checkCollisionProjectileEtres(projectile);
    }

    /**
     * le compteur de collision est incrémenté si hero touché
     * @param projectile
     * @return true si collision
     */
    public void checkCollisionProjectileEtres(Projectile projectile) {
        if(!getHero().equals(projectile.getAppartientA())){
            int life = getHero().getLife();
                Physics.checkCollisionEtreProjectile(getHero(), projectile);
                if(life != getHero().getLife()) ilSestFaitFrapper(getTour());

        }
        for (int i = 0; i < monsters.size(); i++){
                Physics.checkCollisionEtreProjectile(monsters.get(i), projectile);

        }
    }

    public void drawRoom() //AJOUT drawMonstres();
    {
        drawRoomBase();
        drawMonstres();
    }


    private void drawMonstres() { //AJOUT
        for(int i = 0; i < getMonsters().size(); i++){
            getMonsters().get(i).drawGameObject();
        }
    }

    public void clearRoom(){
        setMonsters(new ArrayList<EtreVivant>());
    }


    private static Vector2 positionFromTileIndex(int indexX, int indexY) //BOUGE PAS
    {
        return new Vector2(indexX * RoomInfos.TILE_WIDTH + RoomInfos.HALF_TILE_SIZE.getX(),
                indexY * RoomInfos.TILE_HEIGHT + RoomInfos.HALF_TILE_SIZE.getY());
    }



}
