package hjs.nnpackage.network;

import java.io.Serializable;

public class Connection implements Serializable {
  /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
   * Be careful not to instantiate two connections for the same thing              *
   * This will make it so you update one, but not the other when updating weights  *
   * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
  private double weight;
  private Neuron from; // What neuron sends the signal
  private Neuron to;   // What neuron the connection delivers the signal to
  
  public Connection (double weight, Neuron from, Neuron to) {
    this.weight = weight;
    this.from = from;
    this.to = to;
  }
  
  public double getWeight () {
    return this.weight;
  }
  
  public Neuron getFrom () {
    return this.from;
  }
  
  public Neuron getTo () {
    return this.to;
  }
  
  public void update (double delta) {
    this.weight += delta;
  }
}