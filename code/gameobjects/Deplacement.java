package gameobjects;

import libraries.Physics;
import libraries.Vector2;
import resources.HeroInfos;

import java.util.List;

public class Deplacement {

    /**
     * attributs
     */

    private Vector2 position;
    private double speed;
    private Vector2 direction;
    private EtreVivant etre;

    /**
     *constructeurs
     */

    public Deplacement(Vector2 position,  double speed, EtreVivant etre){
        this.position = position;
        this.speed = speed;
        this.direction = new Vector2(0, 0);
        this.etre = etre;
    }

    public Deplacement(double speed){
        this(new Vector2(0.5, 0.5), speed, null);
    }

    /**
     * getter / setters
     */

    public Vector2 getPosition() {
        return position;
    }

    public void setPosition(Vector2 position) {
        this.position = position;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public Vector2 getDirection() {
        return direction;
    }

    public void setDirection(Vector2 direction) {
        this.direction = direction;
    }

    /**
     * methods
     */

    public void goNext(int direction){
        if(direction == 0){
            getDirection().addY(1);
        }else if(direction == 1){
            getDirection().addY(-1);
        }else if(direction == 2){
            getDirection().addX(1);
        }else if(direction == 3){
            getDirection().addX(-1);

        }
    }

    public void goUpNext()
    {
        getDirection().addY(1);
    }

    public void goDownNext()
    {
        getDirection().addY(-1);
    }

    public void goLeftNext()
    {
        getDirection().addX(-1);
    }

    public void goRightNext()
    {
        getDirection().addX(1);
    }

    public Vector2 getNormalizedDirection()
    {
        Vector2 normalizedVector = new Vector2(direction);
        normalizedVector.euclidianNormalize(speed);
        return normalizedVector;
    }

    /**
     * interdit les déplacement hors-map
     */
    public void move(){
        Vector2 normalizedDirection = getNormalizedDirection();
        Vector2 positionAfterMoving = getPosition().addVector(normalizedDirection);
        if(!isOutMap(positionAfterMoving))setPosition(positionAfterMoving);
        direction = new Vector2();
    }

    /**
     * même fonction, mais laisse les projectiles quitter la map
     * ils seront supprimé par les êtres vivants
     */
    public void moveProjectile(){
        Vector2 normalizedDirection = getNormalizedDirection();
        Vector2 positionAfterMoving = getPosition().addVector(normalizedDirection);
        setPosition(positionAfterMoving);
    }

    private boolean isOutMap(Vector2 positionAfterMoving) {
        return positionAfterMoving.getX() > 1 | positionAfterMoving.getY() >1 | positionAfterMoving.getX() < 0 | positionAfterMoving.getY() <0 ;
    }

    public boolean isOutMap() {
            return ((getPosition().getX() > 1 | getPosition().getY() > 1)) | ((getPosition().getX() < 0 | getPosition().getY() < 0));
    }

    public boolean checkColisionObstacle(Vector2 position,List<Obstacle> obstacles){
        for(int i = 0; i < obstacles.size(); i++){
            Vector2 positionObstacle = obstacles.get(i).getPosition();
            Vector2 sizeObstacle = obstacles.get(i).getSize();
            if(Physics.rectangleCollision(positionObstacle, sizeObstacle, position, HeroInfos.ISAAC_SIZE ) | Physics.isOutMap(position)) {
                obstacles.get(i).actionCollision(etre);
                //if(i !=0) obstacles.add(obstacles.set(0, obstacles.get(i)));
                return true;
            }
        }
        return false;
    }



    public Vector2 getFuturePosition() {
        Vector2 normalizedDirection = getNormalizedDirection();
        return getPosition().addVector(normalizedDirection);
    }
}
