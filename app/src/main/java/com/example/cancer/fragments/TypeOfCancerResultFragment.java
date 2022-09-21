package com.example.cancer.fragments;

import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.cancer.R;
import com.example.cancer.client.cancer.CancerClient;
import com.example.cancer.client.cancer.CancerInterface;
import com.example.cancer.databinding.FragmentTypeOfCancerResultBinding;
import com.example.cancer.models.cancer.Cancer;

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

public class TypeOfCancerResultFragment extends Fragment {

    FragmentTypeOfCancerResultBinding binding;

    private Retrofit retrofit;
    private CancerInterface ci;

    private Uri selectedImage;
    private int n;
    private String message;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentTypeOfCancerResultBinding.inflate(inflater, container, false);

        binding.btnProfile.setOnClickListener(view -> {
            getFragmentManager().beginTransaction().add(R.id.MA, new AccountFragment()).commit();
        });

        binding.btnEdit.setOnClickListener(view -> {
            getFragmentManager().beginTransaction().add(R.id.MA, new CreatingRecordFragment()).commit();
        });

        binding.btnScroll.setOnClickListener(view -> {
            getFragmentManager().beginTransaction().add(R.id.MA, new MyRecordsFragment()).commit();
        });

        binding.btnNews.setOnClickListener(view -> {
            getFragmentManager().beginTransaction().add(R.id.MA, new NewsFragment()).commit();
        });

        binding.tvType.setText(getArguments().getString("type_cancer"));

        retrofit = CancerClient.getClient();
        ci = retrofit.create(CancerInterface.class);

        selectedImage = Uri.parse(getArguments().getString("selectImage"));
        binding.imvDiagnosis.setImageURI(selectedImage);
        n = getArguments().getInt("type_cancer_number");

        try {
            doRequest(selectedImage, n);
            ProgressTask progressTask = new ProgressTask();
            progressTask.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return binding.getRoot();
    }

    private void doRequest(Uri fileUri, int n) {

        File file = new File(createCopyAndReturnRealPath(getActivity(), fileUri));

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
                    message = getString(R.string.ex_internet);
                }
            }

            @Override
            public void onFailure(Call<Cancer> call, Throwable t) {
                getActivity().runOnUiThread(() -> {
                    message = getString(R.string.ex_internet);
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

    class ProgressTask extends AsyncTask<Void, Integer, Void> {
        @Override
        protected void onPreExecute() {
            binding.cardView.setVisibility(View.VISIBLE);
        }

        @Override
        protected Void doInBackground(Void... unused) {
            for (int i = 0; i<100;i++) {

                publishProgress(i);
                SystemClock.sleep(100);

                if (message != null) {
                    break;
                }
            }
            return(null);
        }
        @Override
        protected void onProgressUpdate(Integer... items) {
            binding.progressCircular.setProgress(items[0]+1);
        }
        @Override
        protected void onPostExecute(Void unused) {
            binding.cardView.setVisibility(View.INVISIBLE);

            if (message != null) {
                Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
            }
        }
    }
}