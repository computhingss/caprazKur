package com.tilda.caprazkurlar;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {
    //TextView tv;
    TextView txt_usd, txt_try;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //tv = findViewById(R.id.textView);
        txt_try = findViewById(R.id.txt_try);
        txt_usd = findViewById(R.id.txt_usd);
    }

    public void btnVerileriCek(View v){
        VerileriCek gorev = new VerileriCek();
        gorev.execute();
    }

    class VerileriCek extends AsyncTask<Void, Void, String>{

        @Override
        protected String doInBackground(Void... voids) {
            try {
                URL webAdresi = new URL("http://data.fixer.io/api/latest?access_key=SİZİN_API_KEYİNİZ");
                HttpURLConnection baglanti = (HttpURLConnection) webAdresi.openConnection();
                BufferedReader br = new BufferedReader(new InputStreamReader(baglanti.getInputStream()));

                String sonuc = "";
                String satir = "";
                while((satir=br.readLine())!=null){
                    sonuc += satir;
                }
                br.close();
                baglanti.disconnect();
                return sonuc;

            } catch (Exception e) {
                System.out.println("Hata : " + e.getMessage());
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            System.out.println("Sonuç : " + s);
            //tv.setText(s);
            try {
                JSONObject sonuc = new JSONObject(s);
                JSONObject kurlar = sonuc.getJSONObject("rates");
                String try_kuru = kurlar.getString("TRY");
                String usd_kuru = kurlar.getString("USD");
                txt_try.setText(try_kuru);
                txt_usd.setText(usd_kuru);

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }
}
