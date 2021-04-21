package tictactoe;

import java.util.ArrayList;
import java.util.Arrays;

public class Board{

  //Creates an arryList pre-populated with board spaces.
  public static ArrayList<String> boardList = new ArrayList<String>(Arrays.asList("1","2","3","4","5","6","7","8","9"));

  //Resets the board values step by step:
  public void resetBoard(){
    //Alt tells the game that the board is full, reset to zero.
    RuleSet.alt = 0;

    //pointFlag changes game state and indicates which variable to incrememnt,reset pointFlag to to empty double quotes ("").
    RuleSet.pointFlag ="";

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