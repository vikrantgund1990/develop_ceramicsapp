package ceramics.com.ceramics.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import ceramics.com.ceramics.R;
import ceramics.com.ceramics.activity.BaseActivity;

/**
 * Created by Prakash on 5/14/2017.
 */

public class ProductFragment extends BaseFragment implements View.OnClickListener{

    private Fragment wallFragment, floorFragment;
    private BaseActivity activity;
    private TextView tvWall,tvFloor;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_product,null);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        activity = (BaseActivity)getActivity();
        initView(getView());
    }

    @Override
    public void onResume() {
        super.onResume();
        activity.setActionBarTitle("Products");
        activity.showBackOption(true);
    }

    private void initView(View v){
        tvFloor = (TextView)v.findViewById(R.id.text_floor);
        tvWall = (TextView)v.findViewById(R.id.text_wall);

        tvFloor.setOnClickListener(this);
        tvWall.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.text_floor:
                openFloorFragment();
                break;
            case R.id.text_wall:
                openWallFragment();
                break;
        }
    }

    public void openWallFragment(){
        if (wallFragment == null) {
            wallFragment = new WallFragment();
        }
        activity.loadFragment(wallFragment, R.id.base_layout, true);
    }

    public void openFloorFragment(){
        if (floorFragment == null) {
            floorFragment = new FloorFragment();
        }
        activity.loadFragment(floorFragment, R.id.base_layout, true);
    }
}
