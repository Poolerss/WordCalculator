
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class WordFrequencyCalculator {
    private final Map<String, Integer> wordFrequency;

    public WordFrequencyCalculator(){
        wordFrequency = new HashMap<>();
    }

    public void analyzeFile(String... filenames) throws FileNotFoundException {
    for (String filename : filenames){
        try(BufferedReader reader = new BufferedReader(new FileReader(filename))){
            String line;
            while ((line = reader.readLine()) != null){
                String[] words = line.split("[^а-яА-Я-]+");
                for (String word : words) {
                    if (!word.isEmpty()){
                        word = word.toLowerCase();
                        wordFrequency.put(word, wordFrequency.getOrDefault(word, 0)+1 );
                    }
                }
            }
        }catch (IOException e){
            System.err.println("Error read file" + filename     );
            e.printStackTrace();
        }

    }
    }

    public void generateReports(){
        List<Map.Entry<String, Integer>> sortedByAlphabet = new ArrayList<>(wordFrequency.entrySet());
        sortedByAlphabet.sort(Comparator.comparing(Map.Entry::getKey));

        List<Map.Entry<String, Integer>> sortedByAlphabetRev = new ArrayList<>(sortedByAlphabet);
        sortedByAlphabetRev.sort(Comparator.comparing(entry -> new StringBuilder(entry.getKey()).reverse().toString()));

        List<Map.Entry<String, Integer>> sortedByFrequency = new ArrayList<>(sortedByAlphabet);
        sortedByFrequency.sort(Map.Entry.<String,Integer>comparingByValue(Comparator.reverseOrder()).thenComparing(Map.Entry.comparingByKey()));


        reportByAlphabet(sortedByAlphabet, "reportByAlhabet.txt");
        reportByAlphabet(sortedByAlphabetRev, "reportByAlphabetRev.txt");
        reportByFrequency(sortedByFrequency, "reportByFrenquency");

    }

    private double getRelativeFrequency(int frequency){
        int totalWords = wordFrequency.values().stream().mapToInt(Integer::intValue).sum();
        return (double) frequency/totalWords * 100;
    }

    private void reportByAlphabet(List<Map.Entry<String, Integer>> sortedByAlphabet, String filename){
        try(Formatter formatter = new Formatter(filename)){
            for (Map.Entry<String, Integer> entry : sortedByAlphabet){
                formatter.format("%s\t%d\tt%.2f%%%n", entry.getKey(), entry.getValue(), getRelativeFrequency(entry.getValue()));
            }
        }catch (IOException e){
            System.err.println("error read report" + filename);
            e.printStackTrace();
        }

    }

    private void  reportByFrequency(List<Map.Entry<String, Integer>> sortedByFrequency, String filename){

        try(Formatter formatter = new Formatter(filename)){

            for (Map.Entry<String,Integer> entry : sortedByFrequency){
                formatter.format("%s\t%d\t%2f%%%n", entry.getKey(), entry.getValue(),getRelativeFrequency(entry.getValue()));
            }
        }catch (IOException e){
            System.err.println("error read report" + filename);
            e.printStackTrace();
        }
    }

    public Map<String, Integer> getWordFrequency (){
        return Collections.unmodifiableMap(wordFrequency);
    }
}
