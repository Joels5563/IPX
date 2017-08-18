package com.ipx.demo_app;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

/**
 * 文章内容的视图
 */
public class ArticleDetailActivity extends BaseActionBarActivity {
    //进度条
    private ProgressBar progressBar;
    private WebView webView;

    @SuppressLint("setJavaScriptEnabled")
    @Override
    protected void initWidgets() {
        progressBar = (ProgressBar) findViewById(R.id.loading_progressbar);
        webView = (WebView) findViewById(R.id.articles_webview);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                WebSettings settings = webView.getSettings();
                settings.setBuiltInZoomControls(true);
                view.loadUrl(request.getUrl().toString());
                return true;
            }
        });

        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                progressBar.setVisibility(View.VISIBLE);
                progressBar.setProgress(newProgress);
                if (newProgress == 100) {
                    //加载完成,隐藏进度条
                    progressBar.setVisibility(View.GONE);
                }
            }
        });
    }

    /**
     * 创建完之后的行为
     */
    @Override
    protected void afterOnCreate() {
        Bundle extraBundle = getIntent().getExtras();
        webView.loadUrl(extraBundle.getString("url"));
    }

    /**
     * 获取contentView的资源id
     */
    @Override
    protected int getContentViewResId() {
        return R.layout.detail_activity;
    }
}
