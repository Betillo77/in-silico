package modelo;

import control.Par;


public class BidAsk
{
	public double bid;
	public double ask;
	public Par currency;
	
	public BidAsk(double bid, double ask, Par currency)
	{
		this.bid = bid;
		this.ask = ask;
		this.currency = currency;
	}
}