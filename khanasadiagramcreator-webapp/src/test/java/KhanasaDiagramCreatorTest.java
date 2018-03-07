import org.dynamicus.khanasa.KhanasaDiagramCreator;
import org.dynamicus.model.DbSchema;
import org.junit.Test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class KhanasaDiagramCreatorTest {

    @Test
    public void emulateTableNameList() throws SQLException {

        DbSchema dbBean = new DbSchema();
        List<String> fkAndTableNameList = new ArrayList<>();

        fkAndTableNameList.addAll(Arrays.asList("dbsource -> infoprod"
                , "dbsource -> infocust"
                , "infoprod -> infocust"));
        dbBean.setFkAndTableNameList(fkAndTableNameList);

        KhanasaDiagramCreator diagramCreator = new KhanasaDiagramCreator();
        assertEquals(createRelationName().toString(), diagramCreator.getGraphStringBuilder(dbBean).toString());
    }

    private StringBuilder createRelationName() {
        StringBuilder facit = new StringBuilder
                ("digraph G {\n\trankdir = LR;\n" +
                        "\tsubgraph cluster_0 {\n\t\tlabel = \"KHANASA Diagram\";\n\t\tnode [shape = circle]\n\t\t");
        facit.append("edge [dir=\"both\", arrowhead=\"box\", arrowtail=\"obox\"]\n\t\t");
        facit.append("dbsource -> infoprod ");
        facit.append("dbsource -> infocust ");
        facit.append("infoprod -> infocust ");
        facit.append("} }");
        return facit;
    }
}
