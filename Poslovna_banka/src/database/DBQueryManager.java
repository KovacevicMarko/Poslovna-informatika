package database;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
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
import modelFromXsd.NalogZaPlacanje;
import xml.XmlManager;

public class DBQueryManager {

	public static void importNalog(NalogZaPlacanje nalog, boolean zaUkidanje) {

		Connection conn = DBConnection.getDatabaseWrapper().getConnection();
		
		
		String query = createQueryForImportNalog();
		//PreparedStatement uplataStmt = null;
		CallableStatement uplataStmt = null;
		
		GenericDialogActions action = new GenericDialogActions(null);
		java.sql.Date datumPrijema =  action.returnSqlDate(nalog.getDatumPrijema().toString().replace('-', '/'));
		java.sql.Date datumValute =  action.returnSqlDate(nalog.getDatumValute().toString().replace('-', '/'));
		
		try {

			uplataStmt = conn.prepareCall("{call Uplata (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
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
			int res = uplataStmt.executeUpdate();
			System.out.println(res);
			int tipgreske = uplataStmt.getInt(18);
			uplataStmt.close();
			conn.commit();
			System.out.println(res);

		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
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

	private static String createQueryForImportNalog() {
		
		StringBuilder query = new StringBuilder();

		query.append("EXECUTE Uplata ");
		query.append("@duznik = ?,");
		query.append("@svrhaPlacanja = ?,");
		query.append("@primalac = ?,");
		query.append("@datumPrijema = ?,");
		query.append("@mestoPrijema = ?,");
		query.append("@datumValute = ?,");
		query.append("@racunDuznika = ?,");
		query.append("@modelZaduzenja = ?,");
		query.append("@pozivNaBrojZaduzenja = ?,");
		query.append("@racunPrimaoca = ?,");
		query.append("@modelOdobrenja = ?,");
		query.append("@pozivNaBrojOdobrenja = ?,");
		query.append("@sifraPlacanja = ?,");
		query.append("@iznos = ?,");
		query.append("@oznakaValute = ?,");
		query.append("@hitno = ?,");
		query.append("@tipGreske = ? output");
		
		return query.toString();
	}
	
	public static void revokeBill(Table table){
		
		int selectedRow = table.getSelectedRow();
		
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
			IzvodStanja izvod = findDnevnoStanje(racun);
			izvod.setKlijentId(klijentId);
			izvodiStanja.add(izvod);
		}
		
		
		return izvodiStanja;
	}
	
	private static IzvodStanja findDnevnoStanje(String racun){
		
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
		}
		catch (SQLException e) {
			// TODO: handle exception
			JOptionPane.showMessageDialog(null, SqlExceptionHandler.getHandledMessage("", e.getMessage().toString()));
		}
		finally{
			return izvod;
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
}
