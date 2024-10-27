package Hansra7.calculus;

import java.util.function.DoubleFunction;

public class Calculus {
	
	private static final double INC = 0.00001;
	
	public static double func1(double x) {
		return 3.7 * x + 5.3;
	}
	
	public static double func2(double x) {
		return x * x - 3.23;
	}
	
	public static double func3(double y1, double y2) {
		return y1 * y2 + 4.7 * y1;
	}
	
	public static double func4(double x) {
		return func1(x) * func2(x) + 4.7 * func1(x);
	}
	
	public static double differentiate(DoubleFunction<Double> func, double x) {
		
		double output1 = func.apply(x);
		double output2 = func.apply(x + INC);
		
		
		return (output2 - output1)/INC;
	}

	public static void main(String[] args) {
		
		double x = 2.76;
		double y1 = func1(x);
		double y2 = func2(x);
		double b = func3(y1, y2);
		
		
		double dy1dx = differentiate(Calculus::func1, x);
		double dy2dx = differentiate(Calculus::func2, x);
		double dbdy1 = differentiate(y->func3(y, y2), y1);
		double dbdy2 = differentiate(y->func3(y1, y), y2);
		
		double dbdxCalculated = (dbdy1 * dy1dx) + (dbdy2 * dy2dx);
		
		double dbdxApproximated = differentiate(Calculus::func4, x);
		
		System.out.println(dbdxCalculated);
		System.out.println(dbdxApproximated);
		
		
	}
}