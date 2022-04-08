package com.example.cancer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class TypeOfCancer1ResultActivity extends AppCompatActivity {

    ImageView imv;
    TextView txt;
    private Retrofit retrofit;
    private Cancer1Interface ci;
    InputStream is;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_type_of_cancer_1_result);
        Button button1 = (Button) findViewById(R.id.group_history);
        imv = (ImageView) findViewById(R.id.img1);
        txt = (TextView) findViewById(R.id.txt2);

        retrofit = Cancer1Client.getClient();
        ci = retrofit.create(Cancer1Interface.class);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(TypeOfCancer1ResultActivity.this, TypeOfCancer1Activity.class);
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
                        is = getContentResolver().openInputStream(selectedImage);
                        doRequest(getBytes(is));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }else{
            Toast.makeText(TypeOfCancer1ResultActivity.this, "Произошла ошибка", Toast.LENGTH_SHORT).show();
        }
    }

    public byte[] getBytes(InputStream is) throws IOException {
        ByteArrayOutputStream byteBuff = new ByteArrayOutputStream();

        int buffSize = 2048;
        byte[] buff = new byte[buffSize];

        int len = 0;
        while ((len = is.read(buff)) != -1) {
            byteBuff.write(buff, 0, len);
        }

        return byteBuff.toByteArray();
    }

    private void doRequest(byte[] imageBytes) {

        RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), imageBytes);
        MultipartBody.Part body = MultipartBody.Part.createFormData("image", "image.*", requestFile);

        Call<Cancer1> call = ci.getResCancer1(body);
        call.enqueue(new Callback<Cancer1>() {
            @Override
            public void onResponse(Call<Cancer1> call, Response<Cancer1> response) {
                Cancer1 body = response.body();
                System.out.println(body.res);
                txt.setText(body.res);
            }

            @Override
            public void onFailure(Call<Cancer1> call, Throwable t) {
                runOnUiThread(() -> {
                    Toast.makeText(TypeOfCancer1ResultActivity.this, "Произошла ошибка", Toast.LENGTH_SHORT).show();
                    t.printStackTrace();
                });
            }
        });
    }
}