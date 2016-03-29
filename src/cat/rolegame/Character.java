package cat.rolegame;


public abstract class Character {


	protected String name;
	protected String description;
	//alive = false -> character is dead
	protected boolean alive;
	
//	/**
//	 * @param args
//	 */
//	public static void main(String[] args) {
//
//	}
	
	public boolean isAlive() {
		return alive;
	}
	public void kill() {
		this.alive = false;
	}
	public String getName() {
		return name;
	}
	public String getDescription() {
		return description;
	}
	

}
