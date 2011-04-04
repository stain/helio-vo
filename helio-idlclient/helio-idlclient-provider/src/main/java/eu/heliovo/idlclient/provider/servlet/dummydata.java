package eu.heliovo.idlclient.provider.servlet;

import java.util.ArrayList;

public class dummydata {
	
	public static void fill(CatalogService cs)
	{
		float[] farray = new float[2];
		String[] sarray = new String[3];
		HelioField[] harray = new HelioField[2];
		farray[0] = 1.5f;
		farray[1] = 4.5f;
		sarray[0] = "1. String";
		sarray[1] = "2. String";
		sarray[2] = "3. String";
		
		HelioCatalog hc1 = new HelioCatalog();
		HelioField hf1 = new HelioField();
		HelioCatalog hc2 = new HelioCatalog();
		HelioField hf2 = new HelioField();
		ArrayList<HelioCatalog> hcl = new ArrayList<HelioCatalog>();
		ArrayList<HelioField> hfl = new ArrayList<HelioField>();
		

		hf1.setDescription("HelioField 1");
		hf1.setName("HF1");
		hf1.setMyFloat(1.3f);
		hf1.setMyArray(farray);
		hfl.add(hf1);
		hf2.setDescription("HelioField 2");
		hf2.setName("HF2");
		hf2.setMyFloat(1.7f);
		hf2.setMyArray(farray);
		hfl.add(hf2);
		harray[0] = hf1;
		harray[1] = hf2;

		hc1.setName("HC1");
		hc1.setDescription("HelioCatalog 1");
		hc1.setFields(hfl);
		hc1.setHelioArray(harray);
		hcl.add(hc1);
		hc2.setName("HC2");
		hc2.setDescription("HelioCatalog 2");
		hc2.setFields(hfl);
		hc2.setHelioArray(harray);
		hcl.add(hc2);
		
		cs.setName("Cat1");
		cs.setDescription("desc1");
		cs.setCatalogs(hcl);
		cs.setStringarray(sarray);
	}

}
