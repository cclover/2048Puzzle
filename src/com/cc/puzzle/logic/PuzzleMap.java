package com.cc.puzzle.logic;

import java.util.LinkedList;

import com.cc.puzzle.logic.PuzzleModel.PuzzleOperate;


import android.util.Log;

class PuzzleMap {
	
	private final String TAG = "PuzzleMap";
	private final int MAP_SIZE = 4;
	private PuzzleNumber[][] numberMap;
	
	public PuzzleMap(){
		//Init the map
		numberMap = new PuzzleNumber[MAP_SIZE][MAP_SIZE];
		for(int i = 0; i < MAP_SIZE; i++){
			for(int j = 0; j < MAP_SIZE; j++){
				numberMap[i][j] = new PuzzleNumber();
			}
		}
	}
	
	public void initMap(){
		Log.v(TAG, "Init the Puzzle Map");
		
		//Create two init number
		generateNewNumberInMap();
		generateNewNumberInMap();
	}
	
	public void resetMap(){
		
		Log.v(TAG, "Reset the Puzzle Map");
		for(int i = 0; i < MAP_SIZE; i++){
			for(int j = 0; j < MAP_SIZE; j++){
				numberMap[i][j].clear();
			}
		}
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
		
		//dump map
		dumpNumberMap();
	}
	
	public int doSwipe(PuzzleOperate operate){
		int ret = 0;
		switch (operate) {
		case SWIPE_UP:
			ret = doSwipeUp();
			break;
		case SWIPE_DOWN:
			ret = doSwipeDown();
			break;
		case SWIPE_LEFT:
			ret = doSwipeLeft();
			break;
		case SWIPE_RIGHT:
			ret = doSwipeRight();
			break;
		default:
			ret = 0;
			break;
		}
		//Clear combine flag
		for(int i = 0; i < MAP_SIZE; i++){
			for(int j = 0; j < MAP_SIZE; j++){
				if(numberMap[i][j].hasCombine()){
					numberMap[i][j].clearCombine();
				}
			}
		}
		
		//Dump the map
		dumpNumberMap();
		return ret;
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
		
		dumpNumberMap();
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
		int socre = 0;
		for(int j = 0; j < MAP_SIZE; j++){
			for(int i = MAP_SIZE - 2; i >= 0; i--){
				//skip the empty number 
				if(numberMap[i][j].isEmpty()){
					continue;
				}
				
				int index = i;
				while(index < MAP_SIZE - 1){
					//compare with the below number
					if(numberMap[index+1][j].isEmpty()){
						//Above number is empty, replace it
						numberMap[index+1][j].setNumber(numberMap[index][j]);
						numberMap[index][j].clear();
						index++;
					}else if(!numberMap[index+1][j].hasCombine() && !numberMap[index][j].hasCombine() &&
							numberMap[index][j].getNumber() == numberMap[index+1][j].getNumber()){
						//If same number and not combine, combine with it
						numberMap[index+1][j].combine();
						numberMap[index][j].clear();
						socre += numberMap[index+1][j].getNumber();
						index++;
					}else{
						//Below number is different or combined
						break;
					}
				}
			}
		}
		return socre;
	}
	
	private int doSwipeLeft(){
		Log.v(TAG, "Do Swipe Left");
		int socre = 0;
		for(int i = 0; i < MAP_SIZE; i++){
			for(int j = 1; j < MAP_SIZE; j++){
				//skip the empty number 
				if(numberMap[i][j].isEmpty()){
					continue;
				}
				
				int index = j;
				while(index > 0){
					//compare with the below number
					if(numberMap[i][index-1].isEmpty()){
						//Above number is empty, replace it
						numberMap[i][index-1].setNumber(numberMap[i][index]);
						numberMap[i][index].clear();
						index--;
					}else if(!numberMap[i][index-1].hasCombine() && !numberMap[i][index].hasCombine() &&
							numberMap[i][index].getNumber() == numberMap[i][index-1].getNumber()){
						//If same number and not combine, combine with it
						numberMap[i][index-1].combine();
						numberMap[i][index].clear();
						socre += numberMap[i][index-1].getNumber();
						index--;
					}else{
						//Below number is different or combined
						break;
					}
				}
			}
		}
		return socre;
	}
	
	private int doSwipeRight(){
		Log.v(TAG, "Do Swipe Right");
		int socre = 0;
		for(int i = 0; i < MAP_SIZE; i++){
			for(int j = MAP_SIZE - 2; j >= 0; j--){
				//skip the empty number 
				if(numberMap[i][j].isEmpty()){
					continue;
				}
				
				int index = j;
				while(index < MAP_SIZE - 1){
					//compare with the below number
					if(numberMap[i][index+1].isEmpty()){
						//Above number is empty, replace it
						numberMap[i][index+1].setNumber(numberMap[i][index]);
						numberMap[i][index].clear();
						index++;
					}else if(!numberMap[i][index+1].hasCombine() && !numberMap[i][index].hasCombine() &&
							numberMap[i][index].getNumber() == numberMap[i][index+1].getNumber()){
						//If same number and not combine, combine with it
						numberMap[i][index+1].combine();
						numberMap[i][index].clear();
						socre += numberMap[i][index+1].getNumber();
						index++;
					}else{
						//Below number is different or combined
						break;
					}
				}
			}
		}
		return socre;
	}
	
	private void setNumberInMap(int row, int column, int value){
		Log.v(TAG, String.format("Set Number In Puzzle Map [%d,%d] = %d", row, column, value));
		PuzzleNumber number = numberMap[row][column];
		number.setNumber(value);
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
