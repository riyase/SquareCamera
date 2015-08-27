package com.desmond.squarecamera;

import android.content.Intent;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;


public class CameraActivity extends AppCompatActivity {

    public static final String TAG = CameraActivity.class.getSimpleName();
    public static final String KEY_CAM_ID = "camId";
    public static final String KEY_SHOW_PREVIEW = "showPreview";
    private int cameraID;
    private boolean showPreview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.squarecamera__CameraFullScreenTheme);
        super.onCreate(savedInstanceState);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        setContentView(R.layout.squarecamera__activity_camera);

        cameraID = getIntent().getIntExtra(KEY_CAM_ID,Camera.CameraInfo.CAMERA_FACING_BACK);
        showPreview = getIntent().getBooleanExtra(KEY_SHOW_PREVIEW,true);

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, CameraFragment.newInstance(cameraID,showPreview) , CameraFragment.TAG)
                    .commit();
        }
    }

    public void returnPhotoUri(Uri uri) {
        Intent data = new Intent();
        data.setData(uri);

        if (getParent() == null) {
            setResult(RESULT_OK, data);
        } else {
            getParent().setResult(RESULT_OK, data);
        }

        finish();
    }

    public void onCancel(View view) {
        if (view == null) {
            Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fragment_container);
            if (fragment instanceof EditSavePhotoFragment) {
                // Do nothing
            } else {
                getSupportFragmentManager().popBackStack();
            }
        } else {
            getSupportFragmentManager().popBackStack();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        onCancel(null);
    }
}
