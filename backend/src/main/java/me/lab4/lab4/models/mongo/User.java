package me.lab4.lab4.models.mongo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import me.lab4.lab4.models.Role;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Data
@Document("users")
public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@JsonSerialize(using = ToStringSerializer.class)
	private ObjectId id;

	private String name;

	private boolean pfpUploaded;

	private Role role;
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private String password;

	public User() {
		this.pfpUploaded = false;
		this.role = Role.REGULAR;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public ObjectId getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void pfpUploaded() {
		this.pfpUploaded = true;
	}

	public boolean isPfpUploaded() {
		return pfpUploaded;
	}

	public boolean isMod() {
		return this.role == Role.MODERATOR || this.role == Role.ADMIN || this.role == Role.SUPERADMIN;
	}

	public boolean isAdmin() {
		return this.role == Role.ADMIN || this.role == Role.SUPERADMIN;
	}

	public boolean isSuperAdmin() {
		return this.role == Role.SUPERADMIN;
	}
}
