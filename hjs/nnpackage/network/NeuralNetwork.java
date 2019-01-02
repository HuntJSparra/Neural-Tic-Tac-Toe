package hjs.nnpackage.network;

import java.io.Serializable;

public class NeuralNetwork implements Serializable {
  /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
   * layers is a 2D list of n layers with layers[0] being input and layers[n] being output   *
   * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
  public Neuron[][] layers;

  public NeuralNetwork (Neuron[][] layers) {
    this.layers = layers;
  }
 
  public void update (double[] input) {
    assert(input.length == layers[0].length);

    int inputNumber = 0;

    for (int layer=0; layer<layers.length; layer++)
      for (Neuron neuron : layers[layer]) {
        if (layer == 0) { //if input layer
          neuron.updateInput(input[inputNumber]);
          inputNumber++;
        } else
          neuron.update();
      }
  }
 
  public double[] getOutput () {
    double[] output = new double[this.layers[this.layers.length-1].length];

    for (int i=0; i<output.length; i++)
      output[i] = this.layers[this.layers.length-1][i].getOutput();

    return output;
  }
}