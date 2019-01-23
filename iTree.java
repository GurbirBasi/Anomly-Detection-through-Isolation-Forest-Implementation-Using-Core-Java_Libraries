import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class iTree {
    List<List<Double>> data, leftValues, rightValues;
    List<Double> instance;
    int hLimit, height, divisionIndex;
    double spltPnt;
    iTree left, right;


    iTree(List<List<Double>> data, int height, int hLimit) {
        if(data.size() <= 1 || height == hLimit ){
            this.data = data;
            this.height = height;
            this.hLimit = hLimit;
            this.left = this.right = null;
            this.instance = null;
            this.spltPnt = this.divisionIndex = 0;
            this.rightValues = null;
            this.rightValues = null;
        }else{
            this.data = data;
            this.height = height;
            this.hLimit = hLimit;
            Random rand = new Random();
            this.divisionIndex = rand.nextInt(data.get(0).size());
            this.spltPnt = data.get(rand.nextInt(data.size())).get(divisionIndex);
            this.instance = createInstance(data, divisionIndex);
            this.leftValues = filter(data, spltPnt, divisionIndex, true);
            this.rightValues = filter(data, spltPnt, divisionIndex, false);
            this.left = new iTree(this.leftValues, this.height + 1, this.hLimit);
            this.right = new iTree(this.rightValues, this.height + 1, this.hLimit);
        }
    }

    private List<Double> createInstance(List<List<Double>> listPoints, int pivot){
        List<Double> points = new ArrayList<>();
        for(int i = 0; i < listPoints.size(); i++){
            points.add(listPoints.get(i).get(pivot));
        }
        return points;
    }

    private List<List<Double>> filter(List<List<Double>> rawData, double check, int dmnsn, boolean flag){
        List<List<Double>> filteredData = new ArrayList<>();
        for(int j = 0; j < rawData.size(); j++){
           if(flag) {
               if (rawData.get(j).get(dmnsn) < check) {
                   filteredData.add(rawData.get(j));
               }
           }else{
               if (rawData.get(j).get(dmnsn) >= check){
                   filteredData.add(rawData.get(j));
               }
           }
        }
        return filteredData;
    }
}
