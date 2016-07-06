package com.example.mygame1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class MainActivity extends Activity {
	public static final String TAG = "myGame";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
	}

	public void buttonClick(View v) {
		switch (v.getId()) {
		case R.id.startGame:
			startGame();
			break;

		default:
			break;
		}
	}

	private void startGame() {
		Intent intent = new Intent(this, GameActivity.class);
		startActivity(intent);
	}
}
