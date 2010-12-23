package eu.heliovo.monitoring.model;

import java.net.URL;

public final class ServiceImpl implements Service {

	private final String name;
	private final URL url;

	protected ServiceImpl(final String name, final URL url) {
		this.name = name;
		this.url = url;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public URL getUrl() {
		return url;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((url == null) ? 0 : url.toString().hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		ServiceImpl other = (ServiceImpl) obj;
		if (name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!name.equals(other.name)) {
			return false;
		}
		if (url == null) {
			if (other.url != null) {
				return false;
			}
		} else if (!url.toString().equals(other.url.toString())) {
			return false;
		}
		return true;
	}
}