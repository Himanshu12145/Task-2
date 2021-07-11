package com.example.todolist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

/**
 * @author himan
 */
public class MainActivity2_Register_User extends AppCompatActivity implements View.OnClickListener {
    private FirebaseAuth mAuth;
    private TextView banner,registerUser;
    private EditText editTextFullName,editTextAge,editTextEmail,editTextPassword;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activity2_register_user);

        mAuth = FirebaseAuth.getInstance();
        banner = findViewById(R.id.banner);
        banner.setOnClickListener(this);
        registerUser = findViewById(R.id.registerUser);
        registerUser.setOnClickListener(this);

        editTextAge=findViewById(R.id.age);
        editTextFullName=findViewById(R.id.fullname);
        editTextEmail=findViewById(R.id.email);
        editTextPassword=findViewById(R.id.password);
        progressBar = findViewById(R.id.progressBar);


    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.banner:
                startActivity(new Intent(this,MainActivity.class));
                break;
            case R.id.registerUser:
                registerUser();
                break;
            default:
                break;
        }

    }

    private void registerUser() {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String fullName = editTextFullName.getText().toString().trim();
        String age = editTextAge.getText().toString().trim();

        if (fullName.isEmpty()){
            editTextFullName.setError("Full Name is Required");
            editTextFullName.requestFocus();
            return;
        }
        if (age.isEmpty()){
            editTextAge.setError("Age is Required");
            editTextAge.requestFocus();
            return;
        }
        if (Integer.parseInt(age) >120 || Integer.parseInt(age)< 9 ){
            editTextAge.setError("Please Enter a valid age ");
            editTextAge.requestFocus();
            return;
        }
        if (email.isEmpty()){
            editTextEmail.setError("Email is Required");
            editTextEmail.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editTextEmail.setError("Please Provide Valid Email");
            editTextEmail.requestFocus();
            return;
        }

        if (password.isEmpty()){
            editTextPassword.setError("Password is Required");
            editTextPassword.requestFocus();
            return;
        }
        if (password.length() < 6 ){
            editTextPassword.setError("Min Password Chars should be 6 ");
            editTextPassword.requestFocus();
            return;
        }
         progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull  Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            User user = new User(fullName,age,email);
                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull  Task<Void> task) {
                                    if (task.isSuccessful()){
                                        Toast.makeText(MainActivity2_Register_User.this,"User registered successfully" ,Toast.LENGTH_LONG ).show();
                                        progressBar.setVisibility(View.GONE);
                                        startActivity(new Intent(MainActivity2_Register_User.this,MainActivity.class));


//                                        redirect to Login Layout

                                    }else {
                                        Toast.makeText(MainActivity2_Register_User.this,"Failed to register User ! Try Again !" ,Toast.LENGTH_LONG ).show();
                                        progressBar.setVisibility(View.GONE);
                                    }
                                }
                            });
                        }else {
                            Toast.makeText(MainActivity2_Register_User.this,"User Exists Already ! Try Login !" ,Toast.LENGTH_LONG ).show();
                            progressBar.setVisibility(View.GONE);

                        }
                    }
                });
    }
}