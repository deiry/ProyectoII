package Model;

import com.google.firebase.auth.FirebaseUser;

public class UserSingleton{

    private static volatile UserSingleton user = new UserSingleton();

    private String name;
    private String email;
    private String token;

    //private constructor
    private UserSingleton(){
    }

    public static UserSingleton getInstance(){
        return user;
    }

    public void createUserFromFirebaseUser(FirebaseUser user){
        this.name = user.getDisplayName();
        this.email = user.getEmail();
        this.token = user.getIdToken(true).toString();
    }
}
