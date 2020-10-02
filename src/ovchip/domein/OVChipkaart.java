package ovchip.domein;

import org.hibernate.annotations.JoinColumnOrFormula;

import javax.persistence.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
@Entity(name = "ov_chipkaart")
public class OVChipkaart {
    @Id
//    @Column(name = "kaart_nummer")
    private int kaart_nummer;
    private Date geldig_tot;
    private int klasse;
    private int saldo;
    @Transient
    private Reiziger reiziger;
//    @Transient
    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(name = "ov_chipkaart_product",
        joinColumns = { @JoinColumn(name = "kaart_nummer") },
        inverseJoinColumns = { @JoinColumn(name = "product_nummer") })
    List<Product> products = new ArrayList<>();


    public OVChipkaart(){}


    public OVChipkaart(int kaart_nummer, Date geldig_tot, int klasse, int saldo, Reiziger reiziger) {
        this.kaart_nummer = kaart_nummer;
        this.geldig_tot = geldig_tot;
        this.klasse = klasse;
        this.saldo = saldo;
        this.reiziger = reiziger;
    }

    public int getKaartnummer() {
        return kaart_nummer;
    }

    public void setKaartnummer(int kaartnummer) {
        this.kaart_nummer = kaartnummer;
    }

    public Date getGeldig_tot() {
        return geldig_tot;
    }

    public void setGeldig_tot(Date geldig_tot) {
        this.geldig_tot = geldig_tot;
    }

    public int getKlasse() {
        return klasse;
    }

    public void setKlasse(int klasse) {
        this.klasse = klasse;
    }

    public int getSaldo() {
        return saldo;
    }

    public void setSaldo(int saldo) {
        this.saldo = saldo;
    }

    public Reiziger getReiziger() {
        return reiziger;
    }

    public void setReiziger_id(Reiziger reiziger) {
        this.reiziger = reiziger;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    @Override
    public String toString() {
        return "OVChipkaart{" + kaart_nummer + " " + geldig_tot + " " + klasse + " " + saldo  + " " + products;
    }
}
