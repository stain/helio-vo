package eu.heliovo.shared.common.cis.hit;

import org.ietf.jgss.GSSCredential;
import org.ietf.jgss.GSSException;

import eu.heliovo.shared.common.cis.hit.info.HITInfo;

public class HIT {
	/**
	 * The HSC - High Security Component
	 * 
	 * It consists of a grid proxy certificate
	 * 
	 * */
	GSSCredential HSC = null;
	/**
	 * The HITInfo - The Information of the HIT
	 * 
	 * It contains all the information that is not contained in the high
	 * security component
	 * 
	 * */
	HITInfo hitInfo = null;

	public HIT() {
		super();
	}

	public GSSCredential getHSC() {
		return HSC;
	}

	public void setHSC(GSSCredential hSC) {
		HSC = hSC;
	}

	public HITInfo getHitInfo() {
		return hitInfo;
	}

	public void setHitInfo(HITInfo hitInfo) {
		this.hitInfo = hitInfo;
	}

	// public HIT(boolean secure)
	// {
	// super();
	// hitInfo = new HITInfo();
	// /*
	// * If the hit is secure, contact the my proxy to create the high security
	// component
	// */
	// }
	//
	// public HIT(boolean secure, String identity) throws HITException
	// {
	// super();
	// hitInfo = new HITInfo(identity);
	// /*
	// * If the hit is secure, contact the my proxy to create the high security
	// component
	// */
	// if(secure)
	// {
	// try
	// {
	// if(identity == CISValues.HelioAnonymousUser)
	// HSC = myproxy.getCredFromFile(CISValues.HelioAnonymousProxy);
	// else
	// HSC = myproxy.delegateProxyFromMyProxyServer(hitInfo.getCertId(),
	// hitInfo.getProxyServer(), hitInfo.getProxyPort());
	// }
	// catch (MyProxyManagerException e)
	// {
	// e.printStackTrace();
	// throw new HITException();
	// }
	// }
	// }
	//
	// public GSSCredential getHSC()
	// {
	// return HSC;
	// }
	//
	public String toString() {
		String str = new String("=======================================")
				+ "\n";
		if (HSC != null) {
			str += hitInfo.getUID() + " is a high security HIT" + "\n";
			try {
				str += "Distinguished Name : " + HSC.getName().toString()
						+ "\n";
				str += "Remaining Lifetime : " + HSC.getRemainingLifetime()
						+ "\n";
				str += "-----------------------------------------\n";
			} catch (GSSException e) {
				e.printStackTrace();
			}
		} else
			str += hitInfo.getUID() + " is an low security HIT" + "\n";

		str += hitInfo.toString() + "\n";
		str += "=======================================" + "\n";

		return str;
	}
	//
	// public void addProfile(String userProfile) throws HITException
	// {
	// try
	// {
	// hitInfo.addProfile(userProfile);
	// }
	// catch (HITInfoException e)
	// {
	// e.printStackTrace();
	// throw new HITException();
	// }
	// }
	//
	// public void setCertId(String user)
	// {
	// hitInfo.setCertId(user);
	// }
}
