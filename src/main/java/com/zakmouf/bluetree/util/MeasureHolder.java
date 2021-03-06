package com.zakmouf.bluetree.util;

import com.zakmouf.bluetree.domain.Price;

import java.util.List;

public class MeasureHolder {

    private double riskFreeRate;

    private List<Price> indicePrices;
    private double[] indiceReturns;
    private StandardMeasure indiceMeasure;

    private List<Price> portfolioPrices;
    private double[] portfolioReturns;
    private StandardMeasure portfolioMeasure;

    private RegressionMeasure regressionMeasure;
    private CombinedMeasure combinedMeasure;

    public MeasureHolder(double riskFreeRate) {
        this.riskFreeRate = riskFreeRate;
    }

    private void initializeIndice() {
        indiceReturns = MeasureUtils.getReturns(indicePrices);
        indiceMeasure = new StandardMeasure();
        indiceMeasure.setAverageReturn(MeasureUtils.getMean(indiceReturns));
        indiceMeasure.setStandardDeviation(MeasureUtils.getStdev(indiceReturns));
        indiceMeasure.setSharpRatio(MeasureUtils.getSharpRatio(indiceReturns, riskFreeRate));
    }

    public List<Price> getIndicePrices() {
        return indicePrices;
    }

    public void setIndicePrices(List<Price> indicePrices) {
        this.indicePrices = indicePrices;
        initializeIndice();
    }

    public double[] getIndicePerformances() {
        return indiceReturns;
    }

    public StandardMeasure getIndiceMeasure() {
        return indiceMeasure;
    }

    private void initializePortfolio() {
        portfolioReturns = MeasureUtils.getReturns(portfolioPrices);
        portfolioMeasure = new StandardMeasure();
        portfolioMeasure.setAverageReturn(MeasureUtils.getMean(portfolioReturns));
        portfolioMeasure.setStandardDeviation(MeasureUtils.getStdev(portfolioReturns));
        portfolioMeasure.setSharpRatio(MeasureUtils.getSharpRatio(portfolioReturns, riskFreeRate));
    }

    public List<Price> getPortfolioPrices() {
        return portfolioPrices;
    }

    public void setPortfolioPrices(List<Price> portfolioPrices) {
        this.portfolioPrices = portfolioPrices;
        initializePortfolio();
        initializeRegression();
        initializeCombined();
    }

    public double[] getPortfolioReturns() {
        return portfolioReturns;
    }

    public StandardMeasure getPortfolioMeasure() {
        return portfolioMeasure;
    }

    private void initializeRegression() {
        regressionMeasure = new RegressionMeasure();
        regressionMeasure
                .setDecisionRatio(MeasureUtils.getDecisionRatio(indiceReturns, portfolioReturns, riskFreeRate));
        regressionMeasure.setBeta(MeasureUtils.getBeta(indiceReturns, portfolioReturns));
        regressionMeasure.setAlpha(MeasureUtils.getAlpha(indiceReturns, portfolioReturns));
        regressionMeasure.setBetaBear(MeasureUtils.getBetaBear(indiceReturns, portfolioReturns));
        regressionMeasure.setAlphaBear(MeasureUtils.getAlphaBear(indiceReturns, portfolioReturns));
        regressionMeasure.setBetaBull(MeasureUtils.getBetaBull(indiceReturns, portfolioReturns));
        regressionMeasure.setAlphaBull(MeasureUtils.getAlphaBull(indiceReturns, portfolioReturns));
    }

    public RegressionMeasure getRegressionMeasure() {
        return regressionMeasure;
    }

    private void initializeCombined() {
        combinedMeasure = new CombinedMeasure();
        combinedMeasure.setPortfolioMeasure(portfolioMeasure);
        combinedMeasure.setIndiceMeasure(indiceMeasure);
        combinedMeasure.setRegressionMeasure(regressionMeasure);
    }

    public CombinedMeasure getCombinedMeasure() {
        return combinedMeasure;
    }

}
