package com.example.android60;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import kr.co.namee.permissiongen.PermissionSuccess;

/**
 * Created by ${hujiqiang} on 2017/09/20.
 */

public class Main2 extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main2);
    }

    public void callPhone(View view) {
        doCallPhone();
    }

    @PermissionSuccess(requestCode = 100)
    private void doCallPhone() {
        Intent intent = new Intent(Intent.ACTION_CALL);
        Uri uri = Uri.parse("tel:" + "10086");
        intent.setData(uri);
        startActivity(intent);
    }
}
