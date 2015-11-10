package com.example.thomdejong.qr_reader_application;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openQRScanner();
            }
        });
    }

    // opens the QR scanner
    private void openQRScanner(){
        IntentIntegrator integrator = new IntentIntegrator(this);
        // only search for QR codes
        integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
        integrator.setPrompt("Scan a QR code");
        // Start scan
        integrator.initiateScan();
    }

    // Get result of the QR scanner
    // Called when QR code is found on camera
    @Override
    protected void onActivityResult(int requestcode, int resultcode, Intent intent){
        // Get result
        IntentResult intentResult = IntentIntegrator.parseActivityResult(requestcode, resultcode, intent);
        String contents = intentResult.getContents();

        if(isUrl(contents)) {
            // Open URL
            Intent browserIntent = new Intent(Intent.ACTION_VIEW).setData(Uri.parse(contents));
            startActivity(browserIntent);
        }else {
            // Set text
            TextView textView = (TextView) findViewById(R.id.text);
            textView.setText(contents);
        }
    }

    private boolean isUrl(String value){
        boolean result = true;
        try {
            // Checks start with "http(s)://"
            URL url = new URL(value);
            // Checks buildup of url "text.text"
            url.toURI();
        } catch (MalformedURLException e){
            result = false;
        } catch (URISyntaxException e) {
            result = false;
        }
        return result;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
