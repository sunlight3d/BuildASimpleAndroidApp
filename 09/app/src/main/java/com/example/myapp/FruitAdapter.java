package com.example.myapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class FruitAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Fruit> fruits;

    public FruitAdapter(Context context, ArrayList<Fruit> fruits) {
        this.context = context;
        this.fruits = fruits;
    }

    @Override
    public int getCount() {
        return fruits.size();
    }

    @Override
    public Object getItem(int position) {
        return fruits.get(position);
    }

    @Override
    public long getItemId(int position) {
        return fruits.get(position).getIcon();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Fruit fruit = fruits.get(position);
        if(convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.row_item, parent, false);
            ImageView imageViewFruit = convertView.findViewById(R.id.imageViewFruit);
            TextView txtFruitName = convertView.findViewById(R.id.txtFruitName);
            imageViewFruit.setImageResource(fruit.getIcon());
            txtFruitName.setText(fruit.getName());
        }
        return convertView;
    }
}
