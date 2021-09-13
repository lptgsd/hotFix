package com.demo.hotfix;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * 资源文件 整体替换工具类
 * 原理：通过反射调用ContextImpl,将mResDir指向新的apk文件
 */
public class SourceFixUtils {
    public void fix(Context context,String path){
        File file = new File(path);
        if(!file.exists()){
            Toast.makeText(context, "资源Apk文件不存在", Toast.LENGTH_SHORT).show();
            return;
        }
        try {
            Class<?> contextImpl = Class.forName("android.app.ContextImpl");
            Class<?> loadedApkClass;
            try {
                loadedApkClass = Class.forName("android.app.LoadedApk");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                loadedApkClass = Class.forName("android.app.ActivityThread$PackageInfo");
            }
            Field mPackageInfo = contextImpl.getDeclaredField("mPackageInfo");
            mPackageInfo.setAccessible(true);
            Method methodGetImpl = contextImpl.getDeclaredMethod("getImpl", Context.class);
            methodGetImpl.setAccessible(true);
            Object objImpl = methodGetImpl.invoke(null,context);
            Object loadedApkObj = mPackageInfo.get(objImpl);
            Field resDir = loadedApkClass.getDeclaredField("mResDir");
            resDir.setAccessible(true);
            //String path=""; 资源apk路劲
            resDir.set(loadedApkObj,path);
        }catch (Exception e){
            Log.e("tags",e.getMessage());
        }
    }
}
