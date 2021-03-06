package com.example.dragonmastershop;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.dragonmastershop.api.Api;
import com.example.dragonmastershop.model.ModelWisata;

import org.json.JSONException;
import org.json.JSONObject;

public class DetailWisata extends AppCompatActivity {

    Toolbar tbDetailWisata;
    TextView tvNamaWisata, tvDescWisata, tvageRating;
    ImageView imgWisata;
    String idWisata, NamaWisata, Desc, ageRating;
    ModelWisata modelWisata;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_wisata);

        tbDetailWisata = findViewById(R.id.tbDetailWisata);
        tbDetailWisata.setTitle("Detail Wisata");

        setSupportActionBar(tbDetailWisata);
        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        modelWisata = (ModelWisata) getIntent().getSerializableExtra("detailWisata");
        if (modelWisata != null){
            idWisata = modelWisata.getIdWisata();
            NamaWisata = modelWisata.getTxtNamaWisata();

            //set id
            imgWisata = findViewById(R.id.imgWisata);
            tvNamaWisata = findViewById(R.id.tvNamaWisata);
            tvageRating = findViewById(R.id.tvageRating);
            tvDescWisata = findViewById(R.id.tvDescWisata);

            //get img
            Glide.with(this)
                    .load(modelWisata.getGambarWisata())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(imgWisata);

            getDetailWisata();
        }
    }

    private void getDetailWisata() {
        AndroidNetworking.get(Api.DetailWisata)
                .addPathParameter("id", idWisata)
                .setPriority(Priority.HIGH)
                .setPriority(com.androidnetworking.common.Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener(){
                    @Override
                    public void onResponse(JSONObject response){
                        try {
                            JSONObject okee = response.getJSONObject("data");
                            JSONObject attr = okee.getJSONObject("attributes");
                            JSONObject title = attr.getJSONObject("titles");

                            NamaWisata = title.getString("en_jp");
                            ageRating = attr.getString("ageRatingGuide");
                            Desc = attr.getString("synopsis");

                            //set Text
                            tvNamaWisata.setText(NamaWisata);
                            tvageRating.setText(ageRating);
                            tvDescWisata.setText(Desc);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(DetailWisata.this,
                                    "Gagal menampilkan data !", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(ANError anError){
                        Toast.makeText(DetailWisata.this,
                                "Tidak ada jaringan internet!", Toast.LENGTH_SHORT).show();
                    }
                });
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