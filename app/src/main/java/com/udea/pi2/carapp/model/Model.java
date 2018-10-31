package com.udea.pi2.carapp.model;

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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import callback.CallbackModel;

public abstract class Model{
    private static final String TAG = "com/udea/pi2/carapp/model";
    public static String CLASS_NAME = "className";
    public static String MODEL = "com/udea/pi2/carapp/model";
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
                            else if(className == "Car"){
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
                                            callbackModel.onError(model,message);
                                        }
                                    },documentSnapshot.getData());
                                }
                                else if(className == "State"){
                                    final State model = documentSnapshot.toObject(State.class);
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
                                    },documentSnapshot.getData());
                                    callbackModel.onSuccess(model);
                                }
                                else if(className == "Route"){
                                    final Route model = documentSnapshot.toObject(Route.class);
                                    model.setId(id);
                                    model.mapToModel(new CallbackModel() {
                                        @Override
                                        public void onSuccess(Object id) {
                                            callbackModel.onSuccess(model);
                                        }

                                        @Override
                                        public void onError(Object model, String message) {
                                            callbackModel.onError(model, message);
                                        }
                                    },documentSnapshot.getData());
                                    callbackModel.onSuccess(model);
                                }
                                /*else if(className == "RoutePassenger"){
                                    RoutePassenger com.udea.pi2.carapp.model = documentSnapshot.toObject(RoutePassenger.class);
                                    com.udea.pi2.carapp.model.setId(id);
                                    callbackModel.onSuccess(com.udea.pi2.carapp.model);
                                }*/


                                // you can apply your actions...
                            }
                        } else {

                        }
                    }
                });

    }


    static protected void multiRecord(final CallbackModel callbackModel, String field, String value, final String className){

        Task<QuerySnapshot> query;
        if(field.isEmpty() || field == null){
            query = FirebaseFirestore.getInstance().collection(className).get();
        }
        else{
            query = FirebaseFirestore.getInstance().collection(className).whereEqualTo(field,value).get();
        }

        query.addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull final Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {

                    final List<User> users = new ArrayList<>();
                    final List<Car> cars = new ArrayList<>();
                    final List<State> states = new ArrayList<>();
                    final List<Route> routes = new ArrayList<>();

                    final int size = task.getResult().getDocuments().size() - 1;
                    final int count = 0;
                    List<DocumentSnapshot> documents = task.getResult().getDocuments();


                    //for (final DocumentSnapshot documentSnapshot : task.getResult().getDocuments()) {
                    for (int i = 0; i <= size; i++){

                        final int currentI = i;

                        DocumentSnapshot documentSnapshot = documents.get(i);

                        String id = documentSnapshot.getId();
                        // here you can get the id.
                        if(className.equals(User.class.getSimpleName())){
                            final User model = new User();
                            model.setId(id);
                            model.mapToModel(new CallbackModel() {
                                @Override
                                public void onSuccess(Object id) {

                                    users.add((User) id);
                                    if(size == currentI){
                                        callbackModel.onSuccess(users);
                                    }
                                }

                                @Override
                                public void onError(Object model, String message) {
                                    callbackModel.onError(model,message);
                                }
                            }, documentSnapshot.getData());
                        }
                        else if(className.equals(Car.class.getSimpleName())){
                            final Car model = new Car();
                            model.setId(id);
                            model.mapToModel(new CallbackModel() {
                                @Override
                                public void onSuccess(Object id) {
                                    cars.add((Car) id);
                                    if(size == currentI){
                                        callbackModel.onSuccess(cars);
                                    }
                                }

                                @Override
                                public void onError(Object model, String message) {
                                    callbackModel.onError(model,message);
                                }
                            },documentSnapshot.getData());
                        }
                        else if(className.equals(State.class.getSimpleName())){
                            final State model = documentSnapshot.toObject(State.class);
                            model.setId(id);
                            model.mapToModel(new CallbackModel() {
                                @Override
                                public void onSuccess(Object id) {
                                    states.add((State) id);
                                    if(size == currentI){
                                        callbackModel.onSuccess(states);
                                    }
                                }

                                @Override
                                public void onError(Object model, String message) {
                                    callbackModel.onError(model,message);
                                }
                            },documentSnapshot.getData());
                            //callbackModel.onSuccess(com.udea.pi2.carapp.model);
                        }
                        else if(className.equals(Route.class.getSimpleName())){
                            final Route model = documentSnapshot.toObject(Route.class);
                            model.setId(id);
                            model.mapToModel(new CallbackModel() {
                                @Override
                                public void onSuccess(Object id) {
                                    routes.add((Route) id);
                                    if(size == currentI){
                                        callbackModel.onSuccess(routes);
                                    }
                                }

                                @Override
                                public void onError(Object model, String message) {
                                    callbackModel.onError(model, message);
                                }
                            },documentSnapshot.getData());
                            //callbackModel.onSuccess(com.udea.pi2.carapp.model);
                        }
                        /*else if(className == "RoutePassenger"){
                            RoutePassenger com.udea.pi2.carapp.model = documentSnapshot.toObject(RoutePassenger.class);
                            com.udea.pi2.carapp.model.setId(id);
                            callbackModel.onSuccess(com.udea.pi2.carapp.model);
                        }*/


                        // you can apply your actions...
                    }
                } else {

                }
            }
        });

    }

    private boolean addToList(Object model, String className){
        if(className == User.class.getSimpleName()){

        }
        else if(className == Car.class.getSimpleName()){

        }
        else if(className == Route.class.getSimpleName()){

        }
        else if(className == State.class.getSimpleName()){

        }
        return false;
    }

    static private void setModel(CallbackModel callbackModel, Object model, int size, int count) {

        if(count == (size - 1)){
            callbackModel.onSuccess(model);
        }
    }


    public void saveModel(final CallbackModel callbackModel){
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mFirestore = FirebaseFirestore.getInstance();


        if(this.getId() == null){

            mFirestore.collection(this.getClass().getSimpleName()).add(this.toMap())
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Log.d("SUCCESS MODEL", "DocumentSnapshot written with ID: " + documentReference.getId());
                            callbackModel.onSuccess(documentReference.getId());
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.w("FAILURE MODEL", "Error adding document", e);
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
