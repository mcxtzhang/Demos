package mcxtzhang.zandemo;

import android.content.Context;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by zhangxutong .
 * Date: 16/08/28
 */

public class ZanAdapter extends RecyclerView.Adapter<ZanAdapter.ViewHolder> {
    private Context mContext;
    private List<ZanBean> mDatas;
    private LayoutInflater mInflater;
    private ZanCallBack mZanCallBack;//点赞的回调

    public ZanAdapter(Context mContext, List<ZanBean> mDatas) {
        this.mContext = mContext;
        this.mDatas = mDatas;
        mInflater = LayoutInflater.from(mContext);
        if (mContext instanceof ZanCallBack) {
            mZanCallBack = (ZanCallBack) mContext;
        }
    }

    @Override
    public ZanAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(mInflater.inflate(R.layout.item_zan, parent, false));
    }

    @Override
    public void onBindViewHolder(final ZanAdapter.ViewHolder holder, final int position) {
        final ZanBean zanBean = mDatas.get(position);
        holder.tvZan.setText(zanBean.getZanCount() + "");
        holder.tvZan.setSelected(zanBean.isZaned());
        holder.tvZan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //点击事件执行后，立刻改变UI，产生动画 ， 但是数据先不改变。
                // 用一个变量保存position，因为holder是复用的，等操作回调时，holder可能已经不再屏幕或者被复用了
                final int pos = holder.getLayoutPosition();
                //动画

                //UI改变
                holder.tvZan.setSelected(!zanBean.isZaned());

                //模拟点赞的网络操作,点赞 取消点赞 接口肯定是一个，只不过参数不同。
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (null != mZanCallBack) {
                            double random = Math.random();
                            if (random > 0.5) {//成功
                                Toast.makeText(mContext, (zanBean.isZaned() ? "取消点赞" : "点赞") + "操作成功，回调:" + pos, Toast.LENGTH_SHORT).show();
                                mZanCallBack.onSuccess(pos);
                            } else {//失败
                                Toast.makeText(mContext, (zanBean.isZaned() ? "取消点赞" : "点赞") + "操作失败，回调:" + pos, Toast.LENGTH_SHORT).show();
                                mZanCallBack.onError(pos);
                            }
                        }
                    }
                }, 2000);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDatas != null ? mDatas.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvZan;

        public ViewHolder(View itemView) {
            super(itemView);
            tvZan = (TextView) itemView.findViewById(R.id.tvZan);
        }
    }
}
