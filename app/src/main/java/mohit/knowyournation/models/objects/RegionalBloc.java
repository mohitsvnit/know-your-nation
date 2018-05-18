package mohit.knowyournation.models.objects;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.Ignore;

/**
 * Created by mohit on 2/12/17.
 */

public class RegionalBloc extends RealmObject{

    @SerializedName("acronym")
    @Expose
    private String acronym;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("otherAcronyms")
    @Expose
    @Ignore private List<String> otherAcronyms = null;
    private RealmList<String> listOtherAcronyms = null;

    @SerializedName("otherNames")
    @Expose
    @Ignore private List<String> otherNames = null;
    private RealmList<String> listOtherNames = null;
    public RegionalBloc(){

    }

    public String getAcronym() {
        return acronym;
    }

    public void setAcronym(String acronym) {
        this.acronym = acronym;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getOtherAcronyms() {
        return otherAcronyms;
    }

    public RealmList<String> getListOtherAcronyms() {
        return listOtherAcronyms;
    }

    public void setListOtherAcronyms(RealmList<String> listOtherAcronyms) {
        this.listOtherAcronyms = listOtherAcronyms;
    }

    public RealmList<String> getListOtherNames() {
        return listOtherNames;
    }

    public void setListOtherNames(RealmList<String> listOtherNames) {
        this.listOtherNames = listOtherNames;
    }

    public void setOtherAcronyms(List<String> otherAcronyms) {
        RealmList<String> realmList = new RealmList<>();
        realmList.addAll(otherAcronyms);
        setListOtherAcronyms(realmList);
        this.otherAcronyms = otherAcronyms;
    }

    public List<String> getOtherNames() {
        return otherNames;
    }

    public void setOtherNames(List<String> otherNames) {
        RealmList<String> realmList = new RealmList<>();
        realmList.addAll(otherNames);
        setListOtherNames(realmList);
        this.otherNames = otherNames;
    }


}
