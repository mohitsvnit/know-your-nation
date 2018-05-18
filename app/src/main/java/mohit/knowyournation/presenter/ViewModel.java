package mohit.knowyournation.presenter;

import android.content.Context;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import io.realm.RealmList;
import mohit.knowyournation.models.objects.Country;
import mohit.knowyournation.models.loaders.CountryLoader;
import mohit.knowyournation.models.objects.Currency;
import mohit.knowyournation.models.db.Database;
import mohit.knowyournation.support.Helper;
import mohit.knowyournation.models.objects.Language;
import mohit.knowyournation.view.activities.MainActivity;
import mohit.knowyournation.models.objects.RegionalBloc;

/**
 * Created by mohit on 4/12/17.
 */

public class ViewModel {
    private Context context;
    private Database database;
    public ViewModel(Context context) {
        this.context = context;
        database = new Database();
    }

    private int progress;

    public void getAllCountries(final OnCountryLoaded onCountryLoaded){

        final List<Country> countryList = database.getAllCountries();
        if(countryList.size() > 0){
            Helper.log("From Database");
            for(int i = 0; i < countryList.size(); i++){
                Helper.log(countryList.get(i).toString());
            }
            onCountryLoaded.onResult(countryList);
            onCountryLoaded.onPostExecute();

        }else{
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Helper.log("Loading Countries");
                    CountryLoader countryLoader = new CountryLoader(context);
                    countryLoader.load(new CountryLoader.OnCountryLoaded() {
                        @Override
                        public void onResponse(String response) {
                            Helper.log("Got response in view model");
                            OnResponse(response, onCountryLoaded);
                        }

                        @Override
                        public void onError(String error) {

                        }
                    });
                }
            }).start();

        }


    }

    private void OnResponse(final String response, final OnCountryLoaded onCountryLoaded) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    final List<Country> countryList = new ArrayList<>();
                    final JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        Country country = new Gson().fromJson(jsonObject.toString(), Country.class);
                        setRealmAttributes(country);
                        countryList.add(country);
                        if(i%5 == 0){
                            progress = i;
                            ((MainActivity)context).runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    float tmp = progress*100/jsonArray.length();
                                    onCountryLoaded.onProgress(progress*100/jsonArray.length());
                                }
                            });
                        }
                    }
                    Helper.log("Country List created");
                    saveToDatabase(countryList);
                    onCountryLoaded.onResult(countryList);
                    ((MainActivity)context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            onCountryLoaded.onPostExecute();
                        }
                    });

                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();

    }

    private void setRealmAttributes(Country country) {
        List<Currency> currencies = country.getCurrencies();
        RealmList<Currency> currencyRealmList = new RealmList<>();
        for(int i = 0; i < currencies.size(); i++){
            currencyRealmList.add(currencies.get(i));
        }
        country.setListCurrencies(currencyRealmList);

        RealmList<Language> languageRealmList = new RealmList<>();
        languageRealmList.addAll(country.getLanguages());
        country.setListLanguages(languageRealmList);

        RealmList<RegionalBloc> regionalBlocRealmList = new RealmList<>();
        regionalBlocRealmList.addAll(country.getRegionalBlocs());
        country.setListRegionalBlocs(regionalBlocRealmList);

        RealmList<Double> doubleRealmList = new RealmList<>();
        doubleRealmList.addAll(country.getLatlng());
        country.setListLatLng(doubleRealmList);

        country.setListBorders(listToRealmList(country.getBorders()));
        country.setListTimeZones(listToRealmList(country.getTimezones()));
        country.setListAltSpellings(listToRealmList(country.getAltSpellings()));
        country.setListCallingCodes(listToRealmList(country.getCallingCodes()));
    }

    public List<Country> findCountry(String txt){
        return database.findCountryBySearch(txt);
    }

    private RealmList<String> listToRealmList(List<String> args){
        RealmList<String> realmList = new RealmList<>();
        realmList.addAll(args);
        return realmList;
    }

    private void saveToDatabase(final List<Country> countryList) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                database.saveAllCountry(countryList);
            }
        }).start();
    }

    public interface OnCountryLoaded {
        void onResult(List<Country> countryList);
        void onProgress(int progress);
        void onPostExecute();
    }

}
