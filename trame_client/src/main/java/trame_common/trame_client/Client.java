package trame_common.trame_client;

import java.util.Scanner;

import trame_common.Credential;

/**
 * 
 * @author KOUNTA
 *
 */

public class Client {

	private static Scanner in;

	public static void main(String[] args) {
		Credential cred;
		String login;
		String password;
		in = new Scanner(System.in);
		
		System.out.print("Enter your login : ");
		login = in.nextLine();
		System.out.print("Enter your password : ");
		password = in.nextLine();
		
		cred = new Credential(login, password);
		new RequestorRest().authentificate(cred);
		
		System.out.println("Press any key to stop process");
		in.nextLine();
		
	}	
}
