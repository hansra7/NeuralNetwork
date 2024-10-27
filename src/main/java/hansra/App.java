package hansra;

import java.io.File;

import Hansra7.neuralnetwork.NeuralNetwork;
import Hansra7.neuralnetwork.Transform;
import Hansra7.neuralnetwork.loader.Loader;
import Hansra7.neuralnetwork.loader.MetaData;
import Hansra7.neuralnetwork.loader.image.ImageLoader;

public class App {

	public static void main(String[] args) {
		
		final String filename = "mnistNeural0.net";
		
		
		if(args.length == 0) {
			System.out.println("usage: [app] <MNIST DATA DIRECTORY>");
			return;
			}
	
		String directory = args[0];
		
		if(!new File(directory).isDirectory()) {
			System.out.println("" + directory + "' is not a directory");
			return;
		}
		
		final String trainImages = String.format("%s%s%s", directory, File.separator, "train-images.idx3-ubyte");
		final String trainLabels = String.format("%s%s%s", directory, File.separator, "train-labels.idx1-ubyte");
		final String testImages = String.format("%s%s%s", directory, File.separator,"t10k-images.idx3-ubyte");
		final String testLabels = String.format("%s%s%s", directory, File.separator,"t10k-labels.idx1-ubyte");
		
		Loader trainLoader = new ImageLoader(trainImages, trainLabels, 32);
		Loader testLoader = new ImageLoader(testImages, testLabels, 32);
		
		MetaData metaData = trainLoader.open();
		int inputSize = metaData.getInputSize();
		int outputSize = metaData.getExpectedSizes();
		trainLoader.close();
		
		System.out.println(Runtime.getRuntime().availableProcessors());
		
		NeuralNetwork neuralNetwork = NeuralNetwork.load(filename);
		
		if(neuralNetwork == null) {
			System.out.println("Unable to load neural network from saved.");
		
		NeuralNetwork neuralNetwork1 = new NeuralNetwork();
		
		neuralNetwork1.setScaleInitialWeights(0.2);
		neuralNetwork1.add(Transform.DENSE, 200, inputSize);
		neuralNetwork1.add(Transform.RELU);
		neuralNetwork1.add(Transform.DENSE, outputSize);
		neuralNetwork1.add(Transform.SOFTMAX);
		
		neuralNetwork1.setThreads(5);
		neuralNetwork1.setEpochs(20);
		neuralNetwork1.setLearningRates(0.02, 0.001);
		
		} else {
			System.out.println("Loaded from " + filename);
		}
			
		
		System.out.println(neuralNetwork);
		
		
		neuralNetwork.fit(trainLoader, testLoader);
		
		if(neuralNetwork.save("filename")) {
			System.out.println("Saved to " + filename);
		}
		else {
			System.out.println("Unable to save to" + filename);
		}

}
}
