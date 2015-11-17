package cscie97.asn4.knowledge.engine;

import cscie97.asn4.knowledge.engine.domain.Node;
import cscie97.asn4.knowledge.engine.domain.Predicate;
import cscie97.asn4.knowledge.engine.domain.Triple;
import cscie97.asn4.knowledge.engine.exception.ImportException;
import cscie97.asn4.knowledge.engine.exception.InvalidTripleFormatException;
import cscie97.asn4.knowledge.engine.parser.TripleParser;

import java.io.*;
import java.util.Collections;

/**
 * This class imports triple from a file into {@link KnowledgeGraph}.
 */
public class Importer {

    /**
     * Public method for importing triples from N_Triple formatted file into the KnowledgeGraph. 
     * Checks for valid input file name.  
     * @param tripleFilePath path to the input triple file.
     * @throws InvalidTripleFormatException if a triple is not formatted correctly.
     * @throws ImportException on error accessing or processing the input triple file. 
     */
    public void importTripleFile(String tripleFilePath) throws InvalidTripleFormatException, ImportException{
        InputStream inputStream = null;
        //Read triple file, one line at a time
        try{
            inputStream = new FileInputStream(new File(tripleFilePath));
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
            BufferedReader reader = new BufferedReader(inputStreamReader);

            String line;
            int lineNum = 0;
            while((line = reader.readLine()) !=null   ){
                lineNum++;
                Triple triple = createTriple(tripleFilePath, lineNum, line);
                //A triple can be null, if it was not correctly formatted in the input file.
                if(triple != null){
                    KnowledgeGraph.getInstance().importTriples(Collections.singletonList(triple));
                }

            }
        } catch (IOException e) {
            throw new ImportException(tripleFilePath, e.getMessage());
        }finally {
            try{
                if(inputStream != null){
                    inputStream.close();
                }
            } catch (IOException e) {
                throw new ImportException(tripleFilePath, e.getMessage());
            }
        }
    }

    /**
     * This method creates triple by parsing the given triple text.
     */
    private Triple createTriple(String filePath, int lineNum, String tripleText) throws InvalidTripleFormatException{
        try{
            TripleParser tripleParser = new TripleParser(tripleText);
//            //if it is not a valid triple, then log a warning
            if(!tripleParser.validate()){
                String errorMessage = tripleParser.getErrorMessage();
                System.out.print("Ignoring invalid triple in file: '"+filePath+ "'" +", on line:"+lineNum+". ");
                System.out.print("Root cause is: "+ errorMessage);
                System.out.println();
                return null;
            }

            Node subject = new Node(tripleParser.getSubject());
            Predicate predicate = new Predicate(tripleParser.getPredicate());
            Node object = new Node(tripleParser.getObject());

            Triple triple = new Triple(subject, predicate, object);
            return triple;
        }catch (Exception e){
            throw new InvalidTripleFormatException(filePath, lineNum, e.getMessage());
        }
    }
}
