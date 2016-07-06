package com.example.mygame1.object;

import android.graphics.Bitmap;

public abstract class GameObject {

	public Bitmap bitmap;
	public float x;
	public float y;
	public int bitmapWidth;
	public int bitmapHeigth;
	public boolean isAlive;
	public boolean isHit = false;

	public GameObject() {
		isAlive = true;
	}

	public boolean isCollision(GameObject object) {
		boolean result = false;
		// 判断2个矩形的4个边界是否在重合范围内
		// 判断条件依次为：1.当前左边界和object的右边界是否重合
		// 判断条件依次为：2.当前右边界和object的左边界是否重合
		// 判断条件依次为：3.当前左边界和object的右边界是否重合
		// 判断条件依次为：4.当前左边界和object的右边界是否重合
		if ((x - bitmapWidth / 2) - (object.x + object.bitmapWidth / 2) < 0
				&& (x + bitmapWidth / 2) - (object.x - object.bitmapWidth / 2) > 0
				&& (y - bitmapHeigth / 2)
						- (object.y + object.bitmapHeigth / 2) < 0
				&& (y + bitmapHeigth / 2)
						- (object.y - object.bitmapHeigth / 2) > 0) {
			result = true;

		}
		if (isHit) {
			result = false;
		}
		return result;
	};

	public void recycle() {
		if (bitmap != null && !bitmap.isRecycled()) {
			bitmap.recycle();

		}
		bitmap.recycle();
	}
}
