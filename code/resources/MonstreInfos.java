package resources;
import libraries.Vector2;

public class MonstreInfos {

    public static final int SPIDER_LIFE = 5;
    public static final double SPIDER_SPEED = 0.02;

    public static final int FLY_LIFE = 3;
    public static final double FLY_SPEED = HeroInfos.ISAAC_SPEED/8;

    public static final Vector2 MONSTER_SIZE = RoomInfos.TILE_SIZE.scalarMultiplication(0.4);
    public static final int DEGAT_MONSTER = 1;

    public static final double DELAI_PAUSE_SPIDER = 25;


    public static final int CADENCE_TIR_FLY =100 ;
    public static final int TIR_FLY_POWER = 2;


    public static final int BOSS_LIFE = 6;
    public static final double DEGAT_BOSS = 2;

    public static final int CADENCE_TIR_BOSS =30 ;

}
