package com.pockinc.pockup;

/**
 * Created by Dell on 15/11/2016.
 */
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jesen on 29/10/2016.
 */

public class LoginRequest extends StringRequest{
    private static final String LOGIN_REQUEST_URL = "https://pockup.herokuapp.com/api/access";
    private Map<String, String> params;

    public LoginRequest(String email,  String password, Response.Listener<String> listener) {
        super(Request.Method.POST, LOGIN_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("email", email);
        params.put("password", password);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
