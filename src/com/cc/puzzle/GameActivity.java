package com.cc.puzzle;

import com.cc.puzzle.logic.PuzzleModel;
import com.cc.puzzle.logic.PuzzleModelListener;
import com.cc.puzzle.logic.PuzzleModel.PuzzleOperate;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TextView;



public class GameActivity extends Activity implements View.OnClickListener, View.OnTouchListener, OnGestureListener, PuzzleModelListener {

	private final String TAG = "GameActivity";
	
	//Operate button
	private Button btnStart;
	private Button btnRestart;
	
	//Score lable
	private TextView txtSocre;
	private TableLayout tableMap;
	private TextView[][] numberArray;
	
	private PuzzleModel puzzleModel;
	
	private int newRow, newColumn;
	
	private GestureDetector mGestureDetector;  
	private final int FLING_MIN_DISTANCE_X = 100;  
	private final int FLING_MIN_DISTANCE_Y = 80;  
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		//Get the button instance
		btnStart = (Button)findViewById(R.id.btnStart);
		btnRestart = (Button)findViewById(R.id.btnReset);
		
		//Set click listener
		btnStart.setOnClickListener(this);
		btnRestart.setOnClickListener(this);
		
		//Get the socre textview
		txtSocre = (TextView)findViewById(R.id.lableScore);
		
		//Get the number textview
		numberArray = new TextView[4][4];
		numberArray[0][0] = (TextView)findViewById(R.id.txt_row0_column1);
		numberArray[0][1] = (TextView)findViewById(R.id.txt_row0_column2);
		numberArray[0][2] = (TextView)findViewById(R.id.txt_row0_column3);
		numberArray[0][3] = (TextView)findViewById(R.id.txt_row0_column4);
		numberArray[1][0] = (TextView)findViewById(R.id.txt_row1_column1);
		numberArray[1][1] = (TextView)findViewById(R.id.txt_row1_column2);
		numberArray[1][2] = (TextView)findViewById(R.id.txt_row1_column3);
		numberArray[1][3] = (TextView)findViewById(R.id.txt_row1_column4);
		numberArray[2][0] = (TextView)findViewById(R.id.txt_row2_column1);
		numberArray[2][1] = (TextView)findViewById(R.id.txt_row2_column2);
		numberArray[2][2] = (TextView)findViewById(R.id.txt_row2_column3);
		numberArray[2][3] = (TextView)findViewById(R.id.txt_row2_column4);
		numberArray[3][0] = (TextView)findViewById(R.id.txt_row3_column1);
		numberArray[3][1] = (TextView)findViewById(R.id.txt_row3_column2);
		numberArray[3][2] = (TextView)findViewById(R.id.txt_row3_column3);
		numberArray[3][3] = (TextView)findViewById(R.id.txt_row3_column4);
		
		//Table swipe event
		tableMap = (TableLayout)findViewById(R.id.tableMap);
		tableMap.setOnTouchListener(this);
		
		mGestureDetector = new GestureDetector(getApplicationContext(), this);
		
		//Create the puzzle model
		puzzleModel = new PuzzleModel();
		puzzleModel.setListener(this);
	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		if(id == R.id.btnStart){
			puzzleModel.start();
		}else if(id == R.id.btnReset){
			puzzleModel.restart();
		}
	}
	
	@Override
	public void onPuzzleDateChanged(int[][] mapData) {
		for(int i = 0; i < 4; i++){
			for(int j = 0; j < 4; j++){
				numberArray[i][j].setText(String.valueOf(mapData[i][j]));
				numberArray[i][j].setBackgroundColor(Color.TRANSPARENT);
			}
		}
	}

	@Override
	public void onPuzzleScoreChanged(int newScore, int totalScore) {
		txtSocre.setText(String.valueOf(totalScore));
	}

	@Override
	public void onPuzzleNewNumber(int row, int column, int value) {
		numberArray[row][column].setBackgroundColor(Color.YELLOW);
		numberArray[row][column].setText(String.valueOf(value));
		newRow = row;
		newColumn = column;
	}

	@Override
	public void onPuzzleDateUnChanged() {
		numberArray[newRow][newColumn].setBackgroundColor(Color.TRANSPARENT);
	}

	
	
	@Override
	public boolean onDown(MotionEvent e) {
		Log.v(TAG, "onDown");
		return true;
	}

	@Override
	public void onShowPress(MotionEvent e) {
		Log.v(TAG, "onShowPress");
		
	}

	@Override
	public boolean onSingleTapUp(MotionEvent e) {
		Log.v(TAG, "onSingleTapUp");
		return false;
	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
		Log.v(TAG, "onScroll");
		return false;
	}

	@Override
	public void onLongPress(MotionEvent e) {
		Log.v(TAG, "onLongPress");
	}

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
		if (e1.getX()-e2.getX() > FLING_MIN_DISTANCE_X 
				&& Math.abs(velocityX) > Math.abs(velocityY)) {
			// Fling Left
			Log.v(TAG, "onFling --- left");
			puzzleModel.swipe(PuzzleOperate.SWIPE_LEFT);
		} else if (e2.getX()-e1.getX() > FLING_MIN_DISTANCE_X   
		        && Math.abs(velocityX) > Math.abs(velocityY)) {   
		    // Fling right   
			Log.v(TAG, "onFling --- right");
			puzzleModel.swipe(PuzzleOperate.SWIPE_RIGHT);
		} else if(e1.getY()-e2.getY() > FLING_MIN_DISTANCE_Y
		        && Math.abs(velocityY) > Math.abs(velocityX)){
		    // Fling up
			Log.v(TAG, "onFling --- up");
			puzzleModel.swipe(PuzzleOperate.SWIPE_UP);
		}else if(e2.getY()-e1.getY() > FLING_MIN_DISTANCE_Y  
		       && Math.abs(velocityY) > Math.abs(velocityX)){
			// Fling down
			Log.v(TAG, "onFling --- downs");
			puzzleModel.swipe(PuzzleOperate.SWIPE_DOWN);
		}
		return true;
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		Log.v(TAG, "onTouch");
		return mGestureDetector.onTouchEvent(event);
	}
}
