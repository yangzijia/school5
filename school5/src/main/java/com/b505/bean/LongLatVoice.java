package com.b505.bean;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.hibernate.annotations.GenericGenerator;

import java.sql.Blob;
@Entity
@Table(name="longLatVoice")
@JsonIgnoreProperties(value={"longLat"})
public class LongLatVoice implements Serializable {
	private static final long serialVersionUID = -1260580311418553469L;

	private Long voiceId;
	private String voiceName;
	private String voiceUrl;
	private Blob  voice;
	private LongLat longLat;
	@Id
	@GeneratedValue(generator="a_native")
	@GenericGenerator(name="a_native",strategy="native")
	@Column(name="voiceId")
	public Long getVoiceId() {
		return voiceId;
	}
	public void setVoiceId(Long voiceId) {
		this.voiceId = voiceId;
	}
	@Column(name="voicename")
	public String getVoiceName() {
		return voiceName;
	}
	public void setVoiceName(String voiceName) {
		this.voiceName = voiceName;
	}
	@Column(name="voiceUrl")
	public String getVoiceUrl() {
		return voiceUrl;
	}
	public void setVoiceUrl(String voiceUrl) {
		this.voiceUrl = voiceUrl;
	}
	@Column(name="voice")
	public Blob getVoice() {
		return voice;
	}
	public void setVoice(Blob voice) {
		this.voice = voice;
	}
	@ManyToOne
	@JoinColumn(name="longLat_id")
	public LongLat getLongLat() {
		return longLat;
	}
	public void setLongLat(LongLat longLat) {
		this.longLat = longLat;
	}
	@Override
	public String toString() {
		return "LongLatVoice [voiceId=" + voiceId + ", voiceName=" + voiceName
				+ ", voiceUrl=" + voiceUrl + ", voice=" + voice + "]";
	}
	
}
