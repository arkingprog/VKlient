package com.example.arkin.vkclient.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.arkin.vkclient.R;
import com.vk.sdk.api.model.VKApiDialog;
import com.vk.sdk.api.model.VKApiGetDialogResponse;
import com.vk.sdk.api.model.VKApiMessage;
import com.vk.sdk.api.model.VKList;
import com.vk.sdk.api.model.VKPostArray;

/**
 * Created by arkin on 23.11.2015.
 */
public class DialogAdapter extends ArrayAdapter {
    Context ctx;
    LayoutInflater layoutInflater;
    VKApiGetDialogResponse obj;
    @Override
    public int getCount() {
        return obj.items.size();
    }
    public DialogAdapter(Context context,VKApiGetDialogResponse dialogResponse)
    {
        super(context, R.layout.list_item_messages);
        ctx=context;
        obj=dialogResponse;
        layoutInflater=(LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
       ViewHolder holder;
        View view = convertView;
        if (view == null) {
            view = layoutInflater.inflate(R.layout.list_item_messages, parent, false);
            holder=new ViewHolder();
            holder.imageView=(ImageView) view.findViewById(R.id.imageDialog);
            holder.textName= (TextView) view.findViewById(R.id.textName);
            holder.textMessages= (TextView) view.findViewById(R.id.textMessages);
            //holder.textDate= (TextView) view.findViewById(R.id.textDatePost);
            view.setTag(holder);
        }
        else
        {
            holder=(ViewHolder) view.getTag();
        }
        VKApiMessage p=obj.items.get(position).message;

        //holder.textDate.setText(String.valueOf(p.date));
        holder.textMessages.setText(p.body);
       /* new GetUsersTask(holder.textName,
                holder.imageView,
                getContext())
                .execute(String.valueOf(p.user_id));
*/

        return view;
    }
    static class ViewHolder {
        public ImageView  imageView;
        public TextView textName;
        public TextView textDate;
        public TextView textMessages;

    }
    @Override
    public long getItemId(int position) {
        return position;
    }


}
