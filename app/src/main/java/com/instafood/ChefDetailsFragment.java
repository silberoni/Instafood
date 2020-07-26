package com.instafood;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.instafood.model.Chef;
import com.instafood.model.ChefModel;
import com.instafood.model.Dish;

public class ChefDetailsFragment extends Fragment {
    private Chef chef;
    ImageView chef_img;
    EditText chef_name;
    EditText chef_desc;
    Button chef_edit;
    Button chef_save;
    Button chef_dishes;
    String chef_id;
    String CurrUser;

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
        setHasOptionsMenu(true);
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
        chef_save = view.findViewById(R.id.fragment_chef_details_save_btn);
        chef_dishes = view.findViewById(R.id.fragment_chef_details_see_dishes_btn);

        chef_id = ChefDetailsFragmentArgs.fromBundle(getArguments()).getChefId();
        CurrUser = MainActivity.context.getSharedPreferences("NOTIFY", Context.MODE_PRIVATE).getString("CurrentUser", "");
        if(chef_id == null){
            chef_id = CurrUser;
        }

        ChefModel.instance.getChef(chef_id, new ChefModel.Listener<Chef>() {
            @Override
            public void OnComplete(Chef data) {
                chef = data;
                update_display();
            }
        });

        return view;
    }

    private void update_display() {
        if (chef != null) {
            chef_name.setText(chef.getName());
            chef_desc.setText(chef.getDesc());
            chef_dishes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    NavController navController = Navigation.findNavController(getView());
                    NavGraphDirections.ActionGlobalDishListFragment action = DishListFragmentDirections.actionGlobalDishListFragment();
                    action.setChefList(chef_id);
                    navController.navigate(action);
                }
            });

            if (CurrUser.equalsIgnoreCase(chef.getId())) {
                chef_edit.setVisibility(View.VISIBLE);
                chef_edit.setClickable(true);
                chef_edit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        chef_dishes.setVisibility(View.INVISIBLE);
                        chef_dishes.setClickable(false);
                        chef_save.setVisibility(View.VISIBLE);
                        chef_name.setEnabled(true);
                        chef_name.setHint(R.string.chef_name_hint);
                        chef_desc.setEnabled(true);
                        chef_desc.setHint(R.string.chef_desc_hint);
                        chef_edit.setClickable(false);
                        chef_edit.setVisibility(View.INVISIBLE);
                    }
                });
                chef_save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        chef.setName(chef_name.getText().toString());
                        chef.setDesc(chef_desc.getText().toString());
                        ChefModel.instance.update(chef);
                        Toast.makeText(getActivity(), "Changes saves", Toast.LENGTH_SHORT).show();

                        chef_dishes.setVisibility(View.VISIBLE);
                        chef_dishes.setClickable(true);
                        chef_save.setVisibility(View.INVISIBLE);
                        chef_name.setEnabled(false);
                        chef_name.setHint("");
                        chef_desc.setEnabled(false);
                        chef_desc.setHint("");
                        chef_edit.setClickable(true);
                        chef_edit.setVisibility(View.VISIBLE);

                        //NavController navCtrl = Navigation.findNavController(view);
                        //navCtrl.popBackStack();
                    }
                });
            }
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }

}