package com.felink.workflow.test;


import java.io.IOException;
import java.util.List;

import com.felink.workflow.model.ReCommend;
import com.felink.workflow.model.ReModel;
import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.eval.RecommenderBuilder;
import org.apache.mahout.cf.taste.impl.common.LongPrimitiveIterator;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;

public class RecommenderResult {

    final static int NEIGHBORHOOD_NUM = 2;
    final static int RECOMMENDER_NUM = 3;

    public static void main(String[] args) throws TasteException, IOException {
        String file = "packup_thumb.csv";
        DataModel dataModel = ReModel.DataModel(file, false);
        RecommenderBuilder rb1 = RecommenderEvaluator.userCityBlock(dataModel);
        RecommenderBuilder rb2 = RecommenderEvaluator.itemLoglikelihood(dataModel);

        LongPrimitiveIterator iter = dataModel.getUserIDs();
        while (iter.hasNext()) {
            long uid = iter.nextLong();
            System.out.print("userCityBlock    =>");
            result(uid, rb1, dataModel);
            System.out.print("itemLoglikelihood=>");
            result(uid, rb2, dataModel);
        }
    }

    public static void result(long uid, RecommenderBuilder recommenderBuilder, DataModel dataModel) throws TasteException {
        List<RecommendedItem> list = recommenderBuilder.buildRecommender(dataModel).recommend(uid, RECOMMENDER_NUM);
        ReCommend.showItems(uid, list, false);
    }

}