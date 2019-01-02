import java.util.Scanner;

class HumanPlayer implements Player {
	char symbol;

	public HumanPlayer (char symbol) {
		this.symbol = symbol;
	}

	public char getSymbol () {
		return this.symbol;
	}

	public void getMove (char[][] board) {
		Scanner s = new Scanner(System.in);

		int row = -1;
		int column = -1;

		row = s.nextInt();
		column = s.nextInt();

		board[row][column] = this.symbol;
	}

	// Copied from AI player
	public double[] generateInput (char[][] board) {
		double[] input = new double[18];

		int pos = 0;
		for (int row=0; row<board.length; row++)
			for (int column=0; column<board.length; column++) {
				if (board[row][column] == this.symbol) {
					input[pos] = 1;
					input[pos+1] = 0;
				} else if (board[row][column] != 'e') {
					input[pos] = 0;
					input[pos+1] = 1;
				} else {
					input[pos] = 0;
					input[pos+1] = 0;
				}
				pos += 2;
			}

		return input;
	}
}