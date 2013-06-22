package Server;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

import Sensor.Acelerometro;

public class read implements Runnable{
	private Acelerometro acel;
	private Socket client;
	
	
	public read(Socket client,Acelerometro acel) {
		this.client = client;
		this.acel = acel;
	}
	
	public void run() {
		try {
			DataInputStream d = new DataInputStream(this.client.getInputStream());
			
			while(true) {
				
				String comand[] = d.readUTF().split("/");
				this.acel.setX(Integer.parseInt(comand[0]));
				this.acel.setY(Integer.parseInt(comand[1]));
				this.acel.setZ(Integer.parseInt(comand[2]));
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
}
