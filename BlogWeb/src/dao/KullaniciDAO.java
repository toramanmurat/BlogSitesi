package dao;


import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import model.Blog;
import model.BlogDetay;
import model.Girdi;
import model.GirdiDetay;
import model.Kullanici;
import model.Yorum;
import model.YorumDetay;

public interface KullaniciDAO {

	
	public void baglan() throws SQLException;
	
	public void baglantiyiKes();
	
	public void tumTablolariTemizle() ;
	
	public void tabloIceriginiSil(String tabloAdi) ;
	
	public void kullaniciOlustur(Kullanici k);
	
	public void blogOlustur(Blog b);
	
	public void girdiOlustur(Girdi g);
	
	public void yorumOlustur(Yorum y);
	
	public boolean emailKayitliMi(String email) throws SQLException;
	
	public int kullaniciIDsiniBul(String kullaniciEmail) throws SQLException;
	
	public int blogIDSiniBul(Timestamp OlusturmaTarih) throws SQLException;
	
	public int girdiIDSiniBul(Timestamp GirdiTarih) throws SQLException;
	
	public Kullanici kullaniciBilgisiniGetir(String kullaniciEmail,
	            String kullaniciSifre) throws SQLException;
	
	public Kullanici kullaniciBilgisiGetir(String kullaniciEmail) throws SQLException;
	
	public Kullanici kullaniciBilgisiGetir(int kullaniciId) throws SQLException;
	
	public List<Kullanici> tumKullanicilariGetir() throws SQLException;
	
	public Blog blogBilgisiGetir(int blogId) throws SQLException;
	
	public Yorum yorumBilgisiniGetir(int yorumId) throws SQLException;
	
	public int[] blogIstatistikGetir(int blogID) throws SQLException;
	
	public List<Blog> tumBloglariGetir() throws SQLException;
	
	public List<Blog> birKullaniciyaAitTumBloglariGetir(Kullanici k) throws SQLException;
	
	public int kullanicininSahipOlduguBloglarinSayisiniGetir(Kullanici k) throws SQLException;
	
	public List<Girdi> birBlogaAitTumGirdileriGetir(Blog b) throws SQLException;
	
	public List<Yorum> birGirdiyeAitTumYorumlariGetir(Girdi g) throws SQLException;
	
	public List<Yorum> birGirdiyeAitTumYorumlariGetir(int girdiId) throws SQLException;
	
	public List<YorumDetay> birGirdiyeAitEnGuncelUcYorumDetayiniGetir(int girdiId) throws SQLException;
	
	public List<YorumDetay> birGirdiyeAitTumYorumDetaylariniGetir(int girdiId) throws SQLException;
	
	public boolean yorumuSil(Yorum y) throws SQLException;
	
	public boolean girdiyiSil(Girdi g) throws SQLException;
	
	public boolean bloguSil(Blog b) throws SQLException;
	
	public boolean kullaniciyiSil(Kullanici k) throws SQLException;
	 
	public BlogDetay blogDetayGetir(int blogId) throws SQLException;
	
	public GirdiDetay enGuncelGirdiyiGetir(int blogId) throws SQLException;
	
	public GirdiDetay seciliGirdininDetayiniGetir(int girdiId) throws SQLException;
	
	public List<Girdi> enGuncelBesGirdiyiGetir(int blogId) throws SQLException;
	
	public List<Girdi> tumGirdileriGetir(int blogId) throws SQLException;
	
	public boolean blogBilgisiGuncelle(Blog b) throws SQLException;
	
	public boolean kullaniciBilgisiGuncelle(Kullanici k) throws SQLException;
	
	public boolean girdiBilgisiGuncelle(Girdi g) throws SQLException;
	
	public boolean yorumBilgisiGuncelle(Yorum y) throws SQLException;
	
	public int birGirdiyeAitYorumSayisiniGetir(int girdiId) throws SQLException;
	
	public String[] tabloKolonAdlariniGetir(String tabloAdi) throws SQLException;
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	 
	 
	
	
}
