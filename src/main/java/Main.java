import java.io.FileNotFoundException;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {

            WordFrequencyCalculator calculator = new WordFrequencyCalculator();
            calculator.analyzeFile("src/main/resources/j120-lab2_пример файла для обработки.txt");
            calculator.generateReports();

        Map<String, Integer> words = calculator.getWordFrequency();
        for (Map.Entry<String, Integer> entry : words.entrySet()) {
            String word = entry.getKey();
            Integer frequency = entry.getValue();
            System.out.println("Слово [" + word + "] повторяется в тексте [" + frequency + "] раз.");
        }



    }
}
