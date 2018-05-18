package mohit.knowyournation.models.objects;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;

/**
 * Created by mohit on 2/12/17.
 */

public class Country extends RealmObject{

    @SerializedName("name")
    @Expose
    private String name;

    @Expose
    @Ignore private List<String> callingCodes = null;

    private RealmList<String> listCallingCodes = null;

    @SerializedName("capital")
    @Expose
    private String capital;
    @SerializedName("altSpellings")
    @Expose
    @Ignore private List<String> altSpellings = null;

    private RealmList<String> listAltSpellings = null;

    @SerializedName("region")
    @Expose
    private String region;
    @SerializedName("subregion")
    @Expose
    private String subregion;
    @SerializedName("population")
    @Expose
    private Integer population;
    @SerializedName("latlng")
    @Expose
    @Ignore private List<Double> latlng = null;
    private RealmList<Double> listLatLng = null;

    @SerializedName("demonym")
    @Expose
    private String demonym;
    @SerializedName("area")
    @Expose
    private Double area;
    @SerializedName("timezones")
    @Expose
    @Ignore private List<String> timezones = null;
    private RealmList<String> listTimeZones = null;

    @SerializedName("borders")
    @Expose
    @Ignore private List<String> borders = null;
    private RealmList<String> listBorders = null;

    @SerializedName("nativeName")
    @Expose
    @PrimaryKey
    private String nativeName;
    @SerializedName("numericCode")
    @Expose
    private String numericCode;
    @SerializedName("currencies")
    @Expose
    @Ignore private List<Currency> currencies = null;
    private RealmList<Currency> listCurrencies = null;

    @SerializedName("languages")
    @Expose
    @Ignore private List<Language> languages = null;
    private RealmList<Language> listLanguages = null;

    @SerializedName("flag")
    @Expose
    private String flag;
    @SerializedName("regionalBlocs")
    @Expose
    @Ignore private List<RegionalBloc> regionalBlocs = null;
    private RealmList<RegionalBloc> listRegionalBlocs = null;


    public Country(){

    }




    public RealmList<String> getListCallingCodes() {
        return listCallingCodes;
    }

    public void setListCallingCodes(RealmList<String> listCallingCodes) {
        this.listCallingCodes = listCallingCodes;
    }

    public RealmList<String> getListAltSpellings() {
        return listAltSpellings;
    }

    public void setListAltSpellings(RealmList<String> listAltSpellings) {
        this.listAltSpellings = listAltSpellings;
    }

    public RealmList<Double> getListLatLng() {
        return listLatLng;
    }

    public void setListLatLng(RealmList<Double> listLatLng) {
        this.listLatLng = listLatLng;
    }

    public RealmList<String> getListTimeZones() {
        return listTimeZones;
    }

    public void setListTimeZones(RealmList<String> listTimeZones) {
        this.listTimeZones = listTimeZones;
    }

    public RealmList<String> getListBorders() {
        return listBorders;
    }

    public void setListBorders(RealmList<String> listBorders) {
        this.listBorders = listBorders;
    }

    public RealmList<Currency> getListCurrencies() {
        return listCurrencies;
    }

    public void setListCurrencies(RealmList<Currency> listCurrencies) {
        this.listCurrencies = listCurrencies;
    }

    public RealmList<Language> getListLanguages() {
        return listLanguages;
    }

    public void setListLanguages(RealmList<Language> listLanguages) {
        this.listLanguages = listLanguages;
    }

    public RealmList<RegionalBloc> getListRegionalBlocs() {
        return listRegionalBlocs;
    }

    public void setListRegionalBlocs(RealmList<RegionalBloc> listRegionalBlocs) {
        this.listRegionalBlocs = listRegionalBlocs;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



    public List<String> getCallingCodes() {
        return callingCodes;
    }

    public void setCallingCodes(List<String> callingCodes) {

        this.callingCodes = callingCodes;
    }

    public String getCapital() {
        return capital;
    }

    public void setCapital(String capital) {
        this.capital = capital;
    }

    public List<String> getAltSpellings() {
        return altSpellings;
    }

    public void setAltSpellings(List<String> altSpellings) {

        this.altSpellings = altSpellings;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getSubregion() {
        return subregion;
    }

    public void setSubregion(String subregion) {
        this.subregion = subregion;
    }

    public Integer getPopulation() {
        return population;
    }

    public void setPopulation(Integer population) {
        this.population = population;
    }

    public List<Double> getLatlng() {
        return latlng;
    }

    public void setLatlng(List<Double> latlng) {

        this.latlng = latlng;
    }


    public String getDemonym() {
        return demonym;
    }

    public void setDemonym(String demonym) {
        this.demonym = demonym;
    }

    public Double getArea() {
        return area;
    }

    public void setArea(Double area) {
        this.area = area;
    }



    public List<String> getTimezones() {
        return timezones;
    }

    public void setTimezones(List<String> timezones) {

        this.timezones = timezones;
    }

    public List<String> getBorders() {
        return borders;
    }

    public void setBorders(List<String> borders) {

        this.borders = borders;
    }

    public String getNativeName() {
        return nativeName;
    }

    public void setNativeName(String nativeName) {
        this.nativeName = nativeName;
    }

    public String getNumericCode() {
        return numericCode;
    }

    public void setNumericCode(String numericCode) {
        this.numericCode = numericCode;
    }

    public List<Currency> getCurrencies() {
        return currencies;
    }

    public void setCurrencies(List<Currency> currencies) {
        this.currencies = currencies;
    }

    public List<Language> getLanguages() {
        return languages;
    }

    public void setLanguages(List<Language> languages) {
        this.languages = languages;
    }


    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public List<RegionalBloc> getRegionalBlocs() {
        return regionalBlocs;
    }

    public void setRegionalBlocs(List<RegionalBloc> regionalBlocs) {

        this.regionalBlocs = regionalBlocs;
    }





    @Override
    public String toString() {
        return "Country{" +
                "name='" + name + '\'' +
                ", callingCodes=" + callingCodes +
                ", listCallingCodes=" + listCallingCodes +
                ", capital='" + capital + '\'' +
                ", altSpellings=" + altSpellings +
                ", listAltSpellings=" + listAltSpellings +
                ", region='" + region + '\'' +
                ", subregion='" + subregion + '\'' +
                ", population=" + population +
                ", latlng=" + latlng +
                ", listLatLng=" + listLatLng +
                ", demonym='" + demonym + '\'' +
                ", area=" + area +
                ", timezones=" + timezones +
                ", listTimeZones=" + listTimeZones +
                ", borders=" + borders +
                ", listBorders=" + listBorders +
                ", nativeName='" + nativeName + '\'' +
                ", numericCode='" + numericCode + '\'' +
                ", currencies=" + currencies +
                ", listCurrencies=" + listCurrencies +
                ", languages=" + languages +
                ", listLanguages=" + listLanguages +
                ", flag='" + flag + '\'' +
                ", regionalBlocs=" + regionalBlocs +
                ", listRegionalBlocs=" + listRegionalBlocs +
                '}';
    }
}
