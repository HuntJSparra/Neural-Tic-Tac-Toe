package hjs.nnpackage.pairs;

import java.io.Serializable;

public class BiasLinear implements FunctionDerivativePair, Serializable {
  double slope;
  double bias;
  
  public BiasLinear (double slope, double bias) {
    this.slope = slope;
    this.bias = bias;
  }
  
  public double function (double x) {
    if (slope*x > bias)
      return slope*x;
    else
      return 0;
  }
  
  public double derivative (double x) {
    System.err.println("Warning: Derivative of Bias Linear is bad at best");
    if (slope*x > bias)
      return slope*x;
     else
       return 0;
  }
}