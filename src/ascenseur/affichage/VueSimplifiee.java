package ascenseur.affichage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ascenseur.traitement.*;

public class VueSimplifiee {
	public void getVue1() {
		List<Ascenseur> ascenseurs = Controleur.getInstance().getAscenseurs();
		List<RequeteExterne> requetesExternes = Controleur.getInstance().getRequetesExternes();
		int nombreEtages = Controleur.getInstance().getEtages();

		String affichage= "VUE EN COUPE";

		for(int i = 0; i < 4 + 11 * ascenseurs.size(); ++i) {
			affichage += " ";
		}
		
		System.out.println(affichage + "Boutons activés");
		for(int i = nombreEtages; i >= 0; --i) {
			affichage = "Etage " + i;
			for(int j = 0; j < 10  - Integer.toString(i).length(); ++j) {
				affichage += " ";
			}
			for(Ascenseur ascenseur : ascenseurs) {
				if(ascenseur.getEtage() == i) {
					affichage += ascenseur + "        ";
				}
				else {
					affichage += " '         ";
				}
			}
			for(RequeteExterne requeteExterne : requetesExternes) {
				if(requeteExterne.getEtage() == i) {
					affichage += requeteExterne + " ";
				}
			}
			System.out.println(affichage);
		}
		
		System.out.println("\nPanneaux de controle des ascenseurs (Bouton activé) : ");
		List<Integer> etagesPressed = new ArrayList<>();
        List<Requete> requetesInternes = new ArrayList<>();
        Ascenseur ascenseur;
		for(int i = 0; i < ascenseurs.size(); ++ i) {
			etagesPressed.clear();
			ascenseur = ascenseurs.get(i);
            requetesInternes = ascenseur.getRequetes();
			
			if(requetesInternes != null) {
				for(Requete requete : requetesInternes) {
					etagesPressed.add(requete.getEtage());
				}
			}
			
			affichage = "Ascenseur " + i + " : ";
			for(int j = 0; j < nombreEtages; ++j) {
				if(etagesPressed.contains(j))
					affichage += "(" + j + ") ";
				else
					affichage += " " + j + "  ";
			}
			if(ascenseur.isBloqued())
				affichage += "Bloqué !";
			System.out.println(affichage);
		}
		
	} //getVue1() 
	
	public void getVue2() {
		// Boutons des panneaux de controle (bloquage aussi)
		System.out.println("Ajout de requêtes internes");
		List<Ascenseur> ascenseurs = Controleur.getInstance().getAscenseurs();
		Ascenseur ascenseur;
        int etages = Controleur.getInstance().getEtages();
		String etat;
		for(int i = 0; i < ascenseurs.size(); ++i) {
			ascenseur = ascenseurs.get(i);
			switch (ascenseur.getEtat()) {
				case Constantes.IMMOBILE_OUVERT:
					etat = "Immobile ouvert";
					break;
				case Constantes.MOUVEMENT_VERS_LE_BAS:
					etat = "Mouvement vers le bas";
					break;
				case Constantes.MOUVEMENT_VERS_LE_HAUT:
					etat = "Mouvement vers le haut";
					break;
				default:
					etat = "Immobile fermé";
			}
			System.out.println("Ascenseur " + i);
			System.out.println("Etat : " + etat);

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
            String line;
            int etage;
            for(;;){
                System.out.println("Etage à desservir (Laisser vide pour passer à la suite) :");
                try {
                    line = bufferedReader.readLine();
                    if(line.equals("")) break;
                    etage = Integer.parseInt(line);
                    if(etage > etages) continue;
                    ascenseur.creerRequeteInterne(etage);
                    System.out.println("Requete interne ajouté vers l'étage " + etage);
                }
                catch (IOException e) {
                    System.out.println("Erreur de saisie!");
                }
            }
            System.out.println("Ajout de requêtes internes terminées !");
		}

		System.out.println("Ajout de requêtes externes");
		System.out.println("Etage X :");
		System.out.println("Direction du déplacement (H[AUT] | B[AS]) (Laisser vide pour passer à la suite) :");
		System.out.println("Appel d'un ascenseur à l'étage X, direction Y.");
		System.out.println("Ajout de requêtes externes terminées !");
		// Boutons de chaque palier
	} // getVue2()
	
	public static void main(String[] args) {
		VueSimplifiee vs = new VueSimplifiee();
		List<Ascenseur> ascenseurs = new ArrayList<>();
		List<RequeteExterne> requetesExternes = new ArrayList<>();
		List<RequeteInterne> requetesInternes = new ArrayList<>();
		
		Map<Ascenseur, List<RequeteInterne>> listRequetesInternes = new HashMap<>();
		
		ascenseurs.add(new Ascenseur());
		ascenseurs.add(new Ascenseur());
		ascenseurs.add(new Ascenseur());
		ascenseurs.add(new Ascenseur());
		
		requetesExternes.add(new RequeteExterne(2, Constantes.MOUVEMENT_VERS_LE_BAS));
		
		requetesInternes.add(new RequeteInterne(2));
		requetesInternes.add(new RequeteInterne(4));
		listRequetesInternes.put(ascenseurs.get(0), requetesInternes);
		
		requetesInternes = new ArrayList<>();
		
		requetesInternes.add(new RequeteInterne(1));
		requetesInternes.add(new RequeteInterne(3));
		
		listRequetesInternes.put(ascenseurs.get(2), requetesInternes);		

		vs.getVue1();
	} // main()
}