package com.example.myapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.GridView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        GridView gridView = (GridView)findViewById(R.id.gridview);
        ArrayList<Category> categories = new ArrayList<>();

        categories.add(new Category("Food", R.drawable.food_icon));
        categories.add(new Category("Food", R.drawable.food_icon));
        categories.add(new Category("Food", R.drawable.food_icon));
        categories.add(new Category("Food", R.drawable.food_icon));
        categories.add(new Category("Food", R.drawable.food_icon));
        categories.add(new Category("Food", R.drawable.food_icon));
        categories.add(new Category("Food", R.drawable.food_icon));

        CategoriesAdapter categoriesAdapter = new CategoriesAdapter(this, categories);
        gridView.setAdapter(categoriesAdapter);
    }
}
