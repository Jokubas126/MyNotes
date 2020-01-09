package com.example.mynotes.view.popup_window_controllers;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.mynotes.R;
import com.example.mynotes.presenters.EditNoteActivityPresenter;

public class RecorderWindow implements View.OnClickListener {

    Context context;
    View popupView;
    EditNoteActivityPresenter presenter;

    ConstraintLayout layout;
    ImageButton recordButton;
    CardView cardView;

    public RecorderWindow(){}

    public RecorderWindow(Context context, View popupView, EditNoteActivityPresenter presenter){
        this.context = context;
        this.popupView = popupView;
        this.presenter = presenter;
        setupWindow();
    }

    public void setupWindow(){
        recordButton = popupView.findViewById(R.id.record_button);
        layout = popupView.findViewById(R.id.recorder_background);
        cardView = popupView.findViewById(R.id.recorder_card_view);
        recordButton.setOnClickListener(this);
        layout.setOnClickListener(this);
        cardView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        //Log.d("RecorderWindow", "onClick: CLICKED");
        switch (v.getId()){
            case R.id.record_button:
                Log.d("RecorderWindow", "onClick: BUTTON");
                break;

            case R.id.recorder_background:
                presenter.dismissPopupWindow();
                break;

            case R.id.recorder_card_view:
                Log.d("RecorderWindow", "onClick: CARD");
                break;
        }
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void setPopupView(View popupView) {
        this.popupView = popupView;
    }
}
