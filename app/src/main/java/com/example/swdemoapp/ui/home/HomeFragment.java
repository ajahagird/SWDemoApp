package com.example.swdemoapp.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.swdemoapp.R;
import com.example.swdemoapp.client.WebViewClient;
import com.example.swdemoapp.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final WebView webView = binding.webview;
        webView.setWebViewClient(new WebViewClient(getActivity()));
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setJavaScriptEnabled(true);
        if(getArguments() != null) {
            final String url = HomeFragmentArgs.fromBundle(getArguments()).getUrl();
            webView.loadUrl(url);
        } else {
            webView.loadUrl("https://massive-delirious-wish.glitch.me");
        }

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding.webview.destroy();
        binding = null;
    }
}