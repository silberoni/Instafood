package com.instafood;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.android.material.snackbar.Snackbar;
import com.instafood.model.Dish;
import com.instafood.model.DishModel;

public class DishAddFragment extends Fragment {
    private Dish dish;
    ImageView dish_img;
    EditText dish_name;
    EditText dish_desc;
    EditText dish_sec_1;
    EditText dish_sec_2;
    Button dish_save;

    public DishAddFragment() {
        // Required empty public constructor
    }

    // TODO: is needed?
    public static DishAddFragment newInstance() {
        DishAddFragment fragment = new DishAddFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
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
        View view = inflater.inflate(R.layout.fragment_dish_add, container, false);
        dish_img = view.findViewById(R.id.fragment_dish_add_image_iv);
        dish_name = view.findViewById(R.id.fragment_dish_add_dish_name_ev);
        dish_desc = view.findViewById(R.id.fragment_dish_add_dish_desc_ev);
        dish_sec_1 = view.findViewById(R.id.fragment_dish_add_sec_1_eb);
        dish_sec_2 = view.findViewById(R.id.fragment_dish_add_sec_2_eb);
        dish_save = view.findViewById(R.id.fragment_dish_add_save_btn);
        update_display();
        return view;
    }

    private void update_display() {
        dish_name.setText(dish.getName());
        dish_desc.setText(dish.getDesc());

        dish_sec_1.setText(dish.getIngredients());
        dish_sec_2.setText(dish.getInstructions());

        //TODO: add check if user supposed to have edit button
        dish_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dish.setName(dish_name.getText().toString());
                dish.setDesc(dish_desc.getText().toString());
                dish.setIngredients(dish_sec_1.getText().toString());
                dish.setInstructions(dish_sec_2.getText().toString());
                DishModel.instance.update(dish);
                Snackbar.make(view, "Dish served to the feed", Snackbar.LENGTH_SHORT).show();
                NavController navCtrl = Navigation.findNavController(view);
                navCtrl.popBackStack();
            }
        });
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }
}