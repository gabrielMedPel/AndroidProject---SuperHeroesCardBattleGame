package gabriel.medpel.superheroescardbattlegame.ui.login;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import gabriel.medpel.superheroescardbattlegame.MainActivity;
import gabriel.medpel.superheroescardbattlegame.R;

public class LoginActivity extends AppCompatActivity {
    private FirebaseAuth auth;
    private EditText usernameEditText;
    private EditText passwordEditText;
    private Button loginButton;
    private Button registrationButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usernameEditText = findViewById(R.id.etEmail);
        passwordEditText = findViewById(R.id.etPassword);
        loginButton = findViewById(R.id.btnLogin);
        registrationButton = findViewById(R.id.btnSign);
        auth = FirebaseAuth.getInstance();

        loginButton.setOnClickListener(v -> {
            if (passwordEditText.getText().toString().length() < 6) {
                Toast.makeText(this, "Password must have at least 6 digits.", Toast.LENGTH_SHORT).show();
            } else if (usernameEditText.getText().toString().isEmpty()) {
                Toast.makeText(this, "You must type your e-mail.", Toast.LENGTH_SHORT).show();
            } else {
                auth.signInWithEmailAndPassword(usernameEditText.getText().toString(), passwordEditText.getText().toString())
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                Toast.makeText(this, "Successfully Logged In", Toast.LENGTH_LONG).show();
                                startActivity(new Intent(this, MainActivity.class));
                            } else {
                                Toast.makeText(this, "Login Failed! Try Again!", Toast.LENGTH_LONG).show();
                            }
                        });
            }

        });

        registrationButton.setOnClickListener(v -> {
            SigninFragment fragment = new SigninFragment();
            AppCompatActivity activity = (AppCompatActivity) v.getContext();
            activity.getSupportFragmentManager()
                    .beginTransaction()
                    .setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
                    .replace(R.id.loginContainer, fragment).addToBackStack(null)
                    .commit();
        });
    }
}