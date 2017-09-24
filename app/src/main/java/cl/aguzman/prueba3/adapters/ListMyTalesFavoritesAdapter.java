package cl.aguzman.prueba3.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseIndexRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import cl.aguzman.prueba3.R;
import cl.aguzman.prueba3.data.Nodes;
import cl.aguzman.prueba3.data.Tale;

public class ListMyTalesFavoritesAdapter extends FirebaseIndexRecyclerAdapter<Tale, ListMyTalesFavoritesAdapter.myTalesHolder> {

    private ContentCallback contentCallback;

    public ListMyTalesFavoritesAdapter(ContentCallback contentCallback) {
        super(Tale.class, R.layout.item_list_tale, myTalesHolder.class, new Nodes().favoritesRef(), new Nodes().talesRef());
        this.contentCallback = contentCallback;
    }

    @Override
    protected void populateViewHolder(final myTalesHolder viewHolder, Tale model, int position) {
        ImageView taleImage = viewHolder.imageTale;
        Picasso.with(viewHolder.imageTale.getContext()).load(model.getImage()).into(viewHolder.imageTale);
        viewHolder.titleTale.setText(model.getTitle());
        viewHolder.authorTale.setText(model.getAuthor());
        taleImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int auxPosition = viewHolder.getAdapterPosition();
                String key = getItem(auxPosition).getKey();
                String uid = getItem(auxPosition).getUid();
                contentCallback.getContent(uid, key);
            }
        });
    }

    public static class myTalesHolder extends RecyclerView.ViewHolder {
        private TextView titleTale;
        private TextView authorTale;
        private ImageView imageTale;

        public myTalesHolder(View itemView) {
            super(itemView);
            imageTale = (ImageView) itemView.findViewById(R.id.listTaleIv);
            authorTale = (TextView) itemView.findViewById(R.id.listTaleAuthorTv);
            titleTale = (TextView) itemView.findViewById(R.id.listTaleTitleTv);

        }
    }

    public void checkFavorites(final TextView textView) {
        DatabaseReference databaseReference = new Nodes().favoritesRef();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Object value = dataSnapshot.getValue();
                if (value == null) {
                    textView.setVisibility(View.VISIBLE);
                } else {
                    textView.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

}
