package cscie97.asn4.knowledge.engine;

import cscie97.asn4.knowledge.engine.domain.Node;
import cscie97.asn4.knowledge.engine.domain.Predicate;
import cscie97.asn4.knowledge.engine.domain.Triple;

import java.util.*;

/**
 * This class represents a graph between subjects, predicates, and objects.
 * It can be used to import triples, and to query for them.
 */
public class KnowledgeGraph {

    private static final KnowledgeGraph INSTANCE = new KnowledgeGraph();

    private final Map<String, Node> nodeMap;

    private final Map<String, Triple> tripleMap;

    private final Map<String, Predicate> predicateMap;

    private final Map<String, Set<Triple>> queryMapSet;

    private KnowledgeGraph() {
        this.nodeMap = new HashMap<>();
        this.tripleMap = new HashMap<>();
        this.predicateMap = new HashMap<>();
        this.queryMapSet = new HashMap<>();
    }

    /**
     * This method returns a reference to the single static instance of the KnowledgeGraph.
     * @return the KnowledgeGraph. 
     */
    public static KnowledgeGraph getInstance() {
        return INSTANCE;
    }

    /**
     * Public method for adding a list of Triples to the KnowledgeGraph.
     * @param tripleList
     */
    public void importTriples(List<Triple> tripleList){
        // The following associations must be updated: nodeMap, tripleMap, queryMapSet, predicateMap to reflect the added Triple.  
        // There should be one Triple instance per unique Subject, Predicate, Object combination, so that Triples are not duplicated.
        for (Triple newTriple : tripleList){

            Node subject = getNode(newTriple.getSubject().getIdentifier());
            Predicate predicate = getPredicate(newTriple.getPredicate().getIdentifier());
            Node object = getNode(newTriple.getObject().getIdentifier());

            Triple triple = getTriple(subject, predicate, object);
            updateQueryMapSet(triple);
        }
    }

    private void addQueryResult(String query, Triple newResult) {
        //get current set of results for this query
        Set<Triple> resultSet = queryMapSet.get(query);

        //instantiate the results set, if required
        if(resultSet == null){
            resultSet = new HashSet<>();
            queryMapSet.put(query, resultSet);
        }

        //add the new result for the given query to resultSet.
        resultSet.add(newResult);
    }

    /**
     * The executeQuery() method supports execution of queries against the knowledge graph.  
     * The  Query is specified in the form of a Triple.  
     * Occurrences of the  ?  identifier within the Query can  be supported by leaving the 
     * associated link (subject, predicate or object) as null within the Triple. 
     * The executeQuery() method returns a Set of matching triples.  
     * Triples are unique based on the combination of Subject, Predicate, Object. 
     */
    public Set<Triple> executeQuery(Node subject, Predicate predicate, Node object){
        Triple query = new Triple(subject, predicate, object);
        //Use the queryMapSet to determine the  Triples that match the given Query.  
        // If none are found return an empty Set.
        Set<Triple> queryResult = queryMapSet.get(query.getIdentifier());

        if(queryResult == null){
            queryResult = new HashSet<>();
        }
        return queryResult;
    }

    /**
     * Return a Node Instance for the given node identifier.
     * Node identifiers are case insensitive.
     * @param identifier a unique identifier for Node.
     * @return the Node corresponding to the given identifier.
     */
    public Node getNode(String identifier){
        String cleanedId = cleanIdentifier(identifier);
        //Use the nodeMap to look up the  Node.  
        // If the Node does not exist, create it and add it to the nodeMap.
        Node node = nodeMap.get(cleanedId);

        if(node == null){
            node = new Node(identifier);
            nodeMap.put(cleanedId, node);
        }

        return node;
    }

    private String cleanIdentifier(String identifier) {
        identifier = identifier.trim().toLowerCase();
        return identifier;
    }

    public Predicate getPredicate(String identifier){
        String cleanedIdentifier = cleanIdentifier(identifier);
        Predicate predicate = predicateMap.get(cleanedIdentifier);

        if(predicate == null){
            predicate = new Predicate(cleanedIdentifier);
            predicateMap.put(cleanedIdentifier, predicate);
        }

        return predicate;
    }

    /**
     * Returns the Triple instance for the given  Object, Predicate and Subject.  
     * Uses the  tripleMap to lookup the Triple.  
     * If the Triple  does not exist, creates it and adds it to the tripleMap.
     * @param subject
     * @param predicate
     * @param object
     * @return
     */
    public Triple getTriple(Node subject, Predicate predicate, Node object){
        Triple triple = getOrCreateTriple(subject, predicate, object);
        return triple;
    }

    private Triple getOrCreateTriple(Node subject, Predicate predicate, Node object) {
        String tripleIdentifier = Triple.getIdentifier(subject, predicate, object);
        Triple triple = tripleMap.get(tripleIdentifier);

        if(triple == null){
            triple = new Triple(subject, predicate, object);
            tripleMap.put(tripleIdentifier, triple);
        }
        return triple;
    }

    /**
     * This method generates all queries which can return this triple as their output.
     * For each generated query, this method adds this triple as a result of that query.
     * @param triple given triple.
     */
    private void updateQueryMapSet(Triple triple) {
        addQueryResult(triple.getIdentifier(), triple);

        Triple query1 = getOrCreateTriple(getNode("?"), triple.getPredicate(), triple.getObject());
        addQueryResult(query1.getIdentifier(), triple);

        Triple query2 = getOrCreateTriple(triple.getSubject(), getPredicate("?"), triple.getObject());
        addQueryResult(query2.getIdentifier(), triple);

        Triple query3 = getOrCreateTriple(triple.getSubject(), triple.getPredicate(), getNode("?"));
        addQueryResult(query3.getIdentifier(), triple);

        Triple query4 = getOrCreateTriple(getNode("?"), getPredicate("?"), triple.getObject());
        addQueryResult(query4.getIdentifier(), triple);

        Triple query5 = getOrCreateTriple(triple.getSubject(), getPredicate("?"), getNode("?"));
        addQueryResult(query5.getIdentifier(), triple);

        Triple query6 = getOrCreateTriple(getNode("?"), triple.getPredicate(), getNode("?"));
        addQueryResult(query6.getIdentifier(), triple);

        Triple query7 = getOrCreateTriple(getNode("?"), getPredicate("?"), getNode("?"));
        addQueryResult(query7.getIdentifier(), triple);
    }

    /**
     * This method is used for removing given triples from all qyery results returned by KnowledgeGraph.
     * @param triples
     */
    public void removeTriples(Set<Triple> triples) {
        triples.forEach((triple) -> {
            removeQueryResult(triple.getIdentifier(), triple);

            Triple query1 = getOrCreateTriple(getNode("?"), triple.getPredicate(), triple.getObject());
            removeQueryResult(query1.getIdentifier(), triple);

            Triple query2 = getOrCreateTriple(triple.getSubject(), getPredicate("?"), triple.getObject());
            removeQueryResult(query2.getIdentifier(), triple);

            Triple query3 = getOrCreateTriple(triple.getSubject(), triple.getPredicate(), getNode("?"));
            removeQueryResult(query3.getIdentifier(), triple);

            Triple query4 = getOrCreateTriple(getNode("?"), getPredicate("?"), triple.getObject());
            removeQueryResult(query4.getIdentifier(), triple);

            Triple query5 = getOrCreateTriple(triple.getSubject(), getPredicate("?"), getNode("?"));
            removeQueryResult(query5.getIdentifier(), triple);

            Triple query6 = getOrCreateTriple(getNode("?"), triple.getPredicate(), getNode("?"));
            removeQueryResult(query6.getIdentifier(), triple);

            Triple query7 = getOrCreateTriple(getNode("?"), getPredicate("?"), getNode("?"));
            removeQueryResult(query7.getIdentifier(), triple);
        });
    }

    private void removeQueryResult(String queryId, Triple unwantedResult) {
        Set<Triple> queryResults = queryMapSet.get(queryId);

        if(queryResults != null){
            queryResults.remove(unwantedResult);

            if(queryResults.isEmpty()){
                queryMapSet.remove(queryId);
            }
        }
    }
}
