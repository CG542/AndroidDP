package com.mot.AndroidDP;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by bkmr38 on 5/23/2016.
 */
public class SettingsData {
    private String profileName;

    public String getProfileName() {
        return profileName;
    }

    public void setProfileName(String profileName) {
        this.profileName = profileName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public int getAuthMethod() {
        return authMethod;
    }

    public void setAuthMethod(int authMethod) {
        this.authMethod = authMethod;
    }

    public boolean isCmUSB() {
        return cmUSB;
    }

    public void setCmUSB(boolean cmUSB) {
        this.cmUSB = cmUSB;
    }

    public boolean isCmLan() {
        return cmLan;
    }

    public void setCmLan(boolean cmLan) {
        this.cmLan = cmLan;
    }

    public boolean isCmOverTheAir() {
        return cmOverTheAir;
    }

    public void setCmOverTheAir(boolean cmOverTheAir) {
        this.cmOverTheAir = cmOverTheAir;
    }

    public boolean isAutoProcess() {
        return autoProcess;
    }

    public void setAutoProcess(boolean autoProcess) {
        this.autoProcess = autoProcess;
    }

    private String address;
    private String port;
    private int authMethod;
    private boolean cmUSB;
    private boolean cmLan;
    private boolean cmOverTheAir;
    private boolean autoProcess;

    public static SettingsData getDefaultSetting() {
        SettingsData data = new SettingsData();
        data.setAddress("localhost");
        data.setPort("8675");
        data.setAuthMethod(1);
        data.setCmUSB(true);
        data.setCmLan(true);
        data.setCmOverTheAir(true);
        data.setAutoProcess(true);
        return data;
    }

    public String serializeSettings(){
        StringBuilder sb=new StringBuilder();
        sb.append(String.format("Addr=%s;",this.getAddress()));
        sb.append(String.format("Port=%s;",this.getPort()));
        sb.append(String.format("AuthMeth=%d;",this.getAuthMethod()));
        sb.append(String.format("USB=%s;",this.isCmUSB()?"1":"0"));
        sb.append(String.format("LAN=%s;",this.isCmLan()?"1":"0"));
        sb.append(String.format("OverAir=%s;",this.isCmOverTheAir()?"1":"0"));
        sb.append(String.format("AutoProc=%s;",this.isAutoProcess()?"1":"0"));
        return sb.toString();
    }

    public static ArrayList<String> getAuthMethodList(){
        ArrayList<String> l = new ArrayList<>();
        l.add("Certificate");
        l.add("Windows Credentials");
        return l;
    }

}
