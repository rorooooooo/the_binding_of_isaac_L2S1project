package libraries;

import gameobjects.*;
import resources.RoomInfos;

public class Physics
{
	/**
	 * Calculates whether two rectangles are in collision or not. Two rectangles are
	 * in collision if they share any area.
	 * 
	 * @param pos1:  position of first rectangle
	 * @param size1: size of first rectangle
	 * @param pos2:  position of second rectangle
	 * @param size2: size of second rectangle
	 * @return true if rectangles are in collision else false
	 */
	public static boolean rectangleCollision(Vector2 pos1, Vector2 size1, Vector2 pos2, Vector2 size2)
	{
		// We authorise a small overlap before considering a collision in order to avoid
		// collision between side-to-side objects and floating point approximation
		// errors.
		double authorizedOverlap = RoomInfos.TILE_WIDTH / 1000;
		double coef_division_size = 3;

		boolean tooFarLeft = pos1.getX() + (size1.getX() / coef_division_size) < authorizedOverlap + pos2.getX() - (size2.getX() / coef_division_size);
		boolean tooFarBelow = pos1.getY() + (size1.getY() / coef_division_size) < authorizedOverlap + pos2.getY() - (size2.getY() / coef_division_size);
		boolean tooFarRight = pos1.getX() - (size1.getX() / coef_division_size) + authorizedOverlap > pos2.getX() + (size2.getX() / coef_division_size);
		boolean tooFarAbove = pos1.getY() - (size1.getY() / coef_division_size) + authorizedOverlap > pos2.getY() + (size2.getY() / coef_division_size);

		if (tooFarLeft || tooFarRight || tooFarAbove || tooFarBelow)
		{
			return false;
		}
		return true;
	}

    public static boolean isOutMap(Vector2 positionAfterMoving) {
			return positionAfterMoving.getX() > 1 | positionAfterMoving.getY() >1 | positionAfterMoving.getX() < 0 | positionAfterMoving.getY() <0 ;

    }

	/**
	 *
 	 * @param etre
	 * @param obstacle
	 * @return true si collision, et action de collision avec l'obstacle
	 */
	public static void checkCollisionEtreObstacle(EtreVivant etre, Obstacle obstacle) {

		if(!etre.isFly()){
			Vector2 position = etre.getDeplacement().getPosition();
			Vector2 direction = etre.getDeplacement().getNormalizedDirection();
			Vector2 new_direction = position.addVector(direction);


			if(Physics.rectangleCollision(new_direction,etre.getSize(), obstacle.getPosition(), obstacle.getSize())){
				obstacle.actionCollision(etre);
				new_direction.subVector(direction);
				new_direction.addVector(new Vector2(direction.getX(), 0));
				if(Physics.rectangleCollision(new_direction,etre.getSize(), obstacle.getPosition(), obstacle.getSize())){
					new_direction.subVector(direction);
					new_direction.addVector(new Vector2(0, direction.getY()));
					if(Physics.rectangleCollision(new_direction,etre.getSize(), obstacle.getPosition(), obstacle.getSize())) {
						etre.getDeplacement().setDirection(new Vector2(0, 0));
					}else etre.getDeplacement().setDirection(new_direction);
				}else etre.getDeplacement().setDirection(new_direction);
			}
	}
	}

	/**
	 *
	 * @param etre1
	 * @param etre2
	 * @return true si collision entre les deux êtres, et ils se frappent mutuellement
	 */
	public static void checkCollisionEtreEtre(EtreVivant etre1, EtreVivant etre2) {

			Vector2 position = etre1.getDeplacement().getPosition();
			Vector2 position1 = etre1.getDeplacement().getPosition();
			Vector2 position2 = etre1.getDeplacement().getPosition();
			Vector2 direction = etre1.getDeplacement().getNormalizedDirection();
			Vector2 new_direction = position.addVector(direction);

			if(Physics.rectangleCollision(new_direction,etre1.getSize(), etre2.getPosition(), etre2.getSize())) {
				etre2.frapper(etre1);
				etre1.frapper(etre2);
				new_direction.subVector(direction);
				new_direction.addVector(new Vector2(direction.getX(), 0));
				if (Physics.rectangleCollision(new_direction, etre1.getSize(), etre2.getPosition(), etre2.getSize())) {
					position.subVector(direction);
					new_direction.addVector(new Vector2(0, direction.getY()));
					if (Physics.rectangleCollision(new_direction, etre1.getSize(), etre2.getPosition(), etre2.getSize())) {
						etre1.getDeplacement().setDirection(new Vector2(0, 0));
					} else etre1.getDeplacement().setDirection(new_direction);
				} else etre1.getDeplacement().setDirection(new_direction);
			}


	}

	public static void checkCollisionEtreProjectile(EtreVivant etre1, Projectile projectile) {
		if(!etre1.equals(projectile.getAppartientA())){
			Vector2 position = etre1.getDeplacement().getPosition();
			Vector2 direction = etre1.getDeplacement().getNormalizedDirection();
			Vector2 new_direction = position.addVector(direction);

			if(Physics.rectangleCollision(new_direction,etre1.getSize(), projectile.getPosition(), projectile.getSize())) {
				projectile.frapper(etre1);
				}

		}
	}

	public static void checkCollisionObstacleProjectile(Obstacle obstacle, Projectile projectile) {
		if(Physics.rectangleCollision(obstacle.getPosition(),obstacle.getSize(), projectile.getDeplacement().getPosition(), projectile.getSize())){
			obstacle.prendDegat(1);
			projectile.setCollision(true);
		}
	}


}
