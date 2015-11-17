package cscie97.asn4.knowledge.engine.parser;

/**
 * This class parses the given triple text, and validates it.
 * In case of validation errors, error message can be retrieved using {@link #getErrorMessage()}.
 * Subject, object and predicate of parsed triple can be accessed using getters.
 */
public class TripleParser {

    private final String tripleText;

    private String errorMessage;
    private String subject;
    private String predicate;
    private String object;

    public TripleParser(String tripleText) {
        this.tripleText = tripleText;
    }

    /**
     * Performs following validations on the given tripleText.
     * <ul>
     *     <li>The triple contains exactly three space-delimited words.</li>
     *     <li>The triple does not end with a '.'.</li>
     *     <li>Neither subject, object nor predicate uses '?' as an identifier.</li>
     * </ul>
     * @return true, if and only if triple was parsed successfully.
     */
    public boolean validate() {
        if(tripleText == null){
            errorMessage = "Triple text is null";
            return false;
        }

        tripleText.trim();

        if("".equals(tripleText)){
            errorMessage = "Triple text is empty";
            return false;
        }

        String tripleTerminator = tripleText.substring(tripleText.length()-1);
        String processedTripleText = tripleText.substring(0, tripleText.length()-1);

        if(!".".equals(tripleTerminator)){
            errorMessage = "Triple text does not terminate with '.' :'"
                    + tripleText +"'";
            return false;
        }

        String[] triple = processedTripleText.split(" ");

        if(triple.length < 3){
            errorMessage = "Triple text is missing subject, predicate, or object:'"
            + tripleText +"'";
            return false;
        }

        if(triple.length > 3){
            errorMessage = "Triple text contains too many subject, predicate, or object:'"
                    + tripleText +"'";
            return false;
        }

        if(triple[0].equals("?")){
            errorMessage = "Triple text uses reserved keyword '?' in subject:'"
                    + tripleText +"'";
            return false;
        }

        if(triple[1].equals("?")){
            errorMessage = "Triple text uses reserved keyword '?' in predicate:'"
                    + tripleText +"'";
            return false;
        }

        if(triple[2].equals("?")){
            errorMessage = "Triple text uses reserved keyword '?' in object:'"
                    + tripleText +"'";
            return false;
        }

        subject = triple[0];
        predicate = triple[1];
        object = triple[2];
        return true;
    }

    /**
     * Returns error message, if an earlier call to validate() method has returned false.
     * @return error message. Returns <code>null</code> if validate() method has not been invoked yet,
     * or there are no error messages to report.
     */
    public String getErrorMessage() {
        return errorMessage;
    }

    public String getSubject() {
        return subject;
    }

    public String getPredicate() {
        return predicate;
    }

    public String getObject() {
        return object;
    }
}
