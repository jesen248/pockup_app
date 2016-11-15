package com.pockinc.pockup;

/**
 * Created by Dell on 15/11/2016.
 */

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jesen on 29/10/2016.
 */

public class RegisterRequest extends StringRequest {
    private static final String REGISTER_REQUEST_URL = "http://pockup.herokuapp.com/api/users";
    private Map<String, String> params;

    public RegisterRequest(String username,  String name, String password, String password2, Response.Listener<String> listener) {
        super(Method.POST, REGISTER_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("email", username);
        params.put("password", password);
        params.put("name", name);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
