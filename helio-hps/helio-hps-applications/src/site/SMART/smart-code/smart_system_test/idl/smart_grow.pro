;SMART_GROW 
;Returns a dilated mask. If a NL mask is provided and GAUSSIAN is set, 
;then the result will be a Shrijver R-mask.
;
;Provide RADIUS or FWHM in pixels. FWHM is actually half width at half max!!!
;If GAUS is set, then the convolution stucture will fall off as a gaussian.
;If RADIUS is set then the FWHM of the gaussian will be half that.
;If FWHM is set, then RADIUS will be twice that.

function smart_grow, arr, radius=radius, gaus=gaus, fwhm=fwhm

arr0=arr

if n_elements(radius) lt 1 then radius0=5. else radius0=radius

if n_elements(fwhm) gt 0 then begin
	radius0=fwhm;*2.
endif else fwhm=radius0;/2.
gsig=fwhm/(SQRT(2.*ALOG(2.)))

if keyword_set(gaus) then imgsz=[2,4.*radius0,4.*radius0] else imgsz=[2,2.*radius0,2.*radius0]
struc=fltarr(imgsz[1],imgsz[2])

;Generate coordinate maps.
xcoord=rot(congrid(transpose(findgen(imgsz[1])),imgsz[1],imgsz[2]),90)
ycoord=rot(xcoord,-90)
rcoord=sqrt((xcoord-imgsz[1]/2.)^2.+(ycoord-imgsz[2]/2.)^2)

struc[where(rcoord le radius0)]=1.

if keyword_set(gaus) then begin
	;gparams[0] = maximum value (factor) of Gaussian,
	;gparams[1] = mean value (center) of Gaussian,
	;gparams[2] = standard deviation (sigma) of Gaussian.
	
	gparams=[1.,0.,gsig]
	
;	dummy=gaussian([1,2,3], gparams)
	gstruc=gaussian(rcoord, gparams)
	gstruc=reform(gstruc,imgsz[1],imgsz[2])
	
	;;Normalize GSTRUC so that the "central" or max value of GROWNARR is 1.
	;gstruc=gstruc/total(radius0*2.)

	;Normalize GSTRUC so that the volume is 1
	gstruc=gstruc/total(gstruc)

	grownarr=CONVOL( arr0, gstruc); < 1.
	
	;;grownarr=dilate(arr0,gstruc)

endif else grownarr=dilate(arr0,struc)

return, grownarr

end