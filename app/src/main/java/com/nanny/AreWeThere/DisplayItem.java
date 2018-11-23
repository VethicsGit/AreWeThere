package com.nanny.AreWeThere;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import static com.nanny.AreWeThere.MainActivity.sharedPreferences;

public class DisplayItem extends AppCompatActivity {

    static   RecyclerView recyclerView;
    ImageView closeSignImageView,soundImageView,soundImageView2;
    DatabaseReference mFirebaseDatabase;
    int item = 15;
    int random = (int) Math.floor(Math.random()*item);

    int tot=15;
    int rndo= (int) (Math.random() * tot);
    int us=Integer.parseInt(String.valueOf(rndo),tot);


    int random1 = new Random().nextInt(2);

   private static int countt=0;
   private  static int specialcount =0;
   private  static TextView point;
   static int p=0;
   static int easyCounter=0;
   static  Context context;
   static MediaPlayer player;
static MediaPlayer player2;

DataSnapshot dataSnapshot;

    static    MediaPlayer player1;
   static int firstrandom;
   static  int scondrandom;


   List<ItemGetterSetter> itemGetterSetters;

//   static FirebaseRecyclerAdapter<ItemGetterSetter, UsersViewHolder> firebaseRecyclerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_item);

        int min=1;
        int max=15;


        Random r = new Random();
        firstrandom = r.nextInt(max - min + 1)+min;
        scondrandom = r.nextInt(max - min + 1) + min;



//
//
//        if (firstrandom==scondrandom){
//            scondrandom = r.nextInt(max - min + 1) + min;
//
//            if (firstrandom==scondrandom){
//                scondrandom = r.nextInt(max - min + 1) + min;
//
//                if (firstrandom==scondrandom){
//                    scondrandom = r.nextInt(max - min + 1) + min;
//
//                    if (firstrandom==scondrandom){
//                        scondrandom = r.nextInt(max - min + 1) + min;
//
//                        if (firstrandom==scondrandom){
//                            scondrandom = r.nextInt(max - min + 1) + min;
//
//
//                        }
//                    }
//                }
//            }
//        }



        Intent myService = new Intent(DisplayItem.this, BackgroundSoundService.class);
        startService(myService);


        mFirebaseDatabase = FirebaseDatabase.getInstance().getReference().child("Words");
//        Query query=mFirebaseDatabase.orderByChild("Words").limitToLast(random+15);
        mFirebaseDatabase.keepSynced(true);


        mFirebaseDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {



//                int ads= (int) dataSnapshot.getChildrenCount();
//                int rnd = new Random().nextInt(ads);

                itemGetterSetters = new ArrayList<>();

                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){

                    ItemGetterSetter getterSetter=dataSnapshot1.getValue(ItemGetterSetter.class);
                    Toast.makeText(DisplayItem.this, ""+dataSnapshot.getChildrenCount(), Toast.LENGTH_SHORT).show();
                        String name1=getterSetter.getName();
                        itemGetterSetters.add(getterSetter);
                }

                com.nanny.AreWeThere.FirebaseRecyclerAdapter firebaseRecyclerAdapter = new com.nanny.AreWeThere.FirebaseRecyclerAdapter(itemGetterSetters);
                RecyclerView.LayoutManager layoutmanager = new LinearLayoutManager(DisplayItem.this);
                recyclerView.setLayoutManager(layoutmanager);

                recyclerView.setAdapter(firebaseRecyclerAdapter);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


//


        closeSignImageView=(ImageView)findViewById(R.id.closeSignImageView);
        recyclerView= (RecyclerView)findViewById(R.id.listRecyclerView2);
        soundImageView=(ImageView)findViewById(R.id.volImageView);
        soundImageView2=findViewById(R.id.volImageView2);
        point=(TextView)findViewById(R.id.point);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        closeSignImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i=new Intent(DisplayItem.this,MainActivity.class);
                startActivity(i);


                SharedPreferences.Editor editor =sharedPreferences.edit();
                editor.remove("type");
                editor.clear();
                editor.apply();
                editor.commit();

                countt=0;
            }
        });
        soundImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                soundImageView.setVisibility(View.INVISIBLE);
                soundImageView2.setVisibility(View.VISIBLE);

                Intent myService = new Intent(DisplayItem.this, BackgroundSoundService.class);
                startService(myService);
            }
        });
        soundImageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                soundImageView2.setVisibility(View.INVISIBLE);
                soundImageView.setVisibility(View.VISIBLE);

                Intent myService = new Intent(DisplayItem.this, BackgroundSoundService.class);
                stopService(myService);
            }
        });
    }

    int rn=Integer.parseInt(String.valueOf(random),15);
    Random r=new Random();

//    final SharedPreferences sharedPreferences = DisplayItem.this.getSharedPreferences("mypref", MODE_PRIVATE);
//    String count = sharedPreferences.getString("word", null);

    @Override
    protected void onResume() {
        super.onResume();

        Intent myService = new Intent(DisplayItem.this, BackgroundSoundService.class);
        startService(myService);
    }

        @Override
        protected void onPause() {
            Intent myService = new Intent(DisplayItem.this, BackgroundSoundService.class);
            stopService(myService);
            super.onPause();
        }


/*



        @Override protected void onStart () {
            super.onStart();
             firebaseRecyclerAdapter = new   FirebaseRecyclerAdapter<ItemGetterSetter, UsersViewHolder>(
                    ItemGetterSetter.class,
                    R.layout.item_list_row,
                    UsersViewHolder.class,
                    mFirebaseDatabase.orderByChild("Words").limitToLast(15)) {

                @Override
                protected void populateViewHolder(final UsersViewHolder viewHolder, ItemGetterSetter model, int position) {


                    viewHolder.setName(model.getName());
//                    viewHolder.setIsRecyclable(false);
//                    recyclerView.scrollToPosition(recyclerView.getAdapter().getItemCount());
                }

                @Override
                public long getItemId(int position) {
                    return position;
                }

                 @Override
                 public int getItemCount() {
                     return super.getItemCount();

                 }

                 @Override
                 public int getItemViewType(int position) {
                     return super.getItemViewType(position);
                 }
             };
            firebaseRecyclerAdapter.notifyDataSetChanged();
            recyclerView.setAdapter(firebaseRecyclerAdapter);

//            recyclerView.scrollToPosition(recyclerView.getAdapter().getItemCount());
        }
        public static class UsersViewHolder extends RecyclerView.ViewHolder {

            View mView;
            TextView itemName,itemcount,itemNameTextView2,position,counttxt;
            ImageView grayImageView,special_box;

            RelativeLayout back;

            public UsersViewHolder(@NonNull View itemView) {
                super(itemView);
                mView = itemView;
            }

            public void setName(final String name) {
                final Typeface custom = Typeface.createFromAsset(mView.getContext().getAssets(), "DK Canoodle.ttf");

                itemName = (TextView) mView.findViewById(R.id.itemNameTextView);
                itemName.setText(name);
                itemName.setTypeface(custom);

                itemNameTextView2=mView.findViewById(R.id.itemNameTextView2);
                itemNameTextView2.setText(name);
                itemNameTextView2.setTypeface(custom);

                grayImageView=mView.findViewById(R.id.grayImageView);
                back=mView.findViewById(R.id.back);
                itemcount=mView.findViewById(R.id.counttxt);
                special_box=mView.findViewById(R.id.special_box);
                itemcount.setTypeface(custom);
                position=mView.findViewById(R.id.position);
//                counttxt=mView.findViewById(R.id.counttxt);

                final int cu=getAdapterPosition();


                SharedPreferences sharedPreferences = mView.getContext().getSharedPreferences("mypref",MODE_PRIVATE);
                SharedPreferences.Editor editor= sharedPreferences.edit();
                String key=sharedPreferences.getString(itemName.getText().toString(), null);

                    if (key != null) {
                        itemcount.setText(key);

                        if (cu==5) {
                            back.setBackground(mView.getResources().getDrawable(R.drawable.yellow_box));
                            itemNameTextView2.setTextColor(Color.parseColor("#FFE76200"));
                            itemcount.setTextColor(Color.parseColor("#FFE76200"));
                            special_box.setImageDrawable(mView.getResources().getDrawable(R.drawable.crown_gold));;
                        }
                        else if (cu==10){
                            back.setBackground(mView.getResources().getDrawable(R.drawable.yellow_box));
                            itemNameTextView2.setTextColor(Color.parseColor("#FFE76200"));
                            itemcount.setTextColor(Color.parseColor("#FFE76200"));
                            special_box.setImageDrawable(mView.getResources().getDrawable(R.drawable.crown_gold));
                        } else {
                            back.setBackground(mView.getResources().getDrawable(R.drawable.green_box));
                            itemName.setTextColor(Color.parseColor("#00FE7F"));
                            itemcount.setTextColor(Color.parseColor("#00FE7F"));
                        }
                    } else {
                        itemcount.setText("X");
//                        Random random = new Random();
//                        int rnd=random.nextInt(15 )+0;
//                        if (cu==rnd){
//
//                            special_box.setVisibility(View.VISIBLE);
//                            back.setBackground(mView.getResources().getDrawable(R.drawable.grey_box));
//                            itemName.setVisibility(View.INVISIBLE);
//                            itemNameTextView2.setVisibility(View.VISIBLE);
//                            itemNameTextView2.setText(name);
//
//                        }
//                        else {
//                            back.setBackground(mView.getResources().getDrawable(R.drawable.grey_box));
//                            itemName.setTextColor(Color.parseColor("#777777"));
//                            itemcount.setTextColor(Color.parseColor("#777777"));
//                        }


                        if (cu==5){
                            special_box.setVisibility(View.VISIBLE);
                            back.setBackground(mView.getResources().getDrawable(R.drawable.grey_box));
                            itemName.setVisibility(View.INVISIBLE);
                            itemNameTextView2.setVisibility(View.VISIBLE);
                            itemNameTextView2.setText(name);
                        }
                        else if (cu==10){
                            special_box.setVisibility(View.VISIBLE);
                            back.setBackground(mView.getResources().getDrawable(R.drawable.grey_box));
                            itemName.setVisibility(View.INVISIBLE);
                            itemNameTextView2.setVisibility(View.VISIBLE);
                            itemNameTextView2.setText(name);
                        } else {
                            back.setBackground(mView.getResources().getDrawable(R.drawable.grey_box));
                            itemName.setTextColor(Color.parseColor("#777777"));
                            itemcount.setTextColor(Color.parseColor("#777777"));
                        }
                    }

                itemName.setOnClickListener(new View.OnClickListener() {
                    int count=1;
                    int f;
                    @Override
                        public void onClick(View view) {


                  player1 = MediaPlayer.create(view.getContext(), R.raw.success);

                        player1.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                            @Override
                            public void onCompletion(MediaPlayer mediaPlayer) {
                                mediaPlayer.reset();
                                mediaPlayer.release();
//                                player = null;

                            }
                        });

//                        player.setLooping(false); // Set looping
//                        player.setVolume(100,100);
                        player1.start();


                        back.setBackground(view.getResources().getDrawable(R.drawable.green_box));
                        itemName.setTextColor(Color.parseColor("#00FE7F"));
                        itemcount.setTextColor(Color.parseColor("#00FE7F"));

                        if(itemcount.getText().toString().equals("X")){
                            itemcount.setText(String.valueOf(count));
                            easyCounter++;
                        }
                        else{
                            String c=itemcount.getText().toString();
                            f=Integer.parseInt(c);
                            f++;
                            itemcount.setText(String.valueOf(f));
                        }
                        countt++;
                        point.setText(String.valueOf(countt));


                        SharedPreferences sharedPreferences = mView.getContext().getSharedPreferences("mypref",MODE_PRIVATE);
                        SharedPreferences.Editor editor= sharedPreferences.edit();
                        editor.putString(itemName.getText().toString(),itemcount.getText().toString());
                        editor.commit();



                        if(easyCounter==15){
                            easyCounter=0;
                            Intent i=new Intent(mView.getContext(),MainActivity.class);
                            Bundle bundle=new Bundle();
                            bundle.putInt("totalpoint",countt);
                            i.putExtra("totalpoint",countt);
                            mView.getContext().startActivity(i);

                             sharedPreferences = mView.getContext().getSharedPreferences("mypref",MODE_PRIVATE);
                             editor= sharedPreferences.edit();
                            editor.putString("totalpoint",String.valueOf(countt));
                            editor.commit();

//                            player.stop();

                            player = MediaPlayer.create(view.getContext(), R.raw.gameoverhappy);
                            player.setLooping(false); // Set looping
                            player.setVolume(100,100);
                            player.start();



                            countt=0;



                                }

                    }
                });

                itemNameTextView2.setOnClickListener(new View.OnClickListener() {
                    int special=5;
                    int f;

                    @Override
                    public void onClick(View view) {

                           player2 = MediaPlayer.create(view.getContext(), R.raw.successgold);
                            player2.setLooping(false); // Set looping
                            player2.setVolume(100,100);
                            player2.start();

                            back.setBackground(view.getResources().getDrawable(R.drawable.yellow_box));
                            itemNameTextView2.setTextColor(Color.parseColor("#FFE76200"));
                            itemcount.setTextColor(Color.parseColor("#FFE76200"));
                            special_box.setImageDrawable(view.getResources().getDrawable(R.drawable.crown_gold));


                            if(itemcount.getText().toString().equals("X")){
                            easyCounter++;
                                itemcount.setText(String.valueOf(special));
                            } else {
                                String c=itemcount.getText().toString();
                                f=Integer.parseInt(c);
                                f = f + 5;
                                itemcount.setText(String.valueOf(f));
                            }
                            countt = countt + 5;
                            point.setText(String.valueOf(countt));


                        SharedPreferences sharedPreferences = mView.getContext().getSharedPreferences("mypref",MODE_PRIVATE);
                        SharedPreferences.Editor editor= sharedPreferences.edit();
                        editor.putString(itemName.getText().toString(),itemcount.getText().toString());
                        editor.commit();



                        if(easyCounter==15){
                            easyCounter=0;

                            Intent i=new Intent(mView.getContext(),MainActivity.class);
                            Bundle bundle=new Bundle();
                            bundle.putInt("totalpoint",countt);
                            i.putExtra("totalpoint",countt);
//                            i.putExtras(bundle);
                            mView.getContext().startActivity(i);


                            sharedPreferences = mView.getContext().getSharedPreferences("mypref",MODE_PRIVATE);
                            editor= sharedPreferences.edit();
                            editor.putString("totalpoint",String.valueOf(countt));
                            editor.commit();

                            player.stop();

                            player = MediaPlayer.create(view.getContext(), R.raw.gameoverhappy);
                            player.setLooping(false); // Set looping
                            player.setVolume(100,100);
                            player.start();



                            countt=0;




                        }
                    }
                    });
            }
    }
*/

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Intent i = new Intent(DisplayItem.this,MainActivity.class);
        startActivity(i);

//        player1.stop();
//        player2.stop();


    }
}
