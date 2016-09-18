package com.mcxtzhang.animdemo;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A fragment representing the back of the card.
 */
public class CardBackFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_negative, container, false);
        inflate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (null != mOnChangeListener) {
                    mOnChangeListener.onChange();
                }
            }
        });
        return inflate;
    }

    private CardFrontFragment.onChangeListener mOnChangeListener;

    public void setOnChangeListener(CardFrontFragment.onChangeListener mOnChangeListener) {
        this.mOnChangeListener = mOnChangeListener;
    }
}