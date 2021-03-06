package dataUnit;

import java.util.Date;

public class Footballer {
	private String fullName;
	private Date date;
	private String footballTeam;
	private String hometown;
	private String category;
	private String position;

	public Footballer(String fullName, Date date, String footballTeam, String hometown, String category,
			String position) {
		this.fullName = fullName;
		this.footballTeam = footballTeam;
		this.hometown = hometown;
		this.category = category;
		this.position = position;
		this.date = date;
	}

	public String getName() {
		return this.fullName;
	}

	public Date getDate() {
		return this.date;
	}

	public String getTeam() {
		return this.footballTeam;
	}

	public String getTown() {
		return this.hometown;
	}

	public String getCategory() {
		return this.category;
	}

	public String getPosition() {
		return this.position;
	}
}
