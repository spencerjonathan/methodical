package uk.co.methodical;

import java.util.Date;

public class Composition {
	private Integer id;
	private String title;
	private String author;
	private Date created;
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

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
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
		return this.getTitle().equals(other.getTitle()) &&
				this.getAuthor().equals(other.getAuthor()) &&
				this.getComposition().equals(other.getComposition());
	}

}
