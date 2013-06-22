package config;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Random;

import DataBase.Connect;
import Entidades.movTabuleiro;

public class config {
	private double velocidade;
	private int nrParticle;
	private int nrFlores;
	private int tolerancia;
	private int width = 400;
	private int heigth = 400;
	private final int nrParticoes = 10;

	// Por causa da progressBAR
	private int totalParticoes = 1600;
	private int totalFeito = 0;

	private HashMap<String, movTabuleiro> valores;

	public config() {

	}

	public config(double velocidade, int nrFlores, int nrParticle, int tolerancia) {
		super();
		this.nrFlores = nrFlores;
		this.velocidade = velocidade;
		this.nrParticle = nrParticle;
		this.tolerancia = tolerancia;
		valores = new HashMap<String, movTabuleiro>();
	}

	public int getNrFlowers() {
		return this.nrFlores;
	}

	public double getVelocidade() {
		return velocidade;
	}

	public void setVelocidade(double velocidade) {
		this.velocidade = velocidade;
	}

	public int getNrParticle() {
		return nrParticle;
	}

	public void setNrParticle(int nrParticle) {
		this.nrParticle = nrParticle;
	}

	public int getTolerancia() {
		return tolerancia;
	}

	public void setTolerancia(int tolerancia) {
		this.tolerancia = tolerancia;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeigth() {
		return heigth;
	}

	public void setHeigth(int heigth) {
		this.heigth = heigth;
	}

	public void updateTab() throws IOException {
		Connect c = new Connect();
		// Assumir que a altura Ž igual ao comprimento

		int passo = this.width / nrParticoes;
		PrintWriter p = new PrintWriter(new File("tab.txt"));
		int i = 0;
		for (; i <= this.width; i += passo) {
			p.print("------------------------------------------------------\n");
			String linhaX = "";
			String linhaY = "";
			for (int j = 0; j <= this.heigth; j += passo) {
				movTabuleiro nova = c.getValores(i, i + passo, j, j + passo);
				nova.setX(nova.getX() % 5);
				nova.setY(nova.getY() % 5);
				linhaX += " x"+i+" :" + String.format("%+.2f\t", nova.getX()) + " |";
				linhaY += " y"+j+" :" + String.format("%+.2f\t", nova.getY()) + " |";
				totalFeito++;

				this.valores.put(i + "-" + j, nova);
			}
			p.print(linhaX + "\n");
			p.print(linhaY + "\n");
		}
		p.close();
	}

	public movTabuleiro getValores(int x, int y, int locate) {
		int i = 0;
		int j = 0;
		int passo = this.width / this.nrParticoes;

		// procura por a chave do i

		for (; i < x; i += passo)
			;
		for (; j < y; j += passo)
			;

		i -= passo;
		j -= passo;

		String key = i + "-" + j;
		// Para ajustar a velocidade
		try {
			Random r = new Random(System.currentTimeMillis());

			movTabuleiro mov = new movTabuleiro(this.valores.get(key));
			// int valorX = (r.nextInt(3) - 1);
			// int valorY = (r.nextInt(3) - 1);
			// Caso va para o interior faz o caminho direito
			// if (locate == 1) {
			mov.setX(mov.getX() * this.getVelocidade());
			mov.setY(mov.getY() * this.getVelocidade());

			if (mov.getX() != 0) {
				if (mov.getX() > 0) {
					mov.setX(mov.getX() + r.nextInt(3));
				} else {
					mov.setX(mov.getX() - r.nextInt(3));
				}
			}

			if (mov.getY() != 0) {
				if (mov.getY() > 0) {
					mov.setY(mov.getY() + r.nextInt(3));
				} else {
					mov.setY(mov.getY() - r.nextInt(3));
				}
			}
			// } else {
			// int valorX = (r.nextInt(3) - 1);
			// int valorY = (r.nextInt(3) - 1);
			// mov.setX(valorX + mov.getX() * this.getVelocidade());
			// mov.setY(valorY + mov.getY() * this.getVelocidade());
			// }
			return mov;
		} catch (NullPointerException e) {

		}
		return null;
	}

	public int getTotalParticoes() {
		return totalParticoes;
	}

	public void setTotalParticoes(int totalParticoes) {
		this.totalParticoes = totalParticoes;
	}

	public int getTotalFeito() {
		return totalFeito;
	}

	public void setTotalFeito(int totalFeito) {
		this.totalFeito = totalFeito;
	}

	public int getNrParticoes() {
		return nrParticoes;
	}
}
