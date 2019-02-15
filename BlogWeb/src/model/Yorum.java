package model;

import java.sql.Timestamp;

import general.Araclar;

public class Yorum {
	
	private int yorumId;
	private int girdiId;
	private int kullaniciId;
	private String yorumBaslik;
	private String yorumIcerik;
	private Timestamp yorumTarih;
	
	public void hosgeldinYorumuHazirla(Girdi g,Kullanici k) {
		this.girdiId=g.getGirdiId();
		this.kullaniciId=k.getKullaniciId();
		this.yorumBaslik="Ilk yorum";
		this.yorumIcerik="Merhaba "+k.getKullaniciAdSoyad()
						+" bloğumuza hoşgeldiniz.";
		this.yorumTarih=Araclar.yeniTimeStampOlustur();
		
	}

	public int getYorumId() {
		return yorumId;
	}

	public void setYorumId(int yorumId) {
		this.yorumId = yorumId;
	}

	public int getGirdiId() {
		return girdiId;
	}

	public void setGirdiId(int girdiId) {
		this.girdiId = girdiId;
	}

	public int getKullaniciId() {
		return kullaniciId;
	}

	public void setKullaniciId(int kullaniciId) {
		this.kullaniciId = kullaniciId;
	}

	public String getYorumBaslik() {
		return yorumBaslik;
	}

	public void setYorumBaslik(String yorumBaslik) {
		this.yorumBaslik = yorumBaslik;
	}

	public String getYorumIcerik() {
		return yorumIcerik;
	}

	public void setYorumIcerik(String yorumIcerik) {
		this.yorumIcerik = yorumIcerik;
	}

	public Timestamp getYorumTarih() {
		return yorumTarih;
	}

	public void setYorumTarih(Timestamp yorumTarih) {
		this.yorumTarih = yorumTarih;
	}
	
	

}
