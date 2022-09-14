package com.aumento.intelligentfarming.Fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.aumento.intelligentfarming.MainActivity;
import com.aumento.intelligentfarming.R;
import com.aumento.intelligentfarming.utils.GlobalPreference;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class WeatherFragment extends Fragment {

    private String ip;

    List<String> month = new ArrayList<>();
    List<String> year = new ArrayList<>();

    private Spinner MMspin;
    private Spinner YYspin;

    public WeatherFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        GlobalPreference globalPreference = new GlobalPreference(getActivity());
        ip = globalPreference.RetriveIP();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_weather, container, false);

        final WebView webview = (WebView)view.findViewById(R.id.wv);

        month.add("January");
        month.add("February");
        month.add("March");
        month.add("April");
        month.add("May");
        month.add("June");
        month.add("July");
        month.add("August");
        month.add("September");
        month.add("October");
        month.add("November");
        month.add("December");

        year.add("2019");
        year.add("2020");
        year.add("2021");

        MMspin = (Spinner) view.findViewById(R.id.mmspinner);
        ArrayAdapter MM = new ArrayAdapter(getContext(),android.R.layout.simple_spinner_item,month);
        MM.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        MMspin.setAdapter(MM);

        YYspin = (Spinner) view.findViewById(R.id.yyspinner);
        ArrayAdapter YY = new ArrayAdapter(getActivity(),android.R.layout.simple_spinner_item,year);
        YY.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        YYspin.setAdapter(YY);

        Button marketSearchButton = (Button) view.findViewById(R.id.marketSearchButton);
        marketSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String month = MMspin.getSelectedItem().toString();
                String year  = YYspin.getSelectedItem().toString();

                webview.loadUrl("https://www.accuweather.com/en/in/kochi/204289/"+month+"-weather/204289?year="+year+"&view=list");

            }
        });

        webview.getSettings().setJavaScriptEnabled(true);
        webview.setWebViewClient(new WebViewClient() {
            ProgressDialog progressDialog;

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error){
                Toast.makeText(getActivity(), "Your Internet Connection May not be active" , Toast.LENGTH_LONG).show();
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return true; //Indicates WebView to NOT load the url;
            }

            @Override public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);

                if (progressDialog == null) {
                    progressDialog = new ProgressDialog(getActivity());
                    progressDialog.setMessage("Loading...");
                    progressDialog.show();
                }

            }

            @Override public void onPageCommitVisible(WebView view, String url) {
                super.onPageCommitVisible(view, url);
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                    progressDialog = null;
                }
            }

            @Override
            public void onPageFinished(WebView view, String url)
            {
                webview.loadUrl("javascript:(function() { " +
                        "document.getElementsByClassName('adhesion-header')[0].style.display='none'; " +
                        "document.getElementsByClassName('page-subnav')[0].style.display='none'; " +
                        "document.getElementsByClassName('page-column-2')[0].style.display='none'; "+
                        "document.getElementsByClassName('neighbors-wrapper')[0].style.display='none'; "+
                        "document.getElementsByClassName('breadcrumbs-wrapper')[0].style.display='none'; "+
                        "document.getElementsByClassName('footer-legalese')[0].style.display='none'; "+
                        "document.getElementById('base-header').style.display='none'; "+
                        "document.getElementById('native').style.display='none'; "+
                        "document.getElementById('bottom').style.display='none'; "+
                        "document.getElementById('square-kiss').style.display='none'; "+
                        "document.getElementById('connatix').style.display='none'; "+
                        "})()");
            }
        });

        webview.canGoBack();
        webview.clearHistory();
        webview.loadUrl("https://www.accuweather.com/en/in/kochi/204289/april-weather/204289?year=2020&view=list");

//        webview.setWebViewClient(new WebViewClient() {
//
//            ProgressDialog progressDialog;
//
//            @Override
//            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error){
//                Toast.makeText(getActivity(), "Your Internet Connection May not be active" , Toast.LENGTH_LONG).show();
//            }
//
//            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//
//                view.loadUrl(url);
//                return true;
//            }
//
//           /* @Override public void onPageStarted(WebView view, String url, Bitmap favicon) {
//                super.onPageStarted(view, url, favicon);
//
//                if (progressDialog == null) {
//                    progressDialog = new ProgressDialog(getActivity());
//                    progressDialog.setMessage("Loading...");
//                    progressDialog.show();
//                }
//
//            }
//
//            @Override public void onPageCommitVisible(WebView view, String url) {
//                super.onPageCommitVisible(view, url);
//                if (progressDialog.isShowing()) {
//                    progressDialog.dismiss();
//                    progressDialog = null;
//                }
//            }*/
//
//            /*public void onLoadResource (WebView view, String url) {
//
//                if (progressDialog == null) {
//                    progressDialog = new ProgressDialog(getActivity());
//                    progressDialog.setMessage("Loading...");
//                    progressDialog.show();
//                }
//
//            }
//            public void onPageFinished(WebView view, String url) {
//                try{
//                    if (progressDialog.isShowing()) {
//                        progressDialog.dismiss();
//                        progressDialog = null;
//                    }
//
//                }catch(Exception exception){
//                    exception.printStackTrace();
//                }
//            }*/
//
//        });
        return view;
    }


}
