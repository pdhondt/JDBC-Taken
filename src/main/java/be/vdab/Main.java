package be.vdab;

import be.vdab.repositories.BrouwerRepository;

import java.sql.SQLException;
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
        var repository = new BrouwerRepository();
        var scanner = new Scanner(System.in);
        System.out.print("Geef de id van een brouwer in: ");
        var id = scanner.nextLong();
        try {
            repository.findById(id)
                    .ifPresentOrElse(System.out::println, () -> System.out.println("Id " + id + " niet gevonden"));
        } catch (SQLException ex) {
            ex.printStackTrace(System.err);
        }
    }
}