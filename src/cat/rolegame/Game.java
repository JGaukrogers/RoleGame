package cat.rolegame;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;


public class Game {
	
//	private static final String DEFAULT_GAME_LOCATION = "src/cat/rolegame/games/DemoGame.xml";
	private static final String DEFAULT_GAME_LOCATION = "games/DemoGame.xml";
	public static final String[] GAME_COMMANDS = {"LOOK", "SEARCH", "GO", "FIGHT","EXIT", "PICKUP"};
	private static Place beginPlace;
	private static Protagonist protagonist;
	
	public static void main(String[] args){

		Game game = new Game();
		boolean load = game.loadOrExit();
//		game.loadGame(); //for default game location
		if (load) {
			System.out.println("Game Loaded");
			game.playGame();
		}
		else {
			System.out.println("Bye");
		}
		
	}
	
	/**
	 * 
	 * @return true for loading game, false for exit
	 */
	public boolean loadOrExit(){
		
		boolean load = false;
		
		String command = "NOTHING";
		String parts[] = new String[2];
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		System.out.println("Load a game with LOAD $GAME_PATH\nExit game with EXIT");
		while(!command.equals("EXIT")){
			//Read command
			try {
				command = br.readLine();
//				command = command.toUpperCase();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			//Split command
			parts = command.split(" ");
			
			//Command interpreter
			if(parts.length == 0){
				System.out.println("Invalid command.");
				continue;
			}
			parts[0] = parts[0].toUpperCase();
			
			if (parts[0].equals("LOAD")){
				if (parts.length == 1){
					System.out.println("Usage: LOAD $GAME_PATH");
				}
				else{
					boolean success = loadGame(parts[1]);
					if (success) {
						load = true;
						break;
					}
					else {
						System.out.println("Error loading game. Is given path right?");
					}
				}
			}
			else if (parts[0].equals("EXIT")){
				load = false;
			}
			
		}
		
		return load;
		
	}
	
	private boolean loadGame(String path){
		
		GameReader reader = new GameReader();
		try {
			reader.parseGame(path);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
		//PLACES
		ArrayList<Place> placeList = reader.getPlaceList();
		
		Place placeArray[] = new Place[placeList.size()];
		for(Place p : placeList){
			placeArray[p.getID()] = p;
		}

		beginPlace = placeArray[0];
		
		for(int i = 0; i < placeArray.length; i++){
			Place p = placeArray[i];
			ArrayList<Integer> connects = p.getConnectionIDs();
			for(Integer j : connects){
				p.addPath(placeArray[j]);
			}
		}
		
		//MONSTERS or ENEMIES
		ArrayList<Monster> monsterList = reader.getMonsterList();
		for(Monster m : monsterList){
			placeArray[m.getLocation()].addEnemie(m);
		}
		
		//PROTAGONIST
		protagonist = new Protagonist("Demo Protagonist", "Just some demo hero");
		protagonist.setLocation(beginPlace);
		
		return true;
		
	}
	
	public void loadGame(){
		
		GameReader reader = new GameReader();
		try {
			reader.parseGame(DEFAULT_GAME_LOCATION);
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
		
		//PLACES
		ArrayList<Place> placeList = reader.getPlaceList();
		
		Place placeArray[] = new Place[placeList.size()];
		for(Place p : placeList){
			placeArray[p.getID()] = p;
		}

		beginPlace = placeArray[0];
		
		for(int i = 0; i < placeArray.length; i++){
			Place p = placeArray[i];
			ArrayList<Integer> connects = p.getConnectionIDs();
			for(Integer j : connects){
				p.addPath(placeArray[j]);
			}
		}
		
		//MONSTERS or ENEMIES
		ArrayList<Monster> monsterList = reader.getMonsterList();
		for(Monster m : monsterList){
			placeArray[m.getLocation()].addEnemie(m);
		}
		
		//PROTAGONIST
		protagonist = new Protagonist("Demo Protagonist", "Just some demo hero");
		protagonist.setLocation(beginPlace);
		
	}
	
	public void playGame(){
		String command = "BEGIN";
		String parts[] = new String[2];
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		printCompleteCurrentPlaceDescription();
		System.out.println("What do you wish to do?");
		printCommands();
		while(!command.equals("EXIT")){
			//Read command
			try {
				command = br.readLine();
				command = command.toUpperCase();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			//Split command
			parts = command.split(" ");
			
			//Command interpreter
			if(parts.length == 0){
				System.out.println("Invalid command. Must be \"action target\" or \"exit\".");
				continue;
			}

			if(parts[0].equals(GAME_COMMANDS[0])){
				if(parts.length == 1){
					printCompleteCurrentPlaceDescription();
				}
				else{
					//TODO: add be able to look at objects
					System.out.println(lookAt(parts[1]));
				}
			}
			else if(parts[0].equals(GAME_COMMANDS[1])){
				if (parts.length < 2) 
					System.out.println(searchForObjectsInCurrentLocation());
				else{
					//TODO: inspect objects or rucksack
//					System.out.println("Searching "+ parts[1] +" for objects");
					for (Obj o : protagonist.getRucksack()){
						if (o.getName().equalsIgnoreCase(parts[1])){
							System.out.println(o.getName() + ": " + o.getDescription());
							break;
						}
					}
				}
			}
			else if(parts[0].equals(GAME_COMMANDS[5])){
				if (parts.length < 2) 
					System.out.println("Pick up what?");
				else{
					System.out.println(pickUp(parts[1]));
				}
			}
			else if(parts[0].equals(GAME_COMMANDS[2])){
				if (parts.length < 2) 
					System.out.println("Invalid command. Must be \"Action target\" or \"exit\".");
				else{
					System.out.println(goTo(parts[1]));
				}
			}
			else if(parts[0].equals(GAME_COMMANDS[3])){
				if (parts.length < 2) 
					System.out.println("Invalid command. Must be \"Action target\" or \"exit\".");
				else{
					System.out.println(fightAgainst(parts[1]));
				}
			}
			else if(parts[0].equals(GAME_COMMANDS[4])){
				System.out.println("Exiting game");
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			else{
				printCommands();
			}
			
		}
	}

	private void printCommands() {
		System.out.println("Available commands: ");
		for(int i = 0; i < GAME_COMMANDS.length; i++){
			System.out.println(GAME_COMMANDS[i]);
		}
	}
	public String getCommands() {
		String info = "";
		info += "Available commands: \n";
		for(int i = 0; i < GAME_COMMANDS.length; i++){
			info += GAME_COMMANDS[i] + "\n";
		}
		return info;
	}
	
	public String goTo(String target){
		Place currentLocation = protagonist.getLocation();
		Place destination = null;
		String info = "";
		
		for(Place p : currentLocation.getPossiblePaths()){
			if (p.getName().equalsIgnoreCase(target)){
				destination = p;
				break;
			}
		}
		
		if(destination == null){
			info += "You can't go to " + target + "!";
		}
		else{
			protagonist.setLocation(destination);
			info += "You go to " + target + "\n";
			info += getCompleteCurrentPlaceDescription() + "\n";
		}
		return info;
	}
	
	public String lookAt(String target){
		String info = "";
		Place currentLocation = protagonist.getLocation();
		boolean pTarget = false;
		boolean mTarget = false;
		
		for(Place p : currentLocation.getPossiblePaths()){
			if (p.getName().equalsIgnoreCase(target)){
				pTarget = true;
				info += "You see a path leading to " + target;
				break;
			}
		}
		
		for(Monster m : currentLocation.getEnemieList()){
			if(m.toString().equalsIgnoreCase(target)){
				mTarget = true;
				if(m.isAlive())
					info += m.getDescription();
				else
					info += "You see a dead " + target;
			}
		}
		
		if(!pTarget && !mTarget){
			info += "No " + target + " to look at!";
		}
		return info;
	}
	
	private void printCompleteCurrentPlaceDescription(){
		Place currentPlace = protagonist.getLocation();
		System.out.println(currentPlace.getDescription());
		for(Monster m : currentPlace.getEnemieList()){
			if(m.isAlive())
				System.out.println("You see a " + m.toString() + ". " + m.getDescription());
			else
				System.out.println("You see a dead " + m.toString());
		}
	}
	
	public String getCompleteCurrentPlaceDescription(){
		String info = "";
		
		Place currentPlace = protagonist.getLocation();
		info += currentPlace.getDescription();
		for(Monster m : currentPlace.getEnemieList()){
			if(m.isAlive())
				info += " You see a " + m.toString() + ". " + m.getDescription();
			else
				info += " You see a dead " + m.toString();
		}
		return info;
	}
	
	public String searchForObjectsInCurrentLocation(){
		
		String info = "";
		Place currentPlace = protagonist.getLocation();
		ArrayList<Obj> objects = currentPlace.getObjects();
		for(Obj o : objects){
			info += o.getName() + " : " + o.getDescription() + "\n";
		}
		
		return info;
		
	}
	
	private String pickUp(String string) {
		// TODO Auto-generated method stub
		String info = "";
		Place currentPlace = protagonist.getLocation();
		ArrayList<Obj> objects = currentPlace.getObjects();
		
		boolean found = false;
		for (Obj o : objects) {
			if (o.getName().equalsIgnoreCase(string)){
				protagonist.addToRucksack(o);
				currentPlace.removeObject(o);
				info += "Successfully picked up " + string;
				break;
			}
		}
		return info;
	}
	
	public String fightAgainst(String target){
		
		boolean found = false;
		String info = "";
		
		for(Monster m : protagonist.getLocation().getEnemieList()){
			if(m.toString().equalsIgnoreCase(target)){
				found = true;
				if(m.isAlive()){
					m.kill();
					info += "You fight against " + target + ". You kill it.";
				}
				else{
					info += target + " is already dead.";
				}
				break;
			}
		}
		
		if(!found)
			info += "No " + target + " to fight against.";
		return info;
	}
	
	public ArrayList<Monster> getEnemieList(){
		return protagonist.getLocation().getEnemieList();
	}
	
	public ArrayList<Monster> getAliveEnemieList(){
		ArrayList<Monster> aliveEnemieList = new ArrayList<Monster>();
		for(Monster m : protagonist.getLocation().getEnemieList()){
			if(m.isAlive()){
				aliveEnemieList.add(m);
			}
		}
		return aliveEnemieList;
	}
	
	public ArrayList<Place> getPlacesToGo(){
		return protagonist.getLocation().getPossiblePaths();
	}
	
}
