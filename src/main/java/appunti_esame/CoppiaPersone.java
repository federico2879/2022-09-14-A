package appunti_esame;

import java.util.Objects;

public class CoppiaPersone {
	
	Persona p1; 
	Persona p2;
	public CoppiaPersone(Persona p1, Persona p2) {
		super();
		this.p1 = p1;
		this.p2 = p2;
	}
	@Override
	public int hashCode() {
		return Objects.hash(p1, p2);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CoppiaPersone other = (CoppiaPersone) obj;
		if(Objects.equals(p1, other.p1) && Objects.equals(p2, other.p2)
				|| Objects.equals(p1, other.p2) && Objects.equals(p2, other.p1))
			return true;
		return false;
	}
	@Override
	public String toString() {
		return "("+p1+" : "+p2+")";
	}
	
	
}
