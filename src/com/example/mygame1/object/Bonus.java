package com.example.mygame1.object;

import java.util.Random;

import com.example.mygame1.GameView;
import com.example.mygame1.R;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

public class Bonus extends GameObject {
	public static final int DEFAULT_SPEED = 8;// 默认速度为6
	public static final int TYPE_ADD_HP = 0;
	public static final int TYPE_LEVEL_UP = 1;
	public static final int TYPE_PROTECT = 2;
	public static final float TYPE_ADD_HP_CHANCE = 0.2f;
	public static final float TYPE_LEVEL_UP_CHANCE = 0.5f;
	public static final float TYPE_PROTECT_CHANCE = 0.3f;
	public static final int DISTANCE = GameView.canvasWidth / 3;// 奖励和玩家的距离
	public int type;
	private int speed = DEFAULT_SPEED;
	private int xSpeed = DEFAULT_SPEED;

	public Bonus(Context context, int type) {
		this.type = type;
		init(context);
	}

	private void init(Context context) {
		Random ran = new Random();
		if (ran.nextInt(99) < TYPE_ADD_HP_CHANCE*100) {
			type = Bonus.TYPE_ADD_HP;
		} else if (ran.nextInt(99) < (TYPE_LEVEL_UP_CHANCE+TYPE_ADD_HP_CHANCE)*100) {
			type = Bonus.TYPE_LEVEL_UP;
		} else {
			type = Bonus.TYPE_PROTECT;
		}
		switch (type) {
		case TYPE_ADD_HP:
			bitmap = BitmapFactory.decodeResource(context.getResources(),
					R.drawable.bonus_hp);
			break;
		case TYPE_LEVEL_UP:
			bitmap = BitmapFactory.decodeResource(context.getResources(),
					R.drawable.bullet_level_up);
			break;
		case TYPE_PROTECT:
			bitmap = BitmapFactory.decodeResource(context.getResources(),
					R.drawable.bonus_protect);
			break;
		default:
			break;
		}

		bitmapWidth = bitmap.getWidth();
		bitmapHeigth = bitmap.getHeight();
		y = 0;
		Random r = new Random();
		int randomResult = r.nextInt(5);
		x = GameView.canvasWidth * randomResult / (5 - 1);
	}

	public void draw(Canvas canvas) {
		if (isAlive) {
			canvas.drawBitmap(bitmap, x - bitmapWidth / 2, y - bitmapHeigth,
					null);
		}
	}

	public void update(Player player) {
		if (Math.sqrt((player.x - x) * (player.x - x) + (player.y - y)
				* (player.y - y)) > DISTANCE) {
			switch (type) {
			case TYPE_LEVEL_UP: {
				y += speed;
				x += xSpeed;
				if (x <= 0 + bitmapWidth / 2
						|| x >= GameView.canvasWidth - bitmapWidth / 2) {
					xSpeed = -xSpeed;
				}
				break;
			}
			case TYPE_PROTECT: {
				y += speed;
				x -= xSpeed;
				if (x <= 0 + bitmapWidth / 2
						|| x >= GameView.canvasWidth - bitmapWidth / 2) {
					xSpeed = -xSpeed;
				}
				break;
			}
			case TYPE_ADD_HP: {
				y += speed;
				break;
			}

			default:
				break;
			}

		} else {
			x = x + (player.x - x) / speed;
			y = y + (player.y - y) / speed;
		}
		if (y > GameView.canvasHeight) {
			isAlive = false;
		}
		formatBorder();
	}

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
}
