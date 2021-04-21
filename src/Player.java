package tictactoe;

import java.util.Scanner;

//Player Class: A player just refers to a user of this program. 
//This class maps user I/O to a player and 
public class Player{ 
  public String id;
  public static int[] score = {0,0,0};

  //Starts player Id setup
  public void start(){
    //Prompt user to enter a marker as a String.
    RuleSet.getGamePrompt("start");

    //Validate user input
    setId(readUserInput());
  }

  //Sets first user's id.
  void setId(String user_input){
    switch (user_input){
      case "q": RuleSet.quit();
        break;
      case "x": this.id = "x";
        break;
      case "o": this.id = "o";
        break;
      //Any invalid input routed to loop 
      default: setUpLoop(); 
    }
  }

  //Invalid loops back to start method
  public void setUpLoop(){
    RuleSet.getGamePrompt("invalid");
    start();
  }

  //Displays id  
  public String getId(){
    return this.id;
  }

  //Sets the state of gameboard spaces 1-9
  public static void move(String user_input){
    //to quit
    if(user_input.equals("q")){
      RuleSet.quit();
    }
        
    else if(Board.boardList.contains(user_input)){
      //Get index of user_input.
      int index = Board.boardList.indexOf(user_input);
      //Gets which id to be placed.
      String element = RuleSet.turn;
      //Replaces current value with user's id.
      Board.boardList.set(index, new String(element));
      //Switch to next ID and count switched.
      RuleSet.alt();
    }
        
    else{
      RuleSet.getGamePrompt("invalid");
    }
  }

  //Scoreboard display
  static void scoreboard(String i, String j){
    System.out.println(
        "\n==========\n"
      + "scoreboard"
      + "\n " + i + i + i + " = " + score[0]
      + "\n "
      + j + j + j + " = " + score[1]
      + "\n"
      + "draw" + " = " + score[2]
      + "\n==========\n");
  }

  //Reads user input.
  static String readUserInput(){

    //creates scanner object:
    //resource leak - snanner is never closed
    Scanner input = new Scanner(System.in);
    //input.getString();
    String reader = input.nextLine();

    /*
    //close the scanner:
    error - Exception in thread "main" java.util.NoSuchElementException: No line found
        at java.base/java.util.Scanner.nextLine(Scanner.java:1651)
        at Player.readUserInput(TTT.java:90)
        at Player.loop(TTT.java:108)
        at Player.main(TTT.java:163)
    input.close();
    */
  
    return reader;
  }

  //This is the "Player Loop". It runs inside of the the "RuleSet Loop" located in the main method. 
  //It places markers on the board while checking the game state during each turn.
  public static void loop(){
      //Prompts a user to make a play. 
      RuleSet.getGamePrompt("turn");

      //Get boareadUserInput();
      Board.displayBoard();
      
      //Reads the current user's input and places that ID on the board.
      move(readUserInput());

      //Check the status of game flags.
      RuleSet.getGameStatus();
  }

  //Main calls start method here
  public static void main(String[] args){ 
    
    //Contains memory spaces where address User objects 
    //may be stored. Must be created separately using the 
    //constructor of the Player class.
    //Rename Main to Player  class.
    Player user[] = new Player[2]; 
    user[0] = new Player();
    user[1] = new Player();
    
    /*
    Unsued reference
    RuleSet match = new RuleSet();
    */
    
    Board create = new Board();
    
    Player setupUser = new Player();

    

    //Initializes the static variable called user marker.
    setupUser.start();

    //Scanner reads the first user's input.
    user[0].setId(setupUser.id);
      
    //Second User's ID is set programatically.
    if(user[0].getId() == "o"){
      RuleSet.turn = "o";
      user[1].setId("x");
    }
    else{
      user[1].setId("o");
    }

    //This is the "RuleSet Loop", it calls a seperate Player Loop for each turn and stops when the inner loop finds a change to the state.

    //"RuleSet Loop"
    while(RuleSet.playFlag == true){

      if(RuleSet.turn == "o"){
        
        //"Player Loop"
        Player.loop();
    }
      
      else if(RuleSet.turn == "x"){
        
        //"Player Loop"
        Player.loop();
      }

      //Checks if the Player Loop has expired,adds points, displays game stats and then resets the gameboard.
      if(RuleSet.pointFlag == "win"){

        //Increments score:
        //Adds one to the first element or the second by linking the vals of user objects to specific score array element if that value matches "the flag".
        if(user[0].getId() == RuleSet.lookUpFlag){score[0]+=1;}
        if(user[1].getId() == RuleSet.lookUpFlag){score[1]+=1;}

         //Scoreboard: Displays game stats.
          Player.scoreboard(user[0].getId(), user[1].getId());
        
          //Clears board and automatically starts a rematch.
          create.resetBoard();
      }
      else if(RuleSet.pointFlag == "draw"){
        
        //Increments draw score element:
        //draw method is called when alt method's variable exceeds its condition
        Player.score[2]+=1;
        
        //Scoreboard: Displays game stats.
        Player.scoreboard(user[0].getId(), user[1].getId());
      
        //Clears board and automatically starts a rematch.
        create.resetBoard();
      }
    }
  }
}