package adapter;

import com.udea.pi2.carapp.model.User;

public class UserAdapter {

    public String name;
    public String email;
    public String token;

    public UserAdapter() {
    }

    public UserAdapter(User user) {
        this.name = user.getName();
        this.email = user.getEmail();
        this.token = user.getToken();
    }


}
