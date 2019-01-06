package faraday.betterwebreg;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import org.xml.sax.SAXException;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

public class BusViewActivity extends AppCompatActivity {
    static SwipeRefreshLayout swipeRefreshLayout;
    TextView textView,textView2,textView3,textView4,textView5,textView6;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_view);
        textView=(TextView) findViewById(R.id.textView4);
        textView2=(TextView) findViewById(R.id.textView5);
        textView3=(TextView) findViewById(R.id.textView6);
        textView4=(TextView) findViewById(R.id.textView7);
        textView5=(TextView) findViewById(R.id.textView8);
        textView6=(TextView) findViewById(R.id.textView9);
        textView.setTextColor(Color.BLACK);
        textView2.setTextColor(Color.BLACK);
        textView3.setTextColor(Color.BLACK);
        textView4.setTextColor(Color.BLACK);
        textView5.setTextColor(Color.BLACK);
        textView6.setTextColor(Color.BLACK);
        ScrollView scrollView = (ScrollView) findViewById(R.id.scroll12);
         Intent noficationIntent = getIntent();
         String[] bussesToTurnRed = noficationIntent.getStringArrayExtra("bussesToTurnRed");
         if(bussesToTurnRed!=null){
             Snackbar.make(scrollView,"Busses You Should Take Are Shown In Red",Snackbar.LENGTH_LONG).show();
             for(int i = 0 ;i<bussesToTurnRed.length;i++) {
                 String bus = bussesToTurnRed[i];
                 bus=bus.toLowerCase();
                 switch (bus) {
                     case "a":
                         textView.setTextColor(Color.RED);
                         break;
                     case "b":
                         textView2.setTextColor(Color.RED);
                         break;
                     case "lx":
                         textView3.setTextColor(Color.RED);
                         break;
                     case "rexl":
                         textView4.setTextColor(Color.RED);
                         break;
                     case "rexb":
                         textView5.setTextColor(Color.RED);
                         break;
                     case "f":
                         textView6.setTextColor(Color.RED);
                         break;
                 }
             }
         }

        swipeRefreshLayout=(SwipeRefreshLayout) findViewById(R.id.swiperefresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshable();

            }
        });
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_red_dark,android.R.color.black,android.R.color.darker_gray);
      refreshable();

    }



    public void refreshable() {
        boolean[] loadRefresher = {false,false,false,false,false,false};
        ASYNCXMLPARSER.isAllDone= loadRefresher;
        swipeRefreshLayout.setRefreshing(true);
        NextBus.printBus("a",textView);
        NextBus.printBus("b",textView2);
        NextBus.printBus("lx",textView3);
        NextBus.printBus("rexl", textView4);
        NextBus.printBus("rexb",textView5);
        NextBus.printBus("f",textView6);
    }
}
