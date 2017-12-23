package com.uidia.aadhaar.aadharbiometricauthentication;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import java.io.ByteArrayOutputStream;

public class MainActivity extends AppCompatActivity {

    TextView tv;
    Button buttonInit;
    EditText edit_cardno;
    ImageView imageViewFingerPrint, imageViewFingerPrint2;

    ProgressDialog progressDialog;

//    private byte[] b = null;
//
//    private byte[] b2 = null;

    Bitmap bitmap,bitmap2;

    String encodeImage = " ";

    String encodeImage2 = " ";

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv = (TextView) findViewById(R.id.tv);
        buttonInit = (Button) findViewById(R.id.buttonInit);
        edit_cardno = (EditText) findViewById(R.id.edit_cardno);
        imageViewFingerPrint = (ImageView) findViewById(R.id.imageViewFingerPrint);
        imageViewFingerPrint2 = (ImageView) findViewById(R.id.imageViewFingerPrint2);
//        progressDialog=(ProgressDialog)findViewById(R.id.pb);


        bitmap2 = BitmapFactory.decodeResource(getResources(), R.drawable.download);
        buttonInit.setVisibility(View.INVISIBLE);
        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {

                ByteArrayOutputStream baos2 = new ByteArrayOutputStream();
                bitmap2.compress(Bitmap.CompressFormat.JPEG, 100, baos2);

                byte[] b2 = baos2.toByteArray();

                encodeImage2 = Base64.encodeToString(b2, Base64.DEFAULT);

                Log.e("test-device Image"," the image that is encrypted - "+encodeImage2);

                return encodeImage2;
            }

            @Override
            protected void onPostExecute(String s) {

//                tv.setError(s);
//                tv.setText(s);

                byte[] decodeString2 = Base64.decode(s, Base64.DEFAULT);
                Bitmap decode2 = BitmapFactory.decodeByteArray(decodeString2, 0, decodeString2.length);
                imageViewFingerPrint2.setImageBitmap(decode2);

            }
        }.execute();


        edit_cardno.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // System.out.println("onTextChanged s val"+s+"onTextChanged start==== "+""+start+"=====onTextChanged before===="+before+"====onTextChanged count====="+count);
                System.out.println("1::::::::::"+before);

                if (start < 11 || count == 0|| before== 1) {
                    buttonInit.setVisibility(View.INVISIBLE);
                    //edit_cardno.setError("Please Enter 12 numbers");
                    encodeImage = " ";
                   // imageViewFingerPrint.setImageResource(R.drawable.trans1);
                } else {
                    buttonInit.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //System.out.println(""+s+""+""+start+""+count+""+after);

            }

            @Override
            public void afterTextChanged(Editable s) {

                if (edit_cardno.getText().toString().equals("000123456789")) {

//                    imageViewFingerPrint.setBackgroundResource(R.drawable.download);

                    bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.download2);

                    new AsyncTask<Void, Void, String>() {
                        @Override
                        protected String doInBackground(Void... params) {

                            ByteArrayOutputStream baos = new ByteArrayOutputStream();
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);

                            byte[] b = baos.toByteArray();

                            encodeImage = Base64.encodeToString(b, Base64.DEFAULT);
                            //System.out.println("I am called varun :::::::" + encodeImage);
                            //Log.e("test-aadhar Image"," the image that is encrypted - "+encodeImage);

                            return encodeImage;
                        }

                        @Override
                        protected void onPostExecute(String s) {

//                tv.setError(s);
//                tv.setText(s);
                            System.out.println("Value of s is:::::::"+s);
                            byte[] decodeString = Base64.decode(s, Base64.DEFAULT);
                            Bitmap decode = BitmapFactory.decodeByteArray(decodeString, 0, decodeString.length);
                            imageViewFingerPrint.setImageBitmap(decode);
                            //System.out.println("I have been decoded::::::"+decode.toString());
//                            Log.e("print b and b2"," b - "+b+"\n \n \n"+" b2 - "+b2+"\n \n \n"+b.equals(b2));

                           // Log.e("print"," b - "+encodeImage.equals(encodeImage2));
                        }
                    }.execute();


                } else if(edit_cardno.getText().length()<12){

                    imageViewFingerPrint.setImageResource(R.drawable.trans1);

                } else{
//                    imageViewFingerPrint.setBackgroundResource(android.R.color.transparent);
                    encodeImage = " ";
                    imageViewFingerPrint.setImageResource(R.drawable.download1);

                }

            }
        });


        buttonInit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (edit_cardno.getText().toString().isEmpty()) {

                    Toast.makeText(getApplication(), "Kindly Enter Your Aadhaar Card Number", Toast.LENGTH_LONG).show();
                } else if (encodeImage.equals(encodeImage2)) {

                    progressDialog = new ProgressDialog( MainActivity.this);
                    progressDialog.setCancelable(false);
                    progressDialog.setMessage( " Encrypting Data " );
                    progressDialog.show();


                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            progressDialog.dismiss();


                            Toast.makeText(getApplicationContext(),
                                    "Fingers matched Success", Toast.LENGTH_SHORT).show();

                            new AlertDialog.Builder(MainActivity.this)
                                    .setTitle("Check Status")
                                    .setMessage(" Authenticated ")
                                    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {

                                        public void onClick(DialogInterface arg0, int arg1) {

                                        }
                                    })
                                    .setPositiveButton("Okay", new DialogInterface.OnClickListener() {

                                        public void onClick(DialogInterface arg0, int arg1) {

//                                    finish();
                                        }
                                    }).create().show();

                        }
                    }, 3000);

                } else {


                    progressDialog = new ProgressDialog( MainActivity.this);
                    progressDialog.setCancelable(false);
                    progressDialog.setMessage( " Encrypting Data " );
                    progressDialog.show();


                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            progressDialog.dismiss();

                            Toast.makeText(getApplicationContext(),
                                    "Fingers matched Failed", Toast.LENGTH_SHORT).show();

                            new AlertDialog.Builder(MainActivity.this)
                                    .setTitle("Check Status")
                                    .setMessage(" Not Authenticated ")
                                    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {

                                        public void onClick(DialogInterface arg0, int arg1) {

                                        }
                                    })
                                    .setPositiveButton("Okay", new DialogInterface.OnClickListener() {

                                        public void onClick(DialogInterface arg0, int arg1) {

//                                    finish();
                                        }
                                    }).create().show();

                        }
                    }, 3000);


                }

            }
        });

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Main Page") // TODO: Define a title for the content shown.
                        // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }
}
