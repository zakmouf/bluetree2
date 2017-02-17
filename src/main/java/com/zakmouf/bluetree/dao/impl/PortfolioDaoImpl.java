package com.zakmouf.bluetree.dao.impl;

import java.sql.Types;
import java.util.List;

import org.springframework.stereotype.Component;

import com.zakmouf.bluetree.dao.PortfolioDao;
import com.zakmouf.bluetree.dao.mapper.MarketRowMapper;
import com.zakmouf.bluetree.dao.mapper.PortfolioRowMapper;
import com.zakmouf.bluetree.dao.mapper.PositionRowMapper;
import com.zakmouf.bluetree.domain.Market;
import com.zakmouf.bluetree.domain.Portfolio;
import com.zakmouf.bluetree.domain.Position;
import com.zakmouf.bluetree.domain.User;

@Component
public class PortfolioDaoImpl extends BaseDaoImpl implements PortfolioDao {

    @Override
    public Portfolio findPortfolio(Long id) {
	String sql = "select * from portfolios where portfolio_id=?";
	Object[] args = { id };
	int[] argTypes = { Types.NUMERIC };
	return queryForObject(sql, args, argTypes, new PortfolioRowMapper());
    }

    @Override
    public void insertPortfolio(Portfolio portfolio) {
	String sql = "insert into portfolios values (null, ?, ?, ?, ?, ?, false, null)";
	Object[] args = { portfolio.getName(), portfolio.getFromDate(), portfolio.getToDate(), portfolio.getBeta(),
		portfolio.getSize() };
	int[] argTypes = { Types.VARCHAR, Types.DATE, Types.DATE, Types.NUMERIC, Types.NUMERIC };
	insert(sql, args, argTypes, portfolio);
    }

    @Override
    public void updatePortfolio(Portfolio portfolio) {
	String sql = "update portfolios set portfolio_from_date=?, portfolio_to_date=?, "
		+ "portfolio_executed=true, portfolio_error=? where portfolio_id=?";
	Object[] args = { portfolio.getFromDate(), portfolio.getToDate(), portfolio.getError(), portfolio.getId() };
	int[] argTypes = { Types.DATE, Types.DATE, Types.VARCHAR, Types.NUMERIC };
	update(sql, args, argTypes);
    }

    @Override
    public void deletePortfolio(Portfolio portfolio) {
	//
	String sql = "delete from portfolios where portfolio_id=?";
	Object[] args = { portfolio.getId() };
	int[] argTypes = { Types.NUMERIC };
	update(sql, args, argTypes);
	//
	sql = "delete from portfolio_lnk_stock where pls_portfolio_id=?";
	update(sql, args, argTypes);
	//
	sql = "delete from portfolio_lnk_user where plu_portfolio_id=?";
	update(sql, args, argTypes);
	//
	sql = "delete from portfolio_lnk_market where plm_portfolio_id=?";
	update(sql, args, argTypes);
    }

    @Override
    public List<Portfolio> getPortfolios(User user) {
	String sql = "select * from portfolios, portfolio_lnk_user where portfolio_id=plu_portfolio_id and plu_user_id=? order by portfolio_name";
	Object[] args = { user.getId() };
	int[] argTypes = { Types.NUMERIC };
	return queryForList(sql, args, argTypes, new PortfolioRowMapper());
    }

    @Override
    public void setUser(Portfolio portfolio, User user) {
	//
	String sql = "delete from portfolio_lnk_user where plu_portfolio_id=?";
	Object[] args = { portfolio.getId() };
	int[] argTypes = { Types.NUMERIC };
	update(sql, args, argTypes);
	//
	sql = "insert into portfolio_lnk_user values (?, ?)";
	args = new Object[] { portfolio.getId(), user.getId() };
	argTypes = new int[] { Types.NUMERIC, Types.NUMERIC };
	update(sql, args, argTypes);
    }

    @Override
    public Market getMarket(Portfolio portfolio) {
	String sql = "select * from portfolio_lnk_market, markets where market_id=plm_market_id and plm_portfolio_id=?";
	Object[] args = { portfolio.getId() };
	int[] argTypes = { Types.NUMERIC };
	return queryForObject(sql, args, argTypes, new MarketRowMapper());
    }

    @Override
    public void setMarket(Portfolio portfolio, Market market) {
	//
	String sql = "delete from portfolio_lnk_market where plm_portfolio_id=?";
	Object[] args = { portfolio.getId() };
	int[] argTypes = { Types.NUMERIC };
	update(sql, args, argTypes);
	//
	sql = "insert into portfolio_lnk_market values (?, ?)";
	args = new Object[] { portfolio.getId(), market.getId() };
	argTypes = new int[] { Types.NUMERIC, Types.NUMERIC };
	update(sql, args, argTypes);
    }

    @Override
    public List<Position> getPositions(Portfolio portfolio) {
	String sql = "select * from portfolio_lnk_stock, stocks where pls_stock_id=stock_id and pls_portfolio_id=? order by stock_symbol";
	Object[] args = { portfolio.getId() };
	int[] argTypes = { Types.NUMERIC };
	return queryForList(sql, args, argTypes, new PositionRowMapper());
    }

    @Override
    public void setPositions(Portfolio portfolio, List<Position> positions) {
	//
	String sql = "delete from portfolio_lnk_stock where pls_portfolio_id=?";
	Object[] args = { portfolio.getId() };
	int[] argTypes = { Types.NUMERIC };
	update(sql, args, argTypes);
	//
	for (Position position : positions) {
	    sql = "insert into portfolio_lnk_stock values (?, ?, ?)";
	    args = new Object[] { portfolio.getId(), position.getStock().getId(), position.getWeight() };
	    argTypes = new int[] { Types.NUMERIC, Types.NUMERIC, Types.NUMERIC };
	    update(sql, args, argTypes);
	}
    }

}