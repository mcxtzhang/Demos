package com.mcxtzhang.spandemos;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.widget.EditText;
import android.widget.TextView;

import com.mcxtzhang.zxtcommonlib.ZxtUtils;

import static com.mcxtzhang.zxtcommonlib.ZxtUtils.getBfGoodsNameIsActSpannableString;

public class MainActivity extends AppCompatActivity {
    TextView mTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTv = (TextView) findViewById(R.id.tv);

        //ForegroundColorSpan span = new ForegroundColorSpan(Color.RED);
        String source = "13816320845";//源数据
        SpannableString spannableString = new SpannableString(source);//构建Span
        String key = "184";
        if (!TextUtils.isEmpty(key)) {
            for (int i = 0; i < key.length(); i++) {
                int indexB = source.indexOf(key.charAt(i));
                if (indexB > -1) {//匹配到了,加一个spans
                    spannableString.setSpan(/*span*/new ForegroundColorSpan(Color.RED), indexB, indexB + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                }
            }
        }

        mTv.setText(ZxtUtils.getFriendColorSpannableString(source, "adsfasdfadsf"));


        String source2 = "世纪20年代初，人类设计了由人工智能创造的智能机械（简称为智械），其旨在加强生产和创造世界经济繁荣。然而，令人难以置信的事情发生了，智械变得充满恶意，并开始大量生产军用机器人试图毁灭人类。\n" +
                "各国政府未能阻止智能机械的进攻，因此联合国创建了一个国际维和组织，以反抗智能机械。汇集全世界最优秀最精英的组织被命名为“守望先锋”，其将围绕机器人军队展开一场实力不对称的战争。\n" +
                "最初的守望先锋队伍有：来自德国的士兵莱因哈特·威尔海姆，瑞典武器工程师托比昂·林德霍姆，两名美国的强化试验士兵——军中至交好友——加布里埃尔·莱耶斯（死神）和杰克·莫里森（士兵76），廖，以及安娜（法老之鹰的母亲）。\n" +
                "莱耶斯被选为守望先锋的领导者，但其实这个领袖称号实际上有名无实，莫里森磨合了所有人使之成为一个强大战斗力团队。[4]\n" +
                "俄罗斯士兵与智能机械交战";
/*        SpannableString spannableString1 = new SpannableString(source2);
        spannableString1.setSpan(new BackgroundColorSpan(Color.parseColor("#ffdc5b")), 0, " 活动 ".length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);//设置背景色
        spannableString1.setSpan(new AbsoluteSizeSpan(12, true), 0, " 活动 ".length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);//改变部分文字大小*/

        /*spannableString1.setSpan(new BackgroundColorSpan(Color.parseColor("#ffcc4d")), 0,4, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString1.setSpan(new ForegroundColorSpan(Color.WHITE), 0, 4, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);  //设置前景色 就是文字颜色
        spannableString1.setSpan(new ForegroundColorSpan(Color.parseColor("#66aaaa")), 4, source2.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);  //设置前景色为洋红
*/
        mTv.setText(getBfGoodsNameIsActSpannableString(source2, true));

        EditText et = (EditText) findViewById(R.id.et);
        et.setHint(ZxtUtils.getTextWithPartSpecialColor(source2, 0,2,Color.BLUE));

    }


}
