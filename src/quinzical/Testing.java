package quinzical;

public class Testing {

	public static void main(String[] args) {
		
		String s="Mount Māngere";
		s=s.replace("the","").toLowerCase().trim();
        
		boolean check=s.equals("Mount Māngere".toLowerCase().trim());
		System.out.print(check);
		System.out.println(s);
		System.out.println("Mount Māngere".toLowerCase().trim());
	}

}
