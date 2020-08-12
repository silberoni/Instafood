package com.instafood;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.instafood.model.DishModel;
import com.instafood.model.ModelFirebase;
import com.instafood.model.StoreModel;
import com.squareup.picasso.Picasso;
import com.instafood.model.Chef;
import com.instafood.model.ChefModel;
import com.instafood.model.Dish;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Date;

import static android.app.Activity.RESULT_OK;
import static com.instafood.MainActivity.context;

public class ChefDetailsFragment extends Fragment {
    private Chef chef;
    ImageView chef_img;
    EditText chef_name;
    EditText chef_desc;
    Button chef_edit;
    Button chef_save;
    Button chef_dishes;
    Button chef_add_photo;
    String chef_id;
    String CurrUser;
    Bitmap imageBitmap;

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
        chef_add_photo = view.findViewById(R.id.fragment_chef_details_add_photo_btn);

        assert getArguments() != null;
        chef = ChefDetailsFragmentArgs.fromBundle(getArguments()).getChef();
        CurrUser = MainActivity.context.getSharedPreferences("NOTIFY", Context.MODE_PRIVATE).getString("CurrentUser", "");

        if(chef == null){
            ChefModel.instance.getChef(CurrUser, new ChefModel.Listener<Chef>() {
                @Override
                public void OnComplete(Chef data) {
                    chef = data;
                    Log.d("TAG", "chef 1 :" + chef);

                    update_display();
                }
            });
        } else {
            chef_id = chef.getId();
            update_display();
        }

        return view;
    }

    private void update_display() {
        Log.d("TAG", "chef:" + chef);

        if (chef != null)
        {
            chef_name.setText(chef.getName());
            chef_desc.setText(chef.getDesc());

            if (!chef.getImgUrl().isEmpty()) {
                Picasso.get().load(chef.getImgUrl()).placeholder(R.drawable.avatar).into(chef_img);

            }
            else
            {
                chef_img.setImageResource(R.drawable.avatar);
            }

            chef_dishes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    NavController navController = Navigation.findNavController(getView());
                    NavGraphDirections.ActionGlobalDishListFragment action = DishListFragmentDirections.actionGlobalDishListFragment();
                    action.setChefList(chef.getId());
                    navController.navigate(action);
                }
            });

            Log.d("TAG", "chef id:" + chef.getId());

            if (CurrUser.equalsIgnoreCase(chef.getId()))
            {
                chef_edit.setVisibility(View.VISIBLE);
                chef_edit.setClickable(true);
                chef_edit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        chef_dishes.setVisibility(View.GONE);
                        chef_dishes.setClickable(false);
                        chef_dishes.setEnabled(false);
                        chef_save.setVisibility(View.VISIBLE);
                        chef_name.setEnabled(true);
                        chef_add_photo.setEnabled(true);
                        chef_add_photo.setVisibility(View.VISIBLE);
                        chef_name.setHint(R.string.chef_name_hint);
                        chef_desc.setEnabled(true);
                        chef_desc.setHint(R.string.chef_desc_hint);

                        chef_edit.setClickable(false);
                        chef_edit.setVisibility(View.GONE);
                    }
                });

                chef_save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        chef.setName(chef_name.getText().toString());
                        chef.setDesc(chef_desc.getText().toString());
                        ModelFirebase.addChef(chef, new ChefModel.Listener<Boolean>() {
                            @Override
                            public void OnComplete(Boolean data) {

                            }
                        });



                        chef_dishes.setVisibility(View.VISIBLE);
                        chef_dishes.setClickable(true);
                        chef_dishes.setEnabled(true);
                        chef_edit.setClickable(true);
                        chef_edit.setVisibility(View.VISIBLE);

                        chef_add_photo.setEnabled(false);
                        chef_add_photo.setVisibility(View.INVISIBLE);
                        chef_save.setVisibility(View.GONE);
                        chef_name.setEnabled(false);
                        chef_name.setHint("");
                        chef_desc.setEnabled(false);
                        chef_desc.setHint("");

                        Date d = new Date();
                        StoreModel.uploadImage(imageBitmap, "my_photo" + d.getTime(), new StoreModel.Listener() {
                            @Override
                            public void onSuccess(String url) {
                                Log.d("TAG", "url:" + url);
                                chef.setImgUrl(url);
                                ChefModel.instance.addChef(chef, new ChefModel.Listener<Boolean>() {
                                    @Override
                                    public void OnComplete(Boolean data) {
                                        if (data) {
                                            Log.d("NOTIFY", "added updated ");
                     //                       Toast.makeText(getActivity(), "changes saved", Toast.LENGTH_SHORT).show();
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

                    }
                });

                chef_add_photo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        takePhoto();
                    }
                });
            }
        }
    }

    final static int RESULT_SUCCESS = 0;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    public static final int PICK_IMAGE = 2;

    void uploadPhoto()
    {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, PICK_IMAGE);
    }

    void takePhoto() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            Log.d("NOTIFY", "Opened Camera");
        } else {
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

            chef_img.setImageBitmap(imageBitmap);
            Log.d("NOTIFY", "BITMAP SUCCESS");
        }
        else if (requestCode == PICK_IMAGE && resultCode == RESULT_OK)
        {
            if (data == null) {
                //Display an error
                return;
            }
            try {
                InputStream inputStream = context.getContentResolver().openInputStream(data.getData());
                imageBitmap = BitmapFactory.decodeStream(inputStream);
                chef_img.setImageBitmap(imageBitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            //Now you can do whatever you want with your inpustream, save it as file, upload to a server, decode a bitmap...
        }
        else
        {
            Log.d("NOTIFY", "BITMAP FAILED ");
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }

}