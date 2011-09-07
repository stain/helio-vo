pro vo2str_test

obj_destroy, o
o = obj_new( 'vo2str', '~/idl/egso/gaga.xml' )
data = o->getdata()

end

;-----------------------------------------------------------

function vo2str::init, filename

;self.datatypes_votable = ptr_new(['boolean','unsignedbyte','char', $
;                                  'unicodeChar', 'short', 'int', $
;                                  'long', 'float', 'double', $
;                                  'floatComplex', 'doubleComplex'] )

self.datatypes_idl = ptr_new( [1,1,7,7,2,3,14,4,5,6,9] )
self.datatypes_votable = ptr_new( ['boolean','unsignedbyte','char', $
                                   'unicodeChar', 'short', 'int', $
                                   'long', 'float', 'double', $
                                   'floatComplex', 'doubleComplex'] )

self.debug = 0
self.struct  = ptr_new( /alloc )
self.list =  obj_new( 'linkedlist' )
; we generate a first empty element that will contain

; the finalized structure (so that we can use wrap_up_item
; with end_votable too)
self.list->add,0


ret = self->idlffxmlsax::init()
if is_string(filename) then self.xmlfile = filename

return, ret

end

;-----------------------------------------------------------

function vo2str::map_datatype, value_in, datatype

; this maps the votable datatype into an IDL 
; value_in: the value to convert (string)
; datatype: the datatype to convert to (see below)
; returns the converted value


; first translate the votable datatyoe into an idl datatype
if is_string( datatype ) then begin 
; either the datatype is passed in string, in this case it's  coming from the
; datatype definition withing the xml document...
    datatype_idx = where( *self.datatypes_votable eq  strlowcase( datatype ), count )
    type = *self.datatypes_idl[datatype_idx]
endif else begin 
; ... or it comes as an int, in this case it does not need the conversion it
; is alreay an idl type
    type = datatype
endelse

; unfortunately idl deals differently with bytes so we have to
; have this if block
if type eq 1 then begin
    value = byte( fix( value_in ))
endif else begin 
; general datatype conversion routine
    value = fix( value_in, type = type )
endelse

if n_elements( value ) eq 1 then value = value[0]

;print, value
return, value

end


;-----------------------------------------------------------

pro vo2str::characters, data

self.charbuffer =  self.charbuffer + data

end

;-----------------------------------------------------------

function vo2str::attr2tag, element_name, $
                       attr_list, attr_names, attr_values, type_list = type_list

; in this routine we build the higher-level structure that will contain, on
; one hand, the meta-infromation about the votable element (such as e.e
; "resource"), and on the other hand, its contents (e.g. the tag names that
; will eventually contain the values)

n = n_elements( attr_names )
yes_type_list = exist( type_list )


struct = {element_name: element_name }

for i = 0, n-1 do begin

    attr_idx = where( attr_list eq strlowcase( attr_names[i] ), count )

    if count ne 0 then begin 

        if yes_type_list then begin 
            this_type = type_list[i]
        endif else this_type = 7

        value = self->map_datatype( attr_values[i], this_type )
        struct =  add_tag( struct, value, attr_list[attr_idx] )  
        
    endif

endfor

return, struct

end

;------------------------------------------------------------

pro vo2str::add_element, element_name, $
    attr_list, attr_names, attr_values, type_list = type_list

;this proc adds the element created to the linked list for further use in
;other elements.

struct = self->attr2tag(  element_name, $
    attr_list, attr_names, attr_values, type_list = type_list )

self.list->add, struct

end

;-----------------------------------------------------------

pro vo2str::start_votable,  attr_names, attr_values

if self.debug gt -1 then message, 'parsing votable', /cont, /info

self->add_element, $
    'votable', $
    ['version', 'id'], $
    type_list = [7,7], $
    attr_names, attr_values

end

;-----------------------------------------------------------

pro vo2str::start_resource, attr_names, attr_values

self->add_element, 'resource', $
                    ['name', 'id', 'type', 'utype'], $
                    attr_names, attr_values

end

;-----------------------------------------------------------

pro vo2str::start_param, attr_names, attr_values

self->add_element, 'param', $
                    ['name', 'id', 'type', 'value'], $
                    attr_names, attr_values

end

;-----------------------------------------------------------

pro vo2str::start_info, attr_names, attr_values

;struct = self->attr2tag( 'info', $
;                         ['id', 'name', 'value'], $
;                         attr_names, attr_values )

struct = self->attr2tag( 'info', $
                         ['id', 'name'], $
                         attr_names, attr_values )
                         
self.list->add, struct

end

;-----------------------------------------------------------

pro vo2str::start_table, attr_names, attr_values

self->add_element, 'table', $
                    ['name', 'id', 'ucd', 'utype', 'ref', 'nrows' ], $
                    attr_names, attr_values
 
end

;-----------------------------------------------------------

pro vo2str::start_field, attr_names, attr_values

; in this proc we build the structure that will hold the array.
item = self.list->get_item( self.list->get_count()-1 )

;struct = self->attr2tag( 'field', $
;                         ['id', 'unit', 'datatype', 'precisison', 'width', $
;                          'ref', 'name', 'ucd', 'utype', 'arraysize', 'type'], $
;                         attr_names, attr_values )

struct = self->attr2tag( 'field', $
                         ['id', 'datatype', 'width', $
                          'name', 'type'], $
                         attr_names, attr_values )


if tag_exist( (*item), 'table_struct' ) then table_struct = (*item).table_struct

x = where(attr_names eq 'datatype')

if x ge 0 then begin
    idltype =$
        (*self.datatypes_idl)[ $
        where(*(self.datatypes_votable) eq (attr_values[x])[0]) ]
endif else idltype = 7

x = where(attr_names eq 'arraysize')

if x ge 0 and idltype ne 7 then begin
    dimension = self->arraysize2dim( (*item).arraysize )  
endif else dimension= 1

; we cant deal with "-" in IDL structure names
name = str_replace( struct.name, '-', '_' )

var = make_array( dimension = dimension, type = idltype )
if n_elements( var ) eq 1 then var = var[0]
table_struct = add_tag( table_struct, $
                        var, $
                        name ) 

*item = rep_tag_value( *item, table_struct, 'table_struct' )

self.list->add, struct

end

;-----------------------------------------------------------

function vo2str::arraysize2dim, arraysize

arraysize  = long( strsplit( arraysize, 'x', /extract )) 

; need to be extended for var length arrays
return, arrraysize

end

;-----------------------------------------------------------

pro vo2str::start_tabledata

; now here we know all the fields so we can build the structure
; array that will contain the data so just load the struct into a 

item = self.list->get_item( self.list->get_count()-1 )

ptr_free, self.array_struct
if tag_exist( (*item), 'table_struct' ) then begin 
    self.array_struct = ptr_new( (*item).table_struct )
endif
ptr_free, self.arraycontent 
self.arraycontent = ptr_new( /alloc )
self.arrayindex = 0

end

;-----------------------------------------------------------

pro vo2str::start_tr

self.structindex = 0
*self.arraycontent = append_arr( *self.arraycontent, *self.array_struct )

end

;-----------------------------------------------------------

pro vo2str::start_td

self.charbuffer = ''

end

;-----------------------------------------------------------

pro vo2str::startelement, a, name, qname, attr_names, attr_values

if self.debug gt 0 then begin
	print,' ------------------- startelement:', qname
	if exist( attr_names ) then begin
		print, 'attr names =', attr_names
		print, 'attr values = ', attr_values
	endif
endif

case qname of
        'DATA': 
        'DESCRIPTION': self.charbuffer = ''
        'INFO': self->start_info, attr_names, attr_values
	'FIELD': self->start_field, attr_names, attr_values
	'PARAM': self->start_param, attr_names, attr_values
        'RESOURCE': self->start_resource, attr_names, attr_values
	'TABLE': self->start_table, attr_names, attr_values
        'TABLEDATA': self->start_tabledata
        'TD': self->start_td
        'TR': self->start_tr
	'VOTABLE': self->start_votable, attr_names, attr_values
        ELSE:
endcase


end

;-----------------------------------------------------------

function vo2str::get_last_item

; in fact should go into th elinked list be we'll care about this later


end

;-----------------------------------------------------------

pro vo2str::end_td

; here we have a data item in the buffer, so we stor it in the actual
; and increment the structure index tag

(*self.arraycontent)[self.arrayindex].(self.structindex++) = self.charbuffer

end

;-----------------------------------------------------------

pro vo2str::end_tr

self.arrayindex++

end

;-----------------------------------------------------------

pro vo2str::end_description

item = self.list->get_item( self.list->get_count()-1 )
*item = add_tag( *item, self.charbuffer, 'description' )
 
end
;-----------------------------------------------------------

pro vo2str::end_tabledata

item = self.list->get_item( self.list->get_count()-1 )

if n_tags( *item ) eq 1 then begin 
;; the table passed is empty, so just get  out of here 
    if self.debug gt 0 then message, 'the table is empty.', /cont
    return
endif

; tabledata with name of table
;name = tag_exist( *item, 'name' ) ?  (*item).name : 'table'
; we cant deal with "-" in IDL structure names
;name = str_replace( name, '-', '_' )
name = 'data'
*item = add_tag( *item, *self.arraycontent, name )
;remove table_struct tag.
if tag_exist( (*item), 'TABLE_STRUCT' ) then *item = rem_tag( (*item), 'TABLE_STRUCT')
 
end

;-----------------------------------------------------------

pro vo2str::end_resource

self->wrap_up_item

end

;-----------------------------------------------------------

pro vo2str::end_param

self->wrap_up_item

end

;-----------------------------------------------------------

pro vo2str::end_info

self->wrap_up_item

end

;-----------------------------------------------------------

pro vo2str::end_field

self->wrap_up_item

end

;-----------------------------------------------------------

pro vo2str::end_table

self->wrap_up_item

end

;-----------------------------------------------------------

pro vo2str::end_votable

keep_ntags = self.fulldesc ? 2 : 1
self->wrap_up_item, keep_ntags

end

;-----------------------------------------------------------

pro vo2str::wrap_up_item, keep_ntags

; this procedure is called by the end_xxx procedures, whenever an item in the
; linked list is ready to be attached to a higher-level structure, e.g. a
; resource structure can be added to a votable structure

checkvar, keep_ntags, 1

; get the table and delete the table element in the linked list
last_idx = self.list->get_count()-1
struct = *(self.list->get_item( last_idx ))
self.list->delete, last_idx--
ntags = n_tags( struct )
tagnames = tag_names( struct )

; this is a protection against the case where the subelement was empty
if ntags eq 1 and tagnames[0] eq 'ELEMENT_NAME' then return

; get the higher-level element in the linked list and add the structure at
; this level
item = self.list->get_item( last_idx )

;extract the element name and remove it from struct
element_name = struct.element_name
struct = rem_tag(struct, 'ELEMENT_NAME')

for i=0, keep_ntags-1 do begin
    if tag_exist(*item, element_name) then begin
      index = tag_index( (*item), element_name)
      (*item) = rep_tag_value( (*item),  [(*item).(index), struct], element_name )
    endif else begin
      (*item) = add_tag( *item, struct, element_name )
    endelse
endfor

end

;-----------------------------------------------------------

pro vo2str::endelement, uri, local, qname

case qname of 
    'DESCRIPTION': self->end_description
    'RESOURCE': self->end_resource
    'PARAM': self->end_param
    'INFO': self->end_info
    'FIELD': self->end_field
    'TD' : self->end_td
    'TR': self->end_tr
    'TABLE': self->end_table
    'TABLEDATA' : self->end_tabledata 
    'VOTABLE': self->end_votable
    else:
endcase

END


;-----------------------------------------------------------

function vo2str::getdata, filename

if is_string( filename )  then self.xmlfile = filename

self->parsefile, self.xmlfile
item = self.list->get_item()

return, (*item).(0)

end

;-----------------------------------------------------------

function vo2str::gettable, filename

if is_string( filename )  then self.xmlfile = filename

self->parsefile, self.xmlfile
item = self.list->get_item()

return, (*item).(0).resource.table.data

end

;-----------------------------------------------------------

pro vo2str__define

struct = {vo2str, $
          xmlfile: '', $
          debug: 0b, $
          fulldesc: 0B, $
          struct: ptr_new(), $
          list: obj_new(),  $
          charbuffer: '', $
          datatypes_votable: ptr_new(), $
          datatypes_idl: ptr_new(), $
          array_struct: ptr_new(), $
          arraycontent: ptr_new(), $
          arrayindex: 0L, $
          structindex: 0L, $
          inherits idlffxmlsax}

end
