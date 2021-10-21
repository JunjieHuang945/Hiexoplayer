package net.sunniwell.app.stb.exoplayer;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;

import androidx.appcompat.app.AppCompatActivity;

public class MainWebAvtivity extends AppCompatActivity {
    private WebView mWebView;
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_web);
        mWebView = findViewById(R.id.web_view);
        mWebView.setWebChromeClient(new WebChromeClient());
        WebSettings mWebSettings = mWebView.getSettings();
        mWebSettings.setJavaScriptEnabled(true);
        mWebSettings.setRenderPriority(WebSettings.RenderPriority.HIGH);
        mWebSettings.setUserAgentString("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/38.0.2125.122 UBrowser/4.0.3214.0 Safari/537.36");
        Intent mIntent = getIntent();
        url = mIntent.getStringExtra("url");
        mWebView.loadUrl(url);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mWebView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mWebView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ((ViewGroup) mWebView.getParent()).removeView(mWebView);
        mWebView.destroy();
    }
}
