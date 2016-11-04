
package com.stj.light;

import android.app.Activity;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;

public class LightActivity extends Activity {
    private Button mSwitchBtn = null;
    private Camera mCamera = null;
    private Parameters mParameters = null;
    private boolean isFlashLightOpen = false;
    private RelativeLayout mRelativeLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_light_handler);
        mSwitchBtn = (Button) findViewById(R.id.btn_light);
        mRelativeLayout = (RelativeLayout) findViewById(R.id.content_rl);

        mSwitchBtn.setOnClickListener(new SwitchFlashBtn());
        initCamera();
        openFlashLight();
    }

    private void initCamera() {
        if (mCamera == null) {
            mCamera = Camera.open();
        }
        if (mCamera != null) {
            mParameters = mCamera.getParameters();
        }
    }

    class SwitchFlashBtn implements OnClickListener {
        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.btn_light) {
                if (isFlashLightOpen) {
                    closeFlashLight();
                } else {
                    openFlashLight();
                }
            }
        }
    }

    @Override
    protected void onDestroy() {
        releaseFlashLight();
        super.onDestroy();
    }

    private void openFlashLight() {
        if (mCamera != null) {
            isFlashLightOpen = true;
            mParameters.setFlashMode(Parameters.FLASH_MODE_TORCH);
            mCamera.setParameters(mParameters);
            mSwitchBtn.setBackgroundResource(R.drawable.button_on);
            mRelativeLayout.setBackgroundResource(R.drawable.light_on);

        }
    }

    private void closeFlashLight() {
        if (mCamera != null) {
            isFlashLightOpen = false;
            mParameters.setFlashMode(Parameters.FLASH_MODE_OFF);
            mCamera.setParameters(mParameters);
            mSwitchBtn.setBackgroundResource(R.drawable.button_off);
            mRelativeLayout.setBackgroundResource(R.drawable.light_off);
        }
    }

    private void releaseFlashLight() {
        if (mCamera != null) {
            closeFlashLight();
            mCamera.release();
        }
    }
    
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_DPAD_CENTER){
            mSwitchBtn.performClick();
        }
        return super.onKeyDown(keyCode, event);
    }
}
