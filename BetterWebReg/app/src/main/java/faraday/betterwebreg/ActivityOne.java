package faraday.betterwebreg;

import android.app.Activity;
import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ActivityOne extends AppCompatActivity {
    //Written in HTML, JavaScript, Java, Groovy, and XML
    Button button1;
    FloatingActionButton button2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one);
        button1= (Button) findViewById(R.id.button1);
        button2= (FloatingActionButton) findViewById(R.id.floatingActionButton2) ;
        button2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent y = new Intent(ActivityOne.this,BusViewActivity.class);
                startActivity(y);

            }
        });
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(ActivityOne.this,WebActivity.class);
                EditText usern,pass;
                usern = (EditText) findViewById(R.id.editText2);
                pass = (EditText) findViewById(R.id.editText) ;
                WebActivity.usernam = usern.getText().toString();WebActivity.password=pass.getText().toString();

                    startActivity(a/*,  ActivityOptions.makeClipRevealAnimation(button1,100,100,100,100).toBundle()*/);

                }
        });

    }

}

/*
ActivityOne.this.runOnUiThread(new Runnable() {
                    public void run() {
                        new AlertDialog.Builder(ActivityOne.this).setIcon(R.drawable.ic_access_time_black_24dp).setTitle("WARNING:")
                                .setMessage("The following action will take a while to compute as the NextBus API is slow. This action may take almost 20 seconds depending on your connection speed.")
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        }).show();
                    }
                });
 */