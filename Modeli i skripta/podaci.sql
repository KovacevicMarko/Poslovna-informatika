USE [poslovna]
GO
INSERT [dbo].[DRZAVA] ([DR_SIFRA], [DR_NAZIV]) VALUES (N'SRB', N'Srbija')
INSERT [dbo].[DRZAVA] ([DR_SIFRA], [DR_NAZIV]) VALUES (N'RSR', N'Republika Srpska')
INSERT [dbo].[DRZAVA] ([DR_SIFRA], [DR_NAZIV]) VALUES (N'MNE', N'Crna Gora')
INSERT [dbo].[NASELJENO_MESTO] ([NM_SIFRA], [DR_SIFRA], [NM_NAZIV], [NM_PTTOZNAKA]) VALUES (2121, N'SRB', N'Novi Sad', N'21000')
INSERT [dbo].[NASELJENO_MESTO] ([NM_SIFRA], [DR_SIFRA], [NM_NAZIV], [NM_PTTOZNAKA]) VALUES (1111, N'SRB', N'Beograd', N'11000')
INSERT [dbo].[VALUTE] ([VA_IFRA], [DR_SIFRA], [VA_NAZIV], [VA_DOMICILNA]) VALUES (N'RSD', N'SRB', N'Dinar', 1)
INSERT [dbo].[VRSTE_PLACANJA] ([VPL_OZNAKA], [VPL_NAZIV]) VALUES (N'189', N'Obicna Transakcija')
INSERT [dbo].[BANKA] ([B_PIB], [B_IME], [B_WEB], [B_TELEFON], [B_FAX], [B_ADRESA], [B_EMAIL], [B_SWIFT], [B_OBRACUNSKI_RACUN]) VALUES (N'1         ', N'Unicredit', N'unicredit.com', N'021123456', N'021123457', N'Narodnog Fronta 37', N'unicredit@gmail.com', N'11111111', N'1111111111')
INSERT [dbo].[BANKA] ([B_PIB], [B_IME], [B_WEB], [B_TELEFON], [B_FAX], [B_ADRESA], [B_EMAIL], [B_SWIFT], [B_OBRACUNSKI_RACUN]) VALUES (N'2         ', N'Banca Intesa', N'bancaintesa.com', N'021987564', N'021978612', N'Fruskogorska 10', N'intesa@gmail.com', N'22222222', N'2222222222')
INSERT [dbo].[BANKA] ([B_PIB], [B_IME], [B_WEB], [B_TELEFON], [B_FAX], [B_ADRESA], [B_EMAIL], [B_SWIFT], [B_OBRACUNSKI_RACUN]) VALUES (N'3         ', N'Komercijalna', N'komerc.com', N'021258369', N'021852963', N'Arena', N'kombank@gmail.com', N'33333333', N'3333333333')
INSERT [dbo].[KLIJENT] ([KL_ID], [KL_ADRESA], [KL_EMAIL], [KL_TELEFON]) VALUES (N'1         ', N'Fruskogorska 12', N'nikola@gmail.com', N'021654231')
INSERT [dbo].[KLIJENT] ([KL_ID], [KL_ADRESA], [KL_EMAIL], [KL_TELEFON]) VALUES (N'2         ', N'vrbas', N'njocko@gmail.com', N'02135412')
INSERT [dbo].[KLIJENT] ([KL_ID], [KL_ADRESA], [KL_EMAIL], [KL_TELEFON]) VALUES (N'3         ', N'Kockarska 12', N'kocko@gmail.com', N'014789632')
INSERT [dbo].[RACUNI] ([BAR_RACUN], [B_PIB], [VA_IFRA], [KL_ID], [BAR_DATOTV], [BAR_VAZI]) VALUES (N'1111111', N'2         ', N'RSD', N'1         ', CAST(N'2016-06-29 00:00:00.000' AS DateTime), 1)
INSERT [dbo].[RACUNI] ([BAR_RACUN], [B_PIB], [VA_IFRA], [KL_ID], [BAR_DATOTV], [BAR_VAZI]) VALUES (N'2222222', N'2         ', N'RSD', N'2         ', CAST(N'2016-06-29 00:00:00.000' AS DateTime), 1)
INSERT [dbo].[RACUNI] ([BAR_RACUN], [B_PIB], [VA_IFRA], [KL_ID], [BAR_DATOTV], [BAR_VAZI]) VALUES (N'3333333', N'1         ', N'RSD', N'3         ', CAST(N'2016-06-29 00:00:00.000' AS DateTime), 1)
INSERT [dbo].[DNEVNO_STANJE_RACUNA] ([BAR_RACUN], [DSR_IZVOD], [DSR_DATUM], [DSR_PRETHODNO], [DSR_UKORIST], [DSR_NATERET], [DSR_NOVOSTANJE]) VALUES (N'1111111', CAST(1 AS Numeric(3, 0)), CAST(N'2016-06-12 00:00:00.000' AS DateTime), CAST(0.00 AS Decimal(15, 2)), CAST(0.00 AS Decimal(15, 2)), CAST(0.00 AS Decimal(15, 2)), CAST(15000.00 AS Decimal(15, 2)))
INSERT [dbo].[DNEVNO_STANJE_RACUNA] ([BAR_RACUN], [DSR_IZVOD], [DSR_DATUM], [DSR_PRETHODNO], [DSR_UKORIST], [DSR_NATERET], [DSR_NOVOSTANJE]) VALUES (N'2222222', CAST(1 AS Numeric(3, 0)), CAST(N'2016-06-15 00:00:00.000' AS DateTime), CAST(0.00 AS Decimal(15, 2)), CAST(0.00 AS Decimal(15, 2)), CAST(0.00 AS Decimal(15, 2)), CAST(6000.00 AS Decimal(15, 2)))
INSERT [dbo].[DNEVNO_STANJE_RACUNA] ([BAR_RACUN], [DSR_IZVOD], [DSR_DATUM], [DSR_PRETHODNO], [DSR_UKORIST], [DSR_NATERET], [DSR_NOVOSTANJE]) VALUES (N'3333333', CAST(1 AS Numeric(3, 0)), CAST(N'2016-06-18 00:00:00.000' AS DateTime), CAST(0.00 AS Decimal(15, 2)), CAST(0.00 AS Decimal(15, 2)), CAST(0.00 AS Decimal(15, 2)), CAST(900000.00 AS Decimal(15, 2)))
INSERT [dbo].[ZAPOSLENI] ([ZAP_IME], [ZAP_PREZIME], [ZAP_KORISNICKO_IME], [B_PIB], [ZAP_LOZINKA]) VALUES (N'nikola', N'petrovic', N'petko', N'1         ', N'petko')
INSERT [dbo].[ZAPOSLENI] ([ZAP_IME], [ZAP_PREZIME], [ZAP_KORISNICKO_IME], [B_PIB], [ZAP_LOZINKA]) VALUES (N'bojan', N'njocev', N'jocko', N'2         ', N'jocko')
INSERT [dbo].[FIZICKA_LICA] ([KL_ID], [FL_JMBG], [KL_ADRESA], [KL_EMAIL], [KL_TELEFON], [FL_PREZIME], [FL_IME]) VALUES (N'1         ', N'1234567890', N'Fruskogorska 12', N'nikola@gmail.com', N'021654231', N'Petrovic', N'Nikola')
INSERT [dbo].[FIZICKA_LICA] ([KL_ID], [FL_JMBG], [KL_ADRESA], [KL_EMAIL], [KL_TELEFON], [FL_PREZIME], [FL_IME]) VALUES (N'2         ', N'9875642103', N'vrbas', N'njocko@gmail.com', N'02135412', N'Njocev', N'Bojan')
INSERT [dbo].[PRAVNA_LICA] ([KL_ID], [PR_PIB], [KL_ADRESA], [KL_EMAIL], [KL_TELEFON], [PR_NAZIV], [PR_WEB], [PR_FAX]) VALUES (N'3         ', N'9874123650', N'Kockarska 12', N'kocko@gmail.com', N'014789632', N'Kockarska stedionica', N'kocko.com', N'0145698723')
