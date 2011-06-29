--sxt.sql

BEGIN;
DROP TABLE sxt_cat;
COMMIT;
CREATE TABLE sxt_cat (
        sxt_cat_id  SERIAL,
        Observatory VARCHAR(8),
        Telescope VARCHAR(8),
        Instrument VARCHAR(8),
        Orbit VARCHAR(8),
        InstrumentType VARCHAR(32),
        SamplingMethod VARCHAR(32),
        DataType VARCHAR(16),
        ObservationMode VARCHAR(32),
        DomainType VARCHAR(16),
        EnergyRegime VARCHAR(8),
        Campaign INTEGER, 
        PhysicalParam VARCHAR(32),
        Contact VARCHAR(64),
        Date_obs TIMESTAMP,
        Date_end TIMESTAMP,
        Sci_Obj VARCHAR(128),
        Filename VARCHAR(128),
        CoordSystem VARCHAR(16),
        LocationX INTEGER,
        LocationY INTEGER,
        CoordinateNameX  VARCHAR(8),
        CoordinateNameY VARCHAR(8),
        CoordinateNameW VARCHAR(8),
        SpatialSamplingX FLOAT,
        SpatialSamplingY FLOAT,
        AreaCoveredDX INTEGER,
        AreaCoveredDY INTEGER,
        Naxis1 INTEGER,
        Naxis2 INTEGER,
        Naxis3 INTEGER,
        SpectSampling VARCHAR(32),
        Filter VARCHAR(16),
        JD1 FLOAT,
        JD2 FLOAT, 
PRIMARY KEY (sxt_cat_id));

REVOKE ALL ON TABLE sxt_cat FROM apache;
REVOKE ALL ON TABLE sxt_cat FROM guest;
REVOKE ALL ON TABLE sxt_cat FROM root;

GRANT SELECT ON TABLE sxt_cat TO apache;
GRANT SELECT ON TABLE sxt_cat TO guest;
GRANT ALL ON TABLE sxt_cat TO root;

BEGIN;
DELETE FROM sxt_cat;
COPY sxt_cat (Observatory,Telescope,Instrument,Orbit,InstrumentType,SamplingMethod,DataType,ObservationMode,DomainType,EnergyRegime,Campaign,PhysicalParam,Contact,Date_obs,Date_end,Sci_Obj,Filename,CoordSystem,LocationX,LocationY,CoordinateNameX,CoordinateNameY,CoordinateNameW,SpatialSamplingX,SpatialSamplingY,AreaCoveredDX,AreaCoveredDY,Naxis1,Naxis2,Naxis3,SpectSampling,Filter,JD1,JD2) FROM '/var/www/hec/temp/SXT.postgres.converted';
COMMIT;

