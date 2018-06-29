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
		//요청할주소
		String address="http://127.0.0.1/index.html";
		try {
			url=new URL(address);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//데이터를 가져올(한글을 가져올 경우가 많으므로 InputStream아니라 InputStreamReader로)
		//얘는 단어 단위로 읽음 그래서 line 단위로 읽는 BufferedReader로 빠르게 읽을 수 있음
		InputStreamReader in=null;
		BufferedReader br= null;
		StringBuffer sb=new StringBuffer();//StringBuffer은 한번 설정된 문자열도 수정할 수 있다.
		String str=null;
		
		try {
			
			in=new InputStreamReader(url.openStream());
			br= new BufferedReader(in);
			while(true) {
				str=br.readLine();				
			
				//버퍼에 추가하기 전에 읽은 문자열이 널값이 아닌지 체크
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
				
				//여기로 빼서 닫아야 오류가나도 close가 됨
				br.close();
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}//in도 자동 소멸
		}
		
		System.out.println(sb.toString());
		
		

	}

}
