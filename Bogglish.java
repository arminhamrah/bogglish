/*Armin Hamrah
KMo
AP Computer Science
24 January 2022*/
import java.util.ArrayList;
import acm.program.*;
public class Bogglish extends ConsoleProgram
{
    private static final String ALPHABET = "ABCDEFGHIJKLMNOPRSTUVWXYZ";
    public void run()
    {
        //Fill up board
        setFont("*-*-24");
        String[][] board = new String[5][5];
        String letters = ALPHABET;
        for (int r=0; r<board.length; r++)
        {
            for (int c=0; c<board[0].length; c++)
            {
                int index = (int)(Math.random()*letters.length());
                board[r][c] = letters.substring(index, index+1);
                letters = letters.substring(0, index) + letters.substring(index+1);
            }
        }
        printBoard(board);
        boolean isPlay = true;
        while (isPlay)
        {
            String word = readLine("Enter a word please");
            if (word.equals(""))
            {
                isPlay = false;
            }
            else if (checkWord(word, board))
            {
                println("Nice job!");
            }
            else
            {
                println("Nice try");
            }
        }
        printAllWords(board);
    }

    public void printBoard(String[][] board)
    {
        for (int r=0; r<board.length; r++)
        {
            for (int c=0; c<board[0].length; c++)
            {
                print(board[r][c]);
            }
            println();
        }
    }

    /** Returns true if the word is "contained" somewhere in the board,
     *  following our "Bogglish" rules, false otherwise.
     *  @param word the word to check
     *  @param board the Bogglish board
     *  @return true if the word is in the board, false otherwise.
     */
    private boolean checkWord(String word, String[][] board)
    {
        word = word.toUpperCase();
        if (getLetterPosition(word.substring(0,1), board) == null)
        {
            return false;
        }
        for (int i=0; i<word.length()-1; i++)
        {
            Position firstPosition = getLetterPosition(word.substring(i, i+1), board);
            Position secondPosition = getLetterPosition(word.substring(i+1, i+2), board);
            if(firstPosition == null || secondPosition == null)
            {
                return false;
            }
            if (!(isAdjacent(firstPosition, secondPosition)))
            {
                return false;
            }
        }
        return true;
    }

    public boolean isAdjacent(Position position1, Position position2)
    {
        int row1 = position1.getRow();
        int col1 = position1.getCol();
        int row2 = position2.getRow();
        int col2 = position2.getCol();
        if (Math.abs(row1-row2)<=1 && Math.abs(col1-col2)<=1)
        {
            return true;
        }
        return false;
    }

    /** Returns the Position of where the letter is in the board
     *  Note that this returns a Position, which is a class you wrote.
     *  @param letter a String of length 1 representing the letter
     *  @param board the Bogglish board
     *  @return the Position of the letter, or null if it is not there 
     */
    private Position getLetterPosition(String letter, String[][] board)
    {
        for (int r=0; r<board.length; r++)
        {
            for (int c=0; c<board[0].length; c++)
            {
                if (board[r][c].equals(letter))
                {
                    return new Position(r, c);
                }
            }
        }
        return null;
    }

    public void printAllWords (String[][] board)
    {
        ArrayList<String> allWords = Dictionary.getAllWords();
        for (String word : allWords)
        {
            if (checkWord(word, board))
                println(word);
        }
    }
}