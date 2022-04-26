package projekt;
import java.util.*;

public class Test {

	public static int pouzeCelaCisla(Scanner sc) 
	{
		int cislo = 0;
		try
		{
			cislo = sc.nextInt();
		}
		catch(Exception e)
		{
			System.out.println("Nastala vyjimka typu "+e.toString());
			System.out.println("zadejte prosim cele cislo ");
			sc.nextLine();
			cislo = pouzeCelaCisla(sc);
		}
		return cislo;
	}
	


	public static void main(String[] args) {	
		// TODO Auto-generated method stub
		Scanner sc=new Scanner(System.in);
		Database databaze=new Database();
		SQL sql = new SQL();
		String type; String name; String surname; String date;int ID; int mark;
		int volba;
		String fileName;
		boolean run=true;
		while(run)
		{
			System.out.println("Vyberte pozadovanou cinnost:");
			System.out.println("1 .. Pridat studenta");
			System.out.println("2 .. Zadat studentovi novu znamku");
			System.out.println("3 .. Prepustenie studenta");
			System.out.println("4 .. Vypis informacie o studentovi");
			System.out.println("5 .. Spusti dovednost studenta");
			System.out.println("6 .. Vypis studentov v jednotlivych skupinach");
			System.out.println("7 .. Vypis priemeru v technickom a humanitnom oboru");
			System.out.println("8 .. Vypis celkoveho poctu studentov v jednotlivych skupinach");
			System.out.println("9 .. Nacitanie studentov zo suboru");
			System.out.println("10.. Ulozenie studentov do suboru");
			System.out.println("11.. Ulozenie informacii do SQL databaze");
			System.out.println("12.. Nacitanie informacii z SQL databaze ");
			System.out.println("13.. Ukoncenie programu");
			volba=pouzeCelaCisla(sc);
			switch(volba)
			{
				case 1:
					System.out.println("Zadejte skupinu (Tech, Hum, Kom): ");
					String regex = "^Tech$|^Hum$|^Kom$";
					type = sc.next();
					if(!type.matches(regex)) {
						System.err.println("Zly format skupiny");
						break;
					}
					System.out.println("Zadajte meno, priezvisko, datum narodenia vo formate DD/MM/YYYY");
					name = sc.next();
					surname = sc.next();
					date = sc.next();
					try {
						databaze.addStudent(type, name, surname, date,false);
						System.out.println("Student pridany");
					} catch (Exception e) {
	
						System.err.println(e.getMessage());
					}
					break;
				case 2:
					System.out.println("Zadajte ID a znamku studenta");
					try {
						ID = sc.nextInt();
						mark = sc.nextInt();
						databaze.newMark(ID, mark);
						System.out.println("Nova znamka zadana");
					} catch (Exception e) {
						System.err.println(e.getMessage());
					}
					break;
				case 3:
					System.out.println("Zadejte ID studenta");
					ID = sc.nextInt();
					try {
						databaze.removeStudent(ID);
						System.out.println("Student prepusteny");
					} catch (Exception e) {
						System.err.println(e.getMessage());
					}
					break;
				case 4:
					System.out.println("Zadejte ID studenta");
					try {
						ID = sc.nextInt();
						databaze.printStudent(ID);
					} catch (Exception e) {
						System.err.println(e.getMessage());
					}
					break;
					
				case 5:
					System.out.println("Zadejte ID studenta");
					try {
						ID = sc.nextInt();
						databaze.studentFunctions(ID);
					} catch (Exception e) {
						System.err.println(e.getMessage());
					}
					break;
				case 6:
					databaze.printAllStudents();
					break;
					
				case 7:
					databaze.printTechAndHumAverageMark();
					break;
				
				case 8:
					databaze.printSizesOfStudents();
					break;
					
				case 9:
					System.out.println("Zadejte meno subora studenta");
					fileName = sc.next();
					databaze.loadStudentsFromFile(fileName);
					break;
					
				case 10:
					System.out.println("Zadejte meno subora studenta");
					fileName = sc.next();
					databaze.saveStudentsToFile(fileName);
					break;
					
				case 11:
					sql.connect();
					sql.saveToSQL(databaze);
					sql.disconnect();
					System.out.println("Informacie ulozene do SQL databazy");
					break;
					
				case 12:
					sql.connect();
					try {
						databaze = sql.loadFromSQL(databaze);
					} catch (Exception e) {
						System.err.println(e.getMessage());
					}
					sql.disconnect();
					System.out.println("Informacie nacitane z SQL databazy");
					break;
					
				case 13:
					run = false;
					break;
			}
			
		}
	}

}

