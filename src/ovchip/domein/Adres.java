package ovchip.domein;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

@Entity
public class Adres {
    @Id
    private int adres_id;
    private String postcode;
    private String huisnummer;
    private String straat;
    private String woonplaats;
    private int reiziger_id;
    @Transient
    private int reiziger;

    public Adres(){}


    public Adres(int adres_id, String postcode, String huisnummer, String straat, String woonplaats, int reiziger_id) {
        this.adres_id = adres_id;
        this.postcode = postcode;
        this.huisnummer = huisnummer;
        this.straat = straat;
        this.woonplaats = woonplaats;
        this.reiziger_id = reiziger_id;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public int getAdres_id() {
        return adres_id;
    }

    public void setAdres_id(int adres_id) {
        this.adres_id = adres_id;
    }

    public String getHuisnummer() {
        return huisnummer;
    }

    public void setHuisnummer(String huisnummer) {
        this.huisnummer = huisnummer;
    }

    public String getStraat() {
        return straat;
    }

    public void setStraat(String straat) {
        this.straat = straat;
    }

    public String getWoonplaats() {
        return woonplaats;
    }

    public void setWoonplaats(String woonplaats) {
        this.woonplaats = woonplaats;
    }

    public int getReiziger_id() {
        return reiziger_id;
    }

    public void setReiziger_id(int reiziger_id) {
        this.reiziger_id = reiziger_id;
    }

//    @Override
//    public String toString() {
//        return "{#" + adres_id + postcode + huisnummer + straat + woonplaats + reiziger_id
//    }
    @Override
    public String toString() {
        return "Adres {" + adres_id +", " + postcode + " " + straat +" "+ huisnummer + " " + woonplaats + ", " + reiziger_id + "}";
    }
}
