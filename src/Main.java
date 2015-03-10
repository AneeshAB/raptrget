import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ArrayList;

public class Main {
	
	public static ArrayList<String[]> leftHalf(ArrayList<String[]> list) {
		int size1 = list.size() / 2;
		ArrayList<String[]> left = new ArrayList<String[]>(size1);
		for (int i = 0; i < size1; i++)
			left.add(list.get(i));
		return left;
	}
	
	public static ArrayList<String[]> rightHalf(ArrayList<String[]> list) {
		int size1 = list.size() / 2;
		int size2 = list.size() - size1;
		ArrayList<String[]> right = new ArrayList<String[]>();
		for (int i = 0; i < size2; i++)
			right.add(list.get(i + size1));
		return right;
	}
	
	public static void merge(ArrayList<String[]> result, ArrayList<String[]> left, 
			ArrayList<String[]> right) {
		int i1 = 0;
		int i2 = 0;
		for (int i = 0; i < result.size(); i++) {
			if(i2 >= right.size() || (i1 < left.size() && Integer.parseInt(left.get(i1)[1]) 
					>= Integer.parseInt(right.get(i2)[1]))) {
				result.set(i, left.get(i1));
				i1++;
			} else {
				result.set(i, right.get(i2));
				i2++;
			}
		}
	}
	
	public static void mergeSort(ArrayList<String[]> list) {
		if(list.size() > 1) {
			ArrayList<String[]> left = leftHalf(list);
			ArrayList<String[]> right = rightHalf(list);
			
			mergeSort(left);
			mergeSort(right);
			
			merge(list, left, right);
		}
	}
	
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new FileReader("raptr.in"));
		String line;
		ArrayList<String[]> games = new ArrayList<String[]>();
		
		while ((line = br.readLine()) != null) {
			String url = "http://www.raptr.com/game/" + line + "/discussions";
			URL oracle = new URL(url);
	        BufferedReader in = new BufferedReader(
	        		new InputStreamReader(oracle.openStream()));
	        String inputLine;
	        String members = "";
	        while ((inputLine = in.readLine()) != null) {
	        	if(inputLine.contains("<div class=\\\"user-list\\\"><h3 class=\\\"header\\\">ACTIVE MEMBERS (")) {
	        		int start = inputLine.indexOf("<h3 class=\\\"header\\\">ACTIVE MEMBERS (");
	        		String value = inputLine.substring(start);
	        		start = inputLine.indexOf("ACTIVE MEMBERS (");
	        		value = inputLine.substring(start+16);
	        		start = value.indexOf(")");
	        		value = value.substring(0, start);
	        		value = value.replace(",", "");
	        		members = value;
	        		break;
	        	}
	        }
	        String[] pair = new String[2];
	        pair[0] = line;
	        pair[1] = members;
	        //System.out.print(line + " ");
	        //System.out.println(members);
	        games.add(pair);
	        mergeSort(games);
		}
		
		br.close();
		PrintWriter writer = new PrintWriter("raptr.out", "UTF-8");
		for(int i=0; i<games.size(); i++){
			writer.print(games.get(i)[0] + " " + games.get(i)[1] + "\n");
		}
		writer.close();
	}
}
