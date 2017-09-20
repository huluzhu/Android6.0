package com.example.android60;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import kr.co.namee.permissiongen.PermissionFail;
import kr.co.namee.permissiongen.PermissionGen;
import kr.co.namee.permissiongen.PermissionSuccess;

/**
 * 第三方的android6.0：
 * 优点：使用简单，代码量少，易于阅读
 * 缺点：通过观看源码PermissionGen类，发现在找到注解的方法后，(具体看DoExecuteSuccess)使用反射执行注解方法，
 * 以至于程序的性能会收到影响。
 * <p>
 * 实现思路：
 * 1.添加依赖
 * 2.添加权限
 * 3.判断涉及到用户隐私的功能是否授权了对应权限，如果没有就自动申请
 * 4.申请成功，自动执行的逻辑（使用注解的形式，使用根据简单灵活）
 * 5.用户不同意权限，自动回调的逻辑
 * 6.覆写处理回调的接口方法
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void callphone(View view) {
        //进行动态权限的适配，程序正常运行，判断涉及到的用户隐私的功能是否授权对应权限，没有就自动申请
        //参数：1、上下文  2、int型权限区分码   3.String数组，要申请的权限
        PermissionGen.needPermission(this, 100, new String[]{Manifest.permission.CALL_PHONE});
    }

    public void jump(View view) {
        startActivity(new Intent(MainActivity.this, Main2.class));
    }

    //权限申请成功
    @PermissionSuccess(requestCode = 100)
    private void doCallPhone() {
        Intent intent = new Intent(Intent.ACTION_CALL);
        Uri uri = Uri.parse("tel:" + "10086");
        intent.setData(uri);
        startActivity(intent);
    }

    //权限申请失败
    @PermissionFail(requestCode = 100)
    private void doCallPhoneFail() {
        Toast.makeText(MainActivity.this, "权限没有通过！！！", Toast.LENGTH_SHORT).show();
    }

    //覆写回调处理的接口方法,不要调用父类的方法，而是使用PermissionGen的回调方法，参数多一个上下文，其他一致
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        PermissionGen.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
    }
}
