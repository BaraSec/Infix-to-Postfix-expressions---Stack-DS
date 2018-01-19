package Structures;

public class DoubleLinkedStack
{
	private DoubleNode first;
	private int size;

	public DoubleLinkedStack()
	{
	}

	public void push(double data)
	{
		DoubleNode newNode = new DoubleNode(data);
		newNode.setNext(first);
		first = newNode;
		size++;
	}

	public double pop()
	{
		double temp = first.getData();
		first = first.getNext();
		size--;

		return temp;
	}

	public double peek()
	{
		return first.getData();
	}

	public void clear()
	{
		first = null;
		size = 0;
	}

	public boolean isEmpty()
	{
		return first == null;
	}

	public int size()
	{
		return size;
	}

	public String toString()
	{
		DoubleNode current = first;
		String str = "";
		while(current != null)
		{
			str += current.toString();
			current = current.getNext();
		}
		return str;
	}
}
