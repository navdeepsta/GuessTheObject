package com.example.navdeep.guesstheobject;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.VectorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.Iterator;
import java.util.Random;
import java.util.SortedSet;
import java.util.TreeSet;

public class VectorImageExample extends AppCompatActivity {
LinearLayout l;TextView t;
SortedSet<Character> set;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vector_image_example);
        l = (LinearLayout)findViewById(R.id.linearLayout);
        set = new TreeSet<>();



    }

    public void onClick(View v){

       l.removeAllViews();
        String name = "Australia is awesome";
        Random r = new Random();
        int number = r.nextInt(name.length());
        boolean flag = false;
        for(int i=0; i<name.length();++i){
            TextView textView = new TextView(this);
            textView.setText("_");
            if(i==number) {
                textView.setText(String.valueOf(name.charAt(i)));
                number=r.nextInt(name.length());
            }
           set.add(name.toLowerCase().charAt(i));
            textView.setTextColor(Color.WHITE);
            LinearLayout.LayoutParams params =  (LinearLayout.LayoutParams) l.getLayoutParams();
            params.setMargins(20,20,20,20);
            textView.setLayoutParams(params);
            textView.setWidth(100);
            textView.setHeight(200);
            textView.setGravity(View.TEXT_ALIGNMENT_GRAVITY);
            textView.setBackgroundColor(Color.BLACK);
            textView.setTextSize(36);

            if(name.charAt(i) == ' '){
                textView.setText(" ");
                textView.setBackgroundColor(Color.WHITE);
            }
            l.addView(textView);

        }

        t = (TextView) l.getChildAt(0);
        Toast.makeText(this, t.getText(), Toast.LENGTH_SHORT).show();

    }



    }

