package Interface;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import config.config;

import Entidades.flower;
import Entidades.movTabuleiro;
import Entidades.particle;
import Entidades.position;

@SuppressWarnings("serial")
public class DrawSwarm extends JPanel {

	private position p;

	public DrawSwarm(config config) throws IOException {
		this.p = new position(config);
		this.p.rand();
	}

	protected void paintComponent(Graphics g) {

		super.paintComponent(g);
	}

	public void paint(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.RED);
		g.drawRect(0, 0, p.getConfig().getWidth(), p.getConfig().getHeigth());

		BufferedImage beeNormal = null;
		BufferedImage home = null;
		BufferedImage flower = null;
		try {
			beeNormal = ImageIO.read(new File("beeBall.png"));
			home = ImageIO.read(new File("homeBall.png"));
			flower = ImageIO.read(new File("flowerBall2.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Fail");
			e.printStackTrace();
		}

		ArrayList<particle> array = this.p.getArray();
		int passo = this.p.getConfig().getWidth() / this.p.getConfig().getNrParticoes();
		int i = 0;
		int j = 0;
		// procura por a chave do i
		g.setColor(Color.BLUE);
		g.setFont(new Font("Arial", 1, 9));
		for (; i < p.getConfig().getWidth(); i += passo) {

			for (j = 0; j < p.getConfig().getWidth(); j += passo) {
				movTabuleiro mov = this.p.getConfig().getValores().get(i+"-"+j);
				g.drawString("x: " + (int) mov.getX() + " y: " + (int)mov.getY(), i, j);
				if(mov.getX() == 0 || mov.getY() == 0) {
					g.drawRect(i, j, passo, passo);
				}
			}
		}
		
		g.drawRect(400, 400, 1, 1);

		for (particle part : array) {
			if (part.getType() == 1) {
				g.drawImage(home, part.getX(), part.getY(), null);

			} else {
				flower florPorx = this.getShortFlower(part);
				if (this.p.getConfig().getDebug() == 1) {
					g.drawLine(part.getX(), part.getY(), florPorx.getX(), florPorx.getY());
					g.drawString("" + part.getDebug(), part.getX(), part.getY());
				}
				g.drawImage(beeNormal, part.getX(), part.getY(), null);
			}
		}

		for (flower flor : this.p.getFlowers()) {
			g.drawImage(flower, flor.getX(), flor.getY(), null);
			if (this.p.getConfig().getDebug() == 1) {
				g.drawRect(flor.getX(), flor.getY(), 1, 1);
			}
			// g.drawRect(flor.getX() - 50, flor.getY() - 50, 100, 100);
		}
		this.p.moviment();

	}

	// Devolve a flor mais proxima
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

}
