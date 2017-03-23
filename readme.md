# AutoWrapLayout是一个支持子view自动换行的LinearLayout

### 使用显示如下：
![enter description here][1]


  [1]: ./S70323-203958.jpg "S70323-203958"
  
###  使用方式如下：
  

``` stylus
<RelativeLayout
        xmlns:android="http://schemas.android.com/apk/res/android"
        xmlns:autoWrap="http://schemas.android.com/apk/res/com.example.fish.myapplication"
        android:layout_width="match_parent"
        android:layout_height="match_parent">

        <com.example.fish.myapplication.AutoWrapLayout
            android:id="@+id/autoWrapLayout"
            android:layout_below="@id/tagTextView"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            autoWrap:horizontalInterval="10dp"
            autoWrap:verticalInterval="20dp"
            />

    </RelativeLayout>
```

### 也可以直接在java中使用

``` stylus
		// 设置之控件的左右边距
		autoWrapLayout.setVerticalInterval(ScreenUtils.dip2px(20.0f));
		// 设置之控件的上下边距
	    autoWrapLayout.setHorizontalInterval(ScreenUtils.dip2px(10.0f));
		autoWrapLayout.setPadding(ScreenUtils.dip2px(15.0f), ScreenUtils.dip2px(50.0f),
				ScreenUtils.dip2px(15.0f), ScreenUtils.dip2px(50.0f));
		autoWrapLayout.setBackgroundColor(Color.parseColor("#C0FF3E"));
		// 设置显示方向, 暂时只支持靠左和靠右
		autoWrapLayout.setGravity(Gravity.RIGHT);
		// 设置是否需要换行, 设置为否，则超过的item不加载显示
		autoWrapLayout.setIsLineFeed(false);
```


