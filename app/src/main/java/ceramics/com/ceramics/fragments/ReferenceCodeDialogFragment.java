package ceramics.com.ceramics.fragments;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import ceramics.com.ceramics.R;
import ceramics.com.ceramics.activity.BaseActivity;
import ceramics.com.ceramics.model.ApplicationDataModel;
import ceramics.com.ceramics.utils.ApplicationPreferenceData;

/**
 * Created by vikrantg on 22-03-2017.
 */

public class ReferenceCodeDialogFragment extends DialogFragment implements View.OnClickListener{

    private Button btnCancel,btnSubmit;
    private EditText etReferCode;
    private BaseActivity activity;
    private ApplicationDataModel applicationDataModel;
    private ApplicationPreferenceData preferenceData;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        return inflater.inflate(R.layout.fragment_reference_code_diaglog,null);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        activity = (BaseActivity)getActivity();
        initView(getView());
    }

    private void initView(View view){
        btnCancel = (Button)view.findViewById(R.id.btn_cancel);
        btnSubmit = (Button)view.findViewById(R.id.btn_submit);
        etReferCode = (EditText)view.findViewById(R.id.edit_refer_code);
        preferenceData = ApplicationPreferenceData.getInstance(activity);
        applicationDataModel = preferenceData.getApplicationData();

        btnCancel.setOnClickListener(this);
        btnSubmit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_cancel:
                cancelDialog();
                break;
            case R.id.btn_submit:
                break;

        }
    }

    private void cancelDialog(){
        applicationDataModel.setReferCodeShow(false);
        preferenceData.setApplicationData(applicationDataModel);
        dismiss();
    }
}
