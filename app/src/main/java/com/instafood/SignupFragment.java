package com.instafood;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

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

import java.util.concurrent.Executor;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SignupFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SignupFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public FirebaseAuth firebaseAuth;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SignupFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SignupFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SignupFragment newInstance(String param1, String param2) {
        SignupFragment fragment = new SignupFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //if (getArguments() != null) {
        //    mParam1 = getArguments().getString(ARG_PARAM1);
        //    mParam2 = getArguments().getString(ARG_PARAM2);


        //}
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_signup, container, false);

        firebaseAuth = FirebaseAuth.getInstance();
        Button btnSignUp = view.findViewById(R.id.buttonSignupSignup);
        final EditText textSignupName = view.findViewById(R.id.textSignupFirstname);
        final EditText textSignupEmail = view.findViewById(R.id.textSignupEmail);
        final EditText textSignupPassword = view.findViewById(R.id.textSignupPassword);

        LoginFragment lgFragment = (LoginFragment) getFragmentManager().findFragmentById(R.id.Loginfragment);

        btnSignUp = (Button)view.findViewById(R.id.buttonSignupSignup);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nname = textSignupName.getText().toString();;
                String email = textSignupEmail.getText().toString();
                String pwd = textSignupPassword.getText().toString();

                if(nname.isEmpty())
                {
                    textSignupName.setError("Please Enter Name");
                    textSignupName.requestFocus();
                }
                if(email.isEmpty())
                {
                     textSignupEmail.setError("Please Enter Email");
                     textSignupEmail.requestFocus();
                }
                if(pwd.isEmpty())
                {
                    textSignupPassword.setError("Please Enter Password");
                    textSignupPassword.requestFocus();
                }

                if (!(email.isEmpty() && pwd.isEmpty() && nname.isEmpty()))
                {
                    firebaseAuth.createUserWithEmailAndPassword(email,pwd).addOnCompleteListener((Executor) SignupFragment.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()){
                                //Toast.makeText(SignupFragment.this, "", Toast.LENGTH_SHORT).show();
                                Toast.makeText(getContext(), "SignUp Unsuccessful", Toast.LENGTH_SHORT).show();
                            }
  //                          else{
//                                startActivity(new Intent());

    //                        }

                        }
                    });
                }
                else
                {
                    Toast.makeText(getContext(), "Error Occured!", Toast.LENGTH_SHORT).show();
                }




            }
        });

        return view;
    }
}