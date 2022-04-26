package projekt;

public class Tech extends Student {
	
	private int year;
	
	public Tech(String name,String surname,String date) {
		super(name,surname,date);
		this.year = Integer.parseInt(date.split("/",3)[2]);
	}
	
	public Boolean leapYear() {
		Boolean leap = false;
		if(this.year%4 == 0)
			leap = true;
		return leap;
	}

	@Override
	public void function() {
		System.out.println("Prestupny rok: "+leapYear());
	}
}
