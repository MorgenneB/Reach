package com.reachcp317.reach;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;

/**
 * Retrieves/updates User information through a MySQL database
 * @author James Robertson
 * For more documentation see https://docs.spring.io/spring/docs/4.0.x/spring-framework-reference/html/jdbc.html
 */

@Repository
public class EventRepository{
	@Autowired
	private JdbcTemplate jdbcTemplate;

	/**
	 * Populates an Event object with data; database test
	 * @author James Robertson
	 *
	 */
	public static final class EventMapper implements RowMapper<Event>{

		@Override
		public Event mapRow(ResultSet rs, int rowNum) throws SQLException {
			Event event = new Event(rs.getInt("eventID"), rs.getInt("hostID"));
			//event.setName(rs.getString("name"));
			//event.setTotallnterested(rs.getInt("totalInterested"));
			//event.setEventType(rs.getArray("eventType"));
			event.setAddress(rs.getString("address"));
			event.setDescription(rs.getString("description"));
			event.setLatitude(rs.getDouble("latitude"));
			event.setLongitude(rs.getDouble("longitude"));
			event.setStartTime(rs.getDate("startDate"));
			event.setEndTime(rs.getDate("endDate"));
			event.setCapacity(rs.getInt("capacity"));
			//event.setEventRange(rs.getInt("eventRange"));
			return event;		
		}
	}

	/**
	 * Database test method
	 * @return
	 */
	public List<Event> viewAllEventnames(){
		return this.jdbcTemplate.query("SELECT * FROM event", new EventMapper());
	}

	/**
	 * Returns Event data given an id number, if the Event exists.
	 * @param id
	 * @return
	 */
	public Event getById(int id) {
		Event event = this.jdbcTemplate.queryForObject("SELECT * FROM event WHERE eventID = ?", new Object[] {id}, new EventMapper());
		return event;
	}
	
	//TODO: add selection by radius
	public List<Event> getMapMarkers() {
		return this.jdbcTemplate.query("SELECT * FROM event", new EventMapper());
	}
}