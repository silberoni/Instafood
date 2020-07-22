package com.instafood;

import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.instafood.model.Dish;
import com.instafood.model.ModelFirebase;

import java.util.concurrent.Executor;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LoginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    public FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    //public FirebaseAuth firebaseAuth;

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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //firebase


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view =  inflater.inflate(R.layout.fragment_login, container, false);
        final View view2 = view;

        // Hide Action Bar
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
        ModelFirebase firebase = new ModelFirebase();

        firebaseAuth = FirebaseAuth.getInstance();
        Button btnLoginSignup = (Button)view.findViewById(R.id.buttonLoginSignup);
        Button btnLogin = view.findViewById(R.id.buttonLoginLogin);
        final EditText textLoginEmail = view.findViewById(R.id.textLoginUsername);
        final EditText textLoginPassword = view.findViewById(R.id.textLoginPassword);



        // Open SignUp fragment when clicking
        btnLoginSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View V){
                NavController navController = Navigation.findNavController(view);
                navController.navigate(R.id.action_loginFragment_to_signupFragment);

            }
        });

        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser mFirebaseUser = firebaseAuth.getCurrentUser();
                if (mFirebaseUser != null){
                    Toast.makeText(getContext(), "You are logged in", Toast.LENGTH_SHORT).show();



                    Intent i = new Intent (getContext(), MainActivity.class);
                    startActivity(i);
                }
                else
                {
                    Toast.makeText(getContext(), "Please Login", Toast.LENGTH_SHORT).show();
                }
            }
        };

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = textLoginEmail.getText().toString();
                String pwd = textLoginPassword.getText().toString();

                if (email.isEmpty()) {
                    textLoginEmail.setError("Please Enter Email");
                    textLoginEmail.requestFocus();
                }
                if (pwd.isEmpty()) {
                    textLoginPassword.setError("Please Enter Password");
                    textLoginPassword.requestFocus();
                }

                if (!(email.isEmpty() && pwd.isEmpty())) {
                    // Try Login and authenticate with DB
                    firebaseAuth.signInWithEmailAndPassword(email, pwd).addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()){
                                Log.d("NOTIFY", String.valueOf(task.getException()));
                                Toast.makeText(getActivity(), "Login Error, Please Try Again", Toast.LENGTH_SHORT).show();
                            }
                            else{
                                // save the user information
                                SharedPreferences.Editor edit = MainActivity.context.getSharedPreferences("NOTIFY", MODE_PRIVATE).edit();
                                edit.putString("CurrentUser", task.getResult().getUser().getUid());
                                edit.commit();

                                NavController navController = Navigation.findNavController(view2);
                                navController.navigate(R.id.action_loginFragment_to_dishListFragment);
                            }
                        }
                    });
                }
                else
                {
                    Toast.makeText(getActivity(), "Error Occured", Toast.LENGTH_SHORT).show();
                }
            }
        });

                return view;
    }
}