package com.mcxtzhang.animdemo;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A fragment representing the front of the card.
 */
public class CardFrontFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_positive, container, false);
        view.findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (null != mOnChangeListener) {
                    mOnChangeListener.onChange();
                }
            }
        });
        return view;
    }

    public interface onChangeListener {
        void onChange();
    }

    private onChangeListener mOnChangeListener;

    public void setOnChangeListener(onChangeListener mOnChangeListener) {
        this.mOnChangeListener = mOnChangeListener;
    }
}
