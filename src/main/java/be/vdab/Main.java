package be.vdab;

import be.vdab.repositories.BierRepository;
import be.vdab.repositories.BrouwerRepository;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        /*var repository = new BierRepository();
        try {
            System.out.print(repository.verwijderBierenZonderAlcoholPercentage());
            System.out.println(" bieren verwijderd.");
        } catch(SQLException ex) {
            ex.printStackTrace(System.err);
        }*/
        /*var repository = new BrouwerRepository();
        try {
            System.out.print("De gemiddelde omzet van alle brouwers bedraagt ");
            System.out.println(repository.getGemiddeldeOmzet());
            System.out.println("\nLijst met brouwers met een bovengemiddelde omzet:");
            repository.getBrouwersMetBovenGemiddeldeOmzet().forEach(System.out::println);
        } catch(SQLException ex) {
            ex.printStackTrace(System.err);
        }*/
        /*var repository = new BrouwerRepository();
        var scanner = new Scanner(System.in);
        System.out.print("Geef een minimale omzet in: ");
        var minimum = scanner.nextInt();
        System.out.print("Geef een maximale omzet in: ");
        var maximum = scanner.nextInt();
        try {
            System.out.println("Lijst van brouwers met een omzet tussen " + minimum + " en " + maximum + ": ");
            repository.getBrouwersMetOmzetTussenMinEnMax(minimum, maximum).forEach(System.out::println);
        } catch (SQLException ex) {
            ex.printStackTrace(System.err);
        }*/
        /*var repository = new BrouwerRepository();
        var scanner = new Scanner(System.in);
        System.out.print("Geef de id van een brouwer in: ");
        var id = scanner.nextLong();
        try {
            repository.findById(id)
                    .ifPresentOrElse(System.out::println, () -> System.out.println("Id " + id + " niet gevonden"));
        } catch (SQLException ex) {
            ex.printStackTrace(System.err);
        }*/
        /*var repository = new BrouwerRepository();
        var scanner = new Scanner(System.in);
        System.out.print("Geef een minimale omzet in: ");
        var minimum = scanner.nextInt();
        System.out.print("Geef een maximale omzet in: ");
        var maximum = scanner.nextInt();
        try {
            System.out.println("Lijst van brouwers met een omzet tussen " + minimum + " en " + maximum + ": ");
            repository.getBrouwersMetOmzetTussenViaStoredProcedure(minimum, maximum).forEach(System.out::println);
        } catch (SQLException ex) {
            ex.printStackTrace(System.err);
        }*/
        /*var repository = new BierRepository();
        try {
            repository.brouwer1Failliet();
        } catch (SQLException ex) {
            ex.printStackTrace(System.err);
        }*/
        /*var scanner = new Scanner(System.in);
        System.out.print("Geef een maandnummer in: ");
        var maand = scanner.nextInt();
        while (maand < 1 || maand > 12) {
            System.out.print("Ongeldig maandnummer.  Geef een maandnummer in: ");
            maand = scanner.nextInt();
        }
        var repository = new BierRepository();
        try {
            repository.findBierenVerkochtSinds(maand).forEach(System.out::println);
        } catch (SQLException ex) {
            ex.printStackTrace(System.err);
        }*/
        /*var repository = new BierRepository();
        try {
            System.out.println("Aantal bieren per brouwer, gesorteerd op brouwernaam:");
            repository.findAantalBierenPerBrouwer().forEach(System.out::println);
        } catch (SQLException ex) {
            ex.printStackTrace(System.err);
        }*/
        /*var scanner = new Scanner(System.in);
        System.out.print("Geef een biersoort in: ");
        var soortNaam = scanner.nextLine();
        var repository = new BierRepository();
        try {
            var namen = repository.findBierenVanEenSoort(soortNaam);
            if (namen.isEmpty()) {
                System.out.println("Geen bieren gevonden");
            } else {
                System.out.println("Lijst van bieren van de soort " + soortNaam);
                namen.forEach(System.out::println);
            }
        } catch (SQLException ex) {
            ex.printStackTrace(System.err);
        }*/
        System.out.print("Geef de brouwerIds in van dewelke je de omzet wil leegmaken (0 om te stoppen): ");
        var scanner = new Scanner(System.in);
        var brouwerIds = new HashSet<Long>();
        for (long id; (id = scanner.nextLong()) != 0; ) {
            if (id < 0) {
                System.out.println("Id mag niet negatief zijn.  Geef de brouwerIds in: ");
            } else {
                if (!brouwerIds.add(id)) {
                    System.out.println(id + " is reeds toegevoegd aan de lijst.  Geef de brouwerIds in: ");
                }
            }
            System.out.print("Geef de brouwerIds in van dewelke je de omzet wil leegmaken (0 om te stoppen): ");
        }
        if (!brouwerIds.isEmpty()) {
            var repository = new BrouwerRepository();
            try {
                var aantalLeeggemaakt = repository.maakOmzetLeeg(brouwerIds);
                System.out.println("Aantal leeggemaakte brouwers: " + aantalLeeggemaakt);
                if (aantalLeeggemaakt != brouwerIds.size()) {
                    System.out.println("Niet gevonden brouwerIds: ");
                    var gevondenIds = repository.controleerBrouwerIds(brouwerIds);
                    brouwerIds.stream().filter(id -> ! gevondenIds.contains(id)).forEach(System.out::println);
                }
            } catch (SQLException ex) {
                ex.printStackTrace(System.err);
            }
        }
    }
}