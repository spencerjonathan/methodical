package uk.co.methodical;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class Composition {
	private Integer id;
	private String title;
	private Integer author;
	private Integer length;
	
	@JsonFormat(pattern="yyyy-MM-dd hh:mm:ss")
	private Date created;
	private boolean isTrue;
	private String composition;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getAuthor() {
		return author;
	}

	public void setAuthor(Integer author) {
		this.author = author;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public String getComposition() {
		return composition;
	}

	public void setComposition(String composition) {
		this.composition = composition;
	}
	
	public boolean equals(Composition other) {
		System.out.println("In Composition.equals()");
		System.out.println("  " + this.getTitle() + " == " + other.getTitle());
		System.out.println("  " + this.getAuthor() + " == " + other.getAuthor());
		System.out.println("  " + this.getLength() + " == " + other.getLength());
		System.out.println("  " + this.getCreated() + " == " + other.getCreated());
		System.out.println("  " + this.getCreated().getTime() + " == " + other.getCreated().getTime());
		System.out.println("  " + this.isTrue() + " == " + other.isTrue());
		
		return this.getTitle().equals(other.getTitle()) &&
				this.getAuthor().equals(other.getAuthor()) &&
				this.getComposition().equals(other.getComposition()) &&
				this.getLength().equals(other.getLength()) &&
				Math.abs(this.getCreated().getTime() - other.getCreated().getTime()) < 1000 &&
				this.isTrue() == other.isTrue();
	}

	public Integer getLength() {
		return length;
	}

	public void setLength(Integer length) {
		this.length = length;
	}

	public boolean isTrue() {
		return isTrue;
	}

	public void setTrue(boolean isTrue) {
		this.isTrue = isTrue;
	}

}
