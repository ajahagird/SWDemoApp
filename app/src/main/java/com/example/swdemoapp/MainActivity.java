package com.example.swdemoapp;

import android.os.Bundle;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.WebStorage;
import android.webkit.WebView;
import android.widget.Button;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.swdemoapp.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home,
                R.id.navigation_sw_200, R.id.navigation_sw_500,
                R.id.navigation_500, R.id.navigation_nonsw_500)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);

        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull NavController controller, @NonNull NavDestination destination, @Nullable Bundle arguments) {
                if(destination.getId() == R.id.navigation_error) {
                    navView.setVisibility(View.GONE);
                    getSupportActionBar().hide();
                    findViewById(R.id.clear_webview_button).setVisibility(View.GONE);
                } else {
                    navView.setVisibility(View.VISIBLE);
                    getSupportActionBar().show();
                    findViewById(R.id.clear_webview_button).setVisibility(View.VISIBLE);
                }
            }
        });
        WebView.setWebContentsDebuggingEnabled(true);

        Button button = findViewById(R.id.clear_webview_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WebStorage.getInstance().deleteAllData();

                // Clear all the cookies
                CookieManager.getInstance().removeAllCookies(null);
                CookieManager.getInstance().flush();

                WebView webView = new WebView(getApplicationContext());
                webView.clearCache(true);
                webView.clearFormData();
                webView.clearHistory();
                webView.clearSslPreferences();
                webView.destroy();

                NavController navController = Navigation.findNavController(MainActivity.this, R.id.nav_host_fragment_activity_main);
                navController.navigate(R.id.navigation_home);
            }
        });
    }

}