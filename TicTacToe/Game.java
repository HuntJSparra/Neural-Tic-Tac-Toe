class Game {
	public char[][] board;

	public Game () {
		this.board = new char[][] { {'e','e','e'},
				 	  			 	{'e','e','e'},
				 	  				{'e','e','e'} };
	}

	public String boardToString () {
		String out = "";
		for (int row=0; row<this.board.length; row++) {
			for (int column=0; column<this.board[row].length; column++) {
				out += this.board[row][column];
				if (column != this.board[row].length-1)
					out += '|';
			}

			if (row != this.board.length-1)
				out += "\n-----\n";
		}

		return out;
	}

	public char checkWin () {
		//Horizontal Check
		for (int row=0; row<this.board.length; row++) {
			if (this.board[row][0] != 'e' && this.board[row][0] == this.board[row][1] && this.board[row][1] == this.board[row][2])
				return this.board[row][0];
		}

		//Vertical Check
		for (int column=0; column<board[0].length; column++) {
			if (this.board[0][column] != 'e' && this.board[0][column] == this.board[1][column] && this.board[1][column] == this.board[2][column])
				return this.board[0][column];
		}

		//Diagonal Check
		if (this.board[0][0] != 'e' && this.board[0][0] == this.board[1][1] && this.board[1][1] == this.board[2][2])
			return board[0][0];
		if (this.board[0][2] != 'e' && this.board[0][2] == this.board[1][1] && this.board[1][1] == this.board[2][0])
			return board[0][2];

		//Tie Check
		for (int row=0; row<this.board.length; row++)
			for (int column=0; column<this.board[row].length; column++)
				if (this.board[row][column] == 'e')
					return 'e'; //Not a tie

		//No Winner
		return 'N';
	}
}
