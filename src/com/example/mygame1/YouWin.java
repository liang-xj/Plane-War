package com.example.mygame1;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;

public class YouWin {
	private Bitmap bitmap;
	private Bitmap bitmapscore;
	private int bitmapWidth;
	private int bitmapHeight;
	private int YouWinY;

	public YouWin(Context context, int resId) {
		bitmap = BitmapFactory.decodeResource(context.getResources(),
				R.drawable.you_win);
		bitmapscore = BitmapFactory.decodeResource(context.getResources(),
				R.drawable.game_score);
		bitmapWidth = bitmap.getWidth();
		bitmapHeight = bitmap.getHeight();
		YouWinY = 0;
	}

	public void draw(Canvas canvas) {
		Rect src = new Rect(0, 0, bitmapWidth, bitmapHeight);
		Rect dst = new Rect(0, YouWinY, GameView.canvasWidth, YouWinY
				+ GameView.canvasHeight);
		canvas.drawBitmap(bitmap, src, dst, null);
		canvas.drawBitmap(bitmapscore, GameView.canvasWidth / 6,
				GameView.canvasHeight * 3 / 4, null);

	}

	public void recycle() {
		if (bitmap != null && !bitmap.isRecycled()) {
			bitmap.recycle();

		}
	}
}
