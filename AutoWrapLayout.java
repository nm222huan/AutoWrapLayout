package com.example.fish.myapplication;

import java.util.Hashtable;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;

/**
 * 自定义的 可换行的LinerLayout
 *
 * @author xlt
 */
public class AutoWrapLayout extends ViewGroup {
	private int mHorizontalInterval = 0;
	private int mVerticalInterval = 0;

	Hashtable map = new Hashtable();
	private boolean mIsLineFeed = true;
	private int mGravity = Gravity.LEFT;

	public AutoWrapLayout(Context context) {
		super(context);
	}

	public AutoWrapLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

		int layoutWidth = MeasureSpec.getSize(widthMeasureSpec);
		int childCount = getChildCount();

		int mX = 0;
		int mY = 0;

		/**-------------------- 当前测量辅助判断的左右上下值 ------------------------**/
		int curLeft = getPaddingLeft();
		int curRight = curLeft;
		int curTop = getPaddingTop();
		int curBottom = curTop;
		/**--------------------------------------------**/
		int j = 0;
		int childhMax = 0;

		if (childCount > 0) {
			View child_one = getChildAt(0);
			child_one.measure(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED);
			// 此处增加onlayout中的换行判断，用于计算所需的高度
			mX += child_one.getMeasuredWidth();
			mY += child_one.getMeasuredHeight();
			childhMax = child_one.getMeasuredHeight();
			Position position_one = new Position();
			//curLeft = 0;
			curRight = curLeft + child_one.getMeasuredWidth();
			curBottom = curTop + child_one.getMeasuredHeight();
			mY = curTop; // 每次的高度必须记录 否则控件会叠加到一起

			if (mGravity == Gravity.LEFT) {
				position_one.left = curLeft;
				position_one.top = curTop;
				position_one.right = curRight;
				position_one.bottom = curBottom;
			} else if (mGravity == Gravity.RIGHT) {
				position_one.left = layoutWidth - curRight;
				position_one.top = curTop;
				position_one.right = layoutWidth - curLeft;
				position_one.bottom = curBottom;
			}

			map.put(child_one, position_one);
		}

		for (int i = 1; i < childCount; i++) {
			final View child = getChildAt(i);

			child.measure(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED);
			// 此处增加onlayout中的换行判断，用于计算所需的高度
			int childw = child.getMeasuredWidth() + mHorizontalInterval;
			int childh = child.getMeasuredHeight();
			mX += childw; // 将每次子控件宽度进行统计叠加，如果大于设定的高度则需要换行，高度即Top坐标也需重新设置

			Position position = new Position();
			curLeft = getPosition(i - j, i);
			curRight = curLeft + child.getMeasuredWidth();
			if (mX > layoutWidth - getPaddingRight()) {
				if (!mIsLineFeed) {
					break;
				}
				mX = child.getMeasuredWidth();
				mY += childhMax;
				childhMax = 0;
				j = i;
				curLeft = getPaddingLeft();
				curRight = curLeft + child.getMeasuredWidth();
				curTop = mY + mVerticalInterval;
			}
			childhMax = childh > childhMax ? childh : childhMax;
			curBottom = curTop + child.getMeasuredHeight();
			mY = curTop; // 每次的高度必须记录 否则控件会叠加到一起
			if (mGravity == Gravity.LEFT) {
				position.left = curLeft;
				position.top = curTop;
				position.right = curRight;
				position.bottom = curBottom;
			} else if (mGravity == Gravity.RIGHT) {
				position.left = layoutWidth - curRight;
				position.top = curTop;
				position.right = layoutWidth - curLeft;
				position.bottom = curBottom;
			}
			map.put(child, position);
		}
		curBottom = curTop + childhMax;
		setMeasuredDimension(layoutWidth, curBottom + getPaddingBottom());
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		// TODO Auto-generated method stub

		int count = getChildCount();
		for (int i = 0; i < count; i++) {
			View child = getChildAt(i);
			Position pos = (Position) map.get(child);
			if (pos != null) {
				child.layout(pos.left, pos.top, pos.right, pos.bottom);
			} else {
				Log.i("MyLayout", "error");
			}
		}
	}

	private class Position {
		int left, top, right, bottom;
	}

	public int getPosition(int IndexInRow, int childIndex) {
		if (IndexInRow > 0) {
			return getPosition(IndexInRow - 1, childIndex - 1) + getChildAt(childIndex - 1).getMeasuredWidth() + mHorizontalInterval;
		}
		return getPaddingLeft();
	}

	/**
	 * 设置之控件的左右边距
	 * @param horizontalInterval
	 */
	public void setHorizontalInterval(int horizontalInterval) {
		mHorizontalInterval = horizontalInterval;
	}

	/**
	 * 设置之控件的上下边距
	 * @param verticalInterval
	 */
	public void setVerticalInterval(int verticalInterval) {
		mVerticalInterval = verticalInterval;
	}

	/**
	 * 设置是否需要换行, 设置为否，则超过的item不加载显示
	 */
	public void setIsLineFeed(boolean isLineFeed) {
		mIsLineFeed = isLineFeed;
	}

	/**
	 * 设置显示方向, 暂时只支持靠左和靠右
	 * @param gravity
	 */
	public void setGravity(int gravity) {
		mGravity = gravity;
	}

}
