package cat.rolegame;

import java.util.ArrayList;

public class Place {

	/**
	 * @param args
	 */
	private String name;
	private int id;
	private String description;
	private ArrayList<Monster> enemies;
	private ArrayList<Place> possiblePaths;
	private ArrayList<Integer> connections;
	private ArrayList<Obj> objects;
	
	public Place(String name, String description, int id){
		this.name = name; 
		this.description = description;
		enemies = new ArrayList<Monster>();
		possiblePaths = new ArrayList<Place>();
		connections = new ArrayList<Integer>();
		objects = new ArrayList<Obj>();
	}
	
	public Place(String name, String description, int id, 
			ArrayList<Integer> connections, ArrayList<Obj> objects){
		this.name = name; 
		this.description = description;
		this.connections = connections;
		this.id = id;
		enemies = new ArrayList<Monster>();
		possiblePaths = new ArrayList<Place>();
		this.objects = objects;
	}
	
	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}
	
	public int getID(){
		return id;
	}
	
	public ArrayList<Integer> getConnectionIDs(){
		return connections;
	}
	
	public ArrayList<Place> getPossiblePaths(){
		return possiblePaths;
	}
	
	public ArrayList<Monster> getEnemieList(){
		return enemies;
	}
	
	public ArrayList<Obj> getObjects() {
		return objects;
	}
	
	public void addEnemie(Monster enemie){
		enemies.add(enemie);
	}
	
	public void addPath(Place newPath){
		possiblePaths.add(newPath);
	}
	
	public void addObject(Obj object){
		objects.add(object);
	}
	
	public void printCompleteCurrentPlaceDescription(){
		System.out.println(description);
		for(Monster m : enemies){
			if(m.isAlive())
				System.out.println("You see a " + m.toString() + ". " + m.getDescription());
			else
				System.out.println("You see a dead " + m.toString());
		}
	}
	
	public String toString(){
		return name;
	}

	public void removeObject(Obj o) {
		objects.remove(o);
	}

}
