package com.example.swdemoapp.client;

import android.app.Activity;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Handler;
import android.os.Looper;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.swdemoapp.R;

public class WebViewClient extends android.webkit.WebViewClient {
    private final Activity activity;

    public WebViewClient(Activity activity) {
        super();
        this.activity = activity;
    }

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        Toast.makeText(view.getContext(), "onPageStarted", Toast.LENGTH_SHORT).show();
        super.onPageStarted(view, url, favicon);
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        Toast.makeText(view.getContext(), "onPageFinished", Toast.LENGTH_SHORT).show();
        super.onPageFinished(view, url);
    }

    @Override
    public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
        Toast.makeText(view.getContext(), "onReceivedError", Toast.LENGTH_SHORT).show();
        super.onReceivedError(view, request, error);
    }

    @Override
    public void onReceivedHttpError(WebView view, WebResourceRequest request, WebResourceResponse errorResponse) {
        Toast.makeText(view.getContext(), "onReceivedHttpError", Toast.LENGTH_SHORT).show();
        final Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                NavController navController = Navigation.findNavController(activity, R.id.nav_host_fragment_activity_main);
                navController.navigate(R.id.navigation_error);
            }
        }, 1000);

        super.onReceivedHttpError(view, request, errorResponse);
    }

    @Override
    public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
        Toast.makeText(view.getContext(), "onReceivedSslError", Toast.LENGTH_SHORT).show();
        super.onReceivedSslError(view, handler, error);
    }
}
