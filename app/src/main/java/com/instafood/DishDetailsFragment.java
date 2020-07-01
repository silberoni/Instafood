package com.instafood;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.instafood.model.Dish;
import com.instafood.model.DishModel;

public class DishDetailsFragment extends Fragment {
    private Dish dish;
    ImageView dish_img;
    TextView dish_name;
    TextView dish_desc;
    CheckBox dish_made;
    TextView dish_sec_1;
    TextView dish_sec_2;
    Button dish_edit;
    private delegate parent;

    interface delegate {
        void onItemEdit(Dish dish);
    }

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
    public void onAttach(@NonNull Context context){
        super.onAttach(context);
        if (context instanceof DishListFragment.delegate) {
            parent = (delegate) getActivity();
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement Delegate");
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dish_details, container, false);
        dish_img = view.findViewById(R.id.fragment_dish_details_image_iv);
        dish_name = view.findViewById(R.id.fragment_dish_details_dish_name_tv);
        dish_desc = view.findViewById(R.id.fragment_dish_details_dish_desc_tv);
        dish_made = view.findViewById(R.id.fragment_dish_details_made_cb);
        dish_sec_1 = view.findViewById(R.id.fragment_dish_details_sec_1_tb);
        dish_sec_2 = view.findViewById(R.id.fragment_dish_details_sec_2_tb);
        dish_edit = view.findViewById(R.id.fragment_dish_details_edit_btn);

        if (dish != null) {
            update_display();
        }
        return view;
    }

    public void setDish(Dish dish) {
        this.dish = dish;
        if (dish_name != null) {
            update_display();
        }
    }

    private void update_display() {
        dish_name.setText(dish.getName());
        dish_desc.setText(dish.getDesc());
        dish_made.setChecked(dish.isChecked());
        dish_made.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dish.setChecked(dish_made.isChecked());
            }
        });
        dish_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(parent != null) {
                    parent.onItemEdit(dish);
                } else{
                    throw new RuntimeException("help");
                }
            }
        });
        dish_sec_1.setText(dish.getIngredients());
        dish_sec_2.setText(dish.getInstructions());
    }
    @Override
    public void onDetach() {
        super.onDetach();
        parent = null;
    }

}