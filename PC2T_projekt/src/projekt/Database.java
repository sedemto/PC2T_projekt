package projekt;
import java.util.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;


public class Database {
	
	private TreeMap<Integer,Student> archive;
	private TreeMap<String,Tech> tech;
	private TreeMap<String,Hum>  hum;
	private TreeMap<String,Kom>  kom;
	private int id;
	private int file_id;
	
	public Database() {
		this.archive = new TreeMap<Integer,Student>();
		this.tech = new TreeMap<String,Tech>();
		this.hum = new TreeMap<String,Hum>();
		this.kom = new TreeMap<String,Kom>();
	}
	public void setFileID(int id)
	{
		this.file_id = id;
	}
	public TreeMap<Integer,Student> getAll() {
		return archive;
	}
	// a
	public void addStudent(String type,String name,String surname,String date,Boolean fileID) throws Exception {
		Student student;
		if(fileID)
			id = file_id;
		else
			if(!archive.isEmpty())
				id = archive.lastKey()+1;
			else
				id = 1;
		String regex = "^([0-2][0-9]||3[0-1])/(0[0-9]||1[0-2])/([0-9][0-9])?[0-9][0-9]$";
		if(!date.matches(regex)) {
			System.out.println(id);
			System.out.println(type);
			System.out.println(name);
			System.out.println(surname);
			System.out.println(date);
			throw new Exception("Zly format datumu");
		}
		if(type.equals("Tech")){
			tech.put(surname, new Tech(name,surname,date));
			tech.get(surname).setID(id);
			student = new Tech(name,surname,date);
		}
		else if(type.equals("Hum")) {
			hum.put(surname, new Hum(name,surname,date));
			hum.get(surname).setID(id);
			student = new Hum(name,surname,date);
		}
		else {
			kom.put(surname, new Kom(name,surname,date));
			kom.get(surname).setID(id);
			student = new Kom(name,surname,date);
		}
		student.setID(id);
		archive.put(id, student);
		
	}
	// b
	public void newMark(int ID,int mark) throws Exception {
		if(mark > 5 || mark < 1)
			throw new Exception("Znamka musi byt od 1-5");
		if(archive.get(ID) == null)
			throw new Exception("Student neni v databazi");
		if(archive.get(ID) instanceof Tech)
			tech.get(archive.get(ID).getSurname()).addMark(mark);
		else if (archive.get(ID) instanceof Hum)
			hum.get(archive.get(ID).getSurname()).addMark(mark);
		else if (archive.get(ID) instanceof Kom)
			kom.get(archive.get(ID).getSurname()).addMark(mark);
		archive.get(ID).addMark(mark);
		
	}
	// c
	public void removeStudent(int ID) throws Exception {
		if(archive.get(ID) == null)
			throw new Exception("Student neni v databazi");
		if(archive.get(ID) instanceof Tech)
			tech.remove(archive.get(ID).getSurname());
		else if (archive.get(ID) instanceof Hum)
			hum.remove(archive.get(ID).getSurname());
		else if (archive.get(ID) instanceof Kom)
			kom.remove(archive.get(ID).getSurname());
		
		archive.remove(ID);
		
	}
	// d
	public void printStudent(int ID) throws Exception{
		if(archive.get(ID) == null)
			throw new Exception("Student neni v databazi");
		var student = archive.get(ID);
		System.out.println("meno: "+student.getName()+" priezvisko: "+student.getSurname()
		+" rok narodenia: "+student.getDate()+" priemer: "+student.getAverageMark());
	}
	// e
	public void studentFunctions(int ID) throws Exception {
		if(archive.get(ID) == null)
			throw new Exception("Student neni v databazi");
		var student = archive.get(ID);
		student.function();
	}
	// f
	public void printAllStudents() {
		System.out.println("Studenti technickeho oboru:");
		for(var student : tech.values()) {
			System.out.println("ID: "+student.getID()+ " Meno: "+student.getName()+" Priezvisko: "+student.getSurname()
			+" Rok narodenia: "+student.getDate()+" Priemer: "+student.getAverageMark());
		}
		System.out.println("Studenti humanitneho oboru:");
		for(var student : hum.values()) {
			System.out.println("ID: "+student.getID()+ " Meno: "+student.getName()+" Priezvisko: "+student.getSurname()
			+" Rok narodenia: "+student.getDate()+" Priemer: "+student.getAverageMark());
		}
		System.out.println("Studenti kombinovaneho studia:");
		for(var student : kom.values()) {
			System.out.println("ID: "+student.getID()+ " Meno: "+student.getName()+" Priezvisko: "+student.getSurname()
			+" Rok narodenia: "+student.getDate()+" Priemer: "+student.getAverageMark());
		}
	}
	// g
	public void printTechAndHumAverageMark() {
		double averageTech = 0; double averageHum = 0;
		for(var student : tech.values()) {
			averageTech += student.getAverageMark();
		}
		for(var student : hum.values()) {
			averageHum += student.getAverageMark();
		}
		averageTech = averageTech/tech.size();
		averageHum = averageHum/hum.size();
		System.out.println("Priemer technickeho oboru: "+averageTech+ " Priemer humanitneho oboru: "+averageHum);
	}
	// h
	public void printSizesOfStudents() {
		System.out.println("Pocet studentov technickeho oboru: "+tech.size());
		System.out.println("Pocet studentov humanitneho oboru: "+hum.size());
		System.out.println("Pocet studentov kombinovaneho studia: "+kom.size());
	}
	// i
	public void loadStudentsFromFile(String fileName) {
		FileReader fr = null; BufferedReader in = null;
		try {
			fr = new FileReader(fileName);
			in = new BufferedReader(fr);
			while(true) {
				String line = in.readLine();
				if(line == null || line == "") {
					break;
				}
				String splitter = "; ";
				String[] splits = line.split(splitter);
				setFileID(Integer.parseInt(splits[0]));
				addStudent(splits[1],splits[2],splits[3],splits[4],true);
				String[] marks = splits[5].split("");
				for(String mark : marks) {
					newMark(Integer.parseInt(splits[0]),Integer.parseInt(mark));
				}

			}
		}
		catch(Exception e){
			System.err.println(e.getMessage());
		}
		finally {
			try {
				if (in != null) {
					fr.close();
					in.close();
				}
			}
			catch (IOException e){
				System.err.println("File cannot open");
			}
		}
	}
	// j
	public void saveStudentsToFile(String fileName) {
		try {
			FileWriter fw = new FileWriter(fileName);
			BufferedWriter out = new BufferedWriter(fw);
			for (var student : archive.values()) {
				String marks = "";
				for(Integer mark : student.getMarks()) {
					marks += mark.toString();
				}
				out.write(student.getID()+"; "+student.getClass().getSimpleName()+"; "+student.getName()+"; "+student.getSurname()+"; "+student.getDate()+"; "+marks);
				out.newLine();
			}
			out.close();
			fw.close();
			
		}
		catch(IOException e) {
			System.err.println(e.getMessage());
		}
	}
}
