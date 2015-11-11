package cscie97.asn3.knowledge.engine;

import cscie97.asn3.knowledge.engine.domain.Triple;
import cscie97.asn3.knowledge.engine.exception.BulkQueryException;
import cscie97.asn3.knowledge.engine.exception.QueryEngineException;
import cscie97.asn3.knowledge.engine.parser.QueryParser;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Set;

/**
 *
 */
public class QueryEngine {

    /**
     * Public method for executing a single query on the knowledge graph.
     * Checks for non null and well-formed query string.
     * @param queryText
     * @throws QueryEngineException on error.
     */
    public void executeQuery(String queryText) throws QueryEngineException{
        QueryParser queryParser = new QueryParser(queryText);

        if(!queryParser.validate()){
            String errorMessage = queryParser.getErrorMessage();
            throw new QueryEngineException(queryText, errorMessage);
        }

        Triple query = queryParser.getQuery();
        Set<Triple> resultSet = KnowledgeGraph.getInstance().executeQuery(query.getSubject(), query.getPredicate(), query.getObject());
        System.out.println(queryText);

        if(resultSet.isEmpty()){
            System.out.println("<null>");
        }else{
            resultSet.forEach(System.out::println);
        }
    }


    /**
     * Public method for executing a set of queries read from a file.  
     * Checks for valid file  name. 
     * Delegates to executeQuery for processing individual queries.
     * @param fileName
     * @throws QueryEngineException on error.
     * @throws BulkQueryException on error.
     */
    public void executeQueryFile(String fileName) throws BulkQueryException, QueryEngineException {
        BufferedReader reader = null;
        int lineNum = 0;
        String queryText = null;
        try {
            reader = new BufferedReader(new FileReader(fileName));
            while ((queryText = reader.readLine()) != null) {
                lineNum++;
                executeQuery(queryText);
            }
        } catch (IOException e) {
            throw new BulkQueryException(fileName, e.getMessage());
        } catch (QueryEngineException e){
            throw new BulkQueryException(fileName, lineNum, queryText, e.getMessage());
        }finally {
            try {
                if(reader != null){
                    reader.close();
                }
            } catch (IOException ioe) {
                throw new BulkQueryException(fileName, ioe.getMessage());
            }
        }

    }
}
