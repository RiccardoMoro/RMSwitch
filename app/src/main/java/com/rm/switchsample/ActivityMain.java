package com.rm.switchsample;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.rm.rmswitch.RMSwitch;
import com.rm.rmswitch.RMTristateSwitch;

public class ActivityMain extends AppCompatActivity {

    private RMSwitch mRMSwitch1;
    private TextView mTxtRMSwitchState1;

    private RMSwitch mRMSwitch2;
    private TextView mTxtRMSwitchState2;

    private RMSwitch mRMSwitch3;
    private TextView mTxtRMSwitchState3;

    private RMSwitch mRMSwitch4;
    private TextView mTxtRMSwitchState4;

    private RMSwitch mRMSwitch5;
    private TextView mTxtRMSwitchState5;

    private RMSwitch mRMSwitch6;
    private TextView mTxtRMSwitchState6;

    private RMSwitch mRMSwitch7;
    private TextView mTxtRMSwitchState7;

    private RMSwitch mRMSwitch8;
    private TextView mTxtRMSwitchState8;

    private RMTristateSwitch mRMTristateSwitch1;
    private TextView mTxtRMTristateSwitchState1;

    private RMTristateSwitch mRMTristateSwitch2;
    private TextView mTxtRMTristateSwitchState2;

    private RMTristateSwitch mRMTristateSwitch3;
    private TextView mTxtRMTristateSwitchState3;

    private RMTristateSwitch mRMTristateSwitch4;
    private TextView mTxtRMTristateSwitchState4;

    private RMTristateSwitch mRMTristateSwitch5;
    private TextView mTxtRMTristateSwitchState5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTxtRMSwitchState1 = (TextView) findViewById(R.id.txt_rm_switch_state1);
        mTxtRMSwitchState2 = (TextView) findViewById(R.id.txt_rm_switch_state2);
        mTxtRMSwitchState3 = (TextView) findViewById(R.id.txt_rm_switch_state3);
        mTxtRMSwitchState4 = (TextView) findViewById(R.id.txt_rm_switch_state4);
        mTxtRMSwitchState5 = (TextView) findViewById(R.id.txt_rm_switch_state5);
        mTxtRMSwitchState6 = (TextView) findViewById(R.id.txt_rm_switch_state6);
        mTxtRMSwitchState7 = (TextView) findViewById(R.id.txt_rm_switch_state7);
        mTxtRMSwitchState8 = (TextView) findViewById(R.id.txt_rm_switch_state8);
        mRMTristateSwitch1 = (RMTristateSwitch) findViewById(R.id.rm_triswitch1);
        mRMTristateSwitch2 = (RMTristateSwitch) findViewById(R.id.rm_triswitch2);
        mRMTristateSwitch3 = (RMTristateSwitch) findViewById(R.id.rm_triswitch3);
        mRMTristateSwitch4 = (RMTristateSwitch) findViewById(R.id.rm_triswitch4);
        mRMTristateSwitch5 = (RMTristateSwitch) findViewById(R.id.rm_triswitch5);

        mRMSwitch1 = (RMSwitch) findViewById(R.id.rm_switch1);
        mRMSwitch2 = (RMSwitch) findViewById(R.id.rm_switch2);
        mRMSwitch3 = (RMSwitch) findViewById(R.id.rm_switch3);
        mRMSwitch4 = (RMSwitch) findViewById(R.id.rm_switch4);
        mRMSwitch5 = (RMSwitch) findViewById(R.id.rm_switch5);
        mRMSwitch6 = (RMSwitch) findViewById(R.id.rm_switch6);
        mRMSwitch7 = (RMSwitch) findViewById(R.id.rm_switch7);
        mRMSwitch8 = (RMSwitch) findViewById(R.id.rm_switch8);
        mTxtRMTristateSwitchState1 = (TextView) findViewById(R.id.txt_rm_triswitch_state1);
        mTxtRMTristateSwitchState2 = (TextView) findViewById(R.id.txt_rm_triswitch_state2);
        mTxtRMTristateSwitchState3 = (TextView) findViewById(R.id.txt_rm_triswitch_state3);
        mTxtRMTristateSwitchState4 = (TextView) findViewById(R.id.txt_rm_triswitch_state4);
        mTxtRMTristateSwitchState5 = (TextView) findViewById(R.id.txt_rm_triswitch_state5);


        mRMSwitch1.addSwitchObserver(new RMSwitch.RMSwitchObserver() {
            @Override
            public void onCheckStateChange(boolean isChecked) {
                mTxtRMSwitchState1.setText("Checked: " + isChecked);
            }
        });
        mRMSwitch1.setChecked(true);
        mRMSwitch1.setEnabled(false);
        mRMSwitch1.setForceAspectRatio(false);
        mRMSwitch1.setSwitchBkgCheckedColor(ContextCompat.getColor(this, R.color.green));
        mRMSwitch1.setSwitchBkgNotCheckedColor(ContextCompat.getColor(this, R.color.red));
        mRMSwitch1.setSwitchToggleCheckedColor(ContextCompat.getColor(this, R.color.white));
        mRMSwitch1.setSwitchToggleNotCheckedColor(ContextCompat.getColor(this, R.color.white));

        mTxtRMSwitchState1.setText("Checked: " + mRMSwitch1.isChecked());


        mRMSwitch2.addSwitchObserver(new RMSwitch.RMSwitchObserver() {
            @Override
            public void onCheckStateChange(boolean isChecked) {
                mTxtRMSwitchState2.setText("Checked: " + isChecked);
            }
        });
        mTxtRMSwitchState2.setText("Checked: " + mRMSwitch2.isChecked());


        mRMSwitch3.addSwitchObserver(new RMSwitch.RMSwitchObserver() {
            @Override
            public void onCheckStateChange(boolean isChecked) {
                mTxtRMSwitchState3.setText("Checked: " + isChecked);
            }
        });
        mTxtRMSwitchState3.setText("Checked: " + mRMSwitch3.isChecked());


        mRMSwitch4.addSwitchObserver(new RMSwitch.RMSwitchObserver() {
            @Override
            public void onCheckStateChange(boolean isChecked) {
                mTxtRMSwitchState4.setText("Checked: " + isChecked);
            }
        });
        mTxtRMSwitchState4.setText("Checked: " + mRMSwitch4.isChecked());


        mRMSwitch5.addSwitchObserver(new RMSwitch.RMSwitchObserver() {
            @Override
            public void onCheckStateChange(boolean isChecked) {
                mTxtRMSwitchState5.setText("Checked: " + isChecked);
            }
        });
        mTxtRMSwitchState5.setText("Checked: " + mRMSwitch5.isChecked());


        mRMSwitch6.addSwitchObserver(new RMSwitch.RMSwitchObserver() {
            @Override
            public void onCheckStateChange(boolean isChecked) {
                mTxtRMSwitchState6.setText("Checked: " + isChecked);
            }
        });
        mTxtRMSwitchState6.setText("Checked: " + mRMSwitch6.isChecked());


        mRMSwitch7.addSwitchObserver(new RMSwitch.RMSwitchObserver() {
            @Override
            public void onCheckStateChange(boolean isChecked) {
                mTxtRMSwitchState7.setText("Checked: " + isChecked);
            }
        });
        mTxtRMSwitchState7.setText("Checked: " + mRMSwitch7.isChecked());


        mRMSwitch8.addSwitchObserver(new RMSwitch.RMSwitchObserver() {
            @Override
            public void onCheckStateChange(boolean isChecked) {
                mTxtRMSwitchState8.setText("Checked: " + isChecked);
            }
        });
        mTxtRMSwitchState8.setText("Checked: " + mRMSwitch8.isChecked());


        int state = mRMTristateSwitch1.getState();
        mTxtRMTristateSwitchState1.setText(state == RMTristateSwitch.STATE_LEFT ?
                "Left" :
                state == RMTristateSwitch.STATE_MIDDLE ?
                        "Middle" :
                        "Right");
        mRMTristateSwitch1.addSwitchObserver(new RMTristateSwitch.RMTristateSwitchObserver() {
            @Override
            public void onCheckStateChange(@RMTristateSwitch.State int state) {
                mTxtRMTristateSwitchState1.setText(state == RMTristateSwitch.STATE_LEFT ?
                        "Left" :
                        state == RMTristateSwitch.STATE_MIDDLE ?
                                "Middle" :
                                "Right");
            }
        });

        state = mRMTristateSwitch2.getState();
        mTxtRMTristateSwitchState2.setText(state == RMTristateSwitch.STATE_LEFT ?
                "Left" :
                state == RMTristateSwitch.STATE_MIDDLE ?
                        "Middle" :
                        "Right");
        mRMTristateSwitch2.addSwitchObserver(new RMTristateSwitch.RMTristateSwitchObserver() {
            @Override
            public void onCheckStateChange(@RMTristateSwitch.State int state) {
                mTxtRMTristateSwitchState2.setText(state == RMTristateSwitch.STATE_LEFT ?
                        "Left" :
                        state == RMTristateSwitch.STATE_MIDDLE ?
                                "Middle" :
                                "Right");
            }
        });

        state = mRMTristateSwitch3.getState();
        mTxtRMTristateSwitchState3.setText(state == RMTristateSwitch.STATE_LEFT ?
                "Left" :
                state == RMTristateSwitch.STATE_MIDDLE ?
                        "Middle" :
                        "Right");
        mRMTristateSwitch3.addSwitchObserver(new RMTristateSwitch.RMTristateSwitchObserver() {
            @Override
            public void onCheckStateChange(@RMTristateSwitch.State int state) {
                mTxtRMTristateSwitchState3.setText(state == RMTristateSwitch.STATE_LEFT ?
                        "Left" :
                        state == RMTristateSwitch.STATE_MIDDLE ?
                                "Middle" :
                                "Right");
            }
        });

        state = mRMTristateSwitch4.getState();
        mTxtRMTristateSwitchState4.setText(state == RMTristateSwitch.STATE_LEFT ?
                "Left" :
                state == RMTristateSwitch.STATE_MIDDLE ?
                        "Middle" :
                        "Right");
        mRMTristateSwitch4.addSwitchObserver(new RMTristateSwitch.RMTristateSwitchObserver() {
            @Override
            public void onCheckStateChange(@RMTristateSwitch.State int state) {
                mTxtRMTristateSwitchState4.setText(state == RMTristateSwitch.STATE_LEFT ?
                        "Left" :
                        state == RMTristateSwitch.STATE_MIDDLE ?
                                "Middle" :
                                "Right");
            }
        });

        mRMTristateSwitch5.setSwitchToggleLeftDrawableRes(R.drawable.theme_night);
        mRMTristateSwitch5.setSwitchToggleMiddleDrawableRes(R.drawable.theme_auto);
        mRMTristateSwitch5.setSwitchToggleRightDrawableRes(R.drawable.theme_day);
        state = mRMTristateSwitch5.getState();
        mTxtRMTristateSwitchState5.setText(state == RMTristateSwitch.STATE_LEFT ?
                "Left" :
                state == RMTristateSwitch.STATE_MIDDLE ?
                        "Middle" :
                        "Right");
        mRMTristateSwitch5.addSwitchObserver(new RMTristateSwitch.RMTristateSwitchObserver() {
            @Override
            public void onCheckStateChange(@RMTristateSwitch.State int state) {
                mTxtRMTristateSwitchState5.setText(state == RMTristateSwitch.STATE_LEFT ?
                        "Left" :
                        state == RMTristateSwitch.STATE_MIDDLE ?
                                "Middle" :
                                "Right");
            }
        });

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mRMTristateSwitch5 != null)
                    mRMTristateSwitch5.setSlimDesign(true);
            }
        }, 3000);
    }
}
