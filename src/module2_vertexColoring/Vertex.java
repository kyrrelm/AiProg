package module2_vertexColoring;

import java.awt.Color;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class Vertex implements Comparable<Vertex>{
	
	public int id;
	public double x;
	public double y;
	public List<Color> domain;
	public Color color = Color.GRAY;
	public Set<Vertex> neighbors;
	
	public Vertex(int id, double x, double y) {
		this.id = id;
		this.x = x;
		this.y = y;
		neighbors = new HashSet<Vertex>();
	}
	
	@Override
	public String toString() {
		char[] cha = new char[6];
		for(int i=0;i<cha.length; i++)
			cha[i] = '0';
		for(Color c : domain) {
			if(c == Color.BLUE)
				cha[0] = '1';
			if(c == Color.RED)
				cha[1] = '2';
			if(c == Color.GREEN)
				cha[2] = '3';
			if(c == Color.YELLOW)
				cha[3] = '4';
			if(c == Color.MAGENTA)
				cha[4] = '5';
			if(c == Color.ORANGE)
				cha[5] = '6';			
		}
		String string = (id+1000)+"";
		for(char c : cha)
			string = string+c;
		return string;
	}

	@Override
	public int compareTo(Vertex other) {
		final int WORSE = -1;
		final int EQUAL = 0;
		final int BETTER = 1;
		
		if(domain.size() == other.domain.size()) 	return EQUAL;
		if(domain.size() < other.domain.size()) 	return WORSE;
		else 										return BETTER;
	}
}
