# tictactoe-ai
#### Video Demo: 
#### Description: Uses Minimax Algorithm to play tic tac toe moves against human player

All of the code is within Main.java.
The way this project was done was in different git branches representing different stages of the project. The 'gui' branch is the most complete one.
The GUI is using java swing. It uses a few classes from swing such as

- JFrame
- JPanel
- JButtons
- JLabel

First, the frame is set up, then the title on top indicating game state, then there is a GridLayout for the TicTacToe 3x3 boxes.

##### For the game's logic code

There is a while loop that runs unless the game is at the endgameState.
endgameCheck is a function which returns a String Array, first element containing true/false (whether the game has ended) and second element of who won/tie.
As for the deatils of the function, it first checks if any player has won in all possible positions, then checks if all the positions are occupied(which is a tie)

When the game first begins, it is always the computer's(player1) turn, whose first move is always random as it would take very long if the first move was decided using minimax. When the computer plays, for current state of the board, it checks every legal move and then finds score of each move. Overall, it will return a move with highest score from the legal moves.

The highest score is determined using the minimax function, which takes a copy of the current board and the opponent. The minimax() function is a recursive function. It has a base case that uses endgameCheck to determine the score. This score is appended to an array and the maximum score of this array is returned.

How is the minimax function implemented:

1. check for base case, if so return max if "X" wins min if "O" wins and 0 if tie.
2. it will find out all the legal moves using getLegalMoves(player,board), which returns a String array of moves
3. For each of the moves from legalMoves there is a copy made of the board and a move is made resulting in a newBoard. A deep Copy of board must be made(in this case using a nested for loop) to prevent modifying the original variable, such that it can still be used for other moves
4. minimax(newBoard, opponent) is called again using the opponent and the board after that single move
5. If the current player is "X" or computer the maximum score is returned, if the current player is "O" or human then minimum score is obtained
6. Using this optimal move and score is returned for each player assuming the player makes rational moves

getLegalMoves uses a for loop to check every position within the board and appends to an arraylist, whichever position is empty and returns that arraylist

The easiest way to understand a minimax function is to look at the board state a few states before the terminal state and see which moves can be made, Subsequently you assign scores to the different moves at the terminal state which are bubbled back up the states.

The presence of both minimax and minimaxMove may seem redundant but , I felt it was the simplest way to abstract what the minimax algorithm does.
minimax() only returns the score, but minimaxMove() uses minimax to get the best move by comparing all the scores, and returns that move.
minimaxMoveToBoard prints the move by computer onto the gui as the board will keep track within the code.
`board = changeBoard(newBoard, moveByCom, "X");`
After the computer makes a move, player1Turn is set to false and the title changes to O's turn which uses java swing textField which is a a JLabel that is inside the titlePanel, a JPanel

The actionPerformed() function waits for human to click the button, checks if the button is blank(hasn't been clicked before) then gets the index of button clicked and converts it to index that can be used by askHuman, then changes the gui with "O"

askHuman calls changeBoard with the index and the current board and player, sets player1Turn = true so that the if condition in the while loop becomes true.

The prettyPrint() function is meant for debugging in case something goes wrong with the GUI. A function kept from the previous branches without GUI.
The GUI is simply meant to display the game's state. All of the logic is not directly linked to the GUI.
The structure of Main is largely designed using OOP, where each function has a mostly singular purpose
