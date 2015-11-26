package com.example.arkin.vkclient;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.vk.sdk.api.VKApi;
import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;
import com.vk.sdk.api.model.VKApiUser;
import com.vk.sdk.api.model.VKList;

import java.io.InputStream;

public class GetUsersTask extends AsyncTask<String, Void, VKApiUser> {
    TextView textView;
    ImageView imageView;
   ImageLoader imageLoader;
    Context context;
    VKApiUser vkApiUser;

    public GetUsersTask(TextView textView,ImageView imageView,Context context,ImageLoader imageLoader)
    {
        this.textView=textView;
        this.imageView=imageView;
        this.context=context;
        this.imageLoader=imageLoader;

    }

    protected VKApiUser doInBackground(String... id) {
        String ID = id[0];

        final VKApiUser[] user1 = new VKApiUser[1];
        VKRequest request= VKApi.users().get(VKParameters.from(VKApiConst.USER_ID, ID,VKApiConst.FIELDS,"photo_100"));
        request.setPreferredLang("ru");
        request.executeSyncWithListener(new VKRequest.VKRequestListener() {
            @Override
            public void onComplete(VKResponse response) {
                super.onComplete(response);
                VKList<VKApiUser> us = ((VKList<VKApiUser>) response.parsedModel);
                user1[0] = us.get(0);
            }
        });
        return user1[0];

    }

    protected void onPostExecute(VKApiUser result) {
        if(result!=null) {
            textView.setText(result.first_name + " " + result.last_name);
            /*imageLoader.init(ImageLoaderConfiguration.createDefault(context));
            DisplayImageOptions options=new DisplayImageOptions.Builder()
                    .cacheInMemory(true)
                    .cacheOnDisk(true)
                    .imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2)
                    .build();
*/
          //  imageLoader.displayImage(result.photo_100, imageView);
        }
    }
}