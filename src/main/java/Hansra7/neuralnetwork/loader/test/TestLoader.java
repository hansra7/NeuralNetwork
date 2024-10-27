package Hansra7.neuralnetwork.loader.test;

import Hansra7.neuralnetwork.Util;

import Hansra7.neuralnetwork.loader.BatchData;
import Hansra7.neuralnetwork.loader.MetaData;

public class TestLoader implements Hansra7.neuralnetwork.loader.Loader {
	
	private MetaData metaData;
	
	private int numberItems = 60_000;
	private int inputSize = 500;
	private int expectedSize = 3;
	private int numberBatches;
	
	private int totalItemsRead;
	private int itemsRead;
	private int batchSize = 32;
	
	public TestLoader(int numberItems, int batchSize) {
		this.numberItems = numberItems;
		this.batchSize = batchSize;
		metaData = new TestMetaData();
		metaData.setNumberItems(numberItems);
		
		numberBatches = numberItems/batchSize;
		
		if(numberItems % batchSize != 0) {
			numberBatches += 1;
		}
		metaData.setNumberBatches(numberBatches);
		metaData.setInputSize(inputSize);
		metaData.setExpectedSizes(expectedSize);
	}

	@Override
	public MetaData open() {
		return metaData;
	}

	@Override
	public void close() {
		totalItemsRead = 0;
	}

	@Override
	public MetaData getMetaData() {
	return metaData;
	}

	@Override
	public synchronized BatchData readBatch() {
		
		if(totalItemsRead == numberItems) {
			return null;
		}
		itemsRead = batchSize;
		totalItemsRead += itemsRead;
		int excessItems = totalItemsRead - numberItems;
		
		if(excessItems > 0) {
			totalItemsRead -= excessItems;
			itemsRead -= excessItems;
		}
		var io = Util.generateTrainingArrays(inputSize, expectedSize, itemsRead);
		var batchData = new TestBatchData();
		batchData.setInputBatch(io.getInput());
		batchData.setExpectedBatch(io.getOutput());
		
		metaData.setTotalItemRead(totalItemsRead);
		metaData.setItemsRead(itemsRead);
		
		return batchData;
	}

}
