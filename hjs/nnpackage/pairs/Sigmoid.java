package hjs.nnpackage.pairs;

import java.io.Serializable;

public class Sigmoid implements FunctionDerivativePair, Serializable {
  public double function (double x) {
    return 1/(1+Math.pow(Math.E, -x));
  }
  
  public double derivative (double x) {
    return function(x)*(1-function(x));
  }
}