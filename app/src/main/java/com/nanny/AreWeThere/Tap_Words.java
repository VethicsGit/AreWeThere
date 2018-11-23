package com.nanny.AreWeThere;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static com.nanny.AreWeThere.MainActivity.sharedPreferences;

public class Tap_Words extends AppCompatActivity  {


    TextView text1,text2,text3,text4,text5,text6,text7,text8,text9,text10,text11,text12,finding,countdown;
    DatabaseReference mFirebaseDatabase;

    int count=0;

    int isPlay=0;

    MediaPlayer player;

    private static int SPLASH_TIME_OUT = 6000;

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tap_words);
//        Animation animation;
//        animation = AnimationUtils.loadAnimation(getApplicationContext(),
//                R.animator.toucheffect);


        countdown=findViewById(R.id.countdown);



        if (getIntent().getExtras().getString("clicked")!=null) {

            AlertDialog.Builder builder = new AlertDialog.Builder(Tap_Words.this);
            LayoutInflater inflater = Tap_Words.this.getLayoutInflater();
            View dailog = inflater.inflate(R.layout.instruction_dailog, null);
            builder.setView(dailog);

            final AlertDialog dialog = builder.create();
            dialog.show();
            dialog.getWindow().setBackgroundDrawable(getResources().getDrawable(R.drawable.alert));

            Button play = dailog.findViewById(R.id.start_play);
            TextView instru = dailog.findViewById(R.id.instru);
            TextView insu = dailog.findViewById(R.id.ins);
            TextView tip = dailog.findViewById(R.id.tip);
            TextView ins2 = dailog.findViewById(R.id.ins2);
            Typeface customm = Typeface.createFromAsset(getAssets(), "DK Canoodle.ttf");
//                point.setTypeface(customm);
            instru.setTypeface(customm);
            play.setTypeface(customm);
            insu.setTypeface(customm);
            ins2.setTypeface(customm);
            tip.setTypeface(customm);


            play.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                isPlay = 1;

                    dialog.setCancelable(false);
                    dialog.setCanceledOnTouchOutside(false);
                    dialog.dismiss();

                    Typeface custom=Typeface.createFromAsset(getAssets(),"DK Canoodle.ttf");
                    player = MediaPlayer.create(Tap_Words.this, R.raw.loading);
                    player.setLooping(false); // Set looping
                    player.setVolume(100,100);
                    player.start();
                    countdown.setText("3");

                    Toast.makeText(Tap_Words.this, "Easy", Toast.LENGTH_SHORT).show();

                    countdown.setTypeface(custom);
                    CountDownTimer easycountDownTimer = new CountDownTimer(5000, 1000) {

                        @Override
                        public void onTick(long l) {
                            long seconds = l/1000;

                            int secoun = Integer.parseInt(String.valueOf(seconds));
                            switch (secoun){
                            case 4:
                                countdown.setText("3");
                                break;
                            case 3:
                                countdown.setText("2");
                                break;
                                case 2:
                                    countdown.setText("1");
                                    break;
                                case 1:
                                countdown.setText("Go");
                                break;
                        }
                        }

                        @Override
                        public void onFinish() {
                            Intent i = new Intent(Tap_Words.this,DisplayItem.class);
                            startActivity(i);
                            player.stop();
                        }
                    };
                    easycountDownTimer.start();
                }
            });
        }else if (getIntent().getExtras().getString("time")!=null){
                    Typeface custom=Typeface.createFromAsset(getAssets(),"DK Canoodle.ttf");

            player = MediaPlayer.create(Tap_Words.this, R.raw.loading);
            player.setLooping(false); // Set looping
            player.setVolume(100,100);
            player.start();
                    countdown.setTypeface(custom);
            CountDownTimer timecountDownTimer  = new CountDownTimer(5000, 1000) {
                        @Override
                        public void onTick(long l) {

                            long seconds = l/1000;

                            int secoun = Integer.parseInt(String.valueOf(seconds));
                            switch (secoun){
                                case 4:
                                    countdown.setText("3");
                                    break;
                                case 3:
                                    countdown.setText("2");
                                    break;
                                case 2:
                                    countdown.setText("1");
                                    break;
                                case 1:
                                    countdown.setText("Go");
                                    break;
                            }

                        }

                        @Override
                        public void onFinish() {
                            Intent i = new Intent(Tap_Words.this,TimerScreen.class);
                            i.putExtra("taptime","taptime");
                            startActivity(i);
                            player.stop();

                        }
                    };
                    timecountDownTimer.start();
        }

        mFirebaseDatabase = FirebaseDatabase.getInstance().getReference().child("Words");
        mFirebaseDatabase.keepSynced(true);

        Intent myService = new Intent(Tap_Words.this, BackgroundSoundService.class);
        startService(myService);

        Typeface custom=Typeface.createFromAsset(getAssets(),"DK Canoodle.ttf");

                 text1=findViewById(R.id.text1) ;
                 text2=findViewById(R.id.text2);
                 text3=findViewById(R.id.text3);
                 text4=findViewById(R.id.text4);
                 text5=findViewById(R.id.text5) ;
                 text6=findViewById(R.id.text6);
                 text7=findViewById(R.id.text7);
                 text8=findViewById(R.id.text8);
                text10=findViewById(R.id.text10);
                text11=findViewById(R.id.text11);
                text12=findViewById(R.id.text12);

               finding=findViewById(R.id.finding);
               finding.setTypeface(custom);

        text1.setTypeface(custom);
        text2.setTypeface(custom);
        text3.setTypeface(custom);
        text4.setTypeface(custom);
        text5.setTypeface(custom);
        text6.setTypeface(custom);
        text7.setTypeface(custom);
        text8.setTypeface(custom);
        text10.setTypeface(custom);
        text11.setTypeface(custom);
        text12.setTypeface(custom);
        finding.setTypeface(custom);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (isPlay == 0) {
            SharedPreferences.Editor editor =sharedPreferences.edit();
            editor.remove("type");
            editor.clear();
            editor.commit();
        }

//        player.stop();
    }
}

