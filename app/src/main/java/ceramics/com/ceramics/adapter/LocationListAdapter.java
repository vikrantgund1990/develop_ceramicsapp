package ceramics.com.ceramics.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import ceramics.com.ceramics.R;
import ceramics.com.ceramics.activity.BaseActivity;
import ceramics.com.ceramics.model.UserLocationData;

/**
 * Created by vikrantg on 24-03-2017.
 */

public class LocationListAdapter extends ArrayAdapter<UserLocationData> {

    private BaseActivity activity;
    private ArrayList<UserLocationData> locationList;
    private LayoutInflater inflater;

    public LocationListAdapter(BaseActivity activity, ArrayList<UserLocationData> locationList) {
        super(activity, R.layout.row_location_list,locationList);
        this.activity = activity;
        this.locationList = locationList;
        inflater = LayoutInflater.from(activity);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null){
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.row_location_list,null);
            holder.tvLocation = (TextView)convertView.findViewById(R.id.text_location);
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder)convertView.getTag();
        }

        holder.tvLocation.setText(locationList.get(position).getName());

        return convertView;
    }

    private class ViewHolder{
        TextView tvLocation;
    }
}
