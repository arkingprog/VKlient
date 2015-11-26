package com.example.arkin.vkclient.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.arkin.vkclient.Application;
import com.example.arkin.vkclient.GetUsersTask;
import com.example.arkin.vkclient.R;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.vk.sdk.api.model.VKApiPost;
import com.vk.sdk.api.model.VKPostArray;

import java.util.ArrayList;

public class RecyclerAdapterWall extends RecyclerView.Adapter<RecyclerAdapterWall.ViewHolder>{
    Context ctx;
    ImageLoader imageLoader;


    VKPostArray obj;

        public RecyclerAdapterWall(Context ctx, VKPostArray posts)
        {
            //super(ctx,R.layout.recycler_item_wall);
            this.obj=posts;
            this.ctx=ctx;
            imageLoader.getInstance();

        }
        public RecyclerAdapterWall(Context ctx, VKPostArray posts,String owner_id)
        {
            //super(ctx,R.layout.recycler_item_wall);
            this.obj=posts;
            this.ctx=ctx;
           imageLoader.getInstance();

        }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v=LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item_wall,parent,false);
        ViewHolder viewHolder=new ViewHolder(v);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final VKApiPost p=obj.get(position);

        holder.textPost.setText(p.text);
        holder.textDatePost.setText(String.valueOf(p.date));
        //new GetUsersTask(holder.textNamePost,holder.imageWall,ctx).execute(String.valueOf(p.from_id));
        new GetUsersTask(holder.textNamePost,holder.imageWall,ctx,imageLoader).execute(String.valueOf(p.from_id));


    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public int getItemCount() {
        return obj.size();
    }
    public static class ViewHolder  extends RecyclerView.ViewHolder{
        CardView cv;
        TextView textNamePost;
        TextView textDatePost;
        TextView textPost;
        ImageView imageWall;
        public ViewHolder(View itemView) {
            super(itemView);
            cv=(CardView) itemView.findViewById(R.id.card_view_wall);
            textDatePost=(TextView)itemView.findViewById(R.id.textDatePost);
            textNamePost=(TextView)itemView.findViewById(R.id.textNamePost);
            textPost=(TextView)itemView.findViewById(R.id.textPost);
            imageWall=(ImageView)itemView.findViewById(R.id.imageWall);

        }
    }


}
