package model;

import java.sql.Timestamp;

import general.Araclar;

public class Kullanici {

	private int kullaniciId;
	private String kullaniciEmail;
	private String kullaniciSifre;
	private String kullaniciAdSoyad;
	private String kullaniciIzin;
	private Timestamp kayitTarih;
	
	public void bosKullaniciOlustur() {
		this.kullaniciAdSoyad="Yeni Kullanici";
		this.kullaniciEmail="deneme@deneme.com";
		this.kullaniciSifre="deneme";
		this.kullaniciIzin="A";
		this.kayitTarih=Araclar.yeniTimeStampOlustur();
		
	}

	public int getKullaniciId() {
		return kullaniciId;
	}

	public void setKullaniciId(int kullaniciId) {
		this.kullaniciId = kullaniciId;
	}

	public String getKullaniciEmail() {
		return kullaniciEmail;
	}

	public void setKullaniciEmail(String kullaniciEmail) {
		this.kullaniciEmail = kullaniciEmail;
	}

	public String getKullaniciSifre() {
		return kullaniciSifre;
	}

	public void setKullaniciSifre(String kullaniciSifre) {
		this.kullaniciSifre = kullaniciSifre;
	}

	public String getKullaniciAdSoyad() {
		return kullaniciAdSoyad;
	}

	public void setKullaniciAdSoyad(String kullaniciAdSoyad) {
		this.kullaniciAdSoyad = kullaniciAdSoyad;
	}

	public String getKullaniciIzin() {
		return kullaniciIzin;
	}

	public void setKullaniciIzin(String kullaniciIzin) {
		this.kullaniciIzin = kullaniciIzin;
	}

	public Timestamp getKayitTarih() {
		return kayitTarih;
	}

	public void setKayitTarih(Timestamp kayitTarih) {
		this.kayitTarih = kayitTarih;
	}
	
	
	
	
}
