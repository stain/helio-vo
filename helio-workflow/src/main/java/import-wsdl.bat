@echo off
del /Q eu\heliovo\workflow\clients\*.*
wsimport -p eu.heliovo.workflow.clients.hec -keep -Xnocompile http://msslxw.mssl.ucl.ac.uk:8080/helio-hec/HelioTavernaService?wsdl
wsimport -p eu.heliovo.workflow.clients.ics -keep -Xnocompile http://msslxw.mssl.ucl.ac.uk:8080/helio-ics/HelioTavernaService?wsdl
wsimport -p eu.heliovo.workflow.clients.dpas -keep -Xnocompile http://msslxw.mssl.ucl.ac.uk:8080/helio-dpas/HelioTavernaService?wsdl