package com.example.mygame1;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;

public class BackGround {
	private static final int SPEED = 5;
	private Bitmap bitmap;
	private int bitmapWidth;
	private int bitmapHeight;

	private int backGroundY1;
	private int backGroundY2;

	public int backGroundCount = 0;

	public BackGround(Context context, int resId) {
		bitmap = BitmapFactory.decodeResource(context.getResources(),
				R.drawable.background);
		bitmapWidth = bitmap.getWidth();
		bitmapHeight = bitmap.getHeight();
		backGroundY1 = 0;
		backGroundY2 = -GameView.canvasHeight;
	}

	public void draw(Canvas canvas) {
		Rect src = new Rect(0, 0, bitmapWidth, bitmapHeight);
		Rect dst1 = new Rect(0, backGroundY1, GameView.canvasWidth,
				backGroundY1 + GameView.canvasHeight);
		Rect dst2 = new Rect(0, backGroundY2, GameView.canvasWidth,
				backGroundY2 + GameView.canvasHeight);
		canvas.drawBitmap(bitmap, src, dst1, null);
		canvas.drawBitmap(bitmap, src, dst2, null);
	}

	public void update() {
		backGroundY1 += SPEED;
		backGroundY2 += SPEED;
		if (backGroundY1 >= GameView.canvasHeight) {
			backGroundY1 = -GameView.canvasHeight;
			backGroundCount++;
		}
		if (backGroundY2 >= GameView.canvasHeight) {
			backGroundY2 = -GameView.canvasHeight;
			backGroundCount++;
		}
	}

	public void recycle() {
		if (bitmap != null && !bitmap.isRecycled()) {
			bitmap.recycle();

		}

	}
}
