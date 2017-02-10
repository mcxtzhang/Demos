package com.mcxtzhang.rxjava2demo.retrofit.removewrapper.bendan;

import com.mcxtzhang.rxjava2demo.retrofit.model.bf.BaseBean;
import com.mcxtzhang.rxjava2demo.retrofit.model.bf.base.ResultException;

import io.reactivex.functions.Function;

import static com.mcxtzhang.rxjava2demo.retrofit.base.NetworkConfig.SUCCESS_FLAG;

/**
 * Intro: 用来剥离外层固定包装的Function
 * Author: zhangxutong
 * E-mail: mcxtzhang@163.com
 * Home Page: http://blog.csdn.net/zxt0601
 * Created:   2017/2/10.
 * History:
 */

public class RemoveWrapper<T> implements Function<BaseBean<T>, T> {
    private String mCstErrorCode;

    public RemoveWrapper() {
    }

    public RemoveWrapper(String cstErrorCode) {
        mCstErrorCode = cstErrorCode;
    }

    @Override
    public T apply(BaseBean<T> tBaseBean) throws Exception {
        if (tBaseBean == null) {
            throw new ResultException(mCstErrorCode, "服务器未知错误");
        } else {
            if (!SUCCESS_FLAG.equals(tBaseBean.getFlag())) {
                throw new ResultException(tBaseBean.getFlag(), tBaseBean.getMessage());
            } else {
                return tBaseBean.getData();
            }
        }
    }
}
