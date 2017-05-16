package com.example.realmdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.common.BusiType;
import com.common.RealmManagerFactory;
import com.common.ShopCartManager;
import com.mcxtzhang.realmdemo.R;
import com.shopcart.XYBean;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Realm.init(this);
        Realm.getInstance(new RealmConfiguration.Builder()
                .schemaVersion(3)
                .deleteRealmIfMigrationNeeded()
                .name("zxt.realm")
                .build());

        findViewById(R.id.btnInsert).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                XYBean xyBean = new XYBean();
                xyBean.setName("shop1");
                xyBean.setCount(Integer.parseInt(((EditText) findViewById(R.id.et)).getText().toString().trim()));
                xyBean.setDesc("i am desc");
                xyBean.setPrice("2.7");
                xyBean.setPrimaryKey("1");
                ShopCartManager shopCartManager = new RealmManagerFactory().create();
                shopCartManager.update(xyBean);

                for (XYBean bean : xyBean.mockDatas()) {
                    shopCartManager.update(bean);
                }
            }
        });

        findViewById(R.id.btnQuery).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //购物车用的
                List<XYBean> select = new RealmManagerFactory().create().<XYBean>select(BusiType.TYPE_XIYOU_FOODS);
                if (null != select && !select.isEmpty()) {
                    ((TextView) findViewById(R.id.tvRessult)).setText(select.get(0).toString());
                }
            }
        });

        findViewById(R.id.btnDel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShopCartManager shopCartManager = new RealmManagerFactory().create();
                XYBean xyBean = new XYBean();
                xyBean.setName("shop1");
                xyBean.setCount(Integer.parseInt(((EditText) findViewById(R.id.et)).getText().toString().trim()));
                xyBean.setDesc("i am desc");
                xyBean.setPrice("2.7");
                xyBean.setPrimaryKey("1");
                shopCartManager.delete(xyBean);
            }
        });


    }
}
