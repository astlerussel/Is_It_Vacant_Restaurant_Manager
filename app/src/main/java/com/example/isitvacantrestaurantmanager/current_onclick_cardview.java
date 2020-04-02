package com.example.isitvacantrestaurantmanager;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class current_onclick_cardview extends AppCompatActivity {
    CollapsingToolbarLayout collapsingToolbarLayout;
    private RecyclerView menuRecyclerList;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference menuRef ;
    private MenuInfoAdapter adapter;

    FirebaseAuth mAuth;
    String uid;
    FirebaseFirestore mstore;

    TextView hotel_name,reservation_date,reservation_time,table_id,numberOfGuests,Location;


    TextView grandTotal;
    TextView mobile_no,Username;
    Query query;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_onclick_cardview);
        collapsingToolbarLayout = findViewById(R.id.collapsingtoolbar2);
        collapsingToolbarLayout.setExpandedTitleColor(Color.parseColor("#099ae1"));
        collapsingToolbarLayout.setExpandedTitleGravity(Gravity.BOTTOM);
        collapsingToolbarLayout.setExpandedTitleTypeface(Typeface.DEFAULT_BOLD);

        mAuth = FirebaseAuth.getInstance();
        mstore = FirebaseFirestore.getInstance();
        uid = mAuth.getCurrentUser().getUid();
        Username = findViewById(R.id.Username2);
        mobile_no = findViewById(R.id.Mobile_No2);
        hotel_name = findViewById(R.id.hotel_name2);
        reservation_date = findViewById(R.id.Reservation_date2);
        reservation_time = findViewById(R.id.Reservation_time2);
        numberOfGuests = findViewById(R.id.no_of_guests2);
        Location = findViewById(R.id.Location2);
        table_id = findViewById(R.id.Table_no2);

        menuRecyclerList = (RecyclerView) findViewById(R.id.menu_info_recycler);
        menuRecyclerList.setLayoutManager(new LinearLayoutManager(this));
        String invoiceId = getIntent().getStringExtra("uid");
        char flag = invoiceId.charAt(invoiceId.length()-1);

        if(flag=='N'){

            RelativeLayout layout = findViewById(R.id.menu_info_t);

            layout.setVisibility(View.INVISIBLE);

        }
        else{
            RelativeLayout layout = findViewById(R.id.menu_info_t);

            layout.setVisibility(View.VISIBLE);
        }
        String reserve = getIntent().getStringExtra("reserve");

        menuRef = db.collection("/restaurants/" + uid + "/" + reserve + "/" + invoiceId + "/cart");
        query = menuRef.orderBy("foodName");
        setUpRecyclerView(query);
        grandTotal = findViewById(R.id.total_no_of_items);

        mstore.collection("/restaurants/" + uid + "/" + reserve + "/" + invoiceId + "/cart")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value,
                                        @Nullable FirebaseFirestoreException e) {


                        List<Double> allPrice = new ArrayList<>();
                        double sum = 0;
                        for (QueryDocumentSnapshot doc : value) {
                            if (doc.get("foodName") != null) {
                                allPrice.add(doc.getDouble("totalPrice"));

                            }
                        }


                        for (int i = 0; i < allPrice.size(); i++) {


                            sum = sum + allPrice.get(i);


                        }
                        grandTotal.setText(String.valueOf(sum));
                    }
                });









        collapsingToolbarLayout.setExpandedTitleTypeface(Typeface.DEFAULT_BOLD);
    }

    private void setUpRecyclerView(Query query) {







        FirestoreRecyclerOptions<ModelMenuInfo> options = new FirestoreRecyclerOptions.Builder<ModelMenuInfo>()
                .setQuery(query, ModelMenuInfo.class)
                .build();

        adapter = new MenuInfoAdapter(options);

        RecyclerView recyclerView = findViewById(R.id.menu_info_recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

    }




    @Override
    protected void onStart() {
        super.onStart();


            adapter.startListening();

        String invoiceId =getIntent().getStringExtra("uid");
        String reserve = getIntent().getStringExtra("reserve");

        uid  = mAuth.getCurrentUser().getUid();
        mstore = FirebaseFirestore.getInstance();


        DocumentReference documentReferences2 = mstore.collection("restaurants").document(uid).collection(reserve).document(invoiceId);
        documentReferences2.addSnapshotListener(current_onclick_cardview.this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {

                if (documentSnapshot.exists()){

                    collapsingToolbarLayout.setTitle(documentSnapshot.getString("hotelName"));
                    hotel_name.setText(documentSnapshot.getString("hotelName"));
                    reservation_date.setText(documentSnapshot.getString("date"));
                    reservation_time.setText(documentSnapshot.getString("timeSlot"));
                    table_id.setText(documentSnapshot.getString("tableId"));
                    numberOfGuests.setText(documentSnapshot.getString("guests"));
                    Location.setText(documentSnapshot.getString("location"));
                    Username.setText(documentSnapshot.getString("userName"));
                    mobile_no.setText(documentSnapshot.getString("Mobile"));
                }








            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
      adapter.stopListening();
        finish();

    }
}
