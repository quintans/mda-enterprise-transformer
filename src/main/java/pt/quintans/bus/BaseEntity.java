package pt.quintans.bus;

import java.util.UUID;

public class BaseEntity {	
	protected String id = UUID.randomUUID().toString();
	protected long version = 0L;
	
	protected boolean dirty;
	protected boolean persisted;
		
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public long getVersion() {
		return version;
	}

	public void setVersion(long version) {
		this.version = version;
	}

	public boolean _isDirty() {
		return dirty;
	}

	public void _setDirty(boolean dirty) {
		this.dirty = dirty;
	}

	public boolean _isPersisted() {
		return persisted;
	}

	public void _setPersisted(boolean persisted) {
		this.persisted = persisted;
	}	
}
