#!/usr/bin/env python
# -*-coding:Utf-8 -*

import sys, os, argparse
from hqiTools import PropertyFile

#Header of VoTables returned by the interface
voheader = "HFC Query Interface"

#Local path's root
rootPath = "/usr/local/apache-tomcat-6.0/webapps/hfc-hqi/config"

#Property file's local path
propertyFile = rootPath + "/hfc-hqi-property.txt"

#Auxiliary files' local paths
jdbcFile = rootPath + "/inputs/hfc-hqi-jdbc.txt"
constraintFile = rootPath + "/inputs/hfc-hqi-constraints.txt"

def main():

	parser = argparse.ArgumentParser()
	parser.add_argument('-j','--jdbc_file',nargs='?',default=jdbcFile,help='File containing jdbc information for HFC [Default: '+jdbcFile+']')
	parser.add_argument('-c','--constraint_file',nargs='?',default=constraintFile,help='File containing constraints [Default: '+constraintFile+']')
	parser.add_argument('-o','--output_file',nargs='?',default=propertyFile,help='Name of the property file written [Default: '+propertyFile+']')
	parser.add_argument('-v','--verbose',action='store_true',help='Verbose mode')
	Namespace = parser.parse_args()
	
	jdbc_file = Namespace.jdbc_file
	constraint_file = Namespace.constraint_file
	output_file = Namespace.output_file
	VERBOSE = Namespace.verbose

	#Initialize property file object
	prop = PropertyFile(output_file,voheader=voheader,VERBOSE=True)

	#Load the content of the jdbc input file
	prop.load_jdbc(jdbc_file)
	if (len(prop.jdbc) == 0):
		if (VERBOSE): print "Empty jdbc meta-data!"
		sys.exit(1)
	
	#Load HFC database information required	
	if (prop.load_db_content()): print "done"

	#If available, load contraint information
	if (prop.load_constraints(constraint_file)): print "done"
	
	#If available, load ucd information
	if (prop.load_annotations()): print "done"

	#Build the property file content
	if (prop.build_content()): 
		print "done"
	
		#If an old version of the file already exists, then backup it
		if (prop.do_backup(VERBOSE=VERBOSE)): print "done"
	
		#Write file
		if (prop.write_file()) and (VERBOSE): print "done"	
		
if (__name__ == "__main__"):
	main()