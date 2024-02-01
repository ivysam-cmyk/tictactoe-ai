# tictactoe-ai
#### Video Demo: 
#### Description: Uses Minimax Algorithm to play tic tac toe moves against human player

All of the code is within Main.java.
The way this project was done was in different git branches representing different stages of the project. The 'gui' branch is the most complete one
The GUI is using java swing. It uses a few classes from swing such as

- JFrame
- JPanel
- JButtons

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
3. For each of the moves from legalMoves there is a copy made of the board and a move is made resulting in a newBoard
4. minimax(newBoard, opponent) is called again using the opponent and the board after that single move
