package com.cscodetech.freshfastfooddeliveryboy.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.cscodetech.freshfastfooddeliveryboy.R;
import com.cscodetech.freshfastfooddeliveryboy.utils.SessionManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class InfoActivity extends AppCompatActivity {

    @BindView(R.id.btn_login)
    TextView btnLogin;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        ButterKnife.bind(this);
        getSupportActionBar().hide();
        sessionManager = new SessionManager(this);

        if (sessionManager.getBooleanData("rlogin")) {
            startActivity(new Intent(InfoActivity.this, HomeActivity.class));
            finish();
        }
    }

    @OnClick(R.id.btn_login)
    public void onClick() {
        startActivity(new Intent(InfoActivity.this, LoginActivity.class));
        finish();
    }
}
