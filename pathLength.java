import java.util.List;

public class pathLength {
    int pthLen;
    int findPathLength(List<Double> cords, iTree tree, int currentPathLength){
        this.pthLen = currentPathLength;
        //Extrnal Node.
        if(tree.left == null && tree.right == null  ){
            return this.pthLen;
        }if (tree.height >= tree.hLimit){
            return this.pthLen;
        }//internal Node -> go left or right
        else{
            if(cords.get(tree.divisionIndex) < tree.spltPnt){
                return findPathLength(cords, tree.left, this.pthLen + 1);
            }else{
                return findPathLength(cords, tree.right, this.pthLen + 1);
            }
        }

    }
}
