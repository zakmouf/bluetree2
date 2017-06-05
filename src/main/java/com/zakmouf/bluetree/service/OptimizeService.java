package com.zakmouf.bluetree.service;

import com.zakmouf.bluetree.domain.Holding;
import com.zakmouf.bluetree.domain.Portfolio;

import java.util.List;

public interface OptimizeService {

    List<Holding> optimize(Portfolio portfolio);

}
