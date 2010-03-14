package modelo;

import control.Par;

public class BidAsk
{
	private double bid;
	private double ask;
	private Par currency;
	
	public double getBid() {
		return bid;
	}

	public void setBid(double bid) {
		this.bid = bid;
	}

	public double getAsk() {
		return ask;
	}

	public void setAsk(double ask) {
		this.ask = ask;
	}

	public Par getCurrency() {
		return currency;
	}

	public void setCurrency(Par currency) {
		this.currency = currency;
	}

	public BidAsk(double bid, double ask, Par currency)
	{
		this.bid = bid;
		this.ask = ask;
		this.currency = currency;
	}
}