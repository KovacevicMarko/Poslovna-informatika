/*==============================================================*/
/* DBMS name:      Microsoft SQL Server 2008                    */
/* Created on:     29.6.2016. 01.55.57                          */
/*==============================================================*/


if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('ANALITIKA_IZVODA') and o.name = 'FK_ANALITIK_ANALITIKA_DNEVNO_S')
alter table ANALITIKA_IZVODA
   drop constraint FK_ANALITIK_ANALITIKA_DNEVNO_S
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('ANALITIKA_IZVODA') and o.name = 'FK_ANALITIK_MESTO_PRI_NASELJEN')
alter table ANALITIKA_IZVODA
   drop constraint FK_ANALITIK_MESTO_PRI_NASELJEN
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('ANALITIKA_IZVODA') and o.name = 'FK_ANALITIK_RTGS_PLAC_RTGS')
alter table ANALITIKA_IZVODA
   drop constraint FK_ANALITIK_RTGS_PLAC_RTGS
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('ANALITIKA_IZVODA') and o.name = 'FK_ANALITIK_STAVKA_KL_STAVKA_K')
alter table ANALITIKA_IZVODA
   drop constraint FK_ANALITIK_STAVKA_KL_STAVKA_K
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('ANALITIKA_IZVODA') and o.name = 'FK_ANALITIK_VALUTA_PL_VALUTE')
alter table ANALITIKA_IZVODA
   drop constraint FK_ANALITIK_VALUTA_PL_VALUTE
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('ANALITIKA_IZVODA') and o.name = 'FK_ANALITIK_VRSTA_PLA_VRSTE_PL')
alter table ANALITIKA_IZVODA
   drop constraint FK_ANALITIK_VRSTA_PLA_VRSTE_PL
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('DNEVNO_STANJE_RACUNA') and o.name = 'FK_DNEVNO_S_DNEVNI_IZ_RACUNI')
alter table DNEVNO_STANJE_RACUNA
   drop constraint FK_DNEVNO_S_DNEVNI_IZ_RACUNI
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('FIZICKA_LICA') and o.name = 'FK_FIZICKA__INHERITAN_KLIJENT')
alter table FIZICKA_LICA
   drop constraint FK_FIZICKA__INHERITAN_KLIJENT
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('KURSNA_LISTA') and o.name = 'FK_KURSNA_L_KURS_POSL_BANKA')
alter table KURSNA_LISTA
   drop constraint FK_KURSNA_L_KURS_POSL_BANKA
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('KURS_U_VALUTI') and o.name = 'FK_KURS_U_V_OSNOVNA_V_VALUTE')
alter table KURS_U_VALUTI
   drop constraint FK_KURS_U_V_OSNOVNA_V_VALUTE
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('KURS_U_VALUTI') and o.name = 'FK_KURS_U_V_PREMA_VAL_VALUTE')
alter table KURS_U_VALUTI
   drop constraint FK_KURS_U_V_PREMA_VAL_VALUTE
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('KURS_U_VALUTI') and o.name = 'FK_KURS_U_V_VALUTE_U__KURSNA_L')
alter table KURS_U_VALUTI
   drop constraint FK_KURS_U_V_VALUTE_U__KURSNA_L
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('NASELJENO_MESTO') and o.name = 'FK_NASELJEN_MESTA_U_D_DRZAVA')
alter table NASELJENO_MESTO
   drop constraint FK_NASELJEN_MESTA_U_D_DRZAVA
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('PRAVNA_LICA') and o.name = 'FK_PRAVNA_L_INHERITAN_KLIJENT')
alter table PRAVNA_LICA
   drop constraint FK_PRAVNA_L_INHERITAN_KLIJENT
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('RACUNI') and o.name = 'FK_RACUNI_POSLOVNA__BANKA')
alter table RACUNI
   drop constraint FK_RACUNI_POSLOVNA__BANKA
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('RACUNI') and o.name = 'FK_RACUNI_VALUTA_RA_VALUTE')
alter table RACUNI
   drop constraint FK_RACUNI_VALUTA_RA_VALUTE
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('RACUNI') and o.name = 'FK_RACUNI_VLASNIK_R_KLIJENT')
alter table RACUNI
   drop constraint FK_RACUNI_VLASNIK_R_KLIJENT
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('RTGS') and o.name = 'FK_RTGS_RTGS_PLAC_ANALITIK')
alter table RTGS
   drop constraint FK_RTGS_RTGS_PLAC_ANALITIK
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('STAVKA_KLIRINGA') and o.name = 'FK_STAVKA_K_STAVKA_KL_ANALITIK')
alter table STAVKA_KLIRINGA
   drop constraint FK_STAVKA_K_STAVKA_KL_ANALITIK
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('STAVKA_KLIRINGA') and o.name = 'FK_STAVKA_K_STAVKA_U__KLIRING')
alter table STAVKA_KLIRINGA
   drop constraint FK_STAVKA_K_STAVKA_U__KLIRING
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('UKIDANJE') and o.name = 'FK_UKIDANJE_UKIDANJE__RACUNI')
alter table UKIDANJE
   drop constraint FK_UKIDANJE_UKIDANJE__RACUNI
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('VALUTE') and o.name = 'FK_VALUTE_DRZAVNA_V_DRZAVA')
alter table VALUTE
   drop constraint FK_VALUTE_DRZAVNA_V_DRZAVA
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('ZAPOSLENI') and o.name = 'FK_ZAPOSLEN_ZAPOSLENI_BANKA')
alter table ZAPOSLENI
   drop constraint FK_ZAPOSLEN_ZAPOSLENI_BANKA
go

if exists (select 1
            from  sysobjects
           where  id = object_id('ANALITIKA_IZVODA')
            and   type = 'U')
   drop table ANALITIKA_IZVODA
go

if exists (select 1
            from  sysobjects
           where  id = object_id('BANKA')
            and   type = 'U')
   drop table BANKA
go

if exists (select 1
            from  sysobjects
           where  id = object_id('DNEVNO_STANJE_RACUNA')
            and   type = 'U')
   drop table DNEVNO_STANJE_RACUNA
go

if exists (select 1
            from  sysobjects
           where  id = object_id('DRZAVA')
            and   type = 'U')
   drop table DRZAVA
go

if exists (select 1
            from  sysobjects
           where  id = object_id('FIZICKA_LICA')
            and   type = 'U')
   drop table FIZICKA_LICA
go

if exists (select 1
            from  sysobjects
           where  id = object_id('KLIJENT')
            and   type = 'U')
   drop table KLIJENT
go

if exists (select 1
            from  sysobjects
           where  id = object_id('KLIRING')
            and   type = 'U')
   drop table KLIRING
go

if exists (select 1
            from  sysobjects
           where  id = object_id('KURSNA_LISTA')
            and   type = 'U')
   drop table KURSNA_LISTA
go

if exists (select 1
            from  sysobjects
           where  id = object_id('KURS_U_VALUTI')
            and   type = 'U')
   drop table KURS_U_VALUTI
go

if exists (select 1
            from  sysobjects
           where  id = object_id('NASELJENO_MESTO')
            and   type = 'U')
   drop table NASELJENO_MESTO
go

if exists (select 1
            from  sysobjects
           where  id = object_id('PRAVNA_LICA')
            and   type = 'U')
   drop table PRAVNA_LICA
go

if exists (select 1
            from  sysobjects
           where  id = object_id('RACUNI')
            and   type = 'U')
   drop table RACUNI
go

if exists (select 1
            from  sysobjects
           where  id = object_id('RTGS')
            and   type = 'U')
   drop table RTGS
go

if exists (select 1
            from  sysobjects
           where  id = object_id('STAVKA_KLIRINGA')
            and   type = 'U')
   drop table STAVKA_KLIRINGA
go

if exists (select 1
            from  sysobjects
           where  id = object_id('UKIDANJE')
            and   type = 'U')
   drop table UKIDANJE
go

if exists (select 1
            from  sysobjects
           where  id = object_id('VALUTE')
            and   type = 'U')
   drop table VALUTE
go

if exists (select 1
            from  sysobjects
           where  id = object_id('VRSTE_PLACANJA')
            and   type = 'U')
   drop table VRSTE_PLACANJA
go

if exists (select 1
            from  sysobjects
           where  id = object_id('ZAPOSLENI')
            and   type = 'U')
   drop table ZAPOSLENI
go

/*==============================================================*/
/* Table: ANALITIKA_IZVODA                                      */
/*==============================================================*/
create table ANALITIKA_IZVODA (
   BAR_RACUN            varchar(18)          not null,
   DSR_IZVOD            numeric(3)           not null,
   ASI_BROJSTAVKE       numeric(8)           not null,
   NM_SIFRA             bigint               null,
   VPL_OZNAKA           char(3)              null,
   VA_IFRA              char(3)              null,
   ID_PORUKE            varchar(50)          null,
   ID_NALOGA            varchar(50)          null,
   ASI_DUZNIK           varchar(256)         not null,
   ASI_SVRHA            varchar(256)         not null,
   ASI_POVERILAC        varchar(256)         not null,
   ASI_DATPRI           datetime             not null,
   ASI_DATVAL           datetime             not null,
   ASI_RACDUZ           varchar(18)          null,
   ASI_MODZAD           numeric(2)           null,
   ASI_PBZAD            varchar(20)          null,
   ASI_RACPOV           varchar(18)          null,
   ASI_MODODOB          numeric(2)           null,
   ASI_PBODO            varchar(20)          null,
   ASI_HITNO            bit                  not null default 0
      constraint CKC_ASI_HITNO_ANALITIK check (ASI_HITNO between 0 and 1),
   ASI_IZNOS            decimal(15,2)        not null default 0,
   ASI_TIPGRESKE        numeric(1)           not null default 1
      constraint CKC_ASI_TIPGRESKE_ANALITIK check (ASI_TIPGRESKE in (1,2,3,8,9)),
   ASI_STATUS           char(1)              null default 'E'
      constraint CKC_ASI_STATUS_ANALITIK check (ASI_STATUS is null or (ASI_STATUS in ('E','P'))),
   constraint PK_ANALITIKA_IZVODA primary key nonclustered (BAR_RACUN, DSR_IZVOD, ASI_BROJSTAVKE)
)
go

/*==============================================================*/
/* Table: BANKA                                                 */
/*==============================================================*/
create table BANKA (
   B_PIB                char(10)             not null,
   B_IME                varchar(120)         not null,
   B_WEB                varchar(128)         null,
   B_TELEFON            varchar(20)          not null,
   B_FAX                varchar(20)          null,
   B_ADRESA             varchar(120)         not null,
   B_EMAIL              varchar(50)          null,
   constraint PK_BANKA primary key nonclustered (B_PIB)
)
go

/*==============================================================*/
/* Table: DNEVNO_STANJE_RACUNA                                  */
/*==============================================================*/
create table DNEVNO_STANJE_RACUNA (
   BAR_RACUN            varchar(18)          not null,
   DSR_IZVOD            numeric(3)           not null,
   DSR_DATUM            datetime             not null,
   DSR_PRETHODNO        decimal(15,2)        not null default 0,
   DSR_UKORIST          decimal(15,2)        not null default 0,
   DSR_NATERET          decimal(15,2)        not null default 0,
   DSR_NOVOSTANJE       decimal(15,2)        not null default 0,
   constraint PK_DNEVNO_STANJE_RACUNA primary key nonclustered (BAR_RACUN, DSR_IZVOD)
)
go

/*==============================================================*/
/* Table: DRZAVA                                                */
/*==============================================================*/
create table DRZAVA (
   DR_SIFRA             char(3)              not null,
   DR_NAZIV             varchar(40)          not null,
   constraint PK_DRZAVA primary key nonclustered (DR_SIFRA)
)
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   'Matièni katalog država.',
   'user', @CurrentUser, 'table', 'DRZAVA'
go

/*==============================================================*/
/* Table: FIZICKA_LICA                                          */
/*==============================================================*/
create table FIZICKA_LICA (
   KL_ID                char(10)             not null,
   FL_JMBG              char(10)             not null,
   KL_ADRESA            varchar(120)         not null,
   KL_EMAIL             varchar(128)         null,
   KL_TELEFON           varchar(20)          not null,
   FL_PREZIME           varchar(120)         not null,
   FL_IME               varchar(120)         not null,
   constraint PK_FIZICKA_LICA primary key nonclustered (KL_ID, FL_JMBG)
)
go

/*==============================================================*/
/* Table: KLIJENT                                               */
/*==============================================================*/
create table KLIJENT (
   KL_ID                char(10)             not null,
   KL_ADRESA            varchar(120)         not null,
   KL_EMAIL             varchar(128)         null,
   KL_TELEFON           varchar(20)          not null,
   constraint PK_KLIJENT primary key nonclustered (KL_ID)
)
go

/*==============================================================*/
/* Table: KLIRING                                               */
/*==============================================================*/
create table KLIRING (
   ID_PORUKE            varchar(50)          not null,
   SWIFT_BANKE_DUZNIKA  char(8)              not null,
   RACUN_BANKE_DUZNIKA  varchar(18)          not null,
   SWIFT_BANKE_POVERIOCA char(8)              not null,
   RACUN_BANKE_POVERIOCA varchar(18)          not null,
   UKUPAN_IZNOS         decimal(15,2)        not null,
   SIFRA_VALUTE         char(3)              not null,
   DATUM_VALUTE         datetime             not null,
   DATUM                datetime             not null,
   constraint PK_KLIRING primary key nonclustered (ID_PORUKE)
)
go

/*==============================================================*/
/* Table: KURSNA_LISTA                                          */
/*==============================================================*/
create table KURSNA_LISTA (
   B_PIB                char(10)             not null,
   KL_DATUM             datetime             not null,
   KL_BROJ              numeric(3)           not null,
   KL_DATPR             datetime             not null,
   constraint PK_KURSNA_LISTA primary key nonclustered (B_PIB, KL_DATUM)
)
go

/*==============================================================*/
/* Table: KURS_U_VALUTI                                         */
/*==============================================================*/
create table KURS_U_VALUTI (
   B_PIB                char(10)             not null,
   KL_DATUM             datetime             not null,
   KLS_RBR              numeric(2)           not null,
   VA_IFRA              char(3)              not null,
   VAL_VA_IFRA          char(3)              not null,
   KLS_KUPOVNI          decimal(9,4)         not null default 0,
   KLS_SREDNJI          decimal(9,4)         not null default 0,
   KLS_PRODAJNI         decimal(9,4)         not null default 0,
   constraint PK_KURS_U_VALUTI primary key nonclustered (B_PIB, KL_DATUM, KLS_RBR)
)
go

/*==============================================================*/
/* Table: NASELJENO_MESTO                                       */
/*==============================================================*/
create table NASELJENO_MESTO (
   NM_SIFRA             bigint               not null,
   DR_SIFRA             char(3)              not null,
   NM_NAZIV             varchar(60)          not null,
   NM_PTTOZNAKA         varchar(12)          not null,
   constraint PK_NASELJENO_MESTO primary key nonclustered (NM_SIFRA)
)
go

/*==============================================================*/
/* Table: PRAVNA_LICA                                           */
/*==============================================================*/
create table PRAVNA_LICA (
   KL_ID                char(10)             not null,
   PR_PIB               char(10)             not null,
   KL_ADRESA            varchar(120)         not null,
   KL_EMAIL             varchar(128)         null,
   KL_TELEFON           varchar(20)          not null,
   PR_NAZIV             varchar(120)         not null,
   PR_WEB               varchar(128)         null,
   PR_FAX               varchar(20)          null,
   constraint PK_PRAVNA_LICA primary key nonclustered (KL_ID, PR_PIB)
)
go

/*==============================================================*/
/* Table: RACUNI                                                */
/*==============================================================*/
create table RACUNI (
   BAR_RACUN            varchar(18)          not null,
   B_PIB                char(10)             not null,
   VA_IFRA              char(3)              not null,
   KL_ID                char(10)             not null,
   BAR_DATOTV           datetime             not null,
   BAR_VAZI             bit                  not null default 1
      constraint CKC_BAR_VAZI_RACUNI check (BAR_VAZI between 0 and 1),
   constraint PK_RACUNI primary key nonclustered (BAR_RACUN)
)
go

/*==============================================================*/
/* Table: RTGS                                                  */
/*==============================================================*/
create table RTGS (
   ID_PORUKE            varchar(50)          not null,
   BAR_RACUN            varchar(18)          not null,
   DSR_IZVOD            numeric(3)           not null,
   ASI_BROJSTAVKE       numeric(8)           not null,
   SWIFT_BANKE_DUZNIKA  char(8)              not null,
   RACUN_BANKE_DUZNIKA  varchar(18)          not null,
   SWIFT_BAMKE_POVERIOCA char(8)              not null,
   RACUN_BANKE_POVERIOCA varchar(18)          not null,
   constraint PK_RTGS primary key nonclustered (ID_PORUKE)
)
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   'Tabela u kojoj se cuvaju nalozi koji su otkaceni za hitno ili je iznos veci od
   250 000 dinara.',
   'user', @CurrentUser, 'table', 'RTGS'
go

/*==============================================================*/
/* Table: STAVKA_KLIRINGA                                       */
/*==============================================================*/
create table STAVKA_KLIRINGA (
   ID_NALOGA            varchar(50)          not null,
   BAR_RACUN            varchar(18)          not null,
   DSR_IZVOD            numeric(3)           not null,
   ASI_BROJSTAVKE       numeric(8)           not null,
   ID_PORUKE            varchar(50)          not null,
   constraint PK_STAVKA_KLIRINGA primary key nonclustered (ID_NALOGA)
)
go

/*==============================================================*/
/* Table: UKIDANJE                                              */
/*==============================================================*/
create table UKIDANJE (
   BAR_RACUN            varchar(18)          not null,
   UK_DATUKIDANJA       datetime             not null,
   UK_NARACUN           varchar(20)          not null,
   constraint PK_UKIDANJE primary key nonclustered (BAR_RACUN)
)
go

/*==============================================================*/
/* Table: VALUTE                                                */
/*==============================================================*/
create table VALUTE (
   VA_IFRA              char(3)              not null,
   DR_SIFRA             char(3)              not null,
   VA_NAZIV             varchar(30)          not null,
   VA_DOMICILNA         bit                  not null default 0
      constraint CKC_VA_DOMICILNA_VALUTE check (VA_DOMICILNA between 0 and 1),
   constraint PK_VALUTE primary key nonclustered (VA_IFRA)
)
go

/*==============================================================*/
/* Table: VRSTE_PLACANJA                                        */
/*==============================================================*/
create table VRSTE_PLACANJA (
   VPL_OZNAKA           char(3)              not null,
   VPL_NAZIV            varchar(120)         not null,
   constraint PK_VRSTE_PLACANJA primary key nonclustered (VPL_OZNAKA)
)
go

/*==============================================================*/
/* Table: ZAPOSLENI                                             */
/*==============================================================*/
create table ZAPOSLENI (
   IME_ZAPOSLENI        varchar(100)         null,
   PREZIME_ZAPOSLENI    varchar(100)         null,
   KORSNICKO_IME_ZAPOSLENI varchar(100)         not null,
   B_PIB                char(10)             not null,
   LOZINKA_ZAPOSLENI    varchar(100)         null,
   constraint PK_ZAPOSLENI primary key nonclustered (KORSNICKO_IME_ZAPOSLENI)
)
go

alter table ANALITIKA_IZVODA
   add constraint FK_ANALITIK_ANALITIKA_DNEVNO_S foreign key (BAR_RACUN, DSR_IZVOD)
      references DNEVNO_STANJE_RACUNA (BAR_RACUN, DSR_IZVOD)
go

alter table ANALITIKA_IZVODA
   add constraint FK_ANALITIK_MESTO_PRI_NASELJEN foreign key (NM_SIFRA)
      references NASELJENO_MESTO (NM_SIFRA)
go

alter table ANALITIKA_IZVODA
   add constraint FK_ANALITIK_RTGS_PLAC_RTGS foreign key (ID_PORUKE)
      references RTGS (ID_PORUKE)
go

alter table ANALITIKA_IZVODA
   add constraint FK_ANALITIK_STAVKA_KL_STAVKA_K foreign key (ID_NALOGA)
      references STAVKA_KLIRINGA (ID_NALOGA)
go

alter table ANALITIKA_IZVODA
   add constraint FK_ANALITIK_VALUTA_PL_VALUTE foreign key (VA_IFRA)
      references VALUTE (VA_IFRA)
go

alter table ANALITIKA_IZVODA
   add constraint FK_ANALITIK_VRSTA_PLA_VRSTE_PL foreign key (VPL_OZNAKA)
      references VRSTE_PLACANJA (VPL_OZNAKA)
go

alter table DNEVNO_STANJE_RACUNA
   add constraint FK_DNEVNO_S_DNEVNI_IZ_RACUNI foreign key (BAR_RACUN)
      references RACUNI (BAR_RACUN)
go

alter table FIZICKA_LICA
   add constraint FK_FIZICKA__INHERITAN_KLIJENT foreign key (KL_ID)
      references KLIJENT (KL_ID)
go

alter table KURSNA_LISTA
   add constraint FK_KURSNA_L_KURS_POSL_BANKA foreign key (B_PIB)
      references BANKA (B_PIB)
go

alter table KURS_U_VALUTI
   add constraint FK_KURS_U_V_OSNOVNA_V_VALUTE foreign key (VA_IFRA)
      references VALUTE (VA_IFRA)
go

alter table KURS_U_VALUTI
   add constraint FK_KURS_U_V_PREMA_VAL_VALUTE foreign key (VAL_VA_IFRA)
      references VALUTE (VA_IFRA)
go

alter table KURS_U_VALUTI
   add constraint FK_KURS_U_V_VALUTE_U__KURSNA_L foreign key (B_PIB, KL_DATUM)
      references KURSNA_LISTA (B_PIB, KL_DATUM)
go

alter table NASELJENO_MESTO
   add constraint FK_NASELJEN_MESTA_U_D_DRZAVA foreign key (DR_SIFRA)
      references DRZAVA (DR_SIFRA)
go

alter table PRAVNA_LICA
   add constraint FK_PRAVNA_L_INHERITAN_KLIJENT foreign key (KL_ID)
      references KLIJENT (KL_ID)
go

alter table RACUNI
   add constraint FK_RACUNI_POSLOVNA__BANKA foreign key (B_PIB)
      references BANKA (B_PIB)
go

alter table RACUNI
   add constraint FK_RACUNI_VALUTA_RA_VALUTE foreign key (VA_IFRA)
      references VALUTE (VA_IFRA)
go

alter table RACUNI
   add constraint FK_RACUNI_VLASNIK_R_KLIJENT foreign key (KL_ID)
      references KLIJENT (KL_ID)
go

alter table RTGS
   add constraint FK_RTGS_RTGS_PLAC_ANALITIK foreign key (BAR_RACUN, DSR_IZVOD, ASI_BROJSTAVKE)
      references ANALITIKA_IZVODA (BAR_RACUN, DSR_IZVOD, ASI_BROJSTAVKE)
go

alter table STAVKA_KLIRINGA
   add constraint FK_STAVKA_K_STAVKA_KL_ANALITIK foreign key (BAR_RACUN, DSR_IZVOD, ASI_BROJSTAVKE)
      references ANALITIKA_IZVODA (BAR_RACUN, DSR_IZVOD, ASI_BROJSTAVKE)
go

alter table STAVKA_KLIRINGA
   add constraint FK_STAVKA_K_STAVKA_U__KLIRING foreign key (ID_PORUKE)
      references KLIRING (ID_PORUKE)
go

alter table UKIDANJE
   add constraint FK_UKIDANJE_UKIDANJE__RACUNI foreign key (BAR_RACUN)
      references RACUNI (BAR_RACUN)
go

alter table VALUTE
   add constraint FK_VALUTE_DRZAVNA_V_DRZAVA foreign key (DR_SIFRA)
      references DRZAVA (DR_SIFRA)
go

alter table ZAPOSLENI
   add constraint FK_ZAPOSLEN_ZAPOSLENI_BANKA foreign key (B_PIB)
      references BANKA (B_PIB)
go

