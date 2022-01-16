package gameWorld;

import gameobjects.*;
import gameobjects.Object;
import libraries.StdDraw;
import libraries.Vector2;
import resources.Controls;
import resources.HeroInfos;
import resources.ImagePaths;
import resources.MonstreInfos;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class GameWorld {
    private Hero hero;
    private int tour;
    private int last_tear_hero;
    private List<SalleModele> mes_salles;
    private SalleModele currentRoom;
    private List<String> cheminMapMonsters;

    // A world needs a hero
    public GameWorld(Hero hero, int nb_salles) {
        this.hero = hero;
        this.tour = 0;
        this.cheminMapMonsters = new ArrayList<String>();
        recupererLesSallesMonstres();
        this.last_tear_hero = -HeroInfos.cadence_tir_tear;

        this.mes_salles = creationDuMonde(new ArrayList<SalleModele>(), nb_salles);

        initialize();

    }

    //getters / setters


    public SalleModele getCurrentRoom() {
        return currentRoom;
    }

    public void setCurrentRoom(SalleModele currentRoom) {
        this.currentRoom = currentRoom;
    }

    public Hero getHero() {
        return hero;
    }

    public void setHero(Hero hero) {
        this.hero = hero;
    }



    /**
     * methods
     */

    /**
     * explicite
     */
    public void processUserInput() {
        currentRoom.setTour(++tour);
        processKeysForMovement();
        processKeysForTears();
        processKeysForTriche();
    }

    /**
     * crée si besoin d'ajoter des objets utilisables dans le futur
     */
    private void processKeysForObject() {
        if (StdDraw.isKeyPressed(Controls.object)) {
            hero.useObject();
        }
    }

    /**
     * explicite
     */
    private void processKeysForTriche() {
        if (StdDraw.isKeyPressed(Controls.faster)) {
            if(hero.getPowerTear() == HeroInfos.ISAAC_SPEED) hero.setSpeed(0.1);
            else hero.setSpeed(HeroInfos.ISAAC_SPEED);
            System.out.println(hero.getDeplacement().getSpeed() + " EST VOTRE VITESSE APRES UNE HONTEUSE TRICHE !");

        }
        if (StdDraw.isKeyPressed(Controls.god)) {
           if(hero.getPowerTear() > HeroInfos.TEAR_POWER) hero.setPowerTear(HeroInfos.TEAR_POWER);
           else hero.setPowerTear(100);
           System.out.println(hero.getPowerTear() + " EST VOTRE PUISSANCE APRES UNE HONTEUSE TRICHE !");
        }
        if (StdDraw.isKeyPressed(Controls.invincible)) {
            if(!hero.isInvincible()) hero.setInvincible(true);
            else hero.setInvincible(false);
            System.out.println("ETES VOUS INVINCIBLE TRICHEUR ? " + hero.isInvincible());

        }
        if (StdDraw.isKeyPressed(Controls.killMonsters)) {
            currentRoom.clearRoom();
            System.out.println("LE PASSAGE EST LIBRE TRUAND");

        }
        if (StdDraw.isKeyPressed(Controls.morePO)) {
            hero.addPo(10);
        }
    }

    /**
     * explicite
     * gestion du timer (pas le droit de tirer pdt x sec) ic parce qu'il me semblait naturel de l'y implémenter
     */
    private void processKeysForTears() {
        if (StdDraw.isKeyPressed(Controls.goUpArrow)) {
            if(HeroInfos.cadence_tir_tear + last_tear_hero < tour){
                hero.shoot(0);
                last_tear_hero = tour;}

        }
        if (StdDraw.isKeyPressed(Controls.goDownArrow)) {
            if(HeroInfos.cadence_tir_tear + last_tear_hero < tour){
                hero.shoot(1);
                last_tear_hero = tour;}
        }
        if (StdDraw.isKeyPressed(Controls.goRightArrow)) {
            if(HeroInfos.cadence_tir_tear + last_tear_hero < tour){
                hero.shoot(2);
                last_tear_hero = tour;}
        }
        if (StdDraw.isKeyPressed(Controls.goLeftArrow)) {
            if(HeroInfos.cadence_tir_tear + last_tear_hero < tour){
                hero.shoot(3);
                last_tear_hero = tour;}
        }
    }

    public boolean gameOver() {
        return (currentRoom.isaacDie());
    }

    /**
     * @return true si toute les rooms sont finies
     */
    public boolean gameWin(){
        for(int i = 0; i < mes_salles.size(); i++){
            if(!mes_salles.get(i).isFinish()) return false;
        }
        return true;
    }


    public void updateRoomIfColision(){ //pas très propre
        int collision = currentRoom.checkColisionPortes();
        if(collision!= -1) setSalleActuelle(collision);
    }


    public void updateGameObjects() {
        currentRoom.updateRoom();
    }


    public void drawGameObjects() {
        currentRoom.drawRoom();
        drawInfosGame();
    }


    private void drawInfosGame() {
        drawCoeurs();
        drawIsaacObject();
        drawPo();
    }


    private void drawPo() {
        String solde =  String.valueOf(hero.getSoldePo());
        double x = 0.9;
        double y = 0.1;
        Vector2 sizeCoin = MonstreInfos.MONSTER_SIZE;
        StdDraw.setPenRadius(0.05);
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.text(x, y, solde);
        StdDraw.picture(x + 0.05 , y, ImagePaths.DIME, sizeCoin.getX(), sizeCoin.getY(), 0);
    }

    private void drawIsaacObject() {
        Vector2 position = new Vector2(0.7, 0.9);
        Vector2 sizeObject = HeroInfos.ISAAC_SIZE;
        List<Object> inventaire = hero.getInventaire();
        for(int i = 0; i < inventaire.size(); i++){
            position.setX(position.getX()+0.1);
            StdDraw.picture(position.getX(), position.getY(), inventaire.get(i).getImagePath(), sizeObject.getX(), sizeObject.getY(), 0);

        }
    }

    private void drawCoeurs() {
        Vector2 position = new Vector2(0.005, 0.9);
        int fullHearth = hero.getLife() / 2 ;
        int halfHearth = hero.getLife() % 2 ;
        Vector2 sizeHearth = HeroInfos.ISAAC_SIZE;
        for(double i = 0; i < 0.1 * hero.getLifeMax() /2 ; i+=0.1){
            position.setX(position.getX() + 0.1);
            if(fullHearth > 0){
                StdDraw.picture(position.getX(), position.getY(), ImagePaths.HEART_HUD, sizeHearth.getX(), sizeHearth.getY(), 0);
                fullHearth--;
            }
            else if (halfHearth > 0){
                StdDraw.picture(position.getX(), position.getY(), ImagePaths.HALF_HEART_HUD, sizeHearth.getX(), sizeHearth.getY(), 0);
                halfHearth --;
            }
            else StdDraw.picture(position.getX(), position.getY(), ImagePaths.EMPTY_HEART_HUD, sizeHearth.getX(), sizeHearth.getY(), 0);

        }

    }

    private void processKeysForMovement() {
        if (StdDraw.isKeyPressed(Controls.goUp)) {
            hero.goNext(0);
        }
        if (StdDraw.isKeyPressed(Controls.goDown)) {
            hero.goNext(1);
        }
        if (StdDraw.isKeyPressed(Controls.goRight)) {
            hero.goNext(2);
        }
        if (StdDraw.isKeyPressed(Controls.goLeft)) {
            hero.goNext(3);
        }
    }



    /*prend en entrée un nombre défini de salles
     * retourne une list<Room>, avec les bonnes portes.
     * A chque itération, on ajoute une salle, une porte aléatoire, et on reboucle
     * la suivante réimplémente une porte, et ainsi de suite*/
    public List<SalleModele> creationDuMonde(List<SalleModele> salles, int nb_salles){
        List<SalleModele> les_salles = salles;
        List<Door> portes;

        if(les_salles.size() == 0){
            portes = new ArrayList<Door>();;
        }else{
            portes = les_salles.get(salles.size()-1).getPortes();
        }

        int vers_la_salle;
        if(les_salles.size() == 0) vers_la_salle = 1;
        else vers_la_salle = les_salles.size() + 1 ;

        portes = ajouterUnePorte(portes, vers_la_salle,nb_salles);


        les_salles.add(choixRoom(portes, les_salles.size(), nb_salles));



        if (nb_salles > 1){
            return  creationDuMonde(les_salles, nb_salles - 1);
        }
        else return les_salles;
    }

    /**
     * on doit lire la liste de porte du précédent élément de la liste de room
     * toruver la porte qui mène à la taille actuelle de la liste -1
     * ajouter une porte qui mène à taille machin -2
     * et à la position opposé
     * on ajoute une porte
     *      * qui mène à la salle suivante : les_salles.size()
     *      * (ensuite le curseur choisira la position dans la liste en focntion de versRoom)
     *      * on génère une position aléatoire, en vérifiant que
     *      * si la liste de portes en possède une, ce ne soit pas la même
     * @param portes
     * @return
     */
    public List<gameobjects.Door> ajouterUnePorte(List<gameobjects.Door> portes, int numero_salle, int nb_salle) {
        int positionPorteLien = -1;
        List<Door> portes_a_retourner = new ArrayList<Door>();

        /*
        on lit la loste de porte de la salle précédente:
        on ajoute la porte opposé menant vers la salle adjacente
        on supprime la porte de la liste, car elle appartient
        si vide, on ajoute une porte au hasard
         */
        if(portes.size() != 0){
            for (Door porte : portes) {
                positionPorteLien = oppositePorte(porte.getPositionDansMesCoor());
                if(porte.getVersRoom() == numero_salle - 1){
                    portes_a_retourner.add(new Door(positionPorteLien, porte.getVersRoom() - 1 ));
                }
            }
            int positionAleatoirePorte = nb_aleatoire_0_3();

            while(positionAleatoirePorte == positionPorteLien) positionAleatoirePorte = nb_aleatoire_0_3();
            if(nb_salle > 1) portes_a_retourner.add(new Door(positionAleatoirePorte, numero_salle));
        }
        else{
            portes_a_retourner.add(new Door(nb_aleatoire_0_3(), numero_salle));
        }


        return portes_a_retourner;
    }

    public int nb_aleatoire_0_3() {
        double nb = Math.random() * 100 ;

        if (nb < 25) return 0;
        else if (nb < 50) return 1;
        else if (nb < 75) return 2;
        else return 3;
    }


    private List<Integer> genererDeuxNombreDifferentDe(int differentDe) {
        int positionAleatoirePorte = nb_aleatoire_0_3();
        int positionAleatoirePorte2 = nb_aleatoire_0_3();

        while(differentDe == positionAleatoirePorte | positionAleatoirePorte2 == positionAleatoirePorte){
            positionAleatoirePorte = nb_aleatoire_0_3();
            positionAleatoirePorte2 = nb_aleatoire_0_3();
        }
        List<Integer> liste = new ArrayList<Integer>();
        liste.add(positionAleatoirePorte);
        liste.add(positionAleatoirePorte2);
        return liste;
    }

    /**
     * lis un fichier txt de map (7*7)
     */
    public static String salleLectureFile(String chemin_fichier){
        try
        {
            List<List<String>> texte = new ArrayList<List<String>>();
            // Le fichier d'entrée
            File file = new File(chemin_fichier);
            // Créer l'objet File Reader
            FileReader fr = new FileReader(file);
            // Créer l'objet BufferedReader
            BufferedReader br = new BufferedReader(fr);
            StringBuffer sb = new StringBuffer();
            String line;
            while((line = br.readLine()) != null)
            {
                // ajoute la ligne au buffer
                sb.append(line);
                sb.toString();
                //sb.append("\n");
            }
            fr.close();

            return sb.toString();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        return "";
    }


    /**
     * @param portes les portes de la salle
     * @param choixSalle sa position dans la liste
     * @param nb_salles_restantes le nombre de salle restante, avant d'implémenter la nouvelle renvoyé par la fonction
     * @return une salle
     */
    private Room choixRoom(List<Door> portes, int choixSalle, int nb_salles_restantes) {
        String chemin_room ="C:\\Users\\rorop\\Desktop\\ISAAC_FINAL\\maps\\hub_depart\\salle_vide.txt" ;
        String chemin_room_shop ="C:\\Users\\rorop\\Desktop\\ISAAC_FINAL\\maps\\shop\\shop_base.txt" ;
        String chemin_room_monsters = "";
        String chemin_boss = "C:\\Users\\rorop\\Desktop\\ISAAC_FINAL\\maps\\boss\\boss_room.txt";

        if(choixSalle == 0){
            return new Room(portes,chemin_room);
        }
        else if(nb_salles_restantes == 4){
            return new RoomShop(portes, chemin_room_shop);
        }
        else if(nb_salles_restantes == 1){
            return new RoomWithMonsters(portes, chemin_boss);
        }
        else {
            chemin_room_monsters = salleMonstreAleatoire();
            return new RoomWithMonsters(portes,chemin_room_monsters );
        }

    }

    /**
     * rend une des cartes de monstres au hasard
     * @return
     */
    public String salleMonstreAleatoire() {
        double nb = Math.random()  * cheminMapMonsters.size();
        return cheminMapMonsters.get((int) nb) ;
    }

    public void recupererLesSallesMonstres() {
        try{
            DirectoryStream<Path> stream = Files.newDirectoryStream(Paths.get("C:\\Users\\rorop\\Desktop\\ISAAC_FINAL\\maps\\monsters"));
            for(Path file : stream){
                cheminMapMonsters.add(file.toString());
            }

        }catch (IOException ex){
            System.out.println(ex);
        }

    }

    private List<Obstacle> genererObstacleAleatoire(int nbObstacles) {
        List<Obstacle> obstacles = new ArrayList<Obstacle>();
        for(int i = 0; i < nbObstacles; i++){
            Vector2 position = new Vector2(Math.random(), Math.random());
            Pic pic = new Pic(position);
            obstacles.add(pic);
        }
        return obstacles;
    }

    private List<EtreVivant> genererMonstresAleatoire(int nbMonstres) {
        List<EtreVivant> monstres = new ArrayList<EtreVivant>();
        for(int i = 0; i < nbMonstres; i++){
            Vector2 position1 = new Vector2(Math.random(), Math.random());
            Monstre monstre1 = new Fly(position1);
            Vector2 position2 = new Vector2(Math.random(), Math.random());
            Monstre monstre2 = new Spider(position2);

            monstres.add(monstre1);
            monstres.add(monstre2);
        }
        return monstres;
    }


    public void initialize(){

        this.currentRoom = this.mes_salles.get(0);
        this.currentRoom.setHero(hero);
        this.hero.setPosition(new Vector2(0.5, 0.5));
    }

    /**
     * @param une salle
     * @return la même salle avec le hero
     */
    public void setSalleActuelle(int room){
        this.currentRoom = this.mes_salles.get(room);
        this.currentRoom.setHero(hero);
        this.hero.clearLarme();

        /**
         * TODO modifier la physique, pour stopper les colisions qd pas nécessaire (ex changement de salle)
         */
        //double[] coordonnee_hero = Porte.intToCoor(oppositePorte(room));
        //this.hero.setPosition(new Vector2(coordonnee_hero[0], coordonnee_hero[1]));
        this.hero.setPosition(new Vector2(0.5, 0.5));

    }

    /**
     *
     * @param positionPorte
     * @return la position de la porte opposé (si une porte est crée en haut, alors la salle correspondante l'implémentera en bas, si gauche alors droite etc ...
     */
    public int oppositePorte(int positionPorte){
        if(positionPorte == 1) return 0;
        else if(positionPorte == 2) return 3;
        else if(positionPorte == 3) return 2;
        else return 1;

    }


}
