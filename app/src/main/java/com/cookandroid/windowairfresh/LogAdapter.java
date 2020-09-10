package com.cookandroid.windowairfresh;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class LogAdapter extends ArrayAdapter<Logs> {
    private Context mContext;
    private int mResource;

    public LogAdapter(@NonNull Context context, int resource, @NonNull List<Logs> objects) {
        super(context, resource, objects);
        this.mContext = context;
        this.mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource,parent,false);
        ImageView icon = convertView.findViewById(R.id.icon);
        TextView alerttxt = convertView.findViewById(R.id.alerttxt);

        icon.setImageResource(getItem(position).getIcon());
        alerttxt.setText(getItem(position).getAlertxt());

        return convertView;
    }
}
