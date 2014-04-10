package com.cc.puzzle.logic;

class MapPosition {
	
	private int row;
	private int column;
	
	public MapPosition(int row, int column){
		this.row = row;
		this.column = column;
	}
	
	public int getRow(){
		return this.row;
	}
	
	public int getColumn(){
		return this.column;
	}
}
