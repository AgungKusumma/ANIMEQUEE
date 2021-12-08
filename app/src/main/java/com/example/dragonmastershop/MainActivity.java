package com.example.dragonmastershop;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.dragonmastershop.adapter.WisataAdapter;
import com.example.dragonmastershop.api.Api;
import com.example.dragonmastershop.model.ModelWisata;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements WisataAdapter.onSelectData{

    RecyclerView rvWisata;
    WisataAdapter wisataAdapter;
    ProgressDialog progressDialog;
    List<ModelWisata> modelWisata = new ArrayList<>();
    Toolbar tbWisata;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tbWisata = findViewById(R.id.toolbar_wisata);
        tbWisata.setTitle("Daftar Anime Populer");

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Mohon tunggu");
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Sedang menmapilkan data...");

        rvWisata = findViewById(R.id.rvWisata);
        rvWisata.setHasFixedSize(true);
        rvWisata.setLayoutManager(new LinearLayoutManager(this));

        getWisata();
    }

    private void getWisata() {
        progressDialog.show();
        AndroidNetworking.get(Api.Wisata)
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener(){
                    @Override
                    public void onResponse(JSONObject response){
                        try{
                            progressDialog.dismiss();
                            JSONArray playerArray = response.getJSONArray("data");
                            for (int i = 0; i < playerArray.length(); i++){
                                JSONObject temp = playerArray.getJSONObject(i);
                                JSONObject attr = temp.getJSONObject("attributes");
                                JSONObject img = attr.getJSONObject("posterImage");

                                ModelWisata dataApi = new ModelWisata();
                                dataApi.setIdWisata(temp.getString("id"));
                                dataApi.setTxtNamaWisata(attr.getString("canonicalTitle"));
                                dataApi.setGambarWisata(img.getString("original"));
                                dataApi.setKategoriWisata(attr.getString("averageRating"));
                                modelWisata.add(dataApi);
                                showWisata();
                            }
                        } catch (JSONException e){
                            e.printStackTrace();
                            Toast.makeText(MainActivity.this,
                                    "Gagal Menampilkan data!", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(ANError anError){
                        progressDialog.dismiss();
                        Toast.makeText(MainActivity.this,
                                "Tidak ada jaringan internet!", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void showWisata(){
        wisataAdapter = new WisataAdapter(MainActivity.this, modelWisata, this);
        rvWisata.setAdapter(wisataAdapter);
    }

    @Override
    public void onSelected(ModelWisata modelWisata){
        Intent intent = new Intent(MainActivity.this, DetailWisata.class);
        intent.putExtra("detailWisata", modelWisata);
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if (item.getItemId() == android.R.id.home){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}