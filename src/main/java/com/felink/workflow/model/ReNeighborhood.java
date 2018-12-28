package com.felink.workflow.model;

import com.felink.workflow.enumtype.NeighborhoodEnum;
import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.neighborhood.ThresholdUserNeighborhood;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;

public class ReNeighborhood {
    /**
     * neighborhood
     */

    public static UserNeighborhood userNeighborhood(NeighborhoodEnum type, UserSimilarity s, DataModel m, double num) throws TasteException {
        switch (type) {
            case NEAREST:
                return new NearestNUserNeighborhood((int) num, s, m);
            case THRESHOLD:
            default:
                return new ThresholdUserNeighborhood(num, s, m);
        }
    }
}
