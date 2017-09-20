package com.example.dell.callphoen;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

/**
 * android6.0权限实现思路：
 * 1.所有权限都必须先在清单文件配置，否则程序会报错，且在6.0系统上报错还不容易查找，直接退出了程序
 * 2.要判断用户手机系统是否是android6.0+版本，如果不是，就不做任何操作，如果是，就调用对应的代码（不做的话，功能不受影响，做了，性能会有所提升）
 * 3.判断涉及到用户隐私的功能是否授权了对应权限，没有申请权限，做权限的申请即可。申请了权限做逻辑操作。
 * 4.异步回调接口，判断是否通过了授权，通过了做对应逻辑操作即可
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void callphone(View view) {
        //参数1：上下文  2. 权限  3.PackageManager.PERMISSION_GRANTED授权权限
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            //参数1 上下文  2  字符串数组，可以一次申请多个权限  3.int，请求码
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, 0);
        } else {
            doCallPhone();
        }
    }

    //异步回调，判断是否通过了权限，做对应逻辑处理
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //因为这是处理所有权限申请回调，为了方便做对应权限的逻辑操作，使用switch判断之前请求码的设置，区分权限
        switch (requestCode) {
            case 0://打电话权限的回调处理，判断打电话申请权限是否成功，成功就执行打电话的逻辑处理
                //注意：因为集合里只有一个=参数，所以0代表打电话权限
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    doCallPhone();
                } else {
                    Toast.makeText(MainActivity.this, "授权不成功", Toast.LENGTH_SHORT).show();
                }

                break;
        }
    }

    private void doCallPhone() {
        Intent intent = new Intent(Intent.ACTION_CALL);
        Uri uri = Uri.parse("tel:" + "10086");
        intent.setData(uri);
        try {
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
