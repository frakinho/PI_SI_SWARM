package Entidades;

import java.awt.geom.Point2D;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;

import config.config;

import DataBase.Connect;
import Sensor.Acelerometro;
import Server.Server;

public class position implements Observer {
	// Temos que adicionar abelhas RANDOM
	private ArrayList<particle> array;
	private ArrayList<flower> flowers;

	private int tolerancia = 8;
	private Acelerometro acel;
	private Connect bd = new Connect();
	private config config;

	// Particle position on Board, this information it is on two hash map
	public position(config config) throws IOException {
		this.config = config;
		this.config.updateTab();
		acel = new Acelerometro(0, 0, 0);
		Thread t = new Thread(new Server(acel));
		t.start();

		this.array = new ArrayList<particle>();
		this.flowers = new ArrayList<flower>();
		this.distrFlowers();
	}

	// Coloca as particulas RANDOM
	public void rand() {
		int i = 0;
		Random r = new Random();

		particle casa = new particle(config.getWidth() / 2, config.getHeigth() / 2, 1);

		this.addPoint(casa);
		while (i < config.getNrParticle()) {
			int particleX = r.nextInt(config.getWidth());
			int particleY = r.nextInt(config.getHeigth());
			particle particula = new particle(particleX, particleY, 0, "blackBall", 0);
			particula.setLocate(1);
			particula.setFlower(this.flowers);

			if (this.addPoint(particula)) {
				this.bd.addParticle(particula);
				i++;
			}
		}
		// Procura quem tem conhecimento das flroes, ou seja queme sta proximo
		this.findFlower();

	}

	// metodo para distribuir o nr de flores pelo cenario
	// Com base no nr de flores
	public void distrFlowers() {
		int dist = 200;
		int passo = 360 / this.config.getNrFlowers();
		double passoRad = ((2 * Math.PI * passo) / 360);

		this.flowers = new ArrayList<flower>();
		System.out.println("#######################################");
		for (int i = 0; i < this.config.getNrFlowers(); i++) {
			double ang = toNormalAngle(i * passo);

			double x = dist * Math.cos(Math.toRadians(ang)) + (this.config.getWidth() / 2);
			double y = dist * Math.sin(Math.toRadians(ang)) + (this.config.getHeigth() / 2);
			System.out.println("Passo: " + ang + " Flower: " + i + " X: " + (int) x + " Y: " + (int) y);
			this.flowers.add(new flower(i, (int) x, (int) y));
		}

	}

	public double toNormalAngle(double a) {
		if (a < 90)
			return 90 - a;
		else if (a < 180)
			return 360 - (a - 90);
		else if (a < 270)
			return 270 - (a - 180);
		else
			return 180 - (a - 270);
	}

	// Faz o movimento
	public void moviment() {
		this.findFlower();
		if (this.array.size() == 0) {

		} else {
			for (particle par : this.array) {
				if (par.getType() == 0) { // So faz o movimento para as outras
											// noa para o casa
					// Cria uma particula com a posi‹o futura para calcular
					// sobreposi›es
					movTabuleiro mov = this.config.getValores(par.getX(), par.getY(),par.getLocate());
					particle nova;
					// Nao faz nada caso nao exista movimento
					if (mov != null) {
						try {
							int andaX = ((int) mov.getX());
							int andaY = ((int) mov.getY());
							Point2D.Double adjust = this.adjustDirection(par);

							if (par.getLocate() == 0) {
								andaX *= -1;
								andaY *= -1;
							}

							//andaX += adjust.x;
							//andaY += adjust.y;
							//par.setDebug("" + par.getLocate());
							nova = new particle(par.getX() + andaX, par.getY() + andaY, par.getType(), par.getImage(), par.getDistCentro());
							
						} catch (NullPointerException e) {
							nova = par;
						}

						problema problema = subposition(nova, par);
						if (problema.collition()) { // Caso existam colis›es
													// Verifica para onde pode
													// andar
							particle parAux = problema.moveProblem(par);
							par = parAux;
							// if (!this.verifyCollision(parAux)) {
							// par = parAux;
							// }

						} // Caso nao existam colis›es faz movimento
							// normal
						par.setX(nova.getX());
						par.setY(nova.getY());

						if (par.getX() > 165 && par.getX() < 245 && par.getY() > 140 && par.getY() < 245) {
							par.saveFlower();
							par.setLocate(0);
						}
						if (par.getLocate() == 0 && this.verifyDestination(par)) {
							par.setLocate(1);
							// Para verificar se j‡ mudou tudo
							// Outra fun‹o troca o locate
						}
					}
				}
			}
		}
	}

	// Metodo para dar mais peso a um movimento
	public Point2D.Double adjustDirection(particle par) {
		// Neste primeiro caso so para as flores porque
		// Para dentro elas sabem andar direito
		Point2D.Double valor = new Point2D.Double(1, 1);
		if (this.flowers.size() > 0) {
			if (par.getLocate() == 0) {
				flower closeFlower = this.getShortFlower(par);
				// Depois de encontrar o flor mais perta para onde me dirigir
				// Vou ver as direcoes que tenho que tomar
				if (closeFlower != null) {
					// Vejo de que lado estou mais longe
					// Se do Y ou do X
					// E dou maior relevo ao mais longe
					double difX = Math.abs(par.getX() - closeFlower.getX());
					double difY = Math.abs(par.getY() - closeFlower.getY());
					// Aqui fica a diferenca entre o maior e o mais pequeno
					double dif = 0;
					if (difX > difY) {
						dif = difX / difY;

						// Aqui multi a dif pelo valor que tenho que andar no X
						valor.x = dif % 5;
						valor.y = 0;

						// Tenho que decidir que valor tenho que dar ao
						// deslocamento
						// Depende da posicao que a flor est‡ em relacao
						if (closeFlower.getX() < par.getX()) {
							valor.x *= -1;
						}
					} else {
						dif = difY / difX;
						valor.x = 0;
						valor.y = dif % 5;
						if (closeFlower.getY() < par.getY()) {
							valor.y *= -1;
						}
					}

				}
			}
		}

		return valor;
	}

	// Devolve a flor mais proxima
	// Depois ser‡ a mais proxima e a que esta livre
	public flower getShortFlower(particle par) {
		flower closeFlower = null;

		if (par.getFlower().size() > 0) {
			Point2D.Double part = new Point2D.Double(par.getX(), par.getY());
			// Vai pesquisar em todas as flores e ve qual esta mais proxima
			double aux = 0;
			// Variavel para verificar se aquela particula j‡ visitou todas as
			// Flores, se este valor passar para 1 quer dizer que
			// Esta particula j‡ visitou uma dada flor
			for (flower f : par.getFlower()) {

				Point2D.Double p = new Point2D.Double(f.getX(), f.getY());
				if (aux == 0) {
					aux = p.distance(part);
					closeFlower = f;
				} else {
					if (p.distance(part) < aux) {
						aux = p.distance(part);
						closeFlower = f;
					}
				}

			}
			// Quer dizer que nao visitou nenhuma flor

		}
		return closeFlower;
	}

	public boolean addPoint(particle e) {
		if (verifyCollision(e)) { // Retorna true caso colida
			System.out.println("Fora do cenario");
			return false;
		} else {
			this.array.add(e);
			// this.array.add(e);
			return true;
		}
	}

	// Verifica colisoes

	public boolean verifyCollision(particle e) {
		// the same size on two hashmap
		if (this.array.size() == 0) {
			return false; // nao colide
		}
		// Colis›es com a parede
		if (e.getX() + tolerancia > config.getWidth() || e.getX() - tolerancia < 0) {
			return true; // Pork colide ou sai do lado 0 ou do maior

		} else if (e.getY() + tolerancia > config.getHeigth() || e.getY() - tolerancia < 0) {
			return true; // Colide um dos Y pelo mesmo motivo do X
		}
		// Colis›es com outras particulas
		return subposition(e, null).collition(); // Devolve true se estiver
													// sobre outra
		// particula
	}

	// MŽtodo para saber se se vai subrepor

	public problema subposition(particle e, particle original) {
		problema problema = new problema();

		for (particle par : this.array) {
			if (original != null && original.equals(par)) {
				// NÜo faz nada, nÜo compara com ele proprio
			} else if (e.getX() < par.getX() + tolerancia && e.getX() > par.getX() - tolerancia) {

				if (e.getY() < par.getY() + tolerancia && e.getY() > par.getY() - tolerancia) {
					if (par.getX() > e.getX()) {
						problema.setVizinho_DIREITA(true);
					}
					if (par.getX() < e.getX()) {
						problema.setVizinho_ESQUERDA(true);
					}
					if (par.getY() > e.getY()) {
						problema.setVizinho_CIMA(true);
					}
					if (par.getY() < e.getY()) {
						problema.setVizinho_BAIXO(true);
					}
				}
			}
		}
		return problema;
	}

	// Calcula quem tem conhecimento das flores com uma tolerancia

	public void findFlower() {
		ArrayList<particle> flores = new ArrayList<particle>();

		for (particle par : this.array) {
			if (par.getType() == 2) {
				flores.add(par);
			}
		}

		for (particle par : this.array) {
			if (par.getType() == 0) {
				for (particle flor : flores) {
					if (par.getProximity(flor)) {
						flower flor1 = new flower(0, flor.getX(), flor.getY());
						par.addFlower(flor1);
					}
				}
			}
		}
	}

	// Calcula se a particula ja foi visitar a flor que tem

	public boolean verifyDestination(particle part) {

		int find = -1;

		if (part.getType() == 0) {
			for (flower flor : part.getFlower()) {
				if (part.getProximity(flor) == true) {
					find = 1;
					flor.setVisited(1);
				}
				// return part.getProximity(flor);

			}
		}
		if (find == 1)
			return true;

		return false;
	}

	// Get and Set Methods
	public int getTolerancia() {
		return tolerancia;
	}

	public void setTolerancia(int tolerancia) {
		this.tolerancia = tolerancia;
	}

	public ArrayList<flower> getFlowers() {
		return flowers;
	}

	public ArrayList<particle> getArray() {
		return array;
	}

	public void setArray(ArrayList<particle> array) {
		this.array = array;
	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
	}

}
