package gameloop;

import gameWorld.GameWorld;
import gameobjects.Hero;
import libraries.StdDraw;
import libraries.Timer;
import resources.DisplaySettings;
import resources.HeroInfos;
import resources.ImagePaths;
import resources.RoomInfos;

import java.util.Scanner;


public class Main
{
	public static void main(String[] args)
	{
		// Hero, world and display initialisation.
		System.out.println("Bienvenue sur The Binding of Isaac on a racheté les droits !");
		int recommencer = 1;
		Scanner sc = new Scanner(System.in);
		while(recommencer != 0){
			int nb_salles = 0;
			System.out.println("Avec combien de salles souhaitez-vous jouer ? (9 c'est le top débutant que vous êtes)");
			nb_salles = sc.nextInt();
			Hero isaac = new Hero(RoomInfos.POSITION_CENTER_OF_ROOM, HeroInfos.ISAAC_SIZE, HeroInfos.ISAAC_SPEED, ImagePaths.MAGDALENE);
			GameWorld world = new GameWorld(isaac, nb_salles);
			initializeDisplay();
			while (!world.gameOver() & !world.gameWin())
			{
				processNextStep(world);
			}
			if(world.gameOver()) {
				StdDraw.picture(0.5, 0.5, ImagePaths.LOSE_SCREEN, 1, 1);
				System.out.println("PERDU ! 0 pour quitter, vous feriez mieux");
			}
			else {
				StdDraw.picture(0.5, 0.5, ImagePaths.WIN_SCREEN, 1, 1);
				System.out.println("enfin gagné. hourra. envie d'améliorer votre médiocre ratio ? tapez 1");
			}

			StdDraw.show();
			recommencer = sc.nextInt();
		}


		// Main loop of the game

	}

	private static void processNextStep(GameWorld world)
	{
		Timer.beginTimer();
		StdDraw.clear();
		world.processUserInput();
		world.updateRoomIfColision(); //rajout
		world.updateGameObjects();
		world.drawGameObjects();
		StdDraw.show();
		Timer.waitToMaintainConstantFPS();
	}

	private static void initializeDisplay()
	{
		// Set the window's size, in pixels.
		// It is strongly recommended to keep a square window.
		StdDraw.setCanvasSize(RoomInfos.NB_TILES * DisplaySettings.PIXEL_PER_TILE,
				RoomInfos.NB_TILES * DisplaySettings.PIXEL_PER_TILE);

		// Enables double-buffering.
		// https://en.wikipedia.org/wiki/Multiple_buffering#Double_buffering_in_computer_graphics
		StdDraw.enableDoubleBuffering();
	}


}
