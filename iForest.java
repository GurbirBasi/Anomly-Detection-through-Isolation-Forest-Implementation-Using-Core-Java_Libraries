import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class iForest {
    List<List<Double>> data;
    int numTree, subSamplingSize, heightLimit;
    List<iTree> forest = new ArrayList<>();


    List<iTree> createIForest(List<List<Double>> data, int numTree, int subSamplingSize){
        this.data = data;
        this.numTree = numTree;
        this.subSamplingSize = subSamplingSize;
        this.heightLimit = (int) Math.ceil(Math.log(subSamplingSize)/Math.log(2));
        for(int i =0; i < numTree; i++){
            Collections.shuffle(data);
            forest.add(new iTree(data.subList(0, subSamplingSize), 0, this.heightLimit));
        }
        return forest;
    }
}
