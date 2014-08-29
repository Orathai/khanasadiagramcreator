package org.dynamicus.khanasa;

import org.dynamicus.bean.DatabaseCredentialBean;
import org.dynamicus.bean.DatabaseSchemaBean;
import org.dynamicus.jdbc.TableNameList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.List;

public class KhanasaDiagramCreator {
    private static final Logger log = LoggerFactory.getLogger(KhanasaDiagramCreator.class);

    public void khanasaDiagramCreator(DatabaseCredentialBean dbCredential) {

        StringBuilder graph = getGraphStringBuilder(new TableNameList().getTableNameList(dbCredential));

        log.debug("generating graph..");
        GraphViz gv = new GraphViz();
        String type = "png";
        File out = new File("/tmp/khanasadiagram." + type);
        log.debug("..saving .png file to : " + out);

        gv.writeGraphToFile(gv.getGraph(graph.toString(), type), out);
    }

    public StringBuilder getGraphStringBuilder(DatabaseSchemaBean dbBean) {
        StringBuilder graph = new StringBuilder();
        graph.append("digraph G {\n")
                .append("\trankdir = LR;\n")
                .append("\tsubgraph cluster_0 {\n")
                .append("\t\tlabel = \"KHANASA Diagram\";\n")
                .append("\t\tnode [shape = circle]\n")
                .append("\t\tedge [dir=\"both\", arrowhead=\"box\", arrowtail=\"obox\"]\n")
                .append("\t\t")
                .append(buildNodeWithNameAndForeignKey(dbBean))
                .append(" } }");

        log.debug(graph.toString());
        return graph;
    }

    private String buildNodeWithNameAndForeignKey(DatabaseSchemaBean db) {
        StringBuilder sb = new StringBuilder();
        List<String> fkAndTableNameList = db.getFkAndTableNameList();

        fkAndTableNameList.forEach(fkAndTableName -> {
            if (!sb.toString().isEmpty()) {
                sb.append(" ");
            }
            sb.append(fkAndTableName);
        });

        log.debug(sb.toString());
        return sb.toString();
    }

}
