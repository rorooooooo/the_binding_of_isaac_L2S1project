package gameWorld;

import gameobjects.*;
import gameobjects.Object;
import libraries.Physics;
import libraries.StdDraw;
import libraries.Vector2;
import resources.HeroInfos;
import resources.RoomInfos;
import resources.StuffInfos;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class SalleModele {

    /**
     * attributs
     */
    private static int tour;

    private Hero hero;
    private List<Door> portes;
    private List<Obstacle> obstacles;
    private List<Object> objets;
    private boolean finish;
    private int lastCollisionCadenceWithMonster;


    /**
     * getters setters
     */

    public Hero getHero() {
        return hero;
    }

    public void setHero(Hero hero) {
        this.hero = hero;
    }

    public List<Door> getPortes() {
        return portes;
    }

    public void setPortes(List<Door> portes) {
        this.portes = portes;
    }

    public static int getTour() {
        return tour;
    }

    public void setTour(int tour) {
        this.tour = tour;
    }

    public List<Obstacle> getObstacles() {
        return obstacles;
    }

    public void setObstacles(List<Obstacle> obstacles) {
        this.obstacles = obstacles;
    }

    public List<Object> getObjets() {
        return objets;
    }

    public void setObjets(List<Object> objets) {
        this.objets = objets;
    }

    public boolean isFinish() {
        return finish;
    }

    public void setFinish(boolean finish) {
        this.finish = finish;
    }

    public int getLastCollisionCadenceWithMonster() {
        return lastCollisionCadenceWithMonster;
    }

    public void setLastCollisionCadenceWithMonster(int lastCollisionCadenceWithMonster) {

        this.lastCollisionCadenceWithMonster = lastCollisionCadenceWithMonster;
    }




    /**
     * methods
     */

    public void ilSestFaitFrapper(int lastCollisionCadenceWithMonster) {

        if(hero.isFrappable()){
            hero.setFrappable(false);
            setLastCollisionCadenceWithMonster(getTour());
        }
    }

    /**
     * s'assure que les^portes soient bien ouvertes
     */
    public void checkAllDoors() {
        for(int i = 0; i < portes.size(); i++){
                if(isFinish()) 	portes.get(i).openDoor();
                else portes.get(i).closeDoor();
            }
        }


    /**
     * fait joeur le hero et ses projectiles, en fonction des collisions
     * @param collision avec un element de la pièce
     */
    public void makeHeroPlay()
    {
        checkCollisionEtre(hero);
        hero.updateGameObject();
        makeProjectilePlay(hero.getProjectiles());
        heroDeNouveauFrappable();
    }

    private void heroDeNouveauFrappable() {
        if(getLastCollisionCadenceWithMonster() + HeroInfos.colisionCadence < getTour()) {
            hero.setFrappable(true);
        }
    }

    /**
     * lis projectiles la liste de projectiles
     * vérifie les collisions, et s'il n'y en a pas faire jouer projectile
     * @param projectiles les projectiles de l'EtreVivant, s'il en a
     */
    public void makeProjectilePlay(List<Projectile> projectiles) {
        if(!Objects.isNull(projectiles)){
            for (Projectile projectile : projectiles) {
                checkCollisionProjectile(projectile);
                projectile.updateGameObject();
            }
            }
    }

    /**
     * @param projectile
     * @return true si collision avc un élément de la pièce (ici obstacles)
     */
    public void checkCollisionProjectile(Projectile projectile) {
        checkCollisionProjectileObstacles(projectile) ;
    }

    /**
     * exploite Physics
     * pas d'action particulière éxécutée par Physics, car le projectile sera simplement supprimé par l'EtreVivant
     * @param projectile
     * @return true si collision avec un obstacle
     */
    public void checkCollisionProjectileObstacles(Projectile projectile) {
        for (int i = 0; i < obstacles.size(); i++) {
            Physics.checkCollisionObstacleProjectile(obstacles.get(i), projectile);
            if(obstacles.get(i).getLife() <= 0) obstacles.remove(i);
            }
    }

    /**
     * la fonction appelé, dans Physics, s'occupe des actions en cas de collision
     * lis obstacles la liste d'obstacles
     * @param etre
     * @return true si collision avec un obstacle
     */
    public void checkCollisionEtreObstacles(EtreVivant etre) {

        for (Obstacle obstacle : obstacles) {
            int life = getHero().getLife();
            Physics.checkCollisionEtreObstacle(etre, obstacle);
            if(life != getHero().getLife()) ilSestFaitFrapper(getTour());
        }
    }

    /**
     * explicite, si objet trouvé, interragir avec puis le supprimer si ramassé
     */
    public void verifierIsaacObjetTrouver() {
        for(int i = 0; i < objets.size(); i++){
            Object object = objets.get(i);
            if(Physics.rectangleCollision(object.getPosition(), StuffInfos.OBJECT_SIZE, hero.getPosition(), hero.getSize())){
                if(interactionObject(object))  objets.remove(i);
            }

        }    }

    /**
     * vérifie eque le solde de PO est suffisant
     * @param object
     * @return true si l'objet est ramassé (solde suffisant), sinon false
     */
    public boolean interactionObject(Object object){
        if(object.getPrice() <= hero.getSoldePo()){
            object.setHero(hero);
            hero.ramasserObject(object);
            return true;
        }else{
            return false;
        }
    }

    public void display(){
    }
    /**
     * Convert a tile index to a 0-1 position.
     *
     * @param indexX
     * @param indexY
     * @return
     */
    private static Vector2 positionFromTileIndex(int indexX, int indexY)
    {
        return new Vector2(indexX * RoomInfos.TILE_WIDTH + RoomInfos.HALF_TILE_SIZE.getX(),
                indexY * RoomInfos.TILE_HEIGHT + RoomInfos.HALF_TILE_SIZE.getY());
    }


    /**
     * drawing methods
     */

    public void drawRoomBase() {
        drawBackground();
        hero.drawGameObject();
        drawPortes();
        drawObstacles();
        if(isFinish()) drawObjects();
    }


    public void drawObjects() {
        for(int i = 0; i < objets.size(); i++){
            objets.get(i).drawGameObject();
        }
    }

    public void drawBackground() {
        // For every tile, set background color.
        StdDraw.setPenColor(StdDraw.GRAY);
        for (int i = 0; i < RoomInfos.NB_TILES; i++)
        {
            for (int j = 0; j < RoomInfos.NB_TILES; j++)
            {
                Vector2 position = positionFromTileIndex(i, j);
                StdDraw.filledRectangle(position.getX(), position.getY(), RoomInfos.HALF_TILE_SIZE.getX(),
                        RoomInfos.HALF_TILE_SIZE.getY());
            }
        }
    }


    public void drawObstacles() {
        for(int i = 0; i < obstacles.size(); i++){
            obstacles.get(i).drawGameObject();
        }
    }

    public void drawPortes() {
        for(int i = 0; i < portes.size(); i++){
                portes.get(i).drawGameObject();

        }
    }

    /**
     * lis le tableau de portes, vérifie les collisions si la porte est ouverte(=si imagePath à opened door)
     * @return le numéro vers lequel pointe la porte dans le tableau de Room de gameworld, -1 si aucune collision
     */
    public int checkColisionPortes() {
        for (Door porte : portes) {
            if(porte.isOpen()) {
                if (Physics.rectangleCollision(hero.getPosition(), hero.getSize(), porte.getDeplacement().getPosition(), porte.getSize())) {
                    return porte.getVersRoom();
                }
            }}return -1;
    }


    /**
     * génère
     * @param position la position de l'objet
     * @return un objet parmi les 4 du jeu
     */
    public Object randomObject(Vector2 position){
        double nb = Math.random() * 100;
        if (nb < 25) return new CoeurVariable(position);
        else if (nb < 50) return new Piece(position);
        else if (nb < 75) return new HearthObject(position);
        else return new BloodOfTheMartyr(position);
    }

    /**
     * génère le nb d'objets demandé
     * @param nb le nb d'objets à génerer pour la salle
     */
    public void genererObjetsAleatoires(int nb){
        setObjets(new ArrayList<Object>());
        List<Object> objets = new ArrayList<Object>();
        for (int i = 0; i < nb; i++) {
            Object objet = randomObject(placerObjet(i));
            objet.objetAVendre();
            objets.add(objet);
        }
        setObjets(objets);
    }

    /**
     *
     * @param i le numéro de l'objet (premier, deuxième, troisième géneré ...)
     * @return le vecteur position d'un objet, pour le magasin (placement sur la map)
     */
    private Vector2 placerObjet(int i) {
        return new Vector2(0.2 + i*0.3 , 0.3);
    }

    /**
     *
     * @param etre
     * @return ture si out of map ou collision (avec un obstacle ici)
     */
    public void checkCollisionEtre(EtreVivant etre) {
        checkCollisionEtreObstacles(etre);
    }

    public boolean isOutMap(EtreVivant etre) {
        return Physics.isOutMap(etre.getFuturePosition());
    }


    /**
     * lis le fichier map en entier, et effectue une verif pour chaque caractère
     * si le résultat de la vérification aboutit, on ajoute le résultat à la liste d'obstacles
     * @param chemin_fichier le chemin de la map
     */
    public void creerLesObstacles(String chemin_fichier){
        String fichier_map = GameWorld.salleLectureFile(chemin_fichier);
        for(int i = 0;  i < fichier_map.toString().length(); i++){
            Obstacle carac = checkSiCestAMettreObstacle(fichier_map.charAt(i), i);

            if(!Objects.isNull(carac)){
                obstacles.add(carac);
            }
        }
    }

    /**
     * prend le caractère et vérifie si il faut créer un obstacle
     * se sert de l'indice pour calculer sa position sur la map (divisé en 49 carreaux)
     * @param charAt e caractère lut dans le tableau
     * @param i l'indice du caractère dans le tableau
     * @return
     */
    public Obstacle checkSiCestAMettreObstacle(char charAt, double i) {
        int y = (int) i/7;
        int x = (int) i%7;

        //valeurs aribtraires, trouvé grâce à salle_piege
        double true_x = (double)  ((x)/7.0) + 0.06;
        double true_y = (double) ((7.0-y)/7.0) - 0.07;

        Vector2 position = new Vector2(true_x, true_y);
        if(charAt == 'P'){
            return new Pic(position);
        }else if(charAt == 'R'){
            return new Rock(position);
        } return null;
    }



    /**
     * abstract methods
     */

    public abstract void updateRoom();

    public abstract void drawRoom();

    /**
     * utile pour tricher en enlevant tout les monstres d'un coup
     */
    public abstract void clearRoom();

    public boolean isaacDie() {
        return getHero().isDie();
    }


}
