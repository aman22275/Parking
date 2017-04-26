package com.example.singh.parking;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class Myprofile extends AppCompatActivity {

    TextView printPlace;
    Button bookPlace;
    String user,phone,place="aman";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myprofile);
        String d= getIntent().getExtras().getString("reference");
        Toast.makeText(getBaseContext(),d,Toast.LENGTH_LONG).show();

        printPlace = (TextView)findViewById(R.id.textView);
        bookPlace = (Button)findViewById(R.id.bookPlace);

        user = getIntent().getExtras().getString("user");
        phone = getIntent().getExtras().getString("phone");
        printPlace.setText(d);
    }

    public void book(View v)
    {
        String method = "upload";
        UploadData n = new UploadData(this);
        n.execute(method,place,user,phone);
    }


    public class UploadData extends AsyncTask<String,Void,String> {



        Context ctx;

        UploadData( Context ctx) {
            this.ctx =  ctx;
        }






        @Override
        protected String doInBackground(String... params) {
            String reg_url = "http://app-parking.esy.es/uplooadData.php";
            String method = params[0];
            if (method.equals("upload")) {
                String da = params[1];
                String u = params[2];
                String p = params[3];

                try {
                    URL url = new URL(reg_url);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    OutputStream os = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                    String data = URLEncoder.encode("place", "UTF-8") + "=" + URLEncoder.encode(da, "UTF-8") + "&" +
                            URLEncoder.encode("user", "UTF-8") + "=" + URLEncoder.encode(u, "UTF-8") + "&" +
                            URLEncoder.encode("phone", "UTF-8") + "=" + URLEncoder.encode(p, "UTF-8") ;

                    bufferedWriter.write(data);
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    os.close();

                    InputStream is = httpURLConnection.getInputStream();
                    is.close();

                    return "Parking Registered" + data;


                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }


        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String result) {
            if (result.equals("register")) {
                Toast.makeText(ctx, result, Toast.LENGTH_LONG).show();
            }
            else {
                Toast.makeText(ctx, "", Toast.LENGTH_LONG).show();

            }
        }
    }


}

