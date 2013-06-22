package Entidades;


public class movTabuleiro {

	private double x;
	private double y;
	
	public movTabuleiro(double x, double y) {
		super();
		this.x = x;
		this.y = y;
	}
	
	public movTabuleiro(movTabuleiro mov) {
		this.x = mov.getX();
		this.y = mov.getY();
	}
	
	public double getX() {
		return x;
	}
	public void setX(double x) {
		this.x = x;
	}
	public double getY() {
		return y;
	}
	public void setY(double y) {
		this.y = y;
	}

	@Override
	public String toString() {
		return "movTabuleiro [x=" + x + ", y=" + y + "]";
	}
	
	
	
	

}
