package com.example.isitvacantrestaurantmanager;



import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;

public class ReservationAdapter extends FirestoreRecyclerAdapter<ModelReservation, ReservationAdapter.ReservationHolder> {
    private OnItemClickListener listener;

    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public ReservationAdapter(@NonNull FirestoreRecyclerOptions<ModelReservation> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull ReservationHolder holder, int position, @NonNull ModelReservation model) {


   holder.hotelName.setText(model.getHotelName());
        holder.username.setText(model.getUserName());
        holder.bookDate.setText(model.getDate());
        holder.timeSlot.setText(model.getTimeSlot());
        holder.guests.setText(model.getGuests());
        holder.tableNo.setText(model.getTableId());
        holder.location.setText(model.getLocation());
        holder.payment.setText(String.valueOf(model.getTotalSeatPrice()));






    }

    @NonNull
    @Override
    public ReservationHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.current_cardview, parent, false);
        return new ReservationHolder(view);
    }

    class ReservationHolder extends RecyclerView.ViewHolder{

        TextView username,hotelName,bookDate,timeSlot,guests,tableNo,location,payment;



        public ReservationHolder(@NonNull View itemView) {
            super(itemView);
            username = itemView.findViewById(R.id.Username);
            hotelName = itemView.findViewById(R.id.resto_Title);
            bookDate = itemView.findViewById(R.id.Date);
            timeSlot = itemView.findViewById(R.id.Time);
            guests = itemView.findViewById(R.id.Guests);
            tableNo = itemView.findViewById(R.id.Table_No);
            location = itemView.findViewById(R.id.Location);
            payment = itemView.findViewById(R.id.payment);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int user_position = getAdapterPosition();
                    if (user_position!= RecyclerView.NO_POSITION && listener != null)
                    {
                        listener.OnItemClick(getSnapshots().getSnapshot(user_position),user_position);

                    }

                }
            });
        }
    }

    public interface OnItemClickListener{
        void OnItemClick(DocumentSnapshot documentSnapshot, int position);
    }
    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;

    }
}
