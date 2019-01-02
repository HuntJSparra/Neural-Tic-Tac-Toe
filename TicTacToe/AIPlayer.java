import hjs.nnpackage.network.NeuralNetwork;

import java.util.Random;

class AIPlayer implements Player {
	char symbol;
	NeuralNetwork nn;

	public AIPlayer (char symbol, NeuralNetwork nn) {
		this.symbol = symbol;
		this.nn = nn;
	}

	public char getSymbol () {
		return this.symbol;
	}

	public void getMove (char[][] board) {
		double bestValue = -1;
		int bestRow = -1;
		int bestColumn = -1;

		Random r = new Random();

		for (int row=0; row<board.length; row++)
			for (int column=0; column<board.length; column++) {
				if (board[row][column] != 'e')
					continue;

				double tempValue;

				board[row][column] = this.symbol;
				this.nn.update(generateInput(board));
				tempValue = nn.getOutput()[0];
				tempValue += r.nextFloat()*0.2;

				if (tempValue > bestValue) {
					bestValue = tempValue;
					bestRow = row;
					bestColumn = column;
				}

				board[row][column] = 'e'; //Change back to how it was
			}

		board[bestRow][bestColumn] = this.symbol;
	}

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