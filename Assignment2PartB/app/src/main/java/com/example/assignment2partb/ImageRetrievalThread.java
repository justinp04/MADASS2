package com.example.assignment2partb;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

public class ImageRetrievalThread extends Thread
{
    private RemoteUtilities remoteUtilities;
    private SearchResponseViewModel sViewModel;
    private List<ImageViewModel> imageViewModels;
    private ErrorViewModel errorViewModel;
    private Activity uiActivity;
    public int numHits = 0;

    public ImageRetrievalThread(Activity uiActivity, SearchResponseViewModel viewModel, List<ImageViewModel> imageViewModels, ErrorViewModel errorViewModel)
    {
        remoteUtilities = RemoteUtilities.getInstance(uiActivity);
        this.sViewModel = viewModel;
        this.imageViewModels = imageViewModels;
        this.errorViewModel = errorViewModel;
        this.uiActivity=uiActivity;
    }

    public void run()
    {
        // gets the list of urls for images related to the sViewModel.getResponse()
        List<String> endpoints = getEndpoints(sViewModel.getResponse());

        // If there are no results
        if(endpoints == null || endpoints.size() == 0)
        {
            uiActivity.runOnUiThread(new Runnable()
            {
                @Override
                public void run()
                {
                    Toast.makeText(uiActivity,"No images found",Toast.LENGTH_LONG).show();
                    errorViewModel.setErrorCode(errorViewModel.getErrorCode() + 1);
                }
            });
        }
        else
        {
            // Iterate through as many results there are.
            for(int i = 0; i < endpoints.size(); i++)
            {
                Bitmap image = getImageFromUrl(endpoints.get(i));

                try
                {
                    Thread.sleep(1000);
                }
                catch (Exception e) {}

                Log.d("ID", "" + image);

                imageViewModels.get(i).setImage(image);
            }
        }
    }

    private List<String> getEndpoints(String data)
    {
        List<String> imageUrls = new ArrayList<>();
        try
        {
            JSONObject jBase = new JSONObject(data);
            JSONArray jHits = jBase.getJSONArray("hits");

            if(jHits.length() > 0)
            {
                // Ternary operators cause I'm cool
                int length = jHits.length() > 15 ? 15 : jHits.length();
                numHits = length;

                for(int i = 0; i < length; i++)
                {
                    JSONObject jHitsItem = jHits.getJSONObject(i);
                    imageUrls.add(jHitsItem.getString("largeImageURL"));
                }
            }
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
        return imageUrls;
    }

    // This method retrieves the image from the URL
    private Bitmap getImageFromUrl(String imageUrl)
    {
        Bitmap image = null;
        Uri.Builder url = Uri.parse(imageUrl).buildUpon();
        String urlString = url.build().toString();
        HttpURLConnection connection = remoteUtilities.openConnection(urlString);

        if(connection!=null)
        {
            if(remoteUtilities.connectionStatus(connection)==true)
            {
                image = getBitmapFromConnection(connection);
                connection.disconnect();
            }
        }
        return image;
    }

    // This method retrieves the image from the URL
    public Bitmap getBitmapFromConnection(HttpURLConnection conn)
    {
        Bitmap data = null;

        try
        {
            InputStream inputStream = conn.getInputStream();
            byte[] byteData = getByteArrayFromInputStream(inputStream);
            data = BitmapFactory.decodeByteArray(byteData,0,byteData.length);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        return data;
    }

    // This method retrieves the image from the URL
    private byte[] getByteArrayFromInputStream(InputStream inputStream) throws IOException
    {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        int nRead;
        byte[] data = new byte[4096];

        while ((nRead = inputStream.read(data, 0, data.length)) != -1)
        {
            buffer.write(data, 0, nRead);
        }

        return buffer.toByteArray();
    }

}

