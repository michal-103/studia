import java.io.*;
import java.util.ArrayList;

class PortalGraMax {
	public static void main(String[] args){
		
		int iloscRuchow = 0;
		
		PomocnikGry pomocnik = new PomocnikGry();
		
		ProstyPortal portal = new ProstyPortal();
		
		int liczbaLosowa = (int) (Math.random() * 5);
		
		ArrayList<String> polozenie = new ArrayList<String>();
		
		for (int i=0; i<3; i++){
			String LiczbaString = Integer.toString(liczbaLosowa);
			polozenie.add(LiczbaString);
			liczbaLosowa++;
		}
		portal.setPolaPolozenia(polozenie);
		
		boolean czyistnieje = true;
		
		while (czyistnieje == true){
			String pole = pomocnik.pobierzDaneWej("podaj liczbe");
			
			String wynik = portal.sprawdz(pole);
			
			iloscRuchow++;
		
			if (wynik.equals("zatopiony")){
				czyistnieje = false;
				System.out.println(iloscRuchow + " ruchow");
			}
		}
		
	}
}
class ProstyPortal {
	
	
	private ArrayList<String> PolaPolozenia;
	
	public void setPolaPolozenia(ArrayList<String> ppol){
		PolaPolozenia = ppol;
	}


	public String sprawdz(String ruch){
		
		int indeks = PolaPolozenia.indexOf(ruch);
		
		String wynik = "pudlo";
		
		
		if (indeks >= 0){
			
			PolaPolozenia.remove(indeks);
			if (PolaPolozenia.isEmpty()){
				wynik = "zatopiony";
			}
			else{
			wynik = "trafiony";
			}
		}
		
		System.out.println(wynik);
		return wynik;
	}
}

class PomocnikGry{
	public String pobierzDaneWej(String komunikat){
		String wierszWej = null;
		System.out.println(komunikat + " ");
		try {
			BufferedReader sw = new BufferedReader(new InputStreamReader(System.in));
			wierszWej = sw.readLine();
			if (wierszWej.length() == 0 ) return null;
			
		}catch (IOException e){
			System.out.println("IOException: " + e);
		}
		return wierszWej;
	}
	
}






















