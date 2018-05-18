package mohit.knowyournation.view.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.PictureDrawable;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import com.bumptech.glide.load.engine.DiskCacheStrategy;

import com.bumptech.glide.load.model.StreamEncoder;
import com.bumptech.glide.load.resource.file.FileToStreamDecoder;

import com.caverock.androidsvg.SVG;


import java.io.InputStream;
import java.util.List;
import com.bumptech.glide.GenericRequestBuilder;

import mohit.knowyournation.databinding.CardCountry2Binding;
import mohit.knowyournation.models.objects.Country;
import mohit.knowyournation.R;
import mohit.knowyournation.glide.SvgDecoder;
import mohit.knowyournation.glide.SvgDrawableTranscoder;
import mohit.knowyournation.glide.SvgSoftwareLayerSetter;
import mohit.knowyournation.databinding.CardCountryBinding;

/**
 * Created by mohit on 4/12/17.
 */

public class CountryCardAdapter extends RecyclerView.Adapter<CountryCardAdapter.CountryViewHolder>{
    private Context context;
    private LayoutInflater layoutInflater;
    private List<Country> countryList;
    private OnCountryClickListener onCountryClickListener;



    private GenericRequestBuilder<Uri, InputStream, SVG, PictureDrawable> requestBuilder;


    public CountryCardAdapter(Context context) {
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
        requestBuilder = Glide.with(context)
                .using(Glide.buildStreamModelLoader(Uri.class, context), InputStream.class)
                .from(Uri.class)
                .as(SVG.class)
                .transcode(new SvgDrawableTranscoder(), PictureDrawable.class)
                .sourceEncoder(new StreamEncoder())
                .cacheDecoder(new FileToStreamDecoder<SVG>(new SvgDecoder()))
                .decoder(new SvgDecoder())
                .placeholder(R.drawable.earth_color)
                .error(R.drawable.earth_color)
                .animate(android.R.anim.fade_in)
                .listener(new SvgSoftwareLayerSetter<Uri>());

    }

    public void setOnCountryClickListener(OnCountryClickListener onCountryClickListener) {
        this.onCountryClickListener = onCountryClickListener;
    }

    public List<Country> getCountryList() {
        return countryList;
    }

    public void setCountryList(List<Country> countryList) {
        this.countryList = countryList;
    }

    @Override
    public CountryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CountryViewHolder(layoutInflater.inflate(R.layout.card_country_2,parent, false));
    }

    @SuppressLint("CheckResult")
    @Override
    public void onBindViewHolder(final CountryViewHolder holder, int position) {
        Country country = countryList.get(position);
        holder.country = country;
        requestBuilder
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .load(Uri.parse(country.getFlag()))
                .into(holder.cardCountryBinding.ivFlag);

        holder.cardCountryBinding.tvName.setText(country.getName());
        holder.cardCountryBinding.tvCapital.setText(country.getCapital());

    }

    @Override
    public int getItemCount() {
        if(countryList != null){
            return countryList.size();
        }
        return 0;
    }

    public class CountryViewHolder extends RecyclerView.ViewHolder {
        public CardCountry2Binding cardCountryBinding;
        public Country country;
        CountryViewHolder(View itemView) {
            super(itemView);
            cardCountryBinding = DataBindingUtil.bind(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(onCountryClickListener != null){
                        int position = getAdapterPosition();
                        onCountryClickListener.onClick(countryList.get(position));
                    }
                }
            });
        }
    }

    public interface OnCountryClickListener{
        void onClick(Country country);
    }
}
