package model;

import java.sql.Timestamp;

import general.Araclar;

public class Blog {
	
	private int blogId;
	private int kullaniciId;
	private String blogBaslik;
	private String aciklama;
	private Timestamp olusturmaTarih;
	
	public Blog() {
		
	}
	
	public Blog(Blog b) {
		
		this.blogId=b.blogId;
		this.kullaniciId=b.blogId;
		this.aciklama=b.aciklama;
		this.blogBaslik=b.blogBaslik;
		this.olusturmaTarih=b.olusturmaTarih;	
	}
	
	public void yeniKullaniciIcinBLogBilgisiHazirla(Kullanici k) {
		this.kullaniciId=k.getKullaniciId();
		this.blogBaslik="Merhaba. ben "+k.getKullaniciAdSoyad()+". Bloğuma hoşgeldiniz";
		this.aciklama="Bu otomatik oluşturulmuş bir blogdur. "+" adı ve şifreniz ile sisteme giriş yapınız.";
		this.olusturmaTarih=Araclar.yeniTimeStampOlustur();
		
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

	public String getBlogBaslik() {
		return blogBaslik;
	}

	public void setBlogBaslik(String blogBaslik) {
		this.blogBaslik = blogBaslik;
	}

	public String getAciklama() {
		return aciklama;
	}

	public void setAciklama(String aciklama) {
		this.aciklama = aciklama;
	}

	public Timestamp getOlusturmaTarih() {
		return olusturmaTarih;
	}

	public void setOlusturmaTarih(Timestamp olusturmaTarih) {
		this.olusturmaTarih = olusturmaTarih;
	}
	
	public String getBlogOzetAciklama() {
		
		if(this.aciklama.length()>100) {
			return this.aciklama.substring(0, 100) +" .......";
			
		}
		else {
			return this.aciklama;
		}
	}


	
	
	

}
