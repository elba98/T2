package com.cp317.t2.t2;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class UserListAdapter extends ArrayAdapter<User> {

    private Context mContext;
    int mResource;

    public UserListAdapter(@NonNull Context context, int resource, @NonNull ArrayList<User> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String firstName = getItem(position).getUserFirstName();
        String lastName = getItem(position).getUserLastName();
        String name = firstName + " " + lastName;
        String program = getItem(position).getProgram();

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        ImageView ivAvatar = (ImageView) convertView.findViewById(R.id.avatar_imageView);
        TextView tvName = (TextView) convertView.findViewById(R.id.name_textView);
        TextView tvProgram = (TextView) convertView.findViewById(R.id.program_textView);

        ivAvatar.setImageResource(R.drawable.prof1);
        tvName.setText(name);
        tvProgram.setText(program);

        return convertView;
    }


}
