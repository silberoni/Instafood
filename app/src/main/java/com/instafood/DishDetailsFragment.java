package com.instafood;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.instafood.model.Dish;

public class DishDetailsFragment extends Fragment {
    private Dish dish;
    TextView name;
    TextView id;
    CheckBox cb;

    public DishDetailsFragment() {
        // Required empty public constructor
    }

    // TODO: is needed?
    public static DishDetailsFragment newInstance() {
        DishDetailsFragment fragment = new DishDetailsFragment();
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
        View view = inflater.inflate(R.layout.fragment_dish_details, container, false);
        name =view.findViewById(R.id.dish_details_dish_name);
        id = view.findViewById(R.id.dish_details_dish_id);
        cb = view.findViewById(R.id.dish_details_check_cb);

        if(dish!= null){
            update_display();
        }
        return view;
    }

    public void setDish(Dish dish){
        this.dish = dish;
        if (name != null){
            update_display();
        }
    }

    private void update_display() {
        name.setText(dish.getName());
        id.setText(dish.getId());
        cb.setChecked(dish.isChecked());
        cb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dish.setChecked(cb.isChecked());
            }
        });
    }
}