/*==============================================================*/
/* DBMS name:      ORACLE Version 11g                           */
/* Created on:     29.6.2016. 01.45.37                          */
/*==============================================================*/


alter table ANALITIKA_IZVODA
   drop constraint FK_ANALITIK_ANALITIKA_DNEVNO_S;

alter table ANALITIKA_IZVODA
   drop constraint FK_ANALITIK_MESTO_PRI_NASELJEN;

alter table ANALITIKA_IZVODA
   drop constraint FK_ANALITIK_RTGS_PLAC_RTGS;

alter table ANALITIKA_IZVODA
   drop constraint FK_ANALITIK_STAVKA_KL_STAVKA_K;

alter table ANALITIKA_IZVODA
   drop constraint FK_ANALITIK_VALUTA_PL_VALUTE;

alter table ANALITIKA_IZVODA
   drop constraint FK_ANALITIK_VRSTA_PLA_VRSTE_PL;

alter table DNEVNO_STANJE_RACUNA
   drop constraint FK_DNEVNO_S_DNEVNI_IZ_RACUNI;

alter table FIZICKA_LICA
   drop constraint FK_FIZICKA__INHERITAN_KLIJENT;

alter table KURSNA_LISTA
   drop constraint FK_KURSNA_L_KURS_POSL_BANKA;

alter table KURS_U_VALUTI
   drop constraint FK_KURS_U_V_OSNOVNA_V_VALUTE;

alter table KURS_U_VALUTI
   drop constraint FK_KURS_U_V_PREMA_VAL_VALUTE;

alter table KURS_U_VALUTI
   drop constraint FK_KURS_U_V_VALUTE_U__KURSNA_L;

alter table NASELJENO_MESTO
   drop constraint FK_NASELJEN_MESTA_U_D_DRZAVA;

alter table PRAVNA_LICA
   drop constraint FK_PRAVNA_L_INHERITAN_KLIJENT;

alter table RACUNI
   drop constraint FK_RACUNI_POSLOVNA__BANKA;

alter table RACUNI
   drop constraint FK_RACUNI_VALUTA_RA_VALUTE;

alter table RACUNI
   drop constraint FK_RACUNI_VLASNIK_R_KLIJENT;

alter table RTGS
   drop constraint FK_RTGS_RTGS_PLAC_ANALITIK;

alter table STAVKA_KLIRINGA
   drop constraint FK_STAVKA_K_STAVKA_KL_ANALITIK;

alter table STAVKA_KLIRINGA
   drop constraint FK_STAVKA_K_STAVKA_U__KLIRING;

alter table UKIDANJE
   drop constraint FK_UKIDANJE_UKIDANJE__RACUNI;

alter table VALUTE
   drop constraint FK_VALUTE_DRZAVNA_V_DRZAVA;

alter table ZAPOSLENI
   drop constraint FK_ZAPOSLEN_ZAPOSLENI_BANKA;

drop table ANALITIKA_IZVODA cascade constraints;

drop table BANKA cascade constraints;

drop table DNEVNO_STANJE_RACUNA cascade constraints;

drop table DRZAVA cascade constraints;

drop table FIZICKA_LICA cascade constraints;

drop table KLIJENT cascade constraints;

drop table KLIRING cascade constraints;

drop table KURSNA_LISTA cascade constraints;

drop table KURS_U_VALUTI cascade constraints;

drop table NASELJENO_MESTO cascade constraints;

drop table PRAVNA_LICA cascade constraints;

drop table RACUNI cascade constraints;

drop table RTGS cascade constraints;

drop table STAVKA_KLIRINGA cascade constraints;

drop table UKIDANJE cascade constraints;

drop table VALUTE cascade constraints;

drop table VRSTE_PLACANJA cascade constraints;

drop table ZAPOSLENI cascade constraints;

/*==============================================================*/
/* Table: ANALITIKA_IZVODA                                      */
/*==============================================================*/
create table ANALITIKA_IZVODA 
(
   BAR_RACUN            VARCHAR2(18)         not null,
   DSR_IZVOD            NUMBER(3)            not null,
   ASI_BROJSTAVKE       NUMBER(8)            not null,
   NM_SIFRA             INTEGER,
   VPL_OZNAKA           CHAR(3),
   VA_IFRA              CHAR(3),
   ID_PORUKE            VARCHAR2(50),
   ID_NALOGA            VARCHAR2(50),
   ASI_DUZNIK           VARCHAR2(256)        not null,
   ASI_SVRHA            VARCHAR2(256)        not null,
   ASI_POVERILAC        VARCHAR2(256)        not null,
   ASI_DATPRI           DATE                 not null,
   ASI_DATVAL           DATE                 not null,
   ASI_RACDUZ           VARCHAR2(18),
   ASI_MODZAD           NUMBER(2),
   ASI_PBZAD            VARCHAR2(20),
   ASI_RACPOV           VARCHAR2(18),
   ASI_MODODOB          NUMBER(2),
   ASI_PBODO            VARCHAR2(20),
   ASI_HITNO            SMALLINT             default 0 not null
      constraint CKC_ASI_HITNO_ANALITIK check (ASI_HITNO between 0 and 1),
   ASI_IZNOS            NUMBER(15,2)         default 0 not null,
   ASI_TIPGRESKE        NUMBER(1)            default 1 not null
      constraint CKC_ASI_TIPGRESKE_ANALITIK check (ASI_TIPGRESKE in (1,2,3,8,9)),
   ASI_STATUS           CHAR(1)              default 'E'
      constraint CKC_ASI_STATUS_ANALITIK check (ASI_STATUS is null or (ASI_STATUS in ('E','P'))),
   constraint PK_ANALITIKA_IZVODA primary key (BAR_RACUN, DSR_IZVOD, ASI_BROJSTAVKE)
);

/*==============================================================*/
/* Table: BANKA                                                 */
/*==============================================================*/
create table BANKA 
(
   B_PIB                CHAR(10)             not null,
   B_IME                VARCHAR2(120)        not null,
   B_WEB                VARCHAR2(128),
   B_TELEFON            VARCHAR2(20)         not null,
   B_FAX                VARCHAR2(20),
   B_ADRESA             VARCHAR2(120)        not null,
   B_EMAIL              VARCHAR2(50),
   constraint PK_BANKA primary key (B_PIB)
);

/*==============================================================*/
/* Table: DNEVNO_STANJE_RACUNA                                  */
/*==============================================================*/
create table DNEVNO_STANJE_RACUNA 
(
   BAR_RACUN            VARCHAR2(18)         not null,
   DSR_IZVOD            NUMBER(3)            not null,
   DSR_DATUM            DATE                 not null,
   DSR_PRETHODNO        NUMBER(15,2)         default 0 not null,
   DSR_UKORIST          NUMBER(15,2)         default 0 not null,
   DSR_NATERET          NUMBER(15,2)         default 0 not null,
   DSR_NOVOSTANJE       NUMBER(15,2)         default 0 not null,
   constraint PK_DNEVNO_STANJE_RACUNA primary key (BAR_RACUN, DSR_IZVOD)
);

/*==============================================================*/
/* Table: DRZAVA                                                */
/*==============================================================*/
create table DRZAVA 
(
   DR_SIFRA             CHAR(3)              not null,
   DR_NAZIV             VARCHAR2(40)         not null,
   constraint PK_DRZAVA primary key (DR_SIFRA)
);

comment on table DRZAVA is
'Matièni katalog država.';

/*==============================================================*/
/* Table: FIZICKA_LICA                                          */
/*==============================================================*/
create table FIZICKA_LICA 
(
   KL_ID                CHAR(10)             not null,
   FL_JMBG              CHAR(10)             not null,
   KL_ADRESA            VARCHAR2(120)        not null,
   KL_EMAIL             VARCHAR2(128),
   KL_TELEFON           VARCHAR2(20)         not null,
   FL_PREZIME           VARCHAR2(120)        not null,
   FL_IME               VARCHAR2(120)        not null,
   constraint PK_FIZICKA_LICA primary key (KL_ID, FL_JMBG)
);

/*==============================================================*/
/* Table: KLIJENT                                               */
/*==============================================================*/
create table KLIJENT 
(
   KL_ID                CHAR(10)             not null,
   KL_ADRESA            VARCHAR2(120)        not null,
   KL_EMAIL             VARCHAR2(128),
   KL_TELEFON           VARCHAR2(20)         not null,
   constraint PK_KLIJENT primary key (KL_ID)
);

/*==============================================================*/
/* Table: KLIRING                                               */
/*==============================================================*/
create table KLIRING 
(
   ID_PORUKE            VARCHAR2(50)         not null,
   SWIFT_BANKE_DUZNIKA  CHAR(8)              not null,
   RACUN_BANKE_DUZNIKA  VARCHAR2(18)         not null,
   SWIFT_BANKE_POVERIOCA CHAR(8)              not null,
   RACUN_BANKE_POVERIOCA VARCHAR2(18)         not null,
   UKUPAN_IZNOS         NUMBER(15,2)         not null,
   SIFRA_VALUTE         CHAR(3)              not null,
   DATUM_VALUTE         DATE                 not null,
   DATUM                DATE                 not null,
   constraint PK_KLIRING primary key (ID_PORUKE)
);

/*==============================================================*/
/* Table: KURSNA_LISTA                                          */
/*==============================================================*/
create table KURSNA_LISTA 
(
   B_PIB                CHAR(10)             not null,
   KL_DATUM             DATE                 not null,
   KL_BROJ              NUMBER(3)            not null,
   KL_DATPR             DATE                 not null,
   constraint PK_KURSNA_LISTA primary key (B_PIB, KL_DATUM)
);

/*==============================================================*/
/* Table: KURS_U_VALUTI                                         */
/*==============================================================*/
create table KURS_U_VALUTI 
(
   B_PIB                CHAR(10)             not null,
   KL_DATUM             DATE                 not null,
   KLS_RBR              NUMBER(2)            not null,
   VA_IFRA              CHAR(3)              not null,
   VAL_VA_IFRA          CHAR(3)              not null,
   KLS_KUPOVNI          NUMBER(9,4)          default 0 not null,
   KLS_SREDNJI          NUMBER(9,4)          default 0 not null,
   KLS_PRODAJNI         NUMBER(9,4)          default 0 not null,
   constraint PK_KURS_U_VALUTI primary key (B_PIB, KL_DATUM, KLS_RBR)
);

/*==============================================================*/
/* Table: NASELJENO_MESTO                                       */
/*==============================================================*/
create table NASELJENO_MESTO 
(
   NM_SIFRA             INTEGER              not null,
   DR_SIFRA             CHAR(3)              not null,
   NM_NAZIV             VARCHAR2(60)         not null,
   NM_PTTOZNAKA         VARCHAR2(12)         not null,
   constraint PK_NASELJENO_MESTO primary key (NM_SIFRA)
);

/*==============================================================*/
/* Table: PRAVNA_LICA                                           */
/*==============================================================*/
create table PRAVNA_LICA 
(
   KL_ID                CHAR(10)             not null,
   PR_PIB               CHAR(10)             not null,
   KL_ADRESA            VARCHAR2(120)        not null,
   KL_EMAIL             VARCHAR2(128),
   KL_TELEFON           VARCHAR2(20)         not null,
   PR_NAZIV             VARCHAR2(120)        not null,
   PR_WEB               VARCHAR2(128),
   PR_FAX               VARCHAR2(20),
   constraint PK_PRAVNA_LICA primary key (KL_ID, PR_PIB)
);

/*==============================================================*/
/* Table: RACUNI                                                */
/*==============================================================*/
create table RACUNI 
(
   BAR_RACUN            VARCHAR2(18)         not null,
   B_PIB                CHAR(10)             not null,
   VA_IFRA              CHAR(3)              not null,
   KL_ID                CHAR(10)             not null,
   BAR_DATOTV           DATE                 not null,
   BAR_VAZI             SMALLINT             default 1 not null
      constraint CKC_BAR_VAZI_RACUNI check (BAR_VAZI between 0 and 1),
   constraint PK_RACUNI primary key (BAR_RACUN)
);

/*==============================================================*/
/* Table: RTGS                                                  */
/*==============================================================*/
create table RTGS 
(
   ID_PORUKE            VARCHAR2(50)         not null,
   BAR_RACUN            VARCHAR2(18)         not null,
   DSR_IZVOD            NUMBER(3)            not null,
   ASI_BROJSTAVKE       NUMBER(8)            not null,
   SWIFT_BANKE_DUZNIKA  CHAR(8)              not null,
   RACUN_BANKE_DUZNIKA  VARCHAR2(18)         not null,
   SWIFT_BAMKE_POVERIOCA CHAR(8)              not null,
   RACUN_BANKE_POVERIOCA VARCHAR2(18)         not null,
   constraint PK_RTGS primary key (ID_PORUKE)
);

comment on table RTGS is
'Tabela u kojoj se cuvaju nalozi koji su otkaceni za hitno ili je iznos veci od
250 000 dinara.';

/*==============================================================*/
/* Table: STAVKA_KLIRINGA                                       */
/*==============================================================*/
create table STAVKA_KLIRINGA 
(
   ID_NALOGA            VARCHAR2(50)         not null,
   BAR_RACUN            VARCHAR2(18)         not null,
   DSR_IZVOD            NUMBER(3)            not null,
   ASI_BROJSTAVKE       NUMBER(8)            not null,
   ID_PORUKE            VARCHAR2(50)         not null,
   constraint PK_STAVKA_KLIRINGA primary key (ID_NALOGA)
);

/*==============================================================*/
/* Table: UKIDANJE                                              */
/*==============================================================*/
create table UKIDANJE 
(
   BAR_RACUN            VARCHAR2(18)         not null,
   UK_DATUKIDANJA       DATE                 not null,
   UK_NARACUN           VARCHAR2(20)         not null,
   constraint PK_UKIDANJE primary key (BAR_RACUN)
);

/*==============================================================*/
/* Table: VALUTE                                                */
/*==============================================================*/
create table VALUTE 
(
   VA_IFRA              CHAR(3)              not null,
   DR_SIFRA             CHAR(3)              not null,
   VA_NAZIV             VARCHAR2(30)         not null,
   VA_DOMICILNA         SMALLINT             default 0 not null
      constraint CKC_VA_DOMICILNA_VALUTE check (VA_DOMICILNA between 0 and 1),
   constraint PK_VALUTE primary key (VA_IFRA)
);

/*==============================================================*/
/* Table: VRSTE_PLACANJA                                        */
/*==============================================================*/
create table VRSTE_PLACANJA 
(
   VPL_OZNAKA           CHAR(3)              not null,
   VPL_NAZIV            VARCHAR2(120)        not null,
   constraint PK_VRSTE_PLACANJA primary key (VPL_OZNAKA)
);

/*==============================================================*/
/* Table: ZAPOSLENI                                             */
/*==============================================================*/
create table ZAPOSLENI 
(
   IME_ZAPOSLENI        VARCHAR2(100),
   PREZIME_ZAPOSLENI    VARCHAR2(100),
   KORSNICKO_IME_ZAPOSLENI VARCHAR2(100)        not null,
   B_PIB                CHAR(10)             not null,
   LOZINKA_ZAPOSLENI    VARCHAR2(100),
   constraint PK_ZAPOSLENI primary key (KORSNICKO_IME_ZAPOSLENI)
);

alter table ANALITIKA_IZVODA
   add constraint FK_ANALITIK_ANALITIKA_DNEVNO_S foreign key (BAR_RACUN, DSR_IZVOD)
      references DNEVNO_STANJE_RACUNA (BAR_RACUN, DSR_IZVOD);

alter table ANALITIKA_IZVODA
   add constraint FK_ANALITIK_MESTO_PRI_NASELJEN foreign key (NM_SIFRA)
      references NASELJENO_MESTO (NM_SIFRA);

alter table ANALITIKA_IZVODA
   add constraint FK_ANALITIK_RTGS_PLAC_RTGS foreign key (ID_PORUKE)
      references RTGS (ID_PORUKE);

alter table ANALITIKA_IZVODA
   add constraint FK_ANALITIK_STAVKA_KL_STAVKA_K foreign key (ID_NALOGA)
      references STAVKA_KLIRINGA (ID_NALOGA);

alter table ANALITIKA_IZVODA
   add constraint FK_ANALITIK_VALUTA_PL_VALUTE foreign key (VA_IFRA)
      references VALUTE (VA_IFRA);

alter table ANALITIKA_IZVODA
   add constraint FK_ANALITIK_VRSTA_PLA_VRSTE_PL foreign key (VPL_OZNAKA)
      references VRSTE_PLACANJA (VPL_OZNAKA);

alter table DNEVNO_STANJE_RACUNA
   add constraint FK_DNEVNO_S_DNEVNI_IZ_RACUNI foreign key (BAR_RACUN)
      references RACUNI (BAR_RACUN);

alter table FIZICKA_LICA
   add constraint FK_FIZICKA__INHERITAN_KLIJENT foreign key (KL_ID)
      references KLIJENT (KL_ID);

alter table KURSNA_LISTA
   add constraint FK_KURSNA_L_KURS_POSL_BANKA foreign key (B_PIB)
      references BANKA (B_PIB);

alter table KURS_U_VALUTI
   add constraint FK_KURS_U_V_OSNOVNA_V_VALUTE foreign key (VA_IFRA)
      references VALUTE (VA_IFRA);

alter table KURS_U_VALUTI
   add constraint FK_KURS_U_V_PREMA_VAL_VALUTE foreign key (VAL_VA_IFRA)
      references VALUTE (VA_IFRA);

alter table KURS_U_VALUTI
   add constraint FK_KURS_U_V_VALUTE_U__KURSNA_L foreign key (B_PIB, KL_DATUM)
      references KURSNA_LISTA (B_PIB, KL_DATUM);

alter table NASELJENO_MESTO
   add constraint FK_NASELJEN_MESTA_U_D_DRZAVA foreign key (DR_SIFRA)
      references DRZAVA (DR_SIFRA);

alter table PRAVNA_LICA
   add constraint FK_PRAVNA_L_INHERITAN_KLIJENT foreign key (KL_ID)
      references KLIJENT (KL_ID);

alter table RACUNI
   add constraint FK_RACUNI_POSLOVNA__BANKA foreign key (B_PIB)
      references BANKA (B_PIB);

alter table RACUNI
   add constraint FK_RACUNI_VALUTA_RA_VALUTE foreign key (VA_IFRA)
      references VALUTE (VA_IFRA);

alter table RACUNI
   add constraint FK_RACUNI_VLASNIK_R_KLIJENT foreign key (KL_ID)
      references KLIJENT (KL_ID);

alter table RTGS
   add constraint FK_RTGS_RTGS_PLAC_ANALITIK foreign key (BAR_RACUN, DSR_IZVOD, ASI_BROJSTAVKE)
      references ANALITIKA_IZVODA (BAR_RACUN, DSR_IZVOD, ASI_BROJSTAVKE);

alter table STAVKA_KLIRINGA
   add constraint FK_STAVKA_K_STAVKA_KL_ANALITIK foreign key (BAR_RACUN, DSR_IZVOD, ASI_BROJSTAVKE)
      references ANALITIKA_IZVODA (BAR_RACUN, DSR_IZVOD, ASI_BROJSTAVKE);

alter table STAVKA_KLIRINGA
   add constraint FK_STAVKA_K_STAVKA_U__KLIRING foreign key (ID_PORUKE)
      references KLIRING (ID_PORUKE);

alter table UKIDANJE
   add constraint FK_UKIDANJE_UKIDANJE__RACUNI foreign key (BAR_RACUN)
      references RACUNI (BAR_RACUN);

alter table VALUTE
   add constraint FK_VALUTE_DRZAVNA_V_DRZAVA foreign key (DR_SIFRA)
      references DRZAVA (DR_SIFRA);

alter table ZAPOSLENI
   add constraint FK_ZAPOSLEN_ZAPOSLENI_BANKA foreign key (B_PIB)
      references BANKA (B_PIB);

