package com.example.fish.myapplication;

/**
 * Created by fish- on 2017/3/21.
 */

public class TagInterestBean implements Cloneable {

	public static final int TAG_TYPE_BOOK = 0;
	public static final int TAG_TYPE_FLAG = 1;
	public static final int TAG_TYPE_FOOD = 2;
	public static final int TAG_TYPE_MOVIE = 3;
	public static final int TAG_TYPE_MUSIC = 4;
	public static final int TAG_TYPE_MY_TOUR = 5;
	public static final int TAG_TYPE_SPORT = 6;

	private int type;
	private String name;

	public TagInterestBean(int type, String name) {
		setName(name);
		setType(type);
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public TagInterestBean clone() {
		TagInterestBean tagInterestBean = null;
		try {
			tagInterestBean = (TagInterestBean) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return tagInterestBean;
	}
}
