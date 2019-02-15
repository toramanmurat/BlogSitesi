package model;

import java.sql.Timestamp;

import general.Araclar;

public class Girdi {

	private int girdiId;
	private int blogId;
	private int kullaniciId;
	private String girdiBaslik;
	private String girdiIcerik;
	private Timestamp girdiTarih;
	
	public int getGirdiId() {
		return girdiId;
	}
	public void setGirdiId(int girdiId) {
		this.girdiId = girdiId;
	}
	public int getBlogId() {
		return blogId;
	}
	public void setBlogId(int blogId) {
		this.blogId = blogId;
	}
	public int getKullaniciId() {
		return kullaniciId;
	}
	public void setKullaniciId(int kullaniciId) {
		this.kullaniciId = kullaniciId;
	}
	public String getGirdiBaslik() {
		return girdiBaslik;
	}
	public void setGirdiBaslik(String girdiBaslik) {
		this.girdiBaslik = girdiBaslik;
	}
	public String getGirdiIcerik() {
		return girdiIcerik;
	}
	public void setGirdiIcerik(String girdiIcerik) {
		this.girdiIcerik = girdiIcerik;
	}
	public Timestamp getGirdiTarih() {
		return girdiTarih;
	}
	public void setGirdiTarih(Timestamp girdiTarih) {
		this.girdiTarih = girdiTarih;
	}
	
	public void hosgeldinizGirdisiHazirla(Kullanici k,Blog b) {
		
		this.blogId=b.getBlogId();
		this.kullaniciId=b.getKullaniciId();
		this.girdiBaslik="Bloğuma Hoş Geldiniz";
		this.girdiIcerik="Bu bir deneme girdisidir.Yeni girdi eklemek "+
						" yada bu girdiyi degistirmek icin giris linkini tiklayiniz";
		this.girdiTarih=Araclar.yeniTimeStampOlustur();

	}
	
	public String getGirdiOzetIcerik() {
		if(this.girdiIcerik.length()>100) {
			return this.girdiIcerik.substring(0, 100)+" .......";
		}
		else {
			return this.girdiIcerik;
		}
		
	}
	
	public String getTarih() {
		return Araclar.sadeceTarihGoster(this.girdiTarih);
		
	}
	
	public String getZaman() {
		return Araclar.sadeceZamanGoster(this.girdiTarih);
	}
	
}
