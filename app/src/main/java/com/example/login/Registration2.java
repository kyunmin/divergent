package com.example.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Registration2 extends AppCompatActivity {


    EditText name, email, password, cpassword;
    TextView login, disclaimer;
    Button register;
    FirebaseAuth fAuth;
    ProgressBar progressBar;
    FirebaseFirestore fStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration2);

        name = findViewById(R.id.editName);
        email = findViewById(R.id.editEmail);
        password = findViewById(R.id.editPassword1);
        cpassword = findViewById(R.id.editPassword2);
        login = findViewById(R.id.textLogin);
        disclaimer = findViewById(R.id.textDisclaimer);
        register = findViewById(R.id.btnLogin);
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        progressBar = findViewById(R.id.progressBar);


        if (fAuth.getCurrentUser() != null) {
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }
    disclaimer.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            openDialog();
        }

        private void openDialog(){
            RegisterDialog registerDialog = new RegisterDialog();
            registerDialog.show(getSupportFragmentManager(), "register dialog");
        }
    });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Login.class));
                finish();
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String username = name.getText().toString().trim();
                String useremail = email.getText().toString().trim();
                String userpassword = password.getText().toString().trim();
                String usercpassword = cpassword.getText().toString().trim();
                progressBar.setVisibility(View.VISIBLE);
                if (TextUtils.isEmpty(username)) {
                    name.setError("Username is required.");
                    progressBar.setVisibility(View.GONE);
                    return;
                } else if (TextUtils.isEmpty(useremail)) {
                    email.setError("Email is required.");
                    progressBar.setVisibility(View.GONE);
                    return;
                } else if (TextUtils.isEmpty(userpassword)) {
                    password.setError("Password is required.");
                    progressBar.setVisibility(View.GONE);
                    return;
                } else if (TextUtils.isEmpty(usercpassword)) {
                    cpassword.setError("Please re-enter your password.");
                    progressBar.setVisibility(View.GONE);
                    return;
                } else if (!userpassword.equals(usercpassword)) {
                    cpassword.setError("Wrong password.");
                    progressBar.setVisibility(View.GONE);
                    return;
                } else if (userpassword.length() < 6) {
                    password.setError("Password must greater or equal to 6 characters.");
                    progressBar.setVisibility(View.GONE);
                    return;
                } else {
                    fAuth.createUserWithEmailAndPassword(useremail, userpassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                FirebaseUser user = fAuth.getCurrentUser();
                                Toast.makeText(Registration2.this, "Congratulation! You are our member now.", Toast.LENGTH_SHORT).show();
                                DocumentReference df = fStore.collection("Users").document(user.getUid());
                                Map<String, Object> userInfo = new HashMap<>();
                                userInfo.put("Name", name.getText().toString());
                                userInfo.put("Email", email.getText().toString());
                                userInfo.put("Password", password.getText().toString());
                                userInfo.put("isUser", "1");

                                df.set(userInfo);
                                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                            } else {
                                progressBar.setVisibility(View.GONE);
                                Toast.makeText(Registration2.this, "Error is occurred!" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
                }


            }
            private void openDialog(){
                RegisterDialog registerDialog = new RegisterDialog();
                registerDialog.show(getSupportFragmentManager(), "register dialog");
            }
        });
    }
}