package org.cnodejs.util;

import android.text.Html;
import android.text.Spanned;
import android.webkit.WebView;
import android.widget.TextView;

import org.cnodejs.api.Constants;
import org.markdownj.MarkdownProcessor;

public class Markdown {

    private static final MarkdownProcessor renderer = new MarkdownProcessor();

    public static void render(TextView textView, String markdown) {
        String html = renderer.markdown(markdown);
        Spanned spanned = Html.fromHtml(html);
        textView.setText(spanned);
    }

    public static void render(WebView webView, String markdown) {
        String html = renderer.markdown(markdown);
        webView.loadDataWithBaseURL(Constants.BASE, html, "text/html", "utf8", null);
    }

}
