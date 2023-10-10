package com.example.assignment2partb;

import android.app.Activity;
import android.widget.Toast;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

// The purpose of this file is to store the common methods that are used for API calls.
// Follows a singleton approach
public class RemoteUtilities
{
    public static RemoteUtilities remoteUtilities = null;
    private Activity uiActivity;
    public RemoteUtilities(Activity uiActivity)
    {

        this.uiActivity = uiActivity;
    }

    public void setUiActivity(Activity uiActivity)
    {
        this.uiActivity = uiActivity;
    }

    // To follow Singleton structure
    public static RemoteUtilities getInstance(Activity uiActivity)
    {
        if(remoteUtilities == null)
        {
            remoteUtilities = new RemoteUtilities(uiActivity);
        }

        remoteUtilities.setUiActivity(uiActivity);
        return remoteUtilities;
    }

    // The following method is for opening a remote connection
    public HttpURLConnection openConnection(String urlString)
    {
        HttpURLConnection conn = null;

        try
        {
            URL url = new URL(urlString);
        }
        catch(MalformedURLException e)
        {
            e.printStackTrace();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }

        // If the connection is null
        if(conn == null)
        {
            uiActivity.runOnUiThread(new Runnable()
            {
                @Override
                public void run()
                {
                    Toast.makeText(uiActivity, "Check Internet", Toast.LENGTH_LONG).show();
                }
            });
        }

        return conn;
    }

    // The following method is to check to response code, checking if the connection is okay
    public boolean connectionStatus(HttpURLConnection conn)
    {
        try
        {
            // If the connection is ok
            if(conn.getResponseCode() == HttpURLConnection.HTTP_OK)
            {
                return true;
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
            uiActivity.runOnUiThread(new Runnable()
            {
                @Override
                public void run()
                {
                    Toast.makeText(uiActivity, "Problem with API endpoint", Toast.LENGTH_LONG).show();
                }
            });
        }
        return false;
    }

    // The following method is used to get the response
    public String getResponseString(HttpURLConnection conn)
    {
        String data = null;
        try
        {
            InputStream inputStream = conn.getInputStream();
            byte[] byteData = IOUtils.toByteArray(inputStream);
            data = new String(byteData, StandardCharsets.UTF_8);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return data;
    }
}
