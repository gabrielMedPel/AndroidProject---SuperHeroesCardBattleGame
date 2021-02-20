package gabriel.medpel.superheroescardbattlegame.ui.login;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import gabriel.medpel.superheroescardbattlegame.MainActivity;
import gabriel.medpel.superheroescardbattlegame.R;
import gabriel.medpel.superheroescardbattlegame.models.User;


public class SigninFragment extends Fragment {
    private Button btnRegister;
    private EditText etName, etEmailSign, etPasswordSign;

    private FirebaseAuth auth;
    private DatabaseReference myReff;
    private String userName;

    public SigninFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        auth = FirebaseAuth.getInstance();
        myReff = FirebaseDatabase.getInstance().getReference();
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_signin, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btnRegister = view.findViewById(R.id.btnRegister);
        etName = view.findViewById(R.id.etName);
        etEmailSign = view.findViewById(R.id.etEmailSign);
        etPasswordSign = view.findViewById(R.id.etPasswordSign);


        btnRegister.setOnClickListener(v -> {
            String inputEmail = etEmailSign.getText().toString();
            String inputPassword = etPasswordSign.getText().toString();
            String inputName = etName.getText().toString();

            if (inputEmail.isEmpty()) {
                Toast.makeText(getContext(), "You must type the e-mail.", Toast.LENGTH_SHORT).show();
            } else if (inputPassword.length() < 6) {
                Toast.makeText(getContext(), "Password must have at least 6 digits.", Toast.LENGTH_SHORT).show();
            } else if (inputName.isEmpty()) {
                Toast.makeText(getContext(), "Please Tell us your name !.", Toast.LENGTH_SHORT).show();
            } else {
                myReff.child("users").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        boolean emailExist = false;
                        boolean nameExist = false;
                        for (DataSnapshot snap : snapshot.getChildren()) {
                            String email = snap.child("email").getValue(String.class);
                            String name = snap.child("name").getValue(String.class);

                            if (inputEmail.equals(email)) {
                                Toast.makeText(getContext(), "This E-mail already exists in our DataBase!! Try to Log in!", Toast.LENGTH_LONG).show();
                                emailExist = true;
                            }
                            if (inputName.equals(name)) {
                                Toast.makeText(getContext(), "This UserName already exists in our DataBase!! Try another one!", Toast.LENGTH_LONG).show();
                                nameExist = true;
                            }
                        }
                        if (!emailExist && !nameExist) {
                            auth.createUserWithEmailAndPassword(inputEmail, inputPassword)
                                    .addOnCompleteListener(getActivity(), task -> {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(getContext(), "Successfully Registered and Logged In", Toast.LENGTH_LONG).show();

                                            User user = new User(inputName, inputEmail);


                                            auth.signInWithEmailAndPassword(inputEmail, inputPassword)
                                                    .addOnCompleteListener(task2 -> {
                                                        if (task.isSuccessful()) {
                                                            myReff.child("users").child(auth.getCurrentUser().getUid()).setValue(user);
                                                            startActivity(new Intent(getContext(), MainActivity.class));
                                                        } else {
                                                            Toast.makeText(getContext(), "Login Failed! Try Again!", Toast.LENGTH_LONG).show();
                                                        }
                                                    });

                                        } else {
                                            Toast.makeText(getContext(), "Registration Failed! Try Again", Toast.LENGTH_LONG).show();
                                        }
                                    });
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }

        });
    }
}