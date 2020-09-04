//hjs.nnpackage
import hjs.nnpackage.pairs.*;

import hjs.nnpackage.network.Neuron;
import hjs.nnpackage.network.NeuralNetwork;

import hjs.nnpackage.learning.BatchBackpropagation;

//java
import java.lang.Thread;
import java.lang.Runnable;

import java.io.IOException;
import java.io.FileNotFoundException;

import java.io.Serializable;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;

class TicTacToe {
	static boolean exit;

	static int matchNumber;

	static NeuralNetwork nn;
	static BatchBackpropagation td;

	static boolean humanPlayer;

	public static void main (String args[]) throws IOException {
		TicTacToe.humanPlayer = (args.length > 0);
		TicTacToe.exit = false;

		try {
			FileInputStream fis = new FileInputStream("./nn/neuralnetwork.ser");
			ObjectInputStream ois = new ObjectInputStream(fis);
			TicTacToe.nn = (NeuralNetwork)ois.readObject();

			fis = new FileInputStream("./nn/matchnumber.ser");
			ois = new ObjectInputStream(fis);
			TicTacToe.matchNumber = (int)ois.readObject();

			ois.close();
		} catch (FileNotFoundException e) {
			FunctionDerivativePair lin = new Linear();
			FunctionDerivativePair sig = new Sigmoid();
			
			Neuron[][] network = new Neuron[5][];
			network[0] = new Neuron[18]; //input
			for (int i=0; i<network[0].length; i++)
				network[0][i] = new Neuron(lin);

			network[1] = new Neuron[14]; //hidden1
			for (int i=0; i<network[1].length; i++)
				network[1][i] = new Neuron(sig);

			network[2] = new Neuron[10]; //hidden2
			for (int i=0; i<network[2].length; i++)
				network[2][i] = new Neuron(sig);

			network[3] = new Neuron[8]; //hidden3
			for (int i=0; i<network[3].length; i++)
				network[3][i] = new Neuron(sig);

			network[4] = new Neuron[1]; //output
			for (int i=0; i<network[4].length; i++)
				network[4][i] = new Neuron(sig);

			for (int layer=0; layer<network.length-1; layer++)
				for (int neuron=0; neuron<network[layer].length; neuron++)
					for (int nextNeuron=0; nextNeuron<network[layer+1].length; nextNeuron++)
						network[layer][neuron].connect(network[layer+1][nextNeuron]);

			TicTacToe.nn = new NeuralNetwork(network);

			TicTacToe.matchNumber = 1;
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}


		Thread t = new Thread(new Runnable () {
			public void run() {
				while (TicTacToe.exit == false) {
					System.out.println("Game #"+TicTacToe.matchNumber);
					TicTacToe ttt = new TicTacToe();
					ttt.run();
					TicTacToe.matchNumber++;
				}
			}
		});

		t.start();

		if (TicTacToe.humanPlayer == false) {
			System.in.read();
			TicTacToe.exit = true;

			try {
				FileOutputStream fos = new FileOutputStream("./nn/neuralnetwork.ser");
				ObjectOutputStream oos = new ObjectOutputStream(fos);
				oos.writeObject(TicTacToe.nn);

				fos = new FileOutputStream("./nn/matchnumber.ser");
				oos = new ObjectOutputStream(fos);
				oos.writeObject(TicTacToe.matchNumber);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}


	Game game;

	Player p1;
	Player p2;

	public void run () {
		String gameRecording = "";

		game = new Game();
		p2 = new AIPlayer('X', TicTacToe.nn);
		p1 = ((TicTacToe.humanPlayer == false) ? new AIPlayer('O', TicTacToe.nn) : new HumanPlayer('O'));
	
		Player currentPlayer = p1;

		ErrorDerivativePair edp = new SquareEuclideanDistance();
		BatchBackpropagation tdp1 = new BatchBackpropagation(TicTacToe.nn, edp);
		BatchBackpropagation tdp2 = new BatchBackpropagation(TicTacToe.nn, edp);

		while (game.checkWin() == 'e' && TicTacToe.exit == false) {
			System.out.println(game.boardToString()+'\n');
			if (TicTacToe.matchNumber%1000 == 1)
				gameRecording += game.boardToString()+'\n'+'\n';

			currentPlayer.getMove(game.board);

			if (currentPlayer == p1) {
				currentPlayer = p2;
				tdp1.saveInput(p1.generateInput(game.board));
			} else {
				currentPlayer = p1;
				tdp2.saveInput(p2.generateInput(game.board));
			}
		}
		System.out.println(game.boardToString()+'\n');
		if (TicTacToe.matchNumber%1000 == 1)
			gameRecording += game.boardToString()+'\n';

		char winner = game.checkWin();
		tdp1.backpropagateDiscount(new double[] {(winner == 'N' ? 0.5 : (winner == p1.getSymbol() ? 1 : 0))}, 0.001, 1);
		tdp2.backpropagateDiscount(new double[] {(winner == 'N' ? 0.5 : (winner == p2.getSymbol() ? 1 : 0))}, 0.001, 1);

		if (TicTacToe.matchNumber%1000 == 1) {
			System.out.println("Recording");
			try {
				PrintWriter pw = new PrintWriter("./games/Game#"+TicTacToe.matchNumber+".txt", "UTF-8");
				pw.println(gameRecording);
				pw.close();

				gameRecording = "";
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}