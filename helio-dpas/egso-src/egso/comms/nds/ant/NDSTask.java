/*
 * Created on Jul 6, 2004
 */
package org.egso.comms.nds.ant;

import java.rmi.RemoteException;

import javax.xml.rpc.Stub;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;
import org.egso.comms.nds.types.Application;
import org.egso.comms.nds.types.PIS;
import org.egso.comms.nds.wsdl.NDS_PortType;
import org.egso.comms.nds.wsdl.NDS_Service_Impl;

/**
 * Client class for use with Ant.
 * 
 * @author Nathan Ching (nc@mssl.ucl.ac.uk)
 * @version 2.4
 */
public class NDSTask extends Task {

    private NDS_PortType nds = new NDS_Service_Impl().getNDSPort();

    private Object subtask = null;

    public void execute() throws BuildException {
        // This is a workaround. Sun's SAAJ impl seems to use
        // Thread.currentThread().getContextClassLoader() to
        // get a MessageFactoryImpl which would otherwise result
        // in ClassNotFoundException.
        Thread.currentThread().setContextClassLoader(this.getClass().getClassLoader());
        if (subtask instanceof RegisterPIS) {
            registerPIS((PIS) subtask);
        } else if (subtask instanceof UpdatePIS) {
            updatePIS((PIS) subtask);
        } else if (subtask instanceof DeregisterPIS) {
            deregisterPIS((PIS) subtask);
        } else if (subtask instanceof ListPIS) {
            listPIS();
        } else if (subtask instanceof RegisterApp) {
            registerApp((Application) subtask);
        } else if (subtask instanceof DeregisterApp) {
            deregisterApp((Application) subtask);
        } else if (subtask instanceof ListApps) {
            listApps();
        } else if(subtask instanceof SyncDir) { 
            syncDir();
        }
    }

    public void setEndpoint(String endpoint) {
        ((Stub) nds)._setProperty(Stub.ENDPOINT_ADDRESS_PROPERTY, endpoint);
    }

    public void addRegisterPIS(RegisterPIS registerPIS) {
        this.subtask = registerPIS;
    }

    public void registerPIS(PIS pis) throws BuildException {
        try {
            nds.registerPIS(pis);
            log("OK - Registered PIS, PIS id: " + pis.getId() + ", PIS endpoint: " + pis.getEndpoint());
        } catch (RemoteException e) {
            throw new BuildException(e);
        }
    }

    public void addUpdatePIS(UpdatePIS updatePIS) {
        this.subtask = updatePIS;
    }

    public void updatePIS(PIS pis) throws BuildException {
        try {
            nds.updatePIS(pis);
            log("OK - Updated PIS, PIS id: " + pis.getId() + ", PIS endpoint: " + pis.getEndpoint());
        } catch (RemoteException e) {
            throw new BuildException(e);
        }
    }

    public void addDeregisterPIS(DeregisterPIS deregisterPIS) {
        this.subtask = deregisterPIS;
    }

    public void deregisterPIS(PIS pis) {
        try {
            nds.deregisterPIS(pis);
            log("OK - Deregistered PIS, PIS id: " + pis.getId() + ", PIS endpoint: " + pis.getEndpoint());
        } catch (RemoteException e) {
            throw new BuildException(e);
        }
    }

    public void addListPIS(ListPIS listPIS) throws BuildException {
        this.subtask = listPIS;
    }

    public void listPIS() {
        try {
            PIS[] pis = nds.selectPISByName("%").getPis();
            log("OK - Listed registered PIS");
            for (int i = 0; i < pis.length; i++) {
                log(i + ". PIS id: " + pis[i].getId() + ", PIS endpoint: " + pis[i].getEndpoint());
            }
        } catch (RemoteException e) {
            throw new BuildException(e);
        }
    }

    public void addRegisterApp(RegisterApp registerApp) {
        this.subtask = registerApp;
    }

    private void registerApp(Application application) throws BuildException {
        try {
            nds.registerApplication(application);
            log("OK - Registered application, application id " + application.getId() + ", application endpoint: " + application.getEndpoint());
        } catch (RemoteException e) {
            throw new BuildException(e);
        }
    }

    public void addDeregisterApp(DeregisterApp deregisterApp) {
        this.subtask = deregisterApp;
    }

    private void deregisterApp(Application application) throws BuildException {
        try {
            nds.deregisterApplication(application);
            log("OK - Deregistered application, application id " + application.getId() + ", application endpoint: " + application.getEndpoint());
        } catch (RemoteException e) {
            throw new BuildException(e);
        }
    }

    public void addListApps(ListApps listApps) {
        this.subtask = listApps;
    }

    private void listApps() throws BuildException {
        try {
            Application[] applications = nds.selectApplicationsByName("%").getApplications();
            log("OK - Listed registered applications");
            for (int i = 0; i < applications.length; i++) {
                log(i + ". application id: " + applications[i].getId() + ", application endpoint: " + applications[i].getEndpoint());
            }
        } catch (RemoteException e) {
            throw new BuildException(e);
        }
    }
    
    public void addSyncDir(SyncDir syncDir) {
        this.subtask = syncDir;
    }

    private void syncDir() throws BuildException {
        try {
            nds.synchronizeDirectory();
            log("OK - Directory synchronized");
        } catch (RemoteException e) {
            throw new BuildException(e);
        }
    }
    public static class RegisterPIS extends PIS {
    }

    public static class UpdatePIS extends PIS {
    }

    public static class DeregisterPIS extends PIS {
    }

    public static class ListPIS {
    }

    public static class RegisterApp extends Application {
    }

    public static class DeregisterApp extends Application {
    }

    public static class ListApps {
    }

    public static class SyncDir {        
    }

}