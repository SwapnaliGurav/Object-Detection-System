package com.example.smartshopassistant;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.example.smartshopassistant.Activity.Homescreen;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class SettingActivity extends AppCompatActivity {

    private MeowBottomNavigation meowBottomNavigation;

    private final int ID_PROFILE = 1;
    private final int ID_HOME = 2;
    private final int ID_SETTING = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_setting);

        meowBottomNavigation =findViewById(R.id.navs);


        meowBottomNavigation.show(3,true);

        meowBottomNavigation.add(new MeowBottomNavigation.Model(ID_PROFILE, R.drawable.profile));
        meowBottomNavigation.add(new MeowBottomNavigation.Model(ID_HOME, R.drawable.home));
        meowBottomNavigation.add(new MeowBottomNavigation.Model(ID_SETTING, R.drawable.setting));

        meowBottomNavigation.setOnClickMenuListener(new Function1<MeowBottomNavigation.Model, Unit>() {
            @Override
            public Unit invoke(MeowBottomNavigation.Model item) {
                //  Toast.makeText(Homescreen.this, "clicked item"+item.getId(), Toast.LENGTH_SHORT).show();
                //  Toast.makeText(Homescreen.this, "Clicked item " + item.getId(), Toast.LENGTH_SHORT).show();

                // You can perform additional actions based on the clicked item ID
                switch (item.getId()) {
                    case ID_PROFILE:
                        Intent i = new Intent(SettingActivity.this,MainActivity.class);
                        startActivity(i);
                        finish();
                        // Handle the Home item click
                        break;
                    case ID_HOME:
                        Intent intent=new Intent(SettingActivity.this, Homescreen.class);
                        startActivity(intent);
                        finish();
                        // Handle the Message item click
                        break;
                    case ID_SETTING:
                        Intent p=new Intent(SettingActivity.this, SettingActivity.class);
                        startActivity(p);
                        finish();
                        // Handle the Notification item click
                        break;

                }
                return Unit.INSTANCE;           }
        });


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}