package com.example.arkin.vkclient.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.arkin.vkclient.GetUsersTask;
import com.example.arkin.vkclient.R;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.vk.sdk.api.model.VKApiPost;
import com.vk.sdk.api.model.VKApiUserFull;
import com.vk.sdk.api.model.VKList;
import com.vk.sdk.api.model.VKPostArray;

public class RecyclerAdapterFriend extends RecyclerView.Adapter<RecyclerAdapterFriend.ViewHolder>{
    Context ctx;
    ImageLoader imageLoader;


    VKList obj;

        public RecyclerAdapterFriend(Context ctx, VKList friends)
        {
            //super(ctx,R.layout.recycler_item_wall);
            this.obj=friends;
            this.ctx=ctx;
            imageLoader.getInstance();

        }
        public RecyclerAdapterFriend(Context ctx, VKPostArray posts, String owner_id)
        {
            //super(ctx,R.layout.recycler_item_wall);
            this.obj=posts;
            this.ctx=ctx;
           imageLoader.getInstance();

        }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v=LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item_friend,parent,false);
        ViewHolder viewHolder=new ViewHolder(v);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final VKApiUserFull p= (VKApiUserFull) obj.get(position);
        //holder.imageWall.setVisibility(View.VISIBLE);



       holder.textFullName.setText(p.first_name+ " " + p.last_name);
       // holder.textDatePost.setText(String.valueOf(p.date));

       new GetUsersTask(holder.textFullName,holder.imageFriend,ctx,imageLoader).execute(String.valueOf(p.id));


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

        TextView textFullName;
        ImageView imageFriend;
        ImageView imageIsOnline;

        public ViewHolder(View itemView) {
            super(itemView);

            textFullName=(TextView)itemView.findViewById(R.id.item_friend_fullname);
            imageFriend=(ImageView)itemView.findViewById(R.id.item_friend_image);
            imageIsOnline=(ImageView)itemView.findViewById(R.id.item_friend_is_online);

        }
    }


}
