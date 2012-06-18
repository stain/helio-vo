#!/usr/bin/env python
# -*-coding:Utf-8 -*

import os, shutil, sys, glob
import MySQLdb, csv
import string, time

class PropertyFile:

	def __init__(self,propertyFile,voheader="",VERBOSE=False):
         
		self.propertyFile = propertyFile
		self.voheader = voheader
		self.jdbc = {}
		self.db = {}	
		self.constraints = {}	
		self.annotations = {}	
		self.content = []
		self.VERBOSE=VERBOSE
					
#Load database content
	def load_db_content(self):
		
		if (len(self.jdbc) == 0):
			if (self.VERBOSE): print "Load input jdbc meta-data first (i.e., run hqiTools.load_jdbc module)."
			return {}
		else:
			url = self.jdbc['URL'].split('/')
			host = url[2].split(':')
			port = int(host[1])
			host = host[0]
			db = url[3]
			user = self.jdbc['USER']
			passwd = self.jdbc['PASSWORD']
		
		#print "Querying database: " + database['name'] + " on " + database["host"]  
		try:
			if (self.VERBOSE): print "Loading "+host+"/"+db+":["+user+"@"+passwd+"] table information..."
			buff = MySQLdb.connect(host = host, user = user, passwd = passwd, db = db, port=port)
		except MySQLdb.Error,e:
			print "Error %d: %s" % (e.args[0], e.args[1])
			return {}		

		#Get tables
		query = "SHOW TABLES"
		#if (self.VERBOSE): print "--> "+query
		cursor = buff.cursor()
		cursor.execute(query)
		tableList = cursor.fetchall()
		cursor.close()
				
		#Read all of the fields of each table
		cursor = buff.cursor()
		content = {}
		for current_table in tableList:
			query = "SHOW FULL COLUMNS FROM " + (str(current_table))[2:-3]
			#if (self.VERBOSE): print "--> "+query
			cursor.execute(query)
			#columns -> Field, Type, Collation, Null, etc.
			columns = cursor.fetchall()
			table_name =  current_table[0].upper()
			if not (content.has_key(table_name)): content[table_name] = []
			content[table_name].append(columns)
		
		cursor.close()
		self.db = content
		return True

#Load jdbc info
	def load_jdbc(self,jdbcFile):
		
		if not (os.path.isfile(jdbcFile)): 
			if (self.VERBOSE): print jdbcFile + " not found!"
			return {}
		else:
			if (self.VERBOSE): print "reading "+jdbcFile+"..."
		
		try:
			content = csv.DictReader(open(jdbcFile,'rb'),delimiter="|")
		except csv.Error:
			if (self.VERBOSE): print jdbcFile + " can not be read!"
			return {}
		else:
			self.jdbc = next(content)
	
		return True

#Load content of the constraint file
	def load_constraints(self,constraintFile):

		if not (os.path.isfile(constraintFile)): 
			if (self.VERBOSE): print constraintFile + " not found!"
			return {}
		else:
			if (self.VERBOSE): print "reading "+constraintFile+"..."
			
		try:
			content = csv.DictReader(open(constraintFile,'rb'),delimiter="|")
		except csv.Error:
			if (self.VERBOSE): print constraintFile + " can not be read!"
			return {}
		else:
			constraints = {}
			for row in content:
				if not (row.has_key(row['TABLE'])): constraints[row['TABLE']] = []
				constraints[row['TABLE']] = row
			self.constraints = constraints

		return True

#Load ucd-utype meta-data from ANNOTATIONS table in HFC
	def load_annotations(self):

		if (len(self.jdbc) == 0):
			if (self.VERBOSE): print "Load input jdbc meta-data first (i.e., run hqiTools.load_jdbc module)."
			return {}
		else:
			url = self.jdbc['URL'].split('/')
			host = url[2].split(':')
			port = int(host[1])
			host = host[0]
			db = url[3]
			user = self.jdbc['USER']
			passwd = self.jdbc['PASSWORD']

		#print "Querying database: " + database['name'] + " on " + database["host"]  
		try:
			if (self.VERBOSE): print "Loading ucd-utype meta-data from ANNOTATION table..."
			buff = MySQLdb.connect(host = host, user = user, passwd = passwd, db = db, port=port)
		except MySQLdb.Error,e:
			print "Error %d: %s" % (e.args[0], e.args[1])
			return {}			
		
		#Get tables
		query = "SELECT FIELD_NAME,TABLE_NAME,UCD,UTYPE FROM ANNOTATIONS"
		#if (self.VERBOSE): print "--> "+query
		cursor = buff.cursor()
		cursor.execute(query)
		annotations = cursor.fetchall()
		cursor.close()		
		
		#Generate dictionnary of field names containing TABLE_NAME, UCD, and UTYPE meta-data
		content = {}
		for current_row in annotations:
			current_field = current_row[0]
			line2append = list(current_row[1:])
			if (not content.has_key(current_field)): content[current_field] = []
			content[current_field].extend(line2append)
 
		self.annotations = content
		
		return True

#Build new property file content
	def build_content(self):
		
		if (self.VERBOSE): print "Building property file's content..."
		
		jdbc = self.jdbc
		db = self.db
		constraints = self.constraints
		annotations = self.annotations
		
		content = []
		#Writing jdbc meta-data
		content.append("jdbc.driver=" + jdbc['DRIVER'] + "\n")
		content.append("jdbc.url=" + jdbc['URL'] + "\n")
		content.append("jdbc.user=" + jdbc['USER'] + "\n")
		content.append("jdbc.password=" + jdbc['PASSWORD'] + "\n")
		content.append(" \n")	
		
		#VoTable header 
		content.append("sql.votable.head.desc=" + self.voheader + "\n")
		content.append(" \n")			

		#Loop on each table
		for current_table,current_fields in db.items():
			
			#Write constraints 
			current_items = {'TIME':"",'INSTRUMENT':"",
							'COORDINATES':"",'ORDERBY':"",'MAXRECORD':""}
			if (constraints.has_key(current_table)):	
				for current_key,current_val in constraints[current_table].items():
					current_items[current_key] = current_val
				  	
			content.append("sql.query.time.constraint." + current_table + "="+current_items["TIME"]+"\n")
			content.append("sql.query.instr.constraint." + current_table + "="+current_items["INSTRUMENT"]+"\n")
			content.append("sql.query.coordinates.constraint." + current_table + "="+current_items["COORDINATES"]+"\n")
			content.append("sql.query.orderby.constraint." + current_table + "="+current_items["ORDERBY"]+"\n")
			content.append("sql.query.maxrecord.constraint." + current_table + "="+current_items["MAXRECORD"]+"\n")
			content.append(" \n")				
		
			#Write field name, description, ucd, and utype lines 
			line_name = "sql.columnnames." + current_table + "="
			line_desc = "sql.columndesc." + current_table + "="
			line_ucd = "sql.columnucd." + current_table + "="
			line_utype = "sql.columnutype." + current_table + "="
			for current_fieldname in current_fields:
				for current_item in current_fieldname:
					#Names
					line_name += current_item[0]+"::"
					#Descriptions
					line_desc += current_item[-1]+"::"
					#annotations
					if (annotations.has_key(current_item[0])):
						#ucd 
						current_ucd = annotations[current_item[0]][1]
						if ((len(current_ucd) == 0) or ("new UCD needed" in current_ucd)):
							line_ucd += "undefined::"
						else:
							line_ucd += current_ucd+"::"
						#utype
						current_utype = annotations[current_item[0]][2]
						if ((len(current_utype) == 0) or ("new UCD needed" in current_utype)):
							line_utype += "undefined::"
						else:
							line_utype += current_utype+"::"				
					else:
						line_ucd += "undefined::"
						line_utype += "undefined::"
						
			content.append(line_name[0:-2]+"\n")
			content.append(line_desc[0:-2]+"\n")
			content.append(line_ucd[0:-2]+"\n")
			content.append(line_utype[0:-2]+"\n")
			content.append(" \n")
			
		self.content = content
		return True
		
#Backup older file
	def do_backup(self,oldFilename="",VERBOSE=False):
		
		if (len(oldFilename) == 0):
			oldFile = self.propertyFile.replace(".txt",".old.txt")
		
		#Check if the property file already exists or not	
		#If it does, then make a copy of the file
		if (os.path.isfile(self.propertyFile)):
			os.rename(self.propertyFile,oldFile)
			if (VERBOSE): print self.propertyFile + " renamed to "+oldFile 
		
		return True
		
	def write_file(self):
		
		if (len(self.content) == 0):
			if (self.VERBOSE): print "Empty content!"
			return False			
		else:	
			if (self.VERBOSE): print "Writing "+self.propertyFile+"..."
		
		fw = open(self.propertyFile,'w')
		for current_line in self.content: fw.write(current_line)
		fw.close()		
		
		return True

	def __getattribute__(self,name):
		return self.__dict__[name]
 