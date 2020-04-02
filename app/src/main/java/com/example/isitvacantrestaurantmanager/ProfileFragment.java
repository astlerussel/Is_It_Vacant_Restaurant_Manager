package com.example.isitvacantrestaurantmanager;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

TextView restoName,restoLoc,restoNum,restoType,restoGSTIN,restoCat,restoDes;


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_PARAM3 = "param3";
    private static final String ARG_PARAM4 = "param4";


    private String mParam1;
    private String mParam2;
    private String mParam3;
    private String mParam4;

    View view;
    Button Edit,Logout;
    FirebaseAuth mAuth;


    public ProfileFragment() {
        // Required empty public constructor
    }


    public static ProfileFragment newInstance(String param1, String param2, String param3, String param4) {
        ProfileFragment fragment = new ProfileFragment();
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
        mAuth = FirebaseAuth.getInstance();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_profile, container, false);
        restoCat = view.findViewById(R.id.Res_Category);
        restoDes = view.findViewById(R.id.res_des);
        restoGSTIN = view.findViewById(R.id.res_gstin);
        restoLoc = view.findViewById(R.id.Res_address);
        restoName = view.findViewById(R.id.food_title);
        restoNum = view.findViewById(R.id.Mobile_Number);
        restoType = view.findViewById(R.id.Res_type);


        Edit = view.findViewById(R.id.Edit_profile);
        Logout = view.findViewById(R.id.logout);
        Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                Intent intent = new Intent(getContext(), AuthActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                Toast.makeText(getContext(), "Logged out succesfully", Toast.LENGTH_SHORT).show();
            }
        });
        Edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(),HomeName.class));
            }
        });




        return view;
    }


    @Override
    public void onStart() {
        super.onStart();
        String uid = FirebaseAuth.getInstance().getUid();

        DocumentReference documentReferences2 = FirebaseFirestore.getInstance().collection("restaurants").document(uid);
        documentReferences2.addSnapshotListener(getActivity(), new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {

                if (documentSnapshot.exists()){


                    restoName.setText(documentSnapshot.getString("name"));
                    restoCat.setText(documentSnapshot.getString("category"));
                    restoDes.setText(documentSnapshot.getString("discription"));
                    restoGSTIN.setText(documentSnapshot.getString("GSTIN_NUMBER"));
                    restoLoc.setText(documentSnapshot.getString("Address"));
                    restoNum.setText(documentSnapshot.getString("Mobile"));
                    restoType.setText(documentSnapshot.getString("Type"));

                }








            }
        });
    }
}
