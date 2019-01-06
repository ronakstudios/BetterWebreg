package faraday.betterwebreg;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;


public class WebActivity extends AppCompatActivity {
WebView webView;
ProgressBar progressBar;
    static String usernam="", password=""; //Strings Username and Password are retrieved from the first activity (ActivityOne)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);


        //Disable Touch while WebActivity Runs
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

        //Initiate the Webview
        webView = (WebView) findViewById(R.id.webview1);

        //Initiate Progress bar
        progressBar = (ProgressBar) findViewById(R.id.progressBar) ;
        progressBar.setProgress(0);


        //Clear the Webview's javascript cookies incase they have already been logged in so that it doesn't interfere with the following process
        CookieSyncManager cookieSyncMngr=CookieSyncManager.createInstance(WebActivity.this);
        cookieSyncMngr.startSync();
        CookieManager cookieManager=CookieManager.getInstance();
        cookieManager.removeAllCookie();
        cookieManager.removeSessionCookie();
        cookieSyncMngr.stopSync();
        cookieSyncMngr.sync();

        webView.addJavascriptInterface(new ScheduleGetter(),"ScheduleGetter"); //Inject this java object into the javascript of the webpage


        //in this block all the javascript injection happens
        //first webView.loadUrl("https://sims.rutgers.edu/webreg/chooseSemester.htm?login=cas"); which is below this block is actually run and then all this is called
            webView.setWebViewClient(new WebViewClient() {

        //This javascript basically enters the username and password of the user into webreg and clicks buttons and links on the website until it gets their schedule as a string
        // The reason we have to do this is because the Rutgers API doesn't have anything for getting your schedule from webreg
                @Override
                public void onPageFinished(WebView view, String url) {
                    super.onPageFinished(view, url);
                    progressBar.setProgress(1);
                    webView.loadUrl("javascript:document.getElementById(\"username\").value=\""+usernam+"\";document.getElementById(\"password\").value=\""+password+"\";document.getElementsByName(\"submit\")[0].click();");

                    webView.setWebViewClient(new WebViewClient() {


                        @Override
                        public void onPageFinished(WebView view, String url) {
                            super.onPageFinished(view, url);
                            progressBar.setProgress(2);
                    webView.loadUrl("javascript:document.getElementById(\"semesterSelection2\").click();document.getElementById(\"submit\").click();");
                            webView.setWebViewClient(new WebViewClient() {


                                @Override
                                public void onPageFinished(WebView view, String url) {
                                    super.onPageFinished(view, url);
                                    progressBar.setProgress(3);
                    webView.loadUrl("javascript:window.location.href=\"https://sims.rutgers.edu/webreg/viewScheduleByCourse.htm\";");
                                    webView.setWebViewClient(new WebViewClient() {


                                        @Override
                                        public void onPageFinished(WebView view, String url) {
                                            super.onPageFinished(view, url);
                                            progressBar.setProgress(4);
                                            //get the schedule as a string
                    webView.loadUrl("javascript:window.ScheduleGetter.getSchedule_in_html(document.getElementsByClassName(\"list-course\")[0].innerHTML);");
                                            Intent a = new Intent(WebActivity.this,ScheduleViewerActivity.class);
                                            startActivity(a);

                                            webView.setWebViewClient(new WebViewClient() {


                                                @Override
                                                public void onPageFinished(WebView view, String url) {
                                                    super.onPageFinished(view, url);
                                                    Intent a = new Intent(WebActivity.this,ScheduleViewerActivity.class);
                                                    startActivity(a);




                                                }
                                            });




                                        }
                                    });

                                }
                            });
                        }
                    });

                }
            });

            //Allow javascript
            WebSettings webSettings = webView.getSettings();
            webSettings.setJavaScriptEnabled(true);

            //Even though this line is all the way down here it is actually run before all the javascript
        webView.loadUrl("https://sims.rutgers.edu/webreg/chooseSemester.htm?login=cas");



    }
}
