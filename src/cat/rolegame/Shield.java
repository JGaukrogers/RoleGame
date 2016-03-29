package cat.rolegame;

public class Shield extends Obj {
	
	private int defenseModifier;

	public Shield(String objName, String objDescription, int modifyier) {
		super(objName, objDescription, true, false);
		defenseModifier = modifyier;
	}
	
	public int getDefenseModifier() {
		return defenseModifier;
	}

}
