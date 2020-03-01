package com.han.user_managment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

public class user_recyclerView extends AppCompatActivity {
    private RecyclerView userRecyclerView;
    UserDatabaseHelper userdb;
    SQLiteDatabase sqLiteDatabase;
    private ArrayList<UserModel> userModelArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_recycler_view);
        userModelArrayList=new ArrayList<>();
        userRecyclerView=findViewById(R.id.id_user_recycler);
        userRecyclerView.setHasFixedSize(true);
        if(getApplicationContext().getResources().getConfiguration().orientation== Configuration.ORIENTATION_LANDSCAPE){
            userRecyclerView.setLayoutManager(new GridLayoutManager(this,2));
        }else {
            userRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        }
        userdb=new UserDatabaseHelper(this);
        sqLiteDatabase=userdb.getReadableDatabase();
        Cursor cursor=sqLiteDatabase.rawQuery("select * from users", null);
        try {
            while (cursor.moveToNext()) {
                userModelArrayList.add( new UserModel(
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5),
                        cursor.getString(6)));
            }
        }
        catch (Exception ex){
            ex.printStackTrace();
        } finally {if(!cursor.isClosed())
          cursor.close();
            userdb.close();
    }
        final UsersAdapter userAdapter=new UsersAdapter(this, userModelArrayList);
        userRecyclerView.setAdapter(userAdapter);
        userAdapter.setUserClickListner(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RecyclerView.ViewHolder viewHolder=(RecyclerView.ViewHolder)    v.getTag();
                int pos=viewHolder.getAdapterPosition();
                UserModel  userContainer=userModelArrayList.get(pos);
                String fulname=userContainer.getFulname();
                String username=userContainer.getUsername();
                String email=userContainer.getEmail();
                String password=userContainer.getPassword();
                String phone=userContainer.getPhone();
                String gender=userContainer.getGender();
                Intent userDetailIntent=new Intent(getApplicationContext(),User_detailInfo.class);
                userDetailIntent.putExtra("fulname",fulname);
                userDetailIntent.putExtra("username",username);
                userDetailIntent.putExtra("email",email);
                userDetailIntent.putExtra("password",password);
                userDetailIntent.putExtra("phone",phone);
                userDetailIntent.putExtra("gender",gender);
                startActivity(userDetailIntent);
                finish();
            }
        });
        SharedPreferences preferences=this.getSharedPreferences("userApp", MODE_PRIVATE);
        final String username= preferences.getString("username", null);
        final String password=preferences.getString("password", null);
        userAdapter.setUserLongClickListner(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                RecyclerView.ViewHolder viewHolder=(RecyclerView.ViewHolder)    v.getTag();
                int pos=viewHolder.getAdapterPosition();
                UserModel  userContainer=userModelArrayList.get(pos);
                //deletUsers( userContainer.getUsername());
                if (userContainer.getUsername().equals(username) && userContainer.getPassword().equals(password))
                    getNotification();
                else {
                    userModelArrayList.remove(pos);
                    userAdapter.notifyItemRemoved(pos);
                }
                // userAdapter.notifyItemRangeChanged(pos, userContainerList.size());
                return true;
            }
        });
    }
    public void deletUsers(String username){
        UserDatabaseHelper userInfoDatabas=new UserDatabaseHelper(this);
        userInfoDatabas.deleteUser(username);
    }
    public void getNotification(){
        Toast.makeText(this, "User can not deleted because already log in", Toast.LENGTH_SHORT).show();
    }
    }