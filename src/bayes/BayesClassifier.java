package bayes;

import java.util.Collection;
import java.util.Comparator;
import java.util.SortedSet;
import java.util.TreeSet;

// features: classify(feat1,...,featN) = argmax(P(cat)*PROD(P(featI|cat)
// http://en.wikipedia.org/wiki/Naive_Bayes_classifier
 
public class BayesClassifier extends Classifier {

    // Calculates the product of all feature probabilities: PROD(P(featI|cat).
    private double featuresProbabilityProduct(Collection<String> features,
            String category) {
        double product = 1.0f;
        for (String feature : features)
            product += Math.log(this.featureWeighedAverage(feature, category));
        return product;
    }
 
    //Calculates the probability that the features can be classified as the
    //category given.
    private double categoryProbability(Collection<String> features, String category) {
        return ((double) this.categoryCount(category)
                    / (double) this.getCategoriesTotal())
                * featuresProbabilityProduct(features, category) ;
    }
 
    //Retrieves a sorted Set of probabilities that the given set
    //of features is classified as the available categories.
    private SortedSet<Classification> categoryProbabilities(
            Collection<String> features) {
        SortedSet<Classification> probabilities =
                new TreeSet<Classification>(
                        new Comparator<Classification>() {
                    @Override
                    public int compare(Classification o1,
                            Classification o2) {
                        int toReturn = Double.compare(
                                o1.getProbability(), o2.getProbability());
                        if ((toReturn == 0)
                                && !o1.getCategory().equals(o2.getCategory()))
                            toReturn = -1;
                        return toReturn;
                    }
                });

        for (String category : this.getCategories())
            probabilities.add(new Classification(
                    features, category,
                    this.categoryProbability(features, category)));
       
        return probabilities;
    }

    @Override
    public Classification classify(Collection<String> features) {
        SortedSet<Classification> probabilites =
                this.categoryProbabilities(features);

        if (probabilites.size() > 0) {
        	if (probabilites.last().getProbability() == 0.0)
        		return probabilites.first();
            return probabilites.last();
        }
        return null;
    }
 
    public Collection<Classification> classifyDetailed(
            Collection<String> features) {
        return this.categoryProbabilities(features);
    }

}
