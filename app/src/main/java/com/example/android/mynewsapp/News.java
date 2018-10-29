package com.example.android.mynewsapp;

/**
 * This is a class that creates each instance of a news item it hasone constructor.
 * the constructors stores three variables  mWebTitle,mWebURl, mSectionName
 * mWebTitle is the title of the web page.
 * mSectionName represents the category the news belongs to
 * mWebUrl represents the url to view the full news
 */

public class News {
    private String mSectionName;
    private String mWebTitle;
    private String mWebUrl;

    public News(String sectionName, String webTitle, String webUrl) {
        mSectionName = sectionName;
        mWebTitle = webTitle;
        mWebUrl = webUrl;
    }

    public String getSectionName() {
        return mSectionName;
    }

    public String getWebTitle() {
        return mWebTitle;
    }

    public String getWebUrl() {
        return mWebUrl;
    }
}
