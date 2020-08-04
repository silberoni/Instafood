package com.instafood;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.android.material.snackbar.Snackbar;
import com.instafood.model.Dish;
import com.instafood.model.DishModel;
import com.instafood.model.StoreModel;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Date;
import java.util.UUID;

import static android.app.Activity.RESULT_OK;
import static com.instafood.MainActivity.context;

public class DishAddFragment extends Fragment {
    private Dish dishNew;
    private Dish dishBased;
    ImageView dish_img;
    EditText dish_name;
    EditText dish_desc;
    EditText dish_sec_1;
    EditText dish_sec_2;
    Button dish_save;
    Button dish_add_photo;
    Button dish_upload_photo;
    String chef_id;
    Bitmap imageBitmap;
    ImageView imageView;

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
        dish_add_photo = view.findViewById(R.id.fragment_dish_add_photo_btn);
        dish_upload_photo = view.findViewById(R.id.fragment_dish_upload_photo_from_gallery_btn);

        chef_id = context.getSharedPreferences("NOTIFY", Context.MODE_PRIVATE).getString("CurrentUser", "");

        dishBased = DishAddFragmentArgs.fromBundle(getArguments()).getDish();
        if (dishBased != null) {
            update_display();
        }

        dish_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                // How do we get a new ID each time?
                if (checkFields()) {

                    String id = UUID.randomUUID().toString();

                    dishNew = new Dish(id);
                    dishNew.setName(dish_name.getText().toString());
                    dishNew.setDesc(dish_desc.getText().toString());
                    // picture
                    dishNew.setMakerID(chef_id);
                    if (dishBased != null) {
                        dishNew.setBasedOn(dishBased.getId());
                    }
                    dishNew.setIngredients(dish_sec_1.getText().toString());
                    dishNew.setInstructions(dish_sec_2.getText().toString());

                    Date d = new Date();
                    StoreModel.uploadImage(imageBitmap, "my_photo" + d.getTime(), new StoreModel.Listener() {
                        @Override
                        public void onSuccess(String url) {
                            Log.d("TAG", "url:" + url);
                            dishNew.setImgUrl(url);
                            DishModel.instance.addDish(dishNew, new DishModel.Listener<Boolean>() {
                                @Override
                                public void onComplete(Boolean data) {
                                    if (data) {
                                        Log.d("NOTIFY", "added dish ");
                                       // Snackbar.make(view, "Dish added", Snackbar.LENGTH_SHORT).show();
                                      //  Toast.makeText(getActivity(), "Dish added", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Log.d("NOTIFY", "Something went wrong ");
                                    }
                                }
                            });
                        }

                        @Override
                        public void onFail() {
                            Log.d("NOTIFY", "Something went wrong with adding a photo");
                        }
                    });
                    NavController navCtrl = Navigation.findNavController(view);
                    navCtrl.popBackStack();
                } else {
                    Toast.makeText(getActivity(), "Cannot leave empty fields", Toast.LENGTH_SHORT).show();
                }
            }
        });

        dish_add_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                takePhoto();
                //Toast.makeText(getActivity(), "No photos for you", Toast.LENGTH_SHORT).show();
                Log.d("NOTIFY", "No photos for you ");
            }
        });

        dish_upload_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadPhoto();
                //Toast.makeText(getActivity(), "No photos for you", Toast.LENGTH_SHORT).show();
                Log.d("NOTIFY", "Problem with uploading photo ");
            }
        });
        return view;
    }

    private void update_display() {
        dish_name.setText(dishBased.getName() + "  -  version by" + chef_id);
        dish_desc.setText(dishBased.getDesc());
        dish_sec_1.setText(dishBased.getIngredients());
        dish_sec_2.setText(dishBased.getInstructions());
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    static final int REQUEST_IMAGE_CAPTURE = 1;
    final static int RESULT_SUCCESS = 0;
    public static final int PICK_IMAGE = 2;

    public Boolean checkFields() {
        if ((!dish_name.getText().toString().isEmpty()) &&
                (!dish_desc.getText().toString().isEmpty()) &&
                (!dish_sec_1.getText().toString().isEmpty()) &&
                (!dish_sec_2.getText().toString().isEmpty())) {
            return true;
        } else {
            Log.d("NOTIFY", "Cannot leave empty fields");
            return false;
        }
    }

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

            dish_img.setImageBitmap(imageBitmap);
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
                dish_img.setImageBitmap(imageBitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            //Now you can do whatever you want with your inpustream, save it as file, upload to a server, decode a bitmap...
        }
        else {
            Log.d("NOTIFY", "BITMAP FAILED ");
        }
    }
}