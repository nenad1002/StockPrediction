package bayes;

import java.util.Collection;

public class Classification {
   
    private Collection<String> featureset;
    
    private String category;

    private double probability;
  
    public Classification(Collection<String> featureset, String category) {
        this(featureset, category, 1.0f);
    }
  
    public Classification(Collection<String> featureset, String category,
            double probability) {
        this.featureset = featureset;
        this.category = category;
        this.probability = probability;
    }

    public Collection<String> getFeatureset() {
        return featureset;
    }

    public double getProbability() {
        return this.probability;
    }

  
    public String getCategory() {
        return category;
    }
 
    @Override
    public String toString() {
        return "Classification [category=" + this.category
                + ", probability=" + this.probability
                + ", featureset=" + this.featureset
                + "]";
    }
}
