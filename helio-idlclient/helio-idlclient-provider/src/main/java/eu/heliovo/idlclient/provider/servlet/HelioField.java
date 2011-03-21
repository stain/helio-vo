package eu.heliovo.idlclient.provider.servlet;


public class HelioField {

	private String name;
	
	private String description;
	
	private float MyFloat;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public float getMyFloat() {
		return MyFloat;
	}

	public void setMyFloat(float myFloat) {
		MyFloat = myFloat;
	}
}
