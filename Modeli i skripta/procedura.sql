if object_id ('Uplata') is not null
	drop procedure Uplata; 
go
create procedure Uplata
	@duznik varchar(256),
	@svrhaPlacanja varchar(256),
	@primalac varchar(256),
	@datumPrijema datetime,
	@mestoPrijema varchar(100),
	@datumValute datetime,
	@racunDuznika varchar(18),
	@modelZaduzenja numeric(2),
	@pozivNaBrojZaduzenja varchar(20),
	@racunPrimaoca varchar(18),
	@modelOdobrenja numeric(2),
	@pozivNaBrojOdobrenja varchar(20),
	@iznos decimal(15,2),
	@oznakaValute char(3),
	@hitno bit
as
begin

	declare @racunDuznikaId varchar(18);
	declare @racunPoveriocaId varchar(18);
	declare @dsrId varchar(18);
	declare @naseljenoMestoId varchar(18);

	set @racunDuznikaId = ( select KL_ID
	from dbo.RACUNI rac
	where rac.BAR_RACUN = @racunDuznika);

	
	set @racunPoveriocaId = (
	select KL_ID
	from dbo.Racuni rac
	where rac.BAR_RACUN = @racunPrimaoca);

	set @naseljenoMestoId = (
	select nm.NM_SIFRA
	from NASELJENO_MESTO nm
	where nm.NM_NAZIV = @mestoPrijema);



	if @racunDuznikaId is not null
	begin
		
		set @dsrId = (
			select dsr.DSR_IZVOD
			from DNEVNO_STANJE_RACUNA dsr
			where dsr.DSR_DATUM = @datumPrijema AND dsr.BAR_RACUN= @racunDuznikaId);

		if @dsrId is null
		begin
			declare @novoPrethodnoStanje decimal(15,2);
			
			set @novoPrethodnoStanje = (
			select top 1 dsr.DSR_NOVOSTANJE
			from DNEVNO_STANJE_RACUNA dsr
			where dsr.BAR_RACUN = @racunDuznikaId
			order by dsr.DSR_DATUM desc);
			
			if @novoPrethodnoStanje is null
			begin
				set @novoPrethodnoStanje = 0;
			end
			

			insert into DNEVNO_STANJE_RACUNA(DSR_IZVOD,
							DSR_DATUM,
							DSR_PRETHODNO,
							DSR_UKORIST,
							DSR_NATERET,
							DSR_NOVOSTANJE,
							BAR_RACUN)
			values (0, getdate(), @novoPrethodnoStanje, 0, 0, @novoPrethodnoStanje, @racunDuznika);
		end

		
	end
end
go