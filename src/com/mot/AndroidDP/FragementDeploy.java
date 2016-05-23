package com.mot.AndroidDP;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bkmr38 on 5/16/2016.
 */
public class FragementDeploy extends Fragment {
    private Spinner dpSpinner;
    private Spinner profileSpinner;
    private Button deploy;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.deploy, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        dpSpinner=(Spinner)getView().findViewById(R.id.deploy_dpname);
        profileSpinner=(Spinner)getView().findViewById(R.id.deploy_profilename);
        deploy=(Button)getView().findViewById(R.id.deploy_deploybutton);

        getDPNames();
        getProfileNames();

        deploy.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                String dpName=dpSpinner.getSelectedItem().toString();
                String profileName=profileSpinner.getSelectedItem().toString();
                HttpUtility.SetDPConfig(dpName,profileName);
            }
        });
    }

    public void refreshData(){
        getDPNames();
        getProfileNames();
    }

    private void getDPNames(){
        ArrayList<CharSequence> data = new ArrayList<CharSequence>();

       List<String> webResult=HttpUtility.GetAllDPNames();
        for(String s : webResult){
            data.add(s);
        }
        ArrayAdapter<CharSequence> adapter=new ArrayAdapter<CharSequence>(getView().getContext(),android.R.layout.simple_spinner_item,data);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dpSpinner.setAdapter(adapter);
        dpSpinner.setSelection(0);
        dpSpinner.setVisibility(View.VISIBLE);
    }

    private void getProfileNames(){
        ArrayList<CharSequence> data = new ArrayList<CharSequence>();

        List<String> webResult=HttpUtility.GetAllSettings();
        for(String s : webResult){
            data.add(s);
        }
        ArrayAdapter<CharSequence> adapter=new ArrayAdapter<CharSequence>(getView().getContext(),android.R.layout.simple_spinner_item,data);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        profileSpinner.setAdapter(adapter);
        profileSpinner.setSelection(0);
        profileSpinner.setVisibility(View.VISIBLE);
    }
}
