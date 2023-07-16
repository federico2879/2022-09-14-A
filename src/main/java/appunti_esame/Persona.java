package appunti_esame;

import java.util.Objects;

public class Persona {
	
	String id;
	String coloreOcchi;
	String coloreCapelli;
	public Persona(String id, String coloreOcchi, String coloreCapelli) {
		super();
		this.id = id;
		this.coloreOcchi = coloreOcchi;
		this.coloreCapelli = coloreCapelli;
	}
	@Override
	public String toString() {
		return id;
	}
	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Persona other = (Persona) obj;
		return Objects.equals(id, other.id);
	}
	
	
}
