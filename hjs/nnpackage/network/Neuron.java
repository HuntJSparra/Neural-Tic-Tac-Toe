package hjs.nnpackage.network;

import hjs.nnpackage.pairs.FunctionDerivativePair;

import java.util.LinkedList;
import java.util.Random;

import java.io.Serializable;

public class Neuron implements Serializable {
  private double input;
  private double output;
  
  private LinkedList<Connection> fromConnections; // Connections that the Neuron recieves signals from
  private LinkedList<Connection> toConnections;   // Connections that the Neuron sends signals through
  
  private FunctionDerivativePair fdp; // The activation function and its derivative
  
  public Neuron (FunctionDerivativePair fdp) {
    this.input = 0;
    this.output = 0;
    
    this.fromConnections = new LinkedList<Connection>();
    this.toConnections = new LinkedList<Connection>();
    
    this.fdp = fdp;
  }
  
  public double getInput () {
    return this.input;
  }
  
  public double getOutput () {
    return this.output;
  }
  
  public FunctionDerivativePair getFDP () {
    return this.fdp;
  }
  
  public void updateInput (double value) {
    // This is used to set the input and output of input nodes
    this.input = value;
    this.output = fdp.function(value);
  }
  
  public void update () {
    // Update is called by the NeuralNetwork and is done layer by layer, from top to bottom
    
    double netInput = 0;
    for (Connection connection : this.fromConnections)
      netInput += connection.getWeight()*connection.getFrom().getOutput();
    
    this.input = netInput;
    this.output = fdp.function(this.input);
  }
  
  public void connect (Neuron to) {
    System.err.println("Warning: No connection weight. Assigning random value.");
    Random r = new Random();
    this.connect(to, r.nextDouble()*2-1);
  }
  
  public void connect (Neuron to, double weight) {
    Connection newConnection = new Connection(weight, this, to);
    this.toConnections.add(newConnection);
    to.addFrom(newConnection);
  }
  
  protected void addFrom (Connection connection) {
    this.fromConnections.add(connection);
  }
  
  public LinkedList<Connection> getFromConnections () {
    return this.fromConnections;
  }
  
  public LinkedList<Connection> getToConnections () {
    return this.toConnections;
  }
}