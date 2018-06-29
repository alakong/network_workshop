package tcp4_1;

import java.awt.Button;
import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Panel;
import java.awt.TextField;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client {

	boolean flag = true;
	String address = "127.0.0.1";
	Socket socket;
	Scanner scanner;
	
	Frame f;
	Button bt1,bt2,bt3,bt4,bt5,bt6;
	
	Panel p;
	TextField tf1,tf2,tf3;

	public Client() throws UnknownHostException, IOException {
		socket = new Socket(address, 8888);
		System.out.println("Connected Server ..");
	}

	public void makeUi() {
		
		bt1= new Button("20C");
		bt2= new Button("100km");
		bt3= new Button("1500rpm");
		bt4= new Button("open Window");
		bt5= new Button("30L");
		bt6= new Button("18200km");
		
		bt1.setFont(new Font("serif",Font.BOLD,30));
		bt1.setBackground(Color.black);
		bt1.setForeground(Color.YELLOW);
		
		
     
		f = new Frame();
		f.setLayout(new GridLayout(2, 3));
		f.add(bt1);
		f.add(bt2);
		f.add(bt3);
		f.add(bt4);
		f.add(bt5);
		
		p=new Panel();
		p.setLayout(new GridLayout(3,1));
		tf1=new TextField("Start Car");
		tf2=new TextField("Speed is 100Km");
		tf3=new TextField("Open Window");
		p.add(tf1);	p.add(tf2);	p.add(tf3);
		f.add(p);




		
		f.setLocation(0, 0);
		f.setSize(1000, 600);
		f.setVisible(true);
		f.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {//종료시키기
				
				f.dispose();
				System.exit(0);
				
			}
			
		});
	}

	public void startClient() throws Exception {
		makeUi();


		new Receiver(socket).start();
		Sender sender = new Sender(socket);
		scanner = new Scanner(System.in);
		while (flag != false) {

			System.out.println("클라이언트 입력 하세요:");

			String str = scanner.nextLine();
			if (str.trim().equals("q")) {
				Thread t = new Thread(sender);
				sender.setSendMsg("q");
				t.start();
				flag = false;
				scanner.close();
				break;
			}
			Thread t = new Thread(sender);
			sender.setSendMsg(str);
			t.start();
		}
		Thread.sleep(1000);
		socket.close();

		// System.exit(0);

	}

	class Sender implements Runnable {
		Socket socket;
		OutputStream out;
		DataOutputStream outw;
		String sendMsg;

		public Sender(Socket socket) throws IOException {
			this.socket = socket;
			out = socket.getOutputStream();
			outw = new DataOutputStream(out);
		}

		public void setSendMsg(String sendMsg) {
			this.sendMsg = sendMsg;
		}

		@Override
		public void run() {
			try {
				if (outw != null) {
					outw.writeUTF(sendMsg);
				}

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	class Receiver extends Thread {
		Socket socket;
		InputStream in;
		DataInputStream inr;

		public Receiver(Socket socket) throws IOException {
			this.socket = socket;
			in = socket.getInputStream();
			inr = new DataInputStream(in);
		}

		@Override
		public void run() {
			while (inr != null) {
				try {

					String str = inr.readUTF();
					bt1.setLabel(str);
					System.out.println(str);
					if (str.trim().equals("q")) {
						inr.close();
						break;
					}
				} catch (Exception e) {
					System.out.println("Server Closed");
					break;
				}
			}

			try {
				Thread.sleep(1000);
				flag = false;
				socket.close();
				System.exit(0);
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}

	public static void main(String args[]) {
		try {
			new Client().startClient();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
