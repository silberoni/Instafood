package com.instafood;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
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
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.instafood.model.Dish;
import com.instafood.model.DishModel;

import java.util.LinkedList;
import java.util.List;

// TODO: will recieve a list/pointer to a list of dish posts to show. We can reuse the same fragment with different data.
public class DishListFragment extends Fragment {
    private RecyclerView dish_list;
    private List<Dish> data = new LinkedList<Dish>();
    private DishListViewModel viewModel;
    private dishListAdapter adptr;
    private LiveData<List<Dish>> liveData;


    public DishListFragment() {
    }

    // TODO: is needed?
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
        // TODO: finish building the option menu
        setHasOptionsMenu(true);

        // TODO: use live data
        viewModel = new ViewModelProvider(this).get(DishListViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_dish_list, container, false);

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

        liveData = viewModel.getData();
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
        return view;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    static class DishViewHolder extends RecyclerView.ViewHolder {
        Dish dish;
        TextView nametv;

        public DishViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
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
}