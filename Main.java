import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class Main implements  ActionListener{
    JFrame frame = new JFrame();
    JPanel titlePanel = new JPanel();
    JPanel buttonPanel = new JPanel();
    JLabel textField = new JLabel();//for titlePanel
    JButton[] buttons = new JButton[9];
    // only allow user to press button during askHuman()
    public static String[][] board;
    public static String[] endgameArray = new String[2];
    // create one scanner and use as opening and closing new scanners spawns Exceptions
    public Scanner scann = new Scanner(System.in);

    // assume com always plays 1st
    Main() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800,800);
        frame.getContentPane().setBackground(new Color(50, 50, 50));
        frame.setLayout(new BorderLayout());
        frame.setVisible(true);
        
        textField.setBackground(new Color(25, 25 ,25));
        textField.setForeground(new Color(25, 255 ,0));
        textField.setFont(new Font("Ink Free", Font.BOLD,75));
        textField.setHorizontalAlignment(JLabel.CENTER);
        textField.setText("Tic Tac Toe");
        textField.setOpaque(true);
        
        titlePanel.setLayout(new BorderLayout());
        //title in top left corner. (x coord, y coord, length ,height)
        titlePanel.setBounds(0,0,800,100);
        buttonPanel.setLayout(new GridLayout(3, 3));
        buttonPanel.setBackground(new Color(150, 150 ,150));        

        for (int i = 0; i < 9; i++) {
            //array is used to keep track of who wins/tie?
            buttons[i] = new JButton();  
            buttonPanel.add(buttons[i]);
            buttons[i].setFont(new Font("MV Boli", Font.BOLD, 120));//last num is font size
            buttons[i].setFocusable(false);
            buttons[i].addActionListener(this);
        }
        titlePanel.add(textField);
        frame.add(titlePanel, BorderLayout.NORTH);
        frame.add(buttonPanel);
        


        boardCreator();
        while(endgameCheck(board)[0] == "false"){
            String moveByCom = minimaxMove(board, "X");
            System.out.println("The moveByCom: " + moveByCom);
            String[][] newBoard = deepCopy(board);
            
            board = changeBoard(newBoard, moveByCom, "X");
            prettyPrint(board);
            if (endgameCheck(board)[0] == "true"){
                System.out.println("game over!");
                break;
            }
            // ask human
            askHuman();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e){

    }
    public int minimax(String[][] board, String player){
        /* the function checks every possible move and applies it in all the permutations
         It finds the score for each of the permutations and adds it to a scores list
         this scores list refreshes whenever a new positon is called
         then, depending on who called it, it returns min/max score. */
        if(endgameCheck(board)[0] == "true"){
            System.out.println("endgame reached...");
            // recursion exit case
            switch (endgameCheck(board)[1]){
                case "X":
                    return 10;
                case "O":
                    return -10;
                case "tie":
                    return 0;
            }
        }
        // get all the legal moves for the current player
        ArrayList<String> legalMoves = getLegalMoves(player, board);
        System.out.println("legalMoves: "+ legalMoves);
        ArrayList<Integer> scores = new ArrayList<>();
        for(String move : legalMoves){
            // if the game has ended, dont do another move
            if (endgameCheck(board)[0] == "true") {
                System.out.println("stopped from moving due to endgame");
                break;
            }
            System.out.println("making another move: "+ move);
            // change the board
            String[][] newBoard = deepCopy(board);
            newBoard = changeBoard(newBoard, move, player);
            prettyPrint(newBoard);
            // need to know opponent as need to know whose turn next time minimax is called
            String opponent = getOpponent(player);
            int score = minimax(newBoard,opponent);
            scores.add(score);
            // hashtable is a global var
        }
        System.out.println("scores arraylist: "+scores);
        if (player == "X"){
            return Collections.max(scores);
        } else {
            return Collections.min(scores);
        }
    }

    public String minimaxMove(String[][] board, String player){
        /* for current state of the board, it checks every legal move and then
        finds score of each move. Overall, it will return a move with highest score from the legal moves*/
        String bestMove = "";
        int bestScore = -2; //-2 is random starter value

        //for every legal move of player X, check the score that player O can get in response
        ArrayList<String> legalMoves = getLegalMoves(player, board);
        for (String move: legalMoves){
            if( endgameCheck(board)[0] == "true"){
                break;
            }
            String[][] newBoard = deepCopy(board);
            newBoard = changeBoard(newBoard, move, player);
            String opp = getOpponent(player);
            int score = minimax(newBoard, opp);
            // only use the maximum score as X wants to maximise score
            if(score>bestScore || bestScore == -2){
                bestMove = move;
                bestScore = score; 
            }
        }
        System.out.println("bestMove: "+bestMove);
        prettyPrint(board);
        return bestMove;

    }

    public void askHuman() {
        String choicePos = "";
        boolean whetherInt = true;
        int rowPosition = 0;
        int colPosition = 0;
        do{
            System.out.println("You can choose one of the blank spaces, choose the row and column.");
            System.out.println("Example if you want to choose the middle spot, write 11.");
            choicePos = scann.nextLine();
            System.out.println("Your move is: " + choicePos);
            // keep asking until player inputs right format(is a number and 2 digits)
            try{
                rowPosition = Integer.parseInt(choicePos.substring(0, 1));
                colPosition = Integer.parseInt(choicePos.substring(1));
                System.out.println("rowPosition "+rowPosition);
                System.out.println("colPosition "+colPosition);
                
            // to catch the exception if it is not a num
            } catch (Exception e){
                System.out.println("Enter a number of length 2!");
                whetherInt = false;
            }
        // keep asking until it is a number and length 2 and within range
        }while(whetherInt && choicePos.length()!=2 && ((colPosition < 3 && rowPosition < 3) && (colPosition >=0 && rowPosition >=0)));
        changeBoard(board, choicePos, "O");
    }

    public ArrayList<String> getLegalMoves(String playerChar, String[][] board) {
        ArrayList<String> move_ArrayList = new ArrayList<>();
        // if there is a blank, record it
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if(board[i][j].equals(" ")){
                    // convert to String b4 concat
                    String index = ""+ i+ j;
                    move_ArrayList.add(index);
                }
            }
        }
        return move_ArrayList;
    }

    // 1. make a board
    public String[][] boardCreator(){
        board = new String[3][3];
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
               board[i][j] = " "; 
            }
        } 
        return board;
    }

    public String getOpponent(String player){
        if (player == "X"){
            return "O";
        } else {
            return "X";
        }
    }

    // 4. change the board and print it
    public String[][] changeBoard(String[][] boardToBeChanged, String position, String player){
        int rowPosition = Integer.parseInt(position.substring(0, 1));
        int colPosition = Integer.parseInt(position.substring(1));

        if(boardToBeChanged[rowPosition][colPosition].equals(" ")){
            boardToBeChanged[rowPosition][colPosition] = player;
            return boardToBeChanged;
        } else {
            System.out.println("The position at "+ position+ " is...");
            System.out.println(boardToBeChanged[rowPosition][colPosition]);
            System.out.println("Position "+ position +" already filled, try again!");
            if(player == "O"){
                System.out.println("asking human...");
                askHuman();
            }
        }
        
        return new String[0][0];
    }

    public String[][] deepCopy(String[][] original) {
        if (original == null) {
            return null;
        }
        final String[][] result = new String[original.length][];
        for (int i = 0; i < original.length; i++) {
            result[i] = Arrays.copyOf(original[i], original[i].length);
        }
        return result;
    }

    public void prettyPrint(String[][] board){
        // takes the board and prints it in a better way
        String columns = "  0 1 2"; 
        String seperator = "--------";

        String firstRow = "0|"+board[0][0]+" "+board[0][1]+" "+board[0][2]+"|";
        String secondRow = "1|"+board[1][0]+" "+board[1][1]+" "+board[1][2]+"|";
        String thirdRow = "2|"+board[2][0]+" "+board[2][1]+" "+board[2][2]+"|";

        System.out.println(columns);
        System.out.println(seperator);
        System.out.println(firstRow);
        System.out.println(secondRow);
        System.out.println(thirdRow);
        System.out.println(seperator);
        
    }

    public String[] endgameCheck(String[][] board) {
        // first check if any one wins, 
        // then check if not all spaces are filled, then game hasn't ended
        // if finally, all spaces are filled and no one wins, it is a tie
        //*returns [whether game has ended, and who won, tie, incomplete]
        for (int i = 0; i < board.length; i++) {
            // hor
            if((board[i][0].equals(board[i][1])) && (board[i][1].equals(board[i][2]))){
                // ignore if all blanks and check the other rows
                if(board[i][0].equals(" ")){
                    continue;
                }
                return new String[] {"true", board[i][1]};
                
            }
            // ver
            if((board[0][i].equals(board[1][i])) && (board[1][i].equals(board[2][i]))){
                // ignore if all blanks
                if(board[0][i].equals(" ")){
                    continue;
                }
                return new String[] {"true", board[0][i]};
            }
        }
        // diag
        if(((board[0][0].equals(board[1][1])) && (board[1][1].equals(board[2][2]))) || 
        ((board[2][0].equals(board[1][1])) && (board[1][1].equals(board[0][2])))){
                // ignore if all blanks
                if(!board[1][1].equals(" ")){
                    return new String[] {"true", board[1][1]};
                }
        }
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if(board[i][j].equals(" ")){
                    System.out.println("game not over yet");
                    return new String[] {"false", "incomplete"};
                }
            } 
        }
        
        System.out.println("Tie");
        return new String[] {"true", "tie"};
    }
    public static void main(String[] args) {
       Main game = new Main(); 
    }
}
