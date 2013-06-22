package Entidades;

import java.io.Serializable;

public class flower implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private int visited;
	private int x;
	private int y;

	public flower(int id, int x, int y) {
		this.id = id;
		this.x = x;
		this.y = y;
	}

	public flower(int id, int x, int y, int visited) {
		this.id = id;
		this.x = x;
		this.y = y;
		this.visited = visited;
	}

	// Metodo para detetar proximidade
	public boolean getProximity(flower part) {
		int aux_X = this.x - part.getX();
		int aux_Y = this.y - part.getY();
		int tolerancia = 20;
		if (Math.abs(aux_X) < tolerancia && Math.abs(aux_Y) < tolerancia) {
			return true;
		}

		return false;
	}

	public int getVisited() {
		return this.visited;
	}

	public void setVisited(int visited) {
		this.visited = visited;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + x;
		result = prime * result + y;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		flower other = (flower) obj;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "flower [id=" + id + ", x=" + x + ", y=" + y + "]\n";
	}

}
