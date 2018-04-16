package database;

import java.util.LinkedList;
import java.util.Scanner;

import parserForSQL.Validity;

public class Test {
	public static void main(String[] args) {
		 Scanner scan =new Scanner(System.in);
		 System.out.print("<<");
		 String input = scan.nextLine();
		Validity obj = new Validity();
		while(!input.equals("end")){
			obj.validOrNot(input);
			System.out.print("<<");
			input = scan.nextLine();
		}
	
	}
}
