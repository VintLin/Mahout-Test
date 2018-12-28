package com.felink.workflow.model;

import com.felink.workflow.enumtype.SimilarityEnum;
import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.similarity.*;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.similarity.ItemSimilarity;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;

public class ReSimilarity {
    /**
     * similarity
     */

    public static UserSimilarity userSimilarity(SimilarityEnum type, DataModel m) throws TasteException {
        switch (type) {
            case PEARSON:
                return new PearsonCorrelationSimilarity(m);
            case COSINE:
                return new UncenteredCosineSimilarity(m);
            case TANIMOTO:
                return new TanimotoCoefficientSimilarity(m);
            case LOGLIKELIHOOD:
                return new LogLikelihoodSimilarity(m);
            case SPEARMAN:
                return new SpearmanCorrelationSimilarity(m);
            case CITYBLOCK:
                return new CityBlockSimilarity(m);
            case EUCLIDEAN:
            default:
                return new EuclideanDistanceSimilarity(m);
        }
    }

    public static ItemSimilarity itemSimilarity(SimilarityEnum type, DataModel m) throws TasteException {
        switch (type) {
            case PEARSON:
                return new PearsonCorrelationSimilarity(m);
            case COSINE:
                return new UncenteredCosineSimilarity(m);
            case TANIMOTO:
                return new TanimotoCoefficientSimilarity(m);
            case LOGLIKELIHOOD:
                return new LogLikelihoodSimilarity(m);
            case CITYBLOCK:
                return new CityBlockSimilarity(m);
            case EUCLIDEAN:
            default:
                return new EuclideanDistanceSimilarity(m);
        }
    }

}
