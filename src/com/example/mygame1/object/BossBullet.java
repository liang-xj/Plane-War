package com.example.mygame1.object;

import com.example.mygame1.GameView;
import com.example.mygame1.R;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

public class BossBullet extends GameObject {

	public static final int DEFULT_SPEED = 10;

	private int speed = DEFULT_SPEED;

	private int dir;// 当前Boss子弹方向
	// 8方向常量
	public static final int DIR_UP = -1;
	public static final int DIR_DOWN = 2;
	public static final int DIR_LEFT = 3;
	public static final int DIR_RIGHT = 4;
	public static final int DIR_UP_LEFT = 5;
	public static final int DIR_UP_RIGHT = 6;
	public static final int DIR_DOWN_LEFT = 7;
	public static final int DIR_DOWN_RIGHT = 8;

	public BossBullet(Context context, float x, float y, int speed, int dir) {

		init(context);

		this.speed = speed;
		this.x = x;
		this.y = y;
		this.dir = dir;
	}

	private void init(Context context) {
		bitmap = BitmapFactory.decodeResource(context.getResources(),
				R.drawable.boos_bullet);
		bitmapWidth = bitmap.getWidth();
		bitmapHeigth = bitmap.getHeight();

	}

	public void draw(Canvas canvas) {
		formatBorder();
		canvas.drawBitmap(bitmap, x - bitmapWidth / 2, y - bitmapHeigth / 2,
				null);
	}

	public void update() {
		formatBorder();
		switch (dir) {

		// 方向下的子弹
		case DIR_DOWN:
			y += speed;
			break;

		// 方向左下的子弹
		case DIR_DOWN_LEFT:
			x -= speed;
			y += speed;
			break;
		// 方向右下的子弹
		case DIR_DOWN_RIGHT:
			x += speed;
			y += speed;
			break;

		}
	}

	private void formatBorder() {
		if (x < bitmapWidth / 2) {
			isAlive = false;
		}
		if (x > GameView.canvasWidth - bitmapWidth / 2) {
			isAlive = false;
		}
		if (y > GameView.canvasHeight) {
			isAlive = false;
		}
		if (y < bitmapHeigth / 2) {
			isAlive = false;
		}

	}

	@Override
	public void recycle() {
		if (bitmap != null && bitmap.isRecycled()) {
			bitmap.recycle();
		}
	}

}
