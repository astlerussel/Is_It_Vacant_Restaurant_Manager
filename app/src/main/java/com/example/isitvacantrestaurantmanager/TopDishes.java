package com.example.isitvacantrestaurantmanager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.HashMap;
import java.util.Map;

public class TopDishes extends AppCompatActivity {

    private FirebaseFirestore firebaseFirestore;
    Button Addphoto;
    String uid;
    private Button mButton;
    Uri Image;
    private static final int GalleryPick = 1;
    String type,foodName,foodCost;
    EditText foodTitle,foodcost;
    FirebaseAuth mAuth;
    private StorageReference userProfileImageRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_dishes);

        mButton = (Button) findViewById(R.id.submit_food_bt);

        mAuth = FirebaseAuth.getInstance();
        firebaseFirestore= FirebaseFirestore.getInstance();
        uid = mAuth.getCurrentUser().getUid();


        Addphoto = findViewById(R.id.add_dish_photo);
        foodcost = findViewById(R.id.enter_cost_field);
        foodTitle = findViewById(R.id.food_title);

        //restaurant photo

        Addphoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent();
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent, GalleryPick);
            }
        });

        Spinner spinner2 = (Spinner) findViewById(R.id.type_spinner);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                R.array.dish_type, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner2.setAdapter(adapter2);
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                type= (String) adapterView.getItemAtPosition(i);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                foodName = foodTitle.getText().toString();
                foodCost=foodcost.getText().toString();








                if(foodName.isEmpty()||foodCost.isEmpty()){

                    foodTitle.setError("Mandatory Field...");

                    foodcost.setError("Mandatory Field...");


                }

                else {





                    String uid2 = mAuth.getCurrentUser().getUid();

                    DocumentReference documentReference = firebaseFirestore.collection("restaurants")
                            .document(uid).collection("menu").document(foodName);
                    documentReference.addSnapshotListener(TopDishes.this, new EventListener<DocumentSnapshot>() {
                        @Override
                        public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {

                            if (documentSnapshot.exists()) {



                                Map<String, Object> userMap = new HashMap<>();

                                userMap.put("name", foodName);


                                userMap.put("type", type);
                                userMap.put("cost", foodCost);

                                //userMap.put("image","https://firebasestorage.googleapis.com/v0/b/is-it-vacant-d1cf7.appspot.com/o/profile%20images%2Fprofile_image.png?alt=media&token=07a82599-e485-4e7f-b937-dac00b1ea41d" );





                                firebaseFirestore.collection("restaurants")
                                        .document(uid).collection("menu").document(foodName)
                                        .update(userMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(TopDishes.this, "Menu Added succesfully", Toast.LENGTH_LONG).show();
                                    }


                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        String error = e.getMessage();
                                        Toast.makeText(TopDishes.this, "Error" + error, Toast.LENGTH_LONG).show();
                                    }
                                });





                            }
                            else
                            {

                                Toast.makeText(TopDishes.this, "Error:Please Upload an Image First", Toast.LENGTH_LONG).show();

                            }

                        }
                    });



















                }
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        final String uid2 = mAuth.getCurrentUser().getUid();

        if (requestCode == GalleryPick && resultCode == RESULT_OK && data != null) {


            Uri ImageUri = data.getData();
            CropImage.activity()
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .setAspectRatio(1, 1)
                    .start(TopDishes.this);

        } /*else {
            progressBar.setVisibility(View.INVISIBLE);


        }*/

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            //  progressBar.setVisibility(View.VISIBLE);
            CropImage.ActivityResult result = CropImage.getActivityResult(data);


            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();


                UploadTask uploadTask;
                userProfileImageRef = FirebaseStorage.getInstance().getReference().child("Menu photos");


                final StorageReference ref = userProfileImageRef.child(uid + ".jpg");
                uploadTask = ref.putFile(resultUri);


                Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if (!task.isSuccessful()) {
                            throw task.getException();
                        }

                        // Continue with the task to get the download URL
                        return ref.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if (task.isSuccessful()) {
                            //progressBar.setVisibility(View.INVISIBLE);


                            Toast.makeText(getApplicationContext(), "Menu photo Uploaded Successfully", Toast.LENGTH_LONG).show();
                            final Uri downloadUri = task.getResult();

                            Map<String, Object> userMap = new HashMap<>();
                            userMap.put("name","");


                            userMap.put("type","");
                            userMap.put("cost", "");

                            userMap.put("image", downloadUri.toString());


                            firebaseFirestore.collection("restaurants")
                                    .document(uid).collection("menu").document(foodName)
                                    .set(userMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {

                                }


                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    String error = e.getMessage();
                                    Toast.makeText(getApplicationContext(), "Error" + error, Toast.LENGTH_LONG).show();
                                }
                            });


                        } else {
                            // progressBar.setVisibility(View.INVISIBLE);
                            String error = task.getException().toString();
                            Toast.makeText(getApplicationContext(), "Error" + error, Toast.LENGTH_LONG).show();
                        }
                    }
                });

            }

        } /*else {
            progressBar.setVisibility(View.INVISIBLE);

        }*/
    }
}
