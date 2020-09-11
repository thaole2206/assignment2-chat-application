USE FastTrack;
IF (NOT EXISTS (SELECT * FROM sys.schemas WHERE name = 'Assignment2'))
BEGIN
    EXEC ('CREATE SCHEMA Assignment2 AUTHORIZATION [dbo]')
END