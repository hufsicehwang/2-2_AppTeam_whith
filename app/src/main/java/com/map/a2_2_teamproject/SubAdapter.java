package com.map.a2_2_teamproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SubAdapter extends RecyclerView.Adapter<SubAdapter.CustomViewHolder> {

    private ArrayList<SubData> arrayList;
    private Context context;

    public SubAdapter(ArrayList<SubData> arrayList) {
        this.arrayList = arrayList;
    }
    public SubAdapter(Context context, ArrayList<SubData> arrayList){
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public SubAdapter.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sub_items, parent, false);
        CustomViewHolder holder = new CustomViewHolder(view);


        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final SubAdapter.CustomViewHolder holder, int position) {
        holder.profile.setImageResource(arrayList.get(position).getIv_profile());
        holder.name.setText(arrayList.get(position).getTv_name());
        holder.content.setText(arrayList.get(position).getTv_content());


    }

    @Override
    public int getItemCount() {
        return (null != arrayList ? arrayList.size() : 0);
    }

    public void remove(int position) {
        try {
            arrayList.remove(position);
            notifyItemRemoved(position);
        } catch (IndexOutOfBoundsException ex) {
            ex.printStackTrace();
        }
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {

        protected ImageView profile;
        protected TextView name;
        protected TextView content;

        public CustomViewHolder(View itemView) {
            super(itemView);
            this.profile = (ImageView) itemView.findViewById(R.id.profile);
            this.name = (TextView) itemView.findViewById(R.id.title);
            this.content = (TextView) itemView.findViewById(R.id.adress);
        }
    }
}

