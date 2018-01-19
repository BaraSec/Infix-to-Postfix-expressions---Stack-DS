package Structures;

public class CharsLinkedStack
{
	private CharsNode first;
	private int size;

	public CharsLinkedStack()
	{
	}

	public void push(char data)
	{
		CharsNode newNode = new CharsNode(data);
		newNode.setNext(first);
		first = newNode;
		size++;
	}

	public char pop()
	{
		char temp = first.getData();
		first = first.getNext();
		size--;

		return temp;
	}

	public char peek()
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
		CharsNode current = first;
		String str = "";
		while(current != null)
		{
			str += current.toString();
			current = current.getNext();
		}
		return str;
	}
}
