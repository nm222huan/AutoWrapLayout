package com.example.fish.myapplication;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by fish- on 2017/3/21.
 */

public class AutoWrapLayoutTest extends Activity {
	private AutoWrapLayout autoWrapLayout;
	private String[] backColors = {"#FFFF00", "#FF8C69", "#FF34B3", "#E0EEEE", "#EEAD0E",
	"#D15FEE", "#CD9B1D", "#CD0000", "#B8860B", "#9400D3"};

	private TagTextView tagTextView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout);
		findViews();
		addViews();

		setTags();
	}

	void findViews() {
		autoWrapLayout = (AutoWrapLayout) findViewById(R.id.autoWrapLayout);
		tagTextView = (TagTextView) findViewById(R.id.tagTextView);
	}

	void addViews() {
		Random random = new Random();
		int i = 5;
		while (i-- > 0)
		for (String back : backColors) {
			TextView v = new TextView(this);
			int widht = random.nextInt(50) + 5;
			v.setWidth(ScreenUtils.dip2px(widht * 7.8f));
			int height = random.nextInt(20) + 5;
			v.setHeight(ScreenUtils.dip2px(height * 7.8f));
			v.setBackgroundColor(Color.parseColor(back));
			autoWrapLayout.addView(v);
		}
		//autoWrapLayout.setVerticalInterval(ScreenUtils.dip2px(20.0f));
		//autoWrapLayout.setHorizontalInterval(ScreenUtils.dip2px(10.0f));
		autoWrapLayout.setPadding(ScreenUtils.dip2px(15.0f), ScreenUtils.dip2px(50.0f),
				ScreenUtils.dip2px(15.0f), ScreenUtils.dip2px(50.0f));
		autoWrapLayout.setBackgroundColor(Color.parseColor("#C0FF3E"));
		//autoWrapLayout.setGravity(Gravity.RIGHT);
		//autoWrapLayout.setIsLineFeed(false);
	}

	void setTags() {
		List<TagInterestBean> beanlist = new ArrayList<TagInterestBean>();
		TagInterestBean bean = new TagInterestBean(0, backColors[0]);
		Random random = new Random();
		for (int i=0; i<40; i++) {
			TagInterestBean beanClone = bean.clone();
			int typ = random.nextInt(6);
			beanClone.setType(typ);
			beanClone.setName(backColors[typ]);
			beanlist.add(beanClone);
		}

		tagTextView.setText(beanlist);
	}
}
