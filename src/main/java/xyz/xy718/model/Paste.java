package xyz.xy718.model;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;

import org.springframework.web.jsf.FacesContextUtils;

import lombok.Data;

@Entity
@Data
public class Paste {

	@Id
	@GeneratedValue
	private long id;
	
	@Lob
	@Basic(fetch=FetchType.LAZY)
	@Column(nullable = false)
	private String paste;
	
	@Column(nullable = false)
	private String link;
	
	
	public Paste(String link,String paste) {
		this.paste=paste;
		this.link=link;
	}
	public Paste() {
		
	}
}
