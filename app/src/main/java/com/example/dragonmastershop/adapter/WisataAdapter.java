package com.example.dragonmastershop.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.dragonmastershop.R;
import com.example.dragonmastershop.model.ModelWisata;

import java.util.List;

public class WisataAdapter extends RecyclerView.Adapter<WisataAdapter.ViewHolder> {

    private List<ModelWisata> items;
    private WisataAdapter.onSelectData onSelectData;
    private Context mContext;

    public interface onSelectData{
        void onSelected(ModelWisata modelWisata);
    }

    public WisataAdapter(Context context, List<ModelWisata> items, WisataAdapter.onSelectData xSelectData){
        this.mContext = context;
        this.items = items;
        this.onSelectData = xSelectData;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final ModelWisata data = items.get(position);

        //get img
        Glide.with(mContext)
                .load(data.getGambarWisata())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.imgWisata);

        holder.tvKategori.setText(data.getKategoriWisata());
        holder.tvWisata.setText(data.getTxtNamaWisata());
        holder.cvWisata.setOnClickListener((v) -> {
            onSelectData.onSelected(data);
        });
    }

    @Override
    public int getItemCount(){
        return items.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvKategori;
        public TextView tvWisata;
        public CardView cvWisata;
        public ImageView imgWisata;

        public ViewHolder(View itemView){
            super(itemView);
            cvWisata = itemView.findViewById(R.id.cvWisata);
            tvWisata = itemView.findViewById(R.id.tvWisata);
            tvKategori = itemView.findViewById(R.id.tvKategori);
            imgWisata = itemView.findViewById(R.id.imgWisata);
        }
    }
}
