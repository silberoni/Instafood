package com.instafood;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.instafood.model.Dish;
import com.instafood.model.DishModel;

import java.util.List;

// TODO: will recieve a list/pointer to a list of dish posts to show. We can reuse the same fragment with different data.
public class DishListFragment extends Fragment {
    private RecyclerView dish_list;
    private List<Dish> data;
    private delegate parent;

    interface delegate {
        void onItemSelected(Dish dish);
    }

    public DishListFragment() {
        data = DishModel.instance.getAllDishes();
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
        if (context instanceof delegate) {
            parent = (delegate) getActivity();
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement Delegate");
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dish_list, container, false);


        dish_list = view.findViewById(R.id.fragment_dish_list_rv);
        dish_list.setHasFixedSize(true);

        LinearLayoutManager layoutmngr = new LinearLayoutManager(getContext());
        dish_list.setLayoutManager(layoutmngr);

        dishListAdapter adptr = new dishListAdapter();
        dish_list.setAdapter(adptr);

        adptr.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onClick(int position) {
                Log.d("TAG","row was clicked" + position);
                Dish dish = data.get(position);
                parent.onItemSelected(dish);
            }
        });


        return view;
    }
    @Override
    public void onDetach() {
        super.onDetach();
        parent = null;
    }

    static class DishViewHolder extends RecyclerView.ViewHolder {
        Dish dish;
        TextView nametv;
        CheckBox cb;

        public DishViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            nametv = itemView.findViewById(R.id.dish_row_name_tv);
            cb = itemView.findViewById(R.id.dish_row_check_cb);
            cb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dish.setChecked(cb.isChecked());
                }
            });
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
            cb.setChecked(dish.isChecked());
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
}