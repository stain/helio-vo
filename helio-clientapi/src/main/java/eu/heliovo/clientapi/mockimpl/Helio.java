package eu.heliovo.clientapi.mockimpl;

import java.io.IOException;
import java.util.Map;
import java.util.Scanner;

import eu.heliovo.clientapi.query.HelioParamQuery;
import eu.heliovo.clientapi.query.HelioParameter;
import eu.heliovo.clientapi.result.HelioJob;

public class Helio implements HelioParamQuery {

	public void login(String username) throws IOException {
		String name;
		int age;
		Scanner in = new Scanner(System.in);

		// Reads a single line from the console
		// and stores into name variable
		name = in.nextLine();

		// Reads a integer from the console
		// and stores into age variable
		age = in.nextInt();
		in.close();

		// Prints name and age to the console
		System.out.println("Name :" + name);
		System.out.println("Age :" + age);
		System.out.print("Password [pw]: ");
		for (int i = 0; i < 8; i++) {
			System.out.print("*");
			try {
				System.out.flush();
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// ignore
			}
		}
		System.out.println("");
	}

	@Override
	public HelioJob querySync(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HelioJob queryASync(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HelioParameter[] getParameterDescription(Map<String, Object> context) {
		// TODO Auto-generated method stub
		return null;
	}

}
