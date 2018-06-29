package tcp1;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
   int port;
   ServerSocket serversocket;
   Socket socket;
   OutputStream out;
   OutputStreamWriter outw;
   boolean flag = true;
   
   public Server() throws IOException {
      port=7777;
      serversocket =new ServerSocket(port); //Ư����Ʈ�� �̿��Ͽ� ������Ȱ�� �Ұ��̴�. �ٸ������ ����������ϰ������� ���Ұ�
   }
   public void startServer() throws IOException{
      System.out.println("start server");
      System.out.println("Ready server");
      while(flag) {
         
      try {
      socket = serversocket.accept(); //Ŭ���̾�Ʈ ���� ��û������ ���ο� �������� ��ȯ->Ŭ���̾�Ʈ�� ������ ��ٸ��� ����
      System.out.println(socket.getInetAddress());
      out = socket.getOutputStream();
      outw = new OutputStreamWriter(out);
      outw.write("hi cilent, it's server!");   
      } catch (IOException e) {
         throw e;
      }
      finally {
         if(outw != null)
            outw.close();
         if(socket != null)
            socket.close();
      }
      }
      //System.out.println("end server");
   }
   
   public static void main(String[] args) {
      Server server=null;
      try {
         server = new Server();
         server.startServer();
      } catch (IOException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
   }
}