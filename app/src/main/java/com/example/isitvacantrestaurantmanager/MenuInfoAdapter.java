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

public class MenuInfoAdapter extends FirestoreRecyclerAdapter<ModelMenuInfo, MenuInfoAdapter.MenuInfoHolder> {
    private OnItemClickListener listener;

    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public MenuInfoAdapter(@NonNull FirestoreRecyclerOptions<ModelMenuInfo> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull MenuInfoHolder holder, int position, @NonNull ModelMenuInfo model) {


holder.Name.setText(model.getFoodName());
holder.noOfItems.setText(String.valueOf(model.getQuantity()));






    }

    @NonNull
    @Override
    public MenuInfoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_food_items, parent, false);
        return new MenuInfoHolder(view);
    }

    class MenuInfoHolder extends RecyclerView.ViewHolder{

        TextView Name,noOfItems;



        public MenuInfoHolder(@NonNull View itemView) {
            super(itemView);
        Name = itemView.findViewById(R.id.food_title_name);
            noOfItems = itemView.findViewById(R.id.food_quantity);


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
