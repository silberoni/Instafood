package com.instafood;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import com.instafood.model.ChefModel;
import com.instafood.model.ModelFirebase;

import static android.content.Context.MODE_PRIVATE;

public class SignupFragment extends Fragment {

    public SignupFragment() {
        // Required empty public constructor
    }

    // TODO: I think can be deleted
    public static SignupFragment newInstance() {
        SignupFragment fragment = new SignupFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_signup, container, false);
        final View view2 = view;

        final EditText textSignupName = view.findViewById(R.id.textSignupFirstname);
        final EditText textSignupEmail = view.findViewById(R.id.textSignupEmail);
        final EditText textSignupPassword = view.findViewById(R.id.textSignupPassword);
        final EditText textSignupUsername = view.findViewById(R.id.textSignupUsername);

        Button btnSignUp = (Button) view.findViewById(R.id.buttonSignupSignup);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String nname = textSignupName.getText().toString();
                final String email = textSignupEmail.getText().toString();
                String pwd = textSignupPassword.getText().toString();
                final String username = textSignupUsername.getText().toString();
                if (nname.isEmpty()) {
                    textSignupName.setError("Please Enter Name");
                    textSignupName.requestFocus();
                }
                if (email.isEmpty()) {
                    textSignupEmail.setError("Please Enter Email");
                    textSignupEmail.requestFocus();
                }
                if (pwd.isEmpty()) {
                    textSignupPassword.setError("Please Enter Password");
                    textSignupPassword.requestFocus();
                }
                if (username.isEmpty()) {
                    textSignupUsername.setError("Please Enter Username");
                    textSignupUsername.requestFocus();
                }

                final NavController navController = Navigation.findNavController(getView());

                if (!(email.isEmpty() && pwd.isEmpty() && nname.isEmpty())) {
                    ChefModel.instance.createUser(email, pwd, nname, new ChefModel.Listener<Boolean>() {
                        @Override
                        public void OnComplete(Boolean data) {
                            if (data){
                                SharedPreferences.Editor edit = MainActivity.context.getSharedPreferences("NOTIFY", MODE_PRIVATE).edit();
                                edit.putString("CurrentUser", email);
                                Log.d("NOTIFY", "CurrentUser "+ email);
                                edit.commit();

                                // TODO: uncomment if no back to login possible
                                // navController.popBackStack();
                                navController.popBackStack();
                                navController.navigate(R.id.action_global_dishListFragment);


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