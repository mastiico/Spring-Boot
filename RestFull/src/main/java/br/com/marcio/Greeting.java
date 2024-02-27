package br.com.marcio;

public class Greeting {

	private final long id;
	private final String content;
	
	public long getId() {
		return id;
	}


	public String getContent() {
		return content;
	}


	public Greeting(long id, String content) {
		super();
		this.id = id;
		this.content = content;
	}
}
