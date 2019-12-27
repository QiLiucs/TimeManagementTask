package com.example.kiki.timemanagementtask;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class MainActivity extends AppCompatActivity implements VC1Fragment.OnFragmentInteractionListener, VC2Fragment.OnFragmentInteractionListener{

    public static String collectionsName = "goalItems3";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, new VC1Fragment(),VC1Fragment.mName)
                    .commit();
        }

        Button btnVC1 = findViewById(R.id.btn_vc1);
        btnVC1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, new VC1Fragment(),VC1Fragment.mName)
                        .commit();
            }
        });


        Button btnVC2 = findViewById(R.id.btn_vc2);
        btnVC2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, new VC2Fragment(),VC2Fragment.mName)
                        .commit();
            }
        });

    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
