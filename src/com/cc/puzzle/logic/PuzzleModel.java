package com.cc.puzzle.logic;

import android.util.Log;

public class PuzzleModel {

	private final String TAG = "PuzzleModel";
	private PuzzleMap puzzleMap;
	private int totalScore;
	private int highScore;
	private Boolean isGameStart;
	
	public enum PuzzleOperate{
		SWIPE_UP,
		SWIPE_DOWN,
		SWIPE_LEFT,
		SWIPE_RIGHT,
	}
	
	public PuzzleModel(){
		puzzleMap = new PuzzleMap();
		totalScore = 0;
		highScore = 0;
		isGameStart = false;
	}
	
	public void start(){
		Log.v(TAG, "Start the Game");
		totalScore = 0;
		puzzleMap.initMap();
		isGameStart = true;
	}
	
	public void restart(){
		Log.v(TAG, "Restart the Game");
		isGameStart = false;
		puzzleMap.resetMap();
		start();
	}
	
	public void swipe(PuzzleOperate operate){
		
		if(!isGameStart){
			Log.w(TAG, "Game not start!");
			return;
		}
		
		//Check the map
		if(puzzleMap.isMapFull()){
			Log.v(TAG, "The map is full, check is over or not");
			if(puzzleMap.isOver()){
				isGameStart = false;
				Log.v(TAG, "Game Over!");	
				return;
			}
		}
		
		//Do swipe in map
		int score = puzzleMap.doSwipe(operate);
		if(score > 0){
			totalScore += score;
			Log.d(TAG, String.format("Total Score : %d(+%d)", totalScore, score));
		}
		
		//Generate the new number
		puzzleMap.generateNewNumberInMap();
	}
	

	
	
	
}
