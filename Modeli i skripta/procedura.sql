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
	@iznos decimal(15,2),
	@oznakaValute char(3),
	@hitno bit,
	@zaUkidanje bit,
	@tipGreske integer output
as
begin
	
	declare @swiftDuznika char(8) = '12345678';
	declare @swiftPrimaoca char(8) = '87654321';
	declare @racunDuznikaId varchar(10);
	declare @racunPrimaocaId varchar(10);
	declare @bankaRacunaDuznika char(10);
	declare @bankaRacunaPrimaoca char(10);
	declare @dsrId numeric(3);
	declare @dsrId2 numeric(3); 
	declare @naseljenoMestoId varchar(18);

	set @tipGreske = 1;

	set @racunDuznikaId = ( select rac.KL_ID
	from dbo.RACUNI rac
	where rac.BAR_RACUN = @racunDuznika);

	
	set @racunPrimaocaId = (
	select rac.KL_ID
	from dbo.Racuni rac
	where rac.BAR_RACUN = @racunPrimaoca);

	set @bankaRacunaDuznika = (
		select rac.B_PIB
		from RACUNI rac
		where rac.BAR_RACUN = @racunDuznika);

	set @naseljenoMestoId = (
	select nm.NM_SIFRA
	from NASELJENO_MESTO nm
	where nm.NM_NAZIV = @mestoPrijema);

	set @bankaRacunaPrimaoca = (
		select rac.B_PIB
		from RACUNI rac
		where rac.BAR_RACUN = @racunPrimaoca);

	if (@racunDuznikaId is not null) and (@racunPrimaocaId is not null)
	begin
		-------------------------------- ZA DUZNIKA ---------------------------
		set @dsrId = (
			select dsr.DSR_IZVOD
			from DNEVNO_STANJE_RACUNA dsr
			where dsr.DSR_DATUM = @datumPrijema AND dsr.BAR_RACUN= @racunDuznika);
		

		if @zaUkidanje = 1
		begin
			set @iznos = (select dsr.DSR_NOVOSTANJE
			from DNEVNO_STANJE_RACUNA dsr 
			where dsr.BAR_RACUN = @racunDuznika and dsr.DSR_DATUM=(select max(dsr.DSR_DATUM)
			from DNEVNO_STANJE_RACUNA dsr where dsr.BAR_RACUN = @racunDuznika)) ;
		end

		-- ne postoji dnevno stanje racuna duznika za datum prijema, treba napraviti novo
		if @dsrId is null
		begin
			declare @poslednjeStanje decimal(15,2);
			
			set @poslednjeStanje = (
			select top 1 dsr.DSR_NOVOSTANJE
			from DNEVNO_STANJE_RACUNA dsr
			where dsr.BAR_RACUN = @racunDuznika
			order by dsr.DSR_DATUM desc);

			if @poslednjeStanje is null
			begin
				set @poslednjeStanje = 0;
			end
		
			if (@poslednjeStanje >= @iznos)
			begin
				set @dsrId = (select max(dsr.DSR_IZVOD) from DNEVNO_STANJE_RACUNA dsr)+1;
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
				return;
			end
		end
		-- inace updejtovati stanje za danasnji datum
		else
		begin
			update DNEVNO_STANJE_RACUNA
			set DSR_NOVOSTANJE = DSR_NOVOSTANJE - @iznos,
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
		values (@racunDuznika, @dsrId, (select isnull(max(ASI_BROJSTAVKE),0)
										from ANALITIKA_IZVODA ai
										where ai.BAR_RACUN = @racunDuznika and ai.DSR_IZVOD = @dsrId) + 1,
				@naseljenoMestoId,@sifraPlacanja, @oznakaValute,@duznik,@svrhaPlacanja,
				@primalac,@datumPrijema,@datumValute,@racunDuznika,@modelZaduzenja,@pozivNaBrojZaduzenja,@racunPrimaoca,
				@modelOdobrenja,@pozivNaBrojOdobrenja,@hitno,@iznos,@tipgreske,'P');
		
		

		-------------- KRAJ ZA DUZNIKA -------------------------------------------


		---------------------------- ZA PRIMAOCA -------------------------------------
		if (@bankaRacunaDuznika = @bankaRacunaPrimaoca)
		begin
			set @dsrId2 = (
			select dsr.DSR_IZVOD
			from DNEVNO_STANJE_RACUNA dsr
			where dsr.DSR_DATUM = @datumPrijema AND dsr.BAR_RACUN= @racunPrimaoca);
		

		-- ne postoji dnevno stanje racuna duznika za datum prijema, treba napraviti novo
		if @dsrId2 is null
		begin
			--declare @poslednjeStanje decimal(15,2);
			
			set @poslednjeStanje = (
			select top 1 dsr.DSR_NOVOSTANJE
			from DNEVNO_STANJE_RACUNA dsr
			where dsr.BAR_RACUN = @racunPrimaoca
			order by dsr.DSR_DATUM desc);

			if @poslednjeStanje is null
			begin
				set @poslednjeStanje = 0;
			end
			
			set @dsrId2 = (select max(dsr.DSR_IZVOD) from DNEVNO_STANJE_RACUNA dsr) + 1;
			insert into DNEVNO_STANJE_RACUNA(DSR_IZVOD,
						DSR_DATUM,
						DSR_PRETHODNO,
						DSR_UKORIST,
						DSR_NATERET,
						DSR_NOVOSTANJE,
						BAR_RACUN)
			values (@dsrId2, @datumPrijema, @poslednjeStanje, @iznos,0, @poslednjeStanje+@iznos, @racunPrimaoca);
		end
		-- inace updejtovati stanje za danasnji datum
		else
		begin
			update DNEVNO_STANJE_RACUNA
			set DSR_PRETHODNO = DSR_NOVOSTANJE,
				DSR_NOVOSTANJE = DSR_NOVOSTANJE + @iznos,
				DSR_UKORIST= DSR_UKORIST + @iznos
			where DSR_IZVOD = @dsrId2;
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
		values (@racunPrimaoca, @dsrId2, (select isnull(max(ASI_BROJSTAVKE),0)
										from ANALITIKA_IZVODA ai
										where ai.BAR_RACUN = @racunPrimaoca and ai.DSR_IZVOD = @dsrId) + 1,
				@naseljenoMestoId,@sifraPlacanja, @oznakaValute,@duznik,@svrhaPlacanja,
				@primalac,@datumPrijema,@datumValute,@racunDuznika,@modelZaduzenja,@pozivNaBrojZaduzenja,@racunPrimaoca,
				@modelOdobrenja,@pozivNaBrojOdobrenja,@hitno,@iznos,@tipgreske,'P');
		end
		
		else
		begin
			-- za RTGS
			if (@hitno = 1 or @iznos >= 250000)
			begin
				set @dsrId2 = (
					select dsr.DSR_IZVOD
					from DNEVNO_STANJE_RACUNA dsr
					where dsr.DSR_DATUM = @datumPrijema AND dsr.BAR_RACUN= @racunPrimaoca);
		

				-- ne postoji dnevno stanje racuna duznika za datum prijema, treba napraviti novo
				if @dsrId2 is null
				begin
					--declare @poslednjeStanje decimal(15,2);
			
					set @poslednjeStanje = (
					select top 1 dsr.DSR_NOVOSTANJE
					from DNEVNO_STANJE_RACUNA dsr
					where dsr.BAR_RACUN = @racunPrimaoca
					order by dsr.DSR_DATUM desc);

					if @poslednjeStanje is null
					begin
						set @poslednjeStanje = 0;
					end
			
					set @dsrId2 = (select max(dsr.DSR_IZVOD) from DNEVNO_STANJE_RACUNA dsr) + 1;
					insert into DNEVNO_STANJE_RACUNA(DSR_IZVOD,
								DSR_DATUM,
								DSR_PRETHODNO,
								DSR_UKORIST,
								DSR_NATERET,
								DSR_NOVOSTANJE,
								BAR_RACUN)
					values (@dsrId2, @datumPrijema, @poslednjeStanje, @iznos,0, @poslednjeStanje+@iznos, @racunPrimaoca);
				end
				-- inace updejtovati stanje za danasnji datum
				else
				begin
					update DNEVNO_STANJE_RACUNA
					set DSR_PRETHODNO = DSR_NOVOSTANJE,
						DSR_NOVOSTANJE = DSR_NOVOSTANJE + @iznos,
						DSR_UKORIST= DSR_UKORIST + @iznos
					where DSR_IZVOD = @dsrId2;
				end
				declare @brojStavke numeric(8,0) = (select isnull(max(ASI_BROJSTAVKE),0)
												from ANALITIKA_IZVODA ai
												where ai.BAR_RACUN = @racunPrimaoca and ai.DSR_IZVOD = @dsrId) + 1;
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
				values (@racunPrimaoca, @dsrId2, @brojStavke,
						@naseljenoMestoId,@sifraPlacanja, @oznakaValute,@duznik,@svrhaPlacanja,
						@primalac,@datumPrijema,@datumValute,@racunDuznika,@modelZaduzenja,@pozivNaBrojZaduzenja,@racunPrimaoca,
						@modelOdobrenja,@pozivNaBrojOdobrenja,@hitno,@iznos,@tipgreske,'P');


				-- za rtgs deo
				INSERT INTO [dbo].[RTGS]
							   ([ID_PORUKE]
							   ,[BAR_RACUN]
							   ,[DSR_IZVOD]
							   ,[ASI_BROJSTAVKE]
							   ,[SWIFT_BANKE_DUZNIKA]
							   ,[RACUN_BANKE_DUZNIKA]
							   ,[SWIFT_BAMKE_POVERIOCA]
							   ,[RACUN_BANKE_POVERIOCA])
						 VALUES
							   (convert(varchar(50),NEWID()),

							   @racunPrimaoca,
							   @dsrId2,
							   @brojStavke,
							   @swiftDuznika,
							   @racunDuznika,
							   @swiftPrimaoca,
							   @racunPrimaoca);
			end
			else
			-- za kliring
			begin
				declare @tmp integer;
			end
		end
		
		------------------------- KRAJ ZA PRIMAOCA -----------------------------------

		if @zaUkidanje = 1
		begin
			update RACUNI
			set BAR_VAZI = 0
			where BAR_RACUN = @racunDuznika;

			
			INSERT INTO [dbo].[UKIDANJE]
					   ([BAR_RACUN]
					   ,[UK_DATUKIDANJA]
					   ,[UK_NARACUN])
				 VALUES
					   (@racunDuznika
					   ,GETDATE()
					   ,@racunPrimaoca);
		end
	end

	-- ne postoji racun duznika ili racun primaoca, NEUSPESNA TRANSAKCIJA
	else
	begin
		set @tipGreske = 8;
	end
	

end