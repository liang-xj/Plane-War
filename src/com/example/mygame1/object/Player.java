package com.example.mygame1.object;

import com.example.mygame1.GameView;
import com.example.mygame1.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

public class Player extends GameObject {
	public static final int DEFAULT_SPEED = 15;
	public static final int PROTECT_TIME = 100;
	public int blood;
	public int protectCount = 0;
	public float speed;
	
	public boolean isBoom = false;
	public boolean isProtect = false;
	
	private Bitmap bitmapHit[] = new Bitmap[2];
	private Bitmap bitmapProtect;
	public Bitmap bitmap;
	private int pbCount;

	public Player(Context context, int blood, float speed) {

		bitmap = BitmapFactory.decodeResource(context.getResources(),
				R.drawable.player);
		bitmapHit[0] = BitmapFactory.decodeResource(context.getResources(),
				R.drawable.player1);
		bitmapHit[1] = BitmapFactory.decodeResource(context.getResources(),
				R.drawable.player2);
		bitmapProtect = BitmapFactory.decodeResource(context.getResources(),
				R.drawable.player_protect);
		bitmapWidth = bitmap.getWidth();
		bitmapHeigth = bitmap.getHeight();

		x = GameView.canvasWidth / 2;
		y = GameView.canvasHeight;
		this.blood = blood;
		if (speed == 0) {
			this.speed = DEFAULT_SPEED;
		} else {
			this.speed = speed;
		}

	}

	public void draw(Canvas canvas) {
		formatBorder();

		if (isAlive) {
			if (isProtect) {

				canvas.drawBitmap(bitmapProtect, x - bitmapWidth / 2, y
						- bitmapHeigth, null);
				protectCount++;
				if (protectCount > PROTECT_TIME) {
					isProtect = false;
					protectCount = 0;
				}
			} else {
				canvas.drawBitmap(bitmap, x - bitmapWidth / 2,
						y - bitmapHeigth, null);
			}
		}
		if (isHit && !isProtect) {
			pbCount++;
			if (pbCount <= 3) {
				canvas.drawBitmap(bitmapHit[0], x - bitmapWidth / 2, y
						- bitmapHeigth, null);
			}
			if (pbCount > 3) {
				canvas.drawBitmap(bitmapHit[1], x - bitmapWidth / 2, y
						- bitmapHeigth, null);
			}
			if (pbCount > 7) {
				pbCount = 0;
				isHit = false;
			}

		}
	}

	public void update(float moveToX, float moveToY) {
		double xLenth = moveToX - x;
		double yLenth = moveToY - y;
		double lenth = Math.sqrt(xLenth * xLenth + yLenth * yLenth);
		double count = lenth / speed;
		if (count < 1) {
			x = moveToX;

			y = moveToY;
		} else {
			double xToMove = xLenth / count;
			double yToMove = yLenth / count;
			x += xToMove;
			y += yToMove;
		}

	}

	private void formatBorder() {
		if (x < bitmapWidth / 2) {
			x = bitmapWidth / 2;
		}
		if (x > GameView.canvasWidth - bitmapWidth / 2) {
			x = GameView.canvasWidth - bitmapWidth / 2;
		}
		if (y < bitmapHeigth / 2) {
			y = bitmapWidth / 2;
		}
		if (y > GameView.canvasHeight - bitmapHeigth / 2) {
			y = GameView.canvasHeight - bitmapHeigth / 2;
		}
	}

	public void recycle() {
		bitmap.recycle();
	}
}
