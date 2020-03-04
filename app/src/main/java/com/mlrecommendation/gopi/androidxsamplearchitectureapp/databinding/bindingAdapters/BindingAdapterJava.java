package com.mlrecommendation.gopi.androidxsamplearchitectureapp.databinding.bindingAdapters;

import android.view.View;
import android.widget.Toast;
import androidx.databinding.BindingAdapter;

public class BindingAdapterJava {

    @BindingAdapter("viewVisibilityGopiCustom")
    public static void viewVisibilityGopiCustom(View view, boolean isVisible) {
        if(isVisible){
            view.setVisibility(View.VISIBLE);
        } else {
            view.setVisibility(View.GONE);
        }
    }
}
