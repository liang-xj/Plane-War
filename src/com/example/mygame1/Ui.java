package com.example.mygame1;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;

import android.graphics.RectF;

public class Ui {

	private Bitmap bitmapPlayerHp;
	private Bitmap bitmapBossHp;
	private Bitmap bitmapScore;
	private Bitmap bitmapPause;
	
	private Bitmap bitmapLightButton;

	public Ui(Context context) {
		init(context);
	}

	private void init(Context context) {
		bitmapPlayerHp = BitmapFactory.decodeResource(context.getResources(),
				R.drawable.player_hp);
		bitmapBossHp = BitmapFactory.decodeResource(context.getResources(),
				R.drawable.boss_hp);
		bitmapScore = BitmapFactory.decodeResource(context.getResources(),
				R.drawable.score);
		bitmapPause = BitmapFactory.decodeResource(context.getResources(),
				R.drawable.pause);
		bitmapLightButton = BitmapFactory.decodeResource(
				context.getResources(), R.drawable.button_light);
	}

	public void draw(Canvas canvas, int playerHp, int bossHp, int score,
			int playerBlood, int bossBlood, boolean bossIsCome) {
		Paint paintPlayer1 = new Paint();
		paintPlayer1.setColor(Color.RED);
		paintPlayer1.setTextSize(50);
		paintPlayer1.setAntiAlias(true);
		paintPlayer1.setStyle(Style.STROKE);

		Paint paintPlayer2 = new Paint();
		paintPlayer2.setColor(Color.RED);
		paintPlayer2.setTextSize(50);
		paintPlayer2.setAntiAlias(true);
		paintPlayer2.setStyle(Style.FILL);

		Paint paintBoss1 = new Paint();
		paintBoss1.setColor(Color.BLUE);
		paintBoss1.setTextSize(50);
		paintBoss1.setAntiAlias(true);
		paintBoss1.setStyle(Style.STROKE);

		Paint paintBoss2 = new Paint();
		paintBoss2.setColor(Color.BLUE);
		paintBoss2.setTextSize(50);
		paintBoss2.setAntiAlias(true);
		paintBoss2.setStyle(Style.FILL);

		Paint paintText = new Paint();
		paintText.setColor(Color.LTGRAY);
		paintText.setTextSize(40);
		paintText.setAntiAlias(true);

		RectF playerHp1 = new RectF(bitmapPlayerHp.getWidth(), 0,
				canvas.getWidth() / 5 * 3, bitmapPlayerHp.getHeight());
		RectF playerHp2 = new RectF(bitmapPlayerHp.getWidth(), 0,
				(canvas.getWidth() / 5 * 3 - bitmapPlayerHp.getWidth())
						* playerHp / playerBlood + bitmapPlayerHp.getWidth(),
				bitmapPlayerHp.getHeight());
		RectF bossHp1 = new RectF(bitmapBossHp.getWidth(),
				bitmapPlayerHp.getHeight() + 10, canvas.getWidth() / 4 * 3,
				bitmapPlayerHp.getHeight() + bitmapPlayerHp.getHeight() + 15);
		RectF bossHp2 = new RectF(bitmapBossHp.getWidth(),
				bitmapPlayerHp.getHeight() + 10,
				(canvas.getWidth() / 4 * 3 - bitmapBossHp.getWidth()) * bossHp
						/ bossBlood + bitmapBossHp.getWidth(),
				bitmapPlayerHp.getHeight() + bitmapPlayerHp.getHeight() + 15);

		canvas.drawRect(playerHp2, paintPlayer2);
		canvas.drawRect(playerHp1, paintPlayer1);

		if (bossIsCome) {
			canvas.drawRect(bossHp2, paintBoss2);
			canvas.drawRect(bossHp1, paintBoss1);
			canvas.drawBitmap(bitmapBossHp, 0, bitmapPlayerHp.getHeight() + 10,
					null);
		}
		canvas.drawText("" + score, canvas.getWidth() / 7 * 6,
				bitmapScore.getHeight() - 2, paintText);
		canvas.drawBitmap(bitmapLightButton, canvas.getWidth() / 5 * 4,
				canvas.getHeight() / 5 * 2, null);
		canvas.drawBitmap(bitmapPlayerHp, 0, 0, null);

		canvas.drawBitmap(bitmapScore, canvas.getWidth() / 3 * 2, 0, null);

		canvas.drawBitmap(bitmapPause,
				canvas.getWidth() - bitmapPause.getWidth() * 2,
				bitmapBossHp.getHeight(), null);

	}

}
