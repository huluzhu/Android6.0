package com.example.android60;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * android6.0封装思路：减少冗余代码的编写，提高代码的阅读性和开发效率
 * 在基类BaseActivity中为子类提高权限的检查，和权限申请的方法，再在请求权限的回调处理接口中，暴露出子类要覆写并实现业务逻辑的方法，
 * 如果子类继承基类就可以大大简化6.0权限申请的步骤，根据基类暴露出需要覆写的方法，实现其对应的业务逻辑。
 * <p>
 * 0.常量类
 * 1.在清单文件里配置权限
 * 2.创建一个基类，并在基类中为子类提供一个权限检查的方法
 * 3.在基类中为子类提供一个权限申请方法
 * 4.在基类中集中处理请求权限回调的业务逻辑
 * 5.暴露给子类实现具体业务逻辑的方法，子类如果有此功能，覆写方法即可，没有，就不用管此方法
 */
public class MainActivity extends BaseActivity {
    private Button button;
    private Button button1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = (Button) findViewById(R.id.button);
        button1 = (Button) findViewById(R.id.button1);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.this.requewtPermission(Constants.CALL_PHOEN, Manifest.permission.CALL_PHONE, Manifest.permission.WRITE_EXTERNAL_STORAGE);
                boolean permission = MainActivity.this.hasPermission(Manifest.permission.CALL_PHONE);
                Log.d("111111111", permission + "");
            }
        });
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.this.requewtPermission(Constants.WRITE_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE);
                boolean permission = MainActivity.this.hasPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
                Log.d("2222222222", permission + "");
            }
        });

    }

    @Override
    public void doCallPhone() {
        Intent intent = new Intent(Intent.ACTION_CALL);
        Uri uri = Uri.parse("tel:" + "10086");
        intent.setData(uri);
        startActivity(intent);
    }

    @Override
    public void doWrite() {
        super.doWrite();
        try {
            File file = new File(Environment.getExternalStorageDirectory(), "fuck.txt");
            FileOutputStream fos = new FileOutputStream(file);
            String info = "I am a chinanese!";
            fos.write(info.getBytes());
            fos.close();
            System.out.println("写入成功：");

            Object o = null;
            o.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
