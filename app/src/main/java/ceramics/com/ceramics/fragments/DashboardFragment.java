package ceramics.com.ceramics.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import ceramics.com.ceramics.R;
import ceramics.com.ceramics.activity.BaseActivity;
import ceramics.com.ceramics.adapter.ImageListAdapter;

/**
 * Created by vikrantg on 16-03-2017.
 */

public class DashboardFragment extends BaseFragment implements View.OnClickListener {

    private ImageListAdapter imageListAdapter;
    private ListView lvImages;
    private BaseActivity activity;
    private ArrayList<String> imageList;
    private Fragment wallFragment,floorFragment;
    private TextView tvWall,tvFloor;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dashboard,null);
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
        activity.setActionBarTitle("Ceramics");
        activity.showBackOption(false);
    }

    private void initView(View view){
        lvImages = (ListView)view.findViewById(R.id.image_list);
        tvWall = (TextView)view.findViewById(R.id.text_wall);
        tvFloor = (TextView)view.findViewById(R.id.floor_wall);

        tvWall.setOnClickListener(this);
        tvFloor.setOnClickListener(this);

        addItems();
        imageListAdapter = new ImageListAdapter(activity,imageList);
        lvImages.setAdapter(imageListAdapter);
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

    private void addItems(){
        imageList = new ArrayList<>();
        imageList.add("kitchen.jpg");
        imageList.add("Drawing.jpg");
        imageList.add("Bathroom.jpeg");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.text_wall:
                openWallFragment();
                break;
            case R.id.floor_wall:
                openFloorFragment();
                break;
        }
    }
}
