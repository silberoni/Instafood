package com.instafood;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

// TODO: will recieve a list/pointer to a list of dish posts to show. We can reuse the same fragment with different data.
public class DishListFragment extends Fragment {

    private RecyclerView dish_list;
    private Context con;


    public DishListFragment() {
        // Required empty public constructor
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
        //TODO: What is the difference bewtween this and OnCreateView
        //con = getApplicationContext();
        //RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(con);
        //dishList.setLayoutManager(layoutManager);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dish_list, container, false);
        dish_list = view.findViewById(R.id.dish_list_rv);
        dish_list.setHasFixedSize(true);
        return view;
    }


    // static class DishViewHolder extends RecyclerView.ViewHolder{

    //}

    //class dishAdapter extends RecyclerView.Adapter<> {
    //}
}