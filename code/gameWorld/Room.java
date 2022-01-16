package gameWorld;

import gameobjects.*;
import gameobjects.Object;
import resources.HeroInfos;

import java.util.ArrayList;
import java.util.List;

public class Room extends SalleModele
{
	/**
	 * implémentation simple de SalleModele
	 */

	/**
	 * constructeurs TODO à CLEANER
	 */
	public Room(Hero hero, List<Door> portes, String chemin_map)
	{
		//haut 0, bas 1, est 2, ouest 3
		setHero(hero);
		setPortes(portes);
		setTour(0);
		setObstacles(new ArrayList<Obstacle>());
		creerLesObstacles(chemin_map);
		setObjets(new ArrayList<Object>());
		setFinish(true);
		setLastCollisionCadenceWithMonster(-HeroInfos.colisionCadence);
	}

	public Room(List<Door> portes, String chemin_map){
		this(null, portes, chemin_map);
	}



	/**
	 * methods
	 */

	/**
	 * puisqu'il n'y a aucun monstre à tuer, vérifier que la salle est bien finish
	 */
	@Override
	public void clearRoom(){
		if(!isFinish()) setFinish(true);
	}

	/*
	 * Make every entity that compose a room process one step
	 */
	@Override
	public void updateRoom()
	{
		checkCollisionEtre(getHero());
		makeHeroPlay();
		verifierIsaacObjetTrouver();
		checkAllDoors();
	}

	/*
	 * Drawing
	 */
	@Override
	public void drawRoom()
	{
		drawRoomBase();
	}


}
