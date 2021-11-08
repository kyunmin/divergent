package com.example.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class Login extends AppCompatActivity {

    EditText email, password;
    Button login;
    //yunmin
    TextView register;
    ProgressBar progressBar;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    private FirebaseAuth mAuth;

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = mAuth.getCurrentUser();
        if(user !=null){
            Intent intent = new Intent (getApplicationContext(),MainActivity.class);
            startActivity(intent);
        }
    }

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = findViewById(R.id.editEmail);
        password = findViewById(R.id.editPassword);
        register = findViewById(R.id.textRegister);
        login = findViewById(R.id.btnLogin);
        progressBar = findViewById(R.id.progressBar2);
        mAuth=FirebaseAuth.getInstance();
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();





        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getApplicationContext(), Registration2.class));
                finish();
            }

            private void openDialog() {
                RegisterDialog registerDialog = new RegisterDialog();
                registerDialog.show(getSupportFragmentManager(), "register dialog");
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String useremail = email.getText().toString().trim();
                String userpassword = password.getText().toString().trim();

                if(TextUtils.isEmpty(useremail)){
                    email.setError("Email is required.");
                    return;
                }
                if(TextUtils.isEmpty(userpassword)){
                    password.setError("Password is required.");
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);

                fAuth.signInWithEmailAndPassword(useremail,userpassword).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        Toast.makeText(Login.this, "Login Successfully",Toast.LENGTH_SHORT).show();
                        checkUserAccessLevel(authResult.getUser().getUid());

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Login.this,"Wrong Info!",Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                    }
                });

                    }
                });





    }







    private void checkUserAccessLevel(String uid) {
        DocumentReference df = fStore.collection("Users").document(uid);
        df.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
            Log.d("TAG", "onSuccess"+documentSnapshot.getData());

            if (documentSnapshot.getString("isCompany" )!= null){
                startActivity(new Intent(getApplicationContext(),Company.class));
                finish();
                }
               else if (documentSnapshot.getString("isRestaurant" )!= null){
                    startActivity(new Intent(getApplicationContext(),Restaurant.class));
                    finish();
                }
               else if (documentSnapshot.getString("isUser" )!= null){
                    startActivity(new Intent(getApplicationContext(),MainActivity.class));
                    finish();
                }
            }
        });
    }
}