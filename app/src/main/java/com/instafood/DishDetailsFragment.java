package com.instafood;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.provider.MediaStore;
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

import com.instafood.model.Chef;
import com.instafood.model.ChefModel;
import com.instafood.model.StoreModel;
import com.squareup.picasso.Picasso;
import com.google.android.material.snackbar.Snackbar;
import com.instafood.model.Dish;
import com.instafood.model.DishModel;

import java.util.Date;

import static android.app.Activity.RESULT_OK;

public class DishDetailsFragment extends Fragment {
    private Dish dish;
    private Chef chef;
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
    Button dish_add_photo;
    Bitmap imageBitmap;

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
        dish_add_photo = view.findViewById(R.id.fragment_dish_details_photo_btn);

        dish = DishDetailsFragmentArgs.fromBundle(getArguments()).getDish();

        ChefModel.instance.getChef(dish.getMakerID(), new ChefModel.Listener<Chef>() {
            @Override
            public void OnComplete(Chef data) {
                chef = data;
                update_display();
            }
        });

        return view;
    }

    private void update_display() {
        dish_name.setText(dish.getName());
        dish_desc.setText(dish.getDesc());
        dish_sec_1.setText(dish.getIngredients());
        dish_sec_2.setText(dish.getInstructions());

        if (dish.getImgUrl() != null) {
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

        see_chef.setText(chef.getName());

        see_chef.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavController navController = Navigation.findNavController(view);
                NavGraphDirections.ActionGlobalChefDetailsFragment action = ChefDetailsFragmentDirections.actionGlobalChefDetailsFragment();
                action.setChef(chef);
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
                    dish_add_photo.setEnabled(true);
                    dish_add_photo.setVisibility(View.VISIBLE);
                    dish_delete.setClickable(true);
                    dish_save.setClickable(true);
                    dish_name.setEnabled(true);
                    dish_desc.setEnabled(true);
                    dish_sec_1.setEnabled(true);
                    dish_sec_2.setEnabled(true);

                    dish_edit.setClickable(false);
                    dish_edit.setVisibility(View.GONE);
                    make_version.setClickable(false);
                    make_version.setVisibility(View.GONE);
                    see_chef.setClickable(false);
                    see_chef.setVisibility(View.GONE);

                }
            });
            dish_save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(checkFields()) {
                        dish.setName(dish_name.getText().toString());
                        dish.setDesc(dish_desc.getText().toString());
                        dish.setIngredients(dish_sec_1.getText().toString());
                        dish.setInstructions(dish_sec_2.getText().toString());


                        dish_save.setVisibility(View.GONE);
                        dish_delete.setVisibility(View.GONE);
                        dish_add_photo.setEnabled(false);
                        dish_add_photo.setVisibility(View.INVISIBLE);
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

                        Date d = new Date();
                        StoreModel.uploadImage(imageBitmap, "my_photo" + d.getTime(), new StoreModel.Listener() {
                            @Override
                            public void onSuccess(String url) {
                                Log.d("TAG", "url:" + url);
                                dish.setImgUrl(url);
                                DishModel.instance.addDish(dish, new DishModel.Listener<Boolean>() {
                                    @Override
                                    public void onComplete(Boolean data) {
                                        if (data) {
                                            Log.d("NOTIFY", "added updated ");
                                      //      Toast.makeText(getActivity(), "Dish saves", Toast.LENGTH_SHORT).show();
                                        } else {
                                            Log.d("NOTIFY", "Something went wrong ");
                                        }
                                    }
                                });
                            }
                            @Override
                            public void onFail() {
                                Log.d("NOTIFY", "Something went wrong");
                            }
                        });



                        //NavController navCtrl = Navigation.findNavController(view);
                        //navCtrl.popBackStack();
                    }
                }
            });
            dish_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dish.setDeleted(true);
                    DishModel.instance.addDish(dish, new DishModel.Listener<Boolean>() {
                        @Override
                        public void onComplete(Boolean data) {
                            if (data) {
                                Log.d("NOTIFY", "added updated ");
                            } else {
                                Log.d("NOTIFY", "Something went wrong ");
                            }
                        }
                    });
                  //  Toast.makeText(getActivity(), "Dish deleted", Toast.LENGTH_SHORT).show();
                    NavController navCtrl = Navigation.findNavController(view);
                    navCtrl.popBackStack();
                }
            });

            dish_add_photo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    takePhoto();
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
    static final int REQUEST_IMAGE_CAPTURE = 1;
    final static int RESULT_SUCCESS = 0;

    public Boolean checkFields() {
        if ((!dish_name.getText().toString().isEmpty())&&
                (!dish_desc.getText().toString().isEmpty())&&
                (!dish_sec_1.getText().toString().isEmpty())&&
                (!dish_sec_2.getText().toString().isEmpty())){
            return true;
        } else {
            Log.d("NOTIFY", "Cannot leave empty fields");
            return false;
        }
    }

    void takePhoto() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            Log.d("NOTIFY", "Opened Camera");
        }
        else
        {
            Log.d("NOTIFY", "There is no camera to take photos with ");
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE &&
                resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            imageBitmap = (Bitmap) extras.get("data");
            //imageView.setImageBitmap(imageBitmap);

            dish_img.setImageBitmap(imageBitmap);
            Log.d("NOTIFY", "BITMAP SUCCESS");
        }
        else
        {
            Log.d("NOTIFY", "BITMAP FAILED ");
        }
    }
}