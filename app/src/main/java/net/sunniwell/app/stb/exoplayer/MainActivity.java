package net.sunniwell.app.stb.exoplayer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private EditText mInput, input2;
    private Button mPaly, mPaly2, goweb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mInput = findViewById(R.id.input);
        mPaly = findViewById(R.id.paly);
        mPaly2 = findViewById(R.id.paly2);
        mPaly2.setOnClickListener((v) -> {
            String url = mInput.getText().toString();
            if (TextUtils.isEmpty(url)) {
                Toast.makeText(MainActivity.this, "输入对应的视频地址！", Toast.LENGTH_SHORT).show();
                return;
            }
            Intent mIntent = new Intent(MainActivity.this, MainPlayActivity.class);
            mIntent.putExtra("url", url);
            startActivity(mIntent);
        });
        mPaly.setOnClickListener((v) -> {
            String url = mInput.getText().toString();
            if (TextUtils.isEmpty(url)) {
                Toast.makeText(MainActivity.this, "输入对应的视频地址！", Toast.LENGTH_SHORT).show();
                return;
            }
            Intent mIntent = new Intent(MainActivity.this, MainIjkPlayer.class);
            mIntent.putExtra("url", url);
            startActivity(mIntent);
        });
        input2 = findViewById(R.id.input2);
        goweb = findViewById(R.id.goweb);
        goweb.setOnClickListener((v) -> {
            String url = input2.getText().toString();
            if (TextUtils.isEmpty(url)) {
                Toast.makeText(MainActivity.this, "输入对应的网址！", Toast.LENGTH_SHORT).show();
                return;
            }
            Intent mIntent = new Intent(MainActivity.this, MainWebAvtivity.class);
            mIntent.putExtra("url", url);
            startActivity(mIntent);
        });
        if (checkSelfPermission()) {
            mPaly.setEnabled(true);
            mPaly2.setEnabled(true);
        } else {
            mPaly.setEnabled(false);
            mPaly2.setEnabled(false);
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_EXTERNAL_STORAGE);
        }
    }

    private static final int REQUEST_EXTERNAL_STORAGE = 0x234;

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_EXTERNAL_STORAGE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                // Permission Granted 授予权限
                //处理授权之后逻辑
                mPaly.setEnabled(true);
                mPaly2.setEnabled(true);
            } else {
                // Permission Denied 权限被拒绝
                Toast.makeText(this, "权限被禁用", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private boolean checkSelfPermission() {
        int permission = ActivityCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permission != PackageManager.PERMISSION_GRANTED) {
            return false;
        }
        permission = ActivityCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE);
        if (permission != PackageManager.PERMISSION_GRANTED) {
            return false;
        }
        return true;
    }
}