import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Crosswordpuzzle {
	
	static boolean no_sol = false;
	static boolean done_flag = true;
	public static void display(String[][] dis){
		if(no_sol){
			System.out.println("No Solution is possible");
		}
		else {
        for(int i = 0; i < dis.length; i++){
            for(int j = 0; j < dis[i].length; j++){
                System.out.print(dis[i][j]);
            }
            System.out.println();
        }
	 }
    }

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in); 
        String[][] puzzlegrid = new String[10][10];
        System.out.println("Please enter the 10*10 grid:");
        for(int i = 0; i < 10; i++){
        	puzzlegrid[i] = input.nextLine().split("");
        }
        System.out.println("Please enter the number of words:");
        String dummy = input.nextLine();
        dummy.trim();
        System.out.println("Please enter the list of words with ';' after each word:");
        String[] words = input.nextLine().split(";");
        //for(int sh=0;sh<words.length;sh++) {
        //List<String> wordList = Arrays.asList(words); 
        //Collections.shuffle(wordList);
        //String[] w = wordList.toArray(new String[0]);
        //for(int jk=0; jk<w.length;jk++) {
        //        System.out.println(w[jk]);}
        for(int i = 0; i < words.length; i++){
            no_sol = false;
        	if(crosswordpuzzle(puzzlegrid, words, words[i], 0, 0)){
        		break; 
        	}
        }
        display(puzzlegrid);
    }
    
    

    public static boolean crosswordpuzzle(String[][] puzzlegrid, String[] words, String word, int row, int col){
      no_sol = false;
      // loop to find the no solution
      for(int k=0;k<10;k++) {
    	  for(int l=0;l<10;l++) {
    		  if(puzzlegrid[k][l].equals("1")) {
    			  done_flag = false;
    			  break;
    		  }
    	  }
    		  
      }
    
      // if column value is greater than grid length, go to the next row
      if(col >= puzzlegrid.length){
            col = 0;
            row += 1;
        }

      // if row number is less than grid length (no. of rows in grid) and also the element is not 1, go to next column in the
      // same row until the last column and then increase the row...happens until it finds element 1
      while(row < puzzlegrid.length && !puzzlegrid[row][col].equals("1")){
            col += 1;
            if(col >= puzzlegrid.length){
                col = 0;
                row += 1;
            }
        }
      	
      	// after reaching last row, return false
        if(row == puzzlegrid.length){
          return false;
        }

        boolean checkhoz = false, checkver = false;
        // when there is a previous row and the element in the previous row above the current column is not a '.', go to previous row same col
        while(row - 1 >= 0 && !puzzlegrid[row - 1][col].equals(".")){
          row -= 1;
        }
        
        // place word vertically until done or not possible
        checkver = Verticalposition(puzzlegrid, row, col, word);
        // only is vertically no match found, horizontal match is tried
        if(!checkver){
            //when there is a previous column and element in previous column left to same row is not a '.', go to previous col same row
        	while(col - 1 >= 0 && !puzzlegrid[row][col - 1].equals(".")){
              col -= 1;
            }
          // place word horizontally until done or not possible
        	checkhoz = Horizontalposition(puzzlegrid, row, col, word);
        }
        if( checkver || checkhoz){
          String[] rest = new String[words.length - 1];
          if(rest.length == 0){
                return true;
            }

          int i = 0, j = 0;
            while(i < words.length){
              if(!words[i].equals(word)){
            	  rest[j] = words[i];
                  j++;
              }
              i++;
            }

            String previousword = word;
          for(int k = 0; k < rest.length; k++){
            word = rest[k];
              if(crosswordpuzzle(puzzlegrid, rest, word, row, col + 1)){
            	   return true;
                }
            }
          if(checkhoz){
            //clearHorizontal(grid, row, col, previousword);
          }
          if(checkver){
            //clearVertical(grid, row, col, previousword);
          }
        }
        if(!done_flag) {
        	no_sol = true;
        }
        return false;
    }

    public static boolean Horizontalposition(String[][] puzzlegrid, int row, int col, String word){
        if(word.length() == 0 && (col == puzzlegrid.length || puzzlegrid[row][col].equals("."))){
          return true;
        }else if(col == puzzlegrid.length || word.length() == 0){
            return false;
        }

        if(puzzlegrid[row][col].equals("1") || puzzlegrid[row][col].equals(word.charAt(0)+"")){
          String prev = puzzlegrid[row][col];
          puzzlegrid[row][col] = word.charAt(0) + "";
            if(Horizontalposition(puzzlegrid, row, col + 1, word.substring(1))){
                return true;
            }
            puzzlegrid[row][col] = prev;
        }
        return false;
    }

    public static boolean Verticalposition(String[][] puzzlegrid, int row, int col,String word){
        //checks if word length is zero and also either its last row OR the element is a '.'
    	if(word.length() == 0 && (row == puzzlegrid.length || puzzlegrid[row][col].equals("."))){
            return true;
        } else if(row == puzzlegrid.length || word.length() == 0){
            return false;
        }
        //if the element is 1 or if its a letter of another word, see if its the first character of the word we are looking for
        if(puzzlegrid[row][col].equals("1") || puzzlegrid[row][col].equals(word.charAt(0)+"")){
        	// store the current element as previous
        	String previous = puzzlegrid[row][col];
            // the current element is replaced with the word's first character
        	puzzlegrid[row][col] = word.charAt(0) + "";
            //recursive call-the word is sent except for first character until end
        	if(Verticalposition(puzzlegrid, row + 1, col, word.substring(1))){
                return true;
            }
        	puzzlegrid[row][col] = previous;
        }
        return false;
    }

    public static void clearHorizontal(String[][] puzzlegrid, int row, int col, String word){
        int pcol = col;
      while(pcol < puzzlegrid.length && pcol < col + word.length()){
    	  puzzlegrid[row][pcol] = "1";
            pcol++;
        }
    }

    public static void clearVertical(String[][] puzzlegrid, int row, int col, String word){
        int prow = row;
      while(prow < puzzlegrid.length && prow < row + word.length()){
    	  puzzlegrid[prow][col] = "1";
            prow++;
        }
    }
	
	
	

}
