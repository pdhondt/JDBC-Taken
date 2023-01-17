package be.vdab;

import be.vdab.repositories.BrouwerRepository;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        /*var repository = new BierRepository();
        try {
            System.out.print(repository.verwijderBierenZonderAlcoholPercentage());
            System.out.println(" bieren verwijderd.");
        } catch(SQLException ex) {
            ex.printStackTrace(System.err);
        }*/
        var repository = new BrouwerRepository();
        try {
            System.out.print("De gemiddelde omzet van alle brouwers bedraagt ");
            System.out.println(repository.getGemiddeldeOmzet());
            System.out.println("\nLijst met brouwers met een bovengemiddelde omzet:");
            repository.getBrouwersMetBovenGemiddeldeOmzet().forEach(System.out::println);
        } catch(SQLException ex) {
            ex.printStackTrace(System.err);
        }
    }
}