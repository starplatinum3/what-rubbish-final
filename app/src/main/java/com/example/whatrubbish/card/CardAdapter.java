package com.example.whatrubbish.card;


//import android.support.v7.widget.CardView;

import androidx.cardview.widget.CardView;

public interface CardAdapter {

    int MAX_ELEVATION_FACTOR = 8;

    float getBaseElevation();

    CardView getCardViewAt(int position);

    int getCount();
}
