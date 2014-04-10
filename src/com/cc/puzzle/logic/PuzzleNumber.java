package com.cc.puzzle.logic;

class PuzzleNumber {
	
	private final int EMPTY_VALUE = 0;
	private int numValue;
	private Boolean hasCombine;
	
	public PuzzleNumber(){
		numValue = EMPTY_VALUE;
		hasCombine = false;
	}
	
	public void setNumber(int num){
		numValue = num;
	}
	
	public void setNumber(PuzzleNumber num){
		numValue = num.getNumber();
	}
	
	public void combine(){
		numValue *= 2;
		hasCombine = true;
	}
	
	public void clear(){
		numValue = EMPTY_VALUE;
		hasCombine = false;
	}
	
	public void clearCombine(){
		hasCombine = false;
	}
	
	public int getNumber(){
		return numValue;
	}
	
	public Boolean isEmpty(){
		return numValue == EMPTY_VALUE;
	}
	
	public Boolean hasCombine(){
		return hasCombine;
	}
}
