package tcp6;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server_ip extends Thread {

	ServerSocket serverSocket;
	boolean flag = true;
	boolean rflag = true;
	ArrayList<DataOutputStream> list = new ArrayList<>();

	public Server_ip() throws IOException {
		// Create ServerSocket
		serverSocket = new ServerSocket(8888);
		System.out.println("Server Ready...");

	}

	@Override
	public void run() {// startServer 메서드의 역할
		// Accept Client Connection
		try {
			while (flag) {
				System.out.println("Ready Accept");
				Socket socket = serverSocket.accept();
				// 서버는 기다리다가 client가 들어오면 receiver한테 socket던져서 초기화 작업함
				new Receiver(socket).start();

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Receiver Thread
	class Receiver extends Thread {

		InputStream in;
		DataInputStream din;
		OutputStream out;
		DataOutputStream dout;
		Socket socket;

		public Receiver(Socket socket) {

			try {
				this.socket = socket;
				in = socket.getInputStream();
				din = new DataInputStream(in);
				out = socket.getOutputStream();
				dout = new DataOutputStream(out);
				list.add(dout);
				System.out.println("Connected Count: " + list.size());// 현재 몇명이 접속 중인지 확인가능
			} catch (IOException e) {
				e.printStackTrace();
			}

		}// end Receiver construction

		@Override
		public void run() {

			try {
				while (rflag) {

					if (socket.isConnected() && din != null & din.available() > 0) {
						// &가 하나만있으면 양쪽을 다 비교하겠다. &&는 앞이 false면 뒤로 안가는데 &는 앞이 거짓이어도 뒤로가서 확인함
						String str = din.readUTF();
						if (str != null && str.equals("q")) {
							break;
						}
						System.out.println(str);// 오는거받아서 찍기
						sendAll(str);// 모든 접속자들에게 날리기//이 안에서 sender thread 시작함
						
					}

				}
				

			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				
				//스레드 소멸 전에 흔적지우기!
				list.remove(dout);
				System.out.println("Connected Count: "+list.size());
				try {
					Thread.sleep(200);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				// 스레드 소멸 전에 클라이언트의 흔적지우기!
				if (din != null) {
					try {
						din.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				if (dout != null) {
					try {
						dout.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				if (socket != null) {
					try {
						socket.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

			}

		}

	}

	public void sendAll(String msg) {
		Sender sender = new Sender();
		sender.setMsg(msg);
		sender.start();
	}

	// Sender Thread > Send Messages to all clients
	class Sender extends Thread {
		
		String msg;

		public void setMsg(String msg) {
			this.msg = msg;
		}

		@Override
		public void run() {

			try {
				if (!list.isEmpty() && list.size() >= 0) {
					for (DataOutputStream dout : list) {
						// 리스트 안에 있는 클라이언트들의 아웃풋스트림마다 메세지를 전달
						if(dout !=null) {
						dout.writeUTF(msg);
						}
					}
				}

			} catch (Exception e) {
				//e.printStackTrace();
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

