package eu.heliovo.dpas.ie.services.directory.utils;

import eu.heliovo.dpas.ie.services.common.dao.interfaces.DPASDataProvider;
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
	      case PHOENIX__2: 
	          return new Phoenix2Provider();
	      case RHESSI__HESSI_GMR    : 
	          return new RhessiProvider();      
	      case RHESSI__HESSI_HXR    :   
	    	  return new RhessiProvider();
	      case HINODE__SOT_SP		:
	    	  return new SOT_SPProvider();
	      case HINODE__SOT_FG		:
	    	  return new SOT_FGProvider();
	      case HINODE__XRT		:
	    	  return new XRTProvider();
	      case NOBE__NORH		:
	    	  return new NOBEProvider();
	      default        : 
	          return null;
	    }
	  }
	   	  

	enum DirType{
		PHOENIX__2,RHESSI__HESSI_GMR,RHESSI__HESSI_HXR,HINODE__SOT_SP,HINODE__SOT_FG,HINODE__XRT,NOBE__NORH
	};


}
