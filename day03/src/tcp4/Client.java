package tcp4;


import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;



public class Client {

	
	String ip;
	int port;
	
	Socket socket;

	
	
	
	public Client(String ip, int port) throws UnknownHostException, IOException {
		System.out.println("클라이언트 채팅창");
		this.ip = ip;
		this.port = port;
		connect();
		startClient();
	
	}


	private void connect() {
		while (true) {
			try {
				socket = new Socket(ip, port);
				if (socket != null && socket.isConnected()) {
					break;
				}
			} catch (IOException e) {
				System.out.println("Re-Try");
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e1) {
				}
			}

		}
		System.out.println("Connected");
	}

	
	
	public void startClient(){
		// TODO Auto-generated method stub

		
        Reader reader = new Reader(socket,"client");
        Writer writer = new Writer(socket,"client");
        
        Thread t1 = new Thread(reader);
        Thread t2 = new Thread(writer);
        
        t1.start();
        t2.start();
		
		
		
	}
	
	
	public static void main(String[] args) {
		Client client = null;

		try {
			client = new Client("203.246.196.46", 9999);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
