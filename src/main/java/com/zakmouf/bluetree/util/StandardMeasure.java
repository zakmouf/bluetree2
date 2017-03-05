package com.zakmouf.bluetree.util;

import com.zakmouf.bluetree.domain.BaseObject;

public class StandardMeasure extends BaseObject {
 
    private static final long serialVersionUID = 1L;
    
    private Double averageReturn;
    private Double standardDeviation;
    private Double sharpRatio;

    public Double getAverageReturn() {
	return averageReturn;
    }

    public void setAverageReturn(Double averageReturn) {
	this.averageReturn = averageReturn;
    }

    public Double getStandardDeviation() {
	return standardDeviation;
    }

    public void setStandardDeviation(Double standardDeviation) {
	this.standardDeviation = standardDeviation;
    }

    public Double getSharpRatio() {
	return sharpRatio;
    }

    public void setSharpRatio(Double sharpRatio) {
	this.sharpRatio = sharpRatio;
    }

    @Override
    public String toString() {
	return msg("averageReturn={0,number,0.0000%},standardDeviation={1,number,0.0000%},sharpRatio={2,number,0.0000}",
		averageReturn, standardDeviation, sharpRatio);
    }

}
