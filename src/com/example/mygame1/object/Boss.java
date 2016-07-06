package com.example.mygame1.object;

import com.example.mygame1.GameView;
import com.example.mygame1.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

public class Boss extends GameObject {
	public static final int DEFAULT_SPEED = 2;
	public static final int DEFAULT_BLOOD = 30;
	private static final int CRAZY_TIME = 100;
	public int blood = DEFAULT_BLOOD;
	private int pictureCount = 0;
	private int pictureCount2 = 0;
	private int crazyTimeCount;
	public float speed;


	private boolean direction = true;// BOSS左右移动方向，true表示向右
	public boolean isBoom = false;
	public boolean isHit = false;
	public boolean isCrazy = false;
	

	private Bitmap bitmapBoom[] = new Bitmap[2];
	private Bitmap bitmapHit;
	public Bitmap bitmap;

	public Boss(Context context, int blood, float speed) {

		bitmap = BitmapFactory.decodeResource(context.getResources(),
				R.drawable.boss);
		bitmapBoom[0] = BitmapFactory.decodeResource(context.getResources(),
				R.drawable.boss2);
		bitmapBoom[1] = BitmapFactory.decodeResource(context.getResources(),
				R.drawable.boss_disappear);
		bitmapHit = BitmapFactory.decodeResource(context.getResources(),
				R.drawable.boss1);
		bitmapWidth = bitmap.getWidth();
		bitmapHeigth = bitmap.getHeight();

		x = GameView.canvasWidth / 2;
		y = -bitmapHeigth / 2;
		this.blood = blood;
		if (speed == 0) {
			this.speed = DEFAULT_SPEED;
		} else {
			this.speed = speed;
		}

	}

	public void draw(Canvas canvas) {
		formatBorder();
		if (!isHit && !isBoom) {
			canvas.drawBitmap(bitmap, x - bitmapWidth / 2, y - bitmapHeigth,
					null);
		}
		if (isHit && !isBoom) {
			pictureCount++;
			canvas.drawBitmap(bitmapHit, x - bitmapWidth / 2, y - bitmapHeigth,
					null);
			if (pictureCount >= 4) {
				pictureCount = 0;
				isHit = false;
			}

		}
		if (isBoom && isHit) {
			pictureCount2++;
			if (pictureCount2 <= 6) {
				canvas.drawBitmap(bitmapBoom[0], x - bitmapWidth / 2, y
						- bitmapHeigth, null);
			}
			if (pictureCount2 > 6) {
				canvas.drawBitmap(bitmapBoom[1], x - bitmapBoom[1].getWidth()
						/ 2, y - bitmapBoom[1].getHeight(), null);
			}
			if (pictureCount2 == 10) {
				pictureCount2 = 0;

				isBoom = false;

			}
		}
	}

	public void update() {
		if (y < bitmapHeigth * 2) {
			y += speed;
		} else {
			if (direction) {
				x += speed;
				if (x > GameView.canvasWidth - bitmapWidth) {
					direction = false;
				}
			} else {
				x -= speed;
				if (x < bitmapWidth) {
					direction = true;
				}

			}
		}
		if (isAlive) {
			if (isCrazy == false) {
				x += speed;
				if (x >= GameView.canvasWidth - bitmapWidth / 2) {
					speed = -speed;
				} else if (x <= bitmapWidth / 2) {
					speed = -speed;
				}
				crazyTimeCount++;
				if (crazyTimeCount % CRAZY_TIME == 0) {
					isCrazy = true;
					speed = 25;
				}
			} else {
				speed--;
				y += speed;
				if (y <= bitmapHeigth / 2) {
					isCrazy = false;
					speed = DEFAULT_SPEED;
				}
			}

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
		if (bitmap != null && bitmap.isRecycled()) {
			bitmap.recycle();
		}
	}

}
