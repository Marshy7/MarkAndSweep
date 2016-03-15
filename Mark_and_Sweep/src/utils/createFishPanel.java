package utils;

/**
 * Author: Alan Marsh
 * Date: 07/03/16
 * Version: 2.1
 */

import javax.swing.JPanel;

import controllers.garbageCollector;
import edu.princeton.cs.introcs.Draw;
import java.awt.BorderLayout;
import java.awt.Color;

public class createFishPanel extends JPanel {

	private garbageCollector gc;
	private Draw draw;
	private int handlePoolSize, objectPoolSize;
	private final boolean DEBUG = true;
	
	/**
	 * Create the panel.
	 */
	public createFishPanel(garbageCollector gc, int width, int height) {
		
		this.gc = gc;
		handlePoolSize = gc.HANDLER_SIZE;
		objectPoolSize = gc.POOL_SIZE;
		draw = new Draw();
		draw.setCanvasSize(width, height);
				
		setLayout(new BorderLayout(0, 0));
		
		add(draw.getJLabel());
		
		

	}
	

	
	public JPanel getPanel(){
		
		return this;
		
	}
	
	
	public void reDraw(){
		
		if(DEBUG)System.out.println("\t** createObject.reDraw method called");
		
		draw.clear();
		
		for(int i = 0; i < handlePoolSize; i++){
			if(gc.getHandle(i) != null){
							
				draw.setPenColor(Color.BLACK);
				draw.filledRectangle(.25, (.9 / (handlePoolSize * 2))*((handlePoolSize - i)*2 + 1)- 0.05, .2, (.9/handlePoolSize)/2);
				
				draw.filledRectangle(.75, (.9 / (objectPoolSize * 2))*((objectPoolSize - gc.getHandle(i).fishPos)*2 + 1) + .05, .2, (.9/objectPoolSize)/2);
				
				draw.setPenColor(gc.getHandle(i).fishType.myColor);		
				draw.filledRectangle(.25, (.9 / (handlePoolSize * 2))*((handlePoolSize - i)*2 + 1)-0.05, .195, ((.9/handlePoolSize)/2) - .005);
				
				draw.line(.45, (.9 / (handlePoolSize * 2))*((handlePoolSize - i)*2 + 1)- 0.05,
					.55, (.9 / (objectPoolSize * 2))*((objectPoolSize - gc.getHandle(i).fishPos)*2 + 1) + .05);
				
				for(int j = 1; j <= gc.getHandle(i).fishType.objectSize; j++){
					draw.filledRectangle(.75, (.9 / (objectPoolSize * 2))*((objectPoolSize - (gc.getHandle(i).fishPos + j))*2 + 1) + .05, .2, (.9/objectPoolSize)/2);
				}
				
				
			}
			
			
		}
		
		
	}

}
