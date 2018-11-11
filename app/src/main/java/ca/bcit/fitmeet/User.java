package ca.bcit.fitmeet;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class User {

    private String email;

    public User(){

    }

    public User(String email) {
        this.email = email;
    }


    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("email", email);
        return result;
    }
}
