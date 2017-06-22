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

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.lang.reflect.Type;

import ceramics.com.ceramics.R;
import ceramics.com.ceramics.activity.BaseActivity;
import ceramics.com.ceramics.model.ApplicationDataModel;
import ceramics.com.ceramics.network.APIRequestHelper;
import ceramics.com.ceramics.network.CommonJsonObjectModel;
import ceramics.com.ceramics.utils.ApplicationPreferenceData;
import ceramics.com.ceramics.utils.Utils;

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
                Utils.hide_keyboard(activity);
                addPromocode();
                break;

        }
    }

    private void addPromocode(){
        if (validate()) {
            Type responseModelType = new TypeToken<CommonJsonObjectModel<String>>() {
            }.getType();
            AddPromoCode addPromoCode = new AddPromoCode();
            String promoCode = etReferCode.getText().toString();
            APIRequestHelper.addPromocode(promoCode, responseModelType, new JSONObject(), addPromoCode, addPromoCode, activity);
        }
    }

    private boolean validate(){
        if ("".equalsIgnoreCase(etReferCode.getText().toString())){
            return false;
        }

        return true;
    }

    private void cancelDialog(){
        applicationDataModel.setReferCodeShow(false);
        preferenceData.setApplicationData(applicationDataModel);
        dismiss();
    }

    class AddPromoCode implements Response.Listener<CommonJsonObjectModel<String>>,Response.ErrorListener{

        @Override
        public void onErrorResponse(VolleyError volleyError) {
            activity.showToast(getString(R.string.error));
        }

        @Override
        public void onResponse(CommonJsonObjectModel<String> response) {
            try{
                if (response.isStatus()){
                    activity.showToast(response.getData());
                    cancelDialog();
                }
                else {
                    activity.showToast(response.getData());
                }

            }
            catch (Exception e){
                activity.showToast(getString(R.string.error));
            }
        }
    }
}
