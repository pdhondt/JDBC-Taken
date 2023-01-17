package be.vdab.repositories;

import be.vdab.domain.Brouwer;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BrouwerRepository extends AbstractRepository {
    public BigDecimal getGemiddeldeOmzet() throws SQLException {
        var sql = """
                select avg(omzet) as gemiddelde
                from brouwers
                """;
        try (var connection = super.getConnection();
            var statement = connection.prepareStatement(sql)) {
            var result = statement.executeQuery();
            result.next();
            return result.getBigDecimal("gemiddelde");
        }
    }
    public List<Brouwer> getBrouwersMetBovenGemiddeldeOmzet() throws SQLException {
        var sql = """
                select id, naam, adres, postcode, gemeente, omzet
                from brouwers
                where omzet > (select avg(omzet) from brouwers)
                """;
        var brouwers = new ArrayList<Brouwer>();
        try (var connection = super.getConnection();
            var statement = connection.prepareStatement(sql)) {
            var result = statement.executeQuery();
            while (result.next()) {
                brouwers.add(naarBrouwer(result));
            }
            return brouwers;
        }
    }
    private Brouwer naarBrouwer(ResultSet result) throws SQLException {
        return new Brouwer(result.getLong("id"), result.getString("naam"),
                result.getString("adres"), result.getInt("postcode"),
                result.getString("gemeente"), result.getInt("omzet"));
    }
    public List<Brouwer> getBrouwersMetOmzetTussenMinEnMax(int minimum, int maximum) throws SQLException {
        var brouwers = new ArrayList<Brouwer>();
        var sql = """
                select id, naam, adres, postcode, gemeente, omzet
                from brouwers
                where omzet between ? and ?
                order by omzet, id
                """;
        try (var connection = super.getConnection();
            var statement = connection.prepareStatement(sql)) {
            statement.setInt(1, minimum);
            statement.setInt(2, maximum);
            for (var result = statement.executeQuery(); result.next(); ) {
                brouwers.add(naarBrouwer(result));
            }
            return brouwers;
        }
    }
    public Optional<Brouwer> findById(long id) throws SQLException {
        var sql = """
                select id, naam, adres, postcode, gemeente, omzet
                from brouwers
                where id = ?
                """;
        try (var connection = super.getConnection();
            var statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            var result = statement.executeQuery();
            return result.next() ? Optional.of(naarBrouwer(result)) : Optional.empty();
        }
    }
}
