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
		// �ж�2�����ε�4���߽��Ƿ����غϷ�Χ��
		// �ж���������Ϊ��1.��ǰ��߽��object���ұ߽��Ƿ��غ�
		// �ж���������Ϊ��2.��ǰ�ұ߽��object����߽��Ƿ��غ�
		// �ж���������Ϊ��3.��ǰ��߽��object���ұ߽��Ƿ��غ�
		// �ж���������Ϊ��4.��ǰ��߽��object���ұ߽��Ƿ��غ�
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
