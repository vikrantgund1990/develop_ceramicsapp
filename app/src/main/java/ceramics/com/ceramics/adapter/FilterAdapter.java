package ceramics.com.ceramics.adapter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import ceramics.com.ceramics.R;
import ceramics.com.ceramics.activity.BaseActivity;
import ceramics.com.ceramics.model.Filter;

/**
 * Created by vikrantg on 17-06-2017.
 */

public class FilterAdapter extends ArrayAdapter<Filter> {

    private BaseActivity activity;
    private ArrayList<Filter> filterList;
    private LayoutInflater inflater;

    public FilterAdapter(BaseActivity activity,ArrayList<Filter> filterList){
        super(activity, R.layout.row_location_list,filterList);
        this.activity = activity;
        this.filterList = filterList;
        inflater = LayoutInflater.from(activity);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null){
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.row_location_list,null);
            holder.tvName = (TextView) convertView.findViewById(R.id.text_location);
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder)convertView.getTag();
        }

        holder.tvName.setText(filterList.get(position).getFilterName());

        return convertView;
    }

    private class ViewHolder {
        TextView tvName;
    }
}
