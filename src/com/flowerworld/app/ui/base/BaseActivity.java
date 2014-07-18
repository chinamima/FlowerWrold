package com.flowerworld.app.ui.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import com.flowerworld.app.R;
import com.flowerworld.app.interf.IActivityRequestThreadManager;
import com.flowerworld.app.interf.IHttpProcess;
import com.flowerworld.app.tool.http.HttpRequestFacade;
import com.flowerworld.app.ui.widget.Banner;

public abstract class BaseActivity extends Activity implements IActivityRequestThreadManager {

    protected final String TAG = this.getClass().getSimpleName();
    private ActivityRequestThreadManager requestThreadManager = new ActivityRequestThreadManager();
    private BaseTools base = null;
    private Banner mBanner = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        base = new BaseTools(getWindow().getDecorView());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        requestThreadManager.stopAllCurrentActivityRequestThreads();
    }

    public void requestHttp(IHttpProcess process) {
        HttpRequestFacade.requestHttp(this, process);
    }

//	public void requestHttp(int sign, IHttpProcess process)
//	{
//		HttpRequestFacade.requestHttp(this, process, sign);
//	}

    @Override
    public ActivityRequestThreadManager getRequestThreadManager() {
        return requestThreadManager;
    }

    @Override
    public void startActivity(Intent intent) {
        startActivity(intent, true);
    }

    public void startActivity(Intent intent, boolean isAnim) {
        super.startActivity(intent);
        if (isAnim) {
            overridePendingTransition(R.anim.slide_left_in, R.anim.slide_left_out);
        }
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        startActivityForResult(intent, requestCode, true);
    }

    public void startActivityForResult(Intent intent, int requestCode, boolean isAnim) {
        super.startActivityForResult(intent, requestCode);
        if (isAnim) {
            overridePendingTransition(R.anim.slide_left_in, R.anim.slide_left_out);
        }
    }

    @Override
    public void finish() {
        finish(true);
    }

    public void finish(boolean isAnim) {
        super.finish();
        if (isAnim) {
            overridePendingTransition(R.anim.slide_right_in, R.anim.slide_right_out);
        }
    }

//	protected void initBottomBtnSelected()
//	{
//		base.initBottomBtnSelected();
//	}
//
//	protected void initBottomBtn()
//	{
//		base.initBottomBtn();
//	}

    protected void initBanner(int parent, int bg, int title, int titleBg, int leftText, int leftBg, int rightText, int rightBg,
            OnClickListener leftClk, OnClickListener rightClk) {
        removeBanner();
        mBanner = base.initBanner(parent, bg, title, titleBg, leftText, leftBg, rightText, rightBg, leftClk, rightClk);
    }

    protected Banner getBanner() {
        return mBanner;
    }

    protected void removeBanner() {
        Banner banner = getBanner();
        if (null == banner) {
            return;
        }

        ((ViewGroup) banner.getParent()).removeAllViews();
        mBanner = null;
    }

    @Override
    public Context getContextHanler() {
        return this;
    }

}
