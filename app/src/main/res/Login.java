package ellere.cooksmart;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.textclassifier.TextClassification;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static ellere.cooksmart.API_creator.BASE_URL;

public class Login extends AppCompatActivity {
     Button loginbtn, gosignupbtn;
    TextView textView1;
    EditText user, pass;
    String reg_url = BASE_URL+"login.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        gosignupbtn=(Button) findViewById(R.id.gotosignup_btn);
        loginbtn=(Button) findViewById(R.id.login_btn);
        user=(EditText) findViewById(R.id.username);
        pass=(EditText) findViewById(R.id.password);
        loginbtn.setOnClickListener(new View.OnClickListener() {
                                      @Override
                                      public void onClick(View view) {
                                          String username = user.getText().toString();
                                          String password = pass.getText().toString();

                                          if (username.equals("") || password.equals("")) {
                                              Toast.makeText(getBaseContext(), "Fill up all the field properly", Toast.LENGTH_SHORT).show();
                                              return;
                                          }
                                          else
                                          {
                                              UserLoginFunction(username, password);
                                          }

                                      }
                                  }
        );

        gosignupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(Login.this,SignUp.class);
                startActivity(intent);
            }
        });
    }

    public void UserLoginFunction(final String username,  final String password){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, reg_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonObject= new JSONObject(response);
                            String success =jsonObject.getString("flag");
//                            JSONObject myObj=new JSONObject(success);
                            if (success.equals("1")){

                                    Log.d("test",">>>>1"+textView1.getText());
                                    Toast.makeText(Login.this,"Login Successful",Toast.LENGTH_SHORT).show();
                                    //if(pref.contains("username")&& pref.contains("password"))

                                    Intent intent = new Intent(Login.this, HomePage.class);
                                    startActivity(intent);
                                }

                            else
                            {
                                Toast.makeText(Login.this,"Enter correct username or password",Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(Login.this,"Login failed"+e.toString(),Toast.LENGTH_SHORT).show();
                        }


                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Login.this,"Login Failed"+error.toString(),Toast.LENGTH_SHORT).show();

                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params=new HashMap<>();
                params.put("username",username);
                params.put("password",password);

                return params;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);


    }

}