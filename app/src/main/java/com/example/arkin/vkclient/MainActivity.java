package com.example.arkin.vkclient;

import android.content.Intent;
import android.os.Bundle;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.arkin.vkclient.adapter.RecyclerAdapterWall;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.vk.sdk.VKSdk;
import com.vk.sdk.api.VKApi;
import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;

import com.vk.sdk.api.methods.VKApiMessages;
import com.vk.sdk.api.model.VKApiPost;
import com.vk.sdk.api.model.VKApiUser;
import com.vk.sdk.api.model.VKList;
import com.vk.sdk.api.model.VKPostArray;
import com.vk.sdk.util.VKUtil;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,SwipeRefreshLayout.OnRefreshListener {
    VKApiUser mainUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final ImageLoader imageLoader=ImageLoader.getInstance();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        if(!VKSdk.isLoggedIn())
        {
            Intent intent=new Intent(this,LoginActivity.class);
            startActivity(intent);
        }
        else{
            final VKRequest request=VKApi.users().get(VKParameters.from(VKApiConst.FIELDS, "photo_100,photo_200,online"));
            request.setPreferredLang("ru");
            request.executeWithListener(new VKRequest.VKRequestListener() {
                @Override
                public void onComplete(VKResponse response) {
                    super.onComplete(response);
                    VKList<VKApiUser> userMe = ((VKList<VKApiUser>) response.parsedModel);
                    mainUser = userMe.get(0);
                    ImageView im = (ImageView) findViewById(R.id.imageView);

                    imageLoader.displayImage(userMe.get(0).photo_100,im);
                    im = (ImageView) findViewById(R.id.imageView_main);
                    imageLoader.displayImage(userMe.get(0).photo_200,im);
                    TextView txt = (TextView) findViewById(R.id.fullname);
                    txt.setText(userMe.get(0).first_name + " " + userMe.get(0).last_name);
                    txt = (TextView) findViewById(R.id.userName);
                    txt.setText(userMe.get(0).first_name + " " + userMe.get(0).last_name);
                    txt = (TextView) findViewById(R.id.userOnline);
                    if (userMe.get(0).online) {
                        txt.setText(R.string.isOnline);
                    }
                }
            });



            VKRequest reqWall=VKApi.wall().get(VKParameters.from("owner_id",66505229));
            reqWall.setPreferredLang("ru");
            reqWall.executeWithListener(new VKRequest.VKRequestListener() {
                @Override
                public void onComplete(VKResponse response) {
                    super.onComplete(response);
                    JSONObject jsonObject=response.json;
                    String owner=null;
                    try {
                        owner=(((JSONObject) ((JSONArray) ((JSONObject) jsonObject.get("response")).get("items")).get(0)).getString("owner_id"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Log.v("test", owner);

                    VKPostArray posts=new VKPostArray();
                    try {
                        posts.parse(response.json);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    RecyclerView rv=(RecyclerView)findViewById(R.id.userWall);
                    LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getApplicationContext());
                    rv.setLayoutManager(linearLayoutManager);
                    RecyclerAdapterWall adapterWall=new RecyclerAdapterWall(getApplicationContext(),posts);
                    rv.setAdapter(adapterWall);
/*
                    ListView listWall=(ListView)findViewById(R.id.userWall);
                    WallPostAdapter wallPostAdapter=new WallPostAdapter(getApplicationContext(),posts);
                    listWall.setAdapter(wallPostAdapter);
*/


                }
            });

        }

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        switch (id)
        {
            case R.id.nav_exit:
                VKSdk.logout();
                Intent intent=new Intent(this,LoginActivity.class);
                startActivity(intent);
                break;
            case R.id.nav_messages:
                Intent intentMes=new Intent(this,MessagesActivity.class);
                intentMes.putExtra(VKApiUser.class.getCanonicalName(), mainUser);
                startActivity(intentMes);
                break;
            case R.id.nav_friends:
                startActivity(new Intent(this,FriendActivity.class));
            default:

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }




    @Override
    public void onRefresh() {
        VKRequest reqWall=VKApi.wall().get(VKParameters.from(VKApiConst.FIELDS,"66505229"));
        reqWall.setPreferredLang("ru");
        reqWall.executeWithListener(new VKRequest.VKRequestListener() {
            @Override
            public void onComplete(VKResponse response) {
                super.onComplete(response);

                VKPostArray posts=new VKPostArray();
                try {
                    posts.parse(response.json);
                } catch (JSONException e) {
                    e.printStackTrace();

                }
                /*ListView listWall=(ListView)findViewById(R.id.userWall);
                WallPostAdapter wallPostAdapter=new WallPostAdapter(getApplicationContext(),posts);
                listWall.setAdapter(wallPostAdapter);
*/

            }
        });
    }
}
