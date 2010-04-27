/*
 * Created on Jul 6, 2004
 */
package org.egso.comms.pis.ant;

import java.rmi.RemoteException;

import javax.xml.rpc.Stub;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;
import org.egso.comms.nds.types.Application;
import org.egso.comms.nds.types.PIS;
import org.egso.comms.pis.types.Message;
import org.egso.comms.pis.wsdl.PIS_PortType;
import org.egso.comms.pis.wsdl.PIS_Service_Impl;

/**
 * Client class for use with Ant.
 * 
 * @author Nathan Ching (nc@mssl.ucl.ac.uk)
 * @version 2.4
 */
public class PISTask extends Task {

    private PIS_PortType pis = new PIS_Service_Impl().getPISPort();

    private Object subtask = null;

    public void execute() throws BuildException {
        // This is a workaround. Sun's SAAJ impl seems to use
        // Thread.currentThread().getContextClassLoader() to
        // get a MessageFactoryImpl which would otherwise result
        // in ClassNotFoundException.
        Thread.currentThread().setContextClassLoader(this.getClass().getClassLoader());
        if (subtask instanceof DeliverMessage) {
            deliverMessage((Message) subtask);
        } else if (subtask instanceof ListPIS) {
            listPIS();
        } else if (subtask instanceof RegisterApp) {
            registerApp((Application) subtask);
        } else if (subtask instanceof UpdateApp) {
            updateApp((Application) subtask);
        } else if (subtask instanceof DeregisterApp) {
            deregisterApp((Application) subtask);
        } else if (subtask instanceof ListApps) {
            listApps();
        } else if (subtask instanceof SyncDir) {
            syncDir();
        }
    }

    public void setEndpoint(String endpoint) {
        ((Stub) pis)._setProperty(Stub.ENDPOINT_ADDRESS_PROPERTY, endpoint);
    }

    public void addDeliverMessage(DeliverMessage deliverMessage) {
        this.subtask = deliverMessage;
    }

    private void deliverMessage(Message message) throws BuildException {
        try {
            pis.deliverMessage(message);
            log("OK - Delivered message");
        } catch (RemoteException e) {
            throw new BuildException(e);
        }
    }

    public void addListPIS(ListPIS listPIS) throws BuildException {
        this.subtask = listPIS;
    }

    public void listPIS() {
        try {
            PIS[] piss = pis.selectPISByName("%").getPis();
            log("OK - Listed registered PIS");
            for (int i = 0; i < piss.length; i++) {
                log(i + ". PIS id: " + piss[i].getId() + ", PIS endpoint: " + piss[i].getEndpoint());
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
            pis.registerApplication(application);
            log("OK - Registered application, application id " + application.getId() + ", application endpoint: " + application.getEndpoint());
        } catch (RemoteException e) {
            throw new BuildException(e);
        }
    }

    public void addUpdateApp(UpdateApp registerApp) {
        this.subtask = registerApp;
    }

    private void updateApp(Application application) throws BuildException {
        try {
            pis.updateApplication(application);
            log("OK - Updated application, application id " + application.getId() + ", application endpoint: " + application.getEndpoint());
        } catch (RemoteException e) {
            throw new BuildException(e);
        }
    }

    public void addDeregisterApp(DeregisterApp deregisterApp) {
        this.subtask = deregisterApp;
    }

    private void deregisterApp(Application application) throws BuildException {
        try {
            pis.deregisterApplication(application);
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
            Application[] applications = pis.selectApplicationsByName("%").getApplications();
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
            pis.synchronizeDirectory();
            log("OK - Directory synchronized");
        } catch (RemoteException e) {
            throw new BuildException(e);
        }
    }

    public static class DeliverMessage extends Message {
    }

    public static class ListPIS {
    }

    public static class RegisterApp extends Application {
    }

    public static class UpdateApp extends Application {
    }

    public static class DeregisterApp extends Application {
    }

    public static class ListApps {
    }

    public static class SyncDir {
    }

}