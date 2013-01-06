package ch.demo.business.service;

import javax.ejb.Local;

@Local
public interface StudentStatisticsService {

	public String getStatistics();
	
	public void count();
	
	public void clean();

}
