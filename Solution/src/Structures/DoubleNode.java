package Structures;

public class DoubleNode
{
	private double data;
	private DoubleNode next;

	public DoubleNode(double data)
	{
		this.data = data;
	}

	public double getData()
	{
		return data;
	}

	public void setData(double data)
	{
		this.data = data;
	}

	public DoubleNode getNext()
	{
		return next;
	}

	public void setNext(DoubleNode next)
	{
		this.next = next;
	}

	public String toString()
	{
		return data + "";
	}
}
