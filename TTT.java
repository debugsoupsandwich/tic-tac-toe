  //0 bugs...

  //*To be completed: #1 CPU Player. #2 AI algo.

import java.util.*;

//User Class
class Main{ 
  public String id;
  public static int[] score = {0,0,0};


  //Starts player Id setup
  public void start(){
    //Prompt user to enter a marker as a String.
    Game.getGamePrompt("start");

    //Validate user input
    setId(readUserInput());
  }

  //Sets first user's id.
  void setId(String user_input){
    switch (user_input){
      case "q": Game.quit();
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
    Game.getGamePrompt("invalid");
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
      Game.quit();
    }
        
    else if(Board.boardList.contains(user_input)){
      //Get index of user_input.
      int index = Board.boardList.indexOf(user_input);
      //Gets which id to be placed.
      String element = Game.turn;
      //Replaces current value with user's id.
      Board.boardList.set(index, new String(element));
      //Switch to next ID and count switched.
      Game.alt();
    }
        
    else{
      Game.getGamePrompt("invalid");
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

    //creates scanner object
    Scanner input = new Scanner(System.in);
    //input.getString();
    String reader = input.nextLine();
  
    return reader;
  }

  //This is the "Player Loop". It runs inside of the the "Game Loop" located in the main method. It places markers on the board while checking the game state during each turn.
  public static void loop(){
      //Prompts a user to make a play. 
      Game.getGamePrompt("turn");

      //Get boareadUserInput();
      Board.displayBoard();
      
      //Reads the current user's input and places that ID on the board.
      move(readUserInput());

      //Check the status of game flags.
      Game.getGameStatus();
  }

  //Main calls start method here
  public static void main(String[] args){ 
    
    //Contains memory spaces where address User objects 
    //may be stored. Must be created separately using the //constructor of the User class.
    //Rename Main to User class.
    Main user[] = new Main[2]; 
    user[0] = new Main();
    user[1] = new Main();
    
    /*
    Unsued references
    Game match = new Game();
    */
    
    Board create = new Board();
    
    Main setupUser = new Main();

    //Initializes the static variable called user marker.
    setupUser.start();

    //Scanner reads the first user's input.
    user[0].setId(setupUser.id);
      
    //Second User's ID is set programatically.
    if(user[0].getId() == "o"){
      Game.turn = "o";
      user[1].setId("x");
    }
    else{
      user[1].setId("o");
    }

    //This is the "Game Loop", it calls a seperate Player Loop for each turn and stops when the inner loop finds a change to the state.

    //"Game Loop"
    while(Game.playFlag == true){

      if(Game.turn == "o"){
        
        //"Player Loop"
        Main.loop();
    }
      
      else if(Game.turn == "x"){
        
        //"Player Loop"
        Main.loop();
      }

      //Checks if the Player Loop has expired,adds points, displays game stats and then resets the gameboard.
      if(Game.pointFlag == "win"){

        //Increments score:
        //Adds one to the first element or the second by linking the vals of user objects to specific score array element if that value matches "the flag".
        if(user[0].getId() == Game.lookUpFlag){score[0]+=1;}
        if(user[1].getId() == Game.lookUpFlag){score[1]+=1;}

         //Scoreboard: Displays game stats.
          Main.scoreboard(user[0].getId(), user[1].getId());
        
          //Clears board and automatically starts a rematch.
          create.resetBoard();
      }
      else if(Game.pointFlag == "draw"){
        
        //Increments draw score element:
        //draw method is called when alt method's variable exceeds its condition
        Main.score[2]+=1;
        
        //Scoreboard: Displays game stats.
        Main.scoreboard(user[0].getId(), user[1].getId());
      
        //Clears board and automatically starts a rematch.
        create.resetBoard();
      }
    }
  }
}

class Board{

  //Creates an arryList pre-populated with board spaces.
  public static ArrayList<String> boardList = new ArrayList<String>(Arrays.asList("1","2","3","4","5","6","7","8","9"));

  //Resets the board values step by step:
  public void resetBoard(){
    //Alt tells the game that the board is full, reset to zero.
    Game.alt = 0;

    //pointFlag changes game state and indicates which variable to incrememnt,reset pointFlag to to empty double quotes ("").
    Game.pointFlag ="";

    //Replace element in arraylist while iterating.
    //Count from 0-8 and 1-9, then add each set of numbers to set method's arguments index and element respectively.
    for(int i=0; i<9; i++){
      boardList.set(i, String.valueOf(i+1));
    }
  }

  //displays gameboard spaces 1-9
  public static void displayBoard(){
    System.out.print(
    "\n" + boardList.get(6) + " " + boardList.get(7) + " " + boardList.get(8) +
    "\n" + boardList.get(3) + " " + boardList.get(4) + " " + boardList.get(5) +
    "\n" + boardList.get(0) + " " + boardList.get(1) + " " + boardList.get(2) + "\n"
    );
  }
}

class Game{ 
  public static String turn = "x";
  public static int alt = 0;
  public static String pointFlag ="";
  public static boolean playFlag = true;
  public static String lookUpFlag = "";
  static int index_lowerBound, index_upperBound;
  static HashSet<String> tri = new HashSet<String>();

  static void validator(){
    //If a set of values are identical, HashSet returns 1.
    if(tri.size()==1){
        Game.pointFlag = "win";
        if(tri.contains("x")){Game.lookUpFlag = "x";}
        else if(tri.contains("o")){Game.lookUpFlag = "o";}
      } // validator, if
    else{tri.clear();} // reset validator
    }

  //Alternates "turn flag state" by reassaigning turn a new variable. 
  public static void alt(){
    if(Game.playFlag == true){
      //Counts each time alt is called.
      Game.alt++;
    }
    //Changes the current state of turn.
    switch (Game.turn){
      case "o": Game.turn = "x";
        break;
      case "x": Game.turn = "o";
        break;
    } 
  }

  static void hLookUp(){

    //outer loop
    for(int n=1; n<=3; n++){
    
      //Creates the values at which inner loop stops.
      index_upperBound = n*3;
    
      //Creates the starting values of the inner loop.
      index_lowerBound = index_upperBound-3;

      //inner loop reads values in triplets
      for(; index_lowerBound < index_upperBound; index_lowerBound++){
        
        String set = Board.boardList.get(index_lowerBound);
        tri.add(set);
      } // inner
      validator();
    } // outer
  }

  static void vLookUp(){

    for(int n=1; n<=3; n++){
      
      index_upperBound = 6+n;

      index_lowerBound = index_upperBound - 7;
    
      //inner loop reads values in triplets
      for(; index_lowerBound<index_upperBound; index_lowerBound+=3){
        
        String set = Board.boardList.get(index_lowerBound);
        tri.add(set);
      } // inner
      validator();
    } //outer 
  }

  //programatically checks board for diagonal patterns.
  static void dLookUp(){

    for(int n=0; n<3; n+=2){
      
      index_upperBound = 10 - n;

      index_lowerBound = 0 + n;
    
      //inner loop reads values in triplets
      for(; index_lowerBound < index_upperBound; index_lowerBound+=4 - n){
        
        String set = Board.boardList.get(index_lowerBound);
        tri.add(set);
      } // inner
      validator();
    } //outer 
  }

  //declarePoint method calls one of two methods which notify user of a win, increment score and display scoreboard. The pointFlag is set by getGameStatus method.
  public static void declarePoint(){
    switch (Game.pointFlag){
      //Increments a users score based on turn.
      case "win": win();
        break;
      //Adds a point to draw on scoreboard if no one scored within nine moves.
      case "draw": draw();
        break;
    }
  }

  //Validates a game state and calls another prompt method:
  //Monitors the number of moves without a win, checks game conditions against a set of four lookUp methods and ultimately sets pointFlag as draw unless lookUp 
  //methods find a valid win, in which case the repective lookUp method will set pointFlag to "win." 
  public static void getGameStatus(){
  
    //lookUp methods check for patterns in boardList state and if valid sets pointFlag to "win." 
    //Watch alt counter, when it reaches nine, i.e. board.list spaces are full of user markers, change flag to draw, which will trigger draw method.
    hLookUp();
    vLookUp();
    dLookUp();
    draw();
    Game.declarePoint();
   }
    
  //Displays who won by associating a winning play with the last play "Game.turn" and then shows the board.
  public static void win(){
    Game.getGamePrompt("winner");
    Board.displayBoard();
  }

  //Utilizes getGameStatus method to notify users of a draw and show the board.
  public static void draw(){
    //Watch alt counter, when it reaches nine, i.e. board.list spaces are full of user markers, change flag to draw, which will trigger draw method.
    if(Game.alt == 9){
      Game.pointFlag = "draw";
      Game.getGamePrompt("draw");
      Board.displayBoard();
    }
  }

  //Type q at anytime to end the game.
  public static void quit(){
    Game.getGamePrompt("q");
    System.exit(0);
  }

  //Displays all posible game state messages
  public static void getGamePrompt(String gameState){
    String gameDirections;
      switch (gameState){
        case "start": gameDirections = "\nChoose an x or o.\n \nYou can type q at anytime to quit the game.\n";
          break;

        case "turn": gameDirections = "\nPlayer " + Game.turn + ", select a space from 1-9.";
          break;

        case "invalid": gameDirections = "\nYour input is not valid! LoL :P\n";
          break;

        case "winner": gameDirections = "\n" + Game.lookUpFlag + 
        " wins!\n";
          break;
        
        case "draw": gameDirections = "\nDraw game!\n";
          break;

        case "q": gameDirections = "\nThanks for playing. Goodbye!\n";
          break;

        default:
          gameDirections = "\nGet Ready For The Next Battle!\n";
          break;
    }
      System.out.println(gameDirections);
  }
}



