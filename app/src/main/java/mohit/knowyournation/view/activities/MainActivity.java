package mohit.knowyournation.view.activities;

import android.content.ClipData;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.drm.DrmStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;


import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import io.realm.Realm;
import mohit.knowyournation.models.objects.Country;
import mohit.knowyournation.support.GlobalConstant;
import mohit.knowyournation.support.Helper;
import mohit.knowyournation.R;
import mohit.knowyournation.support.Utils;
import mohit.knowyournation.databinding.ActivityMainBinding;
import mohit.knowyournation.presenter.ViewModel;
import mohit.knowyournation.view.Custom.RecyclerViewItemTouch;
import mohit.knowyournation.view.adapter.CountryCardAdapter;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding activityMainBinding;
    private CountryCardAdapter countryCardAdapter;
    private LinearLayoutManager linearLayoutManager;
    private ViewModel viewModel;
    private List<Country> allCountries;
    private String[] searchKeys;
    private float dY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = DataBindingUtil.setContentView(MainActivity.this, R.layout.activity_main);
        Realm.init(MainActivity.this);
        setSupportActionBar(activityMainBinding.toolbar);


        initialiseRecyclerView();


        Utils.startProgressBar(MainActivity.this, "Please wait");

        viewModel = new ViewModel(MainActivity.this);
        viewModel.getAllCountries(new ViewModel.OnCountryLoaded() {
            @Override
            public void onResult(List<Country> countryList) {
                allCountries = countryList;
            }

            @Override
            public void onProgress(int progress) {
                Utils.startProgressBar(MainActivity.this, "Loading: " + String.valueOf(progress) + "%");
            }

            @Override
            public void onPostExecute() {
                setSearchKeys(allCountries);
                Utils.dismissProgressBar();
                updateCountryList(allCountries);
            }

        });

        initialiseSearch();


    }

    private void setSearchKeys(List<Country> countryList) {
        HashSet<String> searchSet = new HashSet<>();

        for(int i = 0; i < countryList.size(); i++){
            searchSet.add(countryList.get(i).getCapital());
            searchSet.add(countryList.get(i).getName());
            searchSet.add(countryList.get(i).getRegion());
        }
        searchKeys = new String[searchSet.size()];
        Iterator<String> it = searchSet.iterator();
        int ind = 0;
        while (it.hasNext()) {
            searchKeys[ind] = it.next();
            ind += 1;
        }
        updateAutoSearchAdapter();
    }

    private void updateAutoSearchAdapter() {
        activityMainBinding.acSearchCountry.setAdapter(new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_expandable_list_item_1, searchKeys));
    }

    private void initialiseSearch() {
        activityMainBinding.acSearchCountry.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().isEmpty()){
                    List<Country> countryList = viewModel.findCountry(String.valueOf(s));
                    updateCountryList(countryList);
                }else{
                    updateCountryList(allCountries);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                Helper.log(s.toString());
            }
        });
        activityMainBinding.acSearchCountry.setThreshold(1);
        activityMainBinding.ibClearSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activityMainBinding.acSearchCountry.setText("");
            }
        });
    }

    private void updateCountryList(List<Country> countryList){
        countryCardAdapter.setCountryList(countryList);
        countryCardAdapter.notifyDataSetChanged();
    }

    private void initialiseRecyclerView() {
        countryCardAdapter = new CountryCardAdapter(MainActivity.this);
        linearLayoutManager = new LinearLayoutManager(MainActivity.this);
        activityMainBinding.rvCountry.setAdapter(countryCardAdapter);
        activityMainBinding.rvCountry.setLayoutManager(linearLayoutManager);
        countryCardAdapter.setOnCountryClickListener(new CountryCardAdapter.OnCountryClickListener() {
            @Override
            public void onClick(Country country) {
                GlobalConstant.country = country;
                startActivity(new Intent(MainActivity.this, CountryDetailActivity.class));
            }
        });
        RecyclerViewItemTouch recyclerViewItemTouch = new RecyclerViewItemTouch(0,ItemTouchHelper.LEFT, MainActivity.this);
        recyclerViewItemTouch.setItemTouchListener(new RecyclerViewItemTouch.OnItemTouchListener() {
            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int dir) {
                countryCardAdapter.notifyItemChanged(viewHolder.getAdapterPosition());
                GlobalConstant.country = ((CountryCardAdapter.CountryViewHolder)viewHolder).country;
                startActivity(new Intent(MainActivity.this, CountryDetailActivity.class));
            }
        });
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(recyclerViewItemTouch);
        itemTouchHelper.attachToRecyclerView(activityMainBinding.rvCountry);

    }

}
