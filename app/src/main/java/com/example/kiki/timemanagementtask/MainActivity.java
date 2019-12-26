package com.example.kiki.timemanagementtask;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class MainActivity extends AppCompatActivity implements VC1Fragment.OnFragmentInteractionListener, VC2Fragment.OnFragmentInteractionListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, new VC1Fragment(),"vc1")
                    .commit();
        }

        Button btnVC1 = findViewById(R.id.btn_vc1);
        btnVC1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, new VC1Fragment(),"vc1")
                        .commit();
            }
        });


        Button btnVC2 = findViewById(R.id.btn_vc2);
        btnVC2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, new VC2Fragment(),"vc2")
                        .commit();
            }
        });

    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
