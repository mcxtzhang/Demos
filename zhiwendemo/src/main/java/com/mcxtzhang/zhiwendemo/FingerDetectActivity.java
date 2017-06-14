package com.mcxtzhang.zhiwendemo;

import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.security.KeyFactory;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.cert.CertificateException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;

import static com.mcxtzhang.zhiwendemo.FingerUtils.KEY_NAME;

public class FingerDetectActivity extends AppCompatActivity {

    private FingerprintUiHelper mFingerprintUiHelper;
    FingerprintManager.CryptoObject cryptoObject;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fingerprint_dialog_content);
        if (initSignature()) {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                cryptoObject = new FingerprintManager.CryptoObject(mSignature);
            }

        }

        //enroll();


        mFingerprintUiHelper = new FingerprintUiHelper.FingerprintUiHelperBuilder(FingerUtils.getFingerprintManager(this))
                .build((ImageView) findViewById(R.id.fingerprint_icon),
                        (TextView) findViewById(R.id.fingerprint_status),
                        new FingerprintUiHelper.Callback() {
                            @Override
                            public void onAuthenticated(FingerprintManager.AuthenticationResult result) {
                                Toast.makeText(FingerDetectActivity.this, "滚吧辣鸡", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onError() {
                                Toast.makeText(FingerDetectActivity.this, "你真的该滚了", Toast.LENGTH_SHORT).show();
                            }
                        });


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


    /**
     * Enrolls a user to the fake backend.
     */
    private void enroll() {
        try {
            KeyStore keyStore = KeyStore.getInstance("AndroidKeyStore");
            keyStore.load(null);
            PublicKey publicKey = keyStore.getCertificate(KEY_NAME).getPublicKey();
            // Provide the public key to the backend. In most cases, the key needs to be transmitted
            // to the backend over the network, for which Key.getEncoded provides a suitable wire
            // format (X.509 DER-encoded). The backend can then create a PublicKey instance from the
            // X.509 encoded form using KeyFactory.generatePublic. This conversion is also currently
            // needed on API Level 23 (Android M) due to a platform bug which prevents the use of
            // Android Keystore public keys when their private keys require user authentication.
            // This conversion creates a new public key which is not backed by Android Keystore and
            // thus is not affected by the bug.
            KeyFactory factory = KeyFactory.getInstance(publicKey.getAlgorithm());
            X509EncodedKeySpec spec = new X509EncodedKeySpec(publicKey.getEncoded());
            PublicKey verificationKey = factory.generatePublic(spec);

            //mStoreBackend.enroll("user", "password", verificationKey);

        } catch (KeyStoreException | CertificateException | NoSuchAlgorithmException |
                IOException | InvalidKeySpecException e) {
            e.printStackTrace();
        }
    }












    Signature mSignature;
    /**
     * Initialize the {@link Signature} instance with the created key in the
     * {@link #createKeyPair()} method.
     *
     * @return {@code true} if initialization is successful, {@code false} if the lock screen has
     * been disabled or reset after the key was generated, or if a fingerprint got enrolled after
     * the key was generated.
     */
    private boolean initSignature() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M)
            return false;
        try {
            KeyStore keyStore = providesKeystore();
            keyStore.load(null);
            PrivateKey key = (PrivateKey) keyStore.getKey(KEY_NAME, null);
            mSignature = providesSignature(keyStore);
            mSignature.initSign(key);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public KeyStore providesKeystore() {
        try {
            return KeyStore.getInstance("AndroidKeyStore");
        } catch (KeyStoreException e) {
            throw new RuntimeException("Failed to get an instance of KeyStore", e);
        }
    }

    public Signature providesSignature(KeyStore keyStore) {
        try {
            return Signature.getInstance("SHA256withECDSA");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Failed to get an instance of Signature", e);
        }
    }
}
