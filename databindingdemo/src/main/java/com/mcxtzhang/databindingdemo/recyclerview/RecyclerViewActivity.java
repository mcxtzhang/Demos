package com.mcxtzhang.databindingdemo.recyclerview;

import android.app.Activity;
import android.graphics.PointF;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.view.View;

import com.mcxtzhang.databindingdemo.databinding.ActivityRecyclerViewBinding;
import com.mcxtzhang.databindingdemo.recyclerview.m.FirstBindingBean;
import com.mcxtzhang.databindingdemo.recyclerview.multype.MBean1;
import com.mcxtzhang.databindingdemo.recyclerview.multype.MBean2;
import com.mcxtzhang.zxtcommonlib.databinding.base.mul.BaseMulTypeBindingAdapter;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewActivity extends Activity {
    private ActivityRecyclerViewBinding mBinding;
    //private OldBaseBindingAdapter mAdapter;
    private List<FirstBindingBean> mLists;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityRecyclerViewBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());

        initDatas();
        mBinding.rv.setLayoutManager(new LinearLayoutManager(this));
       /* mBinding.rv.setAdapter(mAdapter = new OldBaseBindingAdapter<ItemFirstRvBinding, FirstBindingBean>(this, R.layout.item_first_rv, mLists) {
            @Override
            public void onBindViewHolder(BaseBindingVH<ItemFirstRvBinding> holder, int position, ItemFirstRvBinding itemFirstRvBinding, final FirstBindingBean firstBindingBean) {
                itemFirstRvBinding.setBean(firstBindingBean);
                //普通的加载方法 但是当数据改变时 它不会立刻改变
                Glide.with(RecyclerViewActivity.this).load(firstBindingBean.getUrl()).into(itemFirstRvBinding.normalLoadIv);
                itemFirstRvBinding.getRoot().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(RecyclerViewActivity.this, "改变name", Toast.LENGTH_SHORT).show();
                        firstBindingBean.setName(firstBindingBean.getName() + "改变");
                        firstBindingBean.setUrl("https://ss0.bdstatic.com/5aV1bjqh_Q23odCf/static/superman/img/logo/bd_logo1_31bdc765.png");
                    }
                });
                firstBindingBean.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
                    @Override
                    public void onPropertyChanged(Observable sender, int propertyId) {
                        switch (propertyId) {
                            case BR.name:
                                Toast.makeText(RecyclerViewActivity.this, "name propertyId:" + propertyId, Toast.LENGTH_SHORT).show();
                                break;
                            case BR.url:
                                break;

                        }
                    }
                });

                itemFirstRvBinding.addOnRebindCallback(new OnRebindCallback() {
                    @Override
                    public boolean onPreBind(ViewDataBinding binding) {
                        ViewGroup sceneRoot = (ViewGroup) binding.getRoot();
                        TransitionManager.beginDelayedTransition(sceneRoot);
                        return true;
                    }
                });
            }
        });*/

        //就一种Item：新写法 代码更少了，但是总觉得有种约束感 ：
/*        mBinding.rv.setAdapter(new BaseBindingAdapter<FirstBindingBean, ItemMulType1Binding>(this, R.layout.item_mul_type_1, mLists) {
            @Override
            public void onBindViewHolder(final BaseBindingVH holder, final int position) {
                super.onBindViewHolder(holder, position);
                holder.getBinding().getRoot().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mLists.get(position).setName("变变变");
                        ViewDataBinding binding = holder.getBinding();
                    }
                });
            }
        });*/

        //2016 10 30 封装两个泛型
/*        mBinding.rv.setAdapter(new BaseBindingAdapter<FirstBindingBean, ItemMulType1Binding>(this, R.layout.item_mul_type_1, mLists) {
            @Override
            public void onBindViewHolder(BaseBindingVH<ItemMulType1Binding> holder, int position) {
                super.onBindViewHolder(holder, position);
                final ItemMulType1Binding binding = holder.getBinding();
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        binding.tv.setText("只是试一试 不要这么写");
                    }
                });
            }
        });*/

        //普通多种item(未封装)
        //mBinding.rv.setAdapter( new MulTypeAdapter(this, mLists));

        //Base多种Item
/*        mBinding.rv.setAdapter(new BaseMulTypeBindingAdapter(this, mLists) {
            @Override
            public void onBindViewHolder(final BaseBindingVH holder, final int position) {
                super.onBindViewHolder(holder, position);
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        switch (getItemViewType(position)) {
                            case R.layout.item_mul_type_1:
                                ItemMulType1Binding binding = (ItemMulType1Binding) holder.getBinding();
                                binding.tv.setText(binding.tv.getText() + "追加");
                                break;
                        }
                    }
                });

            }
        });*/


        //Base 多种Item，连bean都可以不一样，你觉得屌不屌
        mBinding.rv.setAdapter(new BaseMulTypeBindingAdapter(this, initMulTypeDatas()));

        //google也太抠门了 ，  方法都写成private protected 让我没办法用。QAQ 只能自己参照源码写
        new SnapHelper() {
            private static final float INVALID_DISTANCE = 1f;

            // Orientation helpers are lazily created per LayoutManager.
            @Nullable
            private OrientationHelper mVerticalHelper;
            @Nullable
            private OrientationHelper mHorizontalHelper;


            //最近的View距离目标的距离
            @Nullable
            @Override
            public int[] calculateDistanceToFinalSnap(@NonNull RecyclerView.LayoutManager layoutManager, @NonNull View targetView) {
                int[] out = new int[2];
                if (layoutManager.canScrollHorizontally()) {
                    out[0] = distanceToEnd(layoutManager, targetView,
                            getHorizontalHelper(layoutManager));
                } else {
                    out[0] = 0;
                }

                if (layoutManager.canScrollVertically()) {
                    out[1] = distanceToEnd(layoutManager, targetView,
                            getVerticalHelper(layoutManager));
                } else {
                    out[1] = 0;
                }
                return out;
            }

            private int distanceToEnd(RecyclerView.LayoutManager layoutManager,
                                      View targetView, OrientationHelper helper) {
                final int end;
                if (layoutManager.getClipToPadding()) {
                    end = helper.getStartAfterPadding() + helper.getTotalSpace();
                } else {
                    end = helper.getEnd();
                }
                return helper.getDecoratedEnd(targetView) - end;
            }

            //获取离我们目标最近的View
            @Nullable
            @Override
            public View findSnapView(RecyclerView.LayoutManager layoutManager) {
                if (layoutManager.canScrollVertically()) {
                     return findEndView(layoutManager, getVerticalHelper(layoutManager));
                } else if (layoutManager.canScrollHorizontally()) {
                    return findEndView(layoutManager, getHorizontalHelper(layoutManager));
                }
                return null;
            }

            private View findEndView(RecyclerView.LayoutManager layoutManager, OrientationHelper helper) {
                int childCount = layoutManager.getChildCount();
                if (childCount == 0) {
                    return null;
                }

                View endChild = null;
                final int end;
                if (layoutManager.getClipToPadding()) {
                    end = helper.getStartAfterPadding() + helper.getTotalSpace();
                } else {
                    end = helper.getEnd();
                }
                int absClosest = Integer.MAX_VALUE;

                //遍历取出底边离end最近的。
                for (int i = 0; i < childCount; i++) {
                    final View child = layoutManager.getChildAt(i);
                    int childEnd = helper.getDecoratedEnd(child);
                    int absDistance = Math.abs(childEnd - end);

                    // 谁的底边离end最近，就是移动谁
                    if (absDistance < absClosest) {
                        absClosest = absDistance;
                        endChild = child;
                    }
                }
                return endChild;
            }


            //要滑动到的目标position
            @Override
            public int findTargetSnapPosition(RecyclerView.LayoutManager layoutManager, int velocityX, int velocityY) {
                if (!(layoutManager instanceof RecyclerView.SmoothScroller.ScrollVectorProvider)) {
                    return RecyclerView.NO_POSITION;
                }

                final int itemCount = layoutManager.getItemCount();
                if (itemCount == 0) {
                    return RecyclerView.NO_POSITION;
                }

                final View currentView = findSnapView(layoutManager);
                if (currentView == null) {
                    return RecyclerView.NO_POSITION;
                }

                final int currentPosition = layoutManager.getPosition(currentView);
                if (currentPosition == RecyclerView.NO_POSITION) {
                    return RecyclerView.NO_POSITION;
                }

                RecyclerView.SmoothScroller.ScrollVectorProvider vectorProvider =
                        (RecyclerView.SmoothScroller.ScrollVectorProvider) layoutManager;
                // deltaJumps sign comes from the velocity which may not match the order of children in
                // the LayoutManager. To overcome this, we ask for a vector from the LayoutManager to
                // get the direction.
                PointF vectorForEnd = vectorProvider.computeScrollVectorForPosition(itemCount - 1);
                if (vectorForEnd == null) {
                    // cannot get a vector for the given position.
                    return RecyclerView.NO_POSITION;
                }

                int vDeltaJump, hDeltaJump;
                if (layoutManager.canScrollHorizontally()) {
                    hDeltaJump = estimateNextPositionDiffForFling(layoutManager,
                            getHorizontalHelper(layoutManager), velocityX, 0);
                    if (vectorForEnd.x < 0) {
                        hDeltaJump = -hDeltaJump;
                    }
                } else {
                    hDeltaJump = 0;
                }
                if (layoutManager.canScrollVertically()) {
                    vDeltaJump = estimateNextPositionDiffForFling(layoutManager,
                            getVerticalHelper(layoutManager), 0, velocityY);
                    if (vectorForEnd.y < 0) {
                        vDeltaJump = -vDeltaJump;
                    }
                } else {
                    vDeltaJump = 0;
                }

                int deltaJump = layoutManager.canScrollVertically() ? vDeltaJump : hDeltaJump;
                if (deltaJump == 0) {
                    return RecyclerView.NO_POSITION;
                }

                int targetPos = currentPosition + deltaJump;
                if (targetPos < 0) {
                    targetPos = 0;
                }
                if (targetPos >= itemCount) {
                    targetPos = itemCount - 1;
                }
                return targetPos;
            }

            private int estimateNextPositionDiffForFling(RecyclerView.LayoutManager layoutManager,
                                                         OrientationHelper helper, int velocityX, int velocityY) {
                int[] distances = calculateScrollDistance(velocityX, velocityY);
                float distancePerChild = computeDistancePerChild(layoutManager, helper);
                if (distancePerChild <= 0) {
                    return 0;
                }
                int distance =
                        Math.abs(distances[0]) > Math.abs(distances[1]) ? distances[0] : distances[1];
                return (int) Math.floor(distance / distancePerChild);
            }

            private float computeDistancePerChild(RecyclerView.LayoutManager layoutManager,
                                                  OrientationHelper helper) {
                View minPosView = null;
                View maxPosView = null;
                int minPos = Integer.MAX_VALUE;
                int maxPos = Integer.MIN_VALUE;
                int childCount = layoutManager.getChildCount();
                if (childCount == 0) {
                    return INVALID_DISTANCE;
                }

                for (int i = 0; i < childCount; i++) {
                    View child = layoutManager.getChildAt(i);
                    final int pos = layoutManager.getPosition(child);
                    if (pos == RecyclerView.NO_POSITION) {
                        continue;
                    }
                    if (pos < minPos) {
                        minPos = pos;
                        minPosView = child;
                    }
                    if (pos > maxPos) {
                        maxPos = pos;
                        maxPosView = child;
                    }
                }
                if (minPosView == null || maxPosView == null) {
                    return INVALID_DISTANCE;
                }
                int start = Math.min(helper.getDecoratedStart(minPosView),
                        helper.getDecoratedStart(maxPosView));
                int end = Math.max(helper.getDecoratedEnd(minPosView),
                        helper.getDecoratedEnd(maxPosView));
                int distance = end - start;
                if (distance == 0) {
                    return INVALID_DISTANCE;
                }
                return 1f * distance / ((maxPos - minPos) + 1);
            }


            @NonNull
            private OrientationHelper getVerticalHelper(@NonNull RecyclerView.LayoutManager layoutManager) {
                if (mVerticalHelper == null) {
                    mVerticalHelper = OrientationHelper.createVerticalHelper(layoutManager);
                }
                return mVerticalHelper;
            }

            @NonNull
            private OrientationHelper getHorizontalHelper(
                    @NonNull RecyclerView.LayoutManager layoutManager) {
                if (mHorizontalHelper == null) {
                    mHorizontalHelper = OrientationHelper.createHorizontalHelper(layoutManager);
                }
                return mHorizontalHelper;
            }


        }.attachToRecyclerView(mBinding.rv);

        mBinding.setPresenter(new FirstPresenter());
    }

    public List initMulTypeDatas() {
        List mulTypeDatas = new ArrayList<>();
        mulTypeDatas.add(new MBean1("http://imgs.ebrun.com/resources/2016_03/2016_03_25/201603259771458878793312_origin.jpg", "张"));


        mulTypeDatas.add(new MBean1("http://p14.go007.com/2014_11_02_05/a03541088cce31b8_1.jpg", "旭童"));
        mulTypeDatas.add(new MBean2("http://news.k618.cn/tech/201604/W020160407281077548026.jpg", "多种type"));
        mulTypeDatas.add(new MBean2("http://www.kejik.com/image/1460343965520.jpg", "多种type"));
        mulTypeDatas.add(new MBean2("http://cn.chinadaily.com.cn/img/attachement/jpg/site1/20160318/eca86bd77be61855f1b81c.jpg", "多种type"));
        mulTypeDatas.add(new MBean2("http://imgs.ebrun.com/resources/2016_04/2016_04_12/201604124411460430531500.jpg", "多种type"));
        mulTypeDatas.add(new MBean1("http://imgs.ebrun.com/resources/2016_04/2016_04_24/201604244971461460826484_origin.jpeg", "多种type"));
        mulTypeDatas.add(new MBean1("http://www.lnmoto.cn/bbs/data/attachment/forum/201408/12/074018gshshia3is1cw3sg.jpg", "多种type"));


        mulTypeDatas.add(new MBean1("http://p14.go007.com/2014_11_02_05/a03541088cce31b8_1.jpg", "旭童"));
        mulTypeDatas.add(new MBean2("http://news.k618.cn/tech/201604/W020160407281077548026.jpg", "多种type"));
        mulTypeDatas.add(new MBean2("http://www.kejik.com/image/1460343965520.jpg", "多种type"));
        mulTypeDatas.add(new MBean2("http://cn.chinadaily.com.cn/img/attachement/jpg/site1/20160318/eca86bd77be61855f1b81c.jpg", "多种type"));
        mulTypeDatas.add(new MBean2("http://imgs.ebrun.com/resources/2016_04/2016_04_12/201604124411460430531500.jpg", "多种type"));
        mulTypeDatas.add(new MBean1("http://imgs.ebrun.com/resources/2016_04/2016_04_24/201604244971461460826484_origin.jpeg", "多种type"));
        mulTypeDatas.add(new MBean1("http://www.lnmoto.cn/bbs/data/attachment/forum/201408/12/074018gshshia3is1cw3sg.jpg", "多种type"));


        mulTypeDatas.add(new MBean1("http://p14.go007.com/2014_11_02_05/a03541088cce31b8_1.jpg", "旭童"));
        mulTypeDatas.add(new MBean2("http://news.k618.cn/tech/201604/W020160407281077548026.jpg", "多种type"));
        mulTypeDatas.add(new MBean2("http://www.kejik.com/image/1460343965520.jpg", "多种type"));
        mulTypeDatas.add(new MBean2("http://cn.chinadaily.com.cn/img/attachement/jpg/site1/20160318/eca86bd77be61855f1b81c.jpg", "多种type"));
        mulTypeDatas.add(new MBean2("http://imgs.ebrun.com/resources/2016_04/2016_04_12/201604124411460430531500.jpg", "多种type"));
        mulTypeDatas.add(new MBean1("http://imgs.ebrun.com/resources/2016_04/2016_04_24/201604244971461460826484_origin.jpeg", "多种type"));
        mulTypeDatas.add(new MBean1("http://www.lnmoto.cn/bbs/data/attachment/forum/201408/12/074018gshshia3is1cw3sg.jpg", "多种type"));
        return mulTypeDatas;
    }


    private void initDatas() {
        mLists = new ArrayList<>();
        ArrayList<FirstBindingBean> nestBeen = new ArrayList<>();
        mLists.add(new FirstBindingBean("http://imgs.ebrun.com/resources/2016_03/2016_03_25/201603259771458878793312_origin.jpg", "张", 1));


        mLists.add(new FirstBindingBean("http://p14.go007.com/2014_11_02_05/a03541088cce31b8_1.jpg", "旭童", 2));
        mLists.add(new FirstBindingBean("http://news.k618.cn/tech/201604/W020160407281077548026.jpg", 3));
        mLists.add(new FirstBindingBean("http://www.kejik.com/image/1460343965520.jpg", 1));
        mLists.add(new FirstBindingBean("http://cn.chinadaily.com.cn/img/attachement/jpg/site1/20160318/eca86bd77be61855f1b81c.jpg", 2));
        mLists.add(new FirstBindingBean("http://imgs.ebrun.com/resources/2016_04/2016_04_12/201604124411460430531500.jpg", 3));
        mLists.add(new FirstBindingBean("http://imgs.ebrun.com/resources/2016_04/2016_04_24/201604244971461460826484_origin.jpeg", 1));
        mLists.add(new FirstBindingBean("http://www.lnmoto.cn/bbs/data/attachment/forum/201408/12/074018gshshia3is1cw3sg.jpg", 2));
    }

    public class FirstPresenter {
        public void onAddClick() {
            mLists.add(new FirstBindingBean("http://finance.gucheng.com/UploadFiles_7830/201603/2016032110220685.jpg", "add"));
            //mAdapter.notifyItemInserted(mLists.size());
        }

        public void onDelClick() {
            mLists.remove(mLists.size() - 1);
            //mAdapter.notifyItemRemoved(mLists.size());
        }
    }


}
