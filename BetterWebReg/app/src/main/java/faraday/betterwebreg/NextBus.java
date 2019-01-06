package faraday.betterwebreg;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;

import org.json.JSONException;
import org.json.JSONObject;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.*;
import java.io.*;
import java.net.URL;

public class NextBus {


    public static String[] busName(String endCampus){
                switch (endCampus){
                    case "Livingston":
                        String[] res2 ={"lx","rexl","b"};
                        return res2;
                    case "Busch":
                        String[]ret= {"rexb","a","b"};
                        return ret;
                    case "College Avenue":
                        String []retu={ "f","a","lx"};
                        return retu;
                    case "Cook/Douglas":
                        String retun[] ={"f","rexb","rexl"};
                            return retun;
                    default:
                return null;

        }
    }


    public static void printBus(String bus, TextView textView) {
        textView.setText("");
        ASYNCXMLPARSER thread = new ASYNCXMLPARSER();
        thread.busName=bus.toLowerCase();
        thread.textView=textView;
        thread.execute();

    }
}

