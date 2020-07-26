package com.instafood;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.instafood.model.Dish;
import com.instafood.model.DishModel;
import com.squareup.picasso.Picasso;

import java.util.LinkedList;
import java.util.List;

public class DishListFragment extends Fragment {
    private RecyclerView dish_list;
    private List<Dish> data = new LinkedList<Dish>();
    private DishListViewModel viewModel;
    private dishListAdapter adptr;
    private LiveData<List<Dish>> liveData;
    String chef_id;


    public DishListFragment() {
    }

    public static DishListFragment newInstance() {
        DishListFragment fragment = new DishListFragment();
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
        viewModel = new ViewModelProvider(this).get(DishListViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_dish_list, container, false);

        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
        dish_list = view.findViewById(R.id.fragment_dish_list_rv);
        dish_list.setHasFixedSize(true);

        LinearLayoutManager layoutmngr = new LinearLayoutManager(getContext());
        dish_list.setLayoutManager(layoutmngr);

        adptr = new dishListAdapter();
        dish_list.setAdapter(adptr);

        adptr.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onClick(int position) {
                Dish dish = data.get(position);
                NavController navController = Navigation.findNavController(view);
                DishListFragmentDirections.ActionDishListFragmentToDishDetailsFragment dir = DishListFragmentDirections.actionDishListFragmentToDishDetailsFragment(dish);
                navController.navigate(dir);
            }
        });

        chef_id = DishListFragmentArgs.fromBundle(getArguments()).getChefList();
        if(chef_id.equalsIgnoreCase("null")) {
            liveData = viewModel.getAllData();
            liveData.observe(getViewLifecycleOwner(), new Observer<List<Dish>>() {
                @Override
                public void onChanged(List<Dish> dishes) {
                    data = dishes;
                    adptr.notifyDataSetChanged();
                }
            });


            final SwipeRefreshLayout swipeRefresh = view.findViewById(R.id.dish_list_swipe_refresh);
            swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    viewModel.refresh(new DishModel.LDListener() {
                        @Override
                        public void onComplete() {
                            swipeRefresh.setRefreshing(false);
                        }
                    });
                }
            });
        } else
        {
            liveData = viewModel.getChefData(chef_id);
            liveData.observe(getViewLifecycleOwner(), new Observer<List<Dish>>() {
                @Override
                public void onChanged(List<Dish> dishes) {
                    data = dishes;
                    adptr.notifyDataSetChanged();
                }
            });
            final SwipeRefreshLayout swipeRefresh = view.findViewById(R.id.dish_list_swipe_refresh);
            swipeRefresh.setEnabled(false);
        }
        return view;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    static class DishViewHolder extends RecyclerView.ViewHolder {
        Dish dish;
        TextView nametv;
        ImageView image;


        public DishViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            image = itemView.findViewById(R.id.fragment_dish_row_image_iv);
            nametv = itemView.findViewById(R.id.dish_row_name_tv);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int pos = getAdapterPosition();
                        if (pos != RecyclerView.NO_POSITION) {
                            listener.onClick(pos);
                        }
                    }
                }
            });
        }
        void bind(Dish dish) {
            this.dish = dish;
            nametv.setText(dish.getName());
            if (dish.getImgUrl() != null && dish.getImgUrl() != "") {
                Picasso.get().load(dish.getImgUrl()).placeholder(R.drawable.avatar).into(image);
            }
            else
            {
                image.setImageResource(R.drawable.avatar);
            }
        }
    }

    interface OnItemClickListener{
        void onClick(int position);
    }

    class dishListAdapter extends RecyclerView.Adapter<DishViewHolder> {
        private OnItemClickListener listener;

        void setOnItemClickListener(OnItemClickListener listener){
            this.listener = listener;
        }
        @NonNull
        @Override
        public DishViewHolder onCreateViewHolder(@NonNull ViewGroup viewgroup, int viewType) {
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.dish_list_row, viewgroup, false);
            DishViewHolder vh = new DishViewHolder(view, listener);
            return vh;
        }

        @Override
        public void onBindViewHolder(@NonNull DishViewHolder holder, int position) {
            Dish dsh = data.get(position);
            holder.bind(dsh);
        }

        @Override
        public int getItemCount() {
            return data.size();
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }


    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        NavController navController = Navigation.findNavController(getView());
        switch (item.getItemId()){
            case R.id.menu_add_dish:
                navController.navigate(DishAddFragmentDirections.actionGlobalDishAddFragment());
                break;
            case R.id.menu_home:
                navController.navigate(DishListFragmentDirections.actionGlobalDishListFragment());
                break;
            case R.id.menu_user:
                String Current =MainActivity.context.getSharedPreferences("NOTIFY", Context.MODE_PRIVATE).getString("CurrentUser", "");
                NavGraphDirections.ActionGlobalChefDetailsFragment action = ChefDetailsFragmentDirections.actionGlobalChefDetailsFragment();
                action.setChefId(Current);
                navController.navigate(action);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}