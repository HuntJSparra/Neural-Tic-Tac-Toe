package hjs.nnpackage.learning;

import hjs.nnpackage.pairs.ErrorDerivativePair;
import hjs.nnpackage.network.Connection;
import hjs.nnpackage.network.Neuron;
import hjs.nnpackage.network.NeuralNetwork;

import java.util.Map;
import java.util.HashMap;

public class Backpropagation {
  private Neuron[] outputLayer;
  public ErrorDerivativePair edp;
  
  private HashMap<Connection, Double> weightDeltas;
  
  public Backpropagation (NeuralNetwork nn, ErrorDerivativePair edp) {
    this.outputLayer = nn.layers[nn.layers.length-1];
    this.edp = edp;
    
    weightDeltas = new HashMap<Connection, Double>();
  }
  
  public void backpropagate (double[] target, double alpha) {
    double[] output = this.getOutput();
    assert(target.length == output.length);
  
    for (int i=0; i<output.length; i++) {
      double error = this.edp.derivative(target[i], output[i]);
      this.backpropagate(this.outputLayer[i], error, alpha);
    }
  }
  
  private void backpropagate (Neuron neuron, double error, double alpha) {
    error *= neuron.getFDP().derivative(neuron.getInput());
    
    for (Connection connection : neuron.getFromConnections()) {
      double connectionOutput = connection.getFrom().getOutput();
      backpropagate(connection.getFrom(), error*connection.getWeight(), alpha);
      
      double oldValue = weightDeltas.getOrDefault(connection, (double)0);
      weightDeltas.put(connection, oldValue+alpha*(error*connectionOutput));
    }
  }
  
  private double[] getOutput () {
    double[] output = new double[this.outputLayer.length];
    
    for (int i=0; i<this.outputLayer.length; i++) {
      output[i] = this.outputLayer[i].getOutput();
    }
    
    return output;
  }
  
  public double getError (double[] target) {
    double error = 0;
    assert(target.length == this.outputLayer.length);
    
    for (int i=0; i<this.outputLayer.length; i++)
      error += this.edp.function(target[i], this.outputLayer[i].getOutput());
      
    return error;
  }
  
  public void updateWeights () {
    for (Map.Entry<Connection, Double> entry : this.weightDeltas.entrySet()) {
      entry.getKey().update(entry.getValue());
      weightDeltas.put(entry.getKey(), (double)0);
    }
  }
}