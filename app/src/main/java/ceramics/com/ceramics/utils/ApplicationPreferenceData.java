package ceramics.com.ceramics.utils;

import android.content.SharedPreferences;

import com.google.gson.Gson;

import ceramics.com.ceramics.activity.BaseActivity;
import ceramics.com.ceramics.model.ApplicationDataModel;

/**
 * Created by vikrantg on 24-03-2017.
 */

public class ApplicationPreferenceData {

    private SharedPreferences sharedPreferences;
    private final String PREF_NAME = "CeramicsKart";
    private final String APP_DATA = "App_Data";
    private static ApplicationPreferenceData applicationPreferenceData;

    private ApplicationPreferenceData(BaseActivity activity){
        if (sharedPreferences == null){
            sharedPreferences = activity.getSharedPreferences(PREF_NAME,activity.MODE_PRIVATE);
        }
    }

    public static ApplicationPreferenceData getInstance(BaseActivity activity){
        if (applicationPreferenceData == null){
            applicationPreferenceData = new ApplicationPreferenceData(activity);
        }
        return applicationPreferenceData;
    }

    public void setApplicationData(ApplicationDataModel applicationData){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(APP_DATA,new Gson().toJson(applicationData));
        editor.apply();
    }

    public ApplicationDataModel getApplicationData(){
        String data = sharedPreferences.getString(APP_DATA,"");
        if (Utils.isNotBlank(data)){
            return new Gson().fromJson(data,ApplicationDataModel.class);
        }
        else {
            return new ApplicationDataModel();
        }
    }
}
