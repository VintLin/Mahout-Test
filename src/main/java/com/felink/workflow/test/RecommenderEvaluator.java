package com.felink.workflow.test;

import java.io.IOException;

import com.felink.workflow.Path;
import com.felink.workflow.enumtype.EvaluatorEnum;
import com.felink.workflow.enumtype.NeighborhoodEnum;
import com.felink.workflow.enumtype.SimilarityEnum;
import com.felink.workflow.model.*;
import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.eval.RecommenderBuilder;
import org.apache.mahout.cf.taste.impl.recommender.svd.ALSWRFactorizer;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.similarity.ItemSimilarity;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;

public class RecommenderEvaluator {

    final static int NEIGHBORHOOD_NUM = 2;
    final static int RECOMMENDER_NUM = 3;

    public static void main(String[] args) throws TasteException, IOException {
        String file = Path.TEMP_PATH + "packup_thumb.csv";
        DataModel dataModel = ReModel.DataModel(file, false);
        userLoglikelihood(dataModel);
        userCityBlock(dataModel);
        userTanimoto(dataModel);
        itemLoglikelihood(dataModel);
        itemCityBlock(dataModel);
        itemTanimoto(dataModel);
    }

    public static RecommenderBuilder userLoglikelihood(DataModel dataModel) throws TasteException, IOException {
        System.out.println("userLoglikelihood");
        UserSimilarity userSimilarity = ReSimilarity.userSimilarity(SimilarityEnum.LOGLIKELIHOOD, dataModel);
        UserNeighborhood userNeighborhood = ReNeighborhood.userNeighborhood(NeighborhoodEnum.NEAREST, userSimilarity, dataModel, NEIGHBORHOOD_NUM);
        RecommenderBuilder recommenderBuilder = ReCommend.userRecommender(userSimilarity, userNeighborhood, false);

        ReEvaluator.evaluate(EvaluatorEnum.AVERAGE_ABSOLUTE_DIFFERENCE, recommenderBuilder, null, dataModel, 0.7);
        ReStatsEvaluator.statsEvaluator(recommenderBuilder, null, dataModel, 2);
        return recommenderBuilder;
    }

    public static RecommenderBuilder userCityBlock(DataModel dataModel) throws TasteException, IOException {
        System.out.println("userCityBlock");
        UserSimilarity userSimilarity = ReSimilarity.userSimilarity(SimilarityEnum.CITYBLOCK, dataModel);
        UserNeighborhood userNeighborhood = ReNeighborhood.userNeighborhood(NeighborhoodEnum.NEAREST, userSimilarity, dataModel, NEIGHBORHOOD_NUM);
        RecommenderBuilder recommenderBuilder = ReCommend.userRecommender(userSimilarity, userNeighborhood, false);

        ReEvaluator.evaluate(EvaluatorEnum.AVERAGE_ABSOLUTE_DIFFERENCE, recommenderBuilder, null, dataModel, 0.7);
        ReStatsEvaluator.statsEvaluator(recommenderBuilder, null, dataModel, 2);
        return recommenderBuilder;
    }

    public static RecommenderBuilder userTanimoto(DataModel dataModel) throws TasteException, IOException {
        System.out.println("userTanimoto");
        UserSimilarity userSimilarity = ReSimilarity.userSimilarity(SimilarityEnum.TANIMOTO, dataModel);
        UserNeighborhood userNeighborhood = ReNeighborhood.userNeighborhood(NeighborhoodEnum.NEAREST, userSimilarity, dataModel, NEIGHBORHOOD_NUM);
        RecommenderBuilder recommenderBuilder = ReCommend.userRecommender(userSimilarity, userNeighborhood, false);

        ReEvaluator.evaluate(EvaluatorEnum.AVERAGE_ABSOLUTE_DIFFERENCE, recommenderBuilder, null, dataModel, 0.7);
        ReStatsEvaluator.statsEvaluator(recommenderBuilder, null, dataModel, 2);
        return recommenderBuilder;
    }

    public static RecommenderBuilder itemLoglikelihood(DataModel dataModel) throws TasteException, IOException {
        System.out.println("itemLoglikelihood");
        ItemSimilarity itemSimilarity = ReSimilarity.itemSimilarity(SimilarityEnum.LOGLIKELIHOOD, dataModel);
        RecommenderBuilder recommenderBuilder = ReCommend.itemRecommender(itemSimilarity, false);

        ReEvaluator.evaluate(EvaluatorEnum.AVERAGE_ABSOLUTE_DIFFERENCE, recommenderBuilder, null, dataModel, 0.7);
        ReStatsEvaluator.statsEvaluator(recommenderBuilder, null, dataModel, 2);
        return recommenderBuilder;
    }

    public static RecommenderBuilder itemCityBlock(DataModel dataModel) throws TasteException, IOException {
        System.out.println("itemCityBlock");
        ItemSimilarity itemSimilarity = ReSimilarity.itemSimilarity(SimilarityEnum.CITYBLOCK, dataModel);
        RecommenderBuilder recommenderBuilder = ReCommend.itemRecommender(itemSimilarity, false);

        ReEvaluator.evaluate(EvaluatorEnum.AVERAGE_ABSOLUTE_DIFFERENCE, recommenderBuilder, null, dataModel, 0.7);
        ReStatsEvaluator.statsEvaluator(recommenderBuilder, null, dataModel, 2);
        return recommenderBuilder;
    }

    public static RecommenderBuilder itemTanimoto(DataModel dataModel) throws TasteException, IOException {
        System.out.println("itemTanimoto");
        ItemSimilarity itemSimilarity = ReSimilarity.itemSimilarity(SimilarityEnum.TANIMOTO, dataModel);
        RecommenderBuilder recommenderBuilder = ReCommend.itemRecommender(itemSimilarity, false);

        ReEvaluator.evaluate(EvaluatorEnum.AVERAGE_ABSOLUTE_DIFFERENCE, recommenderBuilder, null, dataModel, 0.7);
        ReStatsEvaluator.statsEvaluator(recommenderBuilder, null, dataModel, 2);
        return recommenderBuilder;
    }


    public static RecommenderBuilder svd(DataModel dataModel) throws TasteException {
        System.out.println("svd");
        RecommenderBuilder recommenderBuilder = ReCommend.svdRecommender(new ALSWRFactorizer(dataModel, 5, 0.05, 10));

        ReEvaluator.evaluate(EvaluatorEnum.AVERAGE_ABSOLUTE_DIFFERENCE, recommenderBuilder, null, dataModel, 0.7);
        ReStatsEvaluator.statsEvaluator(recommenderBuilder, null, dataModel, 2);

        return recommenderBuilder;
    }

}