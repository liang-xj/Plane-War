package com.example.mygame1;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;

public class GameOver {
	private Bitmap bitmap;
	private Bitmap bitmapscore;
	private int bitmapWidth;
	private int bitmapHeight;
	private int YouWinY;

	public GameOver(Context context, int resId) {
		bitmap = BitmapFactory.decodeResource(context.getResources(),
				R.drawable.game_over);
		bitmapWidth = bitmap.getWidth();
		bitmapHeight = bitmap.getHeight();

	}

	public void draw(Canvas canvas) {
		Rect src = new Rect(0, 0, bitmapWidth, bitmapHeight);
		Rect dst = new Rect(0, YouWinY, GameView.canvasWidth, YouWinY
				+ GameView.canvasHeight);
		canvas.drawBitmap(bitmap, src, dst, null);

	}

	public void recycle() {
		if (bitmap != null && !bitmap.isRecycled()) {
			bitmap.recycle();

		}
	}
}
