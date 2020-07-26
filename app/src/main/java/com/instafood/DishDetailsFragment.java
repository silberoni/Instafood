package com.instafood;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.squareup.picasso.Picasso;
import com.google.android.material.snackbar.Snackbar;
import com.instafood.model.Dish;
import com.instafood.model.DishModel;

public class DishDetailsFragment extends Fragment {
    private Dish dish;
    ImageView dish_img;
    EditText dish_name;
    EditText dish_desc;
    EditText dish_sec_1;
    EditText dish_sec_2;
    Button make_version;
    Button see_chef;
    Button dish_edit;
    Button dish_save;
    Button dish_delete;

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
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dish_details, container, false);
        dish_img = view.findViewById(R.id.fragment_dish_details_image_iv);
        dish_name = view.findViewById(R.id.fragment_dish_details_dish_name_ev);
        dish_desc = view.findViewById(R.id.fragment_dish_details_dish_desc_ev);
        dish_sec_1 = view.findViewById(R.id.fragment_dish_details_sec_1_eb);
        dish_sec_2 = view.findViewById(R.id.fragment_dish_details_sec_2_eb);
        dish_edit = view.findViewById(R.id.fragment_dish_details_edit_btn);
        dish_save = view.findViewById(R.id.fragment_dish_details_save_btn);
        dish_delete = view.findViewById(R.id.fragment_dish_details_delete_btn);
        make_version = view.findViewById(R.id.fragment_dish_details_make_version_btn);
        see_chef= view.findViewById(R.id.fragment_dish_details_chef_btn);

        dish = DishDetailsFragmentArgs.fromBundle(getArguments()).getDish();
        update_display();
        return view;
    }

    private void update_display() {
        dish_name.setText(dish.getName());
        dish_desc.setText(dish.getDesc());
        dish_sec_1.setText(dish.getIngredients());
        dish_sec_2.setText(dish.getInstructions());

        if (dish.getImgUrl() != null && dish.getImgUrl() != "") {
            Picasso.get().load(dish.getImgUrl()).placeholder(R.drawable.avatar).into(dish_img);
        }
        else
        {
            dish_img.setImageResource(R.drawable.avatar);
        }

        make_version.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavController navController = Navigation.findNavController(view);
                NavGraphDirections.ActionGlobalDishAddFragment action = DishAddFragmentDirections.actionGlobalDishAddFragment();
                action.setDish(dish);
                navController.navigate(action);
            }
        });

        see_chef.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavController navController = Navigation.findNavController(view);
                NavGraphDirections.ActionGlobalChefDetailsFragment action = ChefDetailsFragmentDirections.actionGlobalChefDetailsFragment();
                action.setChefId(dish.getMakerID());
                navController.navigate(action);
            }
        });

        //TODO: add check if user supposed to have edit button
        String CurrUser = MainActivity.context.getSharedPreferences("NOTIFY", Context.MODE_PRIVATE).getString("CurrentUser", "");
        if (CurrUser.equalsIgnoreCase(dish.getMakerID())) {
            dish_edit.setVisibility(View.VISIBLE);
            dish_edit.setClickable(true);
            dish_edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dish_save.setVisibility(View.VISIBLE);
                    dish_delete.setVisibility(View.VISIBLE);
                    dish_delete.setClickable(true);
                    dish_save.setClickable(true);
                    dish_name.setEnabled(true);
                    dish_desc.setEnabled(true);
                    dish_sec_1.setEnabled(true);
                    dish_sec_2.setEnabled(true);
                    dish_edit.setClickable(false);
                    dish_edit.setVisibility(View.INVISIBLE);
                    make_version.setClickable(false);
                    make_version.setVisibility(View.INVISIBLE);
                    see_chef.setClickable(false);
                    see_chef.setVisibility(View.INVISIBLE);
                }
            });
            dish_save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dish.setName(dish_name.getText().toString());
                    dish.setDesc(dish_desc.getText().toString());
                    dish.setIngredients(dish_sec_1.getText().toString());
                    dish.setInstructions(dish_sec_2.getText().toString());
                    DishModel.instance.update(dish);
                    Toast.makeText(getActivity(), "Item saves", Toast.LENGTH_SHORT).show();

                    dish_save.setVisibility(View.INVISIBLE);
                    dish_delete.setVisibility(View.INVISIBLE);
                    dish_delete.setClickable(false);
                    dish_save.setClickable(false);
                    dish_name.setEnabled(false);
                    dish_desc.setEnabled(false);
                    dish_sec_1.setEnabled(false);
                    dish_sec_2.setEnabled(false);
                    dish_edit.setClickable(true);
                    dish_edit.setVisibility(View.VISIBLE);
                    make_version.setClickable(true);
                    make_version.setVisibility(View.VISIBLE);
                    see_chef.setClickable(true);
                    see_chef.setVisibility(View.VISIBLE);

                    //NavController navCtrl = Navigation.findNavController(view);
                    //navCtrl.popBackStack();
                }
            });
            dish_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dish.setDeleted(true);
                    DishModel.instance.update(dish);
                    dish_delete.setClickable(false);
                    Toast.makeText(getActivity(), "Item deleted", Toast.LENGTH_SHORT).show();
                    NavController navCtrl = Navigation.findNavController(view);
                    navCtrl.popBackStack();
                }
            });
        } else {
            dish_edit.setVisibility(View.INVISIBLE);
            dish_edit.setEnabled(false);
            dish_save.setVisibility(View.INVISIBLE);
            dish_save.setEnabled(false);
            dish_delete.setVisibility(View.GONE);
            dish_delete.setEnabled(false);
        }
    }


    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }
}