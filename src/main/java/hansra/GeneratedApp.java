package hansra;

import Hansra7.neuralnetwork.NeuralNetwork;
import Hansra7.neuralnetwork.Transform;
import Hansra7.neuralnetwork.loader.Loader;
import Hansra7.neuralnetwork.loader.test.TestLoader;
public class GeneratedApp {
	public static void main(String[] args) {
		
		String filename = "neural1.net";
		System.out.println(Runtime.getRuntime().availableProcessors());
		
		NeuralNetwork neuralNetwork = NeuralNetwork.load(filename);
		
		if(neuralNetwork == null) {
			System.out.println("Unable to load neural network from saved.");
		
		
		int inputRows = 10;
		int outputRows = 3;
		NeuralNetwork neuralNetwork1 = new NeuralNetwork();
		neuralNetwork1.add(Transform.DENSE, 100, inputRows);
		neuralNetwork1.add(Transform.RELU);
		neuralNetwork1.add(Transform.DENSE, 50);
		neuralNetwork1.add(Transform.RELU);
		neuralNetwork1.add(Transform.DENSE, outputRows);
		neuralNetwork1.add(Transform.SOFTMAX);
		
		neuralNetwork1.setThreads(5);
		neuralNetwork1.setEpochs(20);
		neuralNetwork1.setLearningRates(0.02, 0.001);
		System.out.println(neuralNetwork1);
		} else {
			System.out.println(neuralNetwork);
		}
		Loader trainLoader = new TestLoader(60_000, 32);
		Loader testLoader = new TestLoader(10_000, 32);
		
		
		neuralNetwork.fit(trainLoader, testLoader);
		
		if(neuralNetwork.save("filename")) {
			System.out.println("Saved to " + filename);
		}
		else {
			System.out.println("Unable to save to" + filename);
		}
		
	}

}
