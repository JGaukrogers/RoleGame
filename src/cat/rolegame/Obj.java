package cat.rolegame;

public class Obj {
	
	protected String name;
	protected String description;
	protected boolean equipable;
	protected boolean singleUse;
	
	public Obj(String objName, String objDescription, boolean objEquipable, boolean objSingleUse){
		name = objName;
		description = objDescription;
		equipable = objEquipable;
		singleUse = objSingleUse;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

}
