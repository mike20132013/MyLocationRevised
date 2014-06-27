package com.mike.imageutils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collections;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.mike.mylocationrevised.R;

public class ImageLoader {

    MemoryCache memoryCache = new MemoryCache();
    FileCache fileCache;
    private Map<ImageView, String> imageViews = Collections.synchronizedMap(new WeakHashMap<ImageView, String>());

    private Map<LinearLayout, String> imageViews2 = Collections.synchronizedMap(new WeakHashMap<LinearLayout, String>());

    ExecutorService executorService;

    public ImageLoader(Context context) {
        fileCache = new FileCache(context);
        executorService = Executors.newFixedThreadPool(5);
    }

    int stub_id = R.drawable.ic_launcher;


    public void DisplayImage(String url, int loader, ImageView imageView) {
        stub_id = loader;
        imageViews.put(imageView, url);
        Bitmap bitmap = memoryCache.get(url);
        if (bitmap != null)
            imageView.setImageBitmap(bitmap);
        else {
            queuePhoto(url, imageView);
            imageView.setImageResource(loader);
        }
    }


    public void DisplayImage2(String url, int loader, LinearLayout linearLayout) {

        imageViews2.put(linearLayout, url);
        Bitmap bitmap = memoryCache.get(url);
        //BitmapDrawable d = new BitmapDrawable(bitmap);
        //Drawable d = new BitmapDrawable2(context.getResources(), bitmap);


        if (bitmap != null) {

            System.out.println("DisplayImage2 not null");
            BitmapDrawable d = new BitmapDrawable(bitmap);
            //linearLayout.setBackgroundDrawable(d);
            linearLayout.setBackgroundDrawable(d);
        } else {
            System.out.println("DisplayImage2 null");
            queuePhoto2(url, linearLayout);
            //linearLayout.setBackgroundDrawable(d);
            BitmapDrawable d = new BitmapDrawable(bitmap);
            linearLayout.setBackgroundDrawable(d);
        }

    }


    public void setBackgroundLinearLayout(String url, int loader, LinearLayout someBackground) {

        stub_id = loader;
        imageViews2.put(someBackground, url);
        Bitmap bitmap = memoryCache.get(url);

        if (bitmap != null) {
            System.out.println("Bitmap not null");
            //BitmapDrawable mBitmapDrawable = new BitmapDrawable(bitmap);
            BitmapDrawable d = new BitmapDrawable(bitmap);
            //Drawable d = new BitmapDrawable(context.getResources(), bitmap);
            someBackground.setBackgroundDrawable(d);
            //someBackground.setBackgroundDrawable(d);

        } else {

            queuePhoto2(url, someBackground);
        }


    }

    private void queuePhoto(String url, ImageView imageView) {
        PhotoToLoad p = new PhotoToLoad(url, imageView);
        executorService.submit(new PhotosLoader(p));
    }

    private void queuePhoto2(String url, LinearLayout linearLayout) {

        PhotoToLoad2 p = new PhotoToLoad2(url, linearLayout);
        executorService.submit(new PhotosLoader2(p));

    }

    private Bitmap getBitmap(String url) {
        File f = fileCache.getFile(url);

        //from SD cache
        Bitmap b = decodeFile(f);
        if (b != null)
            return b;

        //from web
        try {
            Bitmap bitmap = null;
            URL imageUrl = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) imageUrl.openConnection();
            conn.setConnectTimeout(30000);
            conn.setReadTimeout(30000);
            conn.setInstanceFollowRedirects(true);
            InputStream is = conn.getInputStream();
            OutputStream os = new FileOutputStream(f);
            Utils.CopyStream(is, os);
            os.close();
            bitmap = decodeFile(f);
            return bitmap;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    //decodes image and scales it to reduce memory consumption
    private Bitmap decodeFile(File f) {
        try {
            //decode image size
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(new FileInputStream(f), null, o);

            //Find the correct scale value. It should be the power of 2.
            final int REQUIRED_SIZE = 100;
            int width_tmp = o.outWidth, height_tmp = o.outHeight;
            int scale = 1;
            while (true) {
                if (width_tmp / 2 < REQUIRED_SIZE || height_tmp / 2 < REQUIRED_SIZE)
                    break;
                width_tmp /= 2;
                height_tmp /= 2;
                scale *= 2;
            }

            //decode with inSampleSize
            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            return BitmapFactory.decodeStream(new FileInputStream(f), null, o2);
        } catch (FileNotFoundException e) {


        }
        return null;
    }

    //Task for the queue
    private class PhotoToLoad {
        public String url;
        public ImageView imageView;

        public PhotoToLoad(String u, ImageView i) {
            url = u;
            imageView = i;
        }
    }

    private class PhotoToLoad2 {

        public String url;
        public LinearLayout mlinearLayout;

        public PhotoToLoad2(String u, LinearLayout linearLayout) {

            url = u;
            mlinearLayout = linearLayout;
        }

    }

    class PhotosLoader implements Runnable {
        PhotoToLoad photoToLoad;

        PhotosLoader(PhotoToLoad photoToLoad) {
            this.photoToLoad = photoToLoad;
        }

        @Override
        public void run() {
            if (imageViewReused(photoToLoad))
                return;
            Bitmap bmp = getBitmap(photoToLoad.url);
            memoryCache.put(photoToLoad.url, bmp);
            if (imageViewReused(photoToLoad))
                return;
            BitmapDisplayer bd = new BitmapDisplayer(bmp, photoToLoad);
            Activity a = (Activity) photoToLoad.imageView.getContext();
            a.runOnUiThread(bd);
        }
    }

    class PhotosLoader2 implements Runnable {


        PhotoToLoad2 photoToLoad2;

        PhotosLoader2(PhotoToLoad2 photoToLoad2) {

            this.photoToLoad2 = photoToLoad2;

        }

        @Override
        public void run() {

            if (imageViewReused2(photoToLoad2))
                return;

            Bitmap bmp = getBitmap(photoToLoad2.url);
            memoryCache.put(photoToLoad2.url, bmp);
            if (imageViewReused2(photoToLoad2))
                return;

            BitmapDisplayer2 bd = new BitmapDisplayer2(bmp, photoToLoad2);
            Activity a = (Activity) photoToLoad2.mlinearLayout.getContext();
            a.runOnUiThread(bd);

        }
    }

    boolean imageViewReused(PhotoToLoad photoToLoad) {
        String tag = imageViews.get(photoToLoad.imageView);
        if (tag == null || !tag.equals(photoToLoad.url))
            return true;
        return false;
    }

    boolean imageViewReused2(PhotoToLoad2 photoToLoad2) {

        String tag = imageViews2.get(photoToLoad2.mlinearLayout);
        if (tag == null || !tag.equals(photoToLoad2.url))
            return true;

        return false;

    }

    //Used to display bitmap in the UI thread
    class BitmapDisplayer implements Runnable {
        Bitmap bitmap;
        PhotoToLoad photoToLoad;

        public BitmapDisplayer(Bitmap b, PhotoToLoad p) {
            bitmap = b;
            photoToLoad = p;
        }

        public void run() {
            if (imageViewReused(photoToLoad))
                return;
            if (bitmap != null) {
                System.out.println("BitmapDisplayer not null");
                photoToLoad.imageView.setImageBitmap(bitmap);
            } else {
                System.out.println("BitmapDisplayer null");
                photoToLoad.imageView.setImageResource(stub_id);
            }
        }
    }

    class BitmapDisplayer2 implements Runnable {

        Bitmap bitmap;
        PhotoToLoad2 photoToLoad2;
        //Resources resources;
        //Drawable d, stub;
        //BitmapDrawable d;

        public BitmapDisplayer2(Bitmap b, PhotoToLoad2 p) {

            bitmap = b;
            photoToLoad2 = p;
            //resources = context1.getResources();
            //d = new BitmapDrawable(context1.getResources(), bitmap);

        }

        @Override
        public void run() {

            if (imageViewReused2(photoToLoad2))
                return;
            if (bitmap != null) {
                System.out.println("BitmapDisplayer2 not null");
                BitmapDrawable d = new BitmapDrawable(bitmap);

                photoToLoad2.mlinearLayout.setBackgroundDrawable(d);
            } else {
                System.out.println("BitmapDisplayer2 null");
                BitmapDrawable d = new BitmapDrawable(bitmap);
                photoToLoad2.mlinearLayout.setBackgroundDrawable(d);
            }
        }
    }

    public void clearCache() {
        memoryCache.clear();
        fileCache.clear();
    }

}
