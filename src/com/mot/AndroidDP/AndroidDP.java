package com.mot.AndroidDP;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.widget.RadioButton;
import android.widget.RadioGroup;

//                            _ooOoo_
//                           o8888888o
//                           88" . "88
//                           (| -_- |)
//                            O\ = /O
//                        ____/`---'\____
//                      .   ' \\| |// `.
//                       / \\||| : |||// \
//                     / _||||| -:- |||||- \
//                       | | \\\ - /// | |
//                     | \_| ''\---/'' | |
//                      \ .-\__ `-` ___/-. /
//                   ___`. .' /--.--\ `. . __
//                ."" '< `.___\_<|>_/___.' >'"".
//               | | : `- \`.;`\ _ /`;.`/ - ` : | |
//                 \ \ `-. \_ __\ /__ _/ .-` / /
//         ======`-.____`-.___\_____/___.-`____.-'======
//                            `=---='
//
//         .............................................
//                  佛祖保佑             永无BUG
//
//
//
public class AndroidDP extends Activity {

    private Fragment[] fragments;
    private FragmentManager fragmentMgr;
    private FragmentTransaction fragmentTran;


    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.main);

        setupFragments();
        setupIndicator();

        Intent intent = new Intent(AndroidDP.this,StatusListener.class);
        registerBroadcastReceiver();
        startService(intent);

    }

    private void registerBroadcastReceiver(){
        StatusReceiver receiver = new StatusReceiver();
        IntentFilter filter = new IntentFilter(GlobalPara.Status_Changed_Action);
        registerReceiver(receiver,filter);
    }
    public  class StatusReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(GlobalPara.Status_Changed_Action)) {
                Log.i("StatusReceiver","LoadData");
                ((FragementStatus)fragments[0]).LoadData();
            }
        }
    }

    private void setupFragments() {
        fragments = new Fragment[3];
        fragmentMgr = getFragmentManager();

        fragments[0] = fragmentMgr.findFragmentById(R.id.fragement_status);
        fragments[1] = fragmentMgr.findFragmentById(R.id.fragement_deploy);
        fragments[2] = fragmentMgr.findFragmentById(R.id.fragement_setting);

        fragmentTran = fragmentMgr.beginTransaction().hide(fragments[0]).hide(fragments[1]).hide(fragments[2]);
        fragmentTran.show(fragments[0]).commit();

    }

    private void setupIndicator() {
        RadioGroup bottomRg;
        RadioButton rbOne, rbTwo, rbThree;

        bottomRg = (RadioGroup) findViewById(R.id.bottomRg);
        rbOne = (RadioButton) findViewById(R.id.rbOne);
        rbTwo = (RadioButton) findViewById(R.id.rbTwo);
        rbThree = (RadioButton) findViewById(R.id.rbThree);

        bottomRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                fragmentTran = fragmentMgr.beginTransaction()
                        .hide(fragments[0]).hide(fragments[1])
                        .hide(fragments[2]);
                switch (checkedId) {
                    case R.id.rbOne:
                        fragmentTran.show(fragments[0]).commit();
                        break;

                    case R.id.rbTwo:
                        fragmentTran.show(fragments[1]).commit();
                        break;

                    case R.id.rbThree:
                        fragmentTran.show(fragments[2]).commit();
                        break;

                    default:
                        break;
                }
            }
        });
    }
}
