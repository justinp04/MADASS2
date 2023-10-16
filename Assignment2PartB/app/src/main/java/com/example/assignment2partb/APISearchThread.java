package com.example.assignment2partb;

import android.app.Activity;
import android.app.Notification;
import android.net.Uri;

import java.net.HttpURLConnection;

// This is the thread to handle search in the API to ensure that this long awaited task is still running
// whilst users are able to do other things
public class APISearchThread extends Thread
{
    private String searchKey;
    private String baseUrl;
    private RemoteUtilities remoteUtilities;
    private SearchResponseViewModel viewModel;

    // Constructor
    public APISearchThread(String searchKey, Activity activity, SearchResponseViewModel viewModel)
    {
        this.searchKey = searchKey;

        // The base URL to route to
        baseUrl = "https://pixabay.com/api/";

        // Get the singleton instance
        remoteUtilities = RemoteUtilities.getInstance(activity);
        this.viewModel = viewModel;
    }

    // This method is used to run the API search
    public void run()
    {
        String endpoint = getSearchEndpoint();
        HttpURLConnection connection = remoteUtilities.openConnection(endpoint);

        if(connection != null)
        {
            if(remoteUtilities.connectionStatus(connection))
            {
                String response = remoteUtilities.getResponseString(connection);
                connection.disconnect();

                try
                {
                    Thread.sleep(3000);
                }
                catch (InterruptedException e) {}

                viewModel.setResponse(response);
            }
        }
    }

    private String getSearchEndpoint()
    {
        Uri.Builder url = Uri.parse(this.baseUrl).buildUpon();
        url.appendQueryParameter("key","40073976-7775ce40c284f984ed3b47131");
        url.appendQueryParameter("q",this.searchKey);
        String urlString = url.build().toString();
        return urlString;
    }
}
