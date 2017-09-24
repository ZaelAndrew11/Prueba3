package cl.aguzman.prueba3.data;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Nodes {
    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

    public DatabaseReference root(){
        return databaseReference;
    }
    public DatabaseReference talesRef(){
        return databaseReference.child("allTales");
    }
    public DatabaseReference tales(){
        return databaseReference.child("tales");
    }

    public DatabaseReference mytalesref(){
        return databaseReference.child("tales").child(new CurrentUser().getCurrentUid());
    }

    public DatabaseReference favoritesRef(){
        return  databaseReference.child("favorites").child(new CurrentUser().getCurrentUid());
    }
}
