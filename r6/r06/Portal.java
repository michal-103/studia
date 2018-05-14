import java.util.ArrayList;

class Portal {

  private ArrayList<String> polaPolozenia;                    
  // int iloscTrafien; (już niepotrzebne)

  private String nazwa;
	

  public void setPolaPolozenia(ArrayList<String> ppol) {
    polaPolozenia = ppol;
  }

  public void setNazwa(String nzwPortalu) {
    nazwa = nzwPortalu;  
  }

  
  public String sprawdz(String ruch) {
  
    String wynik = "pudło";

    int indeks = polaPolozenia.indexOf(ruch);
    
    if (indeks >= 0) {
    
      polaPolozenia.remove(indeks);
      
      if (polaPolozenia.isEmpty()) {
        wynik = "zatopiony";
      } else {
        wynik = "trafiony";
      } // koniec if
      
    } // koniec if
    
    return wynik;
  } // koniec metody
} // koniec klasy
