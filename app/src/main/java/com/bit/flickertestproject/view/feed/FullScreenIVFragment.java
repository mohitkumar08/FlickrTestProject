package com.bit.flickertestproject.view.feed;


import android.app.Dialog;
import android.os.Bundle;


import android.support.v4.app.DialogFragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;

import com.bit.flickertestproject.R;
import com.bit.flickertestproject.util.Constants;
import com.squareup.picasso.Picasso;

public class FullScreenIVFragment extends DialogFragment {

    private View view;

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            dialog.getWindow().setLayout(width, height);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_full_screen_iv, container, false);
        initView();
        return view;
    }

    private void initView() {

        if (getArguments() != null) {
            if (getArguments().containsKey(Constants.IMAGE_PATH_KEY)) {
               ImageView demoView = view.findViewById(R.id.image_view);
                String imagePath = getArguments().getString(Constants.IMAGE_PATH_KEY);
                Picasso.get().load(imagePath).into(demoView);
            }
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    @Override
    public void onResume() {
        super.onResume();

        getDialog().setOnKeyListener((dialog, keyCode, event) -> {
            if ((keyCode == KeyEvent.KEYCODE_BACK)) {
                dismiss();
                return true;
            }
            return false;
        });

    }
}
