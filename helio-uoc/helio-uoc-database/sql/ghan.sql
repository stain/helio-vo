--ghan.sql

BEGIN;
DROP TABLE ghan_cat;
COMMIT;
CREATE TABLE ghan_cat (
        ghan_cat_id  SERIAL,
        time_start        TIMESTAMP,
        time_end          TIMESTAMP,
        observatory       VARCHAR(512),
        telescope         VARCHAR(512),
        instrument        VARCHAR(512),
        size_pixels       VARCHAR(16),
        filetype          VARCHAR(16),
        filepath          VARCHAR(512),
        filename          VARCHAR(512),
        ispubblic         VARCHAR(8),
        contact           VARCHAR(512),
PRIMARY KEY (ghan_cat_id));

GRANT SELECT ON TABLE ghan_cat to guest;
GRANT SELECT ON TABLE ghan_cat to apache;

BEGIN;
DELETE FROM ghan_cat;
COPY ghan_cat (time_start,time_end,observatory,telescope,instrument,size_pixels,filetype,filepath,filename,ispubblic,contact) FROM '/var/www/hec/temp/GHAN.postgres.converted';
COMMIT;
