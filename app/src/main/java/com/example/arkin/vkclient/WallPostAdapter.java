package com.example.arkin.vkclient;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.arkin.vkclient.GetUsersTask;
import com.example.arkin.vkclient.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.vk.sdk.api.VKApi;
import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;
import com.vk.sdk.api.methods.VKApiWall;
import com.vk.sdk.api.model.VKApiPost;
import com.vk.sdk.api.model.VKApiUser;
import com.vk.sdk.api.model.VKList;
import com.vk.sdk.api.model.VKPostArray;

import java.sql.Time;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * Created by arkin on 22.11.2015.
 */
public class WallPostAdapter extends ArrayAdapter {
    Context ctx;
    LayoutInflater layoutInflater;
    VKPostArray obj;


    public WallPostAdapter(Context context,VKPostArray posts){
        super(context, R.layout.list_item_wall);
        ctx=context;
        obj=posts;

        layoutInflater=(LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }
    @Override
    public int getCount() {
        return obj.size();
    }

    @Override
    public String getItem(int position) {

        return obj.get(position).text;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        View view = convertView;
        if (view == null) {
            view = layoutInflater.inflate(R.layout.list_item_wall, parent, false);
            holder=new ViewHolder();
            holder.imageView=(ImageView) view.findViewById(R.id.imageWall);
            holder.textName= (TextView) view.findViewById(R.id.textNamePost);
            holder.textPost= (TextView) view.findViewById(R.id.textPost);
            holder.textDate= (TextView) view.findViewById(R.id.textDatePost);
            holder.btn=(Button) view.findViewById(R.id.btnLikeWall);
            view.setTag(holder);
        }
        else
        {
            holder=(ViewHolder) view.getTag();
        }
        final VKApiPost p=obj.get(position);

       final String name="test";
               // name=getUser(p.from_id);

        //holder.textName.setText(name);
        holder.textPost.setText(p.text);
        holder.textDate.setText(String.valueOf(p.date));
        holder.btn.setFocusable(false);
        holder.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               /* VKParameters params=VKParameters.from("type","post","item_id",p.id);
                VKRequest req=new VKRequest("likes.add",params);
                req.executeWithListener(new VKRequest.VKRequestListener() {
                    @Override
                    public void onComplete(VKResponse response) {
                        super.onComplete(response);
                    }
                });*/
                Log.v("test",String.valueOf(p.to_id));


            }
        });

        /*new GetUsersTask(holder.textName,
                holder.imageView,
                getContext())
                .execute(String.valueOf(p.from_id));
*/

      /* ImageLoader imageLoader=ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(getContext()));

        DisplayImageOptions options=new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2)
                .build();
        imageLoader.displayImage(getUserPhoto(p.from_id), (ImageView) view.findViewById(R.id.imageWall), options);
*/

       // Log.v("test",q.first_name);



        return view;
    }

    static class ViewHolder {
        public ImageView  imageView;
        public TextView textName;
        public TextView textDate;
        public TextView textPost;
        public Button btn;
    }
    public String  getUser(Integer id)
    {
        final VKApiUser[] user = new VKApiUser[1];
        VKRequest request= VKApi.users().get(VKParameters.from(VKApiConst.USER_ID,id.toString()));
        request.setPreferredLang("ru");
        request.executeSyncWithListener(new VKRequest.VKRequestListener() {
            @Override
            public void onComplete(VKResponse response) {
                super.onComplete(response);
                VKList<VKApiUser> us = ((VKList<VKApiUser>) response.parsedModel);
                user[0] = us.get(0);
            }
        });
        return user[0].first_name;
    }

    public String getUserPhoto(Integer id)
    {
        final VKApiUser[] user = new VKApiUser[1];
        VKRequest request= VKApi.users().get(VKParameters.from(VKApiConst.USER_ID,id.toString(),VKApiConst.FIELDS,"photo_100"));
        request.setPreferredLang("ru");
        request.executeSyncWithListener(new VKRequest.VKRequestListener() {
            @Override
            public void onComplete(VKResponse response) {
                super.onComplete(response);
                VKList<VKApiUser> us = ((VKList<VKApiUser>) response.parsedModel);
                user[0] =us.get(0);
            }
        });
        return user[0].photo_100;
    }
}
