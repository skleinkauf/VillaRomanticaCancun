package com.example.villaromanticacancun;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class CreateAccount extends AppCompatActivity {
    private EditText EditTextYourName, EditTextYourPhone, EditTextYourEmail, EditTextYourPassword;
    private Button ButtonCreateAccount, ButtonMakeLogin;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog loadingBar;
    private DatabaseReference UserReference;
    String currentUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_account_activity);

        firebaseAuth = FirebaseAuth.getInstance();
      //  currentUserId = firebaseAuth.getUid();
        UserReference = FirebaseDatabase.getInstance().getReference();
        loadingBar = new ProgressDialog(this);


        EditTextYourName = findViewById(R.id.editTextYourNameCreateAccount);
        EditTextYourPhone = findViewById(R.id.editTextPhoneCreateAccount);
        EditTextYourEmail = findViewById(R.id.editTextEmailCreateAccount);
        EditTextYourPassword = findViewById(R.id.editTextPasswordCreateAccount);
        ButtonCreateAccount = findViewById(R.id.buttonCreateAccountCreateAccount);
        ButtonMakeLogin = findViewById(R.id.buttonMakeLoginCreateAccount);

        ButtonMakeLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SendToLoginPage();
            }
        });
        ButtonCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CreateNewAccount();
            }
        });
    }

    private void SendToLoginPage() {
        Intent sendToLogin = new Intent(this, MainActivity.class);
        startActivity(sendToLogin);
        finish();
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        if (currentUser != null)
        {
            SendUserToHome();
        }
    }

    private void SendUserToHome() {
        Intent homeIntent = new Intent(this, Home.class);
        homeIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(homeIntent);
        finish();
    }


    private void CreateNewAccount()
    {
        final String userName = EditTextYourName.getText().toString();
        final String userPhone = EditTextYourPhone.getText().toString();
        final String userEmail = EditTextYourEmail.getText().toString();
        final String userPassword = EditTextYourPassword.getText().toString();

        if (TextUtils.isEmpty(userName))
        {
            Toast.makeText(this, "Please enter a username", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(userPhone))
        {
            Toast.makeText(this, "Please enter your phone", Toast.LENGTH_SHORT).show();
        }
        if (TextUtils.isEmpty(userEmail))
        {
            Toast.makeText(this, "Please enter your email", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(userPassword))
        {
            Toast.makeText(this, "please enter a password", Toast.LENGTH_SHORT).show();
        }
        else
        {
            loadingBar.setTitle("Creating Your Account");
            loadingBar.setMessage("Please wait while we create your account...");
            loadingBar.show();
            loadingBar.setCanceledOnTouchOutside(true);

            firebaseAuth.createUserWithEmailAndPassword(userEmail, userPassword)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>()
                    {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task)
                        {
                            if (task.isSuccessful())
                            {
                                String userId = firebaseAuth.getCurrentUser().getUid();
                                DatabaseReference currentUserAccountCreated = FirebaseDatabase.getInstance().getReference().child("Users").child(userId);

                                String username = EditTextYourName.getText().toString();
                                String phone = EditTextYourPhone.getText().toString();
                                String email = EditTextYourEmail.getText().toString();
                                String password = EditTextYourPassword.getText().toString();

                                HashMap userMap = new HashMap();
                                userMap.put("Username", username);
                                userMap.put("Phone", phone);
                                userMap.put("Email", email);
                                userMap.put("Password", password);
                                currentUserAccountCreated.updateChildren(userMap).addOnCompleteListener(new OnCompleteListener() {
                                    @Override
                                    public void onComplete(@NonNull Task task) {
                                        if (task.isSuccessful()) {
                                            SendToLoginPage();
                                            Toast.makeText(CreateAccount.this, "Your account was created successfully", Toast.LENGTH_SHORT).show();
                                            loadingBar.dismiss();
                                        } else {
                                            String message = task.getException().getMessage();
                                            Toast.makeText(CreateAccount.this, "Error: " + message, Toast.LENGTH_SHORT).show();
                                            loadingBar.dismiss();
                                        }

                                    }
                                });


//                                SaveAccountInformation();
                                Toast.makeText(CreateAccount.this, "Your Account was successfully created", Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();
                            }
                            else
                            {
                                String messageError = task.getException().getMessage();
                                Toast.makeText(CreateAccount.this, "Error: " + messageError, Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();
                            }
                        }
                    });

        }
    }
//    private void SaveAccountInformation() {
//        String username = EditTextYourName.getText().toString();
//        String phone = EditTextYourPhone.getText().toString();
//        String email = EditTextYourEmail.getText().toString();
//        String password = EditTextYourPassword.getText().toString();

//        if (TextUtils.isEmpty(username)) {
//            username = "No Username";
//        }
//        if (TextUtils.isEmpty(phone)) {
//           phone = "000000000";
//        }
//        if (TextUtils.isEmpty(email)) {
//            email = "no email, no account";
//        }
//        if (TextUtils.isEmpty(password))
//        {
//            password = "no password no account";
//        }
//        else {

//            HashMap userMap = new HashMap();
//            userMap.put("Username", username);
//            userMap.put("Phone", phone);
//            userMap.put("Email", email);
//            userMap.put("Password", password);
//            UserReference.updateChildren(userMap).addOnCompleteListener(new OnCompleteListener() {
//                @Override
//                public void onComplete(@NonNull Task task) {
//                    if (task.isSuccessful()) {
//                        SendToLoginPage();
//                        Toast.makeText(CreateAccount.this, "Your account was created successfully", Toast.LENGTH_SHORT).show();
//                        loadingBar.dismiss();
//                    } else {
//                        String message = task.getException().getMessage();
//                        Toast.makeText(CreateAccount.this, "Error: " + message, Toast.LENGTH_SHORT).show();
//                        loadingBar.dismiss();
//                    }
//
//                }
//            });

//        }
//    }
}
