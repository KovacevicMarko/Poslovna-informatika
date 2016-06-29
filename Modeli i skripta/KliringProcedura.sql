if object_id ('KliringProcedura') is not null
	drop procedure KliringProcedura; 
go
CREATE PROCEDURE KliringProcedura
	-- Add the parameters for the stored procedure here
AS
BEGIN
	-- SET NOCOUNT ON added to prevent extra result sets from
	-- interfering with SELECT statements.
	SET NOCOUNT ON;

    -- Insert statements for procedure here
	update ANALITIKA_IZVODA
	set ASI_STATUS = 'P'
	where ASI_TIPGRESKE = 1 and ASI_STATUS = 'E';

	-- Kad se dobije odgovor od narodne banke, treba update-ovati stanja
	-- S obzirom da nemamo narodnu banku, update-ovanje nije realizovano
	-- ali moze da se realizuje (videti kod u proceduri za uplatu)



	 INSERT INTO [dbo].[KLIRING]
           ([CLR_ID_KLIRINGA]
           ,[CLR_DATUM_KLIRINGA])
     VALUES
           (convert(varchar(50), NEWID())
           ,DATEADD(day,1,GETDATE()))






END
GO
