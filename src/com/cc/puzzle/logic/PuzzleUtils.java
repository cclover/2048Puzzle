package com.cc.puzzle.logic;

class PuzzleUtils {
	
	public static int generateNumber(){
		int count = (int)(Math.random()*10)+1; //1-10
		if(count < 7){
			return 2;
		}
		return 4;
	}
	
	public static int generateRandonEmptyPosition(int emptyListCount){
		return (int)(Math.random() * emptyListCount);
	}
}