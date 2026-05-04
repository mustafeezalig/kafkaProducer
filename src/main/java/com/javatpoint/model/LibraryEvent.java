package com.javatpoint.model;

public class LibraryEvent {

	@Override
	public String toString() {
		return "LibraryEvent [libraryEventId=" + libraryEventId + ", libraryEventType=" + libraryEventType + ", book="
				+ book + "]";
	}
	private Integer libraryEventId;
	private String  libraryEventType;
	private Book book;
	public Integer getLibraryEventId() {
		return libraryEventId;
	}
	public void setLibraryEventId(Integer libraryEventId) {
		this.libraryEventId = libraryEventId;
	}
	public String getLibraryEventType() {
		return libraryEventType;
	}
	public void setLibraryEventType(String libraryEventType) {
		this.libraryEventType = libraryEventType;
	}
	public Book getBook() {
		return book;
	}
	public void setBook(Book book) {
		this.book = book;
	}
	
}
