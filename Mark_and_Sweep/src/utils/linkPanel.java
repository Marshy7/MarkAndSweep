package utils;

/**
 * Author: Alan Marsh
 * Date: 13/03/16
 * Version: 2.1
 */

import java.awt.Color;
import javax.swing.JPanel;

import controllers.garbageCollector;
import edu.princeton.cs.introcs.Draw;

public class linkPanel extends JPanel {
	
	private garbageCollector gc;
	private Draw draw;
	private int handlePoolSize, objectPoolSize;
	
	private final boolean DEBUG = true;

	/**
	 * Create the panel.
	 */
	public linkPanel(garbageCollector gc, int width, int height) {
		
		this.gc = gc;
		handlePoolSize = gc.HANDLER_SIZE;
		objectPoolSize = gc.POOL_SIZE;
		draw = new Draw();
		draw.setCanvasSize(width, height);
		
		add(draw.getJLabel());	
		

	}
	
	public JPanel getPanel(){
		
		return this;
		
	}
	
	public void reDraw(){
		draw.clear();
		
		if(DEBUG)System.out.println("\t** linkObject.reDraw method called");
		
		setPoint();
		setRoot();
		
		for(int i = 0; i < handlePoolSize; i++){
			if(gc.handlePool[i] != null){
				
				draw.setPenColor(gc.handlePool[i].fishType.myColor);
				
				double size = 0;
				if(gc.handlePool[i].fishType.objectSize == 12){
					size = .06;
				}
				else if(gc.handlePool[i].fishType.objectSize == 8){
					size = .04;
				}
				else{
					size = .02;
				}
						
				draw.filledRectangle(gc.handlePool[i].fishType.point[0], gc.handlePool[i].fishType.point[1],
						size, size);
				
				if(gc.handlePool[i].hasFriend){
					draw.line(gc.handlePool[i].fishType.point[0], gc.handlePool[i].fishType.point[1],
							gc.handlePool[gc.handlePool[i].friendPos].fishType.point[0], gc.handlePool[gc.handlePool[i].friendPos].fishType.point[1]);
				}
				if(gc.handlePool[i].hasLunch){
					draw.line(gc.handlePool[i].fishType.point[0], gc.handlePool[i].fishType.point[1],
							gc.handlePool[gc.handlePool[i].lunchPos].fishType.point[0], gc.handlePool[gc.handlePool[i].lunchPos].fishType.point[1]);
				}
				if(gc.handlePool[i].hasSnack){
					draw.line(gc.handlePool[i].fishType.point[0], gc.handlePool[i].fishType.point[1],
							gc.handlePool[gc.handlePool[i].snackPos].fishType.point[0], gc.handlePool[gc.handlePool[i].snackPos].fishType.point[1]);
				}
				
				draw.setPenColor(Color.BLACK);
				draw.text(gc.handlePool[i].fishType.point[0], gc.handlePool[i].fishType.point[1], String.valueOf(i));
				
			}
			 
		}
		
	}
	
	public void setPoint(){
		
		if(DEBUG)System.out.println("\t** setPoint method called");
		
		for(int i = 0; i < handlePoolSize; i++){
			if(gc.handlePool[i] != null){
				
				double centerX = .5;
				double centerY = .5;

				double angle = (2*Math.PI/handlePoolSize)*i;
				double radius = .35;
				 
				double xPos = radius * (double)Math.cos(angle) + centerX;
				double yPos = radius * (double)Math.sin(angle) + centerY;
				
				gc.handlePool[i].fishType.point[0] = xPos;
				gc.handlePool[i].fishType.point[1] = yPos;
			}
		}
	}
	
	public void setRoot(){
		
		double centerX = .5;
		double centerY = .5;

		double angle = (2*Math.PI/handlePoolSize);
		double radius = .45;
		
		if(gc.redRoot != handlePoolSize){
			
			double xPos = radius * (double)Math.cos(angle*gc.redRoot) + centerX;
			double yPos = radius * (double)Math.sin(angle*gc.redRoot) + centerY;
			
			draw.setPenColor(Color.RED);
			draw.filledRectangle(xPos, yPos, .02, .02);
			draw.line(xPos, yPos, gc.handlePool[gc.redRoot].fishType.point[0], gc.handlePool[gc.redRoot].fishType.point[1]);
			draw.setPenColor(Color.BLACK);
			draw.text(xPos, yPos, "R");
			
		}
		if(gc.blueRoot != handlePoolSize){
			
			double xPos = radius * (double)Math.cos(angle*gc.blueRoot) + centerX;
			double yPos = radius * (double)Math.sin(angle*gc.blueRoot) + centerY;
			
			draw.setPenColor(Color.BLUE);
			draw.filledRectangle(xPos, yPos, .02, .02);
			draw.line(xPos, yPos, gc.handlePool[gc.blueRoot].fishType.point[0], gc.handlePool[gc.blueRoot].fishType.point[1]);
			draw.setPenColor(Color.BLACK);
			draw.text(xPos, yPos, "R");
			
		}
		if(gc.yellowRoot != handlePoolSize){
	
			double xPos = radius * (double)Math.cos(angle*gc.yellowRoot) + centerX;
			double yPos = radius * (double)Math.sin(angle*gc.yellowRoot) + centerY;
	
			draw.setPenColor(Color.YELLOW);
			draw.filledRectangle(xPos, yPos, .02, .02);
			draw.line(xPos, yPos, gc.handlePool[gc.yellowRoot].fishType.point[0], gc.handlePool[gc.yellowRoot].fishType.point[1]);
			draw.setPenColor(Color.BLACK);
			draw.text(xPos, yPos, "R");
	
}
	}

}
