package com.zakmouf.bluetree.service;

import java.util.List;

import com.zakmouf.bluetree.domain.Portfolio;
import com.zakmouf.bluetree.domain.Holding;

public interface OptimizeService {

    List<Holding> optimize(Portfolio portfolio);

}
