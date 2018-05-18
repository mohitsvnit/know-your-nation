package mohit.knowyournation.models.db;

import java.util.ArrayList;
import java.util.List;

import io.realm.Case;
import io.realm.Realm;
import io.realm.RealmResults;
import mohit.knowyournation.models.objects.CountryFields;
import mohit.knowyournation.models.objects.Country;

/**
 * Created by mohit on 3/12/17.
 */

public class Database {
    private Realm realm;

    public List<Country> getAllCountries(){
        List<Country> countryList = new ArrayList<>();
        Realm realm = Realm.getDefaultInstance();
        RealmResults<Country> realmResults = realm.where(Country.class).findAll();
        if(realmResults.size() > 0){
            for(int i = 0; i < realmResults.size(); i++){
                countryList.add(realmResults.get(i));
            }
        }else{
            return countryList;
        }

        return countryList;
    }

    public Country getCountryByCallingCode(String code){
        Realm realm = Realm.getDefaultInstance();
        RealmResults<Country> realmResults = realm.where(Country.class).equalTo(CountryFields.NUMERIC_CODE, code).findAll();
        if(realmResults.size() > 0){
            return realmResults.get(0);
        }
        return null;
    }

    public void saveCountry(final Country country){
        Realm.getDefaultInstance().executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealm(country);
            }
        });
    }

    public void saveAllCountry(List<Country> countries){
        for(int i = 0; i < countries.size(); i++){
            saveCountry(countries.get(i));
        }
    }

    public List<Country> findCountryBySearch(String txt){
        List<Country> countryList = new ArrayList<>();
        Realm realm = Realm.getDefaultInstance();
        RealmResults<Country> realmResults = realm.where(Country.class)
                                                    .beginsWith(CountryFields.NAME,txt, Case.INSENSITIVE).or()
                                                    .contains(CountryFields.NAME,txt, Case.INSENSITIVE)
                                                    .or().contains(CountryFields.REGION, txt, Case.INSENSITIVE)
                                                    .or().beginsWith(CountryFields.REGION, txt, Case.INSENSITIVE)
                                                    .or().contains(CountryFields.NATIVE_NAME, txt, Case.INSENSITIVE)
                                                    .or().beginsWith(CountryFields.NATIVE_NAME, txt, Case.INSENSITIVE)
                                                    .or().contains(CountryFields.CAPITAL, txt, Case.INSENSITIVE)
                                                    .or().beginsWith(CountryFields.CAPITAL, txt, Case.INSENSITIVE)
                                                    .findAll();
        countryList.addAll(realmResults);
        return countryList;
    }


}
