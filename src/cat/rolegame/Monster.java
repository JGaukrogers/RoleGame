package cat.rolegame;

import java.util.ArrayList;

public class Monster extends Character {
	
	private int level;
	private int location;
	private ArrayList<Obj> drops;

	public Monster(String newName, String newDescription, int newLevel, int newLocation){
		name = newName;
		description = newDescription;
		level = newLevel;
		location = newLocation;
		alive = true;
		drops = new ArrayList<Obj>();
	}
	
	public int getLevel(){
		return level;
	}
	
	public int getLocation(){
		return location;
	}
	
	public String toString(){
		return name;
	}
	
}
