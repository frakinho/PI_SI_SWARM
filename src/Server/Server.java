package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import Sensor.Acelerometro;

public class Server implements Runnable {
	private Acelerometro acel;
	private ServerSocket server;

	public Server(Acelerometro acel) throws IOException {
		this.acel = acel;
		this.server = new ServerSocket(12345);
		System.out.println("Servidor Pronto");
	}

	public void run() {
		try {
			while (true) {
				System.out.println("A espera de client:");
				Socket cliente = this.server.accept();
				System.out.println("Chegou Cliente");
				Thread t = new Thread(new read(cliente, acel));
				t.start();
			}
		} catch (IOException e) {
		}
	}
}
