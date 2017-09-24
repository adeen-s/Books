package me.adeen.books;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.webkit.MimeTypeMap;
import android.widget.Toast;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;


/**
 * Created by adeen-s on 24/9/17.
 * Returns appropriate book info depending on semester
 */

public class BookResolver {

    ArrayList<String> mca1 = new ArrayList<String>(Arrays.asList(new String[]{"C", "FOC", "Maths-I", "Physics", "English"}));
    ArrayList<String> mca2 = new ArrayList<String>(Arrays.asList(new String[]{"C++", "BE", "French", "Chemistry", "Maths-II"}));
    ArrayList<String> mca3 = new ArrayList<String>(Arrays.asList(new String[]{"DS", "DCO", "DE", "FA", "Statistics"}));

    String toPath;


    ArrayList<String> getBookList(String course, int sem) {
        if(course.equalsIgnoreCase("mca")) {
            switch(sem) {
                case 1: return mca1;
                case 2: return mca2;
                case 3: return mca3;
            }
        }
        return new ArrayList<>(Arrays.asList(new String[]{"Not yet available"}));
    }

    boolean openBook(String course, int sem, String book, Context context) {
        String filename = (course.toLowerCase() + "-" + sem + "-" + book + ".pdf");
        try {
            if(!(Arrays.asList(context.getResources().getAssets().list("")).contains(filename))) {
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        new OpenLocalPDF(context, filename).execute();

        return true;
    }



}
