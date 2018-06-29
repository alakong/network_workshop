package http;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class Http4 {

	private static final String DEFAULT_ENCODING = "UTF-8";

	public static void main(String[] args) throws Exception {

		{
			CloseableHttpClient httpclient = HttpClients.createDefault();
			try {
				String url = "http://127.0.0.1/fileserver/upload.do";
				HttpPost httpPost = new HttpPost(url);
				// httpPost.setHeader( "Content-Type" , "application/octet-stream" );
				File f0 = new File("C:\\Users\\student\\Documents\\ppt.zip");

				ContentBody file0 = new FileBody(f0, ContentType.create("application/octet-stream"),
						URLEncoder.encode(f0.getName(), DEFAULT_ENCODING));
				ContentBody comment = new StringBody( "{}" , ContentType.APPLICATION_JSON );


				//ContentBody를 MultipartEntityBuilder에 담는다
				MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder.create();
				multipartEntityBuilder.addPart("file0", file0);
				multipartEntityBuilder.addPart("name", comment);//얘네를 기준으로 서버에서 파라미터를 셋팅하는 것
				
				HttpEntity reqHttpEntity = multipartEntityBuilder.build();
				httpPost.setEntity(reqHttpEntity);
				System.out.println("getRequestLine:" + httpPost.getRequestLine());
				CloseableHttpResponse response = httpclient.execute(httpPost);
				
				try
				{
					int statusCode = response.getStatusLine().getStatusCode();
					HttpEntity resHttpEntity = response.getEntity();
					if (statusCode == 200)
					{
						if (resHttpEntity != null)
						{
							System.out.println("Response Content-Length: " + resHttpEntity.getContentLength());
							System.out.println(
									"Response Content : " + IOUtils.toString(resHttpEntity.getContent(), "UTF-8"));
						}
					}
					else
					{
						System.out.println("StatusCode:" + statusCode);
					}
					System.out.println("getStatusLine:" + response.getStatusLine());
					if (resHttpEntity != null)
					{
						EntityUtils.consume(resHttpEntity);
					}
				}
				finally
				{
					try
					{
						response.close();
					}
					catch (IOException e)
					{
						e.printStackTrace();
					}
				}
			}
			catch (ClientProtocolException e)
			{
				e.printStackTrace();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
			finally
			{
				if (httpclient != null)
				{
					try
					{
						httpclient.close();
					}
					catch (IOException e)
					{

						e.printStackTrace();

					}

				}

			}

		}

	}

}
