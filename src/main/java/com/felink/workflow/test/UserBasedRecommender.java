package com.felink.workflow.test;

import com.felink.workflow.Path;
import org.apache.mahout.cf.taste.impl.model.GenericBooleanPrefDataModel;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.LogLikelihoodSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;

import java.io.File;
import java.util.List;

public class UserBasedRecommender {
    public List<RecommendedItem> recommender(long userID, int size) {
        // step:1 构建模型 2 计算相似度 3 查找k紧邻 4 构造推荐引擎
        List<RecommendedItem> recommendations = null;
        try {
            DataModel model = new GenericBooleanPrefDataModel(
                    GenericBooleanPrefDataModel.toDataMap(
                            new FileDataModel(new File(Path.TEMP_PATH + "packup_thumb.csv"))));
            UserSimilarity similarity = new LogLikelihoodSimilarity(model);//用PearsonCorrelation 算法计算用户相似度
            UserNeighborhood neighborhood = new NearestNUserNeighborhood(10, similarity, model);//计算用户的“邻居”，这里将与该用户最近距离为 3 的用户设置为该用户的“邻居”。
            Recommender recommender = new GenericUserBasedRecommender(model, neighborhood, similarity);//构造推荐引擎，采用 CachingRecommender 为 RecommendationItem 进行缓存
            recommendations = recommender.recommend(userID, size);//得到推荐的结果，size是推荐接过的数目
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return recommendations;
    }

    public static void main(String[] args) {
        UserBasedRecommender recommender = new UserBasedRecommender();
        System.out.println(recommender.recommender(10, 10));
    }
}
