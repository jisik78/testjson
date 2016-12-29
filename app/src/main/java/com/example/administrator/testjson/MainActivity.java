package com.example.administrator.testjson;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private TextView mTv_url;
    private TextView mTv_show;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

         mTv_url = (TextView) findViewById(R.id.tv_url);
         mTv_show = (TextView) findViewById(R.id.tv_show);

    }

    public void request (View v){
        new Thread(new Runnable() {
            @Override
            public void run() {
            String url = mTv_url.getText().toString().trim();
                try {
                    URL url1 = new URL(url);
                    HttpURLConnection conn = (HttpURLConnection) url1.openConnection();
                    conn.setRequestMethod("GET");
                    conn.setConnectTimeout(5000);
                    conn.setReadTimeout(5000);
                    conn.connect();

                    InputStream in = conn.getInputStream();
                    final String data = inputStream2String(in);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mTv_show.setText(data);
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

    private String inputStream2String(InputStream in) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buf = new byte[1024];
        int len = -1 ;

        try {
            while ((len = in.read(buf))!= -1){
                baos.write(buf,0,len);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return  new String(baos.toByteArray());
    }
}
