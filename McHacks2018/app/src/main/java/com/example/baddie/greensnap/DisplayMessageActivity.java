package com.example.baddie.greensnap;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import static android.provider.AlarmClock.EXTRA_MESSAGE;

public class DisplayMessageActivity extends AppCompatActivity {
    final int sdk = android.os.Build.VERSION.SDK_INT;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_message);



        //Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        String message = intent.getStringExtra(EXTRA_MESSAGE);

        // Capture the layout's TextView and set the string as its text
        TextView par= findViewById(R.id.editText2);

        TextView textView = findViewById(R.id.textView);
        textView.setText(message);
        String a= "Thank you for recycling using GreenSnap! \n";
        String bol="";

        if (message.toLowerCase().equals("trash")) bol="not";

        String b= "This object is made up of "+ message +"and is " + bol+ " recyclable \n";

        String c= "You are beautiful and have made a differrence in the world, have a nice day :^)";

        par.setText(a+b+c);
        Log.d("toto", message);



        int id = getResources().getIdentifier(message.toLowerCase(), "drawable", getPackageName());
        Drawable drawable = getResources().getDrawable(id);

        if (message.toLowerCase().equals("plastic")){
            textView.setTextColor(Color.parseColor("#E65100"));
            par.setTextColor(Color.parseColor("#1A237E"));
        }
        if(message.toLowerCase().equals("trash")) textView.setTextColor(Color.parseColor("#D81B60"));

        setActivityBackground(drawable);


    }

    public void setActivityBackground(Drawable x){
        View view = this.getWindow().getDecorView();
        view.setBackground(x);
    }
}
