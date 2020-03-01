package com.han.user_managment;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    TextView welcomelogintv;
    Button btnlogin,btnregister;
    EditText username,password;
    private UserDatabaseHelper userDatabaseHelper;
    SQLiteDatabase userDb;
    private UserSharedPreference userSharedPreference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        welcomelogintv=findViewById(R.id.id_logintv);
        btnlogin=findViewById(R.id.id_loginbtn);
        btnregister=findViewById(R.id.id_registerbtn);
        username=findViewById(R.id.id_username);
        password=findViewById(R.id.id_password);
btnlogin.setOnClickListener(this);
btnregister.setOnClickListener(this);
        userSharedPreference=new UserSharedPreference(this);
        if(userSharedPreference.userLoggedIn()){
            startActivity(new Intent(getApplicationContext(),users_page.class));
            finish();
        }
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.id_loginbtn:
                String getUsername = username.getText().toString().trim();
                String getPassword = password.getText().toString().trim();
                UserDatabaseHelper  userDatabaseHelper= new UserDatabaseHelper(this);
                if (getUsername.isEmpty()){
                    username.setError("ENTER USER NAME!");
                    username.requestFocus();
                } else if(getPassword.isEmpty()){
                    password.setError("ENTER PASSWORD!");
                }
                Boolean getResult=userDatabaseHelper.checkUserLogin(getUsername, getPassword);
                if(getResult==true){
                    SharedPreferences sharedPreferences=this.getSharedPreferences("userApp",MODE_PRIVATE);
                    SharedPreferences.Editor editor=sharedPreferences.edit();
                    editor.putString("username", getUsername);
                    editor.putString("password",getPassword);
                    editor.commit();
                    userSharedPreference.setUserLoggedIn(true);
                    Intent loginIntent = new Intent(new Intent(getApplicationContext(), users_page.class));
                    startActivity(loginIntent);
                    finish();
                }
                else {
                    username.setError("Invalid  user name and password try Again");
                    username.setText("");
                    password.setText("");
                }
                break;
            case R.id.id_registerbtn:
                startActivity(new Intent(getApplicationContext(),register.class));
                finish();
        }
  }

}
