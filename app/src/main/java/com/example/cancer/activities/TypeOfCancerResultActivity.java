package com.example.cancer.activities;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.cancer.client.Cancer;
import com.example.cancer.client.CancerClient;
import com.example.cancer.client.CancerInterface;
import com.example.cancer.databinding.ActivityTypeOfCancerResultBinding;

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

public class TypeOfCancerResultActivity extends AppCompatActivity {

    ActivityTypeOfCancerResultBinding binding;

    private Retrofit retrofit;
    private CancerInterface ci;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityTypeOfCancerResultBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        binding.btnProfile.setOnClickListener(view -> {
            Intent i = new Intent(TypeOfCancerResultActivity.this, AccountActivity.class);
            startActivity(i);
        });

        binding.btnEdit.setOnClickListener(view -> {
            Intent i = new Intent(TypeOfCancerResultActivity.this, CreatingRecordActivity.class);
            startActivity(i);
        });

        binding.btnScroll.setOnClickListener(view -> {
            Intent i = new Intent(TypeOfCancerResultActivity.this, MyRecordsActivity.class);
            startActivity(i);
        });

        binding.btnNews.setOnClickListener(view -> {
            Intent i = new Intent(TypeOfCancerResultActivity.this, NewsActivity.class);
            startActivity(i);
        });

        retrofit = CancerClient.getClient();
        ci = retrofit.create(CancerInterface.class);


        Bundle bundle = getIntent().getExtras();
        int n = bundle.getInt("type_cancer");
        String str = bundle.getString("selectImage");

        if (!str.equals("")) {
            Uri selectedImage = Uri.parse(str);
            binding.imvDiagnosis.setImageURI(selectedImage);
            if (n == 1) {
                binding.tvType.setText("Рак легких по КТ");
            } else if (n == 2) {
                binding.tvType.setText("Рак кожи");
            } else if (n == 3) {
                binding.tvType.setText("Рак молочной железы по биопсии");
            } else if (n == 4) {
                binding.tvType.setText("Рак молочной железы по МРТ");
            } else if (n == 5) {
                binding.tvType.setText("Рак толстой кишки по биопсии");
            } else {
                binding.tvType.setText("Рак полости рта по биопсии");
            }
            try {
                doRequest(selectedImage, n);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(TypeOfCancerResultActivity.this, "Произошла ошибка", Toast.LENGTH_SHORT).show();
        }
    }

    private void doRequest(Uri fileUri, int n) {

        File file = new File(createCopyAndReturnRealPath(this, fileUri));

        MultipartBody.Part body = MultipartBody.Part.createFormData("file", file.getName(), RequestBody.create(MediaType.parse("image/*"), file));

        Call<Cancer> call;

        if (n == 1) {
            call = ci.getResCancer1(body);
        } else if (n == 2) {
            call = ci.getResCancer2(body);
        } else if (n == 3) {
            call = ci.getResCancer3(body);
        } else if (n == 4) {
            call = ci.getResCancer4(body);
        } else if (n == 5) {
            call = ci.getResCancer5(body);
        } else {
            call = ci.getResCancer6(body);
        }

        call.enqueue(new Callback<Cancer>() {
            @Override
            public void onResponse(@NonNull Call<Cancer> call, @NonNull Response<Cancer> response) {
                if (response.isSuccessful()) {
                    Cancer body = response.body();
                    binding.tvResult.setText(body.getResult());
                } else {
                    Toast.makeText(TypeOfCancerResultActivity.this, "Произошла ошибка", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Cancer> call, Throwable t) {
                runOnUiThread(() -> {
                    Toast.makeText(TypeOfCancerResultActivity.this, "Произошла ошибка", Toast.LENGTH_SHORT).show();
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
