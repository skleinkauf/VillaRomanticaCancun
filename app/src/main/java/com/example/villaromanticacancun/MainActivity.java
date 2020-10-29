package com.example.villaromanticacancun;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    private EditText EditTextEmail, EditTextPassword;
    private Button ButtonLogin, ButtonCreateAccount;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseAuth = FirebaseAuth.getInstance();
        loadingBar = new ProgressDialog(this);

        EditTextEmail = findViewById(R.id.editTextEmailMain);
        EditTextPassword = findViewById(R.id.editTextPasswordMain);
        ButtonLogin = findViewById(R.id.buttonMakeLoginMain);
        ButtonCreateAccount = findViewById(R.id.buttonCreatAccountMain);

        ButtonCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SendToCreateAccountActivity();
            }
        });

        ButtonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AllowingUserToLogin();
            }
        });
    }
    @Override
    protected void onStart()
    {
        super.onStart();
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        if (currentUser != null)
        {
            UserLoginSendToHomePage();
        }
    }

    private void AllowingUserToLogin()
    {
        String email = EditTextEmail.getText().toString();
        String password = EditTextPassword.getText().toString();

        if (TextUtils.isEmpty(email))
        {
            Toast.makeText(this, "Please write your email", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(password))
        {
            Toast.makeText(this, "please enter your password", Toast.LENGTH_SHORT).show();
        }
        else
        {
            loadingBar.setTitle("Making Login...");
            loadingBar.setMessage("Please wait while we are making login in your account...");
            loadingBar.show();
            loadingBar.setCanceledOnTouchOutside(true);

            firebaseAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful())
                            {
                                UserLoginSendToHomePage();
                                Toast.makeText(MainActivity.this, "Login Successfully", Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();
                            }
                            else
                            {
                                String message = task.getException().getMessage();
                                Toast.makeText(MainActivity.this, "Error: " + message, Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();
                            }
                        }
                    });
        }
    }

    private void UserLoginSendToHomePage() {
        Intent sendToHome = new Intent(this, Home.class);
        startActivity(sendToHome);
        finish();
    }

    private void SendToCreateAccountActivity() {
        Intent sendToCreateAccount = new Intent(this, CreateAccount.class);
        startActivity(sendToCreateAccount);
        finish();
    }

}