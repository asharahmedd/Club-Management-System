public class Day implements Cloneable, Comparable<Day> {

	private int year;
	private int month;
	private int day;
	private static final String MonthNames = "JanFebMarAprMayJunJulAugSepOctNovDec";

	public Day(int y, int m, int d) {//constructor
		this.year = y;
		this.month = m;
		this.day = d;
	}

	public Day(String sDay) throws ExInvalidDateFormat {
		set(sDay);
	}

	static public boolean isLeapYear(int y) {//check leap year
		if (y % 400 == 0)
			return true;
		else if (y % 100 == 0)
			return false;
		else if (y % 4 == 0)
			return true;
		else
			return false;
	}

	static public boolean valid(int y, int m, int d) {//check if valid
		if (m < 1 || m > 12 || d < 1)
			return false;
		switch (m) {
			case 1:
			case 3:
			case 5:
			case 7:
			case 8:
			case 10:
			case 12:
				return d <= 31;
			case 4:
			case 6:
			case 9:
			case 11:
				return d <= 30;
			case 2:
				if (isLeapYear(y))
					return d <= 29;
				else
					return d <= 28;
		}
		return false;
	}

	@Override
	public String toString() {
		return day + "-" + MonthNames.substring((month - 1) * 3, (month) * 3) + "-" + year; // convert date to string
	}

	@Override
	public Day clone() {//clone the date
		Day copy = null;
		try {
			copy = (Day) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return copy;
	}

	@Override
	public int compareTo(Day another) {//compare with another date
		if (this.year != another.year) {
			return Integer.compare(this.year, another.year);
		}
		if (this.month != another.month) {
			return Integer.compare(this.month, another.month);
		}
		return Integer.compare(this.day, another.day);
	}

	public void set(String sDay) throws ExInvalidDateFormat {//set a date
		String[] sDayParts = sDay.split("-");
		this.day = Integer.parseInt(sDayParts[0]);
		this.year = Integer.parseInt(sDayParts[2]);
		this.month = MonthNames.indexOf(sDayParts[1]) / 3 + 1;
	}

	static public Day fromString(String sDay) throws ExInvalidDateFormat {//convert from string to day
		String[] sDayParts = sDay.split("-");
		if (sDayParts.length != 3) {
			throw new ExInvalidDateFormat();
		}
		int month = MonthNames.indexOf(sDayParts[1]);
		if (month == -1) {
			throw new ExInvalidDateFormat();
		} else {
			month = month / 3 + 1;
		}
		
		try {
			int day = Integer.parseInt(sDayParts[0]);
			int year = Integer.parseInt(sDayParts[2]);
			if (!valid(year, month, day)) {
				throw new ExInvalidDateFormat();
			} else {
				return new Day(year, month, day);
			}
		} catch (NumberFormatException e) {
			throw new ExInvalidDateFormat();
		}
	}

	public Day AddDays(int n) {//adding number of days to the current day
		Day newDay = new Day(this.year, this.month, this.day);
		newDay.day += n;
		while (newDay.day > daysInMonth()) {
			newDay.day -= daysInMonth();
			newDay.month++;
			if (newDay.month > 12) {
				newDay.month = 1;
				newDay.year++;
			}
		}
		return newDay;
	}

	private int daysInMonth() {
		switch (this.month) {
			case 4:
			case 6:
			case 9:
			case 11:
				return 30;
			case 2:
				return (Day.isLeapYear(this.year)) ? 29 : 28;
			default:
				return 31;
		}
	}

	public int getyear() {
		return this.year;
	}

	public int getmonth() {
		return this.month;
	}

	public int getday() {
		return this.day;
	}

	public static boolean dateoverlaps(Day start1, Day end1, Day start2, Day end2) {//check if 2 dates overlap with each other or not
		return (start1.compareTo(end2) <= 0) && (start2.compareTo(end1) <= 0);
	}
}
