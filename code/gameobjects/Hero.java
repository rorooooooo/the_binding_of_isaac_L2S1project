package gameobjects;

import libraries.Physics;
import libraries.StdDraw;
import libraries.Vector2;
import resources.HeroInfos;
import resources.MonstreInfos;
//import libraries.Deque;

import java.util.*;

public class Hero extends EtreVivant
{
	/**
	 * attributs
	 */

	private List<Object> inventaire;
	private int soldePo;
	private boolean invincible;

	/**
	 *
	 * constructeur
	 */

	public Hero(Vector2 position, Vector2 size, double speed, String imagePath)
	{
		setLifeMax(HeroInfos.ISAAC_LIFE);
		setLife(getLifeMax());
		setImagePath(imagePath);
		setDeplacement(new Deplacement(position, speed, this));
		setSize(size);
		setDegatCollision(HeroInfos.DEGATS_COLLISION);

		this.projectiles = new ArrayList<Projectile>();
		this.inventaire = new ArrayList<Object>();
		this.soldePo = 10;
		this.invincible = false;
		setFly(false);
		setFrappable(true);
		setPowerTear(HeroInfos.TEAR_POWER);

	}

	/**
	 * methods
	 */

	public void updateGameObject() {
		getDeplacement().move();
		suppProjectiles();
	}


	private void objetsPassif() {
		for(int i = 0; i < inventaire.size(); i++){
			inventaire.get(i).agir();
		}
	}

	public void useObject(){
			inventaire.get(inventaire.size() - 1).agir();
	}


	/*
	 * Moving from key inputs. Direction vector is later normalised.
	 */
	public void goNext(int direction){
		getDeplacement().goNext(direction);
	}




	public void clearLarme() {
		setProjectiles(new ArrayList<Projectile>());
	}

	@Override
	public void frapper(EtreVivant etre){
		if(!(etre instanceof Hero) & !etre.equals(this)) etre.prendDegat(getDegatCollision());
	}

	public boolean checkColisionTear(Monstre monstre){
		Vector2 monstrePosition = monstre.getDeplacement().getPosition();
		for(int i = 0; i < projectiles.size() ; i++){
			Vector2 positionLarme = projectiles.get(i).getDeplacement().getPosition();
			if(Physics.rectangleCollision(monstrePosition, MonstreInfos.MONSTER_SIZE, positionLarme, HeroInfos.TEAR_SIZE )){
				projectiles.remove(i);
				return true;}
		}
		return false;
	}

	public boolean isDie() {
		return getLife() <=0 & !isInvincible();
	}

	public void ramasserObject(Object object) {
			setSoldePo(getSoldePo() - object.getPrice());
			inventaire.add(object);
			useObject();
			if ((!object.isPassif())) inventaire.remove(object); //si c'est un passif alors le garder
	}




	/*
	 * Getters and Setters
	 */


	public Vector2 getPosition() {return getDeplacement().getPosition();}

	public Vector2 getDirection() {return getDeplacement().getDirection();}

	public void setDirection(Vector2 direction)
	{
		getDeplacement().setDirection(direction);
	}

	public void setPosition(Vector2 vector2) { getDeplacement().setPosition(vector2);}


	public List<Object> getInventaire() {
		return inventaire;
	}

	public void setInventaire(List<Object> inventaire) {
		this.inventaire = inventaire;
	}


	public void heal(int life) {
		if(getLife() + life < getLifeMax()) setLife(getLife() + life);
		else setLife(getLifeMax());
	}

	public Vector2 getNormalizedDirection() {
		return getDeplacement().getNormalizedDirection();
	}

	public int getSoldePo() {
		return soldePo;
	}

	public void setSoldePo(int soldePo) {
		this.soldePo = soldePo;
	}

	public void addPo(int i) {
		setSoldePo(getSoldePo() + 10);
	}

	public boolean isInvincible() {
		return invincible;
	}

	public void setInvincible(boolean invincible) {
		this.invincible = invincible;
	}

	public void setSpeed(double isaacSpeed) {
		getDeplacement().setSpeed(isaacSpeed);
	}

	public void supprimeLarme(int indexOf) {
		projectiles.remove(indexOf);
	}
}
