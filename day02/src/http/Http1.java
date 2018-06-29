package http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;

public class Http1 {

	public static void main(String[] args) {
		
		/*InetAddress ia =null;
		ia=InetAddress.getByName("localhost");
		System.out.println(ia.getHostAddress());
		System.out.println(ia.getHostName());
		*/
		
		
		URL url= null;
		//��û���ּ�
		String address="http://127.0.0.1/index.html";
		try {
			url=new URL(address);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//�����͸� ������(�ѱ��� ������ ��찡 �����Ƿ� InputStream�ƴ϶� InputStreamReader��)
		//��� �ܾ� ������ ���� �׷��� line ������ �д� BufferedReader�� ������ ���� �� ����
		InputStreamReader in=null;
		BufferedReader br= null;
		StringBuffer sb=new StringBuffer();//StringBuffer�� �ѹ� ������ ���ڿ��� ������ �� �ִ�.
		String str=null;
		
		try {
			
			in=new InputStreamReader(url.openStream());
			br= new BufferedReader(in);
			while(true) {
				str=br.readLine();				
			
				//���ۿ� �߰��ϱ� ���� ���� ���ڿ��� �ΰ��� �ƴ��� üũ
				if(str==null) {
					break;
				}
				
				sb.append(str+"\n");
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}finally {
			try {
				
				//����� ���� �ݾƾ� ���������� close�� ��
				br.close();
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}//in�� �ڵ� �Ҹ�
		}
		
		System.out.println(sb.toString());
		
		

	}

}
