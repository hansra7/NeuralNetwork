package Hansra7.neuralnetwork.loader.image;

import Hansra7.neuralnetwork.loader.AbstractMetaData;

public class ImageMetaData extends AbstractMetaData {
	private int width;
	private int height;
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	@Override
	public void setItemsRead(int itemsRead) {
		super.setItemsRead(itemsRead);
		super.setTotalItemRead(super.getTotalItemRead() + itemsRead);
		
	}
	
	
	
	

}
