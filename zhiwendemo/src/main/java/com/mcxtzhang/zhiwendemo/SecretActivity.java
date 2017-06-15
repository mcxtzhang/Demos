package com.mcxtzhang.zhiwendemo;

import android.Manifest;
import android.content.pm.PackageManager;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.mcxtzhang.zhiwendemo.utils.FingerLoginUtils;
import com.mcxtzhang.zhiwendemo.utils.FingerUtils;

import javax.crypto.Cipher;

public class SecretActivity extends AppCompatActivity {
    //String mResult;

    final String mAccount = "zxt";
    //final String mContent = ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_secret);
        findViewById(R.id.buttonCreate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //FingerUtils.generateKey();
                FingerLoginUtils.savePwd(SecretActivity.this, mAccount, "123456789");

            }
        });


        findViewById(R.id.buttonGet).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M)
                    return;

                try {
                    Cipher cipher = FingerUtils.getCipher();
/*                    mEncrypted = cipher.doFinal(mContent.getBytes());
                    Log.d("zxt", "mEncrypted:" + mEncrypted);*/


                    /*final FingerprintManager.CryptoObject crypto = new FingerprintManager.CryptoObject(cipher);

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
                    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M)
                        return;
                    */


/*                    FingerUtils.getFingerprintManager(SecretActivity.this)
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
                            }, null);*/

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
                    byte[] iv = FingerLoginUtils.getIv(SecretActivity.this, mAccount);
                    final byte[] pwd = FingerLoginUtils.getPwd(SecretActivity.this, mAccount);
                    if (iv == null || pwd == null) {
                        Toast.makeText(SecretActivity.this, "存的信息为空", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    final FingerprintManager.CryptoObject crypto = new FingerprintManager.CryptoObject(FingerUtils.getDecriptCipher(iv));
                    if (ActivityCompat.checkSelfPermission(SecretActivity.this, Manifest.permission.USE_FINGERPRINT) != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                    FingerUtils.getFingerprintManager(SecretActivity.this).authenticate(crypto, null, 0, new FingerprintManager.AuthenticationCallback() {
                        @Override
                        public void onAuthenticationSucceeded(FingerprintManager.AuthenticationResult result) {
                            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M)
                                return;
                            try {
                                final Cipher cipher = result.getCryptoObject().getCipher();
                                //Log.d("tag", "Base 64 of data to decrypt is:\n" + Base64.encodeToString(encryptedToken, Base64.URL_SAFE) + "\n");
                                byte[] decrypted = cipher.doFinal(pwd);
                                Log.d("zxt", "decrypted:" + decrypted);
                                Log.d("zxt", "Decrypted data is:" + new String(decrypted));

                                Toast.makeText(SecretActivity.this, "存的信息为"+new String(decrypted), Toast.LENGTH_SHORT).show();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }, null);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                /*try {
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
                }*/
            }
        });


    }
}
