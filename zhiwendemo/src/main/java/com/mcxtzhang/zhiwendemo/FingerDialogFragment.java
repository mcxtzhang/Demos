package com.mcxtzhang.zhiwendemo;

import android.app.DialogFragment;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Intro:
 * Author: zhangxutong
 * E-mail: mcxtzhang@163.com
 * Home Page: http://blog.csdn.net/zxt0601
 * Created:   2017/6/14.
 * History:
 */

public class FingerDialogFragment extends DialogFragment {
    private FingerprintUiHelper mFingerprintUiHelper;
    FingerprintManager.CryptoObject cryptoObject;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);

        View v = inflater.inflate(R.layout.fingerprint_dialog_container, container, false);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            //FingerUtils.generateKey();
            //cryptoObject = new FingerprintManager.CryptoObject(FingerUtils.getCipher());
        }

        mFingerprintUiHelper = new FingerprintUiHelper.FingerprintUiHelperBuilder(FingerUtils.getFingerprintManager(getActivity()))
                .build((ImageView) v.findViewById(R.id.fingerprint_icon),
                        (TextView) v.findViewById(R.id.fingerprint_status),
                        new FingerprintUiHelper.Callback() {
                            @Override
                            public void onAuthenticated(FingerprintManager.AuthenticationResult result) {
                                Toast.makeText(getActivity(), "滚吧辣鸡", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onError() {
                                Toast.makeText(getActivity(), "你真的该滚了", Toast.LENGTH_SHORT).show();
                            }
                        });


        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        mFingerprintUiHelper.startListening(cryptoObject);
    }

    @Override
    public void onPause() {
        super.onPause();
        mFingerprintUiHelper.stopListening();
    }
}
