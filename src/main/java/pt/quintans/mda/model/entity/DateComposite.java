package pt.quintans.mda.model.entity;

public class DateComposite extends Attribute {
	private boolean fullDate;
	private Attribute day;
	private Attribute month;
	private Attribute year;
	
	public DateComposite(Element parent) {
		super(parent);
	}

	public Attribute getDay() {
		return day;
	}
	public void setDay(Attribute day) {
		this.day = day;
		fullDate = day != null && month != null && year != null;
	}
	public Attribute getMonth() {
		return month;
	}
	public void setMonth(Attribute month) {
		this.month = month;
		fullDate = day != null && month != null && year != null;
	}
	public Attribute getYear() {
		return year;
	}
	public void setYear(Attribute year) {
		this.year = year;
		fullDate = day != null && month != null && year != null;
	}
	public boolean isFullDate() {
		return fullDate;
	}
}
