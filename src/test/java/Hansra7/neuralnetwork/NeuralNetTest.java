package Hansra7.neuralnetwork;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Random;

import org.junit.jupiter.api.Test;

import Hansra7.matrix.Matrix;

class NeuralNetTest {

	private Random random = new Random();
	

	
	@Test
	void testTrainEngine() {
		int inputRows = 5;
		int cols = 6;
		int outputRows = 7;
		
		
		Engine engine = new Engine();
		engine.add(Transform.DENSE, 100, inputRows);
		engine.add(Transform.RELU);
		engine.add(Transform.DENSE, outputRows);
		engine.add(Transform.SOFTMAX);
		
		RunningAverages runningAverages = new RunningAverages(2, 10, (callNumber, averages)->{
			assertTrue(averages[0] < 6);
			System.out.printf("%d. Loss: %.3f -- Percent correct: %.2f\n", callNumber, averages[0], averages[1]);
			
		});
	
		double initalLearningRate = 0.02;
		double learningRate = initalLearningRate;
		double iterations = 2000;
		
		for(int i =0; i< 2000; i++) {
			var tm = Util.generateTrainingMatrixes(inputRows, outputRows, cols);
			var input = tm.getInput();
			var expected = tm.getOutput();

		BatchResult batchResult = engine.runForwards(input);
		
		
		engine.runBackwards(batchResult, expected);
		engine.adjust(batchResult, learningRate);
		
		engine.evaluate(batchResult, expected);
		
		runningAverages.add(batchResult.getLoss(), batchResult.getPercentCorrect());
		
		learningRate -= (initalLearningRate/iterations);
		
		System.out.println(learningRate);
		}
	}
	
	
	@Test
	void testWeightGradient() {
		int inputRows = 4;
		int outputRows = 5;
		
		Matrix weights = new Matrix(outputRows, inputRows, i->random.nextGaussian());
		Matrix input = Util.generateInputMatrix(inputRows, 1);
		Matrix expected = Util.generateExpectedMatrix(outputRows, 1);
		
		Matrix output = weights.multiply(input).softMax();
		
		Matrix calculatedError = output.apply((index, value)->value - expected.get(index));
		
		
		Matrix calculatedWeightGradients = calculatedError.multiply(input.transpose());
		System.out.println(calculatedWeightGradients);
		
		Matrix approximatedWeightGradients = Approximator.weightGradient(
				weights,
				w-> {
					Matrix out = weights.multiply(input).softMax();
					return LossFunctions.crossEntropy(expected, out);
				});
				calculatedWeightGradients.setTolerance(0.01);
				assertTrue(calculatedWeightGradients.equals(approximatedWeightGradients));
				
	}
	
	
	
	
	//@Test
	void testEngine() {
		
		int inputRows = 5;
		int cols = 6;
		int outputRows = 4;
		Engine engine = new Engine();
		
		engine.add(Transform.DENSE, 8, 5);
		engine.add(Transform.RELU);
		engine.add(Transform.DENSE, 5);
		engine.add(Transform.RELU);
		engine.add(Transform.DENSE, 4);
		
		engine.add(Transform.SOFTMAX);
		engine.setStoreInputError(true);
		
		Matrix input = Util.generateInputMatrix(inputRows, cols);
		Matrix expected = Util.generateExpectedMatrix(outputRows, cols);
		
		Matrix approximatedError = Approximator.gradient(input, in->{
			BatchResult batchResult = engine.runForwards(in);
			return LossFunctions.crossEntropy(expected, batchResult.getOutput());
		});
		
		BatchResult batchResult = engine.runForwards(input);
		engine.runBackwards(batchResult, expected);
		
		Matrix calculatedError = batchResult.getInputError();
		System.out.println(approximatedError);
		System.out.println(calculatedError);
		
		calculatedError.setTolerance(0.01);
		
		assertTrue(calculatedError.equals(approximatedError));
	}
	
	
	
	
	@Test
	void testBackprop() {
		interface NeuralNet {
			Matrix apply(Matrix m);
		}
		final int inputRows = 4;
		final int cols = 5;
		final int outputRows = 4;
		
		Matrix input = new Matrix(inputRows, cols, i->random.nextGaussian());
		
		Matrix expected = new Matrix(outputRows, cols, i->0);
		
		Matrix weights = new Matrix(outputRows, inputRows, i->random.nextGaussian());
		Matrix biases = new Matrix(outputRows, 1, i->random.nextGaussian());
		
		for(int col = 0; col < cols; col++) {
			int randomRow = random.nextInt(outputRows);
			expected.set(randomRow, col, 1);
		}
		NeuralNet neuralNet = m -> {
			Matrix out = m.apply((index, value)->value > 0 ? value: 0);
			out = weights.multiply(out); // weights
			out.modify((row, col, value)->value + biases.get(row));// biases
			out = out.softMax(); // softmax activation function
			
			return out;
		};
		Matrix softmaxOutput = neuralNet.apply(input);
		
		
		Matrix ApproximatedResult = Approximator.gradient(input, in->{
			Matrix out = neuralNet.apply(in);
			return LossFunctions.crossEntropy(expected, out);
		});
		
		Matrix actualResult = softmaxOutput.apply((index, value)->value - expected.get(index));
		actualResult = weights.transpose().multiply(actualResult);
		actualResult = actualResult.apply((index, value)->input.get(index) > 0 ? value: 0);
		
		assertTrue(ApproximatedResult.equals(actualResult));
		
		
	
		
	}
	
	
	
	
	
	@Test
	void testSoftMaxCrossEntropyGradient() {
		
		final int rows = 4;
		final int cols = 5;
		
		Matrix input = new Matrix(rows, cols, i->random.nextGaussian());
		
		Matrix expected = new Matrix(rows, cols, i->0);
		
		for(int col = 0; col < cols; col++) {
			int randomRow = random.nextInt(rows);
			expected.set(randomRow, col, 1);
		}
		
		Matrix softmaxOutput = input.softMax();
		
		
		Matrix result = Approximator.gradient(input, in->{
			return LossFunctions.crossEntropy(expected, in.softMax());
		});
		
		result.forEach((index, value)->{
			double softmaxVal = softmaxOutput.get(index);
			double expectedVal = expected.get(index);
			
			assertTrue(Math.abs(value - (softmaxVal - expectedVal)) < 0.01);
			
		});		
		
		
	
		
	}
	
	
	
	
	@Test
	void testApproximator() {
		
		final int rows = 4;
		final int cols = 5;
		
		Matrix input = new Matrix(rows, cols, i->random.nextGaussian()).softMax();
		
		Matrix expected = new Matrix(rows, cols, i->0);
		
		for(int col = 0; col < cols; col++) {
			int randomRow = random.nextInt(rows);
			expected.set(randomRow, col, 1);
		}
		
		Matrix result = Approximator.gradient(input, in->{
			return LossFunctions.crossEntropy(expected, in);
		});
		
		input.forEach((index, value)->{
			double resultValue = result.get(index);
			double expectedVal = expected.get(index);
			
			if(expectedVal < 0.001) {
				assertTrue(Math.abs(resultValue) < 0.01);
			}
			else {
				assertTrue(Math.abs(resultValue +1.0/value) < 0.01);
			}
		});
		
	
		
	}
	
	
	@Test
	void testCrossEntropy() {
		double[] expectedValues = {1, 0, 0, 0, 0, 1, 0, 1, 0};
		Matrix expected = new Matrix(3, 3, i->expectedValues[i]);
		
		
		Matrix actual = new Matrix(3, 3, i->0.05 * i*i ).softMax();

		Matrix result = LossFunctions.crossEntropy(expected, actual);
		
		actual.forEach((row, col, index, value)->{
			double expectedValue = expected.get(index);
			double loss = result.get(col);
			
			if(expectedValue > 0.9) {
				assertTrue(Math.abs(Math.log(value) + loss) < 0.001);
			}
			
		});
		}
	
	

	//@Test
	void testTemp() {
		
		int inputSize = 5;
		int layerSize = 6;
		int layer2Size = 4;
		
		Matrix input = new Matrix(inputSize, 1, i->random.nextGaussian());
		
		Matrix layer1Weights = new Matrix(layerSize, input.getRows(), i->random.nextGaussian());
		Matrix layer1biases = new Matrix(layerSize, 1, i->random.nextGaussian());
		
		Matrix layer2Weights = new Matrix(layer2Size, layer1Weights.getRows(), i->random.nextGaussian());
		Matrix layer2biases = new Matrix(layer2Size, 1, i->random.nextGaussian());
		
		var output = input;
		System.out.println(output);
		
		output = layer1Weights.multiply(output);
		System.out.println(output);
		
		output = output.modify((row, col, value)->value + layer1biases.get(row));
		System.out.println(output);
		
		output = output.modify(value -> value > 0 ? value:0);
		System.out.println(output);
		
		output = layer2Weights.multiply(output);
		System.out.println(output);
		
		output = output.modify((row, col, value)->value + layer2biases.get(row));
		System.out.println(output);
		
		output = output.softMax();
		System.out.println(output);
		
	}
	@Test
	void testAddBias() {
		
		Matrix input = new Matrix(3, 3, i->(i+1));
		Matrix weights = new Matrix(3, 3, i->(i+1));
		Matrix biases = new Matrix(3, 1, i->(i+1));
		
		Matrix result = weights.multiply(input).modify((row, col, value)->value + biases.get(row));
		
		double[] expectedValues = { +31.00000,   +37.00000,   +43.00000,
				   +68.00000,   +83.00000,   +98.00000,
				   +105.00000,  +129.00000,  +153.00000 };
		Matrix expected = new Matrix(3, 3, i->expectedValues[i]);
		
		assertTrue(expected.equals(result));

	}



@Test
void testReLu() {
	
	final int numberNeurons = 5;
	final int numberInputs = 6;
	final int inputSize = 4;
	
	Matrix input = new Matrix(inputSize, numberInputs, i->random.nextDouble());
	Matrix weights = new Matrix(numberNeurons, inputSize, i->random.nextGaussian());
	Matrix biases = new Matrix(numberNeurons, 1, i->random.nextGaussian());
	
	Matrix result1 = weights.multiply(input).modify((row, col, value)->value + biases.get(row));
	Matrix result2 = weights.multiply(input).modify((row, col, value)->value + biases.get(row)).modify(value -> value > 0 ? value:0);
	
	result2.forEach((index, value)-> {
		double originalValue = result1.get(index);
		
		if(originalValue > 0) {
			assertTrue(Math.abs(originalValue - value) < 0.00001);
		}
		else {
			assertTrue(Math.abs(value) < 0.00001);
		}
	});

	

}
}



