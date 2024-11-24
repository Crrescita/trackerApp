package com.crrescita.employeetracker.activity.profile_menu;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.crrescita.tel.BuildConfig;
import com.crrescita.tel.R;
import com.bumptech.glide.Glide;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.securepreferences.SecurePreferences;

import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import modelResponse.ModelError;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Converter;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import retrofitAPI.WebRequest;
import utils.AppConstant;
import utils.SingletonHelperGlobal;
import utils.Utility;

public class SupportUsActivity extends AppCompatActivity  {

    private MaterialButton proceedButton;
    private SecurePreferences prefsMain;
    private SecurePreferences.Editor prefsEditor;
    private ProgressDialog progressBar;

    private ImageView imageViewBackButton;


    private TextInputEditText emailAddressEditText;
    private TextInputEditText messageEditText;
    private ImageView imageViewUploadDocuments;
    private ImageView imageViewHeader;
    private TextView textViewFileName;
    private TextView textViewVersionNumber;

    private ImageView imageViewUploadProfile;
    private Uri imageUri;

    private static final int REQUEST_GALLERY = 1;
    private static final int REQUEST_CAMERA = 2;
    private static final int REQUEST_PERMISSION = 3;

    private  File compressedImageFile  = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_support_us);


        progressBar = new ProgressDialog(this);
        prefsMain = SingletonHelperGlobal.getInstance().mySharedPreferenceHelper;
        prefsEditor = prefsMain.edit();

        imageViewBackButton = findViewById(R.id.imageViewBackButton);
        imageViewHeader = findViewById(R.id.imageViewHeader);
        emailAddressEditText = findViewById(R.id.emailAddressEditText);
        emailAddressEditText.setText(prefsMain.getString(AppConstant.EMPLOYEE_EMAIL_ID, "-----NA----"));
        messageEditText = findViewById(R.id.messageEditText);
        textViewVersionNumber = findViewById(R.id.textViewVersionNumber);

        imageViewUploadDocuments = findViewById(R.id.imageViewUploadDocuments);
        imageViewUploadDocuments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkAndRequestPermissions()) {
                    showImagePickerDialog();
                }
            }
        });


        textViewFileName = findViewById(R.id.textViewFileName);
        textViewFileName.setVisibility(View.GONE);

        textViewVersionNumber.setText("App version - "+BuildConfig.VERSION_NAME);

        imageViewBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        proceedButton = findViewById(R.id.proceedButton);
        proceedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(messageEditText.getText().toString().length()==0){
                    Toast.makeText(SupportUsActivity.this, "Please enter message.", Toast.LENGTH_SHORT).show();
                }else {
                    supportUs(messageEditText.getText().toString(),compressedImageFile);
                }

            }
        });



        Glide.with(SupportUsActivity.this)
                .load("https://crrescita.s3.ap-south-1.amazonaws.com/crrescitalogo/2.png")
                .into(imageViewHeader);


    }


    private boolean checkAndRequestPermissions() {
        List<String> listPermissionsNeeded = new ArrayList<>();
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(android.Manifest.permission.CAMERA);
        }
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P && ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(android.Manifest.permission.READ_EXTERNAL_STORAGE);
        }
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P &&
                ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
                ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_MEDIA_IMAGES) != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(android.Manifest.permission.READ_MEDIA_IMAGES);
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[0]), REQUEST_PERMISSION);
            return false;
        }
        return true;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSION) {
            Map<String, Integer> perms = new HashMap<>();
            for (int i = 0; i < permissions.length; i++) {
                perms.put(permissions[i], grantResults[i]);
            }
            boolean allPermissionsGranted = true;
            for (String permission : permissions) {
                if (perms.get(permission) != PackageManager.PERMISSION_GRANTED) {
                    allPermissionsGranted = false;
                    break;
                }
            }
            if (allPermissionsGranted) {
                showImagePickerDialog();
            } else {
                // Handle permission denial
            }
        }
    }

    private void showImagePickerDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose Image From")
                .setItems(new CharSequence[]{"Gallery", "Camera"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 0) {
                            openGallery();
                        } else {
                            openCamera();
                        }
                    }
                })
                .show();
    }


    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(intent, REQUEST_GALLERY);
    }

    private void openCamera() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (cameraIntent.resolveActivity(getPackageManager()) != null) {
            File photoFile = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), "camera_image.jpg");
            imageUri = FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID + ".fileprovider", photoFile);
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            startActivityForResult(cameraIntent, REQUEST_CAMERA);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_GALLERY && data != null) {
                Uri selectedImageUri = data.getData();
                // Handle the selected image from the gallery
                handleImage(selectedImageUri);
            } else if (requestCode == REQUEST_CAMERA) {
                // Handle the captured image from the camera
                if (imageUri != null) {
                    handleImage(imageUri);
                }
            }
        }
    }

    private void handleImage(Uri imageUri) {
        try {
            // Convert the URI to a Bitmap
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);

            // Compress the Bitmap to reduce its file size
            compressedImageFile = compressImage(bitmap);
            Log.e("comress","compressed");

            textViewFileName.setVisibility(View.VISIBLE);
            textViewFileName.setText("File Attached!");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private File compressImage(Bitmap bitmap) throws IOException {
        // Create a file for the compressed image
        File compressedImageFile = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), "compressed_image_main.jpg");

        // Initial quality for compression
        int quality = 80;
        boolean compressed = false;

        while (!compressed) {
            // Compress the bitmap and save it to the file
            try (FileOutputStream fos = new FileOutputStream(compressedImageFile)) {
                bitmap.compress(Bitmap.CompressFormat.JPEG, quality, fos);
                fos.flush();
            }

            // Check if the file size is less than 1 MB
            if (compressedImageFile.length() <= 1 * 1024 * 1024) {
                compressed = true; // Compression is done
            } else {
                quality -= 10; // Reduce quality by 10%
                if (quality < 0) {
                    quality = 0; // Ensure quality does not go below 0
                    compressed = true; // Exit loop if quality reaches 0
                }
            }
        }

        return compressedImageFile;
    }


    public void supportUs(String message, File imageFile) {
        progressBar.setMessage("Please wait...");
        progressBar.setCancelable(false);
        progressBar.setCanceledOnTouchOutside(false);
        progressBar.show();
        // Create RequestBody instances
        RequestBody messageBody = RequestBody.create(MediaType.parse("text/plain"),message);

        MultipartBody.Part imagePart = null;
        if (imageFile != null) {
            // Create MultipartBody.Part for the image if it is not null
            RequestBody imageBody = RequestBody.create(MediaType.parse("image/jpeg"),imageFile); // Change media type if necessary
            imagePart = MultipartBody.Part.createFormData("image", imageFile.getName(), imageBody);
        }


        String token = prefsMain.getString(AppConstant.API_TOKEN, "");
        WebRequest mWebRequest = new WebRequest(this);
        Call<ResponseBody> user1 = mWebRequest.m_ApiInterface.supportUs(
                "Bearer " + token,messageBody, imagePart);
        user1.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.errorBody() != null) {
                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl(BuildConfig.BASE_URL)
                            .addConverterFactory(ScalarsConverterFactory.create())
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();
                    Converter<ResponseBody, ModelError> converter = retrofit.responseBodyConverter(ModelError.class, new Annotation[0]);

                    try {
                        ModelError error = converter.convert(response.errorBody());
                        progressBar.dismiss();
                        Utility.getInstance().handleApiError(error.getMsg(), SupportUsActivity.this,prefsEditor);
                    } catch (IOException e) {
                        //This is Catch Block
                        progressBar.dismiss();
                    }
                } else {
                    progressBar.dismiss();
                    try {
                        String responseBodyString = response.body().string();
                        JSONObject responseJSON = new JSONObject(responseBodyString);
                        if (responseJSON.getBoolean("status")) {
                            Toast.makeText(SupportUsActivity.this, responseJSON.getString("message"), Toast.LENGTH_SHORT).show();
                            messageEditText.setText("");
                            compressedImageFile=null;
                            textViewFileName.setVisibility(View.GONE);
                        } else {
                            Utility.getInstance().handleApiError(responseJSON.getString("message"),SupportUsActivity.this,prefsEditor);
                        }
                    } catch (Exception e) {
                        Log.e("dd", "sdds");
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                progressBar.dismiss();
            }
        });
    }




    @Override
    protected void onDestroy() {
        super.onDestroy();
        Utility.getInstance().deleteCache(SupportUsActivity.this);
    }
}
