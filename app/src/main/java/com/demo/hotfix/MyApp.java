package com.demo.hotfix;

import android.app.Application;
import android.content.Context;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import java.io.File;

public class MyApp extends Application {
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        String patchPath = Environment.getExternalStorageDirectory().getAbsolutePath() +"/Pictures"+ "/patch.dex";
        HotFixUtil.startFix(this, patchPath);
        SourceFixUtils sourceFixUtils = new SourceFixUtils();
        Log.e("Tags",Environment.getExternalStorageDirectory().getPath());
        sourceFixUtils.fix(this,Environment.getExternalStorageDirectory().getAbsolutePath()+"/aa.apk");
        Log.e("attachBaseContext", "==="+Environment.getExternalStorageDirectory().getAbsolutePath() );
    }
}
