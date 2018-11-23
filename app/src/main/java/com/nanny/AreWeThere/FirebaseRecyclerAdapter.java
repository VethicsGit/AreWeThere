package com.nanny.AreWeThere;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class FirebaseRecyclerAdapter extends RecyclerView.Adapter<FirebaseRecyclerAdapter.ViewHolder> {


    Context context;
    List<ItemGetterSetter>itemGetterSetters;

    public FirebaseRecyclerAdapter(List<ItemGetterSetter> itemGetterSetters) {

            this.itemGetterSetters=itemGetterSetters;
//            this.context=applicationContext;

        }

    @NonNull
    @Override
    public FirebaseRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_list_row,viewGroup,false);


        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;


    }

    @Override
    public void onBindViewHolder(@NonNull FirebaseRecyclerAdapter.ViewHolder viewHolder, int i) {

        ItemGetterSetter itemGetterSetter=itemGetterSetters.get(i);


            viewHolder.itemName.setText(itemGetterSetter.getName());

    }

    @Override
    public int getItemCount() {
        return itemGetterSetters.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView itemName,count;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);


                itemName=itemView.findViewById(R.id.itemNameTextView);
                count=itemView.findViewById(R.id.counttxt);
        }
    }
}
