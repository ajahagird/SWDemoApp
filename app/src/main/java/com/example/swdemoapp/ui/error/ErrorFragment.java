package com.example.swdemoapp.ui.error;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;

import com.example.swdemoapp.R;
import com.example.swdemoapp.databinding.FragmentErrorBinding;

public class ErrorFragment extends Fragment {

    private ErrorViewModel errorViewModel;
    private FragmentErrorBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        errorViewModel =
                new ViewModelProvider(this).get(ErrorViewModel.class);

        binding = FragmentErrorBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment_activity_main);
                NavOptions options = new NavOptions.Builder()
                        .setPopUpTo(navController.getGraph().getStartDestination() , true)
                        .build();
                navController.navigate(R.id.navigation_home, null, options);
            }
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}