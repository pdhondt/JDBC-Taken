package be.vdab;

import be.vdab.repositories.BierRepository;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        var repository = new BierRepository();
        try {
            System.out.print(repository.verwijderBierenZonderAlcoholPercentage());
            System.out.println(" bieren verwijderd.");
        } catch(SQLException ex) {
            ex.printStackTrace(System.err);
        }
    }
}