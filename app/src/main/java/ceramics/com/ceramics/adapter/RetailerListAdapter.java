package ceramics.com.ceramics.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
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
import ceramics.com.ceramics.model.RetailersDetails;

/**
 * Created by vikrantg on 16-11-2017.
 */

public class RetailerListAdapter extends ArrayAdapter<RetailersDetails> {

    private BaseActivity activity;
    private ArrayList<RetailersDetails> retailerList;
    private LayoutInflater inflater;

    public RetailerListAdapter(@NonNull BaseActivity activity, ArrayList<RetailersDetails> retailerList) {
        super(activity, R.layout.row_retailer_list,retailerList);
        this.activity = activity;
        this.retailerList = retailerList;
        inflater = LayoutInflater.from(activity);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        ViewHolder holder = null;
        if (convertView == null){
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.row_retailer_list,null);
            holder.name = (TextView) convertView.findViewById(R.id.retailer_name);
            holder.address = (TextView) convertView.findViewById(R.id.address);
            holder.phone = (TextView) convertView.findViewById(R.id.phone);
            holder.email = (TextView) convertView.findViewById(R.id.email);
            holder.website = (TextView) convertView.findViewById(R.id.website);
            holder.working = (TextView) convertView.findViewById(R.id.working_hrs);
            holder.closed = (TextView) convertView.findViewById(R.id.closed_on);
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.name.setText(retailerList.get(position).getRetailerName());
        holder.address.setText(retailerList.get(position).getAddress01()+" "+retailerList.get(position).getAddress02()+
                " "+retailerList.get(position).getAddress03());
        holder.phone.setText(retailerList.get(position).getMobile1()+" "+retailerList.get(position).getMobile2()+" "+
                retailerList.get(position).getLandLine1()+" "+retailerList.get(position).getLandLine2());
        holder.email.setText(retailerList.get(position).getEmail());
        holder.website.setText(retailerList.get(position).getWebsite());
        holder.working.setText(retailerList.get(position).getTimeOpen()+"-"+retailerList.get(position).getTimeClose());
        holder.closed.setText(retailerList.get(position).getDayClose());
        return convertView;
    }

    class ViewHolder{
        TextView name,address,phone,email,website,working,closed;
    }
}
