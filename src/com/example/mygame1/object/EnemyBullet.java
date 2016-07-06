package com.example.mygame1.object;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.BitmapFactory.Options;

import com.example.mygame1.GameView;
import com.example.mygame1.R;

public class EnemyBullet extends GameObject {

	public static final int DEFAULT_SPEED = 15;
	public static final int BOSS_BULLET_SPEED = 8;
	public static final int TYPE_NORMAL = 0;
	public static final int TYPE_BOSS = 1;

	private int speed;
	private int type = TYPE_NORMAL;
	private int pictureCount = 0;

	private Bitmap bitmapBoom;

	public EnemyBullet(Context context, float x, float y, int speed, int type) {
		this.x = x;
		this.y = y;
		this.speed = speed;
		this.type = type;
		init(context);
	}

	private void init(Context context) {
		switch (type) {
		case TYPE_NORMAL: {
			Options options = new Options();
			options.inSampleSize = 2;

			bitmap = BitmapFactory.decodeResource(context.getResources(),
					R.drawable.enemy_bullet, options);

			bitmapBoom = BitmapFactory.decodeResource(context.getResources(),
					R.drawable.bullet_boom, options);

			bitmapWidth = bitmap.getWidth();
			bitmapHeigth = bitmap.getHeight();
			break;
		}
		case TYPE_BOSS: {
			Options options = new Options();
			options.inSampleSize = 2;

			bitmap = BitmapFactory.decodeResource(context.getResources(),
					R.drawable.boos_bullet, options);

			bitmapBoom = BitmapFactory.decodeResource(context.getResources(),
					R.drawable.bullet_boom, options);

			bitmapWidth = bitmap.getWidth();
			bitmapHeigth = bitmap.getHeight();
			break;
		}

		default:
			break;
		}

	}

	public void draw(Canvas canvas) {

		if (!isHit || isAlive) {
			canvas.drawBitmap(bitmap, x - bitmapWidth / 2,
					y - bitmapHeigth / 2, null);
		}
		if (isHit) {

			pictureCount++;
			canvas.drawBitmap(bitmapBoom, x - bitmapWidth / 2, y - bitmapHeigth
					/ 2, null);
			if (pictureCount == 5) {
				pictureCount = 0;
				isAlive = false;
			}
		}

	}

	public void update() {
		if (!isHit) {
			update(type);
		}
	}

	private void update(int level) {
		switch (type) {
		case TYPE_NORMAL: {
			y += speed;
			break;
		}
		case TYPE_BOSS: {
			y += speed;
			break;
		}

		default:
			break;
		}
		if (y > GameView.canvasHeight) {
			isAlive = false;
		}
	}

	public void recycle() {
		bitmap.recycle();

	}

}
