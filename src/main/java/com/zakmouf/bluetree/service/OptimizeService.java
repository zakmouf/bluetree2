package com.zakmouf.bluetree.service;

import java.util.List;

import com.zakmouf.bluetree.domain.Portfolio;
import com.zakmouf.bluetree.domain.Position;

public interface OptimizeService {

    public List<Position> optimize(Portfolio portfolio);

}
