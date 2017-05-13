package com.example.realmdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.common.BusiType;
import com.common.RealmManagerFactory;
import com.common.ShopCartManager;
import com.common.ShopCartManagerFactory;
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
                .schemaVersion(1)
                .deleteRealmIfMigrationNeeded()
                .name("zxt.realm")
                .build());

        findViewById(R.id.btnInsert).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
       /*         XYBean xyBean = new XYBean();
                xyBean.setName("shop1");
                xyBean.setCount(Integer.parseInt(((EditText) findViewById(R.id.et)).getText().toString().trim()));
                xyBean.setDesc("i am desc");
                xyBean.setPrice("2.7");
                xyBean.setPrimaryKey("1");
                shopCartManager.update(xyBean);
*/
                ShopCartManager shopCartManager = new RealmManagerFactory().create();

                for (XYBean bean : XYBean.mockDatas()) {
                    shopCartManager.update(bean);
                }
            }
        });

        findViewById(R.id.btnQuery).setOnClickListener(new View.OnClickListener() {
            boolean first = true;

            @Override
            public void onClick(View view) {
                //购物车用的
                List<XYBean> select = new RealmManagerFactory().create().<XYBean>select(BusiType.TYPE_XIYOU_FOODS);
                if (null != select && !select.isEmpty()) {
                    StringBuilder sb = new StringBuilder();
                    for (XYBean bean : select) {
                        if (first) bean.setCount(10000);

                        sb.append(bean);
                    }
                    ((TextView) findViewById(R.id.tvRessult)).setText(sb.toString());
                } else {
                    ((TextView) findViewById(R.id.tvRessult)).setText("购物车没有数据");

                }
                first = false;
            }
        });

        ShopCartManagerFactory factory = new RealmManagerFactory();
        final ShopCartManager shopCartManager = factory.create();

        findViewById(R.id.btnDel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shopCartManager.delete(((EditText) findViewById(R.id.et)).getText().toString().trim());
            }
        });

        findViewById(R.id.btnDelAll).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shopCartManager.delete(shopCartManager.<XYBean>select(BusiType.TYPE_XIYOU_FOODS));
            }
        });
    }
}
