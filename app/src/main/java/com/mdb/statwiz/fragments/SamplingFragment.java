package com.mdb.statwiz.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.mdb.statwiz.R;

public class SamplingFragment extends Fragment {
    private WebView samplingWebView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.webview_fragment, container, false);
        samplingWebView = (WebView) layout.findViewById(R.id.webview);
        return layout;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        WebSettings webSettings = samplingWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        samplingWebView.loadUrl("file:///android_asset/sampling.html");
    }
}
