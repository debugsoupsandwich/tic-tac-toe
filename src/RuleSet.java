package tictactoe;

import java.util.*;

public class RuleSet{ 
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
          RuleSet.pointFlag = "win";
          if(tri.contains("x")){RuleSet.lookUpFlag = "x";}
          else if(tri.contains("o")){RuleSet.lookUpFlag = "o";}
        } // validator, if
      else{tri.clear();} // resets validator
      }
  
    //Alternates "turn flag state" by reassaigning turn a new variable. 
    public static void alt(){
      if(RuleSet.playFlag == true){
        //Counts each time alt is called.
        RuleSet.alt++;
      }
      //Changes the current state of turn.
      switch (RuleSet.turn){
        case "o": RuleSet.turn = "x";
          break;
        case "x": RuleSet.turn = "o";
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
      switch (RuleSet.pointFlag){
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
      RuleSet.declarePoint();
     }
      
    //Displays who won by associating a winning play with the last play "Game.turn" and then shows the board.
    public static void win(){
      RuleSet.getGamePrompt("winner");
      Board.displayBoard();
    }
  
    //Utilizes getGameStatus method to notify users of a draw and show the board.
    public static void draw(){
      //Watch alt counter, when it reaches nine, i.e. board.list spaces are full of user markers, change flag to draw, which will trigger draw method.
      if(RuleSet.alt == 9){
        RuleSet.pointFlag = "draw";
        RuleSet.getGamePrompt("draw");
        Board.displayBoard();
      }
    }
  
    //Type q at anytime to end the game.
    public static void quit(){
      RuleSet.getGamePrompt("q");
      System.exit(0);
    }
  
    //Displays all posible game state messages
    public static void getGamePrompt(String gameState){
      String gameDirections;
        switch (gameState){
          case "start": gameDirections = "\nChoose an x or o.\n \nYou can type q at anytime to quit the game.\n";
            break;
  
          case "turn": gameDirections = "\nPlayer " + RuleSet.turn + ", select a space from 1-9.";
            break;
  
          case "invalid": gameDirections = "\nYour input is not valid! LoL :P\n";
            break;
  
          case "winner": gameDirections = "\n" + RuleSet.lookUpFlag + 
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