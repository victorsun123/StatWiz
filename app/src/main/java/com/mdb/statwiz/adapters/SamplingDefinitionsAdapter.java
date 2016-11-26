package com.mdb.statwiz.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mdb.statwiz.R;
import com.mdb.statwiz.utils.Tuple;

import java.util.ArrayList;

/**
 * Created by Sayan Paul on 11/25/2016.
 */

public class SamplingDefinitionsAdapter extends RecyclerView.Adapter<SamplingDefinitionsAdapter.DefinitionViewHolder> {


    private static final ArrayList<Tuple<String, String>> DEFINITIONS = new ArrayList<Tuple<String, String>>() {
        {
            add(new Tuple<>("Population", "The entire group of individuals or instances about whom we hope to learn."));
            add(new Tuple<>("Sample", "A (representative) subset of a population, examined in hope of learning about the population."));
            add(new Tuple<>("Sample survey", "A study that asks questions of a sample drawn from some population in the hope of learning something about the entire population. Polls taken to assess voter preferences are common sample surveys."));
            add(new Tuple<>("Bias", "Any systematic failure of a sampling method to represent its population is bias. Biased sampling methods tend to over- or underestimate parameters. It is almost impossible to recover from bias, so efforts to avoid it are well spent. Common errors include:\n\n1. relying on voluntary response.\n2. undercoverage of the population.\n3. nonresponse bias.\n4. response bias."));
            add(new Tuple<>("Randomization", "The best defense against bias is randomization, in which each individual is given a fair, random chance of selection."));
            add(new Tuple<>("Sample size", "The number of individuals in a sample. The sample size determines how well the sample represents the population, not the fraction of the population sampled."));
            add(new Tuple<>("Census", "A sample that consists of the entire population is called a census."));
            add(new Tuple<>("Population parameter", "A numerically valued attribute of a model for a population. We rarely expect to know the true value of a population parameter, but we do hope to estimate it from sampled data. For example, the mean income of all employed people in the country is a population parameter."));
            add(new Tuple<>("Statistic, sample statistic", "Statistics are values calculated for sampled data. Those that correspond to, and thus estimate, a population parameter, are of particular interest. For example, the mean income of all employed people in a representative sample can provide a good estimate of the corresponding population parameter. The term “sample statistic” is sometimes used, usually to parallel the corresponding term “population parameter.”"));
            add(new Tuple<>("Representative", "A sample is said to be representative if the statistics comadded from it accurately reflect the corresponding population parameters."));
            add(new Tuple<>("Simple random sample (SRS)", "A simple random sample of sample size is a sample in which each set of elements in the population has an equal chance of selection."));
            add(new Tuple<>("Sampling frame", "A list of individuals from whom the sample is drawn is called the sampling frame. Individuals who may be in the population of interest, but who are not in the sampling frame, cannot be included in any sample."));
            add(new Tuple<>("Sampling variability", "The natural tendency of randomly drawn samples to differ, one from another. Sometimes, unfortunately, called sampling error, sampling variability is no error at all, but just the natural result of random sampling."));
            add(new Tuple<>("Stratified random sample", "A sampling design in which the population is divided into several subpopulations, or strata, and random samples are then drawn from each stratum. If the strata are homogeneous, but are different from each other, a stratified sample may yield more consistent results than an SRS."));
            add(new Tuple<>("Cluster sample", "A sampling design in which entire groups, or clusters, are chosen at random. Cluster sampling is usually selected as a matter of convenience, practicality, or cost. Each cluster should be representative of the population, so all the clusters should be heterogeneous and similar to each other."));
            add(new Tuple<>("Multistage sample", "Sampling schemes that combine several sampling methods are called multistage samples. For example, a national polling service may stratify the country by geographical regions, select a random sample of cities from each region, and then interview a cluster of residents in each city."));
            add(new Tuple<>("Systematic sample", "A sample drawn by selecting individuals systematically from a sampling frame. When there is no relationship between the order of the sampling frame and the variables of interest, a systematic sample can be representative."));
            add(new Tuple<>("Pilot", "A small trial run of a survey to check whether questions are clear. A pilot study can reduce errors due to ambiguous questions."));
            add(new Tuple<>("Voluntary response bias", "Bias introduced to a sample when individuals can choose on their own whether to participate in the sample. Samples based on voluntary response are always invalid and cannot be recovered, no matter how large the sample size."));
            add(new Tuple<>("Convenience sample", "A convenience sample consists of the individuals who are conveniently available. Convenience samples often fail to be representative because every individual in the population is not equally convenient to sample."));
            add(new Tuple<>("Undercoverage", "A sampling scheme that biases the sample in a way that gives a part of the population less representation than it has in the population suffers from undercoverage."));
            add(new Tuple<>("Nonresponse bias", "Bias introduced when a large fraction of those sampled fails to respond. Those who do respond are likely to not represent the entire population. Voluntary response bias is a form of nonresponse bias, but nonresponse may occur for other reasons. For example, those who are at work during the day won’t respond to a telephone survey conducted only during working hours."));
            add(new Tuple<>("Response bias", "Anything in a survey design that influences responses falls under the heading of response bias. One typical response bias arises from the wording of questions, which may suggest a favored response. Voters, for example, are more likely to express support of “the president” than support of the particular person holding that office at the moment."));
            add(new Tuple<>("Definitions Source", "Bock, David E., Paul F. Velleman, and Richard D. De Veaux. Stats: Modeling the World. 3rd ed. Boston: Addison-Wesley, 2010. Print."));
        }
    };

    @Override
    public DefinitionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sampling_row_view,
                parent, false);
        return new DefinitionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DefinitionViewHolder holder, int position) {
        String term = DEFINITIONS.get(position).key;
        String definition = DEFINITIONS.get(position).value;
        holder.term.setText(term);
        holder.definition.setText(definition);
    }

    @Override
    public int getItemCount() {
        return DEFINITIONS.size();
    }

    class DefinitionViewHolder extends RecyclerView.ViewHolder {
        TextView term, definition;

        DefinitionViewHolder(View v) {
            super(v);
            term = (TextView) v.findViewById(R.id.term);
            definition = (TextView) v.findViewById(R.id.definition);
        }
    }
}
