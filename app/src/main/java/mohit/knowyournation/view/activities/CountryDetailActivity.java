package mohit.knowyournation.view.activities;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.PictureDrawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.bumptech.glide.GenericRequestBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.model.StreamEncoder;
import com.bumptech.glide.load.resource.file.FileToStreamDecoder;
import com.caverock.androidsvg.SVG;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.InputStream;
import java.util.List;

import io.realm.RealmList;
import mohit.knowyournation.models.objects.Country;
import mohit.knowyournation.models.objects.Currency;
import mohit.knowyournation.support.GlobalConstant;
import mohit.knowyournation.models.objects.Language;
import mohit.knowyournation.R;
import mohit.knowyournation.glide.SvgDecoder;
import mohit.knowyournation.glide.SvgDrawableTranscoder;
import mohit.knowyournation.glide.SvgSoftwareLayerSetter;
import mohit.knowyournation.databinding.ActivityCountryDetailBinding;

public class CountryDetailActivity extends AppCompatActivity {

    private MapView mapView;
    private GoogleMap googleMap;
    private ActivityCountryDetailBinding countryDetailBinding;
    private ActionBar actionBar;
    private Country country;
    private GenericRequestBuilder<Uri, InputStream, SVG, PictureDrawable> requestBuilder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        countryDetailBinding = DataBindingUtil.setContentView(CountryDetailActivity.this, R.layout.activity_country_detail);


        if(GlobalConstant.country == null){
            startActivity(new Intent(CountryDetailActivity.this, MainActivity.class));
        }
        country = GlobalConstant.country;

        initActionbar();


        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                mapReady(googleMap);
            }
        });
        initGlide();
        setnames();
        setCurrency();

    }

    private void initGlide() {
        requestBuilder = Glide.with(CountryDetailActivity.this)
                .using(Glide.buildStreamModelLoader(Uri.class, CountryDetailActivity.this), InputStream.class)
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

    private void setCurrency() {
        RealmList<Currency> currencyList = country.getListCurrencies();
        RealmList<Language> languageList = country.getListLanguages();
        if(currencyList != null) {
            //Helper.log("Currencycount: " + String.valueOf());
            StringBuilder currencies = new StringBuilder(currencyList.get(0).toString());
            for (int i = 1; i < currencyList.size(); i++) {
                currencies.append(", " + currencyList.get(i).toString());
            }
            countryDetailBinding.tvCurrency.setText(currencies.toString());
        }
        if(languageList != null) {
            StringBuilder language = new StringBuilder(languageList.get(0).toString());
            for (int i = 1; i < languageList.size(); i++) {
                language.append(", " + languageList.get(i).toString());
            }
            countryDetailBinding.tvLanguages.setText(language.toString());
        }



    }

    private void setnames() {

        if(country.getListAltSpellings().size() > 0) {
            countryDetailBinding.tvName.setText(country.getName() + ", " + listToString(country.getListAltSpellings()));
        }else{
            countryDetailBinding.tvName.setText(country.getName());
        }

        countryDetailBinding.tvPopulation.setText(String.valueOf(country.getPopulation()));
        countryDetailBinding.tvCapital.setText(country.getCapital());

        if(!country.getSubregion().isEmpty()){
            countryDetailBinding.tvRegion.setText(country.getRegion() + "(" + country.getSubregion() + ")");
        }else{
            countryDetailBinding.tvRegion.setText(country.getRegion());
        }
        countryDetailBinding.tvTimeZone.setText(listToString(country.getListTimeZones()));

        countryDetailBinding.tvArea.setText(String.valueOf(country.getArea()) + " sq. mt");
        countryDetailBinding.tvCallingCodes.setText("+" + listToString(country.getListCallingCodes()));
        requestBuilder
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .load(Uri.parse(country.getFlag()))
                .into(countryDetailBinding.ivFlag);

    }

    @NonNull
    private String listToString(List<String> stringList) {
        if(stringList != null) {
            if (stringList.size() > 0) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(stringList.get(0));
                for (int i = 1; i < stringList.size(); i++) {
                    stringBuilder.append(", " + stringList.get(i));
                }
                return stringBuilder.toString();
            } else {
                return "";
            }
        }else{
            return "";
        }
    }

    private void initActionbar() {
        setSupportActionBar(countryDetailBinding.toolbar);
        actionBar = getSupportActionBar();
        actionBar.setTitle(country.getName());
    }

    private void mapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;

        RealmList<Double> latLngRealmList = country.getListLatLng();
        if(latLngRealmList.size() == 2) {
            LatLng latlng = new LatLng(country.getListLatLng().get(0), country.getListLatLng().get(1));

            googleMap.addMarker(new MarkerOptions().position(latlng)
                    .title(country.getName()));
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latlng, 3.0f));
        }
    }
}
