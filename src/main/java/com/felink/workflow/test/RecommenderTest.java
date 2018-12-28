package com.felink.workflow.test;
import java.io.IOException;
import java.util.List;

import com.felink.workflow.Path;
import com.felink.workflow.enumtype.EvaluatorEnum;
import com.felink.workflow.enumtype.NeighborhoodEnum;
import com.felink.workflow.enumtype.SimilarityEnum;
import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.eval.RecommenderBuilder;
import org.apache.mahout.cf.taste.impl.common.LongPrimitiveIterator;
import org.apache.mahout.cf.taste.impl.recommender.svd.ALSWRFactorizer;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.similarity.ItemSimilarity;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;
import org.apache.mahout.common.RandomUtils;
import com.felink.workflow.model.*;


public class RecommenderTest {

    final static int NEIGHBORHOOD_NUM = 2;
    final static int RECOMMENDER_NUM = 3;

    public static void main(String[] args) throws TasteException, IOException {
        RandomUtils.useTestSeed();
        String file = Path.TEMP_PATH + "packup_thumb.csv";
        DataModel dataModel = ReModel.DataModel(file, false);
        userCF(dataModel);
    }

    public static void userCFNoRerf(DataModel dataModel) throws TasteException {
        UserSimilarity userSimilarity = ReSimilarity.userSimilarity(SimilarityEnum.LOGLIKELIHOOD, dataModel);

    }

    public static void userCF(DataModel dataModel) throws TasteException {
        UserSimilarity userSimilarity = ReSimilarity.userSimilarity(SimilarityEnum.LOGLIKELIHOOD, dataModel);
        UserNeighborhood userNeighborhood = ReNeighborhood.userNeighborhood(NeighborhoodEnum.NEAREST, userSimilarity, dataModel, NEIGHBORHOOD_NUM);
        RecommenderBuilder recommenderBuilder = ReCommend.userRecommender(userSimilarity, userNeighborhood, true);

        ReEvaluator.evaluate(EvaluatorEnum.AVERAGE_ABSOLUTE_DIFFERENCE, recommenderBuilder, null, dataModel, 0.7);
        ReStatsEvaluator.statsEvaluator(recommenderBuilder, null, dataModel, 2);

        LongPrimitiveIterator iter = dataModel.getUserIDs();
        while (iter.hasNext()) {
            long uid = iter.nextLong();
            List<RecommendedItem> list = recommenderBuilder.buildRecommender(dataModel).recommend(uid, RECOMMENDER_NUM);
            ReCommend.showItems(uid, list, true);
        }
    }

    public static void itemCF(DataModel dataModel) throws TasteException {
        ItemSimilarity itemSimilarity = ReSimilarity.itemSimilarity(SimilarityEnum.EUCLIDEAN, dataModel);
        RecommenderBuilder recommenderBuilder = ReCommend.itemRecommender(itemSimilarity, true);

        ReEvaluator.evaluate(EvaluatorEnum.AVERAGE_ABSOLUTE_DIFFERENCE, recommenderBuilder, null, dataModel, 0.7);
        ReStatsEvaluator.statsEvaluator(recommenderBuilder, null, dataModel, 2);

        LongPrimitiveIterator iter = dataModel.getUserIDs();
        while (iter.hasNext()) {
            long uid = iter.nextLong();
            List<RecommendedItem> list = recommenderBuilder.buildRecommender(dataModel).recommend(uid, RECOMMENDER_NUM);
            ReCommend.showItems(uid, list, true);
        }
    }

    public static void svd(DataModel dataModel) throws TasteException {
        RecommenderBuilder recommenderBuilder = ReCommend.svdRecommender(new ALSWRFactorizer(dataModel, 10, 0.05, 10));

        ReEvaluator.evaluate(EvaluatorEnum.AVERAGE_ABSOLUTE_DIFFERENCE, recommenderBuilder, null, dataModel, 0.7);
        ReStatsEvaluator.statsEvaluator(recommenderBuilder, null, dataModel, 2);

        LongPrimitiveIterator iter = dataModel.getUserIDs();
        while (iter.hasNext()) {
            long uid = iter.nextLong();
            List<RecommendedItem> list = recommenderBuilder.buildRecommender(dataModel).recommend(uid, RECOMMENDER_NUM);
            ReCommend.showItems(uid, list, true);
        }
    }

}