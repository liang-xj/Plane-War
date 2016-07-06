package com.example.mygame1.object;

import com.example.mygame1.GameView;
import com.example.mygame1.R;

import android.content.Context;

import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Canvas;

public class PlayerBullet extends GameObject {

	public static final int LEVEL_1 = 0;
	public static final int LEVEL_2 = 1;
	public static final int LEVEL_3 = 2;
	public static final int LIGHT = 3;
 
	private int speed;
	public int level;
	

	public PlayerBullet(Context context, float x, float y, int speed, int level) {
		this.x = x;
		this.y = y;
		this.speed = speed;
		this.level = level;
		init(context, level);
	}

	private void init(Context context, int level) {
		switch (level) {
		case LEVEL_1: {
			Options options = new Options();
			options.inSampleSize = 2;
			bitmap = BitmapFactory.decodeResource(context.getResources(),
					R.drawable.player_bullet_level_1, options);

			bitmapWidth = bitmap.getWidth();
			bitmapHeigth = bitmap.getHeight();
			break;
		}
		case LEVEL_2: {
			Options options = new Options();
			options.inSampleSize = 2;
			bitmap = BitmapFactory.decodeResource(context.getResources(),
					R.drawable.player_bullet_level_2);
			bitmapWidth = bitmap.getWidth();
			bitmapHeigth = bitmap.getHeight();
			break;
		}
		case LEVEL_3: {
			Options options = new Options();
			options.inSampleSize = 2;
			bitmap = BitmapFactory.decodeResource(context.getResources(),
					R.drawable.player_bullet_level_3);
			bitmapWidth = bitmap.getWidth();
			bitmapHeigth = bitmap.getHeight();
			break;
		}
		case LIGHT: {
			Options options = new Options();
			options.inSampleSize = 2;
			bitmap = BitmapFactory.decodeResource(context.getResources(),
					R.drawable.light);
			bitmapWidth = bitmap.getWidth();
			bitmapHeigth = bitmap.getHeight();
			break;
		}

		default:
			break;
		}
	}

	public void draw(Canvas canvas) {
		if (isAlive) {
			canvas.drawBitmap(bitmap, x - bitmapWidth / 2,
					y - bitmapHeigth / 2, null);
		}
		/*
		 * if(isHit){
		 * 
		 * pictureCount++; canvas.drawBitmap(bitmapBoom, x - bitmapWidth / 2, y
		 * - bitmapHeigth / 2, null); if(pictureCount==4){ pictureCount=0;
		 * isAlive=false; } }
		 */
	}

	public void update() {
		if (!isHit) {
			update(level);
		}
	}

	private void update(int level) {
		switch (level) {
		case LEVEL_1: {
			if (!isHit) {
				y -= speed;

				if (y < 0) {
					isAlive = false;
				}
			}
			break;
		}
		case LEVEL_2: {
			if (!isHit) {
				y -= speed;

				if (y < 0) {
					isAlive = false;
				}
			}
			break;
		}
		case LEVEL_3: {
			if (!isHit) {
				y -= speed;

				if (y < 0) {
					isAlive = false;
				}
			}
			break;
		}
		case LIGHT: {
			if (x > GameView.canvasWidth / 2) {
				x = x + speed;
				
				if (x >= GameView.canvasWidth) {
					speed = -speed;
					
				}
				if (x <= GameView.canvasWidth / 2) {
					isAlive = false;
				}
			}else{
				x = x - speed;
				if (x <= 0) {
					speed = -speed;
					
				}
				if (x >= GameView.canvasWidth / 2) {
					isAlive = false;
				}
			}
			break;
		}

		default:
			break;
		}
	}

	public void recycle() {
		bitmap.recycle();

	}
}