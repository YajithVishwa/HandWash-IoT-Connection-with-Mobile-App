package com.yajith.hand;


import androidx.annotation.NonNull;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;

public class MainActivity extends AppCompatActivity {
    ListView listView;
    RelativeLayout relativeLayout;
    int count=6;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = findViewById(R.id.list);
        progressDialog=new ProgressDialog(this);
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("Please Wait");
        progressDialog.setCancelable(false);
        progressDialog.show();
        FirebaseMessaging.getInstance().setAutoInitEnabled(true);
        FirebaseMessaging.getInstance().subscribeToTopic("notification");
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            Log.w("TAG", "getInstanceId failed", task.getException());
                            return;
                        }

                        // Get new Instance ID token
                        String token = task.getResult().getToken();

                        // Log and toast
                        String msg = token;
                        Log.d("TAG", msg);
                    }
                });
        relativeLayout = findViewById(R.id.relative);
        FirebaseDatabase.getInstance().getReference().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                count = (int) dataSnapshot.getChildrenCount();
                async async=new async();
                async.execute();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        Log.i("Count",String.valueOf(count));
    }
    String[] name=new String[count];
    int[] value=new int[count];
    int i;
    class async extends AsyncTask<Void,String[],String>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.show();
        }

        @Override
        protected void onPostExecute(String strings) {
            super.onPostExecute(strings);
            if(strings.equals("Central Tank")) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        progressDialog.hide();
                    }
                },4000);

            }
        }

        @Override
        protected void onProgressUpdate(String[]... values) {
            super.onProgressUpdate(values);
            CustomAdapter customAdapter=new CustomAdapter(MainActivity.this,name,value);
            listView.setAdapter(customAdapter);
            onPostExecute("Central Tank");
        }

        @Override
        protected String doInBackground(Void... voids) {
            FirebaseDatabase.getInstance().getReference().child("Central_tank").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    name[0]="Central Tank";
                    value[0]=Integer.parseInt(String.valueOf(dataSnapshot.child("level").getValue()));
                    FirebaseDatabase.getInstance().getReference().child("tank01").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            name[1]="Tank 1";
                            value[1]=Integer.parseInt(String.valueOf(dataSnapshot.child("level").getValue()));
                            FirebaseDatabase.getInstance().getReference().child("tank02").addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    name[2]="Tank 2";
                                    value[2]=Integer.parseInt(String.valueOf(dataSnapshot.child("level").getValue()));
                                    FirebaseDatabase.getInstance().getReference().child("tank03").addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                            name[3]="Tank 3";
                                            value[3]=Integer.parseInt(String.valueOf(dataSnapshot.child("level").getValue()));
                                            FirebaseDatabase.getInstance().getReference().child("tank04").addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                    name[4]="Tank 4";
                                                    value[4]=Integer.parseInt(String.valueOf(dataSnapshot.child("level").getValue()));
                                                    FirebaseDatabase.getInstance().getReference().child("tank05").addListenerForSingleValueEvent(new ValueEventListener() {
                                                        @Override
                                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                            name[5]="Tank 5";
                                                            value[5]=Integer.parseInt(String.valueOf(dataSnapshot.child("level").getValue()));
                                                            publishProgress(name);
                                                        }

                                                        @Override
                                                        public void onCancelled(@NonNull DatabaseError databaseError) {

                                                        }
                                                    });
                                                }

                                                @Override
                                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                                }
                                            });
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {

                                        }
                                    });
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
            return "Central Tank";
        }
    }
}
