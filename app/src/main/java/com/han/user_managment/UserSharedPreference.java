package com.han.user_managment;

import android.content.Context;
import android.content.SharedPreferences;

public class UserSharedPreference {

    SharedPreferences userpreference;
    SharedPreferences.Editor usereditor;
    Context userContext;
    String username,password;
    public UserSharedPreference(Context userContext) {
        this.userContext = userContext;
        userpreference=userContext.getSharedPreferences("userApp", Context.MODE_PRIVATE);
        usereditor=userpreference.edit();
    }
    public void setUserLoggedIn(boolean userLoggedIn){
        usereditor.putBoolean("userLogedInMode", userLoggedIn);
        usereditor.commit();
    }
    public boolean userLoggedIn(){
        return userpreference.getBoolean("userLogedInMode",false);

    }

}
