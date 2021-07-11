package com.example.todolist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * @author himan
 */
public class todoList extends AppCompatActivity {

     ImageView logout;
   private   FirebaseUser user;
     private DatabaseReference reference;
private      String userId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_list);

        logout = findViewById(R.id.imageView);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(todoList.this,MainActivity.class));
                Toast.makeText(todoList.this,"Logged Out Successfully!" ,Toast.LENGTH_LONG ).show();

            }
        });

        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("User");
        userId = user.getUid();
        final TextView fullNameTv;
        fullNameTv = findViewById(R.id.fullNameRESTitle);


        reference.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                User userProfile = snapshot.getValue(User.class);
                if (userProfile != null){
                    String fullName= userProfile.fullName;
                    String fullWelName ="Welcome , "+fullName+ " !";
                    fullNameTv.setText(fullWelName);
                    Log.i("info", "onDataChange: "+userProfile);
                    Log.i("info", "onDataChange: "+fullWelName);
                    Log.i("info", "onDataChange: "+fullName);
                }

            }

            @Override
            public void onCancelled(@NonNull  DatabaseError error) {
                Toast.makeText(todoList.this,"Something went wrong!" ,Toast.LENGTH_LONG ).show();
            }
        });

    }




}