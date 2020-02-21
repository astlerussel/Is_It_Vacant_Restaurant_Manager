package com.example.isitvacantrestaurantmanager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class MainActivity extends AppCompatActivity {
    FirebaseAuth mAuth;

    private FirebaseFirestore firebaseFirestore;

    private String uid,username;
    String currentUserName,currentUserImage,currentUserMobile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
            DocumentReference documentReference = firebaseFirestore.collection("users").document(uid);
            documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
                @Override
                public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {

                    if (documentSnapshot.exists()) {
                        username = documentSnapshot.getString("name");

                        String gender2 = documentSnapshot.getString("Gender");
                        String dob2 = documentSnapshot.getString("Date Of Birth");
                        String nationality2 = documentSnapshot.getString("Nationality");

                        if (username==""|| gender2=="" || nationality2==""|| dob2=="") {
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



            DocumentReference documentReference1 = firebaseFirestore.collection("users").document(uid);
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
