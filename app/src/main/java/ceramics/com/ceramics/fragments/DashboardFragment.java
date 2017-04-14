package ceramics.com.ceramics.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;

import java.util.ArrayList;

import ceramics.com.ceramics.R;
import ceramics.com.ceramics.activity.BaseActivity;
import ceramics.com.ceramics.adapter.ImageListAdapter;
import ceramics.com.ceramics.utils.ApplicationPreferenceData;

/**
 * Created by vikrantg on 16-03-2017.
 */

public class DashboardFragment extends BaseFragment implements View.OnClickListener {

    private ImageListAdapter imageListAdapter;
    private LayoutInflater inflater;
    private SliderLayout sliderLayout;
    private ListView lvImages;
    private BaseActivity activity;
    private ArrayList<String> imageList;
    private ArrayList<String> productImageList;
    private Fragment wallFragment,floorFragment,productByApplication;
    private TextView tvWall,tvFloor;
    private String imgageBaseURL = "http://images.ceramicskart.com/img/home/";
    private String prodctImgageBaseURL = "http://images.ceramicskart.com/application/";

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
        activity.setActionBarTitle(getString(R.string.app_name));
        activity.showBackOption(false);
    }

    private void initView(View view){
        inflater = LayoutInflater.from(getActivity());
        lvImages = (ListView)view.findViewById(R.id.image_list);
        tvWall = (TextView)view.findViewById(R.id.text_wall);
        tvFloor = (TextView)view.findViewById(R.id.floor_wall);
        ViewGroup viewGroup = (ViewGroup)inflater.inflate(R.layout.image_header_view,null);
        lvImages.addHeaderView(viewGroup);
        sliderLayout = (SliderLayout)viewGroup.findViewById(R.id.slider);
        tvWall.setOnClickListener(this);
        tvFloor.setOnClickListener(this);

        addItems();
        imageListAdapter = new ImageListAdapter(activity,productImageList);
        lvImages.setAdapter(imageListAdapter);
        initSlider();
        showPromotionCode();
    }

    private void initSlider(){
        sliderLayout.setPresetTransformer(SliderLayout.Transformer.Accordion);
        sliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        sliderLayout.setCustomAnimation(new DescriptionAnimation());
        sliderLayout.setDuration(3000);
    }

    private void showPromotionCode(){
        ApplicationPreferenceData preferenceData = ApplicationPreferenceData.getInstance(activity);
        if (preferenceData.getApplicationData().isReferCodeShow()){
            openReferCodeFragment();
        }
    }

    public void openReferCodeFragment() {
        ReferenceCodeDialogFragment referCodefragment = new ReferenceCodeDialogFragment();
        referCodefragment.show(activity.getFragmentManager(), "");
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

    private void openProductByApplicationFragment(){
        if (productByApplication == null) {
            productByApplication = new ProductByApplicationListFragment();
        }
        activity.loadFragment(productByApplication, R.id.base_layout, true);
    }

    private void addItems(){
        imageList = new ArrayList<>();
        imageList.add("kitchen.jpg");
        imageList.add("Drawing.jpg");
        imageList.add("Bathroom.jpeg");
        addProductImages();

        for (int i = 0; i < imageList.size(); i++){
            TextSliderView textSliderView = new TextSliderView(activity);
            textSliderView
                    .image(imgageBaseURL+imageList.get(i))
                    .setScaleType(BaseSliderView.ScaleType.Fit);
            textSliderView.bundle(new Bundle());

            sliderLayout.addSlider(textSliderView);
        }
    }

    private void addProductImages(){
        productImageList = new ArrayList<>();
        productImageList.add(prodctImgageBaseURL+"bathroom.jpg");
        productImageList.add(prodctImgageBaseURL+"bedroom.jpg");
        productImageList.add(prodctImgageBaseURL+"living_room.jpg");
        productImageList.add(prodctImgageBaseURL+"kitchen.jpg");
        productImageList.add(prodctImgageBaseURL+"outdoor.jpg");
        productImageList.add(prodctImgageBaseURL+"office.jpg");
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
