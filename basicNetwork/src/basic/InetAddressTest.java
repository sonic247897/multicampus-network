package basic;

import java.net.InetAddress;
import java.net.UnknownHostException;

// InetAddress - IP를 표현하기 위해 모델링한 객체
public class InetAddressTest {
	public static void main(String[] args) {
		try {
			// getByName: 인터넷 도메인을 매개변수로 받음
			InetAddress ia = InetAddress.getByName(args[0]);
			System.out.println("ia.getHostName()=> "
										+ia.getHostName());
			System.out.println("ia.getHostAddress()=> "
										+ia.getHostAddress());
			
			System.out.println("InetAddress.getLocalHost()=> " 
									+ InetAddress.getLocalHost());
			
			InetAddress[] ialist = InetAddress.getAllByName(args[0]);
			for(int i=0; i<ialist.length; ++i) {
				System.out.println("ialist[i].getHostName()=> "
											+ialist[i].getHostName());
				System.out.println("ialist[i].getHostAddress()=> "
											+ialist[i].getHostAddress());
			}
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}
}
