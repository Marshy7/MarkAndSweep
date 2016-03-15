package controllers;

/**
 * Author: Alan Marsh
 * Date: 07/03/16
 * Version: 1.8
 */

import models.fish;
import models.fishHandler;

public class garbageCollector {
	
	public final int HANDLER_SIZE = 10;
	public final int POOL_SIZE = 55;
	
	public int redRoot;
	public int blueRoot;
	public int yellowRoot;
		
	public fishHandler[] handlePool;
	private int[] fishPool;
	
	private final boolean DEBUG = true;
	
	
	public garbageCollector(){
		
		handlePool = new fishHandler[HANDLER_SIZE];
		fishPool = new int[POOL_SIZE];	
		redRoot = HANDLER_SIZE;
		blueRoot = HANDLER_SIZE;
		yellowRoot = HANDLER_SIZE;
		
	}
	
	public fishHandler getHandle(int handlePos){
		
		return handlePool[handlePos];
	}
	
	
	public void addFish(fish fish){
		
		for(int i = 0; i < HANDLER_SIZE; i++){
			if(handlePool[i] == null){
				
				int putHeader = findHeaderEmpty(0, fish.objectSize);
				
				if(putHeader != POOL_SIZE){
					
					handlePool[i] = new fishHandler();
					handlePool[i].fishType = fish;
					if(DEBUG)System.out.println("\t** Added handler at position: " + i);
					if(DEBUG)System.out.println("\t** Object size: " + handlePool[i].fishType.objectSize);
									
					
					fishPool[putHeader] = fish.objectSize + 1;
					handlePool[i].fishPos = putHeader;
					if(DEBUG)System.out.println("\t** Added header size:" + (fish.objectSize + 1) + "  at position: " + putHeader);
					if(DEBUG)System.out.println("\t** Object size: " + handlePool[i].fishType.objectSize);
					
					
					for(int j = putHeader + 1; j < putHeader + fish.objectSize + 1; j++){
						fishPool[j] = 1;				
						
					}			
					
					break;
				}
				
			}
			
		}
		
	}
	
	public int findHeaderEmpty(int headerPos, int objectSize){
		
		if(((POOL_SIZE - headerPos) > objectSize)){
			if(fishPool[headerPos] == 0){
				for(int i = headerPos + 1; i < (headerPos + objectSize); i++){
					if(fishPool[i] !=0){
						
						fishPool[headerPos] = i + 1;
						return findHeaderEmpty((headerPos + fishPool[headerPos]), objectSize);
					}
					
				}
				return headerPos;
			}
			else if (fishPool[headerPos] < objectSize){
				return findHeaderEmpty((headerPos + fishPool[headerPos]), objectSize);
			}
			else{
				for(int i = headerPos + 1; i < (headerPos + fishPool[headerPos]); i++){
					if(fishPool[i] !=0){
						return findHeaderEmpty((headerPos + fishPool[headerPos]), objectSize);
					}
					else{
						return headerPos;
					}
				}
			}
		}
		
		
		return POOL_SIZE;
	}
	
	public void linkFish(int fish1, int fish2){
		if(handlePool[fish2].fishType.objectSize <= handlePool[fish1].fishType.objectSize){
			if(handlePool[fish1].fishType.objectSize == handlePool[fish2].fishType.objectSize){
				handlePool[fish1].hasFriend = true;
				handlePool[fish1].friendPos = fish2;
			}
			else if ((handlePool[fish1].fishType.objectSize)/2 >= handlePool[fish2].fishType.objectSize){
				handlePool[fish1].hasLunch = true;
				handlePool[fish1].lunchPos = fish2;
			}
			else{
				handlePool[fish1].hasSnack = true;
				handlePool[fish1].snackPos = fish2;
			}
		}	
		
	}
	
	public void removeLink(int fish1, int fish2){
		if(handlePool[fish1].friendPos == fish2){
			handlePool[fish1].hasFriend = false;
		}
		else if(handlePool[fish1].lunchPos == fish2){
			handlePool[fish1].hasLunch = false;
		}
		else if(handlePool[fish1].snackPos == fish2){
			handlePool[fish1].hasSnack = false;
		}
	}
	
	public void markFish(int root){
		if(root != HANDLER_SIZE){
			handlePool[root].mark = true;
			if(handlePool[root].hasSnack){
				markFish(handlePool[root].snackPos);
				
			}
			
			if(handlePool[root].hasLunch){
				markFish(handlePool[root].lunchPos);
				
			}
			
			if(handlePool[root].hasFriend){
				markFish(handlePool[root].friendPos);
				
			}
			
		}		
		
	}
	
	public  void sweepFish(){
		for(int i = 0; i < HANDLER_SIZE; i++){
			if(handlePool[i] != null){
				if(!handlePool[i].mark){
					int headerPos = handlePool[i].fishPos;
					
					for(int j = headerPos + 1; j < headerPos + handlePool[i].fishType.objectSize + 1; j++){
						fishPool[j] = 0;				
						
					}
					
					handlePool[i] = null;
				}
			}
		}
		for(int i = 0; i < HANDLER_SIZE; i++){	
			if(handlePool[i] != null){
				handlePool[i].mark = false;
			}
		}
		if(redRoot != HANDLER_SIZE && handlePool[redRoot] == null){
			redRoot = HANDLER_SIZE;
		}
		if(blueRoot != HANDLER_SIZE && handlePool[blueRoot] == null){
			blueRoot = HANDLER_SIZE;
		}
		if(yellowRoot != HANDLER_SIZE && handlePool[yellowRoot] == null){
			yellowRoot = HANDLER_SIZE;
		}
		
	}
	
	public void compactFish(){
		for(int i = 0; i < POOL_SIZE; i++){
			fishPool[i] = 0;
		}
		
		for(int i = 0; i < HANDLER_SIZE; i++){
			if(handlePool[i] != null){
				
				int putHeader = findHeaderEmpty(0, handlePool[i].fishType.objectSize);
				
				if(putHeader != POOL_SIZE){									
					
					fishPool[putHeader] = handlePool[i].fishType.objectSize + 1;
					handlePool[i].fishPos = putHeader;
										
					for(int j = putHeader + 1; j < putHeader + handlePool[i].fishType.objectSize + 1; j++){
						fishPool[j] = 1;							
					}			
					
				}
				
			}
			
		}
	}
	
}
