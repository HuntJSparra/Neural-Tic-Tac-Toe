interface Player {
	abstract public char getSymbol ();
	abstract public void getMove (char[][] board);
	abstract public double[] generateInput(char[][] board);
}