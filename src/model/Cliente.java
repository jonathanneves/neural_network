package model;

public class Cliente {

	public String job;
	public String marital;
	public String education;
	public String creditDefault;
	public int balance;
	public String housing;
	public String loan;
	public int campaing;
	public int pdays;
	public int previous;
	public String hasTerm;
	public String poutcome;
	
	@Override
	public String toString() {
		return "Cliente [job=" + job + ", marital=" + marital + ", education=" + education + ", creditDefault="
				+ creditDefault + ", balance=" + balance + ", housing=" + housing + ", loan=" + loan + ", campaing="
				+ campaing + ", pdays=" + pdays + ", previous=" + previous + ", poutcome=" + poutcome + "]";
	}

	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}

	public String getMarital() {
		return marital;
	}

	public void setMarital(String marital) {
		this.marital = marital;
	}

	public String getEducation() {
		return education;
	}

	public void setEducation(String education) {
		this.education = education;
	}

	public String getCreditDefault() {
		return creditDefault;
	}

	public void setCreditDefault(String creditDefault) {
		this.creditDefault = creditDefault;
	}

	public int getBalance() {
		return balance;
	}

	public void setBalance(int balance) {
		this.balance = balance;
	}

	public String getHousing() {
		return housing;
	}

	public void setHousing(String housing) {
		this.housing = housing;
	}

	public String getLoan() {
		return loan;
	}

	public void setLoan(String loan) {
		this.loan = loan;
	}

	public int getCampaing() {
		return campaing;
	}

	public void setCampaing(int campaing) {
		this.campaing = campaing;
	}

	public int getPdays() {
		return pdays;
	}

	public void setPdays(int pdays) {
		this.pdays = pdays;
	}

	public int getPrevious() {
		return previous;
	}

	public void setPrevious(int previous) {
		this.previous = previous;
	}

	public String getPoutcome() {
		return poutcome;
	}

	public void setPoutcome(String poutcome) {
		this.poutcome = poutcome;
	}

	public String getHasTerm() {
		return hasTerm;
	}

	public void setHasTerm(String hasTerm) {
		this.hasTerm = hasTerm;
	}
	
}
