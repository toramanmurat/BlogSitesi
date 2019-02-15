package dao;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import com.mysql.cj.jdbc.PreparedStatement;

import general.Araclar;
import model.Blog;
import model.BlogDetay;
import model.Girdi;
import model.GirdiDetay;
import model.Kullanici;
import model.Yorum;
import model.YorumDetay;

public class KullaniciDAOImpl implements KullaniciDAO{
	

	String[] tabloIsimleri = {"Yorum", "Girdi", "Kullanicilar", "Blog"};
	private Connection connection=null;
	String veritabaniURL;
	String kullaniciAdi;
	String sifre;

	public KullaniciDAOImpl() {
		this.veritabaniURL="jdbc:mysql://localhost:3306/BlogDb?useSSL=false";
		this.kullaniciAdi = "root";
		this.sifre = "root";
		
	}


	public KullaniciDAOImpl(String veritabaniURL, String kullaniciAdi, String sifre) {
		super();
		this.veritabaniURL = veritabaniURL;
		this.kullaniciAdi = kullaniciAdi;
		this.sifre = sifre;
	}


	@Override
	public void baglan() throws SQLException {
		if((connection!=null)) {
			if(connection.isClosed()==false) {
				return;
			}
		}
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection=DriverManager.getConnection(this.veritabaniURL, this.kullaniciAdi, this.sifre);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}


	@Override
	public void baglantiyiKes()  {
		
		// TODO Auto-generated method stub
	
		try {
			
			if (connection != null) {
	            if (!connection.isClosed()) {
	                connection.close();
	            }
	        }
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
		
		
		
	}

	@Override
	public void tumTablolariTemizle()  {
		for(String s:tabloIsimleri) {
			tabloIceriginiSil(s);
		}
		
		
	}

	@Override
	public void tabloIceriginiSil(String tabloAdi)  {
		String sorgu="DELETE * FROM "+tabloAdi;
		Statement stmt = null;
		try {
			stmt=connection.createStatement();
			stmt.executeQuery(sorgu);
			stmt.close();
			
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
	}

	@Override
	public void kullaniciOlustur(Kullanici k) {
		String sorgu="INSERT INTO Kullanicilar VALUES (DEFAULT,?,?,?,?,?)";
		PreparedStatement pstmt;
		try {
			pstmt = (PreparedStatement) connection.prepareStatement(sorgu);
			pstmt.setString(1, k.getKullaniciEmail());
			pstmt.setString(2, k.getKullaniciSifre());
			pstmt.setString(3, k.getKullaniciAdSoyad());
			pstmt.setString(4, k.getKullaniciIzin());
			pstmt.setTimestamp(5, k.getKayitTarih());
			pstmt.executeUpdate();
			pstmt.close();
			
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
	}

	@Override
	public void blogOlustur(Blog b)  {
		String sorgu="INSERT INTO Blog VALUES (DEFAULT,?,?,?,?)";
		try {
			PreparedStatement pstmt=(PreparedStatement) connection.prepareStatement(sorgu);
			pstmt.setInt(1, b.getKullaniciId());
			pstmt.setString(2, b.getBlogBaslik());
			pstmt.setString(3, b.getAciklama());
			pstmt.setTimestamp(4, b.getOlusturmaTarih());
			pstmt.executeUpdate();
			pstmt.close();
			
		} catch (SQLException e) {
		
			e.printStackTrace();
		}
		
		
	}

	@Override
	public void girdiOlustur(Girdi g) {
		String sorgu="INSERT INTO Girdi VALUES (DEFAULT,?,?,?,?,?)";
		try {
			PreparedStatement pstm=(PreparedStatement) connection.prepareStatement(sorgu);
			pstm.setInt(1, g.getBlogId());
			pstm.setInt(2, g.getKullaniciId());
			pstm.setString(3, g.getGirdiBaslik());
			pstm.setString(4, g.getGirdiIcerik());
			pstm.setTimestamp(5, g.getGirdiTarih());
			pstm.executeUpdate();
			pstm.close();
			
			
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
	}

	@Override
	public void yorumOlustur(Yorum y) {
		String sorgu = "INSERT INTO Yorum VALUES (DEFAULT,?,?,?,?,?)";
		 try {
			PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(sorgu);
			 pstmt.setInt(1, y.getGirdiId());
		     pstmt.setInt(2, y.getKullaniciId());
		     pstmt.setString(3, y.getYorumBaslik());
		     pstmt.setString(4, y.getYorumIcerik());
		     pstmt.setTimestamp(5, y.getYorumTarih());
		     pstmt.executeUpdate();
		     pstmt.close();
		    
			
		} catch (SQLException e) {
		
			e.printStackTrace();
		}
		
	}

	@Override
	public boolean emailKayitliMi(String email) throws SQLException {
		String sorgu = "SELECT KullaniciEmail FROM Kullanicilar where KullaniciEmail = ?";
		
		
			PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(sorgu);
	        pstmt.setString(1, email);
	        boolean sonuc = pstmt.execute();
	        pstmt.close();
	      
	        return sonuc;
		
	
	}

	@Override
	public int kullaniciIDsiniBul(String kullaniciEmail) throws SQLException {
		String sorgu = "SELECT KullaniciId FROM Kullanicilar where KullaniciEmail = ?";
        PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(sorgu);
        pstmt.setString(1, kullaniciEmail);
        ResultSet rs = pstmt.executeQuery();

        int sonuc = 0;
        if (rs.next()) {
            sonuc = rs.getInt("KullaniciId");
        }

        pstmt.close();
        rs.close();
        return sonuc;
		

	}

	@Override
	public int blogIDSiniBul(Timestamp OlusturmaTarih) throws SQLException {
		String sorgu = "SELECT BlogId FROM Blog where OlusturmaTarih = ?";
        PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(sorgu);
        pstmt.setTimestamp(1, OlusturmaTarih);
        ResultSet rs = pstmt.executeQuery();
        int sonuc = 0;
        if (rs.next()) {
            sonuc = rs.getInt("BlogId");
        }


        pstmt.close();
        rs.close();
        
        return sonuc;
		
	}

	@Override
	public int girdiIDSiniBul(Timestamp GirdiTarih) throws SQLException {
		String sorgu = "SELECT GirdiId FROM Girdi where GirdiTarih = ?";
        PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(sorgu);
        pstmt.setTimestamp(1, GirdiTarih);
        ResultSet rs = pstmt.executeQuery();
        int sonuc = 0;
        if (rs.next()) {
            sonuc = rs.getInt("GirdiId");
        }


        pstmt.close();
        rs.close();
       
        return sonuc;
		
		
	}

	@Override
	public Kullanici kullaniciBilgisiniGetir(String kullaniciEmail, String kullaniciSifre) throws SQLException {
		String sorgu = "SELECT * FROM Kullanicilar where "
                + "KullaniciEmail = ? and KullaniciSifre = ?";

        PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(sorgu);
        pstmt.setString(1, kullaniciEmail);
        pstmt.setString(2, kullaniciSifre);
        ResultSet rs = pstmt.executeQuery();
        Kullanici k = null;
        if (rs.next()) {
            k = new Kullanici();
            k.setKullaniciId(rs.getInt("KullaniciId"));
            k.setKullaniciEmail(rs.getString("KullaniciEmail"));
            k.setKullaniciSifre(rs.getString("KullaniciSifre"));
            k.setKullaniciAdSoyad(rs.getString("KullaniciAdSoyad"));
            k.setKullaniciIzin(rs.getString("KullaniciIzin"));
            k.setKayitTarih(rs.getTimestamp("KayitTarih"));
        }
        pstmt.close();
        rs.close();
        
        return k;
		
	}

	@Override
	public Kullanici kullaniciBilgisiGetir(String kullaniciEmail) throws SQLException {
		String sorgu = "SELECT * FROM Kullanicilar where KullaniciEmail = ?";
        PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(sorgu);
        pstmt.setString(1, kullaniciEmail);
        ResultSet rs = pstmt.executeQuery();
        Kullanici k = null;
        if (rs.next()) {
            k = new Kullanici();
            k.setKullaniciId(rs.getInt("KullaniciId"));
            k.setKullaniciEmail(rs.getString("KullaniciEmail"));
            k.setKullaniciSifre(rs.getString("KullaniciSifre"));
            k.setKullaniciAdSoyad(rs.getString("KullaniciAdSoyad"));
            k.setKullaniciIzin(rs.getString("KullaniciIzin"));
            k.setKayitTarih(rs.getTimestamp("KayitTarih"));
        }
        pstmt.close();
        rs.close();
       
        return k;
	}

	@Override
	public Kullanici kullaniciBilgisiGetir(int kullaniciId) throws SQLException {
		String sorgu = "SELECT * FROM Kullanicilar where KullaniciId = ?";
        PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(sorgu);
        pstmt.setInt(1, kullaniciId);
        ResultSet rs = pstmt.executeQuery();
        Kullanici k = null;
        if (rs.next()) {
            k = new Kullanici();
            k.setKullaniciId(rs.getInt("KullaniciId"));
            k.setKullaniciEmail(rs.getString("KullaniciEmail"));
            k.setKullaniciSifre(rs.getString("KullaniciSifre"));
            k.setKullaniciAdSoyad(rs.getString("KullaniciAdSoyad"));
            k.setKullaniciIzin(rs.getString("KullaniciIzin"));
            k.setKayitTarih(rs.getTimestamp("KayitTarih"));
        }
        pstmt.close();
        rs.close();
       
        return k;
	}

	@Override
	public List<Kullanici> tumKullanicilariGetir() throws SQLException {
		String sorgu = "SELECT * FROM Kullanicilar";
        PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(sorgu);
        ResultSet rs = pstmt.executeQuery();
        List<Kullanici> kullaniciList = null;
        if (rs.next()) {
            kullaniciList = new ArrayList<Kullanici>();
        }
        rs.beforeFirst();
        while (rs.next()) {
            Kullanici k = new Kullanici();
            k.setKullaniciId(rs.getInt("KullaniciId"));
            k.setKullaniciEmail(rs.getString("KullaniciEmail"));
            k.setKullaniciSifre(rs.getString("KullaniciSifre"));
            k.setKullaniciAdSoyad(rs.getString("KullaniciAdSoyad"));
            k.setKullaniciIzin(rs.getString("KullaniciIzin"));
            k.setKayitTarih(rs.getTimestamp("KayitTarih"));
            kullaniciList.add(k);
        }
        pstmt.close();
        rs.close();
      
        return kullaniciList;
	}

	@Override
	public Blog blogBilgisiGetir(int blogId) throws SQLException {
		String sorgu = "SELECT * FROM Blog where BlogId = ?";
        PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(sorgu);
        pstmt.setInt(1, blogId);
        ResultSet rs = pstmt.executeQuery();
        Blog b = null;
        if (rs.next()) {
            b = new Blog();
            b.setBlogId(rs.getInt("BlogId"));
            b.setKullaniciId(rs.getInt("KullaniciId"));
            b.setBlogBaslik(rs.getString("BlogBaslik"));
            b.setAciklama(rs.getString("Aciklama"));
            b.setOlusturmaTarih(rs.getTimestamp("OlusturmaTarih"));
        }
        pstmt.close();
        rs.close();
     
        return b;
		
	}

	@Override
	public Yorum yorumBilgisiniGetir(int yorumId) throws SQLException {
		String sorgu = "SELECT * FROM Yorum where YorumId = ?";
        PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(sorgu);
        pstmt.setInt(1, yorumId);
        ResultSet rs = pstmt.executeQuery();
        Yorum y = null;
        if (rs.next()) {
            y= new Yorum();
            y.setYorumId(rs.getInt("YorumId"));
            y.setGirdiId(rs.getInt("GirdiId"));
            y.setKullaniciId(rs.getInt("KullaniciId"));
            y.setYorumBaslik(rs.getString("YorumBaslik"));
            y.setYorumIcerik(rs.getString("YorumIcerik"));
            y.setYorumTarih(rs.getTimestamp("YorumTarih"));
        }
        pstmt.close();
        rs.close();
        
        return y;
	}

	@Override
	public int[] blogIstatistikGetir(int blogID) throws SQLException {
		int[] res = new int[2];
        String sorgu = "SELECT count(*) FROM Girdi where BlogId = ?";
        PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(sorgu);
        pstmt.setInt(1, blogID);
        ResultSet rs = pstmt.executeQuery();
        if (rs.next()) {
            res[0] = rs.getInt(1);
        }

        sorgu = "select count(*) from Yorum y WHERE "
                + "y.GirdiId in"
                + "(Select g.GirdiId FROM Girdi g where g.BlogId = ? )";

        pstmt = (PreparedStatement) connection.prepareStatement(sorgu);
        pstmt.setInt(1, blogID);
        rs = pstmt.executeQuery();
        if (rs.next()) {
            res[1] = rs.getInt(1);
        }

        pstmt.close();
        rs.close();
      
        return res;
	}

	@Override
	public List<Blog> tumBloglariGetir() throws SQLException {
		String sorgu = "SELECT * FROM Blog";
        PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(sorgu);
        ResultSet rs = pstmt.executeQuery();
        List<Blog> blogList = null;
        if (rs.next()) {
            blogList = new ArrayList<Blog>();
        }
        rs.beforeFirst();
        while (rs.next()) {
            Blog b = new Blog();
            b.setBlogId(rs.getInt("BlogId"));
            b.setKullaniciId(rs.getInt("KullaniciId"));
            b.setBlogBaslik(rs.getString("BlogBaslik"));
            b.setAciklama(rs.getString("Aciklama"));
            b.setOlusturmaTarih(rs.getTimestamp("OlusturmaTarih"));
            blogList.add(b);
        }
        pstmt.close();
        rs.close();
        return blogList;
	}

	@Override
	public List<Blog> birKullaniciyaAitTumBloglariGetir(Kullanici k) throws SQLException {
		String sorgu = "SELECT * FROM Blog WHERE KullaniciId = ?";
        PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(sorgu);
        pstmt.setInt(1, k.getKullaniciId());
        ResultSet rs = pstmt.executeQuery();
        List<Blog> blogList = null;
        if (rs.next()) {
            blogList = new ArrayList<Blog>();
        }
        rs.beforeFirst();
        while (rs.next()) {
            Blog b = new Blog();
            b.setBlogId(rs.getInt("BlogId"));
            b.setKullaniciId(rs.getInt("KullaniciId"));
            b.setBlogBaslik(rs.getString("BlogBaslik"));
            b.setAciklama(rs.getString("Aciklama"));
            b.setOlusturmaTarih(rs.getTimestamp("OlusturmaTarih"));
            blogList.add(b);
        }
        pstmt.close();
        rs.close();
      
        return blogList;

	}

	@Override
	public int kullanicininSahipOlduguBloglarinSayisiniGetir(Kullanici k) throws SQLException {
		if(k == null)
            return 0;
       
       String sorgu = "SELECT COUNT(*) FROM Blog WHERE KullaniciId = ?";
       PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(sorgu);
       pstmt.setInt(1, k.getKullaniciId());
       ResultSet rs = pstmt.executeQuery();
       int sonuc = 0;
       if (rs.next()) {
           sonuc = rs.getInt(1);
       }
       
       return sonuc;
	}

	@Override
	public List<Girdi> birBlogaAitTumGirdileriGetir(Blog b) throws SQLException {
		String sorgu = "SELECT * FROM Girdi WHERE BlogId = ?";
        PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(sorgu);
        pstmt.setInt(1, b.getBlogId());
        ResultSet rs = pstmt.executeQuery();
        List<Girdi> girdiList = null;
        if (rs.next()) {
            girdiList = new ArrayList<Girdi>();
        }
        rs.beforeFirst();

        while (rs.next()) {
            Girdi g = new Girdi();
            g.setGirdiId(rs.getInt("GirdiId"));
            g.setBlogId(rs.getInt("BlogId"));
            g.setKullaniciId(rs.getInt("KullaniciId"));
            g.setGirdiBaslik(rs.getString("GirdiBaslik"));
            g.setGirdiIcerik(rs.getString("GirdiIcerik"));
            g.setGirdiTarih(rs.getTimestamp("GirdiTarih"));
            girdiList.add(g);
        }
        pstmt.close();
        rs.close();
        return girdiList;
	}

	@Override
	public List<Yorum> birGirdiyeAitTumYorumlariGetir(Girdi g) throws SQLException {
		String sorgu = "SELECT * FROM Yorum WHERE GirdiId = ?";
        PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(sorgu);
        pstmt.setInt(1, g.getGirdiId());
        ResultSet rs = pstmt.executeQuery();
        List<Yorum> yorumList = null;
        if (rs.next()) {
            yorumList = new ArrayList<Yorum>();
        }
        rs.beforeFirst();

        while (rs.next()) {
            Yorum y = new Yorum();
            y.setYorumId(rs.getInt("YorumId"));
            y.setGirdiId(rs.getInt("GirdiId"));
            y.setKullaniciId(rs.getInt("KullaniciId"));
            y.setYorumBaslik(rs.getString("YorumBaslik"));
            y.setYorumIcerik(rs.getString("YorumIcerik"));
            y.setYorumTarih(rs.getTimestamp("YorumTarih"));
            yorumList.add(y);
        }
        pstmt.close();
        rs.close();
        return yorumList;
	}

	@Override
	public List<Yorum> birGirdiyeAitTumYorumlariGetir(int girdiId) throws SQLException {
		String sorgu = "SELECT * FROM Yorum WHERE GirdiId = ?";
        PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(sorgu);
        pstmt.setInt(1, girdiId);
        ResultSet rs = pstmt.executeQuery();
        List<Yorum> yorumList = null;
        if (rs.next()) {
            yorumList = new ArrayList<Yorum>();
        }
        rs.beforeFirst();

        while (rs.next()) {
            Yorum y = new Yorum();
            y.setYorumId(rs.getInt("YorumId"));
            y.setGirdiId(rs.getInt("GirdiId"));
            y.setKullaniciId(rs.getInt("KullaniciId"));
            y.setYorumBaslik(rs.getString("YorumBaslik"));
            y.setYorumIcerik(rs.getString("YorumIcerik"));
            y.setYorumTarih(rs.getTimestamp("YorumTarih"));
            yorumList.add(y);
        }
        pstmt.close();
        rs.close();
        return yorumList;
	}

	@Override
	public List<YorumDetay> birGirdiyeAitEnGuncelUcYorumDetayiniGetir(int girdiId) throws SQLException {
		String sorgu = "SELECT * FROM Yorum WHERE GirdiId = ? " +
                "ORDER BY YorumTarih DESC LIMIT 3";
        PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(sorgu);
        pstmt.setInt(1, girdiId);
        ResultSet rs = pstmt.executeQuery();
        List<YorumDetay> yorumList = null;
        if (rs.next()) {
            yorumList = new ArrayList<YorumDetay>();
        }
        rs.beforeFirst();

        while (rs.next()) {
            YorumDetay y = new YorumDetay();
            y.setYorumId(rs.getInt("YorumId"));
            y.setGirdiId(rs.getInt("GirdiId"));
            y.setKullaniciId(rs.getInt("KullaniciId"));
            y.setYorumBaslik(rs.getString("YorumBaslik"));
            y.setYorumIcerik(rs.getString("YorumIcerik"));
            y.setYorumTarih(rs.getTimestamp("YorumTarih"));
            y.setKullanici(kullaniciBilgisiGetir(y.getKullaniciId()));
            yorumList.add(y);
        }
        pstmt.close();
        rs.close();
        return yorumList;

	}

	@Override
	public List<YorumDetay> birGirdiyeAitTumYorumDetaylariniGetir(int girdiId) throws SQLException {
		String sorgu = "SELECT * FROM Yorum WHERE GirdiId = ? " +
                "ORDER BY YorumTarih DESC LIMIT 3";
        PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(sorgu);
        pstmt.setInt(1, girdiId);
        ResultSet rs = pstmt.executeQuery();
        List<YorumDetay> yorumList = null;
        if (rs.next()) {
            yorumList = new ArrayList<YorumDetay>();
        }
        rs.beforeFirst();

        while (rs.next()) {
            YorumDetay y = new YorumDetay();
            y.setYorumId(rs.getInt("YorumId"));
            y.setGirdiId(rs.getInt("GirdiId"));
            y.setKullaniciId(rs.getInt("KullaniciId"));
            y.setYorumBaslik(rs.getString("YorumBaslik"));
            y.setYorumIcerik(rs.getString("YorumIcerik"));
            y.setYorumTarih(rs.getTimestamp("YorumTarih"));
            y.setKullanici(kullaniciBilgisiGetir(y.getKullaniciId()));
            yorumList.add(y);
        }
        pstmt.close();
        rs.close();
        return yorumList;

	}

	@Override
	public boolean yorumuSil(Yorum y) throws SQLException {
		String sorgu = "DELETE FROM Yorum WHERE YorumId = ?";
        PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(sorgu);
        pstmt = (PreparedStatement) connection.prepareStatement(sorgu);
        pstmt.setInt(1, y.getYorumId());
        boolean sonuc = (pstmt.executeUpdate() > 0);
        pstmt.close();
        return sonuc;
	}

	@Override
	public boolean girdiyiSil(Girdi g) throws SQLException {
		String sorgu = "DELETE FROM Girdi WHERE GirdiId = ?";
        PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(sorgu);
        pstmt = (PreparedStatement) connection.prepareStatement(sorgu);
        pstmt.setInt(1, g.getGirdiId());
        boolean sonuc = (pstmt.executeUpdate() > 0);
        pstmt.close();
        return sonuc;
	}

	@Override
	public boolean bloguSil(Blog b) throws SQLException {
		String sorgu = "DELETE FROM Blog WHERE BlogId = ?";
        PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(sorgu);
        pstmt = (PreparedStatement) connection.prepareStatement(sorgu);
        pstmt.setInt(1, b.getBlogId());
        boolean sonuc = (pstmt.executeUpdate() > 0);
        pstmt.close();
        
        return sonuc;
	}

	@Override
	public boolean kullaniciyiSil(Kullanici k) throws SQLException {
		 String sorgu = "DELETE FROM Kullanicilar WHERE KullaniciId = ?";
	     PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(sorgu);
	     pstmt = (PreparedStatement) connection.prepareStatement(sorgu);
	     pstmt.setInt(1, k.getKullaniciId());
	     boolean sonuc = (pstmt.executeUpdate() > 0);
	     pstmt.close();
	 
	     return sonuc;
	}

	@Override
	public BlogDetay blogDetayGetir(int blogId) throws SQLException {
		Blog b = blogBilgisiGetir(blogId);
        BlogDetay blogDetay = new BlogDetay(b);
        Kullanici k = kullaniciBilgisiGetir(b.getKullaniciId());
        blogDetay.setKullanici(k);
        int[] istatistik = blogIstatistikGetir(b.getBlogId());
        blogDetay.setGirdiSayisi(istatistik[0]);
        blogDetay.setYorumSayisi(istatistik[1]);
 
        return blogDetay;
	}

	@Override
	public GirdiDetay enGuncelGirdiyiGetir(int blogId) throws SQLException {
		String sorgu = "SELECT * FROM Girdi WHERE BlogId=? ORDER BY GirdiTarih DESC LIMIT 1";
        GirdiDetay g = new GirdiDetay();
        PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(sorgu);
        pstmt = (PreparedStatement) connection.prepareStatement(sorgu);
        pstmt.setInt(1, blogId);
        ResultSet rs = pstmt.executeQuery();
        if (!rs.next()) {
            return null;
        }
        g.setGirdiId(rs.getInt("GirdiId"));
        g.setBlogId(rs.getInt("BlogId"));
        g.setKullaniciId(rs.getInt("KullaniciId"));
        g.setGirdiBaslik(rs.getString("GirdiBaslik"));
        g.setGirdiIcerik(rs.getString("GirdiIcerik"));
        g.setGirdiTarih(rs.getTimestamp("GirdiTarih"));
        g.setYorumlar(birGirdiyeAitEnGuncelUcYorumDetayiniGetir(g.getGirdiId()));
        g.setYorumSayisi(birGirdiyeAitYorumSayisiniGetir(g.getGirdiId()));
     
        return g;
	}

	@Override
	public GirdiDetay seciliGirdininDetayiniGetir(int girdiId) throws SQLException {
		String sorgu = "SELECT * FROM Girdi WHERE GirdiId=? ORDER BY GirdiTarih DESC LIMIT 1";
        GirdiDetay g = new GirdiDetay();
        PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(sorgu);
        pstmt = (PreparedStatement) connection.prepareStatement(sorgu);
        pstmt.setInt(1, girdiId);
        ResultSet rs = pstmt.executeQuery();
        if (!rs.next()) {
            return null;
        }
        g.setGirdiId(rs.getInt("GirdiId"));
        g.setBlogId(rs.getInt("BlogId"));
        g.setKullaniciId(rs.getInt("KullaniciId"));
        g.setGirdiBaslik(rs.getString("GirdiBaslik"));
        g.setGirdiIcerik(rs.getString("GirdiIcerik"));
        g.setGirdiTarih(rs.getTimestamp("GirdiTarih"));
        g.setYorumlar(birGirdiyeAitEnGuncelUcYorumDetayiniGetir(g.getGirdiId()));
        g.setYorumSayisi(birGirdiyeAitYorumSayisiniGetir(g.getGirdiId()));
     
        return g;
	}

	@Override
	public List<Girdi> enGuncelBesGirdiyiGetir(int blogId) throws SQLException {
		String sorgu = "SELECT * FROM Girdi WHERE BlogId=? ORDER BY GirdiTarih DESC LIMIT 5";

        PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(sorgu);
        pstmt = (PreparedStatement) connection.prepareStatement(sorgu);
        pstmt.setInt(1, blogId);
        ResultSet rs = pstmt.executeQuery();
        if (!rs.next()) {
            return null;
        }
        List<Girdi> girdiList = new ArrayList<Girdi>();
        rs.beforeFirst();
        while (rs.next()) {
            Girdi g = new Girdi();
            g.setGirdiId(rs.getInt("GirdiId"));
            g.setBlogId(rs.getInt("BlogId"));
            g.setKullaniciId(rs.getInt("KullaniciId"));
            g.setGirdiBaslik(rs.getString("GirdiBaslik"));
            g.setGirdiIcerik(rs.getString("GirdiIcerik"));
            g.setGirdiTarih(rs.getTimestamp("GirdiTarih"));

            girdiList.add(g);
        }
      
        return girdiList;
	}

	@Override
	public List<Girdi> tumGirdileriGetir(int blogId) throws SQLException {
		String sorgu = "SELECT * FROM Girdi WHERE BlogId=? ORDER BY GirdiTarih DESC LIMIT 5";

        PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(sorgu);
        pstmt = (PreparedStatement) connection.prepareStatement(sorgu);
        pstmt.setInt(1, blogId);
        ResultSet rs = pstmt.executeQuery();
        if (!rs.next()) {
            return null;
        }
        List<Girdi> girdiList = new ArrayList<Girdi>();
        rs.beforeFirst();
        while (rs.next()) {
            Girdi g = new Girdi();
            g.setGirdiId(rs.getInt("GirdiId"));
            g.setBlogId(rs.getInt("BlogId"));
            g.setKullaniciId(rs.getInt("KullaniciId"));
            g.setGirdiBaslik(rs.getString("GirdiBaslik"));
            g.setGirdiIcerik(rs.getString("GirdiIcerik"));
            g.setGirdiTarih(rs.getTimestamp("GirdiTarih"));

            girdiList.add(g);
        }
  
        return girdiList;
	}

	@Override
	public boolean blogBilgisiGuncelle(Blog b) throws SQLException {
		String sorgu = "UPDATE Blog SET BlogBaslik = ?, "
                + "Aciklama = ?, KullaniciId = ? WHERE BlogId = ?";
        PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(sorgu);
        pstmt.setString(1, b.getBlogBaslik());
        pstmt.setString(2, b.getAciklama());
        pstmt.setInt(3, b.getKullaniciId());
        pstmt.setInt(4, b.getBlogId());
        int sonuc = pstmt.executeUpdate();
        pstmt.close();

        return (sonuc > 0);
	}

	@Override
	public boolean kullaniciBilgisiGuncelle(Kullanici k) throws SQLException {
		 String sorgu = "UPDATE Kullanicilar SET KullaniciEmail = ?, "
	                + "KullaniciSifre = ?, KullaniciAdSoyad = ?, "
	                + "KullaniciIzin = ? WHERE KullaniciId = ?";
	     PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(sorgu);
	     pstmt.setString(1, k.getKullaniciEmail());
	     pstmt.setString(2, k.getKullaniciSifre());
	     pstmt.setString(3, k.getKullaniciAdSoyad());
	     pstmt.setString(4, k.getKullaniciIzin());
	     pstmt.setInt(5, k.getKullaniciId());
	     int sonuc = pstmt.executeUpdate();
	     pstmt.close();

	     return (sonuc > 0);
	}

	@Override
	public boolean girdiBilgisiGuncelle(Girdi g) throws SQLException {
		 String sorgu = "UPDATE Girdi SET GirdiBaslik = ?, "
	                + "GirdiIcerik = ?, GirdiTarih = ? "
	                + " WHERE GirdiId = ?";
		 PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(sorgu);
	        
		 pstmt.setString(1, g.getGirdiBaslik());
	     pstmt.setString(2, g.getGirdiIcerik());
	     pstmt.setTimestamp(3, Araclar.yeniTimeStampOlustur());
	     pstmt.setInt(4, g.getGirdiId());
	     int sonuc = pstmt.executeUpdate();
	     pstmt.close();

	     return (sonuc > 0);
	}
	@Override
	public boolean yorumBilgisiGuncelle(Yorum y) throws SQLException {
		String sorgu = "UPDATE Yorum SET YorumBaslik = ?, "
                + "YorumIcerik = ?, YorumTarih = ? "
                + " WHERE YorumId = ?";
        PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(sorgu);

        pstmt.setString(1, y.getYorumBaslik());
        pstmt.setString(2, y.getYorumIcerik());
        pstmt.setTimestamp(3, Araclar.yeniTimeStampOlustur());
        pstmt.setInt(4, y.getYorumId());
        int sonuc = pstmt.executeUpdate();
        pstmt.close();

        return (sonuc > 0);

	}

	@Override
	public int birGirdiyeAitYorumSayisiniGetir(int girdiId) throws SQLException {
		int sonuc = 0;
	    String sorgu = "SELECT count(*) FROM Yorum where GirdiId = ?";
	    PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(sorgu);
	    pstmt.setInt(1, girdiId);
	    ResultSet rs = pstmt.executeQuery();
	    if (rs.next()) {
           sonuc = rs.getInt(1);
        }

	    return sonuc;
	}

	@Override
	public String[] tabloKolonAdlariniGetir(String tabloAdi) throws SQLException {
		String sorgu = "SELECT * FROM " + tabloAdi;
        PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(sorgu);
        ResultSetMetaData metaData = pstmt.getMetaData();

        int sayi = metaData.getColumnCount();
        String[] sonuc = new String[sayi];
        for (int i = 0; i < sayi; i++) {
            sonuc[i] = metaData.getColumnName(i + 1);
        }
        return sonuc;
	}

	
}
