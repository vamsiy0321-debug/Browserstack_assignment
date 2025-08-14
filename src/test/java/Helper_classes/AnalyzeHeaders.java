package Helper_classes;

import java.util.*;

public class AnalyzeHeaders {

    public static void analyze(List<String> headers){
        List<String> words = new ArrayList<>();
        int c=0;
        for(String h:headers){
            String s = h.replaceAll("[()|?,]","").toLowerCase();
            words.addAll(Arrays.asList(s.split("\\s+")));
        }
        Map<String,Integer> wordCount = new HashMap<>();
        for(String header:words){
            wordCount.put(header,wordCount.getOrDefault(header,0)+1);
        }

        for (String k:wordCount.keySet()){
            if(wordCount.get(k)>2){
                System.out.println(k+" "+wordCount.get(k));
                c++;
            }
        }
        if(c==0){
            System.out.println("No words are repeated twice in the translated headers");
        }
    }
}
