package com.instafood;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.instafood.model.Chef;
import com.instafood.model.Dish;
import com.instafood.model.ModelFirebase;

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
        final View view2 = view;

        // Variables
        firebaseAuth = FirebaseAuth.getInstance();
        //Button btnSignUp = view.findViewById(R.id.buttonSignupSignup);
        final EditText textSignupName = view.findViewById(R.id.textSignupFirstname);
        final EditText textSignupEmail = view.findViewById(R.id.textSignupEmail);
        final EditText textSignupPassword = view.findViewById(R.id.textSignupPassword);
        final EditText textSignupUsername = view.findViewById(R.id.textSignupUsername);
        //final NavController nav = Navigation.findNavController(view);
        ModelFirebase firebase = new ModelFirebase();
        //final ProgressBar progressBar = null;

        //LoginFragment lgFragment = (LoginFragment) getFragmentManager().findFragmentById(R.id.Loginfragment);

        Button btnSignUp = (Button)view.findViewById(R.id.buttonSignupSignup);

        // If  the use is already logged in enter MainActivity
        //if (firebaseAuth.getCurrentUser() != null)
        //{
        //     startActivity(new Intent(getContext(), MainActivity.class));
        //}

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String nname = textSignupName.getText().toString();
                final String email = textSignupEmail.getText().toString();
                String pwd = textSignupPassword.getText().toString();
                final String username = textSignupUsername.getText().toString();
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
                if (username.isEmpty())
                {
                    textSignupUsername.setError("Please Enter Username");
                    textSignupUsername.requestFocus();
                }

                //progressBar.setVisibility(view.VISIBLE);

                if (!(email.isEmpty() && pwd.isEmpty() && nname.isEmpty()))
                {
                    firebaseAuth.createUserWithEmailAndPassword(email,pwd).addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()){
                                //Toast.makeText(SignupFragment.this, "", Toast.LENGTH_SHORT).show();
                                Toast.makeText(getActivity(), "SignUp Unsuccessful", Toast.LENGTH_SHORT).show();
                            }
                            else{
                                // Create a new user with a first and last name
                                Map<String, Object> user = new HashMap<>();
                                user.put("firstname", "acb");
                                user.put("username", "abc");
                                user.put("email", "abc");
                                //NavController navController = Navigation.findNavController(view2);
                                //navController.navigate(R.id.action_signupFragment_to_dishListFragment);

                                ModelFirebase.db.collection("data").document("one")
                                        .set(user)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Chef cchef = new Chef(email, username, nname, null);
                                                NavController navController = Navigation.findNavController(view2);
                                                navController.navigate(R.id.action_signupFragment_to_dishListFragment);
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Toast.makeText(getActivity(), "DB SignUp Unsuccessful", Toast.LENGTH_SHORT).show();
                                            }
                                        });

                                //ModelFirebase.db.collection("users")
                                //        .set(user)
                                //        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                //            @Override
                                //            public void onSuccess(DocumentReference documentReference) {
                                //                Chef cchef = new Chef(email, username, nname, null);
                                //                NavController navController = Navigation.findNavController(view2);
                                //                navController.navigate(R.id.action_signupFragment_to_dishListFragment);
                                //            }
                                //        })
                                //        .addOnFailureListener(new OnFailureListener() {
                                //            @Override
                                //            public void onFailure(@NonNull Exception e) {
                                //                //Log.w("TAG", "Error adding document", e);
                                //            }
                                //        });



                               // // Add a new document with a generated ID
                               // ModelFirebase.db.collection("users")
                               //         .add(user)
                               //         .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                               //             @Override
                               //             public void onSuccess(DocumentReference documentReference) {
                               //                 //Intent intent = new Intent(getActivity(), LoginActivity.class);
                               //                 Chef cchef = new Chef(email, username, nname, null);
                               //                 //intent.putExtra("Chef", cchef);
                               //                 //intent.putExtra("email", email);
                               //                 //startActivity(new Intent(getActivity(), LoginActivity.class));
                               //                 //nav.navigate(R.id.action_signupFragment_to_dishListFragment);
                               //                 NavController navController = Navigation.findNavController(view2);
                               //                 navController.navigate(R.id.action_signupFragment_to_dishListFragment);
                               //             }
                               //         })
                               //         .addOnFailureListener(new OnFailureListener() {
                               //             @Override
                               //             public void onFailure(@NonNull Exception e) {
                               //                 Toast.makeText(getActivity(), "DB SignUp Unsuccessful", Toast.LENGTH_SHORT).show();
                               //             }
                               //         });
                            }//
                        }
                    });
                }
                else
                {
                    Toast.makeText(getActivity(), "Error Occured!", Toast.LENGTH_SHORT).show();
                }
            }
        });


        return view;
    }
}