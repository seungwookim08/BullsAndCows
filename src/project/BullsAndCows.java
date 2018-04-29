package projectBullsAndCows;

import javax.swing.*;
import javax.swing.text.DefaultCaret;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.concurrent.TimeUnit;

class BullsAndCows extends JFrame implements ActionListener {

    private int cows, bulls, target, userInput, randomVar;
    private int turnleft,difficultyInt ;
    private long startTime, endTime;
    private int[] diffArray = {12,10,7};
    private String[] diffString = {"Easy","Normal","Hard"};
    private String digit, targetStr, difficultyInput;
    private static final String NL = System.getProperty("line.separator");
    private JMenuBar menuBar;
    private JMenu fileMenu;
    private JMenu helpMenu;
    private JTextArea textArea;
    private JScrollPane scrollPane;
    private JTextField inputLine;
    private JButton guess, resign, restartGame, instruction;
    private JLabel textAreaLabel;
    private JLabel inputLabel;
    private Container frame;
    private int i = -1;
    public static void main(String args[]) {
        BullsAndCows a = new BullsAndCows();
        a.setVisible(true);
    }

    public void actionPerformed(ActionEvent event) {
        if (event.getSource() instanceof JButton) {
            JButton eventButton = (JButton) event.getSource();
            if (eventButton.getText() == "Guess") {
//              if(inputLine.getText()!=null)
                //      b=Integer.parseInt(s);)
                guess();
            }
                
            else if (eventButton.getText() == "New Game")
            	newGame();
            else if (eventButton.getText() == "Resign")
                resign();
            else if (eventButton.getText() == "Help")
            	helpInstruction();
        }
        if (event.getSource() instanceof JTextField) {
            //      if(inputLine.getText()!=null)
            guess();
        }
        if (event.getSource() instanceof JMenuItem) {
            String command = event.getActionCommand();

            if (command == "New Game")
            	newGame();
            else if (command == "Exit")
                System.exit(0);
            else if (command == "About")
                JOptionPane.showMessageDialog(null, "Made by Seungwoo Kim, visit \nseungwoo-kim.firebaseapp.com\ngithub/seungwookim08");
            else if (command == "Instruction")
                helpInstruction();
        }
    }
    
	private void guess() {
    	if (i == -1) {
    		Random gen = new Random();
    		do {
    			targetStr ="";
    			target = 0;
    			digit = inputLine.getText();
    			if (digit.equals("2") || digit.equals("3") || digit.equals("4")) {
    				randomVar = (int) (9*Math.pow(10,(Integer.parseInt(digit)-1)));
    				while(hasDupes(target= (gen.nextInt(randomVar) + (int) (Math.pow(10,(Integer.parseInt(digit)-1))))));
    	            inputLine.setText("");
    				textArea.append("Your input for numbers of digits are curent!"+NL);
    				textArea.append("Please choose the difficulty. Enter a number (1:Easy, 2:Normal, 3:Hard)" + NL);
    				i++;
    	    		targetStr = target +"";
    				guess();
    				break;
    			}    			
    			else {
    				resign();
    				break;
    			}
    		}while(true);
    	}
    	else if(i==0) {
    		difficultyInput ="";
    		textArea.append(difficultyInput + NL);
        	do {
        		try {
        			difficultyInput = inputLine.getText();
    				if (difficultyInput.equals("1")|| difficultyInput.equals("2") || difficultyInput.equals("3")) {
    					difficultyInt = Integer.parseInt(difficultyInput)-1;
    					if (digit.equals("2")) {
    						turnleft = diffArray[difficultyInt]-3;
    					}
    					else if(digit.equals("3")) {
    						turnleft = diffArray[difficultyInt]-1;
    					}
    					else {
    						turnleft = diffArray[difficultyInt];
    					}
    				}
    				else {
    					continue;
    				}
    				textArea.append("You have choose " + diffString[difficultyInt] + " level so you have " + turnleft + " turns left" +NL + "Remaining turn is determined by number of digits and difficulty "+ NL);
    	    		textArea.append("Guess a "+ digit + "-digit number with no duplicate digits: " + NL);
    	            inputLine.setText("");
    	    		i++;
        		} catch(InputMismatchException e){
    				textArea.append("You have entered non-numerical input "+ NL);
    				continue;
    			}
        	}while(false);
        	

    	}

    	else {
    		bulls = 0;
    		cows = 0;
    		do {
    			try{
    				userInput = Integer.parseInt(inputLine.getText());
    				if(hasDupes(userInput) || userInput < Math.pow(10,(Integer.parseInt(digit)-1))||userInput >= Math.pow(10,(Integer.parseInt(digit)))) {
    					textArea.append("Your input must be " + digit + " digit numbers! and no duplication of numbers!" + NL);
        	            inputLine.setText("");
    					continue;
    				}
    				String guessStr = userInput + "";
    	    		for(int i= 0;i < Integer.parseInt(digit);i++){
    	    			if(guessStr.charAt(i) == targetStr.charAt(i)){
    	    				bulls++;
    	    			}else if(targetStr.contains(guessStr.charAt(i)+"")){
    	    				cows++;
    	    			}
    	    		}
	    			if(bulls == Integer.parseInt(digit)){
	    				textArea.append("Congratulations! You won! " + targetStr + " was exactly matched!"+ NL);
	    				endTime = System.currentTimeMillis();
	    				long totalTime = TimeUnit.MILLISECONDS.toSeconds(endTime - startTime);
	    				textArea.append("It takes "+ totalTime + " seconds!" + NL);
	    				textArea.append("If you want to start a new game, Press button <<New Game>>");
	    				
	    			}else if (turnleft==0) {
	    				textArea.append("Your remaining turn is 0. You lose :(" + NL);
	    				endTime = System.currentTimeMillis();
	    				long totalTime = TimeUnit.MILLISECONDS.toSeconds(endTime - startTime);
	    				textArea.append("It takes "+ totalTime + " seconds!" + NL);
	    				textArea.append("The number was " + targetStr + NL);
	    			}	    			
	    			else{
	    				turnleft--;
	    				textArea.append("input :" + userInput + ",    "+cows+" Cows     "+bulls+" Bulls.    Turn left : " + turnleft + NL);
	    	    		textArea.append("Try again! guess a "+ digit + "-digit number with no duplicate digits: " + NL);
	    	    		if (turnleft==0) {
	    	    			textArea.append("This is your last turn! Good luck!");
	    	    		}
	    	            inputLine.setText("");
	    				i++;
	    			}
    			}catch(InputMismatchException e){
    				textArea.append("You have entered non-numerical input "+ NL);
    				continue;
    			}
    		}while(false);    				
    	}
	}
    
    private void newGame() {
        startTime = System.currentTimeMillis();
        inputLine.setText("");
        textArea.setText("");
        textArea.append("NEW GAME HAS STARTED! "+ NL);
        textArea.append("Please enter how many digits you want to have? (range between 2-4);"+NL+"otherwise, dealt as resigned"+NL);
        textArea.append(NL);
        i=-1;
    }
	public static boolean hasDupes(int num){
		boolean[] digitFac = new boolean[10];
		while(num > 0){
			if(digitFac[num%10]) return true;
			digitFac[num%10] = true;
			num/= 10;
		}
		return false;
	}

    private void resign() {
        startTime = System.currentTimeMillis();
        textArea.append("You resigned." + NL);
        if (target != 0) {
            textArea.append("The number was : " + target + NL);
        }
        textArea.append(NL);
		textArea.append("Please enter how many digits you want to have? (range between 2-4);"+NL+"otherwise, dealt as resigned"+NL);
        inputLine.setText("");
        i = -1;
    }
    
    private void helpInstruction() {
		textArea.setText("");
        inputLine.setText("");
        textArea.append("The digits must be all different. Then, in turn, "+NL+"the player tries to guess what is screat number given from computer to be matched"+NL+
        		" If the matching digits are in their right positions, they are \"bulls\","+NL+" if in different positions, they are \"cows\". Example:"+NL+"Secret number: 4271"+
        		NL+"Opponent\'s try: 1234"+NL+"Answer: 1 bull and 2 cows. (The bull is \"2\", the cows are \"4\" and \"1\".)" + NL);
        textArea.append(NL+"Please click New Game button to start a game!");
	}
    
    public BullsAndCows() {
        setSize(500, 610);
        setLocation(100, 100);
        setTitle("Cows and Bulls Game");

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        frame = getContentPane();
        frame.setLayout(null);

        menuBar = new JMenuBar();
        createFileMenu();
        createHelpMenu();
        setJMenuBar(menuBar);
        createTextArea();
        createInputLine();
        createButtons();
        createLabels();
        
        startTime = System.currentTimeMillis();
		textArea.append("Please enter how many digits you want to have? (range between 2-4);"+NL+"otherwise, dealt as resigned"+NL);
        textArea.append(NL);
    }


    private void createButtons() {
        guess = new JButton("Guess");
        guess.setBounds(10, 500, 100, 40);
        frame.add(guess);
        guess.addActionListener(this);

        resign = new JButton("Resign");
        resign.setBounds(135, 500, 100, 40);
        frame.add(resign);
        resign.addActionListener(this);


        restartGame = new JButton("New Game");
        restartGame.setBounds(255, 500, 100, 40);
        frame.add(restartGame);
        restartGame.addActionListener(this);
        
        instruction = new JButton("Help");
        instruction.setBounds(375, 500, 100, 40);
        frame.add(instruction);
        instruction.addActionListener(this);
    }
    
    private void createLabels() {
        textAreaLabel = new JLabel("Console log box");  
        textAreaLabel.setBounds(10,10, 100,30);  
        inputLabel=new JLabel("Input : (You can enter to guess)");  
        inputLabel.setBounds(10,400, 400,30); 
        frame.add(textAreaLabel);
        frame.add(inputLabel);
    }
    private void createInputLine() {
        inputLine = new JTextField();
        inputLine.setBounds(10, 440, 470, 40);
        frame.add(inputLine);
        inputLine.addActionListener(this);
    }


    private void createTextArea() {
        textArea = new JTextArea();
        DefaultCaret caret = (DefaultCaret)textArea.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
        textArea.setBounds(10, 40, 470, 350);
        textArea.setBorder(BorderFactory.createLineBorder(Color.red));
        textArea.setEditable(false);
        scrollPane = new JScrollPane(textArea);
        scrollPane.setBounds(10, 40, 470, 350);
        frame.add(scrollPane);
    }

    private void createFileMenu() {
        fileMenu = new JMenu("File");

        JMenuItem item;

        item = new JMenuItem("New Game");
        item.addActionListener(this);
        fileMenu.add(item);

        fileMenu.addSeparator();

        item = new JMenuItem("Exit");
        item.addActionListener(this);
        fileMenu.add(item);

        menuBar.add(fileMenu);
    }

    private void createHelpMenu() {
        helpMenu = new JMenu("Help");
        JMenuItem item;
        item = new JMenuItem("Instruction");
        item.addActionListener(this);
        helpMenu.add(item);
        
        item = new JMenuItem("About");
        item.addActionListener(this);
        helpMenu.add(item);

        menuBar.add(helpMenu);
    }
}