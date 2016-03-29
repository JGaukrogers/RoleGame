package cat.rolegame;

import java.util.ArrayList;

public class Protagonist extends Character {
	
	private int level;
	private Place location;
	private ArrayList<Obj> rucksack;
	private Weapon weaponInHand;
	private Shield shieldInHand;

	public Protagonist(String newName, String newDescription) {
		name = newName;
		description = newDescription;
		level = 1;
		alive = true;
		location = null;
		rucksack = new ArrayList<Obj>();
		weaponInHand = null;
		shieldInHand = null;
	}
	
	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}
	
	public void increaseLevel() {
		this.level ++;
	}
	
	public void setLocation(Place newPlace){
		location = newPlace;
	}
	
	public Place getLocation(){
		return location;
	}
	
	public ArrayList<Obj> getRucksack() {
		return rucksack;
	}
	
	public void addToRucksack(Obj object){
		rucksack.add(object);
	}
	
	public boolean isWeaponEquiped(){
		return weaponInHand != null;
	}
	
	public boolean isShieldEquiped(){
		return shieldInHand != null;
	}
	
}
