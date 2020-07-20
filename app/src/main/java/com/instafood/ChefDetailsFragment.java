package com.instafood;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.instafood.model.Chef;
import com.instafood.model.Dish;

public class ChefDetailsFragment extends Fragment {
    private Chef chef;
    ImageView chef_img;
    TextView chef_name;
    TextView chef_desc;
    Button chef_edit;

    public ChefDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chef_details, container, false);
        chef_img = view.findViewById(R.id.fragment_chef_details_image_iv);
        chef_desc = view.findViewById(R.id.fragment_chef_details_chef_desc_tv);
        chef_name = view.findViewById(R.id.fragment_chef_details_chef_name_tv);
        chef_edit = view.findViewById(R.id.fragment_chef_details_edit_btn);

        if (chef != null) {
            update_display();
        }
        return view;
    }

    public void setChef(Chef chef) {
        this.chef = chef;
        if (chef_name != null) {
            update_display();
        }
    }

    private void update_display() {
        chef_name.setText(chef.getName());
        chef_desc.setText(chef.getDesc());
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }

}