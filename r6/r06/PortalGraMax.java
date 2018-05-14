import java.util.ArrayList;
import java.io.*;
import java.util.*;

public class PomocnikGry {
  private static final String alfabet = "abcdefg";
  private int dlugoscPlanszy = 7;
  private int wielkoscPlanszy = 49;
  private int [] plansza = new int[wielkoscPlanszy];
  private int iloscPortali = 0;
  
  public String pobierzDaneWejsciowe(String komunikat) {
    String daneWejsciowe = null;
    System.out.print(komunikat + "  ");
    try {
       BufferedReader is = new BufferedReader(
	       new InputStreamReader(System.in));
       daneWejsciowe = is.readLine();
       if (daneWejsciowe.length() == 0 )  return null; 
    } catch (IOException e) {
      System.out.println("IOException: " + e);
    }
    return daneWejsciowe.toLowerCase();
  }
  
  public ArrayList rozmiescPortal(int wielkoscPortalu) {
    ArrayList<String> zajetePola = new ArrayList<String>();
    String [] wspolrzedneLnc = new String [wielkoscPortalu]; // zawiera współrzędne zapisane jako 'f6'
    String pomoc = null;                                // pomocniczy łańcuch znaków
    int [] wspolrzedne = new int[wielkoscPortalu];      // bieżące proponowane współrzędne
    int prob = 0;                                       // licznik ilości prób
    boolean powodzenie = false;                         // flaga = czy znaleziono dobre miejsce
    int polozenie = 0;                                  // bieżące miejsce początkowe
    
    iloscPortali++;                                     // n-ty portal do rozmieszczenia
    int inkr = 1;                                       // określenie przyrostu w poziomie
    if ((iloscPortali % 2) == 1) {                      // jeśli nieparzysty portal (rozmieszczany w pionie)
      inkr = dlugoscPlanszy;                            // określenie przyrostu w pionie
    }

    while (!powodzenie & prob++ < 200 ) {                 // główna pętla poszukiwania
	    polozenie = (int) (Math.random() * wielkoscPlanszy);  // wybór losowego punktu początkowego
      //System.out.print(" sprawdź " + location);
	    int x = 0;                                          // n-ty fragment rozmieszczanego portalu
      powodzenie = true;                                  // zakładamy, że się udało 
      while (powodzenie && x < wielkoscPortalu) {         // szukamy sąsiadujących pustych pól planszy
        if (plansza[polozenie] == 0) {                    // jeśli jeszcze nie zajęte
          wspolrzedne[x++] = polozenie;                   // zapisujemy miejsce
          polozenie += inkr;                              // sprawdzamy 'następne' sąsiadujące pole
          if (polozenie >= wielkoscPlanszy){              // poza zakresem - 'dół' planszy
            powodzenie = false;                           // niepowodzenie
          }
          if (x>0 & (polozenie % dlugoscPlanszy == 0)) {  // poza zakresem - prawa krawędź planszy
            powodzenie = false;                           // niepowodzenie
          }
        } else {                                          // znalezione pole już jest zajęte
          // System.out.print(" już zajęte " + location);
          powodzenie = false;                             // niepowodzenie
        }
      }
    }                                                     // koniec while
    
    int x = 0;                                            // zamieniamy na współrzędne 
    int wiersz = 0;
    int kolumna = 0;
    while (x < wielkoscPortalu) {
      plansza[wspolrzedne[x]] = 1;                        // zaznaczamy pole planszy jako zajęte
      wiersz = (int) (wspolrzedne[x] / dlugoscPlanszy);   // określenie wiersza
      kolumna = wspolrzedne[x] % dlugoscPlanszy;          // pobranie liczby określającej kolumnę
      pomoc = String.valueOf(alfabet.charAt(kolumna));    // konwersja do postaci alfanumerycznej
      
      zajetePola.add(pomoc.concat(Integer.toString(wiersz)));
      x++;
      // System.out.print(" współrzędne "+x+" = "+zajetePola.get(x-1)); 
    }
    //System.out.println("\n");
    
    return zajetePola;
  }
}
public class PortalGraMax {

  private PomocnikGry pomocnik = new PomocnikGry();
  private ArrayList<Portal> listaPortali = new ArrayList<Portal>(); 
  private int iloscRuchow = 0; 

  private void przygotujGre() {   
    // najpierw tworzymy portale i określamy ich położenie
    Portal pierwszy = new Portal();
    pierwszy.setNazwa("onet.pl");
    Portal drugi = new Portal();
    drugi.setNazwa("wp.com");
    Portal trzeci = new Portal();
    trzeci.setNazwa("Go2.com");
    listaPortali.add(pierwszy);
    listaPortali.add(drugi);
    listaPortali.add(trzeci);

    System.out.println("Twoim celem jest zatopienie trzech portali.");
    System.out.println("onet.pl, wp.pl, Go2.com");
    System.out.println("Postaraj się je zatopić w jak najmniejszej ilości ruchów.");
     
    for (Portal rozmieszczanyPortal : listaPortali) {                 
      ArrayList<String> nowePolozenie = pomocnik.rozmiescPortal(3);   
      rozmieszczanyPortal.setPolaPolozenia(nowePolozenie);
    } // koniec pętli for
  } // koniec metody

  private void rozpocznijGre() { 
    while(!listaPortali.isEmpty()) {
      String ruchGracza = pomocnik.pobierzDaneWejsciowe("Podaj pole:"); 
      sprawdzRuchGracza(ruchGracza);
    } // koniec while
    zakonczGre();
  } // koniec metody

  private void sprawdzRuchGracza(String ruch) {
    iloscRuchow++;
    String wynik  = "pudło"; // początkowo zakładamy że gracz spudłował
    for (Portal portalDoSprawdzenia : listaPortali) {     
      wynik = portalDoSprawdzenia.sprawdz(ruch);   
      if (wynik.equals("trafiony")) {
        break;
      }
      if (wynik.equals("zatopiony")) {
        listaPortali.remove(portalDoSprawdzenia); // już po nim !
        break;
      }  
    } // koniec pętli for
    System.out.println(wynik);
  }

  private void zakonczGre() {
    System.out.println("Wszystkie Portale zostały zatopione! Teraz Twoje informacje nie mają znaczenia.");
    if (iloscRuchow <= 18) {
      System.out.println("Wykonałeś jedynie " + iloscRuchow + " ruchów.");
      System.out.println("Wydostałeś się, zanim Twoje informacje zatonęły.");
    } else {
      System.out.println("Ale się grzebałeś! Wykonałeś aż "+ iloscRuchow + " ruchów.");
      System.out.println("Teraz rybki pływają pomiędzy Twoimi informacjami.");
    }
  }  // koniec metody
   
  
  public static void main (String[] args) {
    PortalGraMax gra = new PortalGraMax();
    gra.przygotujGre();
    gra.rozpocznijGre();
  }
}
//import java.util.ArrayList;

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
