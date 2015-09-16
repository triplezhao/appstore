package com.potato.library.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

public class KeepRatioImageView extends ImageView{
	private int targetH;
	private int targetW;
	public KeepRatioImageView(Context context) {
		super(context);
	}

	public KeepRatioImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		// TODO Auto-generated method stub
//		heightMeasureSpec=(int) (widthMeasureSpec*1.8);
		if(targetH==0||targetW==0) return ;
		int currentWidth = getMeasuredWidth();
		int currentHeight = getMeasuredHeight();
		currentHeight=(int) (currentWidth*(float)targetH/(float)targetW);
//		L.i("ImageLoadView", "currentWidth"+currentWidth+"currentHeight"+currentHeight);
		setMeasuredDimension(currentWidth, currentHeight);
	}

	public void setTargetWH(int targetW, int targetH){
		this.targetW=targetW;
		this.targetH=targetH;
	}
}
