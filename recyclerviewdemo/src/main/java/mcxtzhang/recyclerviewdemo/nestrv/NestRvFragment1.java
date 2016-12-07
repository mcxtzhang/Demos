package mcxtzhang.recyclerviewdemo.nestrv;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mcxtzhang.zxtcommonlib.databinding.base.BaseBindingAdapter;
import com.mcxtzhang.zxtcommonlib.databinding.base.BaseBindingVH;

import java.util.ArrayList;

import mcxtzhang.recyclerviewdemo.R;
import mcxtzhang.recyclerviewdemo.databinding.FragmentNestRv1Binding;
import mcxtzhang.recyclerviewdemo.databinding.ItemNestRv1Binding;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link NestRvFragment1.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link NestRvFragment1#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NestRvFragment1 extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private ArrayList<TestBean> mDatas;

    public NestRvFragment1() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NestRvFragment1.
     */
    // TODO: Rename and change types and number of parameters
    public static NestRvFragment1 newInstance(String param1, String param2) {
        NestRvFragment1 fragment = new NestRvFragment1();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        FragmentNestRv1Binding binding = FragmentNestRv1Binding.inflate(LayoutInflater.from(getActivity()));
        binding.rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        initDatas();
        binding.rv.setFocusable(false);
        BaseBindingAdapter<TestBean, ItemNestRv1Binding> baseBindingAdapter = new BaseBindingAdapter<TestBean, ItemNestRv1Binding>(getActivity(), R.layout.item_nest_rv_1, mDatas) {
            @Override
            public void onBindViewHolder(BaseBindingVH<ItemNestRv1Binding> holder, int position) {

                holder.getBinding().nestRv.setFocusable(false);
                //官方的bug 嵌套RecyclerView
                if (holder.getBinding().nestRv.getLayoutManager() == null) {
                    holder.getBinding().nestRv.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
                }
                if (holder.getBinding().nestRv.getAdapter() == null) {
                    BaseBindingAdapter baseBindingAdapter1 = new BaseBindingAdapter(getActivity(), R.layout.item_nest_rv_1, mDatas) {
                        @Override
                        public void onBindViewHolder(BaseBindingVH holder, int position) {
                            super.onBindViewHolder(holder, position);
                        }
                    };
                    baseBindingAdapter1.setHasStableIds(true);
                    holder.getBinding().nestRv.setAdapter(baseBindingAdapter1);
                }


                super.onBindViewHolder(holder, position);
            }
        };
        baseBindingAdapter.setHasStableIds(true);
        binding.rv.setAdapter(baseBindingAdapter);

        return binding.getRoot();
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }


    private void initDatas() {
        int i = 0;
        mDatas = new ArrayList<>();

        for (int j = 0; j < 1; j++) {
            mDatas.add(new TestBean((i++) + "", "http://inthecheesefactory.com/uploads/source/glidepicasso/cover.jpg"));
            mDatas.add(new TestBean((i++) + "", "http://fudaoquan.com/wp-content/uploads/2016/04/wanghong.jpg"));
            mDatas.add(new TestBean((i++) + "", "http://imgs.ebrun.com/resources/2016_03/2016_03_25/201603259771458878793312_origin.jpg"));
            mDatas.add(new TestBean((i++) + "", "http://p14.go007.com/2014_11_02_05/a03541088cce31b8_1.jpg"));
            mDatas.add(new TestBean((i++) + "", "http://news.k618.cn/tech/201604/W020160407281077548026.jpg"));
            mDatas.add(new TestBean((i++) + "", "http://www.kejik.com/image/1460343965520.jpg"));
            mDatas.add(new TestBean((i++) + "", "http://cn.chinadaily.com.cn/img/attachement/jpg/site1/20160318/eca86bd77be61855f1b81c.jpg"));
            mDatas.add(new TestBean((i++) + "", "http://imgs.ebrun.com/resources/2016_04/2016_04_12/201604124411460430531500.jpg"));
            mDatas.add(new TestBean((i++) + "", "http://imgs.ebrun.com/resources/2016_04/2016_04_24/201604244971461460826484_origin.jpeg"));
            mDatas.add(new TestBean((i++) + "", "http://www.lnmoto.cn/bbs/data/attachment/forum/201408/12/074018gshshia3is1cw3sg.jpg"));
            mDatas.add(new TestBean((i++) + "", "http://inthecheesefactory.com/uploads/source/glidepicasso/cover.jpg"));
            mDatas.add(new TestBean((i++) + "", "http://fudaoquan.com/wp-content/uploads/2016/04/wanghong.jpg"));
            mDatas.add(new TestBean((i++) + "", "http://imgs.ebrun.com/resources/2016_03/2016_03_25/201603259771458878793312_origin.jpg"));
            mDatas.add(new TestBean((i++) + "", "http://p14.go007.com/2014_11_02_05/a03541088cce31b8_1.jpg"));
            mDatas.add(new TestBean((i++) + "", "http://news.k618.cn/tech/201604/W020160407281077548026.jpg"));
            mDatas.add(new TestBean((i++) + "", "http://www.kejik.com/image/1460343965520.jpg"));
            mDatas.add(new TestBean((i++) + "", "http://cn.chinadaily.com.cn/img/attachement/jpg/site1/20160318/eca86bd77be61855f1b81c.jpg"));
            mDatas.add(new TestBean((i++) + "", "http://imgs.ebrun.com/resources/2016_04/2016_04_12/201604124411460430531500.jpg"));
            mDatas.add(new TestBean((i++) + "", "http://imgs.ebrun.com/resources/2016_04/2016_04_24/201604244971461460826484_origin.jpeg"));
            mDatas.add(new TestBean((i++) + "", "http://www.lnmoto.cn/bbs/data/attachment/forum/201408/12/074018gshshia3is1cw3sg.jpg"));
        }


    }
}
