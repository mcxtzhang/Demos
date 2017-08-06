package com.mcxtzhang.zxtcommonlib.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import com.mcxtzhang.zxtcommonlib.R;


/**
 * Intro:
 * Author: zhangxutong
 * E-mail: mcxtzhang@163.com
 * Home Page: http://blog.csdn.net/zxt0601
 * Created:   2017/8/6.
 * History:
 */

public class DialogManager {

    public static Dialog showCustom(Context context, View contentView, String leftText) {
        return showCustom(context, contentView, leftText, null);
    }

    public static Dialog showCustom(Context context, View contentView, String leftText, View.OnClickListener leftListener) {
        return showCustom(context, contentView, leftText, leftListener, null, null);
    }

    public static Dialog showCustom(Context context, View contentView, String leftText, View.OnClickListener leftListener, String rightText, View.OnClickListener rightListener) {
        return showCustom(context, contentView, leftText, leftListener, rightText, rightListener, true);
    }

    public static Dialog showCustom(Context context, View contentView, String leftText, View.OnClickListener leftListener, String rightText, View.OnClickListener rightListener, boolean canceledOnTouchOutside) {
        BaseDialog dialog = new BaseDialog.Builder(context).setCustomView(contentView)
                .setLeftText(leftText)
                .setLeftClickListener(leftListener)
                .setRightText(rightText)
                .setRightClickListener(rightListener)
                .setCanceledOnTouchOutside(canceledOnTouchOutside)
                .build();
        dialog.show();
        return dialog;
    }


    private static class BaseDialog extends Dialog {
        ViewGroup mRoot;
        TextView mLeftButton, mRightButton;
        //View mCustomView;

        public BaseDialog(@NonNull Context context) {
            super(context);
            init(context);
        }

        void init(Context context) {
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            getWindow().setGravity(Gravity.CENTER);
            //setCanceledOnTouchOutside(true);
            getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            getWindow().setWindowAnimations(R.style.baseDialogAnimation);
            setContentView(R.layout.base_dialog);
            mRoot = (ViewGroup) findViewById(R.id.dialogRoot);
            mLeftButton = (TextView) findViewById(R.id.leftButton);
            mRightButton = (TextView) findViewById(R.id.rightButton);
        }

        public BaseDialog setLeft(String text, final View.OnClickListener listener) {
            mLeftButton.setText(text);
            mLeftButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                    if (null != listener) {
                        listener.onClick(v);
                    }
                }
            });
            return this;
        }

        //只有右边设置了才显示分割线
        public BaseDialog setRight(String text, final View.OnClickListener listener) {
            mRightButton.setText(text);
            mRightButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                    if (null != listener) {
                        listener.onClick(v);
                    }
                }
            });
            findViewById(R.id.verticalLine).setVisibility(View.VISIBLE);
            mRightButton.setVisibility(View.VISIBLE);
            return this;
        }

        public BaseDialog setCustomView(View view) {
            mRoot.addView(view, 0);
            return this;
        }


        public static class Builder {
            private Context context;
            private View customView;
            private String leftText;
            private View.OnClickListener leftClickListener;
            private String rightText;
            private View.OnClickListener rightClickListener;
            private boolean canceledOnTouchOutside = true;

            public Builder(Context context) {
                this.context = context;
            }

            public boolean isCanceledOnTouchOutside() {
                return canceledOnTouchOutside;
            }

            public Builder setCanceledOnTouchOutside(boolean canceledOnTouchOutside) {
                this.canceledOnTouchOutside = canceledOnTouchOutside;
                return this;
            }

            public View getCustomView() {
                return customView;
            }

            public Builder setCustomView(View customView) {
                this.customView = customView;
                return this;
            }

            public String getLeftText() {
                return leftText;
            }

            public Builder setLeftText(String leftText) {
                this.leftText = leftText;
                return this;
            }

            public View.OnClickListener getLeftClickListener() {
                return leftClickListener;
            }

            public Builder setLeftClickListener(View.OnClickListener leftClickListener) {
                this.leftClickListener = leftClickListener;
                return this;
            }

            public String getRightText() {
                return rightText;
            }

            public Builder setRightText(String rightText) {
                this.rightText = rightText;
                return this;
            }

            public View.OnClickListener getRightClickListener() {
                return rightClickListener;
            }

            public Builder setRightClickListener(View.OnClickListener rightClickListener) {
                this.rightClickListener = rightClickListener;
                return this;
            }

            public BaseDialog build() {
                if (customView == null) {
                    throw new RuntimeException("必须设置Dialog的内容");
                }
                if (leftText == null) {
                    throw new RuntimeException("必须设置左按钮的文案和点击事件");
                }
                BaseDialog baseDialog = new BaseDialog(context);
                baseDialog.setCustomView(customView)
                        .setLeft(leftText, leftClickListener);
                if (rightText != null || rightClickListener != null) {
                    baseDialog.setRight(rightText, rightClickListener);
                }
                baseDialog.setCanceledOnTouchOutside(canceledOnTouchOutside);
                return baseDialog;
            }
        }


    }
}
