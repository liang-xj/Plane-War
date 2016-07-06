package com.example.mygame1;

import java.util.ArrayList;

import com.example.mygame1.object.Bonus;
import com.example.mygame1.object.Boss;
import com.example.mygame1.object.BossBullet;
import com.example.mygame1.object.Enemy;
import com.example.mygame1.object.EnemyBullet;
import com.example.mygame1.object.Player;
import com.example.mygame1.object.PlayerBullet;

import android.content.Context;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameView extends SurfaceView implements SurfaceHolder.Callback,
		Runnable {
	private static final int DRAW_TIME = 15;
	private static final int PLAYER_BLOOD = 30;
	private static final int BOSS_BLOOD = 150;
	private static final int ENEMY_TIMES = 50;
	private static final int PLAYER_BULLET_TIMES = 6;
	private static final int ENEMY_BULLET_TIMES = 20;
	private static final int BOSS_BULLET_TIMES = 10;
	private static final int BOSS_COME_TIME = 10;// 根据背景滚动次数决定BOSS出现时间[[
	private static final int BONUS_TIME = 100;// 奖励出现频率
	public static final int LEVEL_1_SPEED = 20;
	public static final int LEVEL_2_SPEED = 30;
	public static final int LIGHT_BULLET_SPEED = 40;
	public static final int LIGHT_SPEED = 5;
	public static final int BONUS_ADD_HP = 6;
	public static final int BOSS_ATTACK = 3;

	public static int canvasWidth;
	public static int canvasHeight;

	private boolean gameIsRuning;
	private boolean gameIsOver=false;
	private boolean gameIsWin=false;
	private boolean isNeedToMovePlayer;
	private boolean bossIsCome = false;


	private SurfaceHolder surfaceHolder;
	private Context context;
	private Thread gameThread;
	private BackGround backGround;
	private YouWin youwin;
	private GameOver gameover;
	private Player player;
	private Boss boss;
	private Ui ui;
	private PlayerBullet playerBullet;
	private ArrayList<Enemy> enemyList;
	private ArrayList<Enemy> enemyBoomList;
	private ArrayList<PlayerBullet> playerBulletList;
	private ArrayList<EnemyBullet> enemyBulletList;
	private ArrayList<BossBullet> bossBulletArrayList;
	private ArrayList<Bonus> bonusList;

	private int playerBulletlevel = PlayerBullet.LEVEL_1;
	private int playerBulletCount;
	private int enemyCount;
	private int enemyBoomCount;
	private int enemyBulletCount;
	private int lightBulletCount = 0;
	private int bonusCount;
	private int bonusType;
	private int bossBulletCount;
    private int score = 0;

	private float moveToX;
	private float moveToY;

	public GameView(Context context) {
		super(context);
		surfaceHolder = getHolder();
		surfaceHolder.addCallback(this);
		gameThread = new Thread(this);
		this.context = context;
	}

	private void init() {
		// 初始化背景
		backGround = new BackGround(context, 0);
		youwin = new YouWin(context, 0);
		gameover = new GameOver(context, 0);
		// 初始化飞机
		player = new Player(context, PLAYER_BLOOD, 25);
		// 初始化player子弹
		playerBulletList = new ArrayList<PlayerBullet>();
		// 画敌机
		enemyList = new ArrayList<Enemy>();
		enemyBoomList = new ArrayList<Enemy>();
		// 初始化敌机子弹
		enemyBulletList = new ArrayList<EnemyBullet>();
		bonusList = new ArrayList<Bonus>();
		boss = new Boss(context, BOSS_BLOOD, Boss.DEFAULT_SPEED);
		bossBulletArrayList = new ArrayList<BossBullet>();
		ui = new Ui(context);
	}

	private void recycle() {
		backGround.recycle();
		player.recycle();
		for (int i = playerBulletList.size() - 1; i >= 0; i--) {
			playerBulletList.get(i).recycle();

		}
		playerBulletList.clear();
		for (int i = enemyList.size() - 1; i >= 0; i--) {
			enemyList.get(i).recycle();
		}
		enemyList.clear();
		for (int i = enemyBoomList.size() - 1; i >= 0; i--) {
			enemyList.get(i).recycle();
		}
		enemyBoomList.clear();
		boss.recycle();// boss回收
		for (int i = bossBulletArrayList.size() - 1; i > 0; i--) {
			bossBulletArrayList.get(i);
		}
		bossBulletArrayList.clear();
		youwin.recycle();
		gameover.recycle();

	}

	@Override
	public void run() {
		while (gameIsRuning) {

			try {
				draw();
				update();
				Thread.sleep(DRAW_TIME);
			} catch (InterruptedException e) {

				e.printStackTrace();
			}
		}

	}

	private void draw() {
		Canvas canvas = surfaceHolder.lockCanvas();
		Paint paint = new Paint();
		paint.setColor(Color.LTGRAY);
		paint.setTextSize(50);

		if (canvas == null) {
			return;
		}
		// 绘制背景
		backGround.draw(canvas);
		if (gameIsOver) {
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
			
				e.printStackTrace();
			}
			gameover.draw(canvas);
			boss.isAlive = false;
			

		}
		if (gameIsWin) {
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
			
				e.printStackTrace();
			}
			youwin.draw(canvas);
			
			player.isAlive = false;
			canvas.drawText("" + score, GameView.canvasWidth / 7 * 3,
					canvasHeight * 5 / 6, paint);

		}
		// 绘制敌机

		if (player.isAlive) {
			drawEnemy(canvas);
		}

		drawBoomEnemy(canvas);
		// 绘制敌机子弹

		if (player.isAlive) {
			drawEnemyBullet(canvas);
		}

		if (boss.isAlive || player.isAlive) {
			drawBonus(canvas);
		}
		// 绘制玩家

		player.draw(canvas);
		if ((boss.isAlive && player.isAlive) || (!bossIsCome && player.isAlive)) {
			ui.draw(canvas, player.blood, boss.blood, score,
					PLAYER_BLOOD, BOSS_BLOOD, bossIsCome);
		}

		// 绘制玩家子弹
		if (backGround.backGroundCount == BOSS_COME_TIME) {
			bossIsCome = true;

		}
		if (bossIsCome) {
			if (boss.isAlive || boss.isBoom) {
				boss.draw(canvas);
			}
		}
		if (player.isAlive) {
			drawPlayerBullet(canvas);
		}

		if (bossIsCome && boss.isAlive) {
			drawBoosBullet(canvas);
		}
		surfaceHolder.unlockCanvasAndPost(canvas);

	}

	private void drawBonus(Canvas canvas) {
		for (int i = bonusList.size() - 1; i >= 0; i--) {
			Bonus bonus = bonusList.get(i);
			bonus.draw(canvas);

		}

	}

	private void drawBoomEnemy(Canvas canvas) {
		for (int i = enemyBoomList.size() - 1; i >= 0; i--) {
			Enemy enemy = enemyBoomList.get(i);
			enemy.draw(canvas);

		}

	}

	private void update() {
		if (player.isAlive) {
			backGround.update();
		}

		if (isNeedToMovePlayer) {
			player.update(moveToX, moveToY);
		}

		updatePlayerBullet();
		updateBoomEnemy();
		updateEnemy();
		updateBonus();
		if (!bossIsCome && player.isAlive) {
			updateEnemyBullet(EnemyBullet.TYPE_NORMAL);
		}
		if (bossIsCome && player.isAlive) {
			updateEnemyBullet(EnemyBullet.TYPE_NORMAL);
		}
		if (bossIsCome && boss.isAlive) {
			updateBossBullet();
		}
		if (bossIsCome && player.isAlive) {
			updateBoss();
		}

	}

	private void updateBonus() {
		bonusCount++;

		if (bonusCount % BONUS_TIME == 0) {
			bonusCount = 0;

			bonusList.add(new Bonus(context, bonusType));

		}
		for (int i = bonusList.size() - 1; i >= 0; i--) {
			Bonus bonus = bonusList.get(i);
			bonus.update(player);
			if (bonus.isCollision(player) && bonus.isAlive) {
				switch (bonus.type) {
				case Bonus.TYPE_LEVEL_UP: {
					playerBulletlevel++;
					if (playerBulletlevel > 2) {
						playerBulletlevel = 2;
					}
					break;
				}
				case Bonus.TYPE_ADD_HP: {
					player.blood = player.blood + BONUS_ADD_HP;
					if (player.blood > PLAYER_BLOOD) {
						player.blood = PLAYER_BLOOD;
					}
					break;
				}
				case Bonus.TYPE_PROTECT: {
					if (player.isProtect) {
						player.protectCount = 0;
					} else {
						player.isProtect = true;
					}
				}
				default:
					break;
				}
				bonus.isAlive = false;

			}

			if (!bonus.isAlive) {
				bonusList.remove(i);
				bonus.recycle();
			}
		}

	}

	private void drawBoosBullet(Canvas canvas) {
		for (int i = bossBulletArrayList.size() - 1; i >= 0; i--) {
			BossBullet bossBullet = bossBulletArrayList.get(i);
			bossBullet.draw(canvas);
		}

	}

	private void updateBossBullet() {
		bossBulletCount++;
		if (bossBulletCount % BOSS_BULLET_TIMES == 0) {
			bossBulletCount = 0;

			bossBulletArrayList.add(new BossBullet(context, boss.x, boss.y
					- boss.bitmapHeigth / 2, BossBullet.DEFULT_SPEED,
					BossBullet.DIR_DOWN_RIGHT));
			bossBulletArrayList.add(new BossBullet(context, boss.x, boss.y
					- boss.bitmapHeigth / 2, BossBullet.DEFULT_SPEED,
					BossBullet.DIR_DOWN));
			bossBulletArrayList.add(new BossBullet(context, boss.x, boss.y
					- boss.bitmapHeigth / 2, BossBullet.DEFULT_SPEED,
					BossBullet.DIR_DOWN_LEFT));
		}
		for (int i = bossBulletArrayList.size() - 1; i >= 0; i--) {
			BossBullet bossBullet = bossBulletArrayList.get(i);
			bossBullet.update();
			/* boss子弹和玩家碰撞 */
			if (bossBullet.isCollision(player) && !player.isBoom
					&& player.isProtect == false) {
				bossBullet.isAlive = false;

				playerBulletlevel--;
				if (playerBulletlevel < 0) {
					playerBulletlevel = 0;
				}

				player.blood = player.blood - BOSS_ATTACK;
				player.isHit = true;

				if (player.blood <= 0) {
					player.isAlive = false;
					gameIsOver=true;
					// stopGame();
				}
			}

			if (!bossBullet.isAlive) {
				bossBulletArrayList.remove(i);
				bossBullet.recycle();
			}
		}

	}

	private void updateBoss() {
		boss.update();
		if (boss.isCollision(player)) {
			player.blood--;
			if (player.blood <= 0) {
				player.isAlive = false;
				gameIsOver=true;
			}
		}

	}

	private void updateBoomEnemy() {
		enemyBoomCount++;
		if (enemyBoomCount % ENEMY_TIMES == 0) {
			enemyBoomCount = 0;

			if (!bossIsCome) {
				enemyList.add(new Enemy(context, Enemy.TYPE_BOOM_SPEED,
						Enemy.TYPE_BOOM));

			}

		}
		for (int i = enemyBoomList.size() - 1; i >= 0; i--) {
			Enemy enemy = enemyBoomList.get(i);
			enemy.update(player);
			if (enemy.isCollision(player) && enemy.isAlive
					&& player.isProtect == false) {

				player.blood--;
				playerBulletlevel--;
				if (playerBulletlevel < 0) {
					playerBulletlevel = 0;
				}
				player.isHit = true;
				if (player.blood <= 0) {
					player.isAlive = false;
                    gameIsOver=true;
				}
				enemy.blood--;
				enemy.isHit = true;

				if (enemy.blood <= 0) {
					enemy.isAlive = false;
					enemy.isBoom = true;
				}
			}
			if (!enemy.isAlive && !enemy.isBoom) {
				enemyBoomList.remove(i);
				enemy.recycle();
			}
		}

	}

	private void drawEnemy(Canvas canvas) {
		for (int i = enemyList.size() - 1; i >= 0; i--) {
			Enemy enemy = enemyList.get(i);
			enemy.draw(canvas);

		}

	}

	private void updateEnemy() {
		enemyCount++;
		if (enemyCount % ENEMY_TIMES == 0) {
			enemyCount = 0;

			if (!bossIsCome) {
				enemyList.add(new Enemy(context, Enemy.DEFAULT_SPEED,
						Enemy.TYPE_NORMAL));

			}

		}
		for (int i = enemyList.size() - 1; i >= 0; i--) {
			Enemy enemy = enemyList.get(i);
			enemy.update(player);
			if (enemy.isCollision(player) && enemy.isAlive
					&& player.isProtect == false) {

				player.blood--;
				playerBulletlevel--;
				if (playerBulletlevel < 0) {
					playerBulletlevel = 0;
				}
				player.isHit = true;
				if (player.blood <= 0) {
					player.isAlive = false;
					gameIsOver=true;

				}
				enemy.blood--;
				enemy.isHit = true;
				if (enemy.blood <= 0) {
					enemy.isAlive = false;
					enemy.isBoom = true;
				}

			}
			if (!enemy.isAlive && !enemy.isBoom) {
				enemyList.remove(i);
				enemy.recycle();
			}
		}
	}

	private void drawEnemyBullet(Canvas canvas) {
		for (int i = enemyBulletList.size() - 1; i >= 0; i--) {
			EnemyBullet enemyBullet = enemyBulletList.get(i);
			enemyBullet.draw(canvas);
		}

	}

	private void updateEnemyBullet(int enemyBulletType) {
		enemyBulletCount++;
		if (enemyBulletCount % ENEMY_BULLET_TIMES == 0) {
			enemyBulletCount = 0;
			switch (enemyBulletType) {
			case EnemyBullet.TYPE_NORMAL: {
				for (int j = enemyList.size() - 1; j >= 0; j--) {
					Enemy enemy = enemyList.get(j);
					enemyBulletList
							.add(new EnemyBullet(context, enemy.x, enemy.y
									- enemy.bitmapHeigth / 2,
									EnemyBullet.DEFAULT_SPEED,
									EnemyBullet.TYPE_NORMAL));

				}
				break;
			}
			case EnemyBullet.TYPE_BOSS: {
				if (boss.isAlive) {
					enemyBulletList.add(new EnemyBullet(context, boss.x, boss.y
							- boss.bitmapHeigth / 2,
							EnemyBullet.BOSS_BULLET_SPEED,
							EnemyBullet.TYPE_BOSS));

					break;
				}
			}

			default:
				break;
			}

		}
		for (int j = enemyBulletList.size() - 1; j >= 0; j--) {
			EnemyBullet enemyBullet = enemyBulletList.get(j);

			
			enemyBullet.update();
			if (enemyBullet.isCollision(player) && player.isProtect == false) {

				player.blood--;
				playerBulletlevel--;
				if (playerBulletlevel < 0) {
					playerBulletlevel = 0;
					
				}

				player.isHit = true;
				enemyBullet.isAlive = false;

				if (player.blood <= 0) {
					player.isAlive = false;
					gameIsOver=true;
					player.recycle();

				}

			}

			if (!enemyBullet.isAlive) {
				enemyBullet.recycle();
				enemyBulletList.remove(j);
			}
		}

	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			if (event.getY() < canvasHeight / 7 * 1
					&& event.getX() > canvasWidth / 4 * 3) {
				if (gameIsRuning) {
					pause();
				} else {
					resume();

				}
			}
			if (event.getY() < canvasHeight / 2
					&& event.getY() > canvasHeight / 3
					&& event.getX() > canvasWidth / 5 * 4) {
				playerBulletlevel = playerBullet.LIGHT;
			}

			break;
		case MotionEvent.ACTION_MOVE: {

			isNeedToMovePlayer = true;
			moveToX = event.getX();
			moveToY = event.getY();
			break;

		}
		case MotionEvent.ACTION_UP: {
			isNeedToMovePlayer = false;
			break;
		}
		default:
			break;
		}
		return true;

	}

	private void resume() {
		gameIsRuning = true;
		gameThread = new Thread(this);
		gameThread.start();

	}

	private void pause() {
		gameIsRuning = false;
		

	}

	private void drawPlayerBullet(Canvas canvas) {

		for (int i = playerBulletList.size() - 1; i >= 0; i--) {
			PlayerBullet playerBullet = playerBulletList.get(i);
			if (playerBullet.isAlive) {
				playerBullet.draw(canvas);
			}
		}

	}

	private void updatePlayerBullet() {
		playerBulletCount++;
		if (playerBulletCount % PLAYER_BULLET_TIMES == 0) {
			playerBulletCount = 0;
			switch (playerBulletlevel) {
			case PlayerBullet.LEVEL_1: {
				playerBulletList.add(new PlayerBullet(context, player.x,
						player.y - player.bitmapHeigth, LEVEL_1_SPEED,
						PlayerBullet.LEVEL_1));

				break;
			}
			case PlayerBullet.LEVEL_2: {
				playerBulletList.add(new PlayerBullet(context, player.x
						- player.bitmapWidth / 4, player.y
						- player.bitmapHeigth / 2, LEVEL_2_SPEED,
						PlayerBullet.LEVEL_1));

				playerBulletList.add(new PlayerBullet(context, player.x
						+ player.bitmapWidth / 4, player.y
						- player.bitmapHeigth / 2, LEVEL_2_SPEED,
						PlayerBullet.LEVEL_1));
				playerBulletList.add(new PlayerBullet(context, player.x,
						player.y - player.bitmapHeigth, LEVEL_2_SPEED,
						PlayerBullet.LEVEL_1));

				break;
			}

			case PlayerBullet.LEVEL_3: {
				lightBulletCount++;
				if (lightBulletCount > 1) {
					playerBulletList.add(new PlayerBullet(context, player.x
							- player.bitmapWidth / 4, player.y
							- player.bitmapHeigth, LIGHT_BULLET_SPEED,
							PlayerBullet.LEVEL_3));

					playerBulletList.add(new PlayerBullet(context, player.x
							+ player.bitmapWidth / 4, player.y
							- player.bitmapHeigth, LIGHT_BULLET_SPEED,
							PlayerBullet.LEVEL_3));
					/*playerBulletList.add(new PlayerBullet(context, player.x,
							player.y - player.bitmapHeigth, LIGHT_BULLET_SPEED,
							PlayerBullet.LEVEL_3));*/
					lightBulletCount = 0;
				}
				break;
			}
			case PlayerBullet.LIGHT: {

				playerBulletList.add(new PlayerBullet(context,
						canvasWidth / 2 - 10, canvasHeight / 2, LIGHT_SPEED,
						PlayerBullet.LIGHT));
				playerBulletList.add(new PlayerBullet(context,
						canvasWidth / 2 + 10, canvasHeight / 2, LIGHT_SPEED,
						PlayerBullet.LIGHT));
				playerBulletlevel = playerBullet.LEVEL_3;
				break;
			}
			default:
				break;
			}

		}
		for (int i = playerBulletList.size() - 1; i >= 0; i--) {
			playerBullet = playerBulletList.get(i);

			playerBullet.update();
			if (bossIsCome && boss.isAlive && playerBullet.isCollision(boss)) {
				boss.blood--;
				if (playerBullet.level != playerBullet.LEVEL_3
						&& playerBullet.level != playerBullet.LIGHT) {
					playerBullet.isAlive = false;
				}
				boss.isHit = true;
				if (boss.blood <= 0) {
					boss.isAlive = false;
					boss.isBoom = true;

					score = score + 100;
					gameIsWin=true;
				}

			}
			/* 玩家子弹碰撞 */
			// 1.玩家子弹与敌机碰撞
			for (int j = enemyList.size() - 1; j >= 0; j--) {
				Enemy enemy = enemyList.get(j);
				if (enemy.isAlive) {
					if (playerBullet.isCollision(enemy)) {
						// playerBullet.isAlive = false;
						enemy.blood--;
						if (playerBullet.level != playerBullet.LEVEL_3
								&& playerBullet.level != playerBullet.LIGHT) {

							playerBullet.isAlive = false;
						}
						enemy.isHit = true;
						if (enemy.blood <= 0) {
							enemy.isAlive = false;
							enemy.isBoom = true;
							score = score + 10;
						}

					}
				}
			}
			if (!playerBullet.isAlive || playerBullet.y < 0) {

				playerBulletList.remove(i);

				playerBullet.recycle();
			}

		}

	}

	public void surfaceCreated(SurfaceHolder holder) {
		canvasHeight = getHeight();
		canvasWidth = getWidth();
		init();
		startGame();
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {

	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		stopGame();
		recycle();

	}

	private void startGame() {
		if (gameThread != null) {
			gameIsRuning = true;
			gameThread.start();
		}

	}

	private void stopGame() {

		if (gameThread != null) {
			gameIsRuning = false;

		}

	}
}