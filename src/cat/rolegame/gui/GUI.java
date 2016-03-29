package cat.rolegame.gui;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
//import javax.swing.JTextField;

import cat.rolegame.Game;
import cat.rolegame.Monster;
import cat.rolegame.Place;


public class GUI {

	private JFrame frame;
	JPanel panel;
	GridBagConstraints gbc;
	
	private JTextArea gameInfo;
//	private JScrollBar infoScroll;
//	private JScrollBar auxiliarInfoScroll;
	
	private JButton startGame;
	private JButton endGame;
	
//	private JTextField gameCommands;
//	private JButton sendCommand;
	
	// Game control buttons:
	private JButton lookButton;
	private JButton goButton;
	private JButton fightButton;
	
	Game game;
	
	private static final String START_MSG = "Start new game";
	private static final String END_MSG = "End game";
	private static final String LOOK_BUTTON = "Look at...";
	private static final String GO_BUTTON = "Go to...";
	private static final String FIGHT_BUTTON = "Fight against...";
	
	private static final int MIN_TEXT_LENGHT = 40;
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		@SuppressWarnings("unused")
		GUI ncg = new GUI();
	}
	
	public GUI(){
		frame = new JFrame("Role Game");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
        panel = new JPanel(new GridBagLayout());
        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // The information field
        gameInfo = new JTextArea(10, 20);
        gameInfo.setEditable(false);
        gameInfo.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 12));
        gameInfo.append("This is the Game\n");
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(2, 2, 2, 2);
        panel.add(gameInfo, gbc);
        
        // The scroll bars
//        infoScroll = new JScrollBar(JScrollBar.VERTICAL);
//        gbc.gridx = 1;
//        gbc.gridy = 0;
//        panel.add(infoScroll, gbc);
//        
//        auxiliarInfoScroll = new JScrollBar(JScrollBar.HORIZONTAL);
//        gbc.gridx = 0;
//        gbc.gridy = 1;
//        panel.add(auxiliarInfoScroll, gbc);
        
        // The command field
//        gameCommands = new JTextField(20);
//        gameCommands.setEditable(true);
//        gbc.gridx = 0;
//        gbc.gridy = 2;
//        panel.add(gameCommands, gbc);
        
        // Game controls
        lookButton = new JButton(LOOK_BUTTON);
        lookButton.setEnabled(false);
        lookAction(lookButton);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.weightx = 1;
        panel.add(lookButton, gbc);
        
        goButton = new JButton(GO_BUTTON);
        goButton.setEnabled(false);
        goAction(goButton);
        gbc.gridx = 1;
        gbc.gridy = 1;
        panel.add(goButton);
        
        fightButton = new JButton(FIGHT_BUTTON);
        fightButton.setEnabled(false);
        fightAction(fightButton);
        gbc.gridx = 2;
        gbc.gridy = 1;
        panel.add(fightButton);
        
        // The start/stop buttons
        startGame = new JButton(START_MSG);
        startGame.setEnabled(true);
        startGame(startGame);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weightx = 1;
        panel.add(startGame, gbc);
        
        endGame = new JButton(END_MSG);
        endGame.setEnabled(false);
        stopGame(endGame);
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.weightx = 1;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        panel.add(endGame, gbc);
        
        // Sent command
//        sendCommand = new JButton("Send Command");
//        sendCommand.setEnabled(false);
//        sendCommand(sendCommand);
//        gbc.gridx = 0;
//        gbc.gridy = 3;
//        panel.add(sendCommand, gbc);
        
        
        // adding everything in the frame:
        frame.getContentPane().add(panel);
        
        frame.pack();
        frame.setVisible(true);
	}
	
	private void startGame(JButton button){
		
		button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				game = new Game();
				game.loadGame();
				gameInfo.setText("");
				gameInfo.append("New game loaded\n");
//				game.playGame();
				
//				String description = game.getCompleteCurrentPlaceDescription();
//				int position = 0;
//				char ch = ' ';
//				
//				do{
//					position = description.indexOf(ch, position + MIN_TEXT_LENGHT);
//					if(position != -1){
//						description = description.substring(0, position +1) + "\n" + description.substring(position +1, description.length());
//					}
//				}while(position != -1);
//					
//				gameInfo.append(description + "\n");
				gameInfo.append(adaptTextToWindowSize(game.getCompleteCurrentPlaceDescription()));

				lookButton.setEnabled(true);
				goButton.setEnabled(true);
				fightButton.setEnabled(true);
				
				endGame.setEnabled(true);
				startGame.setEnabled(false);
				
			}
		});
		
	}
	
	private void stopGame(JButton button){
		
		button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				game = null;
				
				startGame.setEnabled(true);
				endGame.setEnabled(false);
				
				lookButton.setEnabled(false);
				goButton.setEnabled(false);
				fightButton.setEnabled(false);
				
				gameInfo.setText("Game ended");
				
			}
		});
		
	}
	
/*	private void sendCommand(JButton button){
		
		button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				String command = "BEGIN";
				String parts[] = new String[2];

				//Read command
				command = gameCommands.getText();
				command = command.toUpperCase();


				//Split command
				parts = command.split(" ");

				//Command interpreter
				if(parts.length == 0){
					gameInfo.setText("Invalid command. Must be \"action target\" or \"exit\".");
					return;
				}

				if(parts[0].equals(Game.GAME_COMMANDS[0])){
					if(parts.length == 1){
						gameInfo.setText(game.getCompleteCurrentPlaceDescription() + "\n");
					}
					else{
						gameInfo.setText(game.lookAt(parts[1]));
					}
				}
				else if(parts[0].equals(Game.GAME_COMMANDS[1])){
					if (parts.length < 2) 
						gameInfo.setText("Invalid command. Must be \"Action target\" or \"exit\"\n");
					else{
						//TODO
						gameInfo.setText("Searching "+ parts[1] +" for objects\n");
					}
				}
				else if(parts[0].equals(Game.GAME_COMMANDS[2])){
					if (parts.length < 2) 
						gameInfo.setText("Invalid command. Must be \"Action target\" or \"exit\".");
					else{
						gameInfo.setText(game.goTo(parts[1]));
					}
				}
				else if(parts[0].equals(Game.GAME_COMMANDS[3])){
					if (parts.length < 2) 
						gameInfo.setText("Invalid command. Must be \"Action target\" or \"exit\".");
					else{
						gameInfo.setText(game.fightAgainst(parts[1]) + "\n");
					}
				}
				else if(parts[0].equals(Game.GAME_COMMANDS[4])){
					gameInfo.setText("Exiting game...");
					game = null;
					sendCommand.setEnabled(false);
					gameInfo.append("Exited Game");
				}
				else{
					gameInfo.setText(game.getCommands());
				}
				
				gameCommands.setText("");

			}
		});
		
	}
*/
	
	private void lookAction(JButton button){
		
		button.addActionListener(new ActionListener() {
			@SuppressWarnings("static-access")
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				String[] options;
				int selectedOption;
				ArrayList<Monster> monsterList = game.getEnemieList();
				ArrayList<Place> connectionsList = game.getPlacesToGo();
				
				options = new String[monsterList.size() + connectionsList.size() + 1];

				for(int i = 0; i < monsterList.size(); i++){
					options[i] = monsterList.get(i).getName();
				}
				for(int i = 0; i < connectionsList.size(); i++){
					options[monsterList.size() + i] = connectionsList.get(i).getName();
				}
				options[options.length-1] = "Current location";
				
				JOptionPane newPane = new JOptionPane("message");
				selectedOption = newPane.showOptionDialog(
						frame, 
						"What do you want to look at?", "", 
						JOptionPane.DEFAULT_OPTION, JOptionPane.CANCEL_OPTION, 
						null, 
						options, null);
				
				if(selectedOption != JOptionPane.CLOSED_OPTION){
					if(selectedOption == options.length-1){
						gameInfo.setText(adaptTextToWindowSize(game.getCompleteCurrentPlaceDescription()));
					}
					else{
						gameInfo.setText(adaptTextToWindowSize(game.lookAt(options[selectedOption])));
					}
				}
			}
		});
		
	}
	
	private void goAction(JButton button){
		button.addActionListener(new ActionListener() {
			
			@SuppressWarnings("static-access")
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String[] options;
				int selectedOption;
				ArrayList<Place> connectionsList = game.getPlacesToGo();
				
				options = new String[connectionsList.size()];
				
				for(int i = 0; i < connectionsList.size(); i++){
					options[i] = connectionsList.get(i).getName();
				}
				
				JOptionPane newPane = new JOptionPane("message");
				selectedOption = newPane.showOptionDialog(
						frame, 
						"Where do you want to go?", "", 
						JOptionPane.DEFAULT_OPTION, JOptionPane.CANCEL_OPTION, 
						null, 
						options, null);
				
				if(selectedOption != JOptionPane.CLOSED_OPTION){
					gameInfo.setText(adaptTextToWindowSize(game.goTo(options[selectedOption])));
				}
			}
		});
	}
	
	private void fightAction(JButton button){
		button.addActionListener(new ActionListener() {
			
			@SuppressWarnings("static-access")
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String[] options;
				int selectedOption;
				ArrayList<Monster> monsterList = game.getAliveEnemieList();
				
				// If there are no monsters at the players current location...
				if(monsterList.isEmpty()){
					JOptionPane newPane = new JOptionPane("No enemies");
					newPane.showMessageDialog(frame, "There are no (alive) enemies here.");
				}
				else{
					options = new String[monsterList.size()];
					for(int i = 0; i < monsterList.size(); i++){
						options[i] = monsterList.get(i).getName();
					}

					JOptionPane newPane = new JOptionPane("message");
					selectedOption = newPane.showOptionDialog(
							frame, 
							"Choose a target", "", 
							JOptionPane.DEFAULT_OPTION, JOptionPane.CANCEL_OPTION, 
							null, 
							options, null);

					if(selectedOption != JOptionPane.CLOSED_OPTION){
						gameInfo.setText(game.fightAgainst(options[selectedOption]));
					}
				}
			}
		});
	}
		
	private String adaptTextToWindowSize(String originalText){
		
		String newText = originalText;
		
		int position = 0;
		char ch = ' ';
		
		do{
			position = newText.indexOf(ch, position + MIN_TEXT_LENGHT);
			if(position != -1){
				newText = newText.substring(0, position +1) + "\n" + newText.substring(position +1, newText.length());
			}
		}while(position != -1);
			
		newText += "\n";
		
		return newText;
		
	}

}
