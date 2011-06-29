--planetary.sql

BEGIN;
DROP TABLE planetary_cat;
COMMIT;
CREATE TABLE planetary_cat (
        UOC_id  SERIAL,
        obsinst_key        VARCHAR(256),
        provaider         VARCHAR(256),
        time_start        TIMESTAMP,
        time_end          TIMESTAMP,
        url               VARCHAR(512),
        ancil_filename    VARCHAR(512),
        ancil_info VARCHAR(512),
PRIMARY KEY (UOC_id));

REVOKE ALL ON TABLE planetary_cat FROM apache;
REVOKE ALL ON TABLE planetary_cat FROM guest;
REVOKE ALL ON TABLE planetary_cat FROM root;

GRANT SELECT ON TABLE planetary_cat TO apache;
GRANT SELECT ON TABLE planetary_cat TO guest;
GRANT ALL ON TABLE planetary_cat TO root;


BEGIN;
DELETE FROM planetary_cat;
COPY planetary_cat (obsinst_key,provaider,time_start,time_end,url,ancil_filename,ancil_info) FROM '/var/www/hec/temp/PLANETARY.postgres.converted';
COMMIT;

/*
sql.query.time.constraint.planetary_cat=time_start>='[:kwstartdate:]' AND time_end<='[:kwenddate:]'
sql.query.instr.constraint.planetary_cat=obsinst_key ILIKE '%[:kwinstrument:]%'
sql.query.coordinates.constraint.planetary_cat=
sql.query.orderby.constraint.planetary_cat=
sql.query.maxrecord.constraint.planetary_cat=10000

sql.columnnames.planetary_cat=obsinst_key::provaider::time_start::time_end::url::ancil_filename::ancil_info
sql.columndesc.planetary_cat=Obsinst key::Provaider::Time start::Time end::URL::Ancillary filename::Ancillary Info
sql.columnucd.planetary_cat= :: :: :: :: :: ::
sql.columnutypes.planetary_cat=
*/
