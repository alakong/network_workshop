package http;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class Http2 {
	public static void main(String[] args) throws Exception {
		String name="����";
		name=URLEncoder.encode(name,"UTF-8");
		URL url= new URL("http://127.0.0.1/login?id=qq&pwd=11&name="+name);
		//"?id=qq&pwd=11&name=�ȴ�" �̷��� ���� ��Ʈ���� �ѱ��� �� ä�� �ٷ� url ��ü�� ������ ������ ��
		//�׷��� �ѱ� �κ��� ���ڵ��� �Ŀ� �־��־����
		URLConnection con= url.openConnection();
		con.setConnectTimeout(5000);//5�ʵ��� ������ ������ ���ܹ߻�
		
		InputStream in=con.getInputStream();
		InputStreamReader ir= new InputStreamReader(in,"UTF-8");
		BufferedReader br= new BufferedReader(ir);
		
		String str=br.readLine();//���� ������ �޾ƿ;� �ϴ� ��� ��Ʈ�����ۻ��
		System.out.println(str);
		br.close();
	}
}
