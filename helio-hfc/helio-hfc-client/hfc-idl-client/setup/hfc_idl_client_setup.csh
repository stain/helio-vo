#! /bin/tcsh
#
# Script to set environment variables 
# required by the hfc idl client.
# Must be placed in the hfc_idl_client/setup sub-directory. 
#
# To load this script:
# >source hfc_idl_client_setup.csh
#
# X.Bonnin, 19-AUG-2013

# Define hfc idl client home directory
set CURRENT_DIR=`pwd`
set ARGS=`/usr/sbin/lsof +p $$ | grep -oE /.\*hfc_idl_client_setup.csh`
cd `dirname $ARGS`/..
setenv HFC_IDL_CLIENT_DIR `pwd`
cd $CURRENT_DIR

# Append hfc idl client source files directory to $IDL_PATH
if ($?IDL_PATH) then
    setenv IDL_PATH "$IDL_PATH":+$HFC_IDL_CLIENT_DIR/src
else
    setenv IDL_PATH "$<IDL_DEFAULT>":+$HFC_IDL_CLIENT_DIR/src
endif
