package com.mh.uistudy;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    private TextView mainText;
    private EditText chapter,verse,book_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainText=findViewById(R.id.mainText);
        chapter=findViewById(R.id.chapter);
        verse=findViewById(R.id.verse);
        book_id=findViewById(R.id.bookId);
        getSupportActionBar().hide();
        findViewById(R.id.textView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refresh("http://bible-api.com/?random=verse");
            }
        });

        findViewById(R.id.ref).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refresh("http://bible-api.com/?random=verse");
            }
        });
        findViewById(R.id.readmore).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(book_id.getText().toString().isEmpty() && verse.getText().toString().isEmpty()
                && chapter.getText().toString().isEmpty()){

                    Log.i("myapp1",""+book_id.getText().toString());

                }else
                    refresh("http://bible-api.com/"+book_id.getText().toString()+"+"
                            +chapter.getText().toString()+":"+verse.getText().toString());


                Log.i("myapp",""+book_id.getText().toString());


            }
        });
    }
    void refresh(String url){
        RequestQueue requestQueue;
        requestQueue= Volley.newRequestQueue(this);
        JsonObjectRequest jsonArrayRequest=new JsonObjectRequest(Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String reference=response.getString("reference");
                            Log.i("myapp",reference);

                            //Log.i("myapp",response.toString());
                            book_id.setText(""+response.getJSONArray("verses").getJSONObject(0).getString("book_id"));
                            chapter.setText(""+response.getJSONArray("verses").getJSONObject(0).getInt("chapter"));
                            verse.setText(""+response.getJSONArray("verses").getJSONObject(0).getInt("verse"));

                            Log.i("myapp",""+chapter);
                            Log.i("myapp",""+verse);
                            String detail=response.getJSONArray("verses").getJSONObject(0).getString("text");
                            mainText.setText(""+detail);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("myapp",error.toString());
            }
        });
        requestQueue.add(jsonArrayRequest);
    }
}