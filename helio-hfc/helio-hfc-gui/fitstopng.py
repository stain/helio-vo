# Converts a FTIS file to PNG and make a rotation from P_ANGLE
# usage: read_fits file.fits file.png

import sys
sys.path.append("/data/wwwhelio/html/gui/f2n") # The directory that contains f2n.py and f2n_fonts !
sys.path.append("/usr/local/www/apache22/data/hfc-gui/f2n") # The directory that contains f2n.py and f2n_fonts !
import pyfits,numpy,f2n
import Image,ImageOps,ImageDraw

# Converts the FITS file to PNG
#if 'efz' in sys.argv[1] :
#	data = pyfits.getdata(sys.argv[1])
#	im = Image.fromarray(numpy.uint8(data))
#	im = im.transpose(Image.FLIP_TOP_BOTTOM)
#	im.save(sys.argv[2])
#else :
myimage = f2n.fromfits(sys.argv[1])
myimage.setzscale("ex", "ex")
myimage.makepilimage("lin")
myimage.tonet(sys.argv[2])

# Get the P_ANGLE value from the FITS header
hdulist = pyfits.open(sys.argv[1])
hd = hdulist[0].header
if hd.has_key('p_angle') :
	angle = hdulist[0].header['p_angle']
	# Rotate the PNG file from P_ANGLE
	if angle != 0 :
		im = Image.open(sys.argv[2])
		im_rot = im.rotate(angle)
		im_rot.save(sys.argv[2])
# Get the SC_ROLL value from the FITS header
if hd.has_key('SC_ROLL') :
	angle = hdulist[0].header['SC_ROLL']
	im = Image.open(sys.argv[2])
	im = ImageOps.equalize(im)
	im.save(sys.argv[2])
	# Rotate the PNG file from SC_ROLL
	if angle != 0 :
		im = Image.open(sys.argv[2])
		im_rot = im.rotate(angle)
		im_rot.save(sys.argv[2])
#if hd.has_key('SOLAR_R') :
#	r_sun = hdulist[0].header['SOLAR_R']
#	c_x = hdulist[0].header['CRPIX1']
#	c_y = hdulist[0].header['CRPIX2']
#	box = (c_x-r_sun, c_y-r_sun, c_x+r_sun, c_y+r_sun)
#	im = Image.open(sys.argv[2])
#	draw = ImageDraw.Draw(im)
#	draw.arc(box, 0, 360, fill="#ff0000")
#	draw.ellipse(box, outline="#0000ff")
#	im.save(sys.argv[2])

# Applies equalize for Ca K3 protu meudon images
#if 'mp' in sys.argv[2] :
#	r_sun = hdulist[0].header['SOLAR_R']
#	c_x = hdulist[0].header['CENTER_X']
#	c_y = hdulist[0].header['CENTER_Y']
#	box = (c_x-r_sun, c_y-r_sun, c_x+r_sun, c_y+r_sun)
#	im = Image.open(sys.argv[2])
#	draw = ImageDraw.Draw(im)
#	draw.ellipse(box, fill="#0000ff")
#	im = ImageOps.equalize(im)
#	im.save(sys.argv[2])

hdulist.close()

