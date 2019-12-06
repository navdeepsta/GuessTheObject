package com.example.navdeep.guesstheobject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Field;

public class ChooseCategory extends AppCompatActivity implements View.OnClickListener{
    private TextView sport, vegetable;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_category);
        sport = (TextView)findViewById(R.id.sport);
        vegetable = (TextView)findViewById(R.id.vegetable);


    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, GuessActivity.class);
        switch(v.getId()){
            case R.id.sport:
                intent.putExtra(GuessActivity.EXTRA, "sports");
                startActivity(intent);
                break;
            case R.id.vegetable:
                intent.putExtra(GuessActivity.EXTRA, "vegetables");
                startActivity(intent);
                break;
            default:


        }
    }
}
