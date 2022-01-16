package resources;

import libraries.Keybinding;

import static libraries.Keybinding.SpecialKeys.*;

public class Controls
{
	//moove
	public static int goUp = Keybinding.keycodeOf('z');
	public static int goDown = Keybinding.keycodeOf('s');
	public static int goRight = Keybinding.keycodeOf('d');
	public static int goLeft = Keybinding.keycodeOf('q');

	public static int goUpArrow = Keybinding.keycodeOf(UP);
	public static int goDownArrow = Keybinding.keycodeOf(DOWN);
	public static int goRightArrow = Keybinding.keycodeOf(RIGHT);
	public static int goLeftArrow = Keybinding.keycodeOf(LEFT);

	//triche
	public static int invincible = Keybinding.keycodeOf('i');
	public static int faster = Keybinding.keycodeOf('l');
	public static int killMonsters = Keybinding.keycodeOf('k');
	public static int god = Keybinding.keycodeOf('p');
	public static int morePO = Keybinding.keycodeOf('o');

    public static int object = Keybinding.keycodeOf('y');
}
