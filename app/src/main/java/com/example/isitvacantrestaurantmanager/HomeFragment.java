package com.example.isitvacantrestaurantmanager;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
    View view;

    EditText Date,Time;
    FirebaseAuth mAuth;
    Button Button,Submit,Availabillity,Submit_availabilty,Gallery;
    CardView AddMenu;
    Dialog dialog,dialog1;
    RadioButton openAndClose;
    RadioGroup radio_group;


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_PARAM3 = "param3";
    private static final String ARG_PARAM4 = "param4";


    private String mParam1;
    private String mParam2;
    private String mParam3;
    private String mParam4;

    private FirebaseFirestore firebaseFirestore;

    private String uid,username;
    String currentUserName,currentUserImage,currentUserMobile;
    private FirebaseFirestore mFirestore;
    String time;


    public HomeFragment() {
        // Required empty public constructor
    }


    public static HomeFragment newInstance(String param1, String param2, String param3, String param4) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        args.putString(ARG_PARAM3, param3);
        args.putString(ARG_PARAM4, param4);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
            mParam3 = getArguments().getString(ARG_PARAM3);
            mParam4 = getArguments().getString(ARG_PARAM4);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home, container, false);
        mAuth = FirebaseAuth.getInstance();

        mFirestore = FirebaseFirestore.getInstance();




        Button = view.findViewById(R.id.Add_time_slots);
        Availabillity = view.findViewById(R.id.Availbility);
        Gallery = view.findViewById(R.id.gallery);
        Gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(),Gg.class));
            }
        });

        Availabillity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog1 = new Dialog(getContext());
                dialog1.setContentView(R.layout.availabilty);
                dialog1.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                Window window = dialog1.getWindow();
                WindowManager.LayoutParams wlp = window.getAttributes();
                wlp.gravity = Gravity.CENTER;
                Submit_availabilty = dialog1.findViewById(R.id.submit_Availabilty);
                Submit_availabilty.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        radio_group =  dialog1.findViewById(R.id.radio_group);
                        int selectedId = radio_group.getCheckedRadioButtonId();

                        // find the radiobutton by returned id
                        openAndClose = dialog1.findViewById(selectedId);
                        Toast.makeText(getContext(), openAndClose.getText(), Toast.LENGTH_SHORT).show();
                    }
                });
                dialog1.show();
                dialog1.setCanceledOnTouchOutside(true);

            }
        });
        Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            dialog = new Dialog(getContext());
            dialog.setContentView(R.layout.time_slots);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                Window window = dialog.getWindow();
                WindowManager.LayoutParams wlp = window.getAttributes();

                wlp.gravity = Gravity.CENTER;
                Date = dialog.findViewById(R.id.date_field);
                Time = dialog.findViewById(R.id.time_field);
                Submit = dialog.findViewById(R.id.submit_time_slot);
                Calendar calendar = Calendar.getInstance();
                final int year = calendar.get(Calendar.YEAR);
                final int month = calendar.get(Calendar.MONTH);
                final int day = calendar.get(Calendar.DAY_OF_MONTH);
                final int hourofDay = calendar.get(Calendar.HOUR);
                final int minute = calendar.get(Calendar.MINUTE);
                Date.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                                DatePickerDialog datePickerDialog = new DatePickerDialog(
                                        getContext(), new DatePickerDialog.OnDateSetListener() {
                                    @Override
                                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                        month = month + 1;
                                        String date = dayOfMonth + "/" + month + "/" + year;

                                        Date.setText(date);

                                    }
                                }, year, month, day);
                                datePickerDialog.show();


                    }
                });


                Time.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                String amPm;
                                if (hourOfDay >= 12) {
                                    amPm = "PM";
                                } else {
                                    amPm = "AM";
                                }
                                if(minute>=0 && minute<=9)
                                    {
                                    time = hourOfDay+ ": 0" +minute+" "+amPm;
                                }
                                else
                                {
                                    time = hourOfDay+ ":" +minute+" "+amPm;
                                }

                                Time.setText(time);
                            }
                        },00,00,false);
                        timePickerDialog.show();

                    }
                });
                Submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final String dates = Date.getText().toString();
                        final String timeslot = Time.getText().toString();
                        if (dates.isEmpty() && timeslot.isEmpty()){
                            Date.setError("Please select a date!!!");
                            Time.setError("Please select a time slot!!!");
                            Toast.makeText(getContext(), "please select date and time!!!", Toast.LENGTH_LONG).show();
                        }
                        else{
                            Map<String, String> TimeSlotMap = new HashMap<>();

                            TimeSlotMap.put("date", dates);

                            TimeSlotMap.put("time_slot", timeslot);
                            uid = mAuth.getCurrentUser().getUid();








                            mFirestore.collection("restaurants")
                                    .document(uid).collection("time_slots").document()
                                    .set(TimeSlotMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(getContext(), "Time slot :"+timeslot+"is created for the date:"+dates, Toast.LENGTH_LONG).show();
                                }


                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    String error = e.getMessage();
                                    Toast.makeText(getContext(), "Error" + error, Toast.LENGTH_LONG).show();
                                }
                            });
                            dialog.dismiss();

                        }

                    }
                });

                dialog.show();
                dialog.setCanceledOnTouchOutside(true);
            }
        });
        return view;
    }

}
