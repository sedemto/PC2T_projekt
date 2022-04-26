package projekt;

public class Kom extends Student {
	
	private int day;
	private int month;
	private int year;
	
	public Kom(String name,String surname,String date) {
		super(name,surname,date);
		this.day = Integer.parseInt(date.split("/",3)[0]);
		this.month = Integer.parseInt(date.split("/",3)[1]);
		this.year = Integer.parseInt(date.split("/",3)[2]);
		
	}
	public Boolean leapYear() {
		Boolean leap = false;
		if(this.year%4 == 0)
			leap = true;
		return leap;
	}
	public String zodiacSign() {
		String sign = "";
		if(this.month == 1) {
			if(this.day <= 20)
				sign = "kozorozec";
			else
				sign = "vodnar";
		}
		else if(this.month == 2) {
			if(this.day <= 19)
				sign = "vodnar";
			else
				sign = "ryby";
		}
		else if(this.month == 3) {
			if(this.day <= 20)
				sign = "ryby";
			else
				sign = "baran";
		}
		else if(this.month == 4) {
			if(this.day <= 20)
				sign = "baran";
			else
				sign = "byk";
		}
		else if(this.month == 5) {
			if(this.day <= 21)
				sign = "byk";
			else
				sign = "blizenci";
		}
		else if(this.month == 6) {
			if(this.day <= 21)
				sign = "blizenci";
			else
				sign = "rak";
		}
		else if(this.month == 7) {
			if(this.day <= 22)
				sign = "rak";
			else
				sign = "lev";
		}
		else if(this.month == 8) {
			if(this.day <= 23)
				sign = "lev";
			else
				sign = "panna";
		}
		else if(this.month == 9) {
			if(this.day <= 23)
				sign = "panna";
			else
				sign = "vahy";
		}
		else if(this.month == 10) {
			if(this.day <= 23)
				sign = "vahy";
			else
				sign = "skorpion";
		}
		else if(this.month == 11) {
			if(this.day <= 22)
				sign = "skorpion";
			else
				sign = "strelec";
		}
		else if(this.month == 12) {
			if(this.day <= 21)
				sign = "strelec";
			else
				sign = "kozorozec";
		}
		return sign;
	}
	@Override
	public void function() {
		System.out.println("Prestupny rok: "+leapYear());
		System.out.println("Znamenie: "+zodiacSign());
	}
}
