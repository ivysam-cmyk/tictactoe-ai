print out a list of the ai's able to vs

the ai you choose first plays first

choose via CLI

list all the diff AI according to list names
print board after each move

show winner

possible choices
12 21 13 31 23 32
-------------------------------------------

only find the wins for p1 and p2
Bug: eventhough the board is full, game still calls askOnce... causing index error
bug occurs as we only check for endgame after both AIs have played, instead,
should check after each one plays