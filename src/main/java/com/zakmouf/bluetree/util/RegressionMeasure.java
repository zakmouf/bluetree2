package com.zakmouf.bluetree.util;

import com.zakmouf.bluetree.domain.BaseObject;

public class RegressionMeasure extends BaseObject {

    private static final long serialVersionUID = 1L;

    private Double decisionRatio;
    private Double alpha;
    private Double beta;
    private Double alphaBear;
    private Double betaBear;
    private Double alphaBull;
    private Double betaBull;

    public Double getDecisionRatio() {
	return decisionRatio;
    }

    public void setDecisionRatio(Double decisionRatio) {
	this.decisionRatio = decisionRatio;
    }

    public Double getAlpha() {
	return alpha;
    }

    public void setAlpha(Double alpha) {
	this.alpha = alpha;
    }

    public Double getBeta() {
	return beta;
    }

    public void setBeta(Double beta) {
	this.beta = beta;
    }

    public Double getAlphaBear() {
	return alphaBear;
    }

    public void setAlphaBear(Double alphaBear) {
	this.alphaBear = alphaBear;
    }

    public Double getBetaBear() {
	return betaBear;
    }

    public void setBetaBear(Double betaBear) {
	this.betaBear = betaBear;
    }

    public Double getAlphaBull() {
	return alphaBull;
    }

    public void setAlphaBull(Double alphaBull) {
	this.alphaBull = alphaBull;
    }

    public Double getBetaBull() {
	return betaBull;
    }

    public void setBetaBull(Double betaBull) {
	this.betaBull = betaBull;
    }

    @Override
    public String toString() {
	return msg(
		"decisionRatio=[%1$.5f],alpha=[%2$.5f],beta=[%3$.5f],alphaBear=[%4$.5f],betaBear=[%5$.5f],alphaBull=[%6$.5f],betaBull=[%7$.5f]",
		decisionRatio * 100D, alpha * 100D, beta, alphaBear * 100D, betaBear, alphaBull * 100D, betaBull);
    }

}
