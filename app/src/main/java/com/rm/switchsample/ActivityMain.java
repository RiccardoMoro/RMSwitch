package com.rm.switchsample;

import android.os.Bundle;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
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

    private RMSwitch mRMSwitch9;
    private TextView mTxtRMSwitchState9;


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

    private RMTristateSwitch mRMTristateSwitch6;
    private TextView mTxtRMTristateSwitchState6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRMTristateSwitch1 = findViewById(R.id.rm_triswitch1);
        mRMTristateSwitch2 = findViewById(R.id.rm_triswitch2);
        mRMTristateSwitch3 = findViewById(R.id.rm_triswitch3);
        mRMTristateSwitch4 = findViewById(R.id.rm_triswitch4);
        mRMTristateSwitch5 = findViewById(R.id.rm_triswitch5);
        mRMTristateSwitch6 = findViewById(R.id.rm_triswitch6);

        mTxtRMTristateSwitchState1 = findViewById(R.id.txt_rm_triswitch_state1);
        mTxtRMTristateSwitchState2 = findViewById(R.id.txt_rm_triswitch_state2);
        mTxtRMTristateSwitchState3 = findViewById(R.id.txt_rm_triswitch_state3);
        mTxtRMTristateSwitchState4 = findViewById(R.id.txt_rm_triswitch_state4);
        mTxtRMTristateSwitchState5 = findViewById(R.id.txt_rm_triswitch_state5);
        mTxtRMTristateSwitchState6 = findViewById(R.id.txt_rm_triswitch_state6);

        mRMSwitch1 = findViewById(R.id.rm_switch1);
        mRMSwitch2 = findViewById(R.id.rm_switch2);
        mRMSwitch3 = findViewById(R.id.rm_switch3);
        mRMSwitch4 = findViewById(R.id.rm_switch4);
        mRMSwitch5 = findViewById(R.id.rm_switch5);
        mRMSwitch6 = findViewById(R.id.rm_switch6);
        mRMSwitch7 = findViewById(R.id.rm_switch7);
        mRMSwitch8 = findViewById(R.id.rm_switch8);
        mRMSwitch9 = findViewById(R.id.rm_switch9);

        mTxtRMSwitchState1 = findViewById(R.id.txt_rm_switch_state1);
        mTxtRMSwitchState2 = findViewById(R.id.txt_rm_switch_state2);
        mTxtRMSwitchState3 = findViewById(R.id.txt_rm_switch_state3);
        mTxtRMSwitchState4 = findViewById(R.id.txt_rm_switch_state4);
        mTxtRMSwitchState5 = findViewById(R.id.txt_rm_switch_state5);
        mTxtRMSwitchState6 = findViewById(R.id.txt_rm_switch_state6);
        mTxtRMSwitchState7 = findViewById(R.id.txt_rm_switch_state7);
        mTxtRMSwitchState8 = findViewById(R.id.txt_rm_switch_state8);
        mTxtRMSwitchState9 = findViewById(R.id.txt_rm_switch_state9);


        mRMSwitch1.addSwitchObserver(new RMSwitch.RMSwitchObserver() {
            @Override
            public void onCheckStateChange(RMSwitch switchView, boolean isChecked) {
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
            public void onCheckStateChange(RMSwitch switchView, boolean isChecked) {
                mTxtRMSwitchState2.setText("Checked: " + isChecked);
            }
        });
        mTxtRMSwitchState2.setText("Checked: " + mRMSwitch2.isChecked());


        mRMSwitch3.addSwitchObserver(new RMSwitch.RMSwitchObserver() {
            @Override
            public void onCheckStateChange(RMSwitch switchView, boolean isChecked) {
                mTxtRMSwitchState3.setText("Checked: " + isChecked);
            }
        });
        mTxtRMSwitchState3.setText("Checked: " + mRMSwitch3.isChecked());


        mRMSwitch4.addSwitchObserver(new RMSwitch.RMSwitchObserver() {
            @Override
            public void onCheckStateChange(RMSwitch switchView, boolean isChecked) {
                mTxtRMSwitchState4.setText("Checked: " + isChecked);
            }
        });
        mTxtRMSwitchState4.setText("Checked: " + mRMSwitch4.isChecked());


        mRMSwitch5.addSwitchObserver(new RMSwitch.RMSwitchObserver() {
            @Override
            public void onCheckStateChange(RMSwitch switchView, boolean isChecked) {
                mTxtRMSwitchState5.setText("Checked: " + isChecked);
            }
        });
        mTxtRMSwitchState5.setText("Checked: " + mRMSwitch5.isChecked());


        mRMSwitch6.addSwitchObserver(new RMSwitch.RMSwitchObserver() {
            @Override
            public void onCheckStateChange(RMSwitch switchView, boolean isChecked) {
                mTxtRMSwitchState6.setText("Checked: " + isChecked);
            }
        });
        mTxtRMSwitchState6.setText("Checked: " + mRMSwitch6.isChecked());


        mRMSwitch7.addSwitchObserver(new RMSwitch.RMSwitchObserver() {
            @Override
            public void onCheckStateChange(RMSwitch switchView, boolean isChecked) {
                mTxtRMSwitchState7.setText("Checked: " + isChecked);
            }
        });
        mTxtRMSwitchState7.setText("Checked: " + mRMSwitch7.isChecked());


        mRMSwitch8.addSwitchObserver(new RMSwitch.RMSwitchObserver() {
            @Override
            public void onCheckStateChange(RMSwitch switchView, boolean isChecked) {
                mTxtRMSwitchState8.setText("Checked: " + isChecked);
            }
        });
        mTxtRMSwitchState8.setText("Checked: " + mRMSwitch9.isChecked());

        mRMSwitch9.addSwitchObserver(new RMSwitch.RMSwitchObserver() {
            @Override
            public void onCheckStateChange(RMSwitch switchView, boolean isChecked) {
                mTxtRMSwitchState9.setText("Checked: " + isChecked);
            }
        });
        mTxtRMSwitchState9.setText("Checked: " + mRMSwitch9.isChecked());


        int state = mRMTristateSwitch1.getState();
        mTxtRMTristateSwitchState1.setText(state == RMTristateSwitch.STATE_LEFT ?
                "Left" :
                state == RMTristateSwitch.STATE_MIDDLE ?
                        "Middle" :
                        "Right");
        mRMTristateSwitch1.addSwitchObserver(new RMTristateSwitch.RMTristateSwitchObserver() {
            @Override
            public void onCheckStateChange(RMTristateSwitch switchView,
                                           @RMTristateSwitch.State int state) {
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
            public void onCheckStateChange(RMTristateSwitch switchView,
                                           @RMTristateSwitch.State int state) {
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
            public void onCheckStateChange(RMTristateSwitch switchView,
                                           @RMTristateSwitch.State int state) {
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
            public void onCheckStateChange(RMTristateSwitch switchView,
                                           @RMTristateSwitch.State int state) {
                mTxtRMTristateSwitchState4.setText(state == RMTristateSwitch.STATE_LEFT ?
                        "Left" :
                        state == RMTristateSwitch.STATE_MIDDLE ?
                                "Middle" :
                                "Right");
            }
        });

        mRMTristateSwitch5.setSwitchToggleLeftDrawableRes(R.drawable.theme_night);
        mRMTristateSwitch5.setSwitchToggleMiddleDrawableRes(R.drawable.theme_auto);
        mRMTristateSwitch5.setSwitchToggleRightDrawable(ContextCompat.getDrawable(this,
                R.drawable.theme_day));
        state = mRMTristateSwitch5.getState();
        mTxtRMTristateSwitchState5.setText(state == RMTristateSwitch.STATE_LEFT ?
                "Left" :
                state == RMTristateSwitch.STATE_MIDDLE ?
                        "Middle" :
                        "Right");
        mRMTristateSwitch5.addSwitchObserver(new RMTristateSwitch.RMTristateSwitchObserver() {
            @Override
            public void onCheckStateChange(RMTristateSwitch switchView,
                                           @RMTristateSwitch.State int state) {
                mTxtRMTristateSwitchState5.setText(state == RMTristateSwitch.STATE_LEFT ?
                        "Left" :
                        state == RMTristateSwitch.STATE_MIDDLE ?
                                "Middle" :
                                "Right");
            }
        });

        state = mRMTristateSwitch6.getState();
        mTxtRMTristateSwitchState6.setText(state == RMTristateSwitch.STATE_LEFT ?
                "Left" :
                state == RMTristateSwitch.STATE_MIDDLE ?
                        "Middle" :
                        "Right");
        mRMTristateSwitch6.addSwitchObserver(new RMTristateSwitch.RMTristateSwitchObserver() {
            @Override
            public void onCheckStateChange(RMTristateSwitch switchView,
                                           @RMTristateSwitch.State int state) {
                mTxtRMTristateSwitchState6.setText(state == RMTristateSwitch.STATE_LEFT ?
                        "Left" :
                        state == RMTristateSwitch.STATE_MIDDLE ?
                                "Middle" :
                                "Right");
            }
        });
    }
}
