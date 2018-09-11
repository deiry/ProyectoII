package com.example.sofiaalejandro.carapp.Activities.model;

import android.util.Log;
import android.widget.Toast;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.security.SecureRandom;
import java.util.List;
import java.util.UUID;

public class Model {
    private DatabaseReference mDatabase;
    private String hashCode = null;
    private int LENG = 20;

    public void save(){
        if(hashCode == null){
            hashCode = HashCode.randomString(this.LENG);
        }
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child(this.getClass().getSimpleName()).child(this.hashCode).setValue(this);

    }

    public void find(final Class<?> cls){
        mDatabase = FirebaseDatabase.getInstance().getReference();
        final String code = this.hashCode;

        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                Object post = null;
                try {
                    post = dataSnapshot.child(cls.getSimpleName()).child(code).getValue(Class.forName(cls.getName()));
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                Object v =  post;
                // [END_EXCLUDE]
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w("ERROR", "loadPost:onCancelled", databaseError.toException());
                // [START_EXCLUDE]
            }
        };
        mDatabase.addValueEventListener(postListener);
    }


    public String getHashCode(){
        return this.hashCode;
    }
}


class HashCode {

    static final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    static SecureRandom rnd = new SecureRandom();

    static public String randomString( int len ){
        StringBuilder sb = new StringBuilder( len );
        for( int i = 0; i < len; i++ )
            sb.append( AB.charAt( rnd.nextInt(AB.length()) ) );
        return sb.toString();
    }
}
