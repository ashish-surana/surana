package cscie97.asn3.knowledge.engine.exception;

/**
 * This class represents an exception during importing triples in a {@link cscie97.asn1.knowledge.engine.KnowledgeGraph}
 * using {@link cscie97.asn1.knowledge.engine.Importer}.
 */
public class ImportException extends Exception{

    private final String tripleFilePath;

    /**
     * This constructor creates a new ImportException using given parameters.
     * @param tripleFilePath Path of the offending triple file.
     * @param message Error message.
     */
    public ImportException(String tripleFilePath, String message){
        super(message);
        this.tripleFilePath = tripleFilePath;
    }

    /**
     * @return Path of the offending triple file.
     */
    public String getTripleFilePath() {
        return tripleFilePath;
    }
}
