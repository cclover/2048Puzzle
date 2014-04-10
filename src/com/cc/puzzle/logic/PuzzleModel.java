package com.cc.puzzle.logic;

import com.cc.puzzle.logic.PuzzleUtils.PuzzleOperate;

import android.util.Log;

public class PuzzleModel {

	private final String TAG = "PuzzleModel";
	private PuzzleMap puzzleMap;
	private int totalScore;
	
	public PuzzleModel(){
		puzzleMap = new PuzzleMap();
		totalScore = 0;
	}
	
	public void swipe(PuzzleOperate operate){
		//Check the map
		if(puzzleMap.isMapFull()){
			Log.v(TAG, "The map is full, check is over or not");
			if(puzzleMap.isOver()){
				Log.v(TAG, "Game Over!");	
				return;
			}
		}
		
		//Do swipe in map
		int score = puzzleMap.doSwipe(operate);
		if(score > 0){
			totalScore += score;
		}
		
		//Generate the new number
		puzzleMap.generateNewNumberInMap();
	}
	

	
	
	
}
