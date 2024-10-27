package Hansra7.neuralnetwork.loader.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import Hansra7.matrix.Matrix;
import Hansra7.neuralnetwork.loader.BatchData;
import Hansra7.neuralnetwork.loader.Loader;
import Hansra7.neuralnetwork.loader.MetaData;

class TestTestLoader {

	@Test
	void test() {
		
		int batchSize = 33;
		Loader testLoader = new TestLoader(60_000, 32);
		MetaData metaData = testLoader.open();
		int numberItems = metaData.getNumberItems();
		
		int lastBatchSize = numberItems % batchSize;
		
		int numberBatches = metaData.getNumberBatches();
		
		for(int i = 0; i <metaData.getNumberBatches(); i++) {
			
			BatchData batchData = testLoader.readBatch();
			
			int itemsRead = metaData.getItemsRead();
			
			int inputSize = metaData.getInputSize();
			int expectedSize = metaData.getExpectedSizes();
			
			Matrix input = new Matrix(inputSize, itemsRead, batchData.getInputBatch());
			Matrix expected = new Matrix(expectedSize, itemsRead, batchData.getExpectedBatch());
			
			assertTrue(input.sum() != 0);
			assertTrue(expected.sum() == itemsRead);
			if(i == numberBatches - 1) {
				assertTrue(itemsRead == lastBatchSize);
			} 
			else {
			
			assertTrue(batchData != null);
		}
	}

}
}
