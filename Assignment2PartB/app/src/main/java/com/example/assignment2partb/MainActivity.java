package com.example.assignment2partb;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnAdapterClick
{
    ArrayList<Bitmap> images;
    SearchResponseViewModel sViewModel;
    ImageViewModel imageViewModel;
    List<ImageViewModel> imageViewModels;
    ErrorViewModel errorViewModel;
    CompoundButton colSwitch;

    Button searchButton;
    RecyclerView picture;
    ProgressBar progressBar;
    EditText searchBar;
    int numHits;


    String currSearch;
    int colNum;

    FirebaseStorage storage;
    StorageReference storageReference;


    public MainActivity()
    {
        images = new ArrayList<>();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize the view models
        sViewModel = new ViewModelProvider(this, (ViewModelProvider.Factory) new ViewModelProvider.NewInstanceFactory()).get(SearchResponseViewModel.class);

        imageViewModels = new ArrayList<>();
        for(int i = 0; i < 15; i++)
        {
            // We retrieve a max of 15 images
            imageViewModel = new ViewModelProvider(this, (ViewModelProvider.Factory) new ViewModelProvider.NewInstanceFactory()).get(ImageViewModel.class);
            imageViewModels.add(imageViewModel);
        }

        errorViewModel = new ViewModelProvider(this, (ViewModelProvider.Factory) new ViewModelProvider.NewInstanceFactory()).get(ErrorViewModel.class);

        searchButton = findViewById(R.id.search);
        searchBar = findViewById(R.id.search_bar);
        progressBar = findViewById(R.id.progressBarId);
        colSwitch = findViewById(R.id.columnSwitch);

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReferenceFromUrl("gs://assignment2partb-5edd9.appspot.com");

        colNum = 1;
        GridLayoutManager glm = new GridLayoutManager(this, colNum);
        picture = findViewById(R.id.imageList);
        picture.setLayoutManager(glm);
        ImageDataAdapter adapter = new ImageDataAdapter(images, this);

        picture.setAdapter(adapter);

        picture.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.INVISIBLE);

        // Button to search for the image
        searchButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                // This should reset the list of images.
                images.clear();

                picture.setVisibility(View.INVISIBLE);
                currSearch = searchBar.getText().toString();
                String searchValues = currSearch;


                // Create the search thread to search for the image
                APISearchThread searchThread = new APISearchThread(searchValues,MainActivity.this,sViewModel);
                progressBar.setVisibility(View.VISIBLE);
                searchThread.start();
            }
        });

        // Observer to start when the search is finished
        sViewModel.response.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                progressBar.setVisibility(View.INVISIBLE);

                // Start at thread to retrieve the image.
                // This will store the information in the image view model and the error view model
                ImageRetrievalThread imageRetrievalThread = new ImageRetrievalThread(MainActivity.this, sViewModel, imageViewModels, errorViewModel);
                numHits = imageRetrievalThread.numHits == 0 ? 0 : imageRetrievalThread.numHits - 1;

                progressBar.setVisibility(View.VISIBLE);
                Toast.makeText(MainActivity.this, "Search Complete",Toast.LENGTH_LONG).show();
                imageRetrievalThread.start();
            }
        });

        colSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked)
            {
                glm.setSpanCount(2);
            }
            else {
                glm.setSpanCount(1);
            }
        });

        // Observer to start when an image is retrieved.
        imageViewModels.get(numHits).image.observe(this, new Observer<Bitmap>()
        {
            @Override
            public void onChanged(Bitmap bitmap)
            {
                Log.d("OBSERVER","CALLED");

                progressBar.setVisibility(View.INVISIBLE);
                picture.setVisibility(View.VISIBLE);

                runOnUiThread(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        // Adding to the list of images
                        Log.d("Image Bitmap", "" + imageViewModel.getImage());
                        images.add(bitmap);
                        adapter.notifyDataSetChanged();
                    }
                });

                // Log.d("Image from list", "" + images.get(images.size() == 0 ? 0 : images.size() - 1));
                // adapter.notifyDataSetChanged();
                // Log.d("Image from list", "" + images.get(images.size() == 0 ? 0 : images.size() - 1));
            }
        });

        // An observer for the error view model as well if an error occurs
        errorViewModel.errorCode.observe(this, new Observer<Integer>()
        {
            @Override
            public void onChanged(Integer integer)
            {
                progressBar.setVisibility(View.INVISIBLE);
            }
        });
    }

    public byte[] toByteArray(Bitmap image)
    {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    //Listener code to take the bitmap selected and upload it to firebase
    //Adapted from :https://firebase.google.com/docs/storage/android/upload-files#upload_from_data_in_memory
    @Override
    public void onAdapterClick(String label, Bitmap bitmap) {
        StorageReference imageRef = storageReference.child("images/"+currSearch+ label);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,baos);
        byte[] bytes = baos.toByteArray();

        UploadTask uploadTask = imageRef.putBytes(bytes);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("Upload Failed", e.toString());
                Toast.makeText(MainActivity.this, "Failed to upload image.",Toast.LENGTH_LONG).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Log.d("Upload success!", "Upload success");
                Toast.makeText(MainActivity.this, "Upload successful.",Toast.LENGTH_LONG).show();
            }
        });
    }
}