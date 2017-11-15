package ceramics.com.ceramics.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import ceramics.com.ceramics.R;
import ceramics.com.ceramics.activity.HomeActivity;

/**
 * Created by vikrantg on 11-03-2017.
 */

public class LeftSliderFragment extends BaseFragment implements View.OnClickListener{

    private TextView tvVisualiser,tvContactUs,tvAboutUs,tvProduct,tvFloor,tvHome,tvRefer;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_left_slider,null);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView(getView());
    }

    private void initView(View view){
        //tvHome = (TextView)view.findViewById(R.id.home);
        tvProduct = (TextView)view.findViewById(R.id.products);
        tvRefer = (TextView)view.findViewById(R.id.refer_user);
        tvVisualiser = (TextView)view.findViewById(R.id.tile_visualiser);
        tvContactUs = (TextView)view.findViewById(R.id.contact_us);
        tvAboutUs = (TextView)view.findViewById(R.id.about_us);

        tvVisualiser.setOnClickListener(this);
        tvContactUs.setOnClickListener(this);
        tvAboutUs.setOnClickListener(this);
        tvProduct.setOnClickListener(this);
        tvRefer.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.products:
                ((HomeActivity)getActivity()).openProductFragment();
                break;
            case R.id.tile_visualiser:
                ((HomeActivity)getActivity()).openTileVisualizer();
                break;
            case R.id.contact_us:
                ((HomeActivity)getActivity()).openWebFragment(getString(R.string.contact_us));
                break;
            case R.id.about_us:
                ((HomeActivity)getActivity()).openWebFragment(getString(R.string.about_us));
                break;
            case R.id.refer_user:
                ((HomeActivity)getActivity()).openReferCodeFragment();
                break;
        }
    }

}
