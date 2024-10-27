package Hansra7.neuralnetwork.loader;

public abstract class AbstractMetaData implements MetaData {
	
	private int numberItems;
	private int inputSize;
	private int expectedSize;
	private int numberBatches;
	private int totalItemsRead;
	private int itemsRead;

	@Override
	public int getNumberItems() {
		return numberItems;
	}

	@Override
	public void setNumberItems(int numberItems) {
		this.numberItems = numberItems;

	}

	@Override
	public int getInputSize() {
		return inputSize;
	}

	@Override
	public void setInputSize(int inputSize) {
		this.inputSize = inputSize;
	}

	@Override
	public int getExpectedSizes() {
	     return expectedSize;
	}

	@Override
	public void setExpectedSizes(int expectedSizes) {
		this.expectedSize = expectedSizes;

	}

	@Override
	public int getNumberBatches() {
		return numberBatches;
	}

	@Override
	public void setNumberBatches(int numberBatches) {
		this.numberBatches = numberBatches;

	}

	@Override
	public int getTotalItemRead() {
		
		return totalItemsRead;
	}

	
	public void setTotalItemRead(int totalItemRead) {
		this.totalItemsRead = totalItemsRead;

	}

	@Override
	public int getItemsRead() {
		
		return itemsRead;
	}

	@Override
	public void setItemsRead(int itemsRead) {
	this.itemsRead = itemsRead ;

	}

}
