package com.nanny.AreWeThere;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import java.net.URISyntaxException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Button easyModeButton, playModeButton, howToPlayButton;
    ImageView soundImageView, itemListImageView, soundImageView2;
    TextView id;


    int tot=1100;
    int rndo= (int) (Math.random() * 15);
    int us=Integer.parseInt(String.valueOf(rndo),15);

    DatabaseReference mFirebaseDatabase;
    private boolean flag = false;

//    static FirebaseRecyclerAdapter<ItemGetterSetter, DisplayItem.UsersViewHolder> firebaseRecyclerAdapter;


     static SharedPreferences  sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        mFirebaseDatabase = FirebaseDatabase.getInstance().getReference().child("Words");
        mFirebaseDatabase.keepSynced(true);



//        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<ItemGetterSetter, DisplayItem.UsersViewHolder>(
//                ItemGetterSetter.class,
//                R.layout.item_list_row,
//                DisplayItem.UsersViewHolder.class,
//                mFirebaseDatabase.orderByChild("Words").limitToLast(1100)
//        ) {
//            @Override
//            protected void populateViewHolder(DisplayItem.UsersViewHolder viewHolder, ItemGetterSetter model, int position) {
//
//                sharedPreferences = MainActivity.this.getSharedPreferences("mypref", MODE_PRIVATE);
//                SharedPreferences.Editor editor = sharedPreferences.edit();
//                editor.putString("word", model.getName());
//                editor.commit();
//            }
//        };


        Intent myService = new Intent(MainActivity.this, BackgroundSoundService.class);
        startService(myService);

        Typeface custom = Typeface.createFromAsset(MainActivity.this.getAssets(), "DK Canoodle.ttf");


        easyModeButton = (Button) findViewById(R.id.play_easy_mode_button);
        playModeButton = (Button) findViewById(R.id.play_trip_mode_button);
        howToPlayButton = (Button) findViewById(R.id.how_to_play_button);
        itemListImageView = (ImageView) findViewById(R.id.itemListImageView);
        soundImageView = (ImageView) findViewById(R.id.sound_imageView);
        soundImageView2 = findViewById(R.id.sound_imageView2);
        id = findViewById(R.id.id);

        //     =================== Setting font in button text=================

        easyModeButton.setTypeface(Typeface.createFromAsset(getAssets(), "DK Canoodle.ttf"));
        playModeButton.setTypeface(Typeface.createFromAsset(getAssets(), "DK Canoodle.ttf"));
        howToPlayButton.setTypeface(Typeface.createFromAsset(getAssets(), "DK Canoodle.ttf"));

        soundImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                soundImageView.setVisibility(View.INVISIBLE);
                soundImageView2.setVisibility(View.VISIBLE);

                Intent myService = new Intent(MainActivity.this, BackgroundSoundService.class);
                startService(myService);
            }
        });
        soundImageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                soundImageView2.setVisibility(View.INVISIBLE);
                soundImageView.setVisibility(View.VISIBLE);

                Intent myService = new Intent(MainActivity.this, BackgroundSoundService.class);
                stopService(myService);
            }
        });
        itemListImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, DisplayItem.class);
                startActivity(i);
            }
        });
        easyModeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                sharedPreferences = MainActivity.this.getSharedPreferences("mypref", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("type", "easy");
                editor.commit();

                Intent intent1 = new Intent(getApplicationContext(), Tap_Words.class);
                intent1.putExtra("clicked", "clicked");
                startActivity(intent1);

                String type = sharedPreferences.getString("type", null);
            }
        });
        playModeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sharedPreferences = MainActivity.this.getSharedPreferences("mypref", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("type", "time");
                editor.commit();

                Intent intent1 = new Intent(getApplicationContext(), TimerScreen.class);
                intent1.putExtra("main", "main");
                startActivity(intent1);
            }
        });
        howToPlayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                LayoutInflater inflater = MainActivity.this.getLayoutInflater();
                final View dailog = inflater.inflate(R.layout.play_dailog, null);

                builder.setView(dailog);

                Typeface custom = Typeface.createFromAsset(getAssets(), "DK Canoodle.ttf");
                TextView play = dailog.findViewById(R.id.play);
                TextView desc = dailog.findViewById(R.id.desc);
                ImageView close = dailog.findViewById(R.id.close);

                play.setTypeface(custom);
                desc.setTypeface(custom);

                final AlertDialog dialog1 = builder.create();
                dialog1.show();
                dialog1.getWindow().setBackgroundDrawable(getResources().getDrawable(R.drawable.alert));

                close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog1.dismiss();
                    }
                });
            }
        });


        final SharedPreferences sharedPreferences = MainActivity.this.getSharedPreferences("mypref", MODE_PRIVATE);
        String count = sharedPreferences.getString("totalpoint", null);
        String time = sharedPreferences.getString("timetotalpoint", null);
        String duration=sharedPreferences.getString("duration",null);

        Bundle bundle = getIntent().getExtras();

        String type = sharedPreferences.getString("type", null);
        if (type != null) {
            if (type.equals("easy")) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                LayoutInflater inflater = (LayoutInflater) MainActivity.this.getLayoutInflater();
                final View dailog = inflater.inflate(R.layout.gameover_dailog, null);
                builder.setView(dailog);

                final AlertDialog dialog = builder.create();
                dialog.show();
                dialog.setCanceledOnTouchOutside(false);
                dialog.setCancelable(false);

                dialog.getWindow().setBackgroundDrawable(getResources().getDrawable(R.drawable.green_gameover));


                TextView over = dailog.findViewById(R.id.over);
                TextView word1 = dailog.findViewById(R.id.word1);
                TextView word2 = dailog.findViewById(R.id.word2);
                TextView word3 = dailog.findViewById(R.id.word3);
                TextView word4 = dailog.findViewById(R.id.word4);
                TextView totalpoint1 = dailog.findViewById(R.id.totalpoint);
                TextView totalpoint2 = dailog.findViewById(R.id.totalpoint2);
                TextView equal = dailog.findViewById(R.id.equal);
                ImageView close = dailog.findViewById(R.id.game_over_iv);

                over.setTypeface(custom);
                word1.setTypeface(custom);
                word2.setTypeface(custom);
                word3.setTypeface(custom);
                word4.setTypeface(custom);
                totalpoint1.setTypeface(custom);
                totalpoint2.setTypeface(custom);


//                Intent myService1 = new Intent(MainActivity.this, BackgroungSoundServiceGold.class);
//                startService(myService1);


                totalpoint1.setText(String.valueOf(count));
                totalpoint2.setText(String.valueOf(count));
                close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                        SharedPreferences.Editor editor =sharedPreferences.edit();
                       editor.remove("type");
                       editor.clear();
                       editor.commit();
                    }
                });
            } else if (type.equals("time")) {
                int timecount=Integer.parseInt(time);
                int durationcount = Integer.parseInt(duration);

                if (durationcount == 0){

                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    LayoutInflater inflater = (LayoutInflater) MainActivity.this.getLayoutInflater();
                    final View dailog = inflater.inflate(R.layout.time_gameover, null);
                    builder.setView(dailog);

                    final AlertDialog dialog = builder.create();
                    dialog.show();
                    dialog.setCanceledOnTouchOutside(false);
                    dialog.setCancelable(false);

                    dialog.getWindow().setBackgroundDrawable(getResources().getDrawable(R.drawable.red_gameover));


                    TextView over = dailog.findViewById(R.id.over);
                    TextView word1 = dailog.findViewById(R.id.word1);
                    TextView word2 = dailog.findViewById(R.id.word2);
                    TextView word3 = dailog.findViewById(R.id.word3);
                    TextView word4 = dailog.findViewById(R.id.word4);
                    TextView totalpoint1 = dailog.findViewById(R.id.totalpoint);
                    TextView totalpoint2 = dailog.findViewById(R.id.totalpoint2);
                    TextView leftpoint = dailog.findViewById(R.id.leftpoint);
                    ImageView close = dailog.findViewById(R.id.game_over_iv);
                    RelativeLayout back = dailog.findViewById(R.id.back);

                    over.setTypeface(custom);
                    word1.setTypeface(custom);
                    word2.setTypeface(custom);
                    word3.setTypeface(custom);
                    word4.setTypeface(custom);
                    totalpoint1.setTypeface(custom);
                    totalpoint2.setTypeface(custom);


//                    Intent myService1 = new Intent(MainActivity.this, BackgroungSoundServiceGold.class);
//                    startService(myService1);


                    totalpoint1.setText(String.valueOf(timecount+durationcount));
                    totalpoint2.setText(String.valueOf(time));
                    leftpoint.setText(String.valueOf(duration));


                    close.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.dismiss();
                            SharedPreferences.Editor editor =sharedPreferences.edit();
                            editor.remove("type");
                            editor.clear();
                            editor.commit();
                        }
                    });
                }
                else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    LayoutInflater inflater = (LayoutInflater) MainActivity.this.getLayoutInflater();
                    final View dailog = inflater.inflate(R.layout.gameover_click, null);
                    builder.setView(dailog);

                    final AlertDialog dialog = builder.create();
                    dialog.show();
                    dialog.setCanceledOnTouchOutside(false);
                    dialog.setCancelable(false);
                    dialog.getWindow().setBackgroundDrawable(getResources().getDrawable(R.drawable.green_gameover));


                    TextView over = dailog.findViewById(R.id. over);
                    TextView word1 = dailog.findViewById(R.id.word1);
                    TextView word2 = dailog.findViewById(R.id.word2);
                    TextView word3 = dailog.findViewById(R.id.word3);
                    TextView word4 = dailog.findViewById(R.id.word4);
                    TextView totalpoint1 = dailog.findViewById(R.id.totalpoint);
                    TextView totalpoint2 = dailog.findViewById(R.id.totalpoint2);
                    TextView leftpoint = dailog.findViewById(R.id.leftpoint);
                    ImageView close = dailog.findViewById(R.id.game_over_iv);
                    RelativeLayout back = dailog.findViewById(R.id.back);

                    over.setTypeface(custom);
                    word1.setTypeface(custom);
                    word2.setTypeface(custom);
                    word3.setTypeface(custom);
                    word4.setTypeface(custom);
                    leftpoint.setTypeface(custom);
                    totalpoint1.setTypeface(custom);
                    totalpoint2.setTypeface(custom);

//                    Intent myService1 = new Intent(MainActivity.this, BackgroungSoundServiceGold.class);
//                    startService(myService1);

                    totalpoint1.setText(String.valueOf(timecount+durationcount));
                    totalpoint2.setText(String.valueOf(time));
                    leftpoint.setText(String.valueOf(duration));

                    close.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.dismiss();
                            SharedPreferences.Editor editor =sharedPreferences.edit();
                            editor.remove("type");
                            editor.clear();
                            editor.commit();
                        }
                    });
                }
            }

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent myService = new Intent(MainActivity.this, BackgroundSoundService.class);
        startService(myService);
    }

    @Override
    protected void onPause() {
        Intent myService = new Intent(MainActivity.this, BackgroundSoundService.class);
        stopService(myService);
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        Intent myService = new Intent(MainActivity.this, BackgroundSoundService.class);
        stopService(myService);

        SharedPreferences.Editor editor =sharedPreferences.edit();
        editor.remove("type");
        editor.clear();
        editor.commit();
        super.onDestroy();
    }
}

