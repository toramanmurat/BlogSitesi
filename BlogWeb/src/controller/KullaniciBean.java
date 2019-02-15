package controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import dao.KullaniciDAO;
import dao.KullaniciDAOImpl;
import general.Araclar;
import model.Blog;
import model.BlogDetay;
import model.Girdi;
import model.GirdiDetay;
import model.Kullanici;
import model.Yorum;
import model.YorumDetay;

@ManagedBean
@SessionScoped
public class KullaniciBean implements Serializable{

		/**
	 * 
	 */
		private static final long serialVersionUID = 1L;
		
		
		private String kullaniciAdi;
		private String sifre;
		private Kullanici kullaniciBilgisi=null;
		private List<Blog> blogListesi=null;
		private BlogDetay seciliBlog=null;
		private GirdiDetay seciliGirdi=null;
		private Yorum seciliYorum=null;
		private List<Girdi> girdiListesi=null;
		private List<Girdi> enGuncelBesGirdi=null;
		private KullaniciDAO kullaniciDAO;////???
		
		
		private String blogBaslik=null;
		private String blogAciklama=null;
		private String girdiBaslik=null;
		private String girdiIcerik=null;
		private String yorumBaslik=null;
		private String yorumIcerik=null;
		
		@PostConstruct
		public void initialize() {
			
			kullaniciDAO=new KullaniciDAOImpl();
			
			
		}
		
		public String girisYap() {
			try {
				
				kullaniciDAO.baglan();
				kullaniciBilgisi=kullaniciDAO.kullaniciBilgisiniGetir(kullaniciAdi, sifre);
				kullanicininBloglariniListele();
				kullaniciDAO.baglantiyiKes();
				if(kullaniciBilgisi!=null) {
					return "kullanici";
				}
				
			} catch (Exception e) {
				return null;
			}
			return null;
		}
		
		public String cikis() {
			this.kullaniciBilgisi=null;
			this.kullaniciAdi = null;
		    this.sifre = null;
		    this.blogListesi = null;
		    this.seciliBlog = null;
		    this.seciliGirdi = null;
		    this.seciliYorum = null;
		    return "index";
		}
		
		public String kullanicininBloglariniListele() {
			try {
				kullaniciDAO.baglan();
				blogListesi=kullaniciDAO.birKullaniciyaAitTumBloglariGetir(this.kullaniciBilgisi);
				kullaniciDAO.baglantiyiKes();
				
			} catch (Exception e) {
				return null;
			}
			return "kullanici";
		}
		
		public String tumBloglariListele() {
			try {
				kullaniciDAO.baglan();
				blogListesi=kullaniciDAO.tumBloglariGetir();
				kullaniciDAO.baglantiyiKes();
				
			} catch (Exception e) {
				// TODO: handle exception
				return null;
				
			}
			
			return "kullanici";
			
		}
		
		public String blogaGit(Blog blog) {
			try {
				kullaniciDAO.baglan();
				int id =kullaniciDAO.blogIDSiniBul(blog.getOlusturmaTarih());
				blog.setBlogId(id);
				this.seciliBlog=kullaniciDAO.blogDetayGetir(blog.getBlogId());
				this.seciliGirdi=kullaniciDAO.enGuncelGirdiyiGetir(blog.getBlogId());
				this.enGuncelBesGirdi=kullaniciDAO.enGuncelBesGirdiyiGetir(blog.getBlogId());
				kullaniciDAO.baglantiyiKes();
				return "blog";
				
				
			} catch (Exception e) {
				// TODO: handle exception
				return null;
			}	
		}
		
		public String bloguSil(Blog b) throws Exception{
			kullaniciDAO.baglan();
			kullaniciDAO.bloguSil(b);
			kullaniciDAO.baglantiyiKes();
			kullanicininBloglariniListele();
			return "kullanici?faces-redirect=true";
		}
		
		public String girdiyeGit(Girdi girdi) {
			try {
				kullaniciDAO.baglan();
				this.seciliGirdi=kullaniciDAO.seciliGirdininDetayiniGetir(girdi.getGirdiId());
				kullaniciDAO.baglantiyiKes();
				return "blog";
				
			} catch (Exception e) {
				return null;
			}
			
		}
		
		public String tumYorumlariListele() {
			
			try {
				kullaniciDAO.baglan();
				this.seciliGirdi.setYorumlar(kullaniciDAO.birGirdiyeAitTumYorumDetaylariniGetir(this.seciliGirdi.getGirdiId()));
				
				kullaniciDAO.baglantiyiKes();
				
				return "blog";
				
			} catch (Exception e) {
				return null;
			}
	
		}
		
		public String tumGirdileriGetir() {
			
			try {
				kullaniciDAO.baglan();
				this.girdiListesi=kullaniciDAO.tumGirdileriGetir(this.seciliBlog.getBlogId());
				kullaniciDAO.baglantiyiKes();
				return "tumgirdiler";
				
			} catch (Exception e) {
				return null;
			}
			
		}
		
		public String tumGirdileriGetir(int blogId) {
			
			try {
				kullaniciDAO.baglan();
				this.seciliBlog=kullaniciDAO.blogDetayGetir(blogId);
				this.seciliGirdi=kullaniciDAO.enGuncelGirdiyiGetir(blogId);
				this.enGuncelBesGirdi =kullaniciDAO.enGuncelBesGirdiyiGetir(blogId);
				this.girdiListesi=kullaniciDAO.tumGirdileriGetir(blogId);
				kullaniciDAO.baglantiyiKes();
				return "tumgirdiler";
				
			} catch (Exception e) {
				return null;
			}
		}
		
		 public String blogEkle() throws Exception{
			 Blog b=new Blog();
			 b.setAciklama(this.blogAciklama);
			 b.setBlogBaslik(this.blogBaslik);
			 b.setKullaniciId(this.getKullaniciBilgisi().getKullaniciId());
			 b.setOlusturmaTarih(Araclar.yeniTimeStampOlustur());
			 kullaniciDAO.baglan();
			 kullaniciDAO.blogOlustur(b);
			 kullaniciDAO.baglantiyiKes();
			 return blogaGit(b);
		 }
		 
		 public String girdiEkle() throws Exception{
			 Girdi g=new Girdi();
			 g.setGirdiBaslik(this.girdiBaslik);
			 g.setGirdiIcerik(this.girdiIcerik);
			 g.setKullaniciId(this.getKullaniciBilgisi().getKullaniciId());
			 g.setBlogId(this.seciliBlog.getBlogId());
			 g.setGirdiTarih(Araclar.yeniTimeStampOlustur());
			 kullaniciDAO.baglan();
			 kullaniciDAO.girdiOlustur(g);
			 kullaniciDAO.baglantiyiKes();
			 return blogaGit(seciliBlog);
		 }
		 
		 public String yorumEkle() throws Exception{
			 Yorum y=new Yorum();
			  y.setKullaniciId(this.kullaniciBilgisi.getKullaniciId());
		      y.setYorumBaslik(yorumBaslik);
		      y.setYorumIcerik(yorumIcerik);
		      y.setGirdiId(this.seciliGirdi.getGirdiId());
		      y.setYorumTarih(Araclar.yeniTimeStampOlustur());
		      kullaniciDAO.baglan();
		      kullaniciDAO.yorumOlustur(y);
		      kullaniciDAO.baglantiyiKes();
		      return girdiyeGit(this.seciliGirdi);
			 
		 }
		 
		 public String yorumDuzenleSayfasi() throws Exception {
		        this.yorumBaslik = "";
		        this.yorumIcerik = "";
		        return "yorumduzenle";
		 }
		
		 public String yorumDuzenleSayfasi(YorumDetay yorumDetay) throws Exception{
			 kullaniciDAO.baglan();
			 this.seciliYorum=kullaniciDAO.yorumBilgisiniGetir(yorumDetay.getYorumId());
			 kullaniciDAO.baglantiyiKes();
			 this.yorumBaslik = this.seciliYorum.getYorumBaslik();
		     this.yorumIcerik = this.seciliYorum.getYorumIcerik();
		     return "yorumduzenle";
		
		 }
		 
		 public String yorumGuncelle() throws Exception{
			 
			 Yorum yorum=this.seciliYorum;
			 yorum.setYorumBaslik(yorumBaslik);
			 yorum.setYorumTarih(Araclar.yeniTimeStampOlustur());
			 yorum.setYorumIcerik(yorumIcerik);
			 kullaniciDAO.baglan();
			 kullaniciDAO.yorumBilgisiGuncelle(yorum);
			 kullaniciDAO.baglantiyiKes();
			 return girdiyeGit(seciliGirdi);
			 
		 }
		 
		 public String yorumSil(YorumDetay yorumDetay) throws Exception {
			 kullaniciDAO.baglan();
			 kullaniciDAO.yorumuSil(yorumDetay);
			 kullaniciDAO.baglantiyiKes();
			 return girdiyeGit(seciliGirdi);
		 }
		 
		 public String girdiSil(Girdi girdi) throws Exception{
			 kullaniciDAO.baglan();
			 kullaniciDAO.girdiyiSil(this.seciliGirdi);
			 this.enGuncelBesGirdi=kullaniciDAO.enGuncelBesGirdiyiGetir(this.seciliBlog.getBlogId());
			 this.girdiListesi=kullaniciDAO.tumGirdileriGetir(this.seciliBlog.getBlogId());
			 if(this.enGuncelBesGirdi !=null) {
				 this.seciliGirdi=kullaniciDAO.enGuncelGirdiyiGetir(this.seciliBlog.getBlogId());
			 }
			 kullaniciDAO.baglantiyiKes();
			 return null;
		 }
		 
		 public String girdiSil() throws Exception{
			 kullaniciDAO.baglan();
			 kullaniciDAO.girdiyiSil(this.seciliGirdi);
			 this.enGuncelBesGirdi=kullaniciDAO.enGuncelBesGirdiyiGetir(this.seciliBlog.getBlogId());
			 this.girdiListesi=kullaniciDAO.tumGirdileriGetir(this.seciliBlog.getBlogId());
			 if(this.enGuncelBesGirdi !=null) {
				 this.seciliGirdi=kullaniciDAO.enGuncelGirdiyiGetir(this.seciliBlog.getBlogId());
			 }
			 kullaniciDAO.baglantiyiKes();
			 return "blog";
		 }
		 
		 public String girdiDuzenle() throws Exception {
			 kullaniciDAO.baglan();
			 this.seciliGirdi.setGirdiBaslik(girdiBaslik);
		     this.seciliGirdi.setGirdiIcerik(girdiIcerik);
		     this.seciliGirdi.setGirdiTarih(Araclar.yeniTimeStampOlustur());
		     kullaniciDAO.girdiBilgisiGuncelle(this.seciliGirdi);
		     this.enGuncelBesGirdi=kullaniciDAO.enGuncelBesGirdiyiGetir(this.seciliBlog.getBlogId());
		     this.girdiListesi=kullaniciDAO.tumGirdileriGetir(this.seciliBlog.getBlogId());
		     if(this.enGuncelBesGirdi !=null) {
		    	 this.seciliGirdi=kullaniciDAO.enGuncelGirdiyiGetir(this.seciliBlog.getBlogId());
		     }
		     kullaniciDAO.baglantiyiKes();
		     return "blog";
		 }
		 
		 public String girdiDuzenleSayfasi(Girdi girdi) throws Exception {
			 kullaniciDAO.baglan();
			 this.seciliGirdi=kullaniciDAO.seciliGirdininDetayiniGetir(girdi.getGirdiId());
			 kullaniciDAO.baglantiyiKes();
			 this.girdiBaslik=this.seciliGirdi.getGirdiBaslik();
			 this.girdiIcerik=this.seciliGirdi.getGirdiIcerik();
			 return "girdiduzenle";
			 
		 }
		 
		 public boolean isKullaniciBlogSahibiVeyaAdminMi() {
			 if(this.kullaniciBilgisi.getKullaniciIzin().charAt(0)=='A') {
				 return true;
			 }
			 if ((this.kullaniciBilgisi.getKullaniciIzin().charAt(0) == 'Y')
		                && (this.seciliBlog.getKullanici().getKullaniciId()
		                == this.kullaniciBilgisi.getKullaniciId())) {
		            
		            return true;
		     }
			 
			 return false;
			 
		 }
		 
		 public String yeniGirdi() {
		        this.girdiBaslik = "";
		        this.girdiIcerik = "";
		        return "girdiduzenle";
		 }
		 
		 public String yeniBlog() {
		        this.blogBaslik = "";
		        this.blogAciklama = "";
		        return "blogduzenle";
		}
		 
		 public int getKullanicininBlogSayisi() {
		        if (blogListesi != null) {
		            return blogListesi.size();
		        } else {
		            return 0;
		        }
		 }
		
		public String getKullaniciAdi() {
			return kullaniciAdi;
		}
		public void setKullaniciAdi(String kullaniciAdi) {
			this.kullaniciAdi = kullaniciAdi;
		}
		public String getSifre() {
			return sifre;
		}
		public void setSifre(String sifre) {
			this.sifre = sifre;
		}
		public Kullanici getKullaniciBilgisi() {
			return kullaniciBilgisi;
		}
		public void setKullaniciBilgisi(Kullanici kullaniciBilgisi) {
			this.kullaniciBilgisi = kullaniciBilgisi;
		}
		public List<Blog> getBlogListesi() {
			return blogListesi;
		}
		public void setBlogListesi(List<Blog> blogListesi) {
			this.blogListesi = blogListesi;
		}
		public BlogDetay getSeciliBlog() {
			return seciliBlog;
		}
		public void setSeciliBlog(BlogDetay seciliBlog) {
			this.seciliBlog = seciliBlog;
		}
		public GirdiDetay getSeciliGirdi() {
			return seciliGirdi;
		}
		public void setSeciliGirdi(GirdiDetay seciliGirdi) {
			this.seciliGirdi = seciliGirdi;
		}
		public Yorum getSeciliYorum() {
			return seciliYorum;
		}
		public void setSeciliYorum(Yorum seciliYorum) {
			this.seciliYorum = seciliYorum;
		}
		public List<Girdi> getGirdiListesi() {
			return girdiListesi;
		}
		public void setGirdiListesi(List<Girdi> girdiListesi) {
			this.girdiListesi = girdiListesi;
		}
		public List<Girdi> getEnGuncelBesGirdi() {
			return enGuncelBesGirdi;
		}
		public void setEnGuncelBesGirdi(List<Girdi> enGuncelBesGirdi) {
			this.enGuncelBesGirdi = enGuncelBesGirdi;
		}
		public String getBlogBaslik() {
			return blogBaslik;
		}
		public void setBlogBaslik(String blogBaslik) {
			this.blogBaslik = blogBaslik;
		}
		public String getBlogAciklama() {
			return blogAciklama;
		}
		public void setBlogAciklama(String blogAciklama) {
			this.blogAciklama = blogAciklama;
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
		
		
		
		
		
		
}
