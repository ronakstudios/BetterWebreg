package faraday.betterwebreg;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class ASYNCXMLPARSER extends AsyncTask<String,Void,String[][]> {
     String busName="";
 String [][] res=null;
 TextView textView;
 static boolean[] isAllDone= new boolean[6];

private Exception exception;

    protected String[][] doInBackground(String... strings) {


try {
    // output:
    // stop1 time1 time2
    // stop2 time1 time2 time3
    // ...
    String[] stopsa = {"scott", "stuactcntr", "lot48a", "stadium_a", "werblinback", "hillw", "science", "libofsciw", "buschse", "busch_a", "buells", "werblinm", "rutgerss_a"};
    String[] stopsb = {"quads", "werblinback", "hillw", "science", "libofsci", "buschse", "busch_a", "beck", "livingston_a"};
    String[] stopslx = {"quads", "rutgerss_a", "scott", "traine", "stuactcntr", "beck","livingston_a"};
    String[] stopsf = {"scott", "pubsafs", "redoak", "lipman", "foodsci", "biel", "henders", "katzenbach", "gibbons", "college_a", "stuactcntr", "rutgerss_a"};
    String[] stopsrexl = {"lipman", "college_a", "newstree", "beck", "livingston_a", "pubsafs", "rockhall", "redoak_a"};
    String[] stopsrexb = {"lipman", "college_a", "newstree", "hillw", "allison_a", "hilln", "pubsafs", "rockhall", "redoak_a"};
    String[] stops;
    switch (busName) {
        case "a":
            stops = stopsa;
            Log.d("BUSS!", "doInBackground: B!!!");
            break;
        case "b":
            stops = stopsb;

            break;
        case "lx":
            stops = stopslx;
            break;
        case "f":
            stops = stopsf;
            break;
        case "rexl":
            stops = stopsrexl;
            break;
        case "rexb":
            stops = stopsrexb;
            break;
        default:
            return null;
    }
    String[][] result = new String[stops.length][];
    for (int i = 0; i < stops.length; i++) {

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = null;
        try {
            db = dbf.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }

        URL url = null;
        try {
            url = new URL("http://webservices.nextbus.com/service/publicXMLFeed?command=predictions&a=rutgers&r=" + busName + "&s=" + stops[i]);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        InputStream stream = null;
        try {
            stream = url.openStream();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Document doc = null;
        try {
            doc = db.parse(stream);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }

        doc.getDocumentElement().normalize();
        NodeList predictions = doc.getElementsByTagName("prediction");

        result[i] = new String[predictions.getLength() + 1];
        result[i][0] = ((Element) doc.getElementsByTagName("predictions").item(0)).getAttribute("stopTitle");
        for (int temp = 0; temp < predictions.getLength(); temp++) {
            Node nNode = predictions.item(temp);
            result[i][temp + 1] = ((Element) nNode).getAttribute("minutes");
        }


    }

    res = result;
    return result;
}catch (Exception e) {
    this.exception = e;
return null;
}
}

@Override
    protected void onPostExecute(String[][] res2){

    String tt = ("Route: "+busName.toUpperCase())+"\n";
    if (res!=null) {
        for (String[] y : res) {
            for (String c : y)
                tt += (c + " ");
            tt += "\n";
            textView.setText(tt);

            switch (busName.toLowerCase()) {
                case "a":
                    isAllDone[0]=true;
                    break;
                case "b":
                    isAllDone[1]=true;
                    break;
                case "lx":
                    isAllDone[2]=true;
                    break;
                case "f":
                    isAllDone[3]=true;
                    break;
                case "rexl":
                    isAllDone[4]=true;
                    break;
                case "rexb":
                    isAllDone[5]=true;
                    break;
            }

            if (isAllDone[0]&&isAllDone[1]&&isAllDone[2]&&isAllDone[3]&&isAllDone[4]&&isAllDone[5])
                BusViewActivity.swipeRefreshLayout.setRefreshing(false);
        }
    }else{
        textView.setText(tt+"Something went wrong! Please pull down to refresh.");
        BusViewActivity.swipeRefreshLayout.setRefreshing(false);
    }
}

}
