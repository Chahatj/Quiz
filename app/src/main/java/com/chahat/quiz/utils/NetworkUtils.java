package com.chahat.quiz.utils;

import android.net.Uri;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by chahat on 27/7/17.
 */

public class NetworkUtils {

    private static final String CATEGORY_URL = "https://www.opentdb.com/api_category.php";
    private static final String QUIZ_URL = "https://opentdb.com/api.php";

    public static URL builtURLCategory(){

        Uri uri = Uri.parse(CATEGORY_URL);

        URL url = null;

        if (uri!=null){
            try {
                url = new URL(uri.toString());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
        return url;
    }

    public static URL builtURLQuiz(int numOfQuestion,int category,String difficulty,String type){

        Uri uri = Uri.parse(QUIZ_URL);

        Uri.Builder builder = uri.buildUpon();

        builder.appendQueryParameter("amount",""+numOfQuestion);

        if (category!=-1){
            builder.appendQueryParameter("category",""+category);
        }

        if (difficulty!=null){
            builder.appendQueryParameter("difficulty",difficulty);
        }

        if (type!=null){
            builder.appendQueryParameter("type",type);
        }

        URL url = null;

            try {
                url = new URL(builder.build().toString());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

        return url;

    }

    public static String getResponseFromHttpURL(URL url) throws IOException{

        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

        try {
            InputStream in = httpURLConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");
            boolean hasNext = scanner.hasNext();
            if (hasNext) {
                return scanner.next();
            } else {
                return null;
            }
        }finally {
            httpURLConnection.disconnect();
        }

    }
}
