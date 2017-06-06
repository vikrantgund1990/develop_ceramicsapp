package ceramics.com.ceramics.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.util.ArrayList;

import ceramics.com.ceramics.R;
import ceramics.com.ceramics.activity.BaseActivity;
import ceramics.com.ceramics.utils.CeramicsApplication;

/**
 * Created by vikrantg on 16-03-2017.
 */

public class ImageListAdapter extends ArrayAdapter<String> {

    private BaseActivity activity;
    private ArrayList<String> imageList;
    private LayoutInflater inflater;
    //private String imgageBaseURL = "http://images.ceramicskart.com/img/home/";
    private ImageLoader imageLoader;

    public ImageListAdapter(BaseActivity context,ArrayList<String> imageList) {
        super(context, R.layout.row_image_lsit,imageList);
        this.activity = context;
        this.imageList = imageList;
        inflater = LayoutInflater.from(context);
        imageLoader = CeramicsApplication.getInstance().getImageLoader();
    }



    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null){
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.row_image_lsit,null);
            holder.ivImage = (NetworkImageView)convertView.findViewById(R.id.image_product);
            holder.tvName = (TextView)convertView.findViewById(R.id.text_name);
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder)convertView.getTag();
        }

        holder.ivImage.setImageUrl(imageList.get(position),imageLoader);
        holder.tvName.setText(getApplicationName(position));
        return convertView;
    }

    private String getApplicationName(int position){
        String name = "";
        switch (position){
            case 0: name = "Bedroom";
                    break;
            case 1: name = "Living Room";
                    break;
            case 2: name = "Kitchen";
                    break;
            case 3: name = "Bathroom";
                    break;
            case 4: name = "Office";
                    break;
            case 5: name = "Outdoor";
                    break;
        }

        return name;
    }

    class ViewHolder{
        private NetworkImageView ivImage;
        private TextView tvName;
    }
}
