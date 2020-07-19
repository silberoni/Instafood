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

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.instafood.model.Dish;

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
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    //public FirebaseAuth firebaseAuth;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public LoginFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LoginFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LoginFragment newInstance(String param1, String param2) {
        LoginFragment fragment = new LoginFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);

        }

        //firebase


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view =  inflater.inflate(R.layout.fragment_login, container, false);
        final View view2 = view;

        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();

        firebaseAuth = FirebaseAuth.getInstance();
        Button btnLoginSignup = (Button)view.findViewById(R.id.buttonLoginSignup);
        Button btnLogin = view.findViewById(R.id.buttonLoginLogin);
        final EditText textLoginEmail = view.findViewById(R.id.textLoginUsername);
        //final EditText textLoginEmail = view.findViewById(R.id.textLoginEmail);
        final EditText textLoginPassword = view.findViewById(R.id.textLoginPassword);
       // final NavController nav = Navigation.findNavController(view);


        // Open SignUp fragment when clicking
        btnLoginSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View V){
                //FragmentTransaction fr = getFragmentManager().beginTransaction();
                //fr.replace(R.id.Login_Container, new SignupFragment());
                //fr.addToBackStack(null).commit();

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

                    // save the user information
                    SharedPreferences.Editor edit = MainActivity.context.getSharedPreferences("NOTIFY", MODE_PRIVATE).edit();
                    edit.putString("CurrentUser", mFirebaseUser.getUid());
                    edit.commit();

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
                //String nname = textLoginName.getText().toString();;
                String email = textLoginEmail.getText().toString();
                String pwd = textLoginPassword.getText().toString();

                //if(nname.isEmpty())
                //{
                //    textSignupName.setError("Please Enter Name");
                //    textSignupName.requestFocus();
                //}
                if (email.isEmpty()) {
                    textLoginEmail.setError("Please Enter Email");
                    textLoginEmail.requestFocus();
                }
                if (pwd.isEmpty()) {
                    textLoginPassword.setError("Please Enter Password");
                    textLoginPassword.requestFocus();
                }

                if (!(email.isEmpty() && pwd.isEmpty())) {
                    firebaseAuth.signInWithEmailAndPassword(email, pwd).addOnCompleteListener((Executor) LoginFragment.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()){
                                Toast.makeText(getActivity(), "Login Error, Please Try Again", Toast.LENGTH_SHORT).show();
                            }
                            else{
                                //startActivity(new Intent(getContext(), MainActivity.class));
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