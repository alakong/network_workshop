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
	public void run() {// startServer �޼����� ����
		// Accept Client Connection
		try {
			while (flag) {
				System.out.println("Ready Accept");
				Socket socket = serverSocket.accept();
				// ������ ��ٸ��ٰ� client�� ������ receiver���� socket������ �ʱ�ȭ �۾���
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
				System.out.println("Connected Count: " + list.size());// ���� ����� ���� ������ Ȯ�ΰ���
			} catch (IOException e) {
				e.printStackTrace();
			}

		}// end Receiver construction

		@Override
		public void run() {

			try {
				while (rflag) {

					if (socket.isConnected() && din != null & din.available() > 0) {
						// &�� �ϳ��������� ������ �� ���ϰڴ�. &&�� ���� false�� �ڷ� �Ȱ��µ� &�� ���� �����̾ �ڷΰ��� Ȯ����
						String str = din.readUTF();
						if (str != null && str.equals("q")) {
							break;
						}
						System.out.println(str);// ���°Ź޾Ƽ� ���
						sendAll(str);// ��� �����ڵ鿡�� ������//�� �ȿ��� sender thread ������
						
					}

				}
				

			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				
				//������ �Ҹ� ���� ���������!
				list.remove(dout);
				System.out.println("Connected Count: "+list.size());
				try {
					Thread.sleep(200);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				// ������ �Ҹ� ���� Ŭ���̾�Ʈ�� ���������!
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
						// ����Ʈ �ȿ� �ִ� Ŭ���̾�Ʈ���� �ƿ�ǲ��Ʈ������ �޼����� ����
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

