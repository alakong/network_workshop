package tcp4;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {


	public static void main(String[] args) {
		// TODO Auto-generated method stub

		ServerSocket serversocket = null;
		Socket socket = null;
		int port = 7777;
		
		System.out.println("서버 채팅창");
		
		try {
			serversocket = new ServerSocket(port);
			socket = serversocket.accept();
			System.out.println(socket.getInetAddress()+"님 접속");
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
        Reader reader = new Reader(socket,"Server");
        Writer writer = new Writer(socket,"Server");
        
        Thread t1 = new Thread(reader);
        Thread t2 = new Thread(writer);
        
        t1.start();
        t2.start();
		
		
	}

}
