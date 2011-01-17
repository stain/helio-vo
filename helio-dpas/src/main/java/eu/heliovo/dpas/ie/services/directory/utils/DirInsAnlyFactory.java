package eu.heliovo.dpas.ie.services.directory.utils;

import eu.heliovo.dpas.ie.services.common.dao.interfaces.DPASDataProvider;
import eu.heliovo.dpas.ie.services.directory.provider.HalphaProvider;
import eu.heliovo.dpas.ie.services.directory.provider.NOBEProvider;
import eu.heliovo.dpas.ie.services.directory.provider.Phoenix2Provider;
import eu.heliovo.dpas.ie.services.directory.provider.RhessiProvider;
import eu.heliovo.dpas.ie.services.directory.provider.SOT_FGProvider;
import eu.heliovo.dpas.ie.services.directory.provider.SOT_SPProvider;
import eu.heliovo.dpas.ie.services.directory.provider.XRTProvider;

public abstract class DirInsAnlyFactory {
	  
	  public static DPASDataProvider getDirProvider(
	    String instrument) {
		  DirType type=DirType.valueOf(instrument);
	    switch (type) {
	      case PHOENIX_2: 
	          return new Phoenix2Provider();
	      case HESSI_GMR    : 
	          return new RhessiProvider();      
	      case HESSI_HXR    :   
	    	  return new RhessiProvider();
	      case SP4D		:
	    	  return new SOT_SPProvider();
	      case FGIV		:
	    	  return new SOT_FGProvider();
	      case XRT		:
	    	  return new XRTProvider();
	      case NORH		:
	    	  return new NOBEProvider();
	      case HALPHA		:
	    	  return new HalphaProvider();
	      default        : 
	          return null;
	    }
	  }
	   	  

	enum DirType{
		PHOENIX_2,HESSI_GMR,HESSI_HXR,SP4D,FGIV,XRT,NORH,HALPHA
	};


}
