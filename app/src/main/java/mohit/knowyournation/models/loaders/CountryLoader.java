package mohit.knowyournation.models.loaders;

import android.content.Context;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import mohit.knowyournation.support.Helper;

/**
 * Created by mohit on 2/12/17.
 */

public class CountryLoader {
    private Context context;
    private RequestQueue requestQueue;
    private StringRequest stringRequest;
    private String URL_COUNTRY = "https://restcountries.eu/rest/v2/all";

    public CountryLoader(Context context) {
        this.context = context;
        requestQueue = Volley.newRequestQueue(context);
    }

    public void load(final OnCountryLoaded onCountryLoaded){
        stringRequest = new StringRequest(URL_COUNTRY, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Helper.log("Got response from api");
                onCountryLoaded.onResponse(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                onCountryLoaded.onError(error.toString());
            }
        });
        RetryPolicy retryPolicy = new DefaultRetryPolicy(30000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES
                , DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(retryPolicy);

        requestQueue.add(stringRequest);
    }

    public interface OnCountryLoaded {
        public void onResponse(String response);
        public void onError(String error);
    }
}
