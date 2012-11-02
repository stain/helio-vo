#!/bin/sh
# *****************************************************************************
# *** Shell script for running the HELIO coortindate transformation service ***
# *** This should be called from the context service infrastructure         ***
# *****************************************************************************
# *** The context service is expected to have collected/constructed the     ***
# *** input VOTable based on the users request and will also be responsible ***
# *** for passing the results file back to the user                         ***
# *****************************************************************************
# *** Use the -h option to get help on the usage                            ***
# ***     Supported coordinate systems are:                                 ***
# ***         GEI, GEO, GSE, GSM, GAE, MAG, SM, HCI, HAE, HEE, HEEQ,        ***
# ***         Carrington and GRTN                                           ***
# *****************************************************************************
# *** History: V1.0, chris.perry@stfc.ac.uk, 18/10/2011                     ***
# *****************************************************************************

# *** Define the PATHS needed to pick up the SPICE DLM ***
ROOT=/export/home/ukdcdev/SPASE/helio/cts
export LD_LIBRARY_PATH=$LD_LIBRARY_PATH:$ROOT
export IDL_DLM_PATH="$ROOT:<IDL_DEFAULT>"
export IDL_PATH="$ROOT:<IDL_DEFAULT>"
export IDL_DLM_PATH IDL_PATH

# *** Specify a location for the log file ***
logfile=$ROOT/helio_cts_$$.log

# *** Handle the command line arguments ***

while [ -n "$1" ]
do
	ARG=$1
	shift

	case $ARG in
		-i) 	inpfil=$1 ; shift ;;
		-o)	outfil=$1 ; shift ;;
		-ic)	inpco=$1  ; shift ;;
		-oc)	outco=$1  ; shift ;;
		-irtp)  inopt=", INP_REP='RTP'" ;;
		-ortp)  outopt=", OUTP_REP='RTP'" ;;
		-log)	logfile=$1 ; shift;;

		-h)	echo "Usage `basename $0` -i vot -o vot -ic coord -oc coord [-irtp] [-ortp]"
			echo "  -i vot     Specify the location of the input VOTable (required)"
			echo "  -o vot     Specify the location of the output VOTable (required)"
			echo "  -ic coord  Specify coordinate system for input data (required)"
			echo "  -oc coord  Specif coordinate system for output data (required)"
			echo "  -irtp      Indicate that input is in spherical coordinates (optional)"
			echo "  -ortp      Indicate that output should be in spherical coordinates (optional)"
			exit 0;;

		*)	echo "ERROR : Invalid argument ($ARG)" ; exit 1 ;;
	esac
done

# *** Check that we have the minimum required inputs ***
if [ -z "$inpfil" -o -z "$outfil" -o -z "$inpco" -o -z "$outco" ]; then
	echo "ERROR: You must specify input and output filenames and coordinate systems"
	exit 1
fi

echo "Logfile `date`" > $logfile

# *** Set-up minimal environment for solar soft routines ***
export SSW=/home/csdsadm/ssw
export SSWDB=$SSW
export STEREO_SPICE=$SSW/stereo/gen/data/spice
export setenv STEREO_SPICE_GEN=$SSW/stereo/gen/data/spice/gen
export setenv STEREO_SPICE_SCLK=$SSW/stereo/gen/data/spice/sclk
export setenv STEREO_SPICE_ATTIT_SM=$SSW/stereo/gen/data/spice/ah
export setenv STEREO_SPICE_EPHEM=$SSW/stereo/gen/data/spice/epm
export setenv STEREO_SPICE_DEF_EPHEM=$SSW/stereo/gen/data/spice/depm
export setenv STEREO_SPICE_OTHER=$SSW/stereo/gen/data/spice/other
export setenv STEREO_SPICE_ATTITUDE=$SSWDB/stereo/gen/spice/ah

# *** Call the CTS backend to do the transformation ***
/usr/local/bin/idl -e "RESTORE, '$ROOT/helio_cts.sav' & call_helio_cts $inopt $outopt" \
		-arg $inpfil -arg $inpco -arg $outfil -arg $outco >> $logfile 2>&1
