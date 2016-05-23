package com.mot.AndroidDP;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import java.util.ArrayList;
import java.util.Set;

/**
 * Created by bkmr38 on 5/16/2016.
 */
public class FragementSetting extends Fragment {
    EditText addressGUI;
    EditText portGUI;
    Spinner authMethodGUI;
    CheckBox usbGUI;
    CheckBox lanGUI;
    CheckBox overTheAirGUI;
    CheckBox autoProcessGUI;
    EditText profileNameGUI;
    Button saveButton;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.setting, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);

        addressGUI=(EditText)getView().findViewById(R.id.setting_addressEdit);
        portGUI=(EditText)getView().findViewById(R.id.setting_portEdit);
        authMethodGUI=(Spinner)getView().findViewById(R.id.setting_authMethodSpinner);
        usbGUI=(CheckBox)getView().findViewById(R.id.setting_usbCheckbox);
        lanGUI=(CheckBox)getView().findViewById(R.id.setting_lanCheckbox);
        overTheAirGUI=(CheckBox)getView().findViewById(R.id.setting_overTheAirCheckbox);
        autoProcessGUI=(CheckBox)getView().findViewById(R.id.setting_autoProcessCheckbox);
        profileNameGUI=(EditText)getView().findViewById(R.id.setting_profileNameEdit);
        saveButton=(Button)getView().findViewById(R.id.setting_saveButton);
        initAuthList();
        setGUIBySettingData(SettingsData.getDefaultSetting());

        saveButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
               SettingsData data = getSettingDataFromGUI();
                if(verifyData(data)){
                    HttpUtility.UploadSetting(data.getProfileName(),data.serializeSettings());
                    data.setProfileName("");
                    setGUIBySettingData(data);
                    Toast.makeText(getView().getContext(),"Upload New Profile Done.",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean verifyData(SettingsData data){
        if(data.getProfileName()!=null ) {
            if(data.getProfileName().isEmpty()){
                Toast.makeText(getView().getContext(),"Profile Name can't be empty.",Toast.LENGTH_SHORT).show();
                return false;
            }

            if(!data.isCmUSB() && !data.isCmLan()&&!data.isCmOverTheAir()){
                Toast.makeText(getView().getContext(),"Should Select one Communication Method.",Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        else{
            return false;
        }

        return true;
    }
    public void setGUIBySettingData(SettingsData data){
        addressGUI.setText(data.getAddress());
        portGUI.setText(data.getPort());
        authMethodGUI.setSelection(data.getAuthMethod());
        usbGUI.setChecked(data.isCmUSB());
        lanGUI.setChecked(data.isCmLan());
        overTheAirGUI.setChecked(data.isCmOverTheAir());
        autoProcessGUI.setChecked(data.isAutoProcess());
        profileNameGUI.setText(data.getProfileName());
    }

    public SettingsData getSettingDataFromGUI(){
        SettingsData data =new SettingsData();
        data.setAddress(addressGUI.getText().toString());
        data.setPort(portGUI.getText().toString());
        data.setAuthMethod(authMethodGUI.getSelectedItemPosition());
        data.setCmUSB(usbGUI.isChecked());
        data.setCmLan(lanGUI.isChecked());
        data.setCmOverTheAir(overTheAirGUI.isChecked());
        data.setAutoProcess(autoProcessGUI.isChecked());
        data.setProfileName(profileNameGUI.getText().toString());

        return data;
    }


    private void initAuthList(){
        ArrayList<CharSequence> data = new ArrayList<CharSequence>();

        ArrayList<String> l=SettingsData.getAuthMethodList();
        for(String s : l){
            data.add(s);
        }
        ArrayAdapter<CharSequence> adapter=new ArrayAdapter<CharSequence>(getView().getContext(),android.R.layout.simple_spinner_item,data);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        authMethodGUI.setAdapter(adapter);
        authMethodGUI.setSelection(0);
        authMethodGUI.setVisibility(View.VISIBLE);
    }
}
