package com.nataejna.app;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class MainActivity extends AppCompatActivity {

    private static final String SITE = "https://mhazim929282.pythonanywhere.com/";

    private WebView webView;
    private ProgressBar progressBar;
    private SwipeRefreshLayout swipeRefresh;
    private LinearLayout offlineView;
    private TextView bgWatermark;
    private boolean firstLoad = true;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        webView = findViewById(R.id.webView);
        progressBar = findViewById(R.id.progressBar);
        swipeRefresh = findViewById(R.id.swipeRefresh);
        offlineView = findViewById(R.id.offlineView);
        bgWatermark = findViewById(R.id.bgWatermark);
        Button retryBtn = findViewById(R.id.retryBtn);

        // Animate AWAD HAZIM floating in background
        startWatermarkAnimation();

        // Swipe to refresh
        swipeRefresh.setColorSchemeColors(0xFF22C55E);
        swipeRefresh.setProgressBackgroundColorSchemeColor(0xFF0A1A14);
        swipeRefresh.setOnRefreshListener(() -> {
            if (isOnline()) {
                webView.reload();
            } else {
                showOffline();
                swipeRefresh.setRefreshing(false);
            }
        });

        retryBtn.setOnClickListener(v -> {
            if (isOnline()) {
                hideOffline();
                webView.loadUrl(SITE);
            }
        });

        setupWebView();
        loadSite();
    }

    private void startWatermarkAnimation() {
        ObjectAnimator x = ObjectAnimator.ofFloat(bgWatermark, "translationX", -60f, 60f);
        x.setDuration(9000);
        x.setRepeatMode(ValueAnimator.REVERSE);
        x.setRepeatCount(ValueAnimator.INFINITE);
        x.setInterpolator(new AccelerateDecelerateInterpolator());
        x.start();

        ObjectAnimator y = ObjectAnimator.ofFloat(bgWatermark, "translationY", -40f, 40f);
        y.setDuration(7000);
        y.setRepeatMode(ValueAnimator.REVERSE);
        y.setRepeatCount(ValueAnimator.INFINITE);
        y.setInterpolator(new AccelerateDecelerateInterpolator());
        y.start();

        ObjectAnimator rot = ObjectAnimator.ofFloat(bgWatermark, "rotation", -26f, -18f);
        rot.setDuration(11000);
        rot.setRepeatMode(ValueAnimator.REVERSE);
        rot.setRepeatCount(ValueAnimator.INFINITE);
        rot.setInterpolator(new AccelerateDecelerateInterpolator());
        rot.start();
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void setupWebView() {
        WebSettings s = webView.getSettings();
        s.setJavaScriptEnabled(true);
        s.setDomStorageEnabled(true);
        s.setDatabaseEnabled(true);
        s.setAllowFileAccess(false);
        s.setAllowContentAccess(true);
        s.setBuiltInZoomControls(false);
        s.setDisplayZoomControls(false);
        s.setLoadWithOverviewMode(true);
        s.setUseWideViewPort(true);
        s.setCacheMode(WebSettings.LOAD_DEFAULT);
        s.setMediaPlaybackRequiresUserGesture(false);

        webView.setBackgroundColor(0xFF0A1A14);
        webView.setOverScrollMode(View.OVER_SCROLL_NEVER);

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                view.loadUrl(request.getUrl().toString());
                return true;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                progressBar.setVisibility(View.VISIBLE);
                progressBar.setProgress(0);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                progressBar.setVisibility(View.GONE);
                swipeRefresh.setRefreshing(false);
                firstLoad = false;
                injectPolish(view);
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                if (request.isForMainFrame()) {
                    showOffline();
                }
            }
        });

        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                progressBar.setProgress(newProgress);
                if (newProgress >= 100) {
                    progressBar.setVisibility(View.GONE);
                } else {
                    progressBar.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    private void loadSite() {
        if (isOnline()) {
            hideOffline();
            webView.loadUrl(SITE);
        } else {
            showOffline();
        }
    }

    private void injectPolish(WebView view) {
        String js =
            "(function(){" +
            "if(document.getElementById('awad-hazim-wm'))return;" +

            // CSS
            "var css=document.createElement('style');" +
            "css.innerHTML=`" +
            "html,body{background:#0A1A14!important;}" +
            "body{font-family:system-ui,-apple-system,'Noto Naskh Arabic',sans-serif!important;}" +
            "::-webkit-scrollbar{width:4px;}" +
            "::-webkit-scrollbar-thumb{background:#22C55E;border-radius:4px;}" +
            "#awad-hazim-wm{" +
            "position:fixed;top:50%;left:50%;" +
            "transform:translate(-50%,-50%) rotate(-22deg);" +
            "font-size:clamp(28px,9vw,56px);font-weight:800;letter-spacing:0.18em;" +
            "color:rgba(34,197,94,0.11);pointer-events:none;z-index:9999;" +
            "white-space:nowrap;user-select:none;" +
            "animation:awadFloat 11s ease-in-out infinite alternate;" +
            "}" +
            "@keyframes awadFloat{" +
            "0%{transform:translate(-55%,-48%) rotate(-26deg);}" +
            "50%{transform:translate(-45%,-52%) rotate(-18deg);}" +
            "100%{transform:translate(-50%,-45%) rotate(-24deg);}" +
            "}" +
            "`;document.head.appendChild(css);" +

            // Watermark element
            "var wm=document.createElement('div');" +
            "wm.id='awad-hazim-wm';" +
            "wm.textContent='AWAD HAZIM';" +
            "document.body.appendChild(wm);" +
            "})();";
        view.evaluateJavascript(js, null);
    }

    private boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm == null) return false;
        NetworkCapabilities caps = cm.getNetworkCapabilities(cm.getActiveNetwork());
        return caps != null && (
                caps.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                caps.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                caps.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)
        );
    }

    private void showOffline() {
        offlineView.setVisibility(View.VISIBLE);
        swipeRefresh.setRefreshing(false);
        progressBar.setVisibility(View.GONE);
    }

    private void hideOffline() {
        offlineView.setVisibility(View.GONE);
    }

    @Override
    public void onBackPressed() {
        if (webView != null && webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onDestroy() {
        if (webView != null) {
            webView.destroy();
        }
        super.onDestroy();
    }
}
