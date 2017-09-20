package com.example.android60;

import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
    }

    /**
     * 为子类提供权限检查方法
     *
     * @param permissions：表示不定参数，也就是调用方法的时候可以传入多个string对象（JDK5新特性）
     * @return
     */
    public boolean hasPermission(String... permissions) {
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                return true;
            }
        }
        return false;
    }

    /**
     * 在基类中为子类提供一个权限申请方法
     * 参数1.int.区分码  参数2.权限
     */
    public void requewtPermission(int code, String... permissions) {
        ActivityCompat.requestPermissions(this, permissions, code);
    }

    /**
     * 在基类中集中处理请求权限回调的业务逻辑
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case Constants.CALL_PHOEN:
                //判断打电话权限申请是否成功，成功执行打电话的逻辑
                //注意：因为集合只有一个权限申请，所以参数为0代表打电话的权限
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    doCallPhone();
                } else {
                    Toast.makeText(BaseActivity.this, ",没有权限", Toast.LENGTH_SHORT).show();
                }
                break;
            case Constants.WRITE_EXTERNAL_STORAGE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    doWrite();
                } else {
                    Toast.makeText(BaseActivity.this, ",没有权限", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    /**
     * 打电话业务逻辑
     * 写一个空实现，而不是抽象方法，因为有一些页面没有打电话的功能，如果是抽象方法的话，还要必须覆写此方法，
     * 子类如果有此功能，覆写此方法即可，不用再管权限的配置
     */
    public void doCallPhone() {
    }

    public void doWrite() {
    }
}
