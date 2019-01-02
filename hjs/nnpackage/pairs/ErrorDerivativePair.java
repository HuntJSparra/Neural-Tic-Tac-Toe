package hjs.nnpackage.pairs;

public interface ErrorDerivativePair {
  // Abbreviated to EDP
  public double function(double target, double output);
  public double derivative(double target, double output);
}