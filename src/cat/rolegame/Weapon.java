package cat.rolegame;

public class Weapon extends Obj {
	
	private int attackModifier;

	public Weapon(String objName, String objDescription, int modifyier) {
		super(objName, objDescription, true, false);
		attackModifier = modifyier;
	}
	
	public int getAttackModifier() {
		return attackModifier;
	}

}
