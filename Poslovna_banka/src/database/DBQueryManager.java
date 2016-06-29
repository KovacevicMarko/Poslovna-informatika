package database;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.swing.JOptionPane;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import actions.main.form.GenericDialogActions;
import exceptionHandler.SqlExceptionHandler;
import gui.tablemodel.Table;
import modelFromXsd.IzvodStanja;
import modelFromXsd.MT103;
import modelFromXsd.NalogZaPlacanje;
import modelFromXsd.StavkaIzvoda;
import xml.XmlManager;

public class DBQueryManager {

	public static void importNalog(NalogZaPlacanje nalog, boolean zaUkidanje) {

		Connection conn = DBConnection.getDatabaseWrapper().getConnection();
		
		//PreparedStatement uplataStmt = null;
		CallableStatement uplataStmt = null;
		
		GenericDialogActions action = new GenericDialogActions(null);
		java.sql.Date datumPrijema =  action.returnSqlDate(nalog.getDatumPrijema().toString().replace('-', '/'));
		java.sql.Date datumValute =  action.returnSqlDate(nalog.getDatumValute().toString().replace('-', '/'));
		
		try {

			uplataStmt = conn.prepareCall("{call Uplata (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			uplataStmt.setString(1, nalog.getDuznik());
			uplataStmt.setString(2,nalog.getSvrhaPlacanja() );
			uplataStmt.setString(3,nalog.getPrimalac() );
			uplataStmt.setDate(4, datumPrijema);
			uplataStmt.setString(5,nalog.getMestoPrijema());
			uplataStmt.setDate(6,datumValute);
			uplataStmt.setString(7,nalog.getRacunDuznika());
			uplataStmt.setBigDecimal(8,new BigDecimal(nalog.getModelZaduzenja().doubleValue()));
			uplataStmt.setString(9,nalog.getPozivNaBrojZaduzenja());
			uplataStmt.setString(10,nalog.getRacunPrimaoca());
			uplataStmt.setBigDecimal(11,new BigDecimal(nalog.getModelOdobrenja().doubleValue()));
			uplataStmt.setString(12,nalog.getPozivNaBrojOdobrenja());
			uplataStmt.setString(13, nalog.getSifraPlacanja());
			uplataStmt.setBigDecimal(14,nalog.getIznos());
			uplataStmt.setString(15, nalog.getOznakaValute());
			uplataStmt.setBoolean(16, nalog.isHitno());
			uplataStmt.setBoolean(17, zaUkidanje);
			uplataStmt.registerOutParameter(18, java.sql.Types.INTEGER);
			uplataStmt.registerOutParameter(19, java.sql.Types.VARCHAR);
			int res = uplataStmt.executeUpdate();
			System.out.println(res);
			int tipgreske = uplataStmt.getInt(18);
			String IdPorukeRTGS = uplataStmt.getString(19);
			
			uplataStmt.close();
			conn.commit();
			System.out.println(res);
			
			if(tipgreske == 2)
			{
				JOptionPane.showMessageDialog(null, "Nemate dovoljno sredstava na racunu.");
			}
			else if(tipgreske == 8)
			{
				JOptionPane.showMessageDialog(null, "Uneti racuni su ne vazeci ili ne postoje.");
			}
			
			if(!IdPorukeRTGS.equals("")){
				generateAndExportMT103(IdPorukeRTGS);
			}
			
			

		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			JOptionPane.showMessageDialog(null, SqlExceptionHandler.getHandledMessage("ANALITIKA_IZVODA", e1.getMessage()));
		}
		
		finally {
			
			try {
				uplataStmt.close();
				conn.rollback();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
	}

	
	private static void generateAndExportMT103(String IdPorukeRTGS){
		MT103 mt103 = new MT103();
		AnalitikaPkDto analitikaPk = populateMT103FromRTGS(IdPorukeRTGS, mt103);
		populateMT103FromAnalitika(analitikaPk,mt103);
		XmlManager.generateDocumentMT103(mt103);
	}
	
	private static AnalitikaPkDto populateMT103FromRTGS(String idPorukeRTGS,MT103 mt103){
		
		AnalitikaPkDto pk = new AnalitikaPkDto();
		String query = "SELECT * FROM RTGS where RTGS_ID_PORUKE = '"+idPorukeRTGS+"'";
		Connection conn = DBConnection.getDatabaseWrapper().getConnection();
		Statement stmt = null;
		try{
			stmt = conn.createStatement();
			ResultSet rst = stmt.executeQuery(query);
			
			while(rst.next()){
				mt103.setIdPoruke(rst.getString(1));
				pk.setBrojRacuna(rst.getString(2));
				pk.setBrojIzvoda(rst.getBigDecimal(3));
				pk.setBrojStavke(rst.getBigDecimal(4));
				mt103.setSwiftKodBankeDuznika(rst.getString(5));
				mt103.setRacunBankeDuznika(rst.getString(6));
				mt103.setSwiftKodBankePrimaoca(rst.getString(7));
				mt103.setRacunBankePoverioca(rst.getString(8));
			}
			
			rst.close();
			stmt.close();
			
			return pk;
			
		} catch(SQLException e){
			e.printStackTrace();
		}
		finally{
			return pk;
		}
	}
	
	private static void populateMT103FromAnalitika(AnalitikaPkDto pk,MT103 mt103){
		
		String query = "SELECT * FROM ANALITIKA_IZVODA WHERE BAR_RACUN = '"+pk.getBrojRacuna()+
				"' AND DSR_IZVOD = '"+pk.getBrojIzvoda()+"' AND ASI_BROJSTAVKE = '"+
					pk.getBrojStavke()+"'";
		
		Connection conn = DBConnection.getDatabaseWrapper().getConnection();
		Statement stmt = null;
		try{
			stmt = conn.createStatement();
			ResultSet rst = stmt.executeQuery(query);
			
			while(rst.next()){
				mt103.setDuznik(rst.getString("ASI_DUZNIK"));
				mt103.setSvrhaPlacanja(rst.getString("ASI_SVRHA"));
				mt103.setPrimalac(rst.getString("ASI_POVERILAC"));
				mt103.setDatumPrijema(createXMLdate(new Date()));
				mt103.setDatumValute(createXMLdate(new Date()));
				mt103.setRacunDuznika(rst.getString("ASI_RACDUZ"));
				mt103.setRacunPrimaoca(rst.getString("ASI_RACPOV"));
				mt103.setPozivNaBrojZaduzenja(rst.getString("ASI_PBZAD"));
				mt103.setPozivNaBrojOdobrenja(rst.getString("ASI_PBODO"));
				mt103.setModelZaduzenja(rst.getBigDecimal("ASI_MODZAD").toBigInteger());
				mt103.setModelOdobrenja(rst.getBigDecimal("ASI_MODODOB").toBigInteger());
				mt103.setSifraPlacanja(rst.getString("VPL_OZNAKA"));
				mt103.setOznakaValute(rst.getString("VA_IFRA"));
				mt103.setIznos(rst.getBigDecimal("ASI_IZNOS"));
			}
			
			rst.close();
			stmt.close();
		}
		catch(SQLException e){
			e.printStackTrace();
		}
	}
	
	
	
	public static void callKliring(){
		CallableStatement stmt = null;
		try {
			stmt = DBConnection.getDatabaseWrapper().getConnection().prepareCall("{ call KliringProcedura}");
			stmt.executeUpdate();			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			try {
				stmt.close();
				DBConnection.getDatabaseWrapper().getConnection().commit();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
	
	public static void ExportIzvoda(String klijentId){
		
		List<String> racuni =  findRacuniByKlijentId(klijentId);
		List<IzvodStanja> izvodiStanja = generateIzvodi(klijentId, racuni);
		int indik = 0;
		for(IzvodStanja izvod : izvodiStanja)
		{
			if(izvod.getBrojRacuna()==null){
				continue;
			}
			XmlManager.generateDocumentIzvod(izvod);
			indik++;
		}
		if(indik>0){
			JOptionPane.showMessageDialog(null,"Uspesno generisan izvestaj/i!");
		}
		else{
			JOptionPane.showMessageDialog(null,"Nemate uplata/isplata danas!");
		}
		
	}
	
	private static List<String> findRacuniByKlijentId(String klijentId){
		
		List<String> racuni = new ArrayList<String>();
		
		Connection conn = DBConnection.getDatabaseWrapper().getConnection();
		String query = "Select BAR_RACUN from RACUNI Where KL_ID = " + klijentId;
		Statement stmt = null;
		
		try{
			stmt = conn.createStatement();
			
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				racuni.add(rs.getString("BAR_RACUN"));
			}
			
			rs.close();
			stmt.close();
				
		}catch(SQLException e){
			JOptionPane.showMessageDialog(null,SqlExceptionHandler.getHandledMessage("", e.getMessage().toString()));
		}
		finally{
			try{
				stmt.close();
				return racuni;
			}catch(Exception e){
				e.printStackTrace();
				return racuni;
			}
			
		}
		
	}
	
	private static List<IzvodStanja> generateIzvodi(String klijentId,List<String> racuni)
	{
		List<IzvodStanja> izvodiStanja = new ArrayList<IzvodStanja>();
		
		for(String racun : racuni)
		{		
			IzvodStanja izvod = populateIzvodFromDnevnoStanje(racun);
			populateIzvodFromAnalitika(izvod);
			izvod.setKlijentId(klijentId);
			if(izvod.getBrojIzvoda()!=null){
				izvodiStanja.add(izvod);
			}
		}
		
		return izvodiStanja;
	}
	
	private static IzvodStanja populateIzvodFromDnevnoStanje(String racun){
		
		Connection conn = DBConnection.getDatabaseWrapper().getConnection();
		Statement stmt = null;
		
		Date date = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
		String datumString = format.format(date);
		
		GenericDialogActions action = new GenericDialogActions(null);
		java.sql.Date dateSql = action.returnSqlDate(datumString);
		
		
		String query = "Select * from DNEVNO_STANJE_RACUNA Where BAR_RACUN = '"+racun+
				"' AND CAST(DSR_DATUM as DATE) = '"+dateSql+"'";
		IzvodStanja izvod = null;
		
		try
		{
			stmt = conn.createStatement();
			ResultSet rst = stmt.executeQuery(query);
			izvod = new IzvodStanja();
			
			while(rst.next()){
				izvod.setBrojIzvoda(rst.getBigDecimal("DSR_IZVOD").toBigInteger());
				izvod.setBrojRacuna(rst.getString("BAR_RACUN"));
				izvod.setDatumPrometa(createXMLdate(date));
				izvod.setPrethodnoStanje(rst.getBigDecimal("DSR_PRETHODNO"));
				izvod.setPrometUkorist(rst.getBigDecimal("DSR_UKORIST"));
				izvod.setPrometNaTeret(rst.getBigDecimal("DSR_NATERET"));
				izvod.setNovoStanje(rst.getBigDecimal("DSR_NOVOSTANJE"));
				break;
			}
			
			
			rst.close();
			stmt.close();
			
			return izvod;
		}
		catch (SQLException e) {
			// TODO: handle exception
			JOptionPane.showMessageDialog(null, SqlExceptionHandler.getHandledMessage("", e.getMessage().toString()));
		}
		finally{
			return izvod;
		}
	}
	
	private static void populateIzvodFromAnalitika(IzvodStanja izvod){
		
		BigInteger brojIzvoda = izvod.getBrojIzvoda();
		String brojRacuna = izvod.getBrojRacuna();
		
		String query = "SELECT * FROM ANALITIKA_IZVODA WHERE BAR_RACUN = '"+brojRacuna+
				"' AND DSR_IZVOD = '"+brojIzvoda+"'";
		
		Connection conn = DBConnection.getDatabaseWrapper().getConnection();
		Statement stmt = null;
		try{
			stmt = conn.createStatement();
			ResultSet rst = stmt.executeQuery(query);
			
			while(rst.next()){
				StavkaIzvoda stavka = new StavkaIzvoda();
				stavka.setDuznik(rst.getString("ASI_DUZNIK"));
				stavka.setSvrhaPlacanja(rst.getString("ASI_SVRHA"));
				stavka.setPrimalac(rst.getString("ASI_POVERILAC"));
				stavka.setDatumPrijema(createXMLdate(new Date()));
				stavka.setDatumValute(createXMLdate(new Date()));
				stavka.setRacunDuznika(rst.getString("ASI_RACDUZ"));
				stavka.setRacunPrimaoca(rst.getString("ASI_RACPOV"));
				stavka.setPozivNaBrojZaduzenja(rst.getString("ASI_PBZAD"));
				stavka.setPozivNaBrojOdobrenja(rst.getString("ASI_PBODO"));
				stavka.setModelZaduzenja(rst.getBigDecimal("ASI_MODZAD").toBigInteger());
				stavka.setModelOdobrenja(rst.getBigDecimal("ASI_MODODOB").toBigInteger());
				stavka.setOznakaValute(rst.getString("VA_IFRA"));
				stavka.setIznos(rst.getBigDecimal("ASI_IZNOS"));
				setSmerToStavkaIzvoda(stavka, izvod);
				
				izvod.getStavka().add(stavka);
			}
			
			rst.close();
			stmt.close();
			
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}	
	}
	
	private static XMLGregorianCalendar createXMLdate(Date date){
		GregorianCalendar c = new GregorianCalendar();
		c.setTime(date);
		try {
			return DatatypeFactory.newInstance().newXMLGregorianCalendar(c);

		} catch (DatatypeConfigurationException e3) {
			// TODO Auto-generated catch block
			e3.printStackTrace();
			return null;
		}
		
	}
	
	private static void setSmerToStavkaIzvoda(StavkaIzvoda stavka,IzvodStanja izvod)
	{
		if(izvod.getBrojRacuna().equals(stavka.getRacunDuznika())){
			stavka.setSmer("0");
		}
		else if(izvod.getBrojRacuna().equals(stavka.getRacunPrimaoca())){
			stavka.setSmer("1");
		}
		else{
			stavka.setSmer("0");
		}				
	}
	
}
