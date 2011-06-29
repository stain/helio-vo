--eis.sql

BEGIN;
DROP TABLE eis_cat;
COMMIT;
CREATE TABLE eis_cat (
        eis_cat_id  SERIAL,
        time_start        TIMESTAMP,
        time_end          TIMESTAMP,
        filename          VARCHAR(256),
        fullname          VARCHAR(512),
PRIMARY KEY (eis_cat_id));

REVOKE ALL ON TABLE eis_cat FROM apache;
REVOKE ALL ON TABLE eis_cat FROM guest;
REVOKE ALL ON TABLE eis_cat FROM root;

GRANT SELECT ON TABLE eis_cat TO apache;
GRANT SELECT ON TABLE eis_cat TO guest;
GRANT ALL ON TABLE eis_cat TO root;

BEGIN;
DELETE FROM eis_cat;
COPY eis_cat (time_start,time_end,filename,fullname) FROM '/var/www/hec/temp/EIS.postgres.converted';
COMMIT;
