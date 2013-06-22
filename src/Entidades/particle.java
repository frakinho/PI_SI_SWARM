package Entidades;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;

public class particle {

	private int id;
	private int x;
	private int y;
	private int type; // 0 normal - 1 casa - 2 flores
	private String image;
	private double distCentro;
	// Locate Ž ou casa ou flor
	// Locate 1 casa, 0 vai para as flores
	private int locate;
	// Provavelmente um arraylist de flores
	private ArrayList<flower> flower = new ArrayList<flower>();
	private String debug = "";

	public particle(int x, int y, int type) {
		this.x = x;
		this.y = y;
		this.type = type;
	}

	public particle(int x, int y, int type, String image, double distCentro) {
		this.x = x;
		this.y = y;
		this.type = type;
		this.image = image;
		this.distCentro = distCentro;
	}

	// Com todos os dados da BD
	public particle(int id, int x, int y, int type, String image, double distCentro) {
		this.id = id;
		this.x = x;
		this.y = y;
		this.type = type;
		this.image = image;
		this.distCentro = distCentro;
	}

	public void changeLog() {
		// Se o valor for 1 vai para casa, se for dois vai para a flor
		if (locate == 0)
			locate = 1;
		else
			locate = 0;

	}

	// Metodo para detetar proximidade
	public boolean getProximity(flower part) {
		int aux_X = this.x - part.getX();
		int aux_Y = this.y - part.getY();
		int tolerancia = 50;
		if (Math.abs(aux_X) < tolerancia && Math.abs(aux_Y) < tolerancia) {
			return true;
		}

		return false;
	}
	 
	// Metodo para detetar proximidade com tipo de entrada diferente FITA COLA
		public boolean getProximity(particle part) {
			int aux_X = this.x - part.getX();
			int aux_Y = this.y - part.getY();
			int tolerancia = 50;
			if (Math.abs(aux_X) < tolerancia && Math.abs(aux_Y) < tolerancia) {
				return true;
			}

			return false;
		}

	// GET AND SET
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

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public double getDistCentro() {
		return distCentro;
	}

	public void setDistCentro(double distCentro) {
		this.distCentro = distCentro;
	}

	@Override
	public String toString() {
		return "particle [id=" + id + ", x=" + x + ", y=" + y + ", type=" + type + ", image=" + image + ", distCentro=" + distCentro + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((image == null) ? 0 : image.hashCode());
		result = prime * result + type;
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
		particle other = (particle) obj;
		if (image == null) {
			if (other.image != null)
				return false;
		} else if (!image.equals(other.image))
			return false;
		if (type != other.type)
			return false;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}

	public int getLocate() {
		return locate;
	}

	public void setLocate(int locate) {
		this.locate = locate;
	}

	public String getDebug() {
		return debug;
	}

	public void setDebug(String debug) {
		this.debug = debug;
	}

	// Quem sabe quarda a informa‹o num ficheiro
	public void saveFlower() {
		ArrayList<flower> flores = updateFlower();
		/**
		// Acrescenta as flores guardados por outras a lista
		if (flores != null) {
			for (flower par : flores) {
				this.flower.add(par);
			}
		}

		try {
			PrintWriter p = new PrintWriter(new FileWriter("flores1.txt", true));
			FileOutputStream fout = new FileOutputStream(new File("fores.txt"));
			ObjectOutputStream out = new ObjectOutputStream(fout);
			for (flower par : this.flower) {
				p.append("" + par);
			}
			p.close();
			fout.close();

			// out.writeObject(this.flower);
			out.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/

	}

	public void addFlower(flower flower) {
		// if(!this.flower.contains(flower))
		if (!this.flower.contains(flower))
			this.flower.add(flower);
	}

	public ArrayList<flower> updateFlower() {
		/*try {
			// FileInputStream fis = new FileInputStream("flores.dat");
			// ObjectInputStream ois = new ObjectInputStream(fis);
			// ArrayList<flower> flores = (ArrayList<flower>) ois.readObject();
			// ois.close();
			ArrayList<flower> flores = new ArrayList<flower>();
			BufferedReader f = new BufferedReader(new FileReader(new File("flores1.txt")));
			
			f.close();
			return flores;

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		return null;

	}

	public ArrayList<flower> getFlower() {
		return this.flower;
	}

	public void setFlower(ArrayList<flower> flower) {
		this.flower = flower;
	}
}
