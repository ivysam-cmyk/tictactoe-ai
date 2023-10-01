<!-- for tryToWin -->
to check if one move causes com to win
go through each move from all legal moves
for each move, changeBoard, find endgameCheck and see who wins
current prob: endgame() only returns t/f, make it return both [t/f,x/o/tie] in an array
if no winning move is found, then use any random move