PRO display2d,Z_in,Xin=X_in,Yin=Y_in, $
              xrange=xrange,yrange=yrange, $
              min_val=min_val,max_val=max_val, $
              xtitle=xtitle,ytitle=ytitle,title=title, $
              ttcharsize=ttcharsize, $
              position=position,contrast=contrast, $
              color_table=color_table, $
              map=map,npixels=npixels, $
              window_set=window_set, $
              background=background, $
              xsize=xsize,ysize=ysize, $
              XLOG=XLOG,YLOG=YLOG,DB=DB, $
              REVERSE_COLORS=REVERSE_COLORS, $
              NOERASE=NOERASE, NOBOX=NOBOX, $
              LOUD=LOUD,FREE=FREE, AUTOCONTRAST=AUTOCONTRAST, $
              _EXTRA = EXTRA

;+
; NAME:
;       display2d
;
; PURPOSE:
;	Displays a 2D array.
;
; AUTHOR:
;       X. BONNIN 
;
; CATEGORY:
;       Graphics.
;
; CALLING SEQUENCE:
;       display2d,Z_in, Xin=X_in, Yin=Y_in
;
; INPUTS:
;       Z_in - 2D array to display
;       Xin  - Values along the X-axis vector.
;       Yin  - Values along Y-axis vector.
;
; OPTIONAL INPUTS:
;       XRANGE      - 2 elements vector containing the X-axis range.
;       YRANGE      - 2 elements vector containing the Y-axis range.
;       MIN_VAL     - Scalar containing the minimum value of Z_in. 
;                     (Ignored if contrast or /AUTOCONTRAST are set, by default, min_val=min(Z,/NAN).)
;       MAX_VAL     - Scalar containing the maximum value of Z_in.
;		      (Ignored if contrast or /AUTOCONTRAST are set, by default, max_val=max(Z,/NAN).)
;       XTITLE      - Scalar of string type containing the title of the x-axis.
;       YTITLE      - Scalar of string type containing the title of the y-axis.
;       TITLE       - Scalar of string type containing the main title of the plot.
;	XSIZE       - Scalar of integer type containing the size of the window (in pixels)
;	 	      along X axis (only works if no window is already open).
;	YSIZE	    - Scalar of integer type containing the size of the window (in pixels)
;		      along Y axis (only works if no window is already open).
;       POSITION    - Vector containing the position of the plot in the window (in normal coordinates system).
;       COLOR_TABLE - Scalar containing the index of the color table to use (color_table = 0 by default) 
;       NPIXELS	    - 2-element vector [Nx,Ny], which contains the numbers Nx and Ny 
;		      of pixels along X and Y axis
;                     to use for resizing the image (use CONGRID function to resize image.)
;       WINDOW_SET  - Scalar containing the index of the open window to use.
;       BACKGROUND  - Scalar containing the background color (black by default).
;	CONTRAST    - Scalar of float type containing a contraste rate
;                     between 0 and 1.
;                     (Ignored if /AUTOCONTRAST is set.)
;       TTCHARSIZE  - Specify the charsize of title (defaut is !P.charsize*1.1)
;
; KEYWORD PARAMETERS:
;       /YLOG           - Use log scale on Y-axis is keyword set (linear by default.)
;	/XLOG 	        - Use log scale on X-axis is keyword set (linear by default.)
;       /DB	        - Display array in decibel (i.e. 10.*log10(z))
;	/REVERSE_COLORS - Reverse color scale on plot (e.g., B&W -> W&B)
;       /NOERASE        - The graphic can not be overplotted if this keyword is set
;	/NOBOX          - Suppress box style axis.
;	/LOUD		- Display information about image on the terminal.
;	/FREE		- Create new windows using /FREE keyword.
;			  (Ignored if window_set is provided.)
;       /AUTOCONTRAST   - Apply a histogram equalization method to get
;                         the best contrast for the input image. 
;
; OUTPUTS:
;	None.
;
; OPTIONAL OUTPUTS:
;	MAP - Structure which contains !X, !Y, !Z, !MAP, and !COLOR variable system of the window.
;
; CALLS:
;	None.
;
; COMMON BLOCKS:
;       None.
;
; SIDE EFFECTS:
;       None.
;
; RESTRICTIONS:
;	None.
;
; EXAMPLE:
;
;       To use this program with your 2D image data Z, type:
;
;	IDL> Z = findgen(100,100)
;       IDL> display2d,Z
;
; MODIFICATION HISTORY:
;
;       Written by X.Bonnin, 10-May-2005.
;       15-SEP-2006, X.Bonnin:	Added map keyword.
;	01-JUN-2010, X.Bonnin: 	Added background keyword.
;	17-JUL-2011, X.Bonnin:  Added contrast optional input.	
;       03-JAN-2012, X.Bonnin:  Replaced /SILENT by /LOUD keyword.	
;       30-JAN-2012, X.Bonnin:  Added ttcharsize optional input.
;       26-JUL-2012, X.Bonnin:  Added /AUTOCONTRAST keyword.
;
;-

ON_ERROR,2

IF n_params() LT 1 THEN BEGIN
    message, /info, "Call is: "
    print,'display2d,Z_in,Xin=X_in,Yin=Y_in, $'
    print,'          xrange=xrange,yrange=yrange,min_val=min_val, $ '
    print,'          max_val=max_val,xtitle=xtitle,ytitle=ytitle,title=title, $'
    print,'          color_table=color_table,ttcharsize=ttcharsize, $'
    print,'          map=map,npixels=npixels,contrast=contrast, $'
    print,'          position=position,background=background, $'
    print,'          xsize=xsize,ysize=ysize,$'
    print,'          window_set=window_set, $'
    print,'          /XLOG,/YLOG,/DB, $'
    print,'          /REVERSE_COLORS,/NOBOX, $'
    print,'          /NOERASE,/LOUD,/FREE,, $'
    print,'          /AUTOCONTRAST, _EXTRA=EXTRA'
    RETURN
ENDIF

;Set keyword parameters
XLOG = keyword_set(XLOG)
YLOG = keyword_set(YLOG)
DB = keyword_set(DB)
REVERSE_COLORS=keyword_set(REVERSE_COLORS)
NOERASE = keyword_set(NOERASE)
NOBOX = keyword_set(NOBOX)
LOUD = keyword_set(LOUD)
FREE = keyword_set(FREE)
AUTOCONTRAST = keyword_set(AUTOCONTRAST)

if (not keyword_set(Z_in)) then begin
    if (LOUD) then print,'You must provide at least a 2d array to display!'
    return 
endif else Z = reform(Z_in)

;**** save initial plot information ****
map0 = {p:!p,x:!x,y:!y,z:!z,map:!map,color:!color}

low_per_val = 1.e-33

sz = size(Z) 
if (sz[0] ne 2) then message,'>> Z must be a 2D array <<'

nx = sz[1] & ny = sz[2]
if (not keyword_set(X_in)) then X = findgen(nx) else X = X_in
if (not keyword_set(Y_in)) then Y = findgen(ny) else Y = Y_in

;if (not keyword_set(position)) and (!P.multi(2) EQ 0) $
;	then position = [.15,.2,.9,.9]
if (not keyword_set(title)) then tt = '' else tt = title
if (not keyword_set(xtitle)) then xt = 'X' else xt = xtitle
if (not keyword_set(ytitle)) then yt = 'Y' else yt = ytitle
if (not keyword_set(ttcharsize)) then ttcharsize = !P.charsize*1.1

if (not keyword_set(xrange)) then xr = [min(X),max(X)] $
	else xr = xrange(sort(xrange))

if (not keyword_set(yrange)) then yr = [min(Y),max(Y)] $
	else yr = yrange(sort(yrange))

;Keep only data points between xrange and yrange
iX = where(X ge xr(0) and X le xr(1),nX)
iY = where(Y ge yr(0) and Y le yr(1),nY)
if (iX(0) eq -1) or (iY(0) eq -1) then return
Z = Z(ix,*)
Z = Z(*,iY)
X = X(iX) & Y = Y(iY)

;**** Resize Image ****
if (keyword_set(npixels)) then begin
   if (n_elements(npixels)) eq 2 then begin 
      Z = CONGRID(Z,npixels[0],npixels[1])   
      X = interpol(X,npixels[0])
      Y = interpol(Y,npixels[1])
      nX = npixels[0] & nY = npixels[1]
   endif
endif
if (LOUD) then begin
   print,'nX = '+strtrim(nX,2)+' / nY = '+strtrim(nY,2)
   print,'Xmin = '+strtrim(min(X,/NAN),2)+' / Ymin = '+strtrim(min(Y,/NAN),2)
   print,'Xmax = '+strtrim(max(X,/NAN),2)+' / Ymax = '+strtrim(max(Y,/NAN),2)
endif

;dB unit
if (DB) then z = 10.*alog10(z > low_per_val)

;**** dynamic range ****
zmin = min(z,/NAN,max=zmax)
if (keyword_set(contrast)) or (AUTOCONTRAST) then begin
   if (zmin eq zmax) then begin
      print,'Input array has only one pixel value'
      goto,skip_contrast
   endif
   h = float(histogram(z,min=zmin,max=zmax,locations=xh,/NAN))
   h = (h[1:*]/max(h[1:*])) 
   ht = total(h)
   xh = xh[1:*]
   
   if (AUTOCONTRAST) then begin
      hs = 0.0 & & i=0l
      while (hs lt 0.02*ht) do begin
         hs = hs +  h[i]
         i++
      endwhile
      min_val = xh[i-1l]

      hs = 0.0 & & i=0l
      while (hs lt 0.99*ht) do begin
         hs = hs +  h[i]
         i++
      endwhile
      max_val = xh[i-1l]
 
   endif else begin
      where_contrast = where(h ge contrast,nh)
      if (nh ge 3) then begin
         min_val = xh[where_contrast[0]]
         max_val = xh[where_contrast[nh-1L]]
      endif else begin
         if (LOUD) then print,'No contrast applied!'
      endelse
   endelse
endif 
skip_contrast:
if (n_elements(min_val) eq 0) then min_val = zmin
if (n_elements(max_val) eq 0) then max_val = zmax

if (LOUD) then print,'min_val = ',min_val,' / max_val = ',max_val

arrayb =bytscl(Z,min=min_val,max=max_val,top=(!d.n_colors<255)-1)

;**** Create window ****
if (!D.NAME eq 'X') or (!D.NAME eq 'WIN') then begin
   device,decomposed=0
   if (n_elements(window_set) ne 0) then begin
      wset,window_set 
   endif else begin
      if (!D.WINDOW eq -1) or (FREE) then begin	
         
         ; Get the screen size if xsize and ysize not set:
         device, get_screen_size=screensize
         if not (keyword_set(xsize)) then xsize=screensize(0)*.5
         if not (keyword_set(ysize)) then ysize=screensize(1)*.5
    
         window,xsize=xsize,ysize=ysize,/FREE
         window_set = !D.WINDOW
      endif
   endelse
   
   if (keyword_set(background)) then col=!P.color - background > 0
endif


;**** Pick the initial color table ****
if (n_elements(color_table) ne 0) then loadct,color_table,/SILENT

if (REVERSE_COLORS) then $
    	arrayb[*,*] = min(arrayb) + max(arrayb) - arrayb[*,*] 
 

;**** Display image ****
shade_surf,Z,X,Y,/xs,/ys,zs=4 $
           ,az=0,ax=90 $
           ,shades=arrayb $
           ,background=background,color=col $
           ,YLOG=YLOG,XLOG=XLOG $
           ,position=position $
           ,xtitle=xt,ytitle=yt $
           ,xrange=xr,yrange=yr $
           ,_EXTRA = EXTRA

xyouts,.5*(!X.WINDOW[0] + !X.WINDOW[1]),.5*(!Y.WINDOW[1]+!Y.REGION[1]),tt $
	  ,color=col,/NORMAL,alignment=0.5 $
	  ,charsize=ttcharsize,_EXTRA = EXTRA 

if (~NOBOX) then begin
	N = 30
	axis,xaxis=1,yrange=yr,color=col $
		,xticks=1,xtickname=REPLICATE(' ', N),ticklen=1.e-20 

	axis,yaxis=1,xrange=xr,color=col,yticks=1 $
		,ytickname=REPLICATE(' ', N),ticklen=1.e-20 
endif

;**** save plot information ****
map = {p:!p,x:!x,y:!y,z:!z,map:!map,color:!color}

;**** restore initial plot information ****
if (NOERASE) then begin
	!P = map0.P
	!X = map0.X
	!Y = map0.Y
	!Z = map0.Z
	!MAP = map0.MAP
	!COLOR = map0.COLOR
endif



END
