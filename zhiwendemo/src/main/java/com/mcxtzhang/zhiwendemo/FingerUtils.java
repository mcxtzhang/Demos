package com.mcxtzhang.zhiwendemo;

import android.Manifest;
import android.app.KeyguardManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import java.security.InvalidAlgorithmParameterException;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.spec.ECGenParameterSpec;

/**
 * Intro:
 * Author: zhangxutong
 * E-mail: mcxtzhang@163.com
 * Home Page: http://blog.csdn.net/zxt0601
 * Created:   2017/6/13.
 * History:
 */

public class FingerUtils {

    public static boolean isOpenFingerDetect(Context context) {
        try {
            return isSupportFingerPrint(context) && hasEnrolledFingerprints(context);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    /**
     * 设备是否支持指纹
     *
     * @param context
     * @return
     */
    public static boolean isSupportFingerPrint(Context context) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M)
            return false;
        KeyguardManager keyguardManager = (KeyguardManager) context.getSystemService(Context.KEYGUARD_SERVICE);
        //Log.d(TAG, "isSupportFingerPrint() called with: context = [" + keyguardManager.isKeyguardSecure() + "]");
        return keyguardManager.isKeyguardSecure();
    }

    /**
     * 是否设置了指纹
     *
     * @param context
     * @return
     */
    public static boolean hasEnrolledFingerprints(Context context) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M)
            return false;
        FingerprintManager manager = getFingerprintManager(context);
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.USE_FINGERPRINT) != PackageManager.PERMISSION_GRANTED) {
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return false;
        } else {

/*            Log.d(TAG, "hasEnrolledFingerprints() called with: context = [" + manager.hasEnrolledFingerprints() + "]");
            Log.d(TAG, "hasEnrolledFingerprints() called with: context = [" + manager.isHardwareDetected() + "]");*/
            return manager.hasEnrolledFingerprints() && manager.isHardwareDetected();
        }
    }


    public static FingerprintManager getFingerprintManager(Context context) {
        FingerprintManager fingerprintManager = null;
        try {
            fingerprintManager = (FingerprintManager) context.getSystemService(Context.FINGERPRINT_SERVICE);
        } catch (Throwable e) {
            Log.w("zxt", "have not class FingerprintManager");
        }
        return fingerprintManager;
    }




    public static final String KEY_NAME = "a_l_y_k_e_y";

    /**
     * Generates an asymmetric key pair in the Android Keystore. Every use of the private key must
     * be authorized by the user authenticating with fingerprint. Public key use is unrestricted.
     */
    public static void createKeyPair() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M)
            return;
        // The enrolling flow for fingerprint. This is where you ask the user to set up fingerprint
        // for your flow. Use of keys is necessary if you need to know if the set of
        // enrolled fingerprints has changed.
        try {
            // Set the alias of the entry in Android KeyStore where the key will appear
            // and the constrains (purposes) in the constructor of the Builder
            KeyPairGenerator keyPairGenerator = providesKeyPairGenerator();
            keyPairGenerator.initialize(
                    new KeyGenParameterSpec.Builder(KEY_NAME,
                            KeyProperties.PURPOSE_SIGN)
                            .setDigests(KeyProperties.DIGEST_SHA256)
                            .setAlgorithmParameterSpec(new ECGenParameterSpec("secp256r1"))
                            // Require the user to authenticate with a fingerprint to authorize
                            // every use of the private key
                            .setUserAuthenticationRequired(true)
                            .build());
            keyPairGenerator.generateKeyPair();
        } catch (InvalidAlgorithmParameterException e) {
            throw new RuntimeException(e);
        }
    }

    public static KeyPairGenerator providesKeyPairGenerator() {
        try {
            return KeyPairGenerator.getInstance(KeyProperties.KEY_ALGORITHM_EC, "AndroidKeyStore");
        } catch (NoSuchAlgorithmException | NoSuchProviderException e) {
            throw new RuntimeException("Failed to get an instance of KeyPairGenerator", e);
        }
    }
}
