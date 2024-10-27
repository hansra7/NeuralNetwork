package Hansra7.neuralnetwork.loader;

public interface MetaData {
	
	public int getNumberItems();
	
	public void setNumberItems(int numberItems);
	
	public int getInputSize();
	
	public void setInputSize(int inputSize);
	
	public int getExpectedSizes();
	
	public void setExpectedSizes(int expectedSizes);
	public int getNumberBatches();
	
	public void setNumberBatches(int numberBatches);
	
	public int getTotalItemRead();
	
	public void setTotalItemRead(int totalItemRead);
	
	public int getItemsRead();
	
	public void setItemsRead(int itemsRead);
	

}
