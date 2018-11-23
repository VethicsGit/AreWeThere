package com.nanny.AreWeThere;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Random;

import static com.nanny.AreWeThere.MainActivity.sharedPreferences;

public class TimerScreen extends AppCompatActivity {


    RecyclerView recyclerView;
    DatabaseReference mFirebaseDatabase;
    private static int countpoint = 0;
    private static int specialcount = 0;
    ImageView soundImageView, time_paushImageview, time_closeSignImageView,soundImageView2;
    static   TextView  time_count;
    TextView cal;
    Button time_play;
    static int easycounter=0;
    EditText time_text;
    private  static TextView time_point;

    int isPlay=0;
    static int firstrandom;
    static  int scondrandom;

    private int mMinute = 60;
    private int mHour;
     static MediaPlayer player;
    private static CountDownTimer countDownTimer;
    private  long timeleft=600000;
    private boolean timerunning;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.timer_screen);


        int min=1;
        int max=15;



        Random r = new Random();
        firstrandom = r.nextInt(max - min + 1)+min;
        scondrandom = r.nextInt(max - min + 1) + min;



        if (firstrandom==scondrandom){
            scondrandom = r.nextInt(max - min + 1) + min;

            if (firstrandom==scondrandom){
                scondrandom = r.nextInt(max - min + 1) + min;

                if (firstrandom==scondrandom){
                    scondrandom = r.nextInt(max - min + 1) + min;

                    if (firstrandom==scondrandom){
                        scondrandom = r.nextInt(max - min + 1) + min;

                        if (firstrandom==scondrandom){
                            scondrandom = r.nextInt(max - min + 1) + min;


                        }
                    }
                }
            }
        }


        soundImageView=(ImageView)findViewById(R.id.time_volImageView);
        soundImageView2=(ImageView)findViewById(R.id.time_volImageView2);
        time_count = findViewById(R.id.time_count);
        time_point = findViewById(R.id.time_point);
//        time_paushImageview = findViewById(R.id.time_paushImageview);
        time_closeSignImageView = findViewById(R.id.time_closeSignImageView);
        time_text = findViewById(R.id.time_text);
        time_play = findViewById(R.id.time_play);
        cal=findViewById(R.id.cal);





        recyclerView = (RecyclerView) findViewById(R.id.time_recycle);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        final Typeface  customm=Typeface.createFromAsset(getAssets(),"DK Canoodle.ttf");
        time_text.setTypeface(customm);
        time_play.setTypeface(customm);
        cal.setTypeface(customm);


        Intent myService = new Intent(TimerScreen.this, BackgroundSoundService.class);
        startService(myService);

        mFirebaseDatabase = FirebaseDatabase.getInstance().getReference().child("Words");
        mFirebaseDatabase.keepSynced(true);






        time_text.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(!b){
                    hideKeyboard(view);
                }
            }
        });
        time_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String time = time_text.getText().toString();

                if (time.isEmpty()){
                    AlertDialog.Builder builder= new AlertDialog.Builder(TimerScreen.this);
                    LayoutInflater inflater=TimerScreen.this.getLayoutInflater();
                    final View dailog=inflater.inflate(R.layout.time_alert,null);
                    builder.setView(dailog);

                    TextView alert_text=dailog.findViewById(R.id.alert_message);
                    Button alert=dailog.findViewById(R.id.time_ok);
                    alert_text.setTypeface(customm);
                    alert.setTypeface(customm);

                    final AlertDialog alertDialog=builder.create();
                    alertDialog.show();

                    alert.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            alertDialog.dismiss();
                        }
                    });
                }else {
                    Intent intent1= new Intent(getApplicationContext(),Tap_Words.class);
                    intent1.putExtra("time","time");
                    startActivity(intent1);
                    isPlay = 1;
                    start();
                }
            }
        });
        time_closeSignImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i=new Intent(TimerScreen.this,MainActivity.class);
                startActivity(i);


                SharedPreferences.Editor editor =sharedPreferences.edit();
                editor.remove("type");
                editor.clear();
                editor.apply();
                editor.commit();
                countpoint=0;

//                countDownTimer.cancel();
//                player1.stop();
            }
        });

        soundImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                soundImageView.setVisibility(View.INVISIBLE);
                soundImageView2.setVisibility(View.VISIBLE);

                 Intent myService = new Intent(TimerScreen.this, BackgroundSoundService.class);
                 startService(myService);
            }
        });

        soundImageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                 soundImageView2.setVisibility(View.INVISIBLE);
                 soundImageView.setVisibility(View.VISIBLE);

                 Intent myService = new Intent(TimerScreen.this, BackgroundSoundService.class);
                 stopService(myService);
            }
        });




        if (getIntent().getExtras().getString("main")!=null) {

            android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(TimerScreen.this);
            LayoutInflater inflater = TimerScreen.this.getLayoutInflater();
            View dailog = inflater.inflate(R.layout.time_instruction, null);
            builder.setView(dailog);

            final android.support.v7.app.AlertDialog dialog = builder.create();
            dialog.show();
            dialog.getWindow().setBackgroundDrawable(getResources().getDrawable(R.drawable.alert));
            Button play = dailog.findViewById(R.id.time_start_play);
            TextView instru = dailog.findViewById(R.id.time_instru);
            TextView insu = dailog.findViewById(R.id.time_ins);
            TextView tip = dailog.findViewById(R.id.time_tip);
            Typeface custom = Typeface.createFromAsset(getAssets(), "DK Canoodle.ttf");
//        point.setTypeface(custom);
            instru.setTypeface(custom);
            play.setTypeface(custom);
            insu.setTypeface(custom);
            tip.setTypeface(custom);


            play.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    dialog.setCancelable(false);
                    dialog.setCanceledOnTouchOutside(false);
                    dialog.dismiss();


//
                }
//
            });


        }else if (getIntent().getExtras().getString("taptime")!=null){

            time_play.setVisibility(View.INVISIBLE);
            time_text.setVisibility(View.INVISIBLE);
            cal.setVisibility(View.INVISIBLE);
            recyclerView.setVisibility(View.VISIBLE);
//            start();



        }

    }

    @Override
    protected void onResume() {
        super.onResume();

        Intent myService = new Intent(TimerScreen.this, BackgroundSoundService.class);
        startService(myService);
    }

    @Override
    protected void onPause() {
        Intent myService = new Intent(TimerScreen.this, BackgroundSoundService.class);
        stopService(myService);

        Intent intent1=new Intent(TimerScreen.this,BackgroundSoundServiceTicking.class);
        stopService(intent1);

        super.onPause();
    }

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }



    private void start() {


        String time=time_text.getText().toString();
        int count = Integer.parseInt(time) * 60000;


        countDownTimer = new CountDownTimer(count,1000) {
            MediaPlayer player1 = new MediaPlayer();

            Typeface  customm=Typeface.createFromAsset(getAssets(),"DK Canoodle.ttf");
            @Override
            public void onTick(long l) {
                time_count.setText((l / 60000)+":"+(l % 60000 / 1000));
                time_count.setTypeface(customm);


                String time=time_count.getText().toString();

                String[] units =time.split(":");
                int minutes = Integer.parseInt(units[0]);
                int second = Integer.parseInt(units[1]);
                int duration = 60 * minutes  + second;

                Log.e("time",""+duration);
                if (duration <= 10){

                    player1 = MediaPlayer.create(TimerScreen.this, R.raw.ticking_new);
                    player1.setLooping(false); // Set looping
                    player1.setVolume(100,100);
                    player1.start();


//                    Intent intent1=new Intent(TimerScreen.this,BackgroundSoundServiceTicking.class);
//                    startService(intent1);
                }




            }


            @Override
            public void onFinish() {
                Intent i=new Intent(TimerScreen.this,MainActivity.class);
                Bundle bundle=new Bundle();
                bundle.putInt("timetotalpoint",countpoint);
                i.putExtras(bundle);
                startActivity(i);
                countpoint=0;

                String time=time_count.getText().toString();

                String[] units =time.split(":");
                int minutes = Integer.parseInt(units[0]);
                int second = Integer.parseInt(units[1]);
                int duration = 60 * minutes  + second;



                SharedPreferences sharedPreferences = TimerScreen.this.getSharedPreferences("mypref",MODE_PRIVATE);
                SharedPreferences.Editor editor= sharedPreferences.edit();
                Log.e("point",""+countpoint);
                editor.putString("timetotalpoint",String.valueOf(time_point.getText().toString()));
                editor.putString("duration","0");
                editor.commit();


                player = MediaPlayer.create(TimerScreen.this, R.raw.gameoversad);


                player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mediaPlayer) {

                        mediaPlayer.reset();
                        mediaPlayer.release();
                        player = null;

                    }
                });
//                    player.setLooping(false); // Set looping
//                    player.setVolume(100,100);
                player.start();
            }
        };
        countDownTimer.start();



    }



    @Override
    protected void onStart() {
            super.onStart();
//        start();
        com.firebase.ui.database.FirebaseRecyclerAdapter<ItemGetterSetter,UsersViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<ItemGetterSetter,UsersViewHolder>(
                ItemGetterSetter.class,
                R.layout.item_list_row,
                UsersViewHolder.class,
                mFirebaseDatabase.orderByChild("Words").limitToLast(15)) {

            @Override
            protected void populateViewHolder(UsersViewHolder viewHolder, ItemGetterSetter model, int position) {
                viewHolder.setName(model.getName());
//                viewHolder.setIsRecyclable(false);
            }
        };
        recyclerView.setAdapter(firebaseRecyclerAdapter);
    }

    public static class UsersViewHolder extends RecyclerView.ViewHolder {

        View mView;
        TextView itemcount,itemName1, itemNameTextView3;
        ImageView grayImageView,special_box;
        RelativeLayout back;

        public UsersViewHolder(@NonNull View itemView) {
            super(itemView);
            mView = itemView;
        }

        public void setName(final String name) {
            Typeface custom = Typeface.createFromAsset(mView.getContext().getAssets(), "DK Canoodle.ttf");


            itemName1 =mView.findViewById(R.id.itemNameTextView);
            itemName1.setText(name);
            itemName1.setTypeface(custom);

            itemNameTextView3= mView.findViewById(R.id.itemNameTextView2);
            itemNameTextView3.setText(name);
            itemNameTextView3.setTypeface(custom);

            grayImageView = mView.findViewById(R.id.grayImageView);
            back = mView.findViewById(R.id.back);
            special_box = mView.findViewById(R.id.special_box);

            itemcount = mView.findViewById(R.id.counttxt);
            itemcount.setTypeface(custom);

            int cu = getAdapterPosition();



            SharedPreferences sharedPreferences = mView.getContext().getSharedPreferences("mypref",MODE_PRIVATE);
            SharedPreferences.Editor editor= sharedPreferences.edit();
            String key=sharedPreferences.getString(itemName1.getText().toString(), null);

            if (key != null) {
                itemcount.setText(key);

                if (cu==5) {
                    back.setBackground(mView.getResources().getDrawable(R.drawable.yellow_box));
                    itemNameTextView3.setTextColor(Color.parseColor("#FFE76200"));
                    itemcount.setTextColor(Color.parseColor("#FFE76200"));
                    special_box.setImageDrawable(mView.getResources().getDrawable(R.drawable.crown_gold));;

                }
                else if (cu==10){
                    back.setBackground(mView.getResources().getDrawable(R.drawable.yellow_box));
                    itemNameTextView3.setTextColor(Color.parseColor("#FFE76200"));
                    itemcount.setTextColor(Color.parseColor("#FFE76200"));
                    special_box.setImageDrawable(mView.getResources().getDrawable(R.drawable.crown_gold));
                } else {

                    back.setBackground(mView.getResources().getDrawable(R.drawable.green_box));
                    itemName1.setTextColor(Color.parseColor("#00FE7F"));
                    itemcount.setTextColor(Color.parseColor("#00FE7F"));

                }
            } else {
                itemcount.setText("X");

                if (cu == 5) {
                    special_box.setVisibility(View.VISIBLE);
                    back.setBackground(mView.getResources().getDrawable(R.drawable.grey_box));
                    itemName1.setVisibility(View.INVISIBLE);
                    itemNameTextView3.setVisibility(View.VISIBLE);
                    itemNameTextView3.setText(name);
                } else if (cu == 10) {
                    special_box.setVisibility(View.VISIBLE);
                    back.setBackground(mView.getResources().getDrawable(R.drawable.grey_box));
                    itemName1.setVisibility(View.INVISIBLE);
                    itemNameTextView3.setVisibility(View.VISIBLE);
                    itemNameTextView3.setText(name);
                } else {
                    back.setBackground(mView.getResources().getDrawable(R.drawable.grey_box));
                    itemName1.setTextColor(Color.parseColor("#777777"));
                    itemcount.setTextColor(Color.parseColor("#777777"));

                }
            }


            itemName1.setOnClickListener(new View.OnClickListener() {

                int countitem = 1;
                int f;
                @Override
                public void onClick(View view) {


                    player = MediaPlayer.create(view.getContext(), R.raw.success);


                    player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mediaPlayer) {

                            mediaPlayer.reset();
                            mediaPlayer.release();
                            player = null;

                        }
                    });
//                    player.setLooping(false); // Set looping
//                    player.setVolume(100,100);
                    player.start();



                    back.setBackground(view.getResources().getDrawable(R.drawable.green_box));
                    itemName1.setTextColor(Color.parseColor("#00FE7F"));
                    itemcount.setTextColor(Color.parseColor("#00FE7F"));

                    if(itemcount.getText().toString().equals("X")){
                        itemcount.setText(String.valueOf(countitem));
                        easycounter++;
                    } else{
                        String c=itemcount.getText().toString();
                        f=Integer.parseInt(c);
                        f++;
                        itemcount.setText(String.valueOf(f));
                    }
                    countpoint++;
                    time_point.setText(String.valueOf(countpoint));

                    SharedPreferences sharedPreferences = mView.getContext().getSharedPreferences("mypref",MODE_PRIVATE);
                    SharedPreferences.Editor editor= sharedPreferences.edit();
                    editor.putString(itemName1.getText().toString(),itemcount.getText().toString());
                    editor.commit();



                    if(easycounter==15) {
                        easycounter = 0;

                        String time=time_count.getText().toString();

                        String[] units =time.split(":");
                        int minutes = Integer.parseInt(units[0]);
                        int second = Integer.parseInt(units[1]);
                        int duration = 60 * minutes  + second;


                        Intent i = new Intent(view.getContext(), MainActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putInt("timetotalpoint", countpoint);
                        bundle.putInt("durtion",duration);
                        i.putExtras(bundle);
                        view.getContext().startActivity(i);


                        sharedPreferences = mView.getContext().getSharedPreferences("mypref",MODE_PRIVATE);
                         editor= sharedPreferences.edit();
                        Log.e("point",""+countpoint);
                        editor.putString("timetotalpoint",String.valueOf(countpoint));
                        editor.putString("duration",String.valueOf(duration));
                        editor.commit();


                        player.stop();

                        player = MediaPlayer.create(view.getContext(), R.raw.gameoverhappy);
                        player.setLooping(false); // Set looping
                        player.setVolume(100,100);
                        player.start();

                        countpoint = 0;

                        countDownTimer.cancel();

                    }

                }
            });

            itemNameTextView3.setOnClickListener(new View.OnClickListener() {

                int special = 5;
                int f;

                @Override
                public void onClick(View view) {
                    int cup = getAdapterPosition();

                    MediaPlayer player;
                    player = MediaPlayer.create(view.getContext(), R.raw.successgold);
                    player.setLooping(false); // Set looping
                    player.setVolume(100,100);
                    player.start();

                    back.setBackground(view.getResources().getDrawable(R.drawable.yellow_box));
                    itemNameTextView3.setTextColor(Color.parseColor("#FFE76200"));
                    itemcount.setTextColor(Color.parseColor("#FFE76200"));
                    special_box.setImageDrawable(view.getResources().getDrawable(R.drawable.crown_gold));

                    if(itemcount.getText().toString().equals("X")){
                        easycounter++;
                        itemcount.setText(String.valueOf(special));
                    } else {
                        String c=itemcount.getText().toString();
                        f=Integer.parseInt(c);
                        f = f + 5;
                        itemcount.setText(String.valueOf(f));
                    }



                    countpoint=countpoint+5;
                    time_point.setText(String.valueOf(countpoint));



                    SharedPreferences sharedPreferences = mView.getContext().getSharedPreferences("mypref",MODE_PRIVATE);
                    SharedPreferences.Editor editor= sharedPreferences.edit();
                    editor.putString(itemNameTextView3.getText().toString(),itemcount.getText().toString());
                    editor.commit();

                    if(easycounter==15) {
                        easycounter = 0;

                        String time=time_count.getText().toString();

                        String[] units =time.split(":");
                        int minutes = Integer.parseInt(units[0]);
                        int second = Integer.parseInt(units[1]);
                        int duration = 60 * minutes  + second;


                        Intent i = new Intent(view.getContext(), MainActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putInt("timetotalpoint", countpoint);
                        bundle.putInt("durtion",duration);
                        i.putExtras(bundle);
                        view.getContext().startActivity(i);


                         sharedPreferences = mView.getContext().getSharedPreferences("mypref",MODE_PRIVATE);
                      editor= sharedPreferences.edit();
                        Log.e("point",""+countpoint);
                        editor.putString("timetotalpoint",String.valueOf(countpoint));
                        editor.putString("duration",String.valueOf(duration));
                        editor.commit();

                        player.stop();
                        player = MediaPlayer.create(view.getContext(), R.raw.gameoverhappy);
                        player.setLooping(false); // Set looping
                        player.setVolume(100,100);
                        player.start();

                        countpoint = 0;
                        countDownTimer.cancel();

                    }
                }
            });
        }
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (isPlay == 0) {
            SharedPreferences.Editor editor =sharedPreferences.edit();
            editor.remove("type");
            editor.clear();
            editor.commit();

            time_count.setText("00");
            countDownTimer.cancel();


//            player.stop();
        }

        Intent i = new Intent(TimerScreen.this,MainActivity.class);
        startActivity(i);
    }
}

