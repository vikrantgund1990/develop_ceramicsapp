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

    private TextView tvWall,tvFloor,tvHome;

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
        tvHome = (TextView)view.findViewById(R.id.home);
        tvWall = (TextView)view.findViewById(R.id.wall);
        tvFloor = (TextView)view.findViewById(R.id.floor);
        tvWall.setOnClickListener(this);
        tvFloor.setOnClickListener(this);
        tvHome.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.wall:
                openWallFragment();
                break;
            case R.id.floor:
                openFloorFragment();
                break;
            case R.id.home:
                openHome();
                break;
        }
    }

    private void openWallFragment(){
        ((HomeActivity)getActivity()).openWallFragment();
    }
    private void openFloorFragment(){
        ((HomeActivity)getActivity()).openFloorFragment();
    }
    private void openHome(){
        ((HomeActivity)getActivity()).openProductTypeListFragment();
    }
}