package be.vdab.repositories;

import be.vdab.domain.Brouwer;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
}
