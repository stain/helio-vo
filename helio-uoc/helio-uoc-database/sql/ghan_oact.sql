--ghan_oact.sql

BEGIN;
COPY ghan_cat (time_start,time_end,observatory,telescope,instrument,size_pixels,filetype,filepath,filename,ispubblic,contact) FROM '/var/www/hec/temp/GHAN-OACT.postgres.converted';
COMMIT;
