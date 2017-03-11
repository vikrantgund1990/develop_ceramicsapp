package ceramics.com.ceramics.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.util.ArrayList;

import ceramics.com.ceramics.R;
import ceramics.com.ceramics.activity.BaseActivity;
import ceramics.com.ceramics.model.ProductDetails;
import ceramics.com.ceramics.utils.CeramicsApplication;

/**
 * Created by vikrantg on 11-03-2017.
 */

public class ProductListGridAdapater extends ArrayAdapter<ProductDetails> {

    private BaseActivity activity;
    private LayoutInflater inflater;
    private ArrayList<ProductDetails> list;
    private String imgageBaseURL = "http://images.ceramicskart.com/img/";
    private ImageLoader imageLoader;

    public ProductListGridAdapater(BaseActivity context, ArrayList<ProductDetails> list) {
        super(context, R.layout.row_product_grid_list,list);
        activity = context;
        this.list = list;
        inflater = LayoutInflater.from(context);
        imageLoader = CeramicsApplication.getInstance().getImageLoader();
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null){
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.row_product_grid_list,null);
            holder.tvProductName = (TextView)convertView.findViewById(R.id.tvProductName);
            holder.ivProductImage = (NetworkImageView)convertView.findViewById(R.id.imgProductImage);
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder)convertView.getTag();
        }
        holder.ivProductImage.setDefaultImageResId(R.mipmap.merchandise_stub_image);
        holder.tvProductName.setText(list.get(position).getManufacturerProductId()+"");
        holder.ivProductImage.setImageUrl(imgageBaseURL+list.get(position).getManufacturerProductId()+".jpg",imageLoader);

        return convertView;
    }

    class ViewHolder{
        TextView tvProductName;
        NetworkImageView ivProductImage;
    }
}
