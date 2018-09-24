package model;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.json.JSONObject;

import java.util.List;
import java.util.Map;

import callback.CallbackModel;

public abstract class Model{
    private static final String TAG = "model";
    public static String CLASS_NAME = "className";
    public static String MODEL = "model";
    public static String ID = "id";

    public String id = null;

    private DatabaseReference mDatabase;
    private FirebaseFirestore mFirestore;

    /* abstract methods */
    public abstract List<JSONObject> modelToJSON();
    public abstract void save(CallbackModel callbackModel);
    public abstract Map<String,Object> toMap();

    static public void find(final CallbackModel callbackModel, String field, String value, final String className){
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        DocumentReference docRef = db.collection(className).document(value);
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                User city = documentSnapshot.toObject(User.class);
                callbackModel.onSuccess(city);
            }
        });

    }

    public void saveModel(final CallbackModel callbackModel){
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mFirestore = FirebaseFirestore.getInstance();


        if(this.getId() == null){

            mFirestore.collection(this.getClass().getSimpleName()).add(this.toMap())
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Log.d(TAG, "DocumentSnapshot written with ID: " + documentReference.getId());
                            callbackModel.onSuccess(documentReference.getId());
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.w(TAG, "Error adding document", e);
                            callbackModel.onError(this,e.getMessage());
                        }
                    });
        }
        else{
            final String id = this.getId();
            mFirestore.collection(this.getClass().getSimpleName()).document(this.getId()).update(this.toMap())
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            callbackModel.onSuccess(id);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            callbackModel.onError(this,e.getMessage());
                        }
                    });
        }

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
