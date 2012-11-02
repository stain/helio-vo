;+
; PROJECT: HELIO
;
; NAME:
;       helio_cts
;
; PURPOSE:
;       backend for the HELIO coordinate transformation service
;
; CATEGORY: HELIO, CTS
;
; EXPLANATION:
;	This is the IDL backend to the HELIO coordinate transformation 
;	service. It makes use of the SSW convert_stereo_coord which 
;	provides a very straightforward interface to the SSW spice
;	routines.
;	The routine expects a well formed VOTable as one of its inputs
;	and generates an output VOTable containing the transformed
;	coordinates.
;	For this initial release it is assumed that the first four
;	columns in the VOTable contain the time and three element 
;	position. Any other columns are ignored as are and description
;	or metadata. Similarly the output VOTable will contain four
;	columns which are time and the three element positions in the
;	converted coordinate systems.
;	Where coordinate systems have different origins distances must
;	be provided in km. 
;	The additional keywords are used to indicate if the input or 
;	output are in spherical coordinates in which case the phi and
;	theta angles must be provided in degrees.
;
; CALLING SEQUENCE:
;       helio_cts, inp_votable, inp_coord, outp_votable, outp_coord
;
; INPUTS:
;       inp_votable	The path to a VOTable containing the input coordinates
;	inp_coord	A string containng the coordinate system of input data
;	outp_votable	The path where the output VOTable is to be written
;
; KEYWORDS
;       inp_rep   	Set to 'RTP' if the input is in spherical representation
;	outp_rep	Set to 'RTP' if the output is in spherical representation
;
; HISTORY:
;      18 Oct 2011  CHP  First version, comments and bug reports to chris.perry@stfc.ac.uk
;-

; *** Service interface, can build any special security cmdline sanitization here ***

PRO CALL_HELIO_CTS, _EXTRA=options

	args = COMMAND_LINE_ARGS(COUNT=n)
	IF (n NE 4) THEN BEGIN
		PRINT, 'ERROR - Incorrect number of command line arguments'
		EXIT, STATUS=1
	ENDIF

	HELIO_CTS, args(0), args(1), args(2), args(3), _EXTRA=options
END

; *** Main CTS backend ***

PRO HELIO_CTS, inp_file, inp_coord, outp_file, outp_coord, $
	INP_REP=inp_rep, OUTP_REP=outp_rep

	; *** Enumerated lists used for checking ***
	allowed_coord = ['GEI','GEO','GSE','GSM','GAE','MAG','SM','HCI','HAE', $
		'HEE','HEEQ','Carrington','GRTN']
	allowed_rep   = ['XYZ','RTP']

	; *** Check inp/outp coordinate systems ***
	dummy = WHERE( inp_coord  EQ allowed_coord, n1 )
	dummy = WHERE( outp_coord EQ allowed_coord, n2 )
	IF ( n1 EQ 0 OR n2 EQ 0 ) THEN BEGIN
		PRINT, 'ERROR - Input/Output coordinates must be one of ', $
			STRJOIN(allowed_coord,', ')
		EXIT, STATUS=1
	ENDIF

	; *** Check optional inputs ***
	IF ( N_ELEMENTS(inp_rep) EQ 0 ) THEN inp_rep  = allowed_rep(0)
	IF ( N_ELEMENTS(outp_rep) EQ 0 ) THEN outp_rep = allowed_rep(0)

	; *** Check inp/outp representation ***
	dummy = WHERE( inp_rep  EQ allowed_rep, n1 )
	dummy = WHERE( outp_rep EQ allowed_rep, n2 )
	IF ( n1 EQ 0 OR n2 EQ 0 ) THEN BEGIN
		PRINT, 'ERROR - Input/Output representation must be one of ', $
			STRJOIN(allowed_rep,', ')
		EXIT, STATUS=1
	ENDIF

	; *** Read the input data from the supplied VOTable ***
	OPENR, u, inp_file, /GET_LUN, ERROR=err
	IF ( err NE 0 ) THEN BEGIN
		PRINT,'ERROR - Input file not readable'
		EXIT, STATUS=1
	ENDIF

	; =================== END OF INPUT CHECKING ================

	; *** Initialise the read variable to make sure we read as strings ***
	s = ''

	; *** Read the entire file ***
	WHILE( NOT EOF(u) ) DO BEGIN
		READF, u, s
		IF (N_ELEMENTS(r) EQ 0) THEN r=[s] ELSE r=[r,s]
	ENDWHILE

	; *** Finished with input file ***
	FREE_LUN, u

	; *** Decode the VOTable into an IDL structure ***
	inp = decode_votable(r,/quiet)

	; *** For now we assume that the input table contains 
	; ***   time, v1, v2, v3

	; *** Get the time, this can be in 'anytime' format ***
	; *** Note: Currently we have to trim the end of the string because
	; ***       decode_votable does not extract strings correctly.
	t = inp.(0)
	FOR i=0L, N_ELEMENTS(t)-1 DO IF ( STRPOS(t(i),'<') GE 0 ) THEN $
		t(i) = STRMID(t(i),0,STRPOS(t(i),'<'))

	; *** Get the input coordinates, if required convert RTP to XYZ ***
	; *** Note: Angles are assumed to be in degrees ***
	IF ( inp_rep EQ 'RTP' ) THEN BEGIN
		z = inp.(1)*SIN(inp.(2)*!DTOR)
		a = SQRT((inp.(1))^2 - z^2)
		v = TRANSPOSE([ [a*COS(inp.(3)*!DTOR)], $
				[a*SIN(inp.(3)*!DTOR)], $
				[z]])
	ENDIF ELSE v = TRANSPOSE( [ [inp.(1)], [inp.(2)], [inp.(3)] ] )

	; *** Use the SSW routines to do the coordinate transformation ***
	PRINT, v
	convert_stereo_coord, t, v, inp_coord, outp_coord , /PRECESS
	PRINT, v

	; *** Prepare the output structure, if required convert to RTP (degrees) ***
	IF ( outp_rep EQ 'RTP' ) THEN BEGIN
		r = SQRT(TOTAL(v^2,1))
		outp = { time:t, r:r, theta:ASIN(v(2,*)/r)*!RADEG, $
			phi:ATAN(v(1,*),v(0,*))*!RADEG }
		idx = WHERE( outp.phi LT 0.0, nt )
		IF ( nt GT 0 ) THEN outp.phi(idx) = outp.phi(idx)+360.0
	ENDIF ELSE outp = { time:t, x:v(0,*), y:v(1,*), z:v(2,*) }

	; *** Save the transformed data to the output file ***
	vot_desc = 'VOTable file created by the HELIO Coordinate Transformation Service'
	res_desc = 'Transformed coordinate list'
	tab_desc = 'Transformation from '+inp_coord+'/'+inp_rep+' to '+outp_coord+'/'+outp_rep
	vobs_struct2votable, outp, outp_file, $
		votable=vot_desc, resource=res_desc, table=tab_desc

END
