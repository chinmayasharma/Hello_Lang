package jackah2.hellolangfeed;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class LoginActivity extends Activity {
    EditText email,password;
    Button login;
    TextView register;
    String emailtxt,passwordtxt;
    List<NameValuePair> params;
    SharedPreferences pref;
    Dialog reset;
    ServerRequest sr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        sr = new ServerRequest();

        email = (EditText)findViewById(R.id.login_username);
        password = (EditText)findViewById(R.id.login_password);
        login = (Button)findViewById(R.id.login_button);
        register = (TextView)findViewById(R.id.register_link);
        //forpass = (Button)findViewById(R.id.register_link);

        pref = getSharedPreferences("AppPref", MODE_PRIVATE);


        login.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                emailtxt = email.getText().toString();
                passwordtxt = password.getText().toString();
                params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("email", emailtxt));
                params.add(new BasicNameValuePair("password", passwordtxt));
                ServerRequest sr = new ServerRequest();
                JSONObject json = sr.getJSON("http://10.0.2.2:8080/login",params);
                if(json != null){
                try{
                String jsonstr = json.getString("response");
                    if(json.getBoolean("res")){
                        String token = json.getString("token");
                        String grav = json.getString("grav");
                        SharedPreferences.Editor edit = pref.edit();
                        edit.putString("token", token);
                        edit.putString("grav", grav);
                        edit.commit();

                        //TODO: Check if this is correct.
                        Intent profactivity = new Intent(LoginActivity.this,Feed.class);

                        startActivity(profactivity);
                        finish();
                    }

                        Toast.makeText(getApplication(),jsonstr,Toast.LENGTH_LONG).show();

                }catch (JSONException e) {
                    e.printStackTrace();
                }
                }
            }
        });
/*
        forpass.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                reset = new Dialog(LoginActivity.this);
                reset.setTitle("Reset Password");
                reset.setContentView(R.layout.reset_pass_init);
                cont = (Button)reset.findViewById(R.id.resbtn);
                cancel = (Button)reset.findViewById(R.id.cancelbtn);
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        reset.dismiss();
                    }
                });
                res_email = (EditText)reset.findViewById(R.id.email);

                cont.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        email_res_txt = res_email.getText().toString();

                        params = new ArrayList<NameValuePair>();
                        params.add(new BasicNameValuePair("email", email_res_txt));

                        JSONObject json = sr.getJSON("http://10.0.2.2:8080/api/resetpass", params);

                        if (json != null) {
                            try {
                                String jsonstr = json.getString("response");
                                if(json.getBoolean("res")){
                                Log.e("JSON", jsonstr);
                                Toast.makeText(getApplication(), jsonstr, Toast.LENGTH_LONG).show();
                                reset.setContentView(R.layout.reset_pass_code);
                                cont_code = (Button)reset.findViewById(R.id.conbtn);
                                code = (EditText)reset.findViewById(R.id.code);
                                newpass = (EditText)reset.findViewById(R.id.npass);
                                cancel1 = (Button)reset.findViewById(R.id.cancel);
                                cancel1.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            reset.dismiss();
                                        }
                                    });
                                cont_code.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        code_txt = code.getText().toString();
                                        npass_txt = newpass.getText().toString();
                                        Log.e("Code",code_txt);
                                        Log.e("New pass",npass_txt);
                                        params = new ArrayList<NameValuePair>();
                                        params.add(new BasicNameValuePair("email", email_res_txt));
                                        params.add(new BasicNameValuePair("code", code_txt));
                                        params.add(new BasicNameValuePair("newpass", npass_txt));

                                        JSONObject json = sr.getJSON("http://10.0.2.2:8080/api/resetpass/chg", params);

                                        if (json != null) {
                                            try {

                                                String jsonstr = json.getString("response");
                                                if(json.getBoolean("res")){
                                                reset.dismiss();
                                                Toast.makeText(getApplication(),jsonstr,Toast.LENGTH_LONG).show();

                                                }else{
                                                    Toast.makeText(getApplication(),jsonstr,Toast.LENGTH_LONG).show();

                                                }
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                        }

                                        }
                                });
                                }else{

                                    Toast.makeText(getApplication(),jsonstr,Toast.LENGTH_LONG).show();

                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                    }
                });
                reset.show();
            }
        });
*/
    }

    public void onClick(View v){
        Intent regactivity = new Intent(LoginActivity.this,RegisterActivity.class);
        startActivity(regactivity);
        finish();
    }
}
