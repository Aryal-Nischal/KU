package activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import codeinvasion.materialmashup.R;

import android.content.Context;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

public class CheckConnection extends Activity{
    ConnectionDetector cd;
    boolean isInternetPresent=false;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        cd = new ConnectionDetector(getApplicationContext());
        isInternetPresent = cd.isConnectingToInternet();
        if (isInternetPresent) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
        else {
            AlertDialog alertDialog = new AlertDialog.Builder(
                    CheckConnection.this).create();
https://github.com/aryalNischal7717/KU.git

            // Setting Dialog Message
            alertDialog.setMessage("No Internet Connection");


            // Setting OK Button
            alertDialog.setButton("Close", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                    System.exit(0);
                }
            });
            // Showing Alert Message
            alertDialog.show();
        }
    }
}
