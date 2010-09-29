{\rtf1\ansi\ansicpg1252\cocoartf1038\cocoasubrtf250
{\fonttbl\f0\fmodern\fcharset0 Courier;}
{\colortbl;\red255\green255\blue255;}
\paperw11900\paperh16840\margl1440\margr1440\vieww9000\viewh8400\viewkind0
\deftab720
\pard\pardeftab720\ql\qnatural

\f0\fs24 \cf0 #!/usr/bin/perl\
\
# PERL MODULES\
use Mysql;\
\
# MYSQL CONFIG VARIABLES\
$host = "msslxt.mssl.ucl.ac.uk";\
$database = "helio";\
$tablename = "INSTRUMENTS";\
$user = "hello_admin";\
$pw = "majorca";\
\
# PERL CONNECT()\
$connect = Mysql->connect($host, $database, $user, $pw);\
\
# LISTDBS()\
@databases = $connect->listdbs;\
foreach $database (@databases) \{\
	print "$database<br />";\
\}\
}