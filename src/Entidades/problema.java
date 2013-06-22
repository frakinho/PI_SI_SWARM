package Entidades;

import java.util.Random;

public class problema {
	private int id;
	private boolean vizinho_CIMA;
	private boolean vizinho_BAIXO;
	private boolean vizinho_DIREITA;
	private boolean vizinho_ESQUERDA;

	public problema() {

	}

	public problema(int id, boolean vizinho_CIMA, boolean vizinho_BAIXO,
			boolean vizinho_DIREITA, boolean vizinho_ESQUERDA) {
		super();
		this.id = id;
		this.vizinho_CIMA = vizinho_CIMA;
		this.vizinho_BAIXO = vizinho_BAIXO;
		this.vizinho_DIREITA = vizinho_DIREITA;
		this.vizinho_ESQUERDA = vizinho_ESQUERDA;
	}

	public problema(boolean vizinho_CIMA, boolean vizinho_BAIXO, boolean vizinho_DIREITA,
			boolean vizinho_ESQUERDA) {

		this.vizinho_CIMA = vizinho_CIMA;
		this.vizinho_BAIXO = vizinho_BAIXO;
		this.vizinho_DIREITA = vizinho_DIREITA;
		this.vizinho_ESQUERDA = vizinho_ESQUERDA;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public boolean getVizinho_CIMA() {
		return vizinho_CIMA;
	}

	public void setVizinho_CIMA(boolean vizinho_CIMA) {
		this.vizinho_CIMA = vizinho_CIMA;
	}

	public boolean getVizinho_BAIXO() {
		return vizinho_BAIXO;
	}

	public void setVizinho_BAIXO(boolean vizinho_BAIXO) {
		this.vizinho_BAIXO = vizinho_BAIXO;
	}

	public boolean getVizinho_DIREITA() {
		return vizinho_DIREITA;
	}

	public void setVizinho_DIREITA(boolean vizinho_DIREITA) {
		this.vizinho_DIREITA = vizinho_DIREITA;
	}

	public boolean getVizinho_ESQUERDA() {
		return vizinho_ESQUERDA;
	}

	public void setVizinho_ESQUERDA(boolean vizinho_ESQUERDA) {
		this.vizinho_ESQUERDA = vizinho_ESQUERDA;
	}

	@Override
	public String toString() {
		String result = "";
		if (vizinho_BAIXO) {
			result += "BAIXO ";
		} 
		if (vizinho_CIMA) {
			result += "CIMA ";
		} 
		if (vizinho_DIREITA) {
			result += "DIREITA ";
		} 
		if (vizinho_ESQUERDA) {
			result += "ESQUERDA ";
		}
		return result;
	}

	public boolean collition() {
		if (this.vizinho_BAIXO) {
			return true;
		}
		if (this.vizinho_CIMA) {
			return true;
		}
		if (this.vizinho_DIREITA) {
			return true;
		}
		if (this.vizinho_ESQUERDA) {
			return true;
		}
		return false;
	}
	
	public int returnValue(boolean valor) {
		if(valor) 
			return 1;
		return 0;
	}
	
	public particle moveProblem(particle e) {
		Random r = new Random();
		 
		
		int alphaX = r.nextInt(10);
		int alphaY = r.nextInt(10);

		int anda_X = ((-1) * returnValue(vizinho_CIMA) * alphaX) + (returnValue(vizinho_BAIXO) * alphaX);
		int anda_Y = (returnValue(vizinho_ESQUERDA) * alphaY) + ((-1) * returnValue(vizinho_DIREITA) * alphaY);
		
		
		
		//Anda para as flores
		if(e.getLocate() == 0) {
			anda_X *= -1;
			anda_Y *= -1;
		}
		
		e.setX(e.getX() + anda_X);
		e.setY(e.getY() + anda_Y);
		
		
		
		
		return e;
	}

}
