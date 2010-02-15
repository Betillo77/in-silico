package control;

public class BidAsk
{
	double bid;
	double ask;
	Par currency;
	
	public BidAsk(double bid, double ask, Par currency)
	{
		this.bid = bid;
		this.ask = ask;
		this.currency = currency;
	}
}