package com.example.myapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public class MainActivity extends AppCompatActivity {
    private ArrayList<Fruit> fruits = new ArrayList<>();
    private GridView gridView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fruits.add(new Fruit("Apple", R.drawable.apple));
        fruits.add(new Fruit("Jack fruit", R.drawable.jackfruit));
        fruits.add(new Fruit("Jack fruit", R.drawable.bitter_melon));
        fruits.add(new Fruit("Jack fruit", R.drawable.custard_apple));
        fruits.add(new Fruit("Jack fruit", R.drawable.dragon_fruit));
        fruits.add(new Fruit("Jack fruit", R.drawable.grape));
        fruits.add(new Fruit("Jack fruit", R.drawable.kiwi));
        fruits.add(new Fruit("Jack fruit", R.drawable.lychee));
        fruits.add(new Fruit("Jack fruit", R.drawable.mangosteen));
        fruits.add(new Fruit("Jack fruit", R.drawable.orange));
        fruits.add(new Fruit("Jack fruit", R.drawable.peach));
        fruits.add(new Fruit("Jack fruit", R.drawable.pomegranate));
        fruits.add(new Fruit("Jack fruit", R.drawable.rambutan));

        this.gridView = (GridView)findViewById(R.id.gridview);
        FruitAdapter fruitAdapter = new FruitAdapter(this, fruits);
        gridView.setAdapter(fruitAdapter);

    }
}
