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
	@sifraPlacanja char(3),
	@sifraValute char(3),
	@iznos decimal(15,2),
	@oznakaValute char(3),
	@hitno bit,
	@tipGreske numeric(1) output
as
begin

	declare @racunDuznikaId varchar(18);
	declare @racunPrimaocaId varchar(18);
	declare @dsrId varchar(18);
	declare @naseljenoMestoId varchar(18);

	set @racunDuznikaId = ( select rac.KL_ID
	from dbo.RACUNI rac
	where rac.BAR_RACUN = @racunDuznika);

	
	set @racunPrimaocaId = (
	select rac.KL_ID
	from dbo.Racuni rac
	where rac.BAR_RACUN = @racunPrimaoca);

	set @naseljenoMestoId = (
	select nm.NM_SIFRA
	from NASELJENO_MESTO nm
	where nm.NM_NAZIV = @mestoPrijema);

	if (@racunDuznikaId is not null) and (@racunPrimaocaId is not null)
	begin
		-------------------------------- ZA DUZNIKA ---------------------------
		set @dsrId = (
			select dsr.DSR_IZVOD
			from DNEVNO_STANJE_RACUNA dsr
			where dsr.DSR_DATUM = @datumPrijema AND dsr.BAR_RACUN= @racunDuznikaId);
		

		-- ne postoji dnevno stanje racuna duznika za datum prijema, treba napraviti novo
		if @dsrId is null
		begin
			declare @poslednjeStanje decimal(15,2);
			
			set @poslednjeStanje = (
			select top 1 dsr.DSR_NOVOSTANJE
			from DNEVNO_STANJE_RACUNA dsr
			where dsr.BAR_RACUN = @racunDuznikaId
			order by dsr.DSR_DATUM desc);

			if @poslednjeStanje is null
			begin
				set @poslednjeStanje = 0;
			end
			
			if (@poslednjeStanje >= @iznos)
			begin
				set @dsrId = 0;
				insert into DNEVNO_STANJE_RACUNA(DSR_IZVOD,
							DSR_DATUM,
							DSR_PRETHODNO,
							DSR_UKORIST,
							DSR_NATERET,
							DSR_NOVOSTANJE,
							BAR_RACUN)
				values (@dsrId, @datumPrijema, @poslednjeStanje, 0, @iznos, @poslednjeStanje-@iznos, @racunDuznika);
			end
			-- nedovoljno novca na racunu, NEUSPESNA TRANSAKCIJA
			else
			begin
				set @tipGreske = 2;
			end
		end
		-- inace updejtovati stanje za danasnji datum
		else
		begin
			update DNEVNO_STANJE_RACUNA
			set DSR_PRETHODNO = DSR_NOVOSTANJE,
				DSR_NOVOSTANJE = DSR_NOVOSTANJE - @iznos,
				DSR_NATERET = DSR_NATERET + @iznos
			where DSR_IZVOD = @dsrId;
		end

		insert into ANALITIKA_IZVODA (BAR_RACUN,
									DSR_IZVOD,
									ASI_BROJSTAVKE,
									NM_SIFRA,
									VPL_OZNAKA,
									VA_IFRA,
									ASI_DUZNIK,
									ASI_SVRHA,
									ASI_POVERILAC,
									ASI_DATPRI,
									ASI_DATVAL,
									ASI_RACDUZ,
									ASI_MODZAD,
									ASI_PBZAD,
									ASI_RACPOV,
									ASI_MODODOB,
									ASI_PBODO,
									ASI_HITNO,
									ASI_IZNOS,
									ASI_TIPGRESKE,
									ASI_STATUS)
		values (@racunDuznikaId, @dsrId, (select isnull(max(ASI_BROJSTAVKE),0)
										from ANALITIKA_IZVODA ai
										where ai.BAR_RACUN = @racunDuznikaId and ai.DSR_IZVOD = @dsrId) + 1,
				@naseljenoMestoId,@sifraPlacanja, @sifraValute,@duznik,@svrhaPlacanja,
				@primalac,@datumPrijema,@datumValute,@racunDuznika,@modelZaduzenja,@pozivNaBrojZaduzenja,@racunPrimaoca,
				@modelOdobrenja,@pozivNaBrojOdobrenja,@hitno,@iznos,@tipgreske,'E');
		


		-------------- KRAJ ZA DUZNIKA -------------------------------------------


		---------------------------- ZA PRIMAOCA -------------------------------------
		set @dsrId = (
			select dsr.DSR_IZVOD
			from DNEVNO_STANJE_RACUNA dsr
			where dsr.DSR_DATUM = @datumPrijema AND dsr.BAR_RACUN= @racunPrimaocaId);
		

		-- ne postoji dnevno stanje racuna duznika za datum prijema, treba napraviti novo
		if @dsrId is null
		begin
			--declare @poslednjeStanje decimal(15,2);
			
			set @poslednjeStanje = (
			select top 1 dsr.DSR_NOVOSTANJE
			from DNEVNO_STANJE_RACUNA dsr
			where dsr.BAR_RACUN = @racunPrimaocaId
			order by dsr.DSR_DATUM desc);

			if @poslednjeStanje is null
			begin
				set @poslednjeStanje = 0;
			end
			
			set @dsrId = 0;
			insert into DNEVNO_STANJE_RACUNA(DSR_IZVOD,
						DSR_DATUM,
						DSR_PRETHODNO,
						DSR_UKORIST,
						DSR_NATERET,
						DSR_NOVOSTANJE,
						BAR_RACUN)
			values (@dsrId, @datumPrijema, @poslednjeStanje, @iznos,0, @poslednjeStanje+@iznos, @racunPrimaocaId);
		end
		-- inace updejtovati stanje za danasnji datum
		else
		begin
			update DNEVNO_STANJE_RACUNA
			set DSR_PRETHODNO = DSR_NOVOSTANJE,
				DSR_NOVOSTANJE = DSR_NOVOSTANJE + @iznos,
				DSR_UKORIST= DSR_UKORIST + @iznos
			where DSR_IZVOD = @dsrId;
		end

		insert into ANALITIKA_IZVODA (BAR_RACUN,
									DSR_IZVOD,
									ASI_BROJSTAVKE,
									NM_SIFRA,
									VPL_OZNAKA,
									VA_IFRA,
									ASI_DUZNIK,
									ASI_SVRHA,
									ASI_POVERILAC,
									ASI_DATPRI,
									ASI_DATVAL,
									ASI_RACDUZ,
									ASI_MODZAD,
									ASI_PBZAD,
									ASI_RACPOV,
									ASI_MODODOB,
									ASI_PBODO,
									ASI_HITNO,
									ASI_IZNOS,
									ASI_TIPGRESKE,
									ASI_STATUS)
		values (@racunDuznikaId, @dsrId, (select isnull(max(ASI_BROJSTAVKE),0)
										from ANALITIKA_IZVODA ai
										where ai.BAR_RACUN = @racunDuznikaId and ai.DSR_IZVOD = @dsrId) + 1,
				@naseljenoMestoId,@sifraPlacanja, @sifraValute,@duznik,@svrhaPlacanja,
				@primalac,@datumPrijema,@datumValute,@racunDuznika,@modelZaduzenja,@pozivNaBrojZaduzenja,@racunPrimaoca,
				@modelOdobrenja,@pozivNaBrojOdobrenja,@hitno,@iznos,@tipgreske,'E');
		------------------------- KRAJ ZA PRIMAOCA -----------------------------------
	end

	-- ne postoji racun duznika ili racun primaoca, NEUSPESNA TRANSAKCIJA
	else
	begin
		set @tipGreske = 8;
	end
	

end
