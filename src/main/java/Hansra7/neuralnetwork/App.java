package Hansra7.neuralnetwork;
//@formatter:off
/**
 * 
 */
//@formatter:off
public class App {
	static double neuron(double[] x, double[]w, double b) {
		// weighted sum
		double sum = 0.0;
		
		for (int i = 0; i < x.length; i++) {
        	sum += x[i] * w[i];
        }
        sum += b;
      //activation function
        return sum > 0 ? 1.0: 0.0;
	}
	
	static double and(double x1, double x2) {
		return neuron(new double[] {x1, x2}, new double[] {1,1}, -1);
	}
	static double or(double x1, double x2) {
		return neuron(new double[] {x1, x2}, new double[] {1,1}, 0);
	}
	static double xor(double x1, double x2) {
		return and(or(x1, x2), nand(x1, x2));
	}
	static double nor(double x1, double x2) {
		return neuron(new double[] {x1, x2}, new double[] {-1,-1}, -1);
	}
	static double nand(double x1, double x2) {
		return neuron(new double[] {x1, x2}, new double[] {-1,-1}, 2);
	}
	static double xnor(double x1, double x2) {
		return or(and(x1, x2), nor(x1, x2));
	}
    public static void main(String[] args) {
       for(int i =0; i <4; i++) {
    	   double x1 = i/2;
    	   double x2 = i%2;
    	   double output = and(x1, x2);
    	   System.out.printf("%d%d\t%d\n",(int)x1,(int)x2,(int)output);
       }
    }
}
