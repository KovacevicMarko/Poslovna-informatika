package database;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import actions.main.form.GenericDialogActions;
import modelFromXsd.NalogZaPlacanje;

public class DBQueryManager {

	public static void importNalog(NalogZaPlacanje nalog) {

		Connection conn = DBConnection.getDatabaseWrapper().getConnection();
		
		
		String query = createQueryForImportNalog();
		PreparedStatement uplataStmt = null;
		
		GenericDialogActions action = new GenericDialogActions(null);
		java.sql.Date datumPrijema =  action.returnSqlDate(nalog.getDatumPrijema().toString());
		java.sql.Date datumValute =  action.returnSqlDate(nalog.getDatumValute().toString());
		
		try {
			
			uplataStmt = conn.prepareStatement(query);

			uplataStmt = conn.prepareStatement(query);
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
			uplataStmt.setBigDecimal(13,nalog.getIznos());
			uplataStmt.setString(14, nalog.getOznakaValute());
			uplataStmt.setBoolean(15, nalog.isHitno());
			
			int res = uplataStmt.executeUpdate();
			System.out.println(res);
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
		query.append("@iznos = ?,");
		query.append("@oznakaValute = ?,");
		query.append("@hitno = ?");

		return query.toString();
	}
	
}
