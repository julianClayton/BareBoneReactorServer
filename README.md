A Server that is used via a socket connection and dispactches requests using the REACTOR pattern. I used this server in the TicTacToe game for Android. 

Messages sent to the server are registered in the reactor so that they are dispactched to the proper event handlers each on a different thread.
