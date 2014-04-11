package com.cc.puzzle.logic;

public interface PuzzleModelListener {
	
	public void onPuzeleStart(int[][] mapData, int highScore);
	
	public void onPuzzleDateChanged(int[][] mapData);
	
	public void onPuzzleScoreChanged(int newScore, int totalScore);
	
	public void onPuzzleNewNumber(int row, int column, int value);
	
	public void onPuzzleOver(int totalScore);
}
