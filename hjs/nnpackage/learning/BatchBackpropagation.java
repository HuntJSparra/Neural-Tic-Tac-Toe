package hjs.nnpackage.learning;

import hjs.nnpackage.pairs.ErrorDerivativePair;
import hjs.nnpackage.network.NeuralNetwork;

import java.util.LinkedList;
import java.util.Map;

public class BatchBackpropagation extends Backpropagation {
  // At the end, rerun all saved inputs and batch
  private NeuralNetwork nn;
  
  private LinkedList<double[]> savedInputs;
  
  public BatchBackpropagation (NeuralNetwork nn, ErrorDerivativePair edp) {
    super(nn, edp);
    this.nn = nn;
    
    this.savedInputs = new LinkedList<double[]>();
  }
  
  public void saveInput () {
    this.savedInputs.add(this.getInput());
  }

  public void saveInput (double[] input) {
    this.savedInputs.add(input);
  }
  
  private double[] getInput () {
    double[] inputLayer = new double[this.nn.layers[0].length];
    
    for (int i=0; i<this.nn.layers[0].length; i++) {
      inputLayer[i] = this.nn.layers[0][i].getInput();
    }
    
    return inputLayer;
  }
  
  public void backpropagate (double[][] targets, double alpha) {
    assert(targets.length == this.savedInputs.size());
    
    int i=0;
    for (double[] input : this.savedInputs) {
      nn.update(input);
      super.backpropagate(targets[i], alpha);
      
      i++;
    }
    super.updateWeights();
    
    this.savedInputs.clear();
  }

  public void backpropagateDiscount (double[] target, double alpha, double discount) {
    double targets[][] = new double[this.savedInputs.size()][target.length];
    double runningDiscount = 1.0;
    for (int i=0; i<targets.length; i++) {
      for (int j=0; j<target.length; j++) {
        targets[i][j] = target[j] * runningDiscount;
      }
      runningDiscount *= discount;
    }

    this.backpropagate(targets, alpha);
  }
}