;+
;Project: 
;     HELIOVO
;
;Name: 
;     heliovo_test
;
;Purpose: 
;     Test programm for helio idl client.
;
;Last Modified: 
;      19 Sep 2011 - Matthias Meyer 

;
;-
;-- heliovo_test.

pro heliovo_test
  print, 'starting heliovo testing...'
  
  ;create heliovo object
  o = heliovo()
  
  ;list all services
  o->print, /service
  
  ;find a service
  o->find, service = 'hec'
  
  ;create service object
  s = o->get(service='hec')
  
  ;list all catalogs
  s->print, /catalog
  
  ;find a catalog
  s->find, catalog='hessi'
  
  ;create catalog object
  c = s->get(catalog='rhessi_hxr_flare')
  
  ;set timeinterval
  c->set, TIME_INTERVAL=['1-may-2005','10-may-2005']
  
  ;run query an parse result to struct directly
  data = c->get_struct()
  
  ;print some data of parsed data
  help, data, /str
  help, data.resource.table.data, /str
  
  ;step by step:
  ;run the query
  result = c->get(/q)
  
  ;download result
  x = wget(url=result->get(/url), file='temp.xml')
  
  ;parse result votable
  parser = obj_new('votable2struct')
  data = parser->getdata(x)
  OBJ_DESTROY, parser
  file_delete, x
  
  ;print some data of parsed data
  help, data, /str
  help, data.resource.table.data, /str
  
end