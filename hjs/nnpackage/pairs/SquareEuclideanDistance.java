package hjs.nnpackage.pairs;

public class SquareEuclideanDistance implements ErrorDerivativePair {
  public double function (double target, double output) {
    return 0.5*Math.pow((target-output), 2);
  }
  
  public double derivative (double target, double output) {
    return target-output;
  }
}