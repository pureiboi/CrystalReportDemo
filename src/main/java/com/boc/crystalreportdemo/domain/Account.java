package com.boc.crystalreportdemo.domain;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "TB_ACCOUNT")
public class Account {

	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	private String accId;
	private String accType;
	private String accNo;
	private String accName;
	private String accSeq;

	private BigDecimal balance;
	private Timestamp accCreatedDate;
	private String currency;

	@OneToMany(mappedBy = "account")
	private Set<Transaction> transactions;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FK_BRANCH_ID")
	private Branch branch;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FK_USER_ID")
	private User user;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FK_ADDRESS_ID")
	private Address address;

	public String getAccId() {
		return accId;
	}

	public void setAccId(String accId) {
		this.accId = accId;
	}

	public String getAccType() {
		return accType;
	}

	public void setAccType(String accType) {
		this.accType = accType;
	}

	public String getAccNo() {
		return accNo;
	}

	public void setAccNo(String accNo) {
		this.accNo = accNo;
	}

	public String getAccName() {
		return accName;
	}

	public void setAccName(String accName) {
		this.accName = accName;
	}

	public String getAccSeq() {
		return accSeq;
	}

	public void setAccSeq(String accSeq) {
		this.accSeq = accSeq;
	}

	public Timestamp getAccCreatedDate() {
		return accCreatedDate;
	}

	public void setAccCreatedDate(Timestamp accCreatedDate) {
		this.accCreatedDate = accCreatedDate;
	}

	public Branch getBranch() {
		return branch;
	}

	public void setBranch(Branch branch) {
		this.branch = branch;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

}
