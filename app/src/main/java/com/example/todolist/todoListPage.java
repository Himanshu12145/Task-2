package com.example.todolist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * @author himan
 */
public class todoListPage extends AppCompatActivity {


//    TextView titlepage, subtitlepage, endpage;
//
//    RecyclerView ourdoes;
//    ArrayList<User> list;
//    DoesAdapter doesAdapter;

    private ImageView logout;
    private FirebaseUser user;
    private DatabaseReference reference;
    private String userId;
    TextView fullNameTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_list_page);



        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(todoListPage.this,MainActivity.class));
            }
        });

        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("User");
        userId = user.getUid();
//        fullNameTv = findViewById(R.id.fullNameRESTitle);


        reference.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull  DataSnapshot snapshot) {

                User userProfile = snapshot.getValue(User.class);
                if (userProfile != null){
                    String fullName= userProfile.fullName;
//                    fullNameTv.setText("Welcome , "+fullName+ " !");
                    Log.i("info", "onDataChange: "+fullName);
                    logout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                        }
                    });
                }

            }

            @Override
            public void onCancelled(@NonNull  DatabaseError error) {
                Toast.makeText(todoListPage.this,"Something went wrong!" ,Toast.LENGTH_LONG ).show();


            }
        });


//        // working with data
//        ourdoes = findViewById(R.id.ourdoes);
//        ourdoes.setLayoutManager(new LinearLayoutManager(this));
//        list = new ArrayList<User>();
//
//        // get data from firebase
//        reference = FirebaseDatabase.getInstance().getReference().child("Users");
//        userId = user.getUid();
//        reference.child(userId).addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                // set code to retrieve data and replace layout
//                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren())
//                {
//                    User p = dataSnapshot1.getValue(User.class);
//                    list.add(p);
//                }
//                doesAdapter = new DoesAdapter(todoListPage.this, list);
//                ourdoes.setAdapter(doesAdapter);
//                doesAdapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//                // set code to show an error
//                Toast.makeText(todoListPage.this, "No Data", Toast.LENGTH_SHORT).show();
//            }
//        });

    }
}