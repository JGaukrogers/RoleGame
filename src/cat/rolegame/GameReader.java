package cat.rolegame;

import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class GameReader {

//	private static final String DEFAULT_GAME_LOCATION = "";
	private static final String PLACE_TAG = "place";
	private static final String MONSTERS_TAG = "enemie";
	private static final String NAME_TAG = "name";
	private static final String LEVEL_TAG = "level";
	private static final String DESCRIPTION_TAG = "description";
	private static final String LOCATION_TAG = "location";
	
	private static final String WEAPON_TAG = "weapon";
	
	private ArrayList<Place> placeList;
	private ArrayList<Monster> monsterList;
	
	public ArrayList<Place> getPlaceList(){
		return placeList;
	}
	
	public ArrayList<Monster> getMonsterList(){
		return monsterList;
	}
	
	public void parseGame(String file) throws ParserConfigurationException, SAXException, IOException{
		

		placeList = new ArrayList<Place>();
		monsterList = new ArrayList<Monster>();
		
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document doc = db.parse(file);
		
		//Get list of places
		Element elm = doc.getDocumentElement();
		NodeList nl = elm.getElementsByTagName(PLACE_TAG);
		
		if(nl != null && nl.getLength() > 0) {
			for(int i = 0 ; i < nl.getLength();i++) {
				Element el = (Element)nl.item(i);
				Place place = getPlace(el);
				placeList.add(place);
			}
		}
		
		//Get list of monsters
		nl = elm.getElementsByTagName(MONSTERS_TAG);

		if(nl != null && nl.getLength() > 0) {
			for(int i = 0 ; i < nl.getLength();i++) {
				Element el = (Element)nl.item(i);
				Monster monster = getMonster(el);
				monsterList.add(monster);
			}
		}
		
		
	}

	private Place getPlace(Element el) {
		Place place;
		String name = getStringValue(el, NAME_TAG);
		String description = getStringValue(el, DESCRIPTION_TAG);
		int id = getIntValue(el, "id");
		ArrayList<Integer> connectionList;
		connectionList = getConnections(el);
		
		ArrayList<Obj> objectList;
		objectList = getObjects(el);
		
		place = new Place(name, description, id, connectionList, objectList);
		return place;
	}

	private Monster getMonster(Element el) {
		Monster monster;
		String name = getStringValue(el, NAME_TAG);
		String description = getStringValue(el, DESCRIPTION_TAG);
		int level = getIntValue(el, LEVEL_TAG);
		int location = getIntValue(el, LOCATION_TAG);
		
		monster = new Monster(name, description, level, location);
		return monster;
	}
	
	private String getStringValue(Element el, String tag){
		String val = null;
		NodeList nl = el.getElementsByTagName(tag);
		if(nl.getLength()>1)
			System.err.println("WARNING: more than one element \""+tag+"\" found. Taking the first value");
		if(nl!=null && nl.getLength()>0){
			Element aux = (Element)nl.item(0);
			if(aux.getFirstChild()!=null)
				val = aux.getFirstChild().getNodeValue();
			else val=null;
		}
		return val;
	}
	
	private int getIntValue(Element el, String tag){
		int val = 0;
		String vaux;
		NodeList nl = el.getElementsByTagName(tag);
		if(nl.getLength()>1)
			System.err.println("WARNING: more than one element \""+tag+"\" found. Taking the first value");
		if(nl!=null && nl.getLength()>0){
			Element aux = (Element)nl.item(0);
			if(aux.getFirstChild()!=null){
				vaux = aux.getFirstChild().getNodeValue();
				try{
					val = Integer.parseInt(vaux);
				}
				catch(NumberFormatException e){
					val = 0;
				}
			}
			else
				val = 0;
		}	
		return val;
	}
	
	private ArrayList<Integer> getConnections(Element el){
		int val;
		String vaux;
		ArrayList<Integer> connectionList = new ArrayList<Integer>();
		NodeList nl = el.getElementsByTagName("connection");
		if(nl!=null && nl.getLength()>0){
			for(int i=0; i<nl.getLength(); i++){
				Element aux = (Element)nl.item(i);
				if(aux.getFirstChild()!=null){
					vaux = aux.getFirstChild().getNodeValue();
					try{
						val = Integer.parseInt(vaux);
					}
					catch(NumberFormatException e){
						val = 0;
					}
				}
				else val=-1;
				
				connectionList.add(val);
			}
		}
		return connectionList;
	}
	
	private ArrayList<Obj> getObjects(Element el) {
		
		ArrayList<Obj> objectList = new ArrayList<Obj>();
		
		// Get all weapons
		NodeList nl = el.getElementsByTagName("weapon");
		if (nl!=null && nl.getLength()>0) {
			for(int i=0; i<nl.getLength(); i++){
				Element aux = (Element)nl.item(i);
				if(aux.getFirstChild()!=null){
					NamedNodeMap nnm = aux.getAttributes();
					Node attackNode = nnm.getNamedItem("attack");
					int attack = Integer.valueOf(attackNode.getNodeValue());
					
					Node descriptionNode = nnm.getNamedItem("description");
					String description = descriptionNode.getNodeValue();
					
					Node nameNode = nnm.getNamedItem("name");
					String name = nameNode.getNodeValue();
					
					Weapon weapon = new Weapon(name, description, attack);
					objectList.add(weapon);
				}
			}
		}
		
		// Get all shields
		nl = el.getElementsByTagName("shield");
		if (nl!=null && nl.getLength()>0) {
			for(int i=0; i<nl.getLength(); i++){
				Element aux = (Element)nl.item(i);
				if(aux.getFirstChild()!=null){
					NamedNodeMap nnm = aux.getAttributes();
					Node defenseNode = nnm.getNamedItem("defense");
					int defense = Integer.valueOf(defenseNode.getNodeValue());
					
					Node descriptionNode = nnm.getNamedItem("description");
					String description = descriptionNode.getNodeValue();
					
					Node nameNode = nnm.getNamedItem("name");
					String name = nameNode.getNodeValue();
					
					Shield shield = new Shield(name, description, defense);
					objectList.add(shield);
				}
			}
		}
		
		// Get all other
		nl = el.getElementsByTagName("object");
		if (nl!=null && nl.getLength()>0) {
			for(int i=0; i<nl.getLength(); i++){
				Element aux = (Element)nl.item(i);
				if(aux.getFirstChild()!=null){
					NamedNodeMap nnm = aux.getAttributes();
					
					Node descriptionNode = nnm.getNamedItem("description");
					String description = descriptionNode.getNodeValue();
					
					Node nameNode = nnm.getNamedItem("name");
					String name = nameNode.getNodeValue();
					
					Obj object = new Obj(name, description, false, false);
					objectList.add(object);
				}
			}
		}
		
		return objectList;
	}

}
