package mcxtzhang.recyclerviewdemo.generalizerv.base.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewStub;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import mcxtzhang.recyclerviewdemo.R;

/**
 * 介绍：ListView/GridView/RecyclerView 分页加载时使用到的FooterView
 * 作者：xjzhao
 * 邮箱：mr.feeling.heart@gmail.com
 * 时间: 2016-03-16  下午6:12
 */
public class LoadingFooter extends RelativeLayout {

    protected State mState = State.Normal;
    private View mLoadingView;
    private View mNetworkErrorView;
    private View mTheEndView;
    private ProgressBar mLoadingProgress;
    //    private TextView mLoadingText;
    private OnLoadErrorClickListener mOnLoadErrorClickListener;
    private OnStateChangeListener onStateChangeListener;

    public LoadingFooter(Context context) {
        super(context);
        init(context);
    }

    public LoadingFooter(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public LoadingFooter(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public LoadingFooter(Context context, OnLoadErrorClickListener l) {
        super(context);
        this.mOnLoadErrorClickListener = l;
        init(context);
    }

    private boolean isAniming;

    public void onResumeLoading() {
        if (!isAniming && null != mLoadingProgress) {
            isAniming = true;
            mLoadingProgress.setVisibility(View.VISIBLE);
        }
    }

    public void onPauseLoading() {
        if (isAniming && null != mLoadingProgress) {
            isAniming = false;
            mLoadingProgress.setVisibility(View.GONE);
        }
    }

    public void init(Context context) {
        inflate(context, R.layout.app_loading_footer, this);
        setOnClickListener(null);
        setState(State.Normal, true);
    }


    public State getState() {
        return mState;
    }

    public void setOnStateChangeListener(OnStateChangeListener onStateChangeListener) {
        this.onStateChangeListener = onStateChangeListener;
    }

    public void setState(State status) {
        setState(status, true);
    }

    /**
     * 设置状态
     *
     * @param status
     * @param showView 是否展示当前View
     */
    public void setState(State status, boolean showView) {
        if (mState == status) {
            return;
        }
        mState = status;
        switch (status) {

            case Normal:
                setVisibility(GONE);
                setOnClickListener(null);
                onPauseLoading();
//                if (mLoadingView != null) {
//                    mLoadingView.setVisibility(GONE);
//                }
//
//                if (mTheEndView != null) {
//                    mTheEndView.setVisibility(GONE);
//                }
//
//                if (mNetworkErrorView != null) {
//                    mNetworkErrorView.setVisibility(GONE);
//                }
                break;
            case Loading:
                onResumeLoading();
                setVisibility(VISIBLE);
                setOnClickListener(null);
                if (mTheEndView != null) {
                    mTheEndView.setVisibility(GONE);
                }

                if (mNetworkErrorView != null) {
                    mNetworkErrorView.setVisibility(GONE);
                }

                if (mLoadingView == null) {
                    ViewStub viewStub = (ViewStub) findViewById(R.id.loading_viewstub);
                    mLoadingView = viewStub.inflate();

                    mLoadingProgress = (ProgressBar) mLoadingView.findViewById(R.id.progressBar);
//                    mLoadingText = (TextView) mLoadingView.findViewById(R.id.loading_text);
                } else {
                    mLoadingView.setVisibility(VISIBLE);
                }

                mLoadingView.setVisibility(showView ? VISIBLE : GONE);

                mLoadingProgress.setVisibility(View.VISIBLE);
//                mLoadingText.setText(R.string.list_footer_loading);
                onResumeLoading();
                setOnClickListener(null);
                break;
            case End:
                onPauseLoading();
                setVisibility(VISIBLE);
                setOnClickListener(null);
                if (mLoadingView != null) {
                    mLoadingView.setVisibility(GONE);
                }

                if (mNetworkErrorView != null) {
                    mNetworkErrorView.setVisibility(GONE);
                }

                if (mTheEndView == null) {
                    ViewStub viewStub = (ViewStub) findViewById(R.id.end_viewstub);
                    mTheEndView = viewStub.inflate();
                } else {
                    mTheEndView.setVisibility(VISIBLE);
                }

                mTheEndView.setVisibility(showView ? VISIBLE : GONE);
                setOnClickListener(null);
                break;
            case Error:
                onPauseLoading();
                setVisibility(VISIBLE);
                if (mLoadingView != null) {
                    mLoadingView.setVisibility(GONE);
                }

                if (mTheEndView != null) {
                    mTheEndView.setVisibility(GONE);
                }

                if (mNetworkErrorView == null) {
                    ViewStub viewStub = (ViewStub) findViewById(R.id.network_error_viewstub);
                    mNetworkErrorView = viewStub.inflate();
                } else {
                    mNetworkErrorView.setVisibility(VISIBLE);
                }

                mNetworkErrorView.setVisibility(showView ? VISIBLE : GONE);
                setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (null != mOnLoadErrorClickListener) {
                            mOnLoadErrorClickListener.onClick();
                        }
                    }
                });
                break;
            default:
                break;
        }

    }

    public boolean isNeewVisible() {
        switch (mState) {
            case Normal:
                return false;
            case Loading:
            case End:
            case Error:
                return true;
            default:
                return true;
        }
    }


    public interface OnLoadErrorClickListener {
        void onClick();
    }

    public enum State {
        Normal/**正常*/
        , End /**加载到最底了*/
        , Loading/**加载中..*/
        , Error /**加载失败，点击重试*/
    }


    public interface OnStateChangeListener {
        void onVisible(boolean visible);
    }


}
