package com.example.swapshop2;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Upload_Item_Adapter extends RecyclerView.Adapter<Upload_Item_Adapter.ViewHolder> {
    //Initialize variable
    ArrayList<Uri> arrayList;

    //Create constructer
    public Upload_Item_Adapter(ArrayList<Uri> arrayList){
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Initialize view
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_main, parent, false);
        //Pass holder view
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //Print image using uri
        holder.ivImage.setImageURI(arrayList.get(position));
    }

    @Override
    public int getItemCount() {
        //Pass list size
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        //Initialize variable
        ImageView ivImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            //Assign variable
            ivImage = itemView.findViewById(R.id.iv_image);
        }
    }
}
