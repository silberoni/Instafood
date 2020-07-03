package com.instafood;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.instafood.model.Dish;
import com.instafood.model.DishModel;

import java.util.List;

public class DishEditFragment extends Fragment {
    private Dish dish_edit;
    ImageView dish_edit_img;
    EditText dish_edit_name;
    EditText dish_edit_desc;
    CheckBox dish_edit_made;
    EditText dish_edit_sec_1;
    EditText dish_edit_sec_2;
    Button dish_edit_save;
    Button dish_edit_delete;


    public DishEditFragment() {
        // Required empty public constructor
    }

    public static DishEditFragment newInstance(String param1, String param2) {
        DishEditFragment fragment = new DishEditFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dish_edit, container, false);
        dish_edit_img = view.findViewById(R.id.fragment_dish_edit_image_iv);
        dish_edit_name = view.findViewById(R.id.fragment_dish_edit_dish_name_tv);
        dish_edit_desc = view.findViewById(R.id.fragment_dish_edit_dish_desc_tv);
        dish_edit_sec_1 = view.findViewById(R.id.fragment_dish_edit_sec_1_tb);
        dish_edit_sec_2 = view.findViewById(R.id.fragment_dish_edit_sec_2_tb);
        dish_edit_save = view.findViewById(R.id.fragment_dish_edit_save_btn);
        dish_edit_delete = view.findViewById(R.id.fragment_dish_edit_delete_btn);

        if (dish_edit != null) {
            update_display();
        }
        return view;
    }

    private void update_display() {
        dish_edit_name.setText(dish_edit.getName());
        dish_edit_desc.setText(dish_edit.getDesc());
        dish_edit_sec_1.setText(dish_edit.getIngredients());
        dish_edit_sec_2.setText(dish_edit.getInstructions());
        dish_edit_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dish_edit.setName(dish_edit_name.getText().toString());
                dish_edit.setDesc(dish_edit_desc.getText().toString());
                dish_edit.setIngredients(dish_edit_sec_1.getText().toString());
                dish_edit.setInstructions(dish_edit_sec_2.getText().toString());
                DishModel.instance.update(dish_edit);
                Snackbar.make(view, "Item saves", Snackbar.LENGTH_SHORT).show();
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });
        dish_edit_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dish_edit.setDeleted(true);
                // Log.d("TAG","1. desc: "+dish_edit.getDesc());
                DishModel.instance.update(dish_edit);
                dish_edit_delete.setClickable(false);
                Snackbar.make(view, "Item deleted", Snackbar.LENGTH_SHORT).show();
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });
    }

    public void setDish(Dish dish) {
        this.dish_edit = dish;
        if (dish_edit_name != null) {
            update_display();
        }

    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }
}