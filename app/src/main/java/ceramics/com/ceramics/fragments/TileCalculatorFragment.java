package ceramics.com.ceramics.fragments;

import android.app.Dialog;
import android.app.Fragment;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import ceramics.com.ceramics.R;

/**
 * Created by vikrantg on 31-05-2017.
 */

public class TileCalculatorFragment extends DialogFragment implements View.OnClickListener{

    private double coverage;
    private EditText etLength,etBreadth;
    private TextView tvResult;
    private Button btnCalc,btnReset;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tile_calculator,null);
    }

    @Override
    public Dialog onCreateDialog(final Bundle savedInstanceState) {
        RelativeLayout root = new RelativeLayout(getActivity());
        root.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.fragment_tile_calculator);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        return dialog;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        coverage = getArguments().getDouble("Coverage");
        initView(getView());
    }

    private void initView(View view){
        etBreadth = (EditText)view.findViewById(R.id.breadth);
        etLength = (EditText)view.findViewById(R.id.length);
        tvResult = (TextView)view.findViewById(R.id.result);
        btnCalc = (Button)view.findViewById(R.id.calculate);
        btnReset = (Button)view.findViewById(R.id.reset);

        btnCalc.setOnClickListener(this);
        btnReset.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.calculate:
                calculate();
                break;
            case R.id.reset:
                etLength.setText("");
                etBreadth.setText("");
                tvResult.setText("");
                break;
        }
    }

    private void calculate(){
        double len = Double.parseDouble(etLength.getText().toString());
        double brdth = Double.parseDouble(etBreadth.getText().toString());
        double noOfBox = (len * brdth) / coverage;
        tvResult.setText((int) noOfBox+"");
    }
}
