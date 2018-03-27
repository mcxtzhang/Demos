package com.mcxtzhang.github.test;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.mcxtzhang.github.R;

public class UploadPhotoJumpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_photo_jump);

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText etSmartMark = (EditText) findViewById(R.id.etSmartMark);
                EditText etShopId = (EditText) findViewById(R.id.etShopId);

                uploadShopPhoto(UploadPhotoJumpActivity.this, etShopId.getText().toString().trim(), etSmartMark.getText().toString().trim());
            }
        });
    }


    public void uploadShopPhoto(Context context, String shopId, String smartMark) {

        if (context == null) {
            return;
        }

        Uri.Builder builder = Uri.parse("dianping://addshopphoto").buildUpon();
        builder.appendQueryParameter("shopid", shopId);
        builder.appendQueryParameter("smartMark", smartMark);
//            builder.appendQueryParameter("shopname", shop.getString("Name"));
//            builder.appendQueryParameter("maxnum", String.valueOf(ConfigHelper.ugcPhotoUploadLimit));
//            builder.appendQueryParameter("checkdraft", String.valueOf(true));
//            DPObject[] categoryObj = shop.getArray("ShopPhotoCategory");
//            StringBuilder category = new StringBuilder();
//            if (categoryObj != null) {
//                for (DPObject co : categoryObj) {
//                    String cateName = co.getString("Name");
//                    category.append(cateName).append(",");
//                }
//                if (category.length() > 0) {
//                    builder.appendQueryParameter("category", category.deleteCharAt(category.length() - 1).toString());
//                }
//            }
//            builder.appendQueryParameter("shopphoto", shop.getString("DefaultPic"));
//            String abtest = shop.getString("Abtest");
//            try {
//                /**
//                 * abtest  json结构的 key value：
//                 add_photo_type:   normal/ai     #传图
//                 friend_like_type:    ad/platform      #小伙伴还喜欢
//                 */
//                if (!TextUtils.isEmpty(abtest)) {
//                    JSONObject abtestJson = new JSONObject(abtest);
//                    String addPhotoType = abtestJson.optString("add_photo_type");
//                    if ("ai".equals(addPhotoType)) {
//                        builder.appendQueryParameter("smartMark", "true");
//                    }
//                    builder.appendQueryParameter("smartMark", "true");
//
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
        //}
        Intent intent = new Intent(Intent.ACTION_VIEW, builder.build());
        context.startActivity(intent);
    }
}
