package com.cc.puzzle.logic;

import android.util.Log;

public class PuzzleModel {

	private final String TAG = "PuzzleModel";
	private PuzzleMap puzzleMap;
	private int totalScore;
	private int highScore;
	private Boolean isGameStart;
	private Boolean canSwipe;
	private PuzzleModelListener listener;
	
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
		canSwipe = false;
	}
	
	public void setListener(PuzzleModelListener listener){
		this.listener = listener;
		puzzleMap.setListener(listener);
	}
	
	public void start(){
		if(isGameStart){
			return;
		}
		Log.v(TAG, "Start the Game");
		totalScore = 0;
		puzzleMap.initMap();
		isGameStart = true;
		canSwipe = true;
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
		
		if(!canSwipe){
			Log.v(TAG, "In swipe process");
			return;
		}
		
		canSwipe = false;
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
			if(listener != null){
				listener.onPuzzleScoreChanged(score, totalScore);
			}
			Log.d(TAG, String.format("Total Score : %d(+%d)", totalScore, score));
		}
		
		canSwipe = true;
	}

}
