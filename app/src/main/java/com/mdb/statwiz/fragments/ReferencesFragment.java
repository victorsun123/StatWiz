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

public class ReferencesFragment extends Fragment {
    private WebView referencesWebView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.references_fragment, container, false);
        referencesWebView = (WebView) layout.findViewById(R.id.references_webview);
        return layout;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        WebSettings webSettings = referencesWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        referencesWebView.loadUrl("file:///android_asset/references.html");
    }
}
