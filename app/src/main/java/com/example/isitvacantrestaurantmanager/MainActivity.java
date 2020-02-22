package com.example.isitvacantrestaurantmanager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class MainActivity extends AppCompatActivity {
    FirebaseAuth mAuth;
    Button Logout;

    private FirebaseFirestore firebaseFirestore;

    private String uid,username;
    String currentUserName,currentUserImage,currentUserMobile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Logout = findViewById(R.id.logout);
        Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                Intent intent = new Intent(MainActivity.this, AuthActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                Toast.makeText(MainActivity.this, "Logged out succesfully", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth = FirebaseAuth.getInstance();
        firebaseFirestore= FirebaseFirestore.getInstance();

        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            Intent intent = new Intent(MainActivity.this, AuthActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

            startActivity(intent);
        }

        else {
            uid = mAuth.getCurrentUser().getUid();
            DocumentReference documentReference = firebaseFirestore.collection("restaurants").document(uid);
            documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
                @Override
                public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {

                    if (documentSnapshot.exists()) {
                        username = documentSnapshot.getString("name");

                        String type = documentSnapshot.getString("Type");
                        String gstin = documentSnapshot.getString("GSTIN_NUMBER");
                        String address = documentSnapshot.getString("Address");
                        String desc = documentSnapshot.getString("discription");
                        String cate = documentSnapshot.getString("category");

                        if (username==""|| type=="" || address==""|| gstin=="" || desc=="" || cate=="") {
                            Intent intent1 = new Intent(MainActivity.this, HomeName.class);
                            intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent1);
                        }
                    }
                    else{
                        Intent intent1 = new Intent(MainActivity.this, HomeName.class);
                        intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent1);
                    }

                }
            });



            DocumentReference documentReference1 = firebaseFirestore.collection("restaurants").document(uid);
            documentReference1.addSnapshotListener(MainActivity.this, new EventListener<DocumentSnapshot>() {
                @Override
                public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {

                    currentUserName = documentSnapshot.getString("name");
                    currentUserImage=documentSnapshot.getString("image");

                    currentUserMobile=documentSnapshot.getString("Mobile");

                }
            });


        }
    }
}
