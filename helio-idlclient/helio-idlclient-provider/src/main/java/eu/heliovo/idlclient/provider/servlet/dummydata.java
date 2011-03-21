package eu.heliovo.idlclient.provider.servlet;

import java.util.ArrayList;

public class dummydata {
	
	public static void fill(CatalogService cs)
	{
		HelioCatalog hc1 = new HelioCatalog();
		HelioField hf1 = new HelioField();
		HelioCatalog hc2 = new HelioCatalog();
		HelioField hf2 = new HelioField();
		ArrayList<HelioCatalog> hcl = new ArrayList<HelioCatalog>();
		ArrayList<HelioField> hfl = new ArrayList<HelioField>();

		hf1.setDescription("HelioField 1");
		hf1.setName("HF1");
		hf1.setMyFloat(1.3f);
		hfl.add(hf1);
		hf2.setDescription("HelioField 2");
		hf2.setName("HF2");
		hf2.setMyFloat(1.7f);
		hfl.add(hf2);

		hc1.setName("HC1");
		hc1.setDescription("HelioCatalog 1");
		hc1.setFields(hfl);
		hcl.add(hc1);
		hc2.setName("HC2");
		hc2.setDescription("HelioCatalog 2");
		hc2.setFields(hfl);
		hcl.add(hc2);
		
		cs.setName("Cat1");
		cs.setDescription("desc1");
		cs.setCatalogs(hcl);
	}

}
