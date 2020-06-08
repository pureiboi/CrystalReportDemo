package com.boc.crystalreportdemo.domain;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="TB_USER")
public class User {

	@Id
	@GeneratedValue(generator="system-uuid")
	@GenericGenerator(name="system-uuid", strategy = "uuid")
	private String userId;
	private String userName;
	private String fullName;
	private String password;
	private String role;
	
	
	@OneToMany(mappedBy="user", fetch = FetchType.LAZY)
	private Set<Account> accounts;
	
	@OneToMany(mappedBy="user", fetch = FetchType.LAZY)
	private Set<Document> document;

	public Set<Document> getDocument() {
		return document;
	}
	public void setDocument(Set<Document> document) {
		this.document = document;
	}
	public String getUserName() {
		return userName;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public Set<Account> getAccounts() {
		
		return accounts;
	}
	public void setAccounts(Set<Account> accounts) {
		this.accounts = accounts;
	}
	
	public boolean isAdmin() {
		return this.role.equalsIgnoreCase("ADMIN");
	}
	
	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("userId: " + userId + ", ");
		stringBuilder.append("userName: " + userName + ", ");
		stringBuilder.append("fullName: " + fullName + ", ");
		stringBuilder.append("role: " + role);
		return stringBuilder.toString();
	}
}
