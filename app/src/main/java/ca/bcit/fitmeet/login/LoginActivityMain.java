package ca.bcit.fitmeet.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Map;

import ca.bcit.fitmeet.MainActivity;
import ca.bcit.fitmeet.R;
import ca.bcit.fitmeet.User;

public class LoginActivityMain extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkIfUserIsLoggedIn();
        setContentView(R.layout.activity_login_main);

        Button loginBtn, signUpBtn;
        loginBtn = findViewById(R.id.btn_login);
        signUpBtn = findViewById(R.id.btn_signup);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivityMain.this, PostLoginActivity.class));
            }
        });

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivityMain.this, SignupActivity.class));
            }
        });
    }

    private void checkIfUserIsLoggedIn() {
        FirebaseAuth auth;
        auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser() != null) {
            startActivity(new Intent(LoginActivityMain.this, MainActivity.class));
            finish();
        }
    }

}