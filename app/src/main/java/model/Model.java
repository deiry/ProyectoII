package model;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

import adapter.CarAdapter;

public abstract class Model{
    private DatabaseReference mDatabase;
    public String hashCode = null;
    public static String CLASS_NAME = "className";
    public static String MODEL = "model";
    public static String ID = "id";
    private int LENG = 15;

    public abstract List<JSONObject> modelToJSON();

    public void saveModel(){
        mDatabase = FirebaseDatabase.getInstance().getReference();
        ArrayList<JSONObject> list = null;

        list = (ArrayList<JSONObject>) this.modelToJSON();
        for (int i = 0; i < list.size(); i++) {
            JSONObject json = list.get(i);
            try {
                //find(Class.forName(json.getString(CLASS_NAME)),json.optString(ID));
                mDatabase.child(json.getString(CLASS_NAME)).child(json.optString(ID)).setValue(json.get(MODEL));
            }catch (JSONException e){
                Log.e("ErrorJSON",e.getMessage());
            } /*catch (ClassNotFoundException e) {
                Log.e("ErrorClass",e.getMessage());
            }*/
        }
    }

    public void find(final Class<?> cls,final String id){
        mDatabase = FirebaseDatabase.getInstance().getReference();

        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                Object post = null;
                post = dataSnapshot.child(cls.getSimpleName()).child(id).getValue();
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

    public static void onChange(final Class<?> cls, final String id){
        DatabaseReference mPostReference = FirebaseDatabase.getInstance().getReference().child(cls.getSimpleName()).child(id);
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                Object post = null;
                post = (CarAdapter) dataSnapshot.child(cls.getSimpleName()).child(id).getValue(CarAdapter.class);
                /*try {
                    post = (CarAdapter) dataSnapshot.child(cls.getSimpleName()).child(id).getValue(CarAdapter.class);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }*/
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
        mPostReference.addValueEventListener(postListener);
    }


    public String getHashCode(){
        if(hashCode == null)
            this.hashCode = HashCode.randomString(this.LENG);
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
