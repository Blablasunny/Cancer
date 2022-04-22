package com.example.cancer.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cancer.client.Cancer1;
import com.example.cancer.client.Cancer1Client;
import com.example.cancer.client.Cancer1Interface;
import com.example.cancer.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class TypeOfCancer3ResultActivity extends AppCompatActivity {

    ImageView imv;
    TextView txt;
    private Retrofit retrofit;
    private Cancer1Interface ci;
    InputStream is;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_type_of_cancer_3_result);
        Button button1 = (Button) findViewById(R.id.group_history);
        imv = (ImageView) findViewById(R.id.img1);
        txt = (TextView) findViewById(R.id.txt2);

        retrofit = Cancer1Client.getClient();
        ci = retrofit.create(Cancer1Interface.class);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(TypeOfCancer3ResultActivity.this, TypeOfCancer3Activity.class);
                startActivity(i);
            }
        });

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            String str = bundle.getString("selectImage");
            if (str != null) {
                if (!str.equals("")) {
                    Uri selectedImage = Uri.parse(str);
                    imv.setImageURI(selectedImage);
                    try {
                        doRequest(selectedImage);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }else{
            Toast.makeText(TypeOfCancer3ResultActivity.this, "Произошла ошибка", Toast.LENGTH_SHORT).show();
        }
    }
    private void doRequest(Uri fileUri) {

        File file = new File(createCopyAndReturnRealPath(this, fileUri));

        MultipartBody.Part body = MultipartBody.Part.createFormData("file", file.getName(), RequestBody.create(MediaType.parse("image/*"), file));

        Call<Cancer1> call = ci.getResCancer3(body);
        call.enqueue(new Callback<Cancer1>() {
            @Override
            public void onResponse(@NonNull Call<Cancer1> call, @NonNull Response<Cancer1> response) {
                if (response.isSuccessful()) {
                    Cancer1 body = response.body();
                    txt.setText(body.getResult());
                } else {
                    Toast.makeText(TypeOfCancer3ResultActivity.this, "Произошла ошибка", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Cancer1> call, Throwable t) {
                runOnUiThread(() -> {
                    Toast.makeText(TypeOfCancer3ResultActivity.this, "Произошла ошибка", Toast.LENGTH_SHORT).show();
                    t.printStackTrace();
                });
            }
        });
    }
    @Nullable
    public static String createCopyAndReturnRealPath(
            @NonNull Context context, @NonNull Uri uri) {
        final ContentResolver contentResolver = context.getContentResolver();
        if (contentResolver == null)
            return null;

        String filePath = context.getApplicationInfo().dataDir + File.separator + "temp_file";
        File file = new File(filePath);
        try {
            InputStream inputStream = contentResolver.openInputStream(uri);
            if (inputStream == null)
                return null;
            OutputStream outputStream = new FileOutputStream(file);
            byte[] buf = new byte[1024];
            int len;
            while ((len = inputStream.read(buf)) > 0)
                outputStream.write(buf, 0, len);
            outputStream.close();
            inputStream.close();
        } catch (IOException ignore) {
            return null;
        }
        return file.getAbsolutePath();
    }
}