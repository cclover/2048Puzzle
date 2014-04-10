package com.cc.puzzle.logic;

class PuzzleUtils {
	
	public enum PuzzleOperate{
		SWIPE_UP,
		SWIPE_DOWN,
		SWIPE_LEFT,
		SWIPE_RIGHT,
	}
	
	public static int generateNumber(){
		int count = (int)(Math.random()*10)+1; //1-10
		if(count % 2 == 0){
			return 2;
		}
		return 4;
	}
	
	public static int generateRandonEmptyPosition(int emptyListCount){
		return (int)(Math.random() * emptyListCount);
	}
}