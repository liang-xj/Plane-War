package com.example.mygame1.object;

import java.util.Random;

import com.example.mygame1.GameView;
import com.example.mygame1.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

public class Enemy extends GameObject {
	public static final int DEFAULT_SPEED = 6;// 默认速度为6
	public static final int TYPE_NORMAL = 0;
	public static final int TYPE_BOOM = 1;
	public static final int DEFAUL_BLOOD = 3;// 默认血量3
	public static final int RANDOM_TIMES = 2;
	public static final int TYPE_BOOM_SPEED = 30;// BOOM类型敌机速度
	public int blood = DEFAUL_BLOOD;

	private int pictureCount = 0;
	private int pictureCount2 = 0;// 用于控制爆炸动画

	public boolean isBoom = false;// 是否爆炸
	public boolean isHit = false;// 是否被击中
	private Bitmap bitmapBoom[] = new Bitmap[2];// 爆炸图片
	private Bitmap bitmapHit;// 被击中图片

	private int speed;
	private int type = TYPE_NORMAL;
	private int randomCount;
	private int flag = 0;
	private int xSpeed = 0;

	public Enemy(Context context, int speed, int type) {
		this.speed = speed;
		this.type = type;
		init(context);

	}

	private void init(Context context) {
		switch (type) {
		case TYPE_NORMAL: {
			bitmap = BitmapFactory.decodeResource(context.getResources(),
					R.drawable.enemy);

			bitmapHit = BitmapFactory.decodeResource(context.getResources(),
					R.drawable.enemy_boom_1);

			bitmapBoom[0] = BitmapFactory.decodeResource(
					context.getResources(), R.drawable.enemy_boom_2);
			bitmapBoom[1] = BitmapFactory.decodeResource(
					context.getResources(), R.drawable.enemy_boom_3);

			bitmapWidth = bitmap.getWidth();
			bitmapHeigth = bitmap.getHeight();

			y = 0;
			Random r = new Random();
			int randomResult = r.nextInt(5);
			x = GameView.canvasWidth * randomResult / (5 - 1);// 随机X的坐标
			break;
		}
		case TYPE_BOOM: {

			bitmap = BitmapFactory.decodeResource(context.getResources(),
					R.drawable.enemy_type_boom_1);

			bitmapHit = BitmapFactory.decodeResource(context.getResources(),
					R.drawable.enemy_type_boom_2);

			bitmapBoom[0] = BitmapFactory.decodeResource(
					context.getResources(), R.drawable.enemy_type_boom_3);
			bitmapBoom[1] = BitmapFactory.decodeResource(
					context.getResources(), R.drawable.enemy_boom_3);

			bitmapWidth = bitmap.getWidth();
			bitmapHeigth = bitmap.getHeight();

			y = 0;
			Random r = new Random();
			int randomResult = r.nextInt(5);
			x = GameView.canvasWidth * randomResult / (5 - 1);
			break;
		}
		default:
			break;
		}
	}

	public void draw(Canvas canvas) {
		if (!isHit && isAlive) {
			canvas.drawBitmap(bitmap, x - bitmapWidth / 2, y - bitmapHeigth,
					null);
		}
		if (isHit && !isBoom) {
			pictureCount++;
			canvas.drawBitmap(bitmapHit, x - bitmapWidth / 2, y - bitmapHeigth,
					null);
			if (pictureCount >= 3) {
				pictureCount = 0;
				isHit = false;
			}

		}
		if (isBoom && isHit) {
			pictureCount2++;

			if (pictureCount2 <= 5) {
				canvas.drawBitmap(bitmapBoom[0], x - bitmapWidth / 2, y
						- bitmapHeigth, null);
			}
			if (pictureCount2 > 5) {
				canvas.drawBitmap(bitmapBoom[1], x - bitmapBoom[1].getWidth()
						/ 2, y - bitmapBoom[1].getHeight(), null);
			}
			if (pictureCount2 >= 10) {
				pictureCount2 = 0;

				isBoom = false;
			}
		}
		formatBorder();

	}

	// 边界判断
	private void formatBorder() {
		if (x < bitmapWidth / 2) {
			x = bitmapWidth / 2;
		}
		if (x > GameView.canvasWidth - bitmapWidth / 2) {
			x = GameView.canvasWidth - bitmapWidth / 2;
		}

	}

	public void recycle() {
		bitmap.recycle();
	}

	public void update(Player player) {
		switch (type) {
		case TYPE_NORMAL: {
			y += speed;
			randomCount++;
			if (randomCount % RANDOM_TIMES == 0) {
				randomCount = 0;

				Random r = new Random();
				xSpeed = r.nextInt(6) + 6;
				flag = r.nextInt(16);
			}
			if (flag < 8) {
				x -= xSpeed;
			} else if (flag < 16) {
				x += xSpeed;
			} else {

			}
			break;
		}
		case TYPE_BOOM: {
			x = x + (player.x - x) / speed;
			y = y + (player.y - y) / speed;
			break;
		}

		default:
			break;
		}
		if (y > GameView.canvasHeight) {
			isAlive = false;
		}
	}
}