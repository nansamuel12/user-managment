package com.han.user_managment;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.UsersViewHolder> {
    private Context usercontext;
    private View.OnClickListener userClickListner;
    private View.OnLongClickListener userLongClickListner;
    private ArrayList<UserModel> userModels;

    public UsersAdapter(Context usercontext, ArrayList<UserModel> userModels) {
        this.usercontext = usercontext;
        this.userModels=userModels;
    }

    public void setUserClickListner(View.OnClickListener userClickListner) {
        this.userClickListner = userClickListner;
    }

    public void setUserLongClickListner(View.OnLongClickListener userLongClickListner) {
        this.userLongClickListner = userLongClickListner;
    }

    @NonNull
    @Override
    public UsersAdapter.UsersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(usercontext);
        View view=   inflater.inflate(R.layout.user_list, null);
        return new UsersViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull UsersAdapter.UsersViewHolder holder, int position) {
        UserModel userModel=userModels.get(position);
        holder.userfulname.setText(userModel.getFulname());
    }

    @Override
    public int getItemCount() {
        return userModels.size();
    }

    public class UsersViewHolder extends RecyclerView.ViewHolder {
        TextView userfulname;
        public UsersViewHolder(@NonNull View itemView) {
            super(itemView);
            userfulname=itemView.findViewById(R.id.id_users);
            itemView.setTag(this);
            itemView.setOnClickListener(userClickListner);
            itemView.setOnLongClickListener(userLongClickListner);
        }
    }
}
