package tcp4;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public class Reader implements Runnable {
	
	
	Socket socket;
	InputStream in;
	DataInputStream dis;
	boolean flag;
	String name;
	
	public Reader() {
		
	}
	
	public Reader(Socket socket, String name) {
		this.socket = socket;
		flag = true;
		this.name = name;
		
		 try {
			in = this.socket.getInputStream();
			dis = new DataInputStream(in);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		
	}
	
	public void closeSocket(){
		if(socket!=null) {
			try {
				socket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		while(flag){
		   try {

			 String msg = dis.readUTF();
			 
			 System.out.println(msg);
			 
		} catch (IOException e) {
			// TODO Auto-generated catch block
			flag = false;
			closeSocket();
		}
		  
		  
		}
		
	}

	

}
