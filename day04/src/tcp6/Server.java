package tcp6; 

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

public class Server extends Thread {

	ServerSocket serverSocket;
	boolean flag = true;
	boolean rflag = true;
	HashMap<String, DataOutputStream> map = new HashMap<>();

	public Server() throws IOException {
		// Create ServerSocket ...
		serverSocket = new ServerSocket(8888);
		System.out.println("Ready Server...");
	}

	@Override
	public void run() {
		// Accept Client Connection ...
		try {
			while (flag) {
				System.out.println("Ready Accept");
				Socket socket = serverSocket.accept();
				new Receiver(socket).start();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	class Receiver extends Thread {//클라이언트별로 하나씩 생긴다. 클라이언트가 접속 할때마다 서버에서 하나씩 리시버만드는것

		InputStream in;
		DataInputStream din;
		OutputStream out;
		DataOutputStream dout;
		Socket socket;
		String ip;

		public Receiver(Socket socket) {
			try {
				this.socket = socket;
				in = socket.getInputStream();
				din = new DataInputStream(in);
				out = socket.getOutputStream();
				dout = new DataOutputStream(out);
				ip = socket.getInetAddress().getHostAddress();
				map.put(ip, dout);
				System.out.println("Connected Count:" + map.size());
			} catch (IOException e) {
				e.printStackTrace();
			}
		} // end Recevier

		@Override
		public void run() {
			try {
				while (rflag) {

					if (socket.isConnected() && din != null && din.available() > 0) {
						String str = din.readUTF();//사용자들의 메세지가 들어오면
						if (str != null && str.equals("q")) {//클라이언트 나갈때
							map.remove(ip);
							System.out.println("Connected Count:" + map.size());
							break;
						}
						sendAll("[" + ip + "]" + str);
					}
				}

			} catch (Exception e) {
				e.printStackTrace();
			} finally {

				try {
					Thread.sleep(200);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
				if (dout != null) {
					try {
						dout.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				if (din != null) {
					try {
						din.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				if (socket != null) {
					try {
						socket.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}

	}

	public void sendAll(String msg) {
		System.out.println(msg);//서버에서 한번 찍히고
		Sender sender = new Sender();
		sender.setMeg(msg);
		sender.start();
	}

	// Send Message All Clients
	class Sender extends Thread {

		String msg;

		public void setMeg(String msg) {
			this.msg = msg;
		}

		@Override
		public void run() {
			try {
				Collection<DataOutputStream> col = map.values();//맵에서 values를 꺼내서
				Iterator<DataOutputStream> it = col.iterator();//iterator로 돌린다~
				while (it.hasNext()) {
					it.next().writeUTF(msg);
				}

			} catch (Exception e) {
				// e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		Server server = null;
		try {
			server = new Server();
			server.start();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
