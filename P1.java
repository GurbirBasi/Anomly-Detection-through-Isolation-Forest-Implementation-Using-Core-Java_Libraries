import java.io.*;
import java.util.*;

import static java.util.stream.Collectors.toMap;

public class P1 {
    public static void main(String[] args) {
        List<List<Double>> points = new ArrayList<>();
        List<String> rawDataPoints = new ArrayList<>();


        //input(from command line argument args[0]) including error handling
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(System.in));
            String s;
            while ((s = br.readLine( )) != null) {
                rawDataPoints.add(s);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        int k = Integer.parseInt(rawDataPoints.get(0));
        System.out.println("The top-k (" + k + ") anomalies are:");
        rawDataPoints.remove(0);

        for(String dataPoint: rawDataPoints) {
            String[] stringCoordinates = dataPoint.split(",");
            List<Double> dataCoordinates = new ArrayList<>();
            for(int i =0; i < stringCoordinates.length; i++){
                dataCoordinates.add(Double.parseDouble(stringCoordinates[i]));
            }
            points.add(dataCoordinates);
        }

        int nTrees = points.size(), subSamplingSize = points.size()/2;

        iForest iF = new iForest();
        double[] pathLengths = new double[points.size()];
        for (int i = 0; i < pathLengths.length; i++) {
            pathLengths[i] = 0;
        }



        //create a list of iTrees ie Forest

        List<iTree> iTrees = iF.createIForest(points, nTrees, subSamplingSize);


        //Find the average path length for each point

        for (int i = 0; i < points.size(); i++) {
            for (int j = 0; j < iTrees.size(); j++) {
                pathLength PL = new pathLength();
                pathLengths[i] += PL.findPathLength(points.get(i), iTrees.get(j), 0);
            }
            pathLengths[i] /= iTrees.size();
        }


        //Find average value of C(N) for N = Tree Size

        double cN = 0;
        for (int l = 0; l < iTrees.size(); l++) {
            double sizeTree = treeSize(iTrees.get(l));
            cN += 2 * (Math.log(sizeTree - 1) + 0.5772156649 - (sizeTree - 1) / sizeTree);
        }
        double averageCN = cN / iTrees.size();


        //Find Anamoly Scores for each point

        List<Double> anamolyScores = new ArrayList<>();
        for (int m = 0; m < points.size(); m++) {
            anamolyScores.add(Math.pow(2, -(pathLengths[m] / averageCN)));
        }


        //get top k anamolies

        Map<List<Double>, Double> map = new HashMap<>();
        for(int p = 0; p < points.size(); p++){
            map.put(points.get(p), anamolyScores.get(p));
        }
        //Sort map by value
        Map<List<Double>, Double> sorted = map.entrySet().stream().sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                .collect(toMap(Map.Entry::getKey, Map.Entry::getValue,(e1,e2) -> e2, LinkedHashMap::new));

        //extract keys into a list
        List<List<Double>> keys = new ArrayList<>();
        for(List<Double> key: sorted.keySet()){
            keys.add(key);
        }
        //print top k anamolies
        for(int i = 0; i < k; i++){
            System.out.println((i+1)+":\t"+ keys.get(i));
        }
    }


    // static function to get the size of the tree
    static  double treeSize(iTree tree){
        if(tree.left == null && tree.right == null){
            return 0;
        }
        else {
            return 1 + treeSize(tree.left) + treeSize(tree.right);
        }
    }


}

