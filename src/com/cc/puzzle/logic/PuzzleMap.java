package com.cc.puzzle.logic;

import java.util.LinkedList;

import com.cc.puzzle.logic.PuzzleUtils.PuzzleOperate;


import android.net.UrlQuerySanitizer.ValueSanitizer;
import android.util.Log;

class PuzzleMap {
	
	private final String TAG = "PuzzleMap";
	private final int MAP_SIZE = 4;
	private PuzzleNumber[][] numberMap;
	
	public PuzzleMap(){
		numberMap = new PuzzleNumber[MAP_SIZE][MAP_SIZE];
		initMap();
	}
	
	public void generateNewNumberInMap(){
		
		//Get the all empty number position
		LinkedList<Integer> emptyNumberList = new LinkedList<Integer>();
		for(int i = 0; i < MAP_SIZE; i++){
			for(int j = 0; j < MAP_SIZE; j++){
				if(numberMap[i][j].isEmpty()){
					emptyNumberList.add(mapPosition2mapIndex(i,j));
				}
			}
		}
		
		//Select an empty position from list
		int listIndex = PuzzleUtils.generateRandonEmptyPosition(emptyNumberList.size());
		int mapIndex = emptyNumberList.get(listIndex);
		MapPosition position = mapIndex2mapPosition(mapIndex);
		int newNumber = PuzzleUtils.generateNumber();
		Log.v(TAG, "Generate New Number In Puzzle Map");
		setNumberInMap(position.getRow(), position.getColumn(), newNumber);
	}
	
	public int doSwipe(PuzzleOperate operate){
		//Clear combine flag
		for(int i = 0; i < MAP_SIZE; i++){
			for(int j = 0; j < MAP_SIZE; j++){
				if(numberMap[i][j].hasCombine()){
					numberMap[i][j].clearCombine();
				}
			}
		}
		switch (operate) {
		case SWIPE_UP:
			return doSwipeUp();
		case SWIPE_DOWN:
			return doSwipeDown();
		case SWIPE_LEFT:
			return doSwipeLeft();
		case SWIPE_RIGHT:
			return doSwipeLeft();
		default:
			break;
		}
		return 0;
	}
	
	public Boolean isOver(){
		//from [0,0] to [3,3], compare the number with left and below
		for(int i = 0; i < MAP_SIZE - 1; i++){
			for(int j = 0; j < MAP_SIZE - 1; j++){
				if(numberMap[i][j] == numberMap[i][j+1] || 
						numberMap[i][j] == numberMap[i+1][j]){
					return false;
				}
			}
		}
		
		//Compare the column[k,3] and row[3,k]
		for(int k = 0; k < MAP_SIZE - 1; k++){
			if(numberMap[k][3] == numberMap[k+1][3] || 
					numberMap[3][k] == numberMap[3][k+1]){
				return false;
			}
		}
		return true;
	}
	
	public Boolean isMapFull(){
		for(int i = 0; i < MAP_SIZE; i++){
			for(int j = 0; j < MAP_SIZE; j++){
				if(numberMap[i][j].isEmpty()){
					return false;
				}
			}
		}
		return true;
	}
	
	private int doSwipeUp(){
		Log.v(TAG, "Do Swipe Up");
		int socre = 0;
		for(int j = 0; j < MAP_SIZE; j++){
			for(int i = 1; i < MAP_SIZE; i++){//start from second number
				//skip the empty number 
				if(numberMap[i][j].isEmpty()){
					continue;
				}
				
				int index = i;
				while(index > 0){
					//compare with the above number
					if(numberMap[index-1][j].isEmpty()){
						//Above number is empty, replace it
						numberMap[index-1][j].setNumber(numberMap[index][j]);
						numberMap[index][j].clear();
						index--;
					}else if(!numberMap[index-1][j].hasCombine() && !numberMap[index][j].hasCombine() &&
							numberMap[index][j].getNumber() == numberMap[index-1][j].getNumber()){
						//If same number and not combine, combine with it
						numberMap[index-1][j].combine();
						numberMap[index][j].clear();
						socre += numberMap[index-1][j].getNumber();
						index--;
					}else{
						//Above number is different or combined
						break;
					}
				}
			}
		}
		return socre;
	}
	
	private int doSwipeDown(){
		Log.v(TAG, "Do Swipe Down");
		return 0;
	}
	
	private int doSwipeLeft(){
		Log.v(TAG, "Do Swipe Left");
		return 0;
	}
	
	private int doSwipeRight(){
		Log.v(TAG, "Do Swipe Right");
		return 0;
	}
	
	private void setNumberInMap(int row, int column, int value){
		Log.v(TAG, String.format("Set Number In Puzzle Map [%d,%d] = %d", row, column, value));
		PuzzleNumber number = numberMap[row][column];
		number.setNumber(value);
	}
	
	private void initMap(){
		
		Log.v(TAG, "Init the Puzzle Map");
		//Init the map and empty list
		for(int i = 0; i < MAP_SIZE; i++){
			for(int j = 0; j < MAP_SIZE; j++){
				numberMap[i][j] = new PuzzleNumber();
			}
		}
	
		//Create two init number
		generateNewNumberInMap();
		generateNewNumberInMap();
		
		//dump the map for test
		dumpNumberMap();
	}
	
	private int mapPosition2mapIndex(int row, int column){
		return row * MAP_SIZE + column;
	}
	
	private MapPosition mapIndex2mapPosition(int mapIndex){
		int row = mapIndex / MAP_SIZE;
		int column = mapIndex % MAP_SIZE;
		return new MapPosition(row, column);
	}
	
	private void dumpNumberMap(){
		StringBuilder builder = new StringBuilder();
		for(int i = 0; i < MAP_SIZE; i++){
			builder.append("---------\r\n");
			for(int j = 0; j < MAP_SIZE; j++){
				builder.append("|");
				builder.append(numberMap[i][j].getNumber());
			}
			builder.append("|\r\n");
		}
		builder.append("---------\r\n");
		Log.d(TAG, "Number Map dump"); 
		Log.d(TAG, builder.toString()); 
	}
	
}
