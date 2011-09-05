;+
;Project: 
;     HELIOVO
;
;Name: 
;     heliovo_framework__define
;
;Purpose: 
;     Framework for the helio idl client.
;
;Last Modified: 
;      29 Aug 2011 - Matthias Meyer 

;
;-
;-- HELIOVO_FRAMEWORK init
  
function heliovo_framework::init
  self.time= anytim(!stime)
return, 1
end


;------------------------------------------------------------------------------------------------------------------------
;-- Generic get function to return variables of this object.

function heliovo_framework::get, _extra=_extra

  self_tag_names = obj_props( self )

  if keyword_set(_extra) then begin
  
    index = where_arr( self_tag_names, (tag_names(_extra))[0] )
  
    if index  ne -1 then begin
      ;tmp = size(self.(index)) 
      if (size(self.(index)))[1] eq 10 then begin
        return, *(self.(index))
      endif else begin
        return, self.(index)
      endelse
    endif 
    
  endif
  
  return, -1
end


;------------------------------------------------------------------------------------------------------------------------
;-- Show the variables of this object

function heliovo_framework::show
  return, obj_props( self )
end

pro heliovo_framework::show
  print, obj_props( self )
end


;------------------------------------------------------------------------------------------------------------------------
;-- HELIO_FRAMEWORK data structure

pro heliovo_framework__define
  self = {heliovo_framework, time:0D}
end
