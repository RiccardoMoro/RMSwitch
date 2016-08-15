package com.rm.switchsample;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.rm.rmswitch.RMSwitch;

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

        mRMSwitch1 = (RMSwitch) findViewById(R.id.rm_switch1);

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

        mRMSwitch2 = (RMSwitch) findViewById(R.id.rm_switch2);

        mRMSwitch2.addSwitchObserver(new RMSwitch.RMSwitchObserver() {
            @Override
            public void onCheckStateChange(boolean isChecked) {
                mTxtRMSwitchState2.setText("Checked: " + isChecked);
            }
        });
        mTxtRMSwitchState2.setText("Checked: " + mRMSwitch2.isChecked());

        mRMSwitch3 = (RMSwitch) findViewById(R.id.rm_switch3);

        mRMSwitch3.addSwitchObserver(new RMSwitch.RMSwitchObserver() {
            @Override
            public void onCheckStateChange(boolean isChecked) {
                mTxtRMSwitchState3.setText("Checked: " + isChecked);
            }
        });
        mTxtRMSwitchState3.setText("Checked: " + mRMSwitch3.isChecked());

        mRMSwitch4 = (RMSwitch) findViewById(R.id.rm_switch4);

        mRMSwitch4.addSwitchObserver(new RMSwitch.RMSwitchObserver() {
            @Override
            public void onCheckStateChange(boolean isChecked) {
                mTxtRMSwitchState4.setText("Checked: " + isChecked);
            }
        });
        mTxtRMSwitchState4.setText("Checked: " + mRMSwitch4.isChecked());

        mRMSwitch5 = (RMSwitch) findViewById(R.id.rm_switch5);

        mRMSwitch5.addSwitchObserver(new RMSwitch.RMSwitchObserver() {
            @Override
            public void onCheckStateChange(boolean isChecked) {
                mTxtRMSwitchState5.setText("Checked: " + isChecked);
            }
        });
        mTxtRMSwitchState5.setText("Checked: " + mRMSwitch5.isChecked());

        mRMSwitch6 = (RMSwitch) findViewById(R.id.rm_switch6);

        mRMSwitch6.addSwitchObserver(new RMSwitch.RMSwitchObserver() {
            @Override
            public void onCheckStateChange(boolean isChecked) {
                mTxtRMSwitchState6.setText("Checked: " + isChecked);
            }
        });
        mTxtRMSwitchState6.setText("Checked: " + mRMSwitch6.isChecked());

        mRMSwitch7 = (RMSwitch) findViewById(R.id.rm_switch7);

        mRMSwitch7.addSwitchObserver(new RMSwitch.RMSwitchObserver() {
            @Override
            public void onCheckStateChange(boolean isChecked) {
                mTxtRMSwitchState7.setText("Checked: " + isChecked);
            }
        });
        mTxtRMSwitchState7.setText("Checked: " + mRMSwitch7.isChecked());

        mRMSwitch8 = (RMSwitch) findViewById(R.id.rm_switch8);

        mRMSwitch8.addSwitchObserver(new RMSwitch.RMSwitchObserver() {
            @Override
            public void onCheckStateChange(boolean isChecked) {
                mTxtRMSwitchState8.setText("Checked: " + isChecked);
            }
        });
        mTxtRMSwitchState8.setText("Checked: " + mRMSwitch8.isChecked());
    }
}
