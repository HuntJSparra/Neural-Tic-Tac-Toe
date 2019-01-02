package hjs.nnpackage.pairs;

import java.io.Serializable;

public class Linear implements FunctionDerivativePair, Serializable {
  double slope;
  
  public Linear () {
    this.slope = 1;
  }
  
  public Linear (double slope) {
     this.slope = slope;
  }
  
  public double function (double x) {
    return slope*x;
  }
  
  public double derivative (double x) {
    return slope;
  }
}