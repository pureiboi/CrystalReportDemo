package com.boc.crystalreportdemo.domain;

import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "TB_TRANSACTION")
public class Transaction {
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	private String txnId;
	private Timestamp txnDate;
	private BigDecimal txnAmt;
	private String vocNo;
	private String purpose;
	private String txnDesc;
	@Column(name = "BAL_AFT")
	private BigDecimal balAfter;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FK_ACC_ID")
	private Account account;

	public String getTxnId() {
		return txnId;
	}

	public void setTxnId(String txnId) {
		this.txnId = txnId;
	}

	public Timestamp getTxnDate() {
		return txnDate;
	}

	public void setTxnDate(Timestamp txnDate) {
		this.txnDate = txnDate;
	}

	public BigDecimal getTxnAmt() {
		return txnAmt;
	}

	public void setTxnAmt(BigDecimal txnAmt) {
		this.txnAmt = txnAmt;
	}

	public String getVocNo() {
		return vocNo;
	}

	public void setVocNo(String vocNo) {
		this.vocNo = vocNo;
	}

	public String getPurpose() {
		return purpose;
	}

	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}

	public String getTxnDesc() {
		return txnDesc;
	}

	public void setTxnDesc(String txnDesc) {
		this.txnDesc = txnDesc;
	}

	public BigDecimal getBalAfter() {
		return balAfter;
	}

	public void setBalAfter(BigDecimal balAfter) {
		this.balAfter = balAfter;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

}
