package uk.co.methodical.file;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.springframework.dao.DataIntegrityViolationException;

import uk.co.methodical.database.MethodJDBCTemplate;
import uk.co.methodical.database.TowerJDBCTemplate;

public class TowerImporter {

	public static void importFile(String fileName) throws IOException {

		FileReader fr = new FileReader(fileName);
		BufferedReader br = new BufferedReader(fr);

		String recordAsStr;

		while ((recordAsStr = br.readLine()) != null) {
			process(recordAsStr);
		}

	}

	private static void process(String recordAsStr) {

		System.out.println(recordAsStr);
		String[] recordAsArr = recordAsStr.split("( *, *)");

		/*
		 * for (int i = 0; i < recordAsArr.length; ++i) { recordAsArr[i] =
		 * recordAsArr[i].trim(); }
		 */

		String city = null;
		String county = null;
		String country = null;
		String designation = null;
		Integer bells = null;
		String notes = null;

		city = recordAsArr[0];
		county = recordAsArr[1];

		if (recordAsArr.length == 4) {

			bells = Integer.parseInt(recordAsArr[2]);
			county = null;
			designation = recordAsArr[1];
			notes = recordAsArr[3];

		} else if (recordAsArr[2].equals("Wales") || recordAsArr[2].equals("Scotland") || recordAsArr[2].equals("USA")
				|| recordAsArr[2].equals("Australia") || recordAsArr[2].equals("New Zealand")
				|| recordAsArr[2].equals("Ireland") || recordAsArr[2].equals("Canada")
				|| recordAsArr[2].equals("South Africa")) {

			country = recordAsArr[2];

			try {
				bells = Integer.parseInt(recordAsArr[4]);
				designation = recordAsArr[3];
				notes = recordAsArr[5];

			} catch (NumberFormatException e) {
				bells = Integer.parseInt(recordAsArr[5]);
				designation = recordAsArr[3] + ", " + recordAsArr[4];
				notes = recordAsArr[6];
			}

		} else {
			country = "England";

			try {
				bells = Integer.parseInt(recordAsArr[3]);
				designation = recordAsArr[2];
				notes = recordAsArr[4];

			} catch (NumberFormatException e) {
				try {
					bells = Integer.parseInt(recordAsArr[4]);
					designation = recordAsArr[2] + ", " + recordAsArr[3];
					notes = recordAsArr[5];
				} catch (NumberFormatException e2) {
					bells = Integer.parseInt(recordAsArr[5]);
					designation = recordAsArr[2] + ", " + recordAsArr[3] + ", " + recordAsArr[4];
					notes = recordAsArr[6];
				}
			}
		}

		try {
			TowerJDBCTemplate.instance().addTower(city, county, country, designation, bells, notes);
		} catch (DataIntegrityViolationException e) {
			System.err.println("Failed inserting record " + recordAsStr);
			System.err.println("Found city        = " + city);
			System.err.println("Found county      = " + county);
			System.err.println("Found country     = " + country);
			System.err.println("Found designation = " + designation);
			System.err.println("Found bells       = " + bells);
			System.err.println("Found notes       = " + notes);
			e.printStackTrace();
		}

	}

}
