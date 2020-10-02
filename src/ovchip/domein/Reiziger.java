package ovchip.domein;

import javax.persistence.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Entity //table name
public class Reiziger {
    @Id //primary key
    //@Column(name = "") //dit gebruikt je wanneer attribuut naam niet overeen komt met tabel naam, bij jou nu wel
    private int reiziger_id;
    private String voorletters;
    private String tussenvoegsel;
    private String achternaam;
    private Date geboortedatum;
//    @Transient // dit negeert de relatie voor nu maar dit moet je straks fixen
    @OneToOne
    @JoinColumn(name = "reiziger_id", foreignKey = @ForeignKey(name = "REIZIGER_ID_FK"))
    private Adres adres;
//    @Transient
    @OneToMany
    @JoinColumn(name = "reiziger_id", foreignKey = @ForeignKey(name = "REIZIGER_ID_FK"))
    List<OVChipkaart> ovChipkaarten = new ArrayList<>();

    public Reiziger() { //defualt constructor

    }
    public Reiziger(int reiziger_id, String voorletters, String tussenvoegsel, String achternaam, Date geboortedatum) {
        this.reiziger_id = reiziger_id;
        this.voorletters = voorletters;
        this.tussenvoegsel = tussenvoegsel;
        this.achternaam = achternaam;
        this.geboortedatum = geboortedatum;

    }


    public void setOvChipkaarten(OVChipkaart ovChipkaart) {
        this.ovChipkaarten.add(ovChipkaart);
    }

    public int getReiziger_id() {
        return reiziger_id;
    }

    public void setReiziger_id(int reiziger_id) {
        this.reiziger_id = reiziger_id;
    }

    public String getVoorletters() {
        return voorletters;
    }

    public void setVoorletters(String voorletters) {
        this.voorletters = voorletters;
    }

    public String getTussenvoegsel() {
        return tussenvoegsel;
    }

    public void setTussenvoegsel(String tussenvoegsel) {
        this.tussenvoegsel = tussenvoegsel;
    }

    public String getAchternaam() {
        return achternaam;
    }

    public void setAchternaam(String achternaam) {
        this.achternaam = achternaam;
    }

    public Date getGeboortedatum() {
        return geboortedatum;
    }

    public void setGeboortedatum(Date geboortedatum) {
        this.geboortedatum = geboortedatum;
    }

    public void setAdres(Adres adres) {
        this.adres = adres;
    }

    public Adres getAdres() {
        return adres;
    }

    public void createAdres(Adres adres) {
        this.adres = adres;
    }

    public List<OVChipkaart> getOvChipkaarten() {
        return ovChipkaarten;
    }

    @Override
    public String toString() {
        return "Reiziger{#" + reiziger_id + " " + voorletters + ". " + tussenvoegsel + " " + achternaam + ", geb. " + geboortedatum + ", " + adres + ", " +
                 ovChipkaarten +
                "}";
    }

}
