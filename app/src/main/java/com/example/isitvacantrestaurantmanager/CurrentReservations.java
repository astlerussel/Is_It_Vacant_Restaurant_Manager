package com.example.isitvacantrestaurantmanager;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;


/**
 * A simple {@link Fragment} subclass.
 */
public class CurrentReservations extends Fragment {
    private View view;
    private RecyclerView reservationRecycler;
    EditText searchText;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
    private CollectionReference contactsRef ;
    private ReservationAdapter adapter;
    Query query;

    public CurrentReservations() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         view = inflater.inflate(R.layout.fragment_current_reservations, container, false);


        reservationRecycler = (RecyclerView) view.findViewById(R.id.current_reservation_recycler);
        reservationRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        contactsRef = db.collection("/restaurants/"+uid+"/current_reservations");
        query = contactsRef;
        setUpRecyclerView(query);
        adapter.setOnItemClickListener(new ReservationAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(DocumentSnapshot documentSnapshot, int position) {
                String[] pathwithuid;
                String path =documentSnapshot.getReference().getPath();
                //Toast.makeText(FindFriendsActivity.this,"Position"+position+"\t UID:"+id,Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getContext(),current_onclick_cardview.class);

                pathwithuid = path.split("/");
                String uid2=pathwithuid[3];
                intent.putExtra("uid",uid2);
                intent.putExtra("reserve","current_reservations");



                startActivity(intent);

            }
        });






        return view;
    }


    private void setUpRecyclerView(Query query) {







        FirestoreRecyclerOptions<ModelReservation> options = new FirestoreRecyclerOptions.Builder<ModelReservation>()
                .setQuery(query, ModelReservation.class)
                .build();

        adapter = new ReservationAdapter(options);

        RecyclerView recyclerView = view.findViewById(R.id.current_reservation_recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
    }


    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}
