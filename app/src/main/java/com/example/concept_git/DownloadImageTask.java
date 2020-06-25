package com.example.concept_git;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.InputStream;
// this class has three parameters params,progress,result
// params - string ,int ,url etc, // noting to pass should write void
// same as progress or result

public class DownloadImageTask extends AsyncTask<String,Void, Bitmap> {

    ImageView myImage;
    // this is accept image object
    public DownloadImageTask(ImageView myImage) {

        this.myImage = myImage;
    }

    @Override
    protected Bitmap doInBackground(String... strings) {
        // taking url as argument ;
        String urldisplay=strings[0];
        Bitmap mIcon=null;
        try{
            /// InputStream is use for read from file of webpage
            InputStream in=new java.net.URL(urldisplay).openStream();

            //This is the easiest way to load bitmaps from the sdcard.
            // Simply pass the path to the image to BitmapFactory.decodeFile() and let the Android SDK do the rest.
            mIcon= BitmapFactory.decodeStream(in);


        }catch (Exception e)
        {
            Log.e("Error =",e.getMessage());
            e.printStackTrace();

        }
        return mIcon;
    }

    @Override
    protected void onPostExecute(Bitmap result) {
        //super.onPostExecute(bitmap);
        myImage.setImageBitmap(result);
    }
}
