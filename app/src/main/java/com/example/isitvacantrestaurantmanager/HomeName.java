package com.example.isitvacantrestaurantmanager;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class HomeName extends AppCompatActivity {
    private EditText meditText,dobText,addrText,disText;

    private TextView mobText;
    private Button mButton;
    String mob0;
    Button Addphoto;
    String uid;
    FirebaseAuth mAuth;
    String username1;
    Uri Image;
    private static final int GalleryPick = 1;
    String username;
    String dob,addr,description,category;
    private StorageReference userProfileImageRef;
    FirebaseFirestore mstore;









    String type;



    private FirebaseFirestore firebaseFirestore;


    private FirebaseFirestore mFirestore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_name);
        mFirestore = FirebaseFirestore.getInstance();
        mobText=findViewById(R.id.enter_mob_field);

        dobText=findViewById(R.id.enter_dob_field);
        disText = findViewById(R.id.enter_dis_field);


        meditText = (EditText) findViewById(R.id.enter_name_field);
        addrText = findViewById(R.id.enter_addr_field);

        mButton = (Button) findViewById(R.id.submit_details_button);
        mstore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        firebaseFirestore= FirebaseFirestore.getInstance();
        uid = mAuth.getCurrentUser().getUid();
        Addphoto = findViewById(R.id.add_res_photo);

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

        Spinner spinner = (Spinner) findViewById(R.id.type_spinner);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.type, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                type= (String) adapterView.getItemAtPosition(i);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        Spinner spinner2 = (Spinner) findViewById(R.id.category_spinner);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                R.array.category, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner2.setAdapter(adapter2);
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                category= (String) adapterView.getItemAtPosition(i);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        mobText.setText(mAuth.getCurrentUser().getPhoneNumber());








        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                username1 = meditText.getText().toString();
                dob=dobText.getText().toString();
                addr = addrText.getText().toString();
                description = disText.getText().toString();





                if(username1.isEmpty()||dob.isEmpty()||addr.isEmpty()||description.isEmpty()){

                    meditText.setError("Mandatory Field...");

                    dobText.setError("Mandatory Field...");
                    addrText.setError("Mandatory Field...");
                    disText.setError("Mandatory Field...");

                    meditText.requestFocus();

                }
                else {
                    mob0=mobText.getText().toString();



                    String uid2 = mAuth.getCurrentUser().getUid();
















                    Map<String, String> userMap = new HashMap<>();

                    userMap.put("name", username1);

                    userMap.put("GSTIN_NUMBER", dob);
                    userMap.put("Type", type);
                    userMap.put("Mobile", mob0);
                    userMap.put("Address",addr );
                    userMap.put("uid",uid2);
                    userMap.put("category",category);
                    userMap.put("total_rating","0.0");
                    userMap.put("discription",description);
                    userMap.put("image","https://firebasestorage.googleapis.com/v0/b/is-it-vacant-d1cf7.appspot.com/o/profile%20images%2Fprofile_image.png?alt=media&token=07a82599-e485-4e7f-b937-dac00b1ea41d" );





                    mFirestore.collection("restaurants")
                            .document(uid2)
                            .set(userMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(HomeName.this, "Data Added succesfully", Toast.LENGTH_LONG).show();
                        }


                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            String error = e.getMessage();
                            Toast.makeText(HomeName.this, "Error" + error, Toast.LENGTH_LONG).show();
                        }
                    });

                    Intent intent = new Intent(HomeName.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);

                    startActivity(intent);
                }
            }
        });
    }














    @Override
    protected void onStart() {
        super.onStart();

        mAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();



        uid = mAuth.getCurrentUser().getUid();
        DocumentReference documentReference = firebaseFirestore.collection("restaurants").document(uid);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {

                if (documentSnapshot.exists()) {
                    username = documentSnapshot.getString("name");

                    String type = documentSnapshot.getString("Type");
                    String dob2 = documentSnapshot.getString("GSTIN_NUMBER");
                    String address = documentSnapshot.getString("Address");
                    String desc = documentSnapshot.getString("discription");
                    String cate = documentSnapshot.getString("category");


                    if (username != ""  && type!="" && address!=""&& dob2!="" && desc!="" && cate!=""){
                        Intent intent1 = new Intent(HomeName.this, MainActivity.class);
                        intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent1);
                    }

                }

            }
        });
    }
//edited res photos
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        final String uid2 = mAuth.getCurrentUser().getUid();

        if (requestCode == GalleryPick && resultCode == RESULT_OK && data != null) {


            Uri ImageUri = data.getData();
            CropImage.activity()
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .setAspectRatio(1, 1)
                    .start(HomeName.this);

        } /*else {
            progressBar.setVisibility(View.INVISIBLE);


        }*/

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
          //  progressBar.setVisibility(View.VISIBLE);
            CropImage.ActivityResult result = CropImage.getActivityResult(data);


            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();


                UploadTask uploadTask;
                userProfileImageRef = FirebaseStorage.getInstance().getReference().child("Restaurant photos");


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


                            Toast.makeText(getApplicationContext(), "Restaurant photo Uploaded Successfully", Toast.LENGTH_LONG).show();
                            final Uri downloadUri = task.getResult();

                            Map<String, Object> userMap = new HashMap<>();

                            userMap.put("image", downloadUri.toString());


                            mstore.collection("restaurants")
                                    .document(uid)
                                    .update(userMap).addOnSuccessListener(new OnSuccessListener<Void>() {
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
