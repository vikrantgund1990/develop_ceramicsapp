package ceramics.com.ceramics.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import ceramics.com.ceramics.R;

/**
 * Created by vikrantg on 26-02-2017.
 */

public class CustomActionBar extends LinearLayout implements View.OnClickListener {

    private Context context;
    private LayoutInflater inflater;
    private ImageView ivActionBack,ivActionMenu;
    private TextView tvTitle;
    private ActionBarListener actionBarListener;

    public CustomActionBar(Context context) {
        super(context);
        this.context = context;
        inflater = LayoutInflater.from(context);
        initView();
    }

    public CustomActionBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        inflater = LayoutInflater.from(context);
        initView();
    }

    private void initView(){
        inflater.inflate(R.layout.layout_actionbar,this);
        ivActionBack = (ImageView) findViewById(R.id.image_back_action);
        tvTitle = (TextView)findViewById(R.id.text_action_title);
        ivActionMenu = (ImageView)findViewById(R.id.image_menu_action);
        ivActionBack.setOnClickListener(this);
        ivActionMenu.setOnClickListener(this);
    }

    public void setActionBarTitle(String title){
        tvTitle.setText(title);
    }

    public void setActionBarListner(ActionBarListener actionBarListener){
        this.actionBarListener = actionBarListener;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.image_back_action:
                onBackClick();
                break;
            case R.id.image_menu_action:
                onMenuClick();
                break;

        }
    }

    private void onBackClick(){
        if (actionBarListener != null){
            actionBarListener.onBackIconPressed();
        }
        else {
            Toast.makeText(context,"Implement action bar listener in activity",Toast.LENGTH_SHORT).show();
        }
    }

    private void onMenuClick(){
        if (actionBarListener != null){
            actionBarListener.onMenuIconPressed();
        }
        else {
            Toast.makeText(context,"Implement action bar listener in activity",Toast.LENGTH_SHORT).show();
        }
    }
}
