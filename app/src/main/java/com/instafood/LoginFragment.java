package com.instafood;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.instafood.model.ChefModel;
import com.instafood.model.Dish;
import com.instafood.model.ModelFirebase;

import java.util.concurrent.Executor;

import static android.content.Context.MODE_PRIVATE;

public class LoginFragment extends Fragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    public LoginFragment() {
        // Required empty public constructor
    }

    public static LoginFragment newInstance() {
        LoginFragment fragment = new LoginFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) { super.onCreate(savedInstanceState); }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_login, container, false);
        final View view2 = view;
        // Hide Action Bar
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();

        LinearLayout linearl = view.findViewById(R.id.loginlayout);
        AnimationDrawable ad = (AnimationDrawable) linearl.getBackground();
        ad.setEnterFadeDuration(2000);
        ad.setExitFadeDuration(4000);
        ad.start();

        Button btnLoginSignup = (Button) view.findViewById(R.id.buttonLoginSignup);
        Button btnLogin = view.findViewById(R.id.buttonLoginLogin);
        final EditText textLoginEmail = view.findViewById(R.id.textLoginUsername);
        final EditText textLoginPassword = view.findViewById(R.id.textLoginPassword);

        // Open SignUp fragment when clicking
        btnLoginSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View V) {
                NavController navController = Navigation.findNavController(view);
                navController.navigate(R.id.action_loginFragment_to_signupFragment);

            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                final String email = textLoginEmail.getText().toString();
                String pwd = textLoginPassword.getText().toString();

                if (email.isEmpty()) {
                    textLoginEmail.setError("Please Enter Email");
                    textLoginEmail.requestFocus();
                }
                if (pwd.isEmpty()) {
                    textLoginPassword.setError("Please Enter Password");
                    textLoginPassword.requestFocus();
                }

                final NavController navController = Navigation.findNavController(getView());

                if (!(email.isEmpty() && pwd.isEmpty())) {
                    // Try Login and authenticate with DB
                    ChefModel.instance.authUser(email, pwd, new ChefModel.Listener<Boolean>() {
                        @Override
                        public void OnComplete(Boolean data) {
                            if (data) {
                                SharedPreferences.Editor edit = MainActivity.context.getSharedPreferences("NOTIFY", MODE_PRIVATE).edit();
                                edit.putString("CurrentUser", email);
                                Log.d("NOTIFY", "CurrentUser " + email);
                                edit.commit();

                                // TODO: uncomment if no back to login possible
                                // navController.popBackStack();
                                navController.navigate(R.id.action_loginFragment_to_dishListFragment);

                            } else {
                                Toast.makeText(getActivity(), "Something went wrong!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
        return view;
    }
}