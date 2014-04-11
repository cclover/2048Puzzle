package com.cc.puzzle;

import com.cc.puzzle.logic.PuzzleModel;
import com.cc.puzzle.logic.PuzzleModel.PuzzleOperate;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;



public class GameActivity extends Activity implements View.OnClickListener {

	private Button btnUp;
	private Button btnDown;
	private Button btnLeft;
	private Button btnRight;
	private Button btnStart;
	private Button btnRestart;
	
	private PuzzleModel puzzleModel;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		//Get the button instance
		btnUp = (Button)findViewById(R.id.btnUP);
		btnDown = (Button)findViewById(R.id.btnDown);
		btnLeft = (Button)findViewById(R.id.btnLeft);
		btnRight = (Button)findViewById(R.id.btnRight);
		btnStart = (Button)findViewById(R.id.btnStart);
		btnRestart = (Button)findViewById(R.id.btnReset);
		
		//Set click listener
		btnUp.setOnClickListener(this);
		btnDown.setOnClickListener(this);
		btnLeft.setOnClickListener(this);
		btnRight.setOnClickListener(this);
		btnStart.setOnClickListener(this);
		btnRestart.setOnClickListener(this);
		
		//Create the puzzle model
		puzzleModel = new PuzzleModel();
	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		if(id == R.id.btnStart){
			puzzleModel.start();
		}else if(id == R.id.btnReset){
			puzzleModel.restart();
		}else if(id == R.id.btnUP){
			puzzleModel.swipe(PuzzleOperate.SWIPE_UP);
		}else if(id == R.id.btnDown){
			puzzleModel.swipe(PuzzleOperate.SWIPE_DOWN);
		}else if(id == R.id.btnLeft){
			puzzleModel.swipe(PuzzleOperate.SWIPE_LEFT);
		}else if(id == R.id.btnRight){
			puzzleModel.swipe(PuzzleOperate.SWIPE_RIGHT);
		}
		
	}
}
