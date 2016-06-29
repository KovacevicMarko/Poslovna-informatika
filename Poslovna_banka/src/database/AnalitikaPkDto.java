package database;

import java.math.BigDecimal;

public class AnalitikaPkDto {
	
	private String brojRacuna;
	private BigDecimal brojIzvoda;
	private BigDecimal brojStavke;
	
	public AnalitikaPkDto() {
		// TODO Auto-generated constructor stub
	}
	
	

	public AnalitikaPkDto(String brojRacuna, BigDecimal brojIzvoda, BigDecimal brojStavke) {
		this.brojRacuna = brojRacuna;
		this.brojIzvoda = brojIzvoda;
		this.brojStavke = brojStavke;
	}



	public String getBrojRacuna() {
		return brojRacuna;
	}

	public void setBrojRacuna(String brojRacuna) {
		this.brojRacuna = brojRacuna;
	}

	public BigDecimal getBrojIzvoda() {
		return brojIzvoda;
	}

	public void setBrojIzvoda(BigDecimal brojIzvoda) {
		this.brojIzvoda = brojIzvoda;
	}

	public BigDecimal getBrojStavke() {
		return brojStavke;
	}

	public void setBrojStavke(BigDecimal brojStavke) {
		this.brojStavke = brojStavke;
	}
	
	
	
}
