package com.han.user_managment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class register extends AppCompatActivity {
    TextView id_txtview;
    EditText id_fname,id_uname,id_email,id_pass,id_mob;
    RadioButton id_female,id_male;
    RadioGroup radioGroup;
    Button id_register;
    private String fulname,username,email,password,phone,gender;
    UserDatabaseHelper userDatabaseHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        id_txtview=(TextView)findViewById(R.id.id_txtview);

        id_fname=(EditText) findViewById(R.id.id_fname);
        id_uname=(EditText) findViewById(R.id.id_uname);
        id_email=(EditText) findViewById(R.id.id_email);
        id_pass=(EditText) findViewById(R.id.id_pass);
        id_mob=(EditText) findViewById(R.id.id_mob);
radioGroup=findViewById(R.id.radioGroup);
        id_female=(RadioButton)findViewById(R.id.id_female);
        id_male=(RadioButton)findViewById(R.id.id_male);
        id_register=(Button)findViewById(R.id.id_registeruser);
        userDatabaseHelper=new UserDatabaseHelper(this);
    }

    public void register(View view) {
        userDatabaseHelper=new UserDatabaseHelper(this);
        fulname=id_fname.getText().toString().trim();
        username=id_uname.getText().toString().trim();
        email=id_email.getText().toString().trim();
        password=id_pass.getText().toString().trim();
        phone=id_mob.getText().toString().trim();
        gender=((RadioButton)findViewById(radioGroup.getCheckedRadioButtonId())).getText().toString().trim();
        if(fulname.isEmpty())
            id_fname.setError("USER FULL NAME REQUIRED!");
        else if(username.isEmpty())
            id_uname.setError("USER NAME REQUIRED!");
        else  if(email.isEmpty())
            id_email.setError("USER EMAIL REQUIRED!");
        else if(password.isEmpty())
            id_pass.setError("USER PASSWORD REQUIRED!");
        else  if(phone.isEmpty())
            id_mob.setError("USER PHONE NAME REQUIRED!");
        else {
            userDatabaseHelper.addUser(fulname, username, email, password, phone, gender);
            id_fname.setText("");
            id_uname.setText("");
            id_email.setText("");
            id_pass.setText("");
            id_mob.setText("");
            Intent intent=new Intent(getApplicationContext(),users_page.class);
            startActivity(intent);
            finish();
        }

    }
}
