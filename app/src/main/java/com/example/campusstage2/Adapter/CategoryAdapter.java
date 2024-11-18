package com.example.campusstage2.Adapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.campusstage2.model.Category;

import java.util.List;

public class CategoryAdapter extends ArrayAdapter<Category> {
    public CategoryAdapter(Context context, List<Category> categories) {
        super(context, 0, categories);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext())
                    .inflate(android.R.layout.simple_list_item_1, parent, false);
        }

        Category category = getItem(position);
        TextView textView = convertView.findViewById(android.R.id.text1);

        if (category.getParentId() != null) {
            textView.setPadding(50, 0, 0, 0);  // Indent subcategories
        } else {
            textView.setPadding(0, 0, 0, 0);   // No indent for main categories
        }

        textView.setText(category.getName());
        return convertView;
    }
}
