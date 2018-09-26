package model;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

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
    public abstract void mapToModel(CallbackModel callbackModel, Map<String,Object> mapRequest);

    static  protected void findById(final CallbackModel callbackModel,String value, final String className){
        //TODO: buscar por id
        FirebaseFirestore.getInstance().collection(className).document(value).get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(task.isSuccessful()){
                            String id = task.getResult().getId();

                            if(className == "User"){
                                final User model = new User();
                                model.setId(id);
                                model.mapToModel(new CallbackModel() {
                                    @Override
                                    public void onSuccess(Object id) {
                                        callbackModel.onSuccess(model);
                                    }

                                    @Override
                                    public void onError(Object model, String message) {
                                        callbackModel.onError(model,message);
                                    }
                                }, task.getResult().getData());
                            }
                        }

                    }
                });
    }

    static protected void singleRecord(final CallbackModel callbackModel, String field, String value, final String className){
        FirebaseFirestore.getInstance().collection(className).whereEqualTo(field,value).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (DocumentSnapshot documentSnapshot : task.getResult().getDocuments()) {
                                String id = documentSnapshot.getId();
                                // here you can get the id.
                                if(className == "User"){
                                    final User model = new User();
                                    model.setId(id);
                                    model.mapToModel(new CallbackModel() {
                                        @Override
                                        public void onSuccess(Object id) {
                                            callbackModel.onSuccess(model);
                                        }

                                        @Override
                                        public void onError(Object model, String message) {
                                            callbackModel.onError(model,message);
                                        }
                                    }, documentSnapshot.getData());


                                }
                                else if(className == "Car"){
                                    final Car model = new Car();
                                    model.setId(id);
                                    model.mapToModel(new CallbackModel() {
                                        @Override
                                        public void onSuccess(Object id) {
                                            callbackModel.onSuccess(model);
                                        }

                                        @Override
                                        public void onError(Object model, String message) {

                                        }
                                    },documentSnapshot.getData());

                                }
                                /*else if(className == "State"){
                                    State model = documentSnapshot.toObject(State.class);
                                    model.setId(id);
                                    callbackModel.onSuccess(model);
                                }
                                else if(className == "Route"){
                                    Route model = documentSnapshot.toObject(Route.class);
                                    model.setId(id);
                                    callbackModel.onSuccess(model);
                                }
                                else if(className == "RoutePassenger"){
                                    RoutePassenger model = documentSnapshot.toObject(RoutePassenger.class);
                                    model.setId(id);
                                    callbackModel.onSuccess(model);
                                }*/


                                // you can apply your actions...
                            }
                        } else {

                        }
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

    protected void setId(String id) {
        this.id = id;
    }
}
