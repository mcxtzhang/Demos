package com.mcxtzhang.zhiwendemo;

import android.Manifest;
import android.content.pm.PackageManager;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.Bundle;
import android.security.keystore.KeyProperties;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

public class SecretActivity extends AppCompatActivity {

    byte[] mIV;
    byte[] mEncrypted;
    //String mResult;
    final String mContent = "123456789";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_secret);
        findViewById(R.id.buttonCreate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FingerUtils.generateKey();
            }
        });


        findViewById(R.id.buttonGet).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M)
                    return;
                final Cipher cipher;
                try {
                    SecretKey key = FingerUtils.getKey();
                    if (key == null)
                        return;

                    cipher = Cipher.getInstance(KeyProperties.KEY_ALGORITHM_AES + "/" + KeyProperties.BLOCK_MODE_CBC + "/" + KeyProperties.ENCRYPTION_PADDING_PKCS7);
                    cipher.init(Cipher.ENCRYPT_MODE, key);

                    final FingerprintManager.CryptoObject crypto = new FingerprintManager.CryptoObject(cipher);

                    if (ActivityCompat.checkSelfPermission(SecretActivity.this, Manifest.permission.USE_FINGERPRINT) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return;
                    }
                    FingerUtils.getFingerprintManager(SecretActivity.this)
                            .authenticate(crypto, null, 0, new FingerprintManager.AuthenticationCallback() {
                                @Override
                                public void onAuthenticationSucceeded(FingerprintManager.AuthenticationResult result) {
                                    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M)
                                        return;
                                    try {
                                        final Cipher cipher = result.getCryptoObject().getCipher();
                                        mEncrypted = cipher.doFinal(mContent.getBytes());
                                        mIV = cipher.getIV();

                                        Log.d("zxt", "mEncrypted:" + mEncrypted);
                                        Log.d("zxt", "mIV:" + mIV);
                                    } catch (Exception e) {

                                    }

                                }
                            }, null);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        findViewById(R.id.buttonDecipher).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M)
                        return;
                    SecretKey key = FingerUtils.getKey();
                    if (key == null)
                        return;
                    final Cipher cipher = Cipher.getInstance(KeyProperties.KEY_ALGORITHM_AES + "/" + KeyProperties.BLOCK_MODE_CBC + "/" + KeyProperties.ENCRYPTION_PADDING_PKCS7);
                    cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(mIV));
                    final FingerprintManager.CryptoObject crypto = new FingerprintManager.CryptoObject(cipher);

                    if (ActivityCompat.checkSelfPermission(SecretActivity.this, Manifest.permission.USE_FINGERPRINT) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return;
                    }
                    FingerUtils.getFingerprintManager(SecretActivity.this).authenticate(crypto, null, 0, new FingerprintManager.AuthenticationCallback() {
                        @Override
                        public void onAuthenticationSucceeded(FingerprintManager.AuthenticationResult result) {
                            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M)
                                return;
                            super.onAuthenticationSucceeded(result);
                            final Cipher cipher = result.getCryptoObject().getCipher();
                            //Log.d("tag", "Base 64 of data to decrypt is:\n" + Base64.encodeToString(encryptedToken, Base64.URL_SAFE) + "\n");
                            try {
                                byte[] decrypted = cipher.doFinal(mEncrypted);
                                Log.d("zxt", "decrypted:" + decrypted);
                                Log.d("zxt", "Decrypted data is:" + new String(decrypted));
                                //Log.d("zxt", "mResult:" + mResult);
                            } catch (IllegalBlockSizeException | BadPaddingException e) {
                            }
                        }
                    }, null);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });


    }
}
