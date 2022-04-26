package projekt;
import java.util.*;
public abstract class Student {
	private int ID;
	private String name;
	private String surname;
	private String date; // day/month/year
	private List<Integer> marks = new ArrayList<Integer>();
	private double averageMark;
	
	public Student(String name,String surname,String date) {
		this.name = name;
		this.date = date;
		this.surname = surname;

	}
	
	public int getID() {
		return ID;
	}
	public void setID(int ID) {
		this.ID = ID;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	
	public void addMark(int mark) {
		this.marks.add(mark);
		this.averageMark = (double)marks.stream().mapToInt(Integer::intValue).sum()/marks.size();

	}
	public double getAverageMark() {
		return averageMark;
	}
	public List<Integer> getMarks(){
		return marks;
	}
	abstract public void function();
}
