package com.example.popupactivity;

/**
 * author:zkp
 * date:2016-4-8
 */
import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.Toast;

@SuppressLint("NewApi")
public class MainActivity extends Activity implements View.OnClickListener {

	private static final int MENU_TRANSLATION_Y = 1200;
	private static final int MENU_CHILD_TRANSLATION_Y = 800;
	private static final int MENU_CHILD_ANIMATION_DURATION = 500;
	private static final int MENU_CHILD_ANIMATION_OFFSET = 100;
	private static final int MENU_ALPHA_DURATION = 500;
	private static final int BUTTON_ROTATE_DURATION = 500;
	private static final int BUTTON_UNROTATE_DURATION = 200;
	private static final float MENU_ALPHA = 0.8f;

	public ViewGroup mMenu;
	private ImageView ivMenuItem1;
	private ImageView ivMenuItem2;
	private ImageView ivMenuItem3;
	private View mToggle;

	private Animation mToggleRotateAnimation;
	private Animation mToggleUnRotateAnimation;
	private AlphaAnimation mMenuAlphaAnimation;
	private List<AnimationSet> animations;
	private boolean isRotated = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mToggle = findViewById(R.id.toggle);
		mToggle.setOnClickListener(this);

		mMenu = (ViewGroup) findViewById(R.id.menu);
		mMenu.setAlpha(MENU_ALPHA);
		mMenu.setOnClickListener(this);
		ivMenuItem1 = (ImageView) findViewById(R.id.one);
		ivMenuItem1.setOnClickListener(this);
		ivMenuItem2 = (ImageView) findViewById(R.id.two);
		ivMenuItem2.setOnClickListener(this);
		ivMenuItem3 = (ImageView) findViewById(R.id.three);
		ivMenuItem3.setOnClickListener(this);
		mMenu.setTranslationY(MENU_TRANSLATION_Y);

		animations = new ArrayList<AnimationSet>();
		initAnimations();
	}

	private void initAnimations() {
		// TODO 自动生成的方法存根
		// 按钮旋转
		mToggleRotateAnimation = new RotateAnimation(0, 90,
				Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
				0.5f);
		mToggleRotateAnimation.setDuration(BUTTON_ROTATE_DURATION);
		mToggleRotateAnimation.setFillAfter(true);
		mToggleUnRotateAnimation = new RotateAnimation(90, 0,
				Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
				0.5f);
		mToggleUnRotateAnimation.setDuration(BUTTON_UNROTATE_DURATION);
		mToggleUnRotateAnimation.setFillAfter(true);
		// 菜单透明度
		mMenuAlphaAnimation = new AlphaAnimation(0.0f, MENU_ALPHA);
		mMenuAlphaAnimation.setDuration(MENU_ALPHA_DURATION);

		// 菜单项上移动画
		for (int i = 0; i < mMenu.getChildCount(); i++) {
			AnimationSet set = new AnimationSet(true);
			TranslateAnimation animationTranslate = new TranslateAnimation(0,
					0, MENU_CHILD_TRANSLATION_Y, 0);
			animationTranslate.setDuration(MENU_CHILD_ANIMATION_DURATION);
			// animation.setInterpolator(new BounceInterpolator());
			animationTranslate.setStartOffset(MENU_CHILD_ANIMATION_OFFSET * i);
			AlphaAnimation animationAlpha = new AlphaAnimation(0, 1.0f);
			animationAlpha.setDuration(MENU_CHILD_ANIMATION_DURATION);
			// animation.setInterpolator(new BounceInterpolator());
			animationAlpha.setStartOffset(MENU_CHILD_ANIMATION_OFFSET * i);
			set.addAnimation(animationTranslate);
			set.addAnimation(animationAlpha);

			animations.add(set);
		}
	}

	@Override
	public void onClick(View v) {
		// TODO 自动生成的方法存根
		switch (v.getId()) {
		case R.id.toggle:
		case R.id.menu:
			toggle();
			break;
		case R.id.one:
			toggle();
			Toast.makeText(this, "Item Setting clicked", Toast.LENGTH_SHORT)
					.show();
			break;
		case R.id.two:
			toggle();
			Toast.makeText(this, "Item Menu clicked", Toast.LENGTH_SHORT)
					.show();
			break;
		case R.id.three:
			toggle();
			Toast.makeText(this, "Item Edit clicked", Toast.LENGTH_SHORT)
					.show();
			break;
		default:
			break;
		}

	}

	private void toggle() {
		if (!isRotated) {
			// 按钮旋转
			mToggle.startAnimation(mToggleRotateAnimation);
			isRotated = true;
			// 菜单上移、透明度改变
			mMenu.setTranslationY(0);
			mMenu.startAnimation(mMenuAlphaAnimation);
			playMenuItemAnimation();
		} else {
			// 按钮回转
			mToggle.startAnimation(mToggleUnRotateAnimation);
			isRotated = false;
			// 菜单下移
			mMenu.setTranslationY(MENU_TRANSLATION_Y);
		}
	}

	private void playMenuItemAnimation() {
		// 菜单项上移
		for (int i = 0; i < mMenu.getChildCount(); i++) {
			final View child = mMenu.getChildAt(i);
			AnimationSet set = animations.get(i);
			child.setAnimation(set);
			set.start();
		}
	}
}
