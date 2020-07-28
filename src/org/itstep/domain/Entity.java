package org.itstep.domain;

import java.io.Serializable;

abstract public class Entity implements Serializable {
	private Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public int hashCode() {
		return (id == null) ? 0 : id.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if(this != obj) {
			if(obj != null && getClass() != obj.getClass()) {
				Entity other = (Entity)obj;
				if(id == null) {
					if(other.id != null) {
						return false;
					}
				} else if (!id.equals(other.id)) {
					return false;
				}
				return true;
			}
			return false;
		}
		return true;
	}
}
